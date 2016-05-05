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
 * Show button to create new diagram and list of diagram
 */
public class CacooDiagramsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/account.json", null);

            // Check if user has been authenticated with OAuth
            if(res==null){
				return;
			}

            // Retrieve user account information
			JsonElement json = CacooUtils.httpResponse2json(res);
			JsonObject root = json.getAsJsonObject();
            request.setAttribute("uid", root.get("name").getAsString());
            request.setAttribute("userName", root.get("nickname").getAsString());
            request.setAttribute("imageUrl", root.get("imageUrl").getAsString());
            request.setAttribute("type", root.get("type").getAsString());

            // Retrieve user license information
            res = CacooUtils.cacooApi(request, response, "api/v1/account/license.json", null);

            // Check if user has been authenticated with OAuth
            if(res == null) {
                return;
            }
            json = CacooUtils.httpResponse2json(res);
            root = json.getAsJsonObject();
            request.setAttribute("plan", root.get("plan").getAsString());
            request.setAttribute("remainingSheets", root.get("remainingSheets").getAsString());
            request.setAttribute("remainingSharedFolders", root.get("remainingSharedFolders").getAsString());
            request.setAttribute("maxNumberOfSharersPerDiagram", root.get("maxNumberOfSharersPerDiagram").getAsString());
            request.setAttribute("maxNumberOfSharersPerSharedFolder", root.get("maxNumberOfSharersPerSharedFolder").getAsString());
            request.setAttribute("canCreateSheet", root.get("canCreateSheet").getAsString());
            request.setAttribute("canCreateSharedFolder", root.get("canCreateSharedFolder").getAsString());
            request.setAttribute("canExportVectorFormat", root.get("canExportVectorFormat").getAsString());

            // Retrieve user diagram information
			res = CacooUtils.cacooApi(request, response, "api/v1/diagrams.json", null);

            // Check if user has been authenticated with OAuth
            if(res == null) {
				return;
			}

            // Retrieve the list of diagrams from user account
			json = CacooUtils.httpResponse2json(res);
			root = json.getAsJsonObject();
			JsonArray result = root.get("result").getAsJsonArray();
			List<Map<String, String>> diagrams = new ArrayList<>();
            request.setAttribute("diagrams", diagrams);

            // Store list of diagrams in a map for retrieval after redirect
			for(int i = 0; i < result.size(); i++){
				JsonObject diagram = result.get(i).getAsJsonObject();
				Map<String, String> map = new HashMap<>();
				diagrams.add(map);
				map.put("diagramId", diagram.get("diagramId").getAsString());
				map.put("title", diagram.get("title").getAsString());
				map.put("imageUrl", diagram.get("imageUrl").getAsString());
			}
            request.getRequestDispatcher("/WEB-INF/jsp/cacoo/diagrams.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
	
}
