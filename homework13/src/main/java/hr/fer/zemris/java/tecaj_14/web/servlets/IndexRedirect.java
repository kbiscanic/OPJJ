package hr.fer.zemris.java.tecaj_14.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to redirect requests from <code>index.jsp</code> to
 * <code>servleti/main</code> servlet.
 * 
 * @author Kristijan Biscanic
 */
@WebServlet("/index.jsp")
public class IndexRedirect extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("servleti/main");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("servleti/main");

	}

}