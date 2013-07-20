package hr.fer.zemris.java.web.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet used to dynamically generate a sample pie chart every time when
 * called.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/reportImage")
public class ReportImage extends HttpServlet {

	private static final long serialVersionUID = 5242093131624546059L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/png");
		OutputStream out = resp.getOutputStream();

		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "OS usage");

		BufferedImage chartImage = chart.createBufferedImage(600, 600);
		ChartUtilities.writeBufferedImageAsPNG(out, chartImage);
		out.flush();
		out.close();
	}

	/**
	 * Method used to create chart with given title from given dataset.
	 * 
	 * @param dataset
	 *            Dataset.
	 * @param title
	 *            Title.
	 * @return Chart.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true,
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	/**
	 * Method used to create dataset from list.s
	 * 
	 * @param list
	 *            List.
	 * @return Dataset.
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		return result;
	}

}
