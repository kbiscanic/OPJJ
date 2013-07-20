package hr.web.adresar.servleti;

import hr.web.adresar.Adresar;
import hr.web.adresar.Record;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/save")
public class Save extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		obradi(req, resp);
	}

	protected void obradi(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		if (!"Pohrani".equals(req.getParameter("metoda"))) {
			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/listaj");
			return;
		}

		FormularForm f = new FormularForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		if (!f.imaGresaka()) {
			Record r = new Record();
			f.popuniURecord(r);

			String fileName = req.getServletContext().getRealPath(
					"/WEB-INF/adresar-baza.txt");
			Adresar adresar = Adresar.procitajIzDatoteke(fileName);
			adresar.zapamti(r);
			adresar.spremiUDatoteku(fileName);

			resp.sendRedirect(req.getServletContext().getContextPath()
					+ "/listaj");
			return;
		}

		req.setAttribute("zapis", f);
		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req,
				resp);

	}

}
