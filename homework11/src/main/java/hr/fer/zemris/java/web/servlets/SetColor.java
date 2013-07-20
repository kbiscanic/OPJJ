package hr.fer.zemris.java.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet mapped to /setcolor URL and used to set background color for all
 * pages in current session. Servlet is setting new color to string received from clr parameter.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/setcolor")
public class SetColor extends HttpServlet {

	private static final long serialVersionUID = -4809550590193668131L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String color = req.getParameter("clr");

		if (color != null) {
			req.getSession().setAttribute("pickedBgCol", color);
		}

		if (req.getSession().getAttribute("pickedBgCol") == null) {
			req.getSession().setAttribute("pickedBgCol", "WHITE");
		}

		resp.sendRedirect(req.getContextPath() + "/index.jsp");

	}

}
