package hr.fer.zemris.java.hw06.part1;

import hr.fer.zemris.java.hw06.part1.ComplexImplementations.Complex;
import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Program used to display Newton-Raphson fractal for any complex polynomial
 * determined by at least 2 of its roots.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Newton {

	/**
	 * Main method for this program.
	 * 
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {

		System.out
				.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out
				.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> roots = new ArrayList<>();

		int cnt = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, StandardCharsets.UTF_8));
		String line = null;
		while (true) {
			cnt++;
			System.out.print("Root " + cnt + "> ");
			try {
				line = reader.readLine();
			} catch (IOException e) {
				System.err.println("IO error!");
				System.exit(-1);
			}
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.equals("done")) {
				break;
			}
			roots.add(toComplex(line));
		}

		if (roots.size() < 2) {
			System.out.println("Polynomial must have at least 2 roots!");
			System.exit(-1);
		}

		Complex[] roots2 = roots.toArray(new Complex[0]);

		IFractalProducer generator = new NewtonGenerator(roots2);

		FractalViewer.show(generator);

	}

	/**
	 * Private static method used to parse <code>line</code> into a
	 * {@link Complex} number.
	 * 
	 * @param line
	 *            Input.
	 * @return New {@link Complex} number from given input.
	 */
	private static Complex toComplex(String line) {
		double re;
		double im;
		boolean negative = false;

		String input = line.trim();

		if (input.charAt(0) == '+') {
			input = input.substring(1).trim();
		}

		if (input.charAt(0) == '-') {
			negative = true;
			input = input.substring(1).trim();
		}

		if (input.charAt(0) == 'i') {
			re = 0;
			input = input.substring(1).trim();
			if (input.isEmpty()) {
				im = 1;
			} else {
				im = Double.parseDouble(input);
			}
			return new Complex(re, negative ? -im : im);
		}

		String[] splitted = input.split("\\s");
		re = Double.parseDouble(splitted[0]);
		re = negative ? -re : re;

		if (splitted.length == 1) {
			im = 0;
		} else {
			im = toComplex(input.substring(splitted[0].length()).trim())
					.getImag();
		}
		return new Complex(re, im);
	}
}
