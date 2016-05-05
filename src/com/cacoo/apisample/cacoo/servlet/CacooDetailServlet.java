package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * Show detailed information of the diagram
 */
public class CacooDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diagramId = request.getParameter("diagramId");
		try{
			HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + diagramId + ".json", null);

			// Check if user has been authenticated with OAuth
			if(res==null) {
				return;
			}

			// Retrieve JSON object and set attributes
			JsonElement json = CacooUtils.httpResponse2json(res);
			JsonObject root = json.getAsJsonObject();
			request.setAttribute("diagramId", diagramId);
			request.setAttribute("title", root.get("title").getAsString());
			request.setAttribute("imageUrl", root.get("imageUrl").getAsString());
			if(!root.get("description").isJsonNull()){
				request.setAttribute("description", root.get("description").getAsString());
			}

			// Create a map to hold the diagram comments
			List<Map<String, String>> comments = new ArrayList<>();
			request.setAttribute("comments", comments);
			JsonArray jsonComments = root.get("comments").getAsJsonArray();
			for(int i=0; i<jsonComments.size(); i++) {
				JsonObject c = jsonComments.get(i).getAsJsonObject();
				JsonObject user = c.get("user").getAsJsonObject();
				Map<String,String> map = new HashMap<>();
				comments.add(map);
				map.put("imageUrl", user.get("imageUrl").getAsString());
				map.put("userName", user.get("nickname").getAsString());
				map.put("comment", c.get("content").getAsString());
			}
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/detail.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
}
