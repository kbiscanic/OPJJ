package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;

/**
 * Program containing example <code>1.2.2</code>.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Prog3 {

	/**
	 * Main method executing program.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		IMatrix a = Matrix.parseSimple("3 5 | 2 10");
		IMatrix r = Matrix.parseSimple("2 | 8");
		IMatrix v = a.nInvert().nMultiply(r);
		System.out.println("Rjesenje sustava je: ");
		System.out.println(v);

	}

}
