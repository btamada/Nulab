package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * Create a new diagram and redirect to the editing page
 */
public class CacooCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{

			// Create Diagram
            HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/create.json", null);

			// Check if user has been authenticated with OAuth
			if(res==null) {
				return;
			}
            JsonElement json = CacooUtils.httpResponse2json(res);
            JsonObject root = json.getAsJsonObject();
			String diagramId = root.get("diagramId").getAsString();
			
			// Open Editing Page
			res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + diagramId + "/editor/token.json", null);

			// Check if user has been authenticated with OAuth
			if(res==null) {
				return;
			}
			json = CacooUtils.httpResponse2json(res);
			root = json.getAsJsonObject();
			String editorToken = root.get("token").getAsString();
			
			// Redirect to Editing Page
			response.sendRedirect(CacooUtils.editorLink(diagramId, editorToken, "", request));
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
}
