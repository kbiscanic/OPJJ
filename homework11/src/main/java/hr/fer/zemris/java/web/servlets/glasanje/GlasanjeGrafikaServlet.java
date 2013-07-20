package hr.fer.zemris.java.web.servlets.glasanje;

import hr.fer.zemris.java.web.servlets.glasanje.GlasanjeRezultatiServlet.VotingResultEntry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
 * Servlet used to dynamically generate a pie chart of results in voting
 * competition.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	private static final long serialVersionUID = 4242711836346154315L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("image/png");
		OutputStream out = resp.getOutputStream();

		PieDataset dataset = createDataset((List<VotingResultEntry>) req
				.getSession().getAttribute("results"));
		JFreeChart chart = createChart(dataset, "Voting results");

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
		plot.setForegroundAlpha(0.7f);
		return chart;
	}

	/**
	 * Method used to create dataset from list.s
	 * 
	 * @param list
	 *            List.
	 * @return Dataset.
	 */
	private PieDataset createDataset(List<VotingResultEntry> list) {
		DefaultPieDataset result = new DefaultPieDataset();
		for (VotingResultEntry vre : list) {
			result.setValue(vre.getName(), (double) vre.getVotes());
		}
		return result;
	}

}
