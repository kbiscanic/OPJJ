package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.Matrix;
import hr.fer.zemris.linearna.Vector;

/**
 * Program containing example <code>1.2.3</code>.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Prog4 {

	/**
	 * Main method executing program.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		IMatrix mat = Matrix.parseSimple("1 5 3 | 0 0 8 | 1 1 1");

		IMatrix t = Vector.parseSimple("3 4 1").toColumnMatrix(true);

		IMatrix sol = mat.nInvert().nMultiply(t);

		double t1 = sol.get(0, 0);
		double t2 = sol.get(1, 0);
		double t3 = sol.get(2, 0);

		System.out.println("Baricentricne koordinate su: (" + t1 + ", " + t2
				+ ", " + t3 + ").");
	}

}
