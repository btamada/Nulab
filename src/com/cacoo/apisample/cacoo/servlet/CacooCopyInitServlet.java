package com.cacoo.apisample.cacoo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Forward to input page of diagram copy
 */
public class CacooCopyInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("diagramId", request.getParameter("diagramId"));
		request.getRequestDispatcher("/WEB-INF/jsp/cacoo/copy_input.jsp").forward(request, response);
	}
}
