package hr.web.adresar.servleti;

import hr.web.adresar.Adresar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listaj")
public class Listaj extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath(
				"WEB-INF/adresar-baza.txt");
		Adresar adresar = Adresar.procitajIzDatoteke(fileName);

		req.setAttribute("zapisi", adresar.dohvatiSveZapise());
		req.getRequestDispatcher("/WEB-INF/pages/Listaj.jsp")
				.forward(req, resp);
	}

}
