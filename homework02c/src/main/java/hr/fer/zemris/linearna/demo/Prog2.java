package hr.fer.zemris.linearna.demo;

import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.Vector;

/**
 * Program containing example <code>1.2.1</code>.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Prog2 {

	/**
	 * Main method executing program.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		IVector a = Vector.parseSimple("1 0 0");
		IVector b = Vector.parseSimple("5 0 0");
		IVector c = Vector.parseSimple("3 8 0");

		IVector t = Vector.parseSimple("3 4 0");

		double pov = b.nSub(a).nVectorProduct(c.nSub(a)).norm();
		double povA = b.nSub(t).nVectorProduct(c.nSub(t)).norm();
		double povB = a.nSub(t).nVectorProduct(c.nSub(t)).norm();
		double povC = a.nSub(t).nVectorProduct(b.nSub(t)).norm();

		double t1 = povA / pov;
		double t2 = povB / pov;
		double t3 = povC / pov;

		System.out.println("Baricentricne koordinate su: (" + t1 + ", " + t2
				+ ", " + t3 + ").");

	}

}
