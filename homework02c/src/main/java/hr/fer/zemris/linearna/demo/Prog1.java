package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Program containing example <code>1.1.2</code>.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Prog1 {

	/**
	 * Main method executing program.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		IMatrix m1 = Matrix.parseSimple("1 2 3 | 4 5 6");
		IMatrix m2 = m1.nTranspose(true);

		System.out.println("m1: ");
		System.out.println(m1.toString());
		System.out.println("m2: ");
		System.out.println(m2.toString());
		System.out.println();

		m2.set(2, 1, 9);

		System.out.println("m1: ");
		System.out.println(m1.toString());
		System.out.println("m2: ");
		System.out.println(m2.toString());
	}

}
