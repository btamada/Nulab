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

/*
 * Copy diagram
 */
public class CacooCopyInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diagramId = request.getParameter("diagramId");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		try{
			Map<String, String> params = new HashMap<>();
			params.put("diagramId", diagramId);
			params.put("title", title);
			params.put("description", description);
			HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + diagramId + "/copy.json", params);

			// Check if user has been authenticated with OAuth
			if(res==null){
				return;
			}
			if(res.getStatusLine().getStatusCode() != 200){
				request.setAttribute("message", "Error : " + res.getStatusLine().getReasonPhrase() + " (" + res.getStatusLine().getStatusCode()+")");
			}else{
				request.setAttribute("message", "Diagrams has been copied");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/copy_done.jsp").forward(request, response);

		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
}
