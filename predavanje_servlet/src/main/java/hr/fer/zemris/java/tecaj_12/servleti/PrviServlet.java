package hr.fer.zemris.java.tecaj_12.servleti;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrviServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");

		PrintWriter writer = resp.getWriter();

		String sBroj = req.getParameter("broj");
		if (sBroj != null) {
			Long broj = null;
			try {
				broj = Long.valueOf(sBroj);
			} catch (Exception ignorable) {
			}
			if (broj == null) {
				writer.print("<html><body><h1>Hello World!</h1><p>Primljen je neispravan parametar.</p></body></html>");
			} else {
				writer.print("<html><body><h1>Hello World!</h1><p>Kvadrat broja"
						+ broj + " je " + (broj * broj) + "</p></body></html>");
			}
		} else {
			writer.print("<html><body><h1>Hello World!</h1></body></html>");
		}
	}
}
