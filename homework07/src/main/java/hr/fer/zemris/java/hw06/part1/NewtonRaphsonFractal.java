package hr.fer.zemris.java.hw06.part1;

import hr.fer.zemris.java.hw06.part1.ComplexImplementations.Complex;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.ComplexPolynomial;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.ComplexRootedPolynomial;

/**
 * Class representing computation of Newton-Raphson fractal.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class NewtonRaphsonFractal {

	/**
	 * Method used to generate a Newton-Raphson fractal from given arguments.
	 */
	public static void generate(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int yMin, int yMax,
			short maxIter, double convergenceTreshold, double rootTreshold,
			ComplexRootedPolynomial rooted, ComplexPolynomial polynomial,
			ComplexPolynomial derived, short[] data) {

		int offset = yMin * width;

		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				Complex c = new Complex(x * (reMax - reMin) / (width - 1)
						+ reMin, (height - 1 - y) * (imMax - imMin)
						/ (height - 1) + imMin);
				Complex zn = c;
				short iteration = 0;
				double module = 0;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.divide(denominator);
					Complex zn1 = zn.sub(fraction);
					module = zn1.sub(zn).module();
					zn = zn1;

				} while (iteration < maxIter && module > convergenceTreshold);
				data[offset] = (short) rooted.indexOfClosestRootFor(zn,
						rootTreshold);
				offset++;
			}
		}

	}
}
