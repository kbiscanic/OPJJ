package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Program containing example <code>1.2.4</code>.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Prog5 {

	/**
	 * Main method executing program.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {

		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, Charset.forName("UTF-8")));

		System.out
				.println("Napomena: Vektori se unose kao razmakom odvojeni brojevi!");
		System.out.print("Unesite prvi vektor: ");
		IVector first = null;
		try {
			first = Vector.parseSimple(reader.readLine());
		} catch (IOException ex) {
			System.out.println("Unable to read!");
			System.exit(-1);
		}
		System.out.print("Unesite drugi vektor: ");
		IVector second = null;
		try {
			second = Vector.parseSimple(reader.readLine());
		} catch (IOException ex) {
			System.out.println("Unable to read!");
			System.exit(-1);
		}

		if (first.getDimension() != second.getDimension()) {
			System.out.println("Both vectors must be in same dimensions!");
			System.exit(-1);
		}
		if (first.getDimension() != 2 && first.getDimension() != 3) {
			System.out.println("Vectors must be 2D or 3D!");
		}

		IVector sol = first.scalarMultiply(
				2. * second.scalarProduct(first) / first.norm() / first.norm())
				.nSub(second);

		System.out.println("Refleksni vektor = " + sol.toString());
	}

}
