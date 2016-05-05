package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * Edit diagram
 */
public class CacooEditorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diagramId = request.getParameter("diagramId");
		try {

            HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + diagramId + "/editor/token.json", null);

			// Check if user has been authenticated with OAuth
			if(res == null){
				return;
			}

            JsonElement json = CacooUtils.httpResponse2json(res);
            JsonObject root = json.getAsJsonObject();
			String editorToken = root.get("token").getAsString();
			
			// Insert parameters into the map
			Map<String,String> params = new HashMap<>();

			String imageUrl = "https://www.google.com";
			
			params.put("command", "{\"operations\":[{\"type\":\"AddImageUrl\",\"parameter\":{\"url\":\""+imageUrl+"\",\"x\":10,\"y\":10}}]}");
			res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + diagramId + "/editor/automation.json", params);

			// Check if user has been authenticated with OAuth
			if(res == null){
				return;
			}
			json = CacooUtils.httpResponse2json(res);
			root = json.getAsJsonObject();
			String automationToken = root.get("token").getAsString();
			
			// Redirect to editing page
			response.sendRedirect(CacooUtils.editorLink(diagramId, editorToken, automationToken, request));
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
}
