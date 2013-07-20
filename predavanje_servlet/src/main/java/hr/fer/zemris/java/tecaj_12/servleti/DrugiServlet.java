package hr.fer.zemris.java.tecaj_12.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/drugi")
public class DrugiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String sBroj = req.getParameter("broj");
		if (sBroj != null) {
			Long broj = null;
			try {
				broj = Long.valueOf(sBroj);
			} catch (Exception ignorable) {
			}
			if (broj == null) {
				req.setAttribute("uspjeh", Integer.valueOf(2));
			} else {
				req.setAttribute("uspjeh", Integer.valueOf(3));
				req.setAttribute("predaniBroj", broj);
				req.setAttribute("rezultat", broj * broj);
			}
		} else {
			req.setAttribute("uspjeh", Integer.valueOf(1));
		}

		req.getRequestDispatcher("/WEB-INF/pages/sucelje.jsp").forward(req,
				resp);
	}
}
