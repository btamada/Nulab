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
 * Post a comment for the diagram
 */
public class CacooCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String diagramId = request.getParameter("diagramId");
		String comment = request.getParameter("comment");

		try {
			Map<String,String> params = new HashMap<>();
			params.put("content", comment);
			HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + diagramId + "/comments/post.json", params);

			// Check if user has been authenticated with OAuth
			if(res == null) {
				return;
			}

			// Reload page so the new comment will appear
			response.sendRedirect("detail?diagramId=" + diagramId);
			
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request
			.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp")
			.forward(request, response);
		}
	}
}
