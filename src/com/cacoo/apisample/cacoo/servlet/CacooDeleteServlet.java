package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;

/*
 * Delete a diagram
 */
public class CacooDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + request.getParameter("diagramId") + "/delete.json", null);

			// Check if user has been authenticated with OAuth
			if(res==null) {
				return;
			}
			if(res.getStatusLine().getStatusCode()!= 200){
				request.setAttribute("message", "Error : " + res.getStatusLine().getReasonPhrase() + " (" + res.getStatusLine().getStatusCode() + ")");
			}else{
				request.setAttribute("message", "Diagram has been deleted.");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/delete.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
}
