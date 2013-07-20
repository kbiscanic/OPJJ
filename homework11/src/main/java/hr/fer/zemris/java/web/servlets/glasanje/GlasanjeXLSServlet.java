package hr.fer.zemris.java.web.servlets.glasanje;

import hr.fer.zemris.java.web.servlets.glasanje.GlasanjeRezultatiServlet.VotingResultEntry;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used to dynamically generate a XLS document containing current
 * results of voting. There is only 1 sheet created containing 2 columns. First
 * column contains bands names and second one contains number of current votes.
 * Bands are sorted descending by number of votes.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	private static final long serialVersionUID = -5500377006820689677L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition",
				"attachment;filename=Rezultati.xls");
		OutputStream out = resp.getOutputStream();

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("results");

		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Band name");
		headerRow.createCell(1).setCellValue("Votes");

		int t = 1;
		for (VotingResultEntry vre : (List<VotingResultEntry>) req.getSession()
				.getAttribute("results")) {
			HSSFRow row = sheet.createRow(t++);
			row.createCell(0).setCellValue(vre.getName());
			row.createCell(1).setCellValue(vre.getVotes());
		}

		hwb.write(out);
		out.flush();
		out.close();
	}

}
