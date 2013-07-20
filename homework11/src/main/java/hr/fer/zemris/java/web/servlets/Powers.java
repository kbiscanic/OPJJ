package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used to dynamically generate XLS document containing powers from 1 to
 * <code>n</code> of all numbers in range from <code>a</code> to <code>b</code>.
 * <code>a</code>, <code>b</code> and <code>n</code> are received as parameters
 * from the URL. If they are illegal or missing, request is forwarded to
 * <code>invalidParameters.jsp</code> page.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/powers")
public class Powers extends HttpServlet {

	private static final long serialVersionUID = -7481145003399156233L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment;filename=Powers.xls");

		Integer a = null;
		Integer b = null;
		Integer n = null;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException | NullPointerException nfe) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidParameters.jsp")
					.forward(req, resp);
			return;
		}

		if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidParameters.jsp")
					.forward(req, resp);
		}

		OutputStream out = resp.getOutputStream();
		if (a > b) {
			int temp = b;
			b = a;
			a = temp;
		}

		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet(Integer.toString(i));
			int t = 0;
			for (int curr = a; curr <= b; curr++) {
				HSSFRow row = sheet.createRow(t++);
				row.createCell(0).setCellValue(Integer.toString(curr));
				row.createCell(1).setCellValue(
						Integer.toString((int) Math.pow(curr, i)));
			}
		}

		hwb.write(out);
		out.flush();
		out.close();

	}

}
