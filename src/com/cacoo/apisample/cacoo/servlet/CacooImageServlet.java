package com.cacoo.apisample.cacoo.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.cacoo.apisample.cacoo.CacooUtils;

/*
 * Show an image of the diagram.
 * https://cacoo.com/api_image
 */
public class CacooImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diagramId = request.getParameter("diagramId");
		try{
			HttpResponse res = CacooUtils.cacooApi(request, response, "api/v1/diagrams/" + diagramId + ".png", null);

			// Check if user has been authenticated with OAuth
			if(res==null) {
				return;
			}

			response.setContentType(res.getEntity().getContentType().getValue());
			response.setContentLength((int)res.getEntity().getContentLength());
			BufferedInputStream in = new BufferedInputStream(res.getEntity().getContent());
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

			try {
				byte[] buffer = new byte[64*1024];
				while(true){
					int len = in.read(buffer);
					if(len < 0){
						break;
					}
					out.write(buffer, 0, len);
				}
			} finally {
				try {
					in.close();
				} catch (Exception e) {}

				try {
					out.close();
				} catch (Exception e) {}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/cacoo/error.jsp").forward(request, response);
		}
	}
}
