package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cacoo.apisample.cacoo.CacooUtils;
import com.cacoo.apisample.cacoo.CacooUtils.OAuthToken;

/*
 * You will be redirected to this Servlet after authentication.
 */
public class CacooCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String oauthVerifier = request.getParameter("oauth_verifier");
		try{
			OAuthToken token = CacooUtils.retrieveAccessToken(request, oauthVerifier);
			HttpSession session = request.getSession();

            // Retrieve the OAuth tokens
			session.setAttribute(CacooUtils.SESSION_CACOO_TOKEN, token);
			String url = (String)session.getAttribute(CacooUtils.SESSION_NEXT_URL);

            // Redirect to the URL after authentication
			session.removeAttribute(CacooUtils.SESSION_NEXT_URL);
			response.sendRedirect(url);
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
	
}
