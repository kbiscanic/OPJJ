package hr.fer.zemris.java.tecaj_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Solution to problem 4 of homework03.
 *
 * @author Kristijan Biscanic
 */
public class AboveAverage {

	private static final double MULTIPLICATOR = 1.2;

	/**
	 * Main method for this program. Reading double values from standard input
	 * and displaying only those who are at least 20% greater then average.
	 *
	 * @param args
	 *            Unused.
	 * @throws IOException
	 *             In case of exception while reading from keyboard.
	 */
	public static void main(final String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, "UTF-8"));

		List<Double> list = new ArrayList<Double>();

		String line = null;
		while (true) {
			line = reader.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.equalsIgnoreCase("quit")) {
				break;
			}
			list.add(Double.parseDouble(line));
		}

		Collections.sort(list);

		double avg = 0;
		double cnt = 0;
		for (Double element : list) {
			cnt++;
			avg += element.doubleValue();
		}

		avg /= cnt;
		avg *= MULTIPLICATOR;

		for (Double element : list) {
			if (element.doubleValue() >= avg) {
				System.out.println(element.doubleValue());
			}
		}

	}

}
