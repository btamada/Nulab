package com.cacoo.apisample.cacoo;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CacooUtils {

    // Cacoo API OAuth consumer key and secret
	private static final String CONSUMER_KEY = "PEeTYkAhUYkbmCuPgFlYBV";
	private static final String CONSUMER_SECRET = "INkNnTAkeyqkVIkyxERTwWWMdHGaDqkzBMbPmbDopS";

    // Cacoo session information
	public static final String SESSION_CACOO_TOKEN = "SESSION_CACOO_TOKEN";
	public static final String SESSION_NEXT_URL = "SESSION_NEXT_URL";
	public static final String SESSION_OAUTH_PROVIDER = "SESSION_OAUTH_PROVIDER";
	public static final String SESSION_OAUTH_CONSUMER = "SESSION_OAUTH_CONSUMER";

	// List of Cacoo URLs for redirection and OAuth authentication
	public static final String CACOO_URL = "https://cacoo.com/";
    private static final String ACCESS_TOKEN_URL = CACOO_URL + "oauth/access_token";
    private static final String AUTHORIZE_URL = CACOO_URL + "oauth/authorize";
    private static final String REQUEST_TOKEN_URL = CACOO_URL + "oauth/request_token";
	
    /*
     * Access to Cacoo API.
     */
    public static HttpResponse cacooApi(HttpServletRequest request, HttpServletResponse response, String apiCall, Map<String,String> params)
			throws Exception{

    	System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
    	System.setProperty("org.apache.commons.logging.simplelog.showdatetime","true");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http","DEBUG");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire","DEBUG");
    			
		OAuthConsumer consumer = createConsumer(request);

		// Check if user has been authenticated with OAuth
		if(consumer.getToken()==null){
			setupAuthRedirect(request, response, consumer);
			return null;
		}

		HttpRequestBase method = null;
		if(params!=null && !params.isEmpty()){
			StringBuilder query = new StringBuilder();
			String sep = "";
			for(String name : params.keySet()){
				query.append(sep).append(name).append("=").append(URLEncoder.encode(params.get(name), "UTF-8"));
				sep="&";
			}

			HttpPost post = new HttpPost(CACOO_URL + apiCall);
			StringEntity entity = new StringEntity(query.toString());
			entity.setContentType("application/x-www-form-urlencoded");
			post.setEntity(entity);
			method = post;
		} else {
			method = new HttpGet(CACOO_URL + apiCall);
		}
		consumer.sign(method);
		HttpClient client = new DefaultHttpClient();
		HttpResponse res = client.execute(method);

        // Check if the user has been lost authentication
		if(res.getStatusLine().getStatusCode() == 401) {
			setupAuthRedirect(request, response, consumer);
			return null;
		}
		return res;
	}
    
    /*
     * Convert the HTTP response to a JSON object
     */
    public static JsonElement httpResponse2json(HttpResponse response) {
    	try {
    		String s = EntityUtils.toString(response.getEntity());
    		// System.out.println("## JSON : "+s);
    		return new JsonParser().parse(s);
    	} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /*
     * Retrieve OAuth access token.
     */
    public static OAuthToken retrieveAccessToken(HttpServletRequest request, String oauthVerifier) throws Exception {
    	HttpSession session = request.getSession();
    	OAuthConsumer consumer = (OAuthConsumer)session.getAttribute(SESSION_OAUTH_CONSUMER);
    	OAuthProvider provider = (OAuthProvider)session.getAttribute(SESSION_OAUTH_PROVIDER);
    	session.removeAttribute(SESSION_OAUTH_CONSUMER);
    	session.removeAttribute(SESSION_OAUTH_PROVIDER);
    	
		provider.retrieveAccessToken(consumer, oauthVerifier, new String[0]);
		OAuthToken token = new OAuthToken();
		token.setAccessToken(consumer.getToken());
		token.setTokenSecret(consumer.getTokenSecret());
		return token;
    }
    
    /*
     * URL for diagram editor.
     */
    public static String editorLink(String diagramId, String editorToken, String automationToken, HttpServletRequest request){
    	JsonObject root = new JsonObject();
    	root.addProperty("callbackUrl", createRequestHost(request)+request.getContextPath() + "/cacoo/");
    	JsonArray buttons = new JsonArray();
    	JsonObject closeButton = new JsonObject();
    	closeButton.addProperty("label", "CLOSE");
    	closeButton.addProperty("action", "saveAndExit");
    	buttons.add(closeButton);
    	root.add("buttons",buttons);
    	try {
    		String url = CACOO_URL + "diagrams/" + diagramId + "/edit?parameter=" + URLEncoder.encode(root.toString(),"UTF-8");
    		if (editorToken != null && editorToken.length() > 0) {
    			url += "&editorToken=" + URLEncoder.encode(editorToken, "UTF-8");
    		}
    		if (automationToken != null && automationToken.length() > 0) {
    			url += "&automationToken=" + URLEncoder.encode(automationToken, "UTF-8");
    		}
    		return url;
    	}catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /*
     * Access Token and Token Secret for OAuth.
     */
    public static class OAuthToken {
    	private String accessToken;
    	private String tokenSecret;
		public String getAccessToken() {
			return accessToken;
		}
		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
		public String getTokenSecret() {
			return tokenSecret;
		}
		public void setTokenSecret(String tokenSecret) {
			this.tokenSecret = tokenSecret;
		}
    	
    }
	
	/*
	 * Redirected to Cacoo for login.
	 */
	private static void setupAuthRedirect(HttpServletRequest request, HttpServletResponse response, OAuthConsumer consumer) throws Exception{
		OAuthProvider provider = new CommonsHttpOAuthProvider(REQUEST_TOKEN_URL, ACCESS_TOKEN_URL, AUTHORIZE_URL);
		String query = request.getQueryString();
		if(query == null || query.isEmpty()){
			query = "";
		} else {
			query = "?" +query;
		}

		HttpSession session= request.getSession();
		session.setAttribute(SESSION_NEXT_URL, request.getRequestURL().toString() + query);
		session.setAttribute(SESSION_OAUTH_CONSUMER, consumer);
		session.setAttribute(SESSION_OAUTH_PROVIDER, provider);
		String url = null;
		try {
			url = provider.retrieveRequestToken(consumer, createCallbackURL(request), new String[0]);
			response.sendRedirect(url);
		} catch (OAuthException e) {
			request.setAttribute("message", "Unable to connect to Cacoo API");
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
			return;
		}
	}
	
	/*
	 * Callback URL after OAuth authentication.
	 */
	private static String createCallbackURL(HttpServletRequest request){
		String url = createRequestHost(request);
		url += request.getContextPath() + "/cacoo/callback";
		return url;
	}
	
	/*
	 * Scheme and host part of the request URL.
	 * example: http://example.com:8080
	 */
	private static String createRequestHost(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName();
		int port = request.getServerPort();
		if(port!=80){
			url += ":"+port;
		}
		return url;
	}
	
	/*
	 * Create OAuth consumer.
	 */
	private static OAuthConsumer createConsumer(HttpServletRequest request) {
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
				CONSUMER_SECRET);

        // Retrieve the token from the authentication server
		HttpSession session = request.getSession();
		OAuthToken token = (OAuthToken)session.getAttribute(SESSION_CACOO_TOKEN);
		if(token!=null) {
			consumer.setTokenWithSecret(token.getAccessToken(), token.getTokenSecret());
		}
		return consumer;
	}
    
}
