package hr.fer.zemris.java.hw06.part1;

/**
 * Abstract class containing implementations of {@link Complex},
 * {@link ComplexPolynomial} and {@link ComplexRootedPolynomial} classes. All 3
 * classes are used for computing with complex numbers and polynomials. All 3
 * classes are immutabsle.
 * 
 * @author Kristijan Biscanic
 * 
 */
public abstract class ComplexImplementations {

	/**
	 * Class representing a single complex number. Real and imaginary parts are
	 * both saved with double precision. This class is immutable once created.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public static class Complex {

		/**
		 * Real part of complex number.
		 */
		private final double real;
		/**
		 * Imaginary part of complex number.
		 */
		private final double imag;

		public static final Complex ZERO = new Complex(0, 0);
		public static final Complex ONE = new Complex(1, 0);
		public static final Complex ONE_NEG = new Complex(-1, 0);
		public static final Complex IM = new Complex(0, 1);
		public static final Complex IM_NEG = new Complex(0, -1);

		/**
		 * Default constructor that creates Complex number representing value of
		 * 0.
		 */
		public Complex() {
			this(0, 0);
		}

		/**
		 * Constructor for creating {@link Complex} object which is representing
		 * a complex number.
		 * 
		 * @param real
		 *            Real part of complex number.
		 * @param imag
		 *            Imaginary part of complex number.
		 */
		public Complex(double real, double imag) {
			super();
			this.real = real;
			this.imag = imag;
		}

		/**
		 * Getter method for real part of complex number.
		 * 
		 * @return Value of real part.
		 */
		public double getReal() {
			return real;
		}

		/**
		 * Getter method for imaginary part of complex number.
		 * 
		 * @return Value of imaginary part.
		 */
		public double getImag() {
			return imag;
		}

		/**
		 * Method used to compute a module of complex number.
		 * 
		 * @return Value of module.
		 */
		public double module() {
			return Math.sqrt(real * real + imag * imag);
		}

		/**
		 * Method used to multiply current complex number with
		 * <code>other</code>.
		 * 
		 * @param other
		 *            Second multiplicand for multiplying.
		 * @return New {@link Complex} number.
		 */
		public Complex multiply(Complex other) {
			return new Complex(real * other.getReal() - imag * other.getImag(),
					imag * other.getReal() + real * other.getImag());
		}

		/**
		 * Method used to divide current complex number with <code>other</code>.
		 * 
		 * @param other
		 *            Divisor of current number.
		 * @return New {@link Complex} number.
		 */
		public Complex divide(Complex other) {
			double real2 = other.getReal();
			double imag2 = other.getImag();
			return new Complex((real * real2 + imag * imag2)
					/ (real2 * real2 + imag2 * imag2), (imag * real2 - real
					* imag2)
					/ (real2 * real2 + imag2 * imag2));
		}

		/**
		 * Method used to add <code>other</code> complex number to current.
		 * 
		 * @param other
		 *            Number we are adding to the current one.
		 * @return new {@link Complex} number.
		 */
		public Complex add(Complex other) {
			return new Complex(real + other.getReal(), imag + other.getImag());
		}

		/**
		 * Method used to subtract <code>other</code> complex number from
		 * current.
		 * 
		 * @param other
		 *            Number we are subtracting from the current one.
		 * @return new {@link Complex} number.
		 */
		public Complex sub(Complex other) {
			return new Complex(real - other.getReal(), imag - other.getImag());
		}

		/**
		 * Method used to retrieve new {@link Complex} number that is negation
		 * of the current one.
		 * 
		 * @return New {@link Complex} number.
		 */
		public Complex negate() {
			return new Complex(-real, -imag);
		}

		@Override
		public String toString() {
			boolean realZero = Math.abs(real) <= 10E-6;
			boolean imagZero = Math.abs(imag) <= 10E-6;
			if (realZero && imagZero) {
				return "0";
			}
			if (realZero) {
				if (imag > 0) {
					return "i" + imag;
				} else {
					return "-i" + Math.abs(imag);
				}
			}
			if (imagZero) {
				return Double.toString(real);
			}
			StringBuilder builder = new StringBuilder();
			builder.append(real + " ");
			builder.append(imag > 0 ? "+" : "-");
			builder.append("i" + Math.abs(imag));
			return builder.toString();
		}

	}

	/**
	 * Class representing polynomial of complex numbers determined by its roots.
	 * Roots are saved as {@link Complex} numbers. This class is immutable once
	 * created.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public static class ComplexRootedPolynomial {

		/**
		 * Array of {@link Complex} numbers representing roots for given
		 * polynomial.
		 */
		private final Complex[] roots;

		/**
		 * Default constructor creating a {@link ComplexRootedPolynomial} object
		 * from given <code>roots</code>.
		 * 
		 * @param roots
		 *            Roots of polynomial.
		 */
		public ComplexRootedPolynomial(Complex... roots) {
			super();
			this.roots = roots;
		}

		/**
		 * Method used to apply a complex number <code>z</code> into polynomial.
		 * 
		 * @param z
		 *            {@link Complex} number we are applying.
		 * @return {@link Complex} value of polynomial after applying given
		 *         number.
		 */
		public Complex apply(Complex z) {
			Complex solution = z.sub(roots[roots.length - 1]);
			for (int i = roots.length - 2; i >= 0; i--) {
				solution = solution.multiply(z.sub(roots[i]));
			}

			return solution;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < roots.length; i++) {
				builder.append("(z -(" + roots[i].negate().toString() + "))");
			}
			return builder.toString();
		}

		/**
		 * Method used to get {@link ComplexPolynomial} equivalent of this
		 * {@link ComplexRootedPolynomial}.
		 * 
		 * @return New {@link ComplexPolynomial}.
		 */
		public ComplexPolynomial toComplexPolynom() {
			ComplexPolynomial solution = new ComplexPolynomial(Complex.ONE,
					roots[0].negate());
			for (int i = 1; i < roots.length; i++) {
				solution = solution.multiply(new ComplexPolynomial(Complex.ONE,
						roots[i].negate()));
			}
			return solution;
		}

		/**
		 * Method used to get index of closes root for given <code>number</code>
		 * . Number needs to be at most <code>threshold</code> distanced from
		 * root.
		 * 
		 * @param number
		 *            Number for which we are the finding closest root.
		 * @param threshold
		 *            Threshold.
		 * @return Value representing number of roots. 0 if there is no such
		 *         roots.
		 */
		public int indexOfClosestRootFor(Complex number, double threshold) {
			int sol = -1;
			double min = 0;
			for (int i = 0; i < roots.length; i++) {
				double current = number.sub(roots[i]).module();
				if (current > threshold) {
					continue;
				}
				if (sol == -1 || current < min) {
					sol = i;
					min = current;
				}
			}

			return sol + 1;
		}
	}

	/**
	 * Class representing a polynomial determined by its coefficients.
	 * Coefficients are saved as {@link Complex} objects. This class is
	 * immutable once created.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public static class ComplexPolynomial {

		/**
		 * Array of {@link Complex} numbers representing coefficients for given
		 * polynomial. <code>factors[0]</code> is representing the coefficient
		 * for highest power in polynomial.
		 */
		private final Complex[] factors;

		/**
		 * Default constructor creating a {@link ComplexPolynomial} object from
		 * given <code>factors</code>.
		 * 
		 * @param factors
		 *            Coefficients of polynomial.
		 */
		public ComplexPolynomial(Complex... factors) {
			super();
			this.factors = factors;
		}

		/**
		 * Method returning order of current polynomial.
		 * 
		 * @return Order.
		 */
		public short order() {
			return (short) (factors.length - 1);
		}

		/**
		 * Method used to multiply current {@link ComplexPolynomial} with
		 * <code>other</code> one.
		 * 
		 * @param other
		 *            Second multiplicand.
		 * @return New {@link ComplexPolynomial} representing the result.
		 */
		public ComplexPolynomial multiply(ComplexPolynomial other) {
			int newOrder = this.order() + other.order();
			Complex[] newFactors = new Complex[newOrder + 1];
			for (int i = 0; i <= newOrder; i++) {
				newFactors[i] = Complex.ZERO;
			}
			for (int i = order(); i >= 0; i--) {
				for (int j = other.order(); j >= 0; j--) {
					Complex nn = factors[i].multiply(other.factors[j]);
					newFactors[i + j] = newFactors[i + j].add(nn);
				}
			}

			return new ComplexPolynomial(newFactors);
		}

		/**
		 * Method used to compute the first derivation of current polynomial.
		 * 
		 * @return New {@link ComplexPolynomial} representing the first
		 *         derivation.
		 */
		public ComplexPolynomial derive() {
			Complex[] newFactors = new Complex[this.order()];

			for (int i = 0; i < newFactors.length; i++) {
				newFactors[i] = factors[i].multiply(new Complex(this.order()
						- i, 0));
			}

			return new ComplexPolynomial(newFactors);
		}

		/**
		 * Method used to apply given complex <code>number</code> into
		 * polynomial.
		 * 
		 * @param number
		 *            {@link Complex} number.
		 * @return {@link Complex} value after applying.
		 */
		public Complex apply(Complex number) {
			Complex solution = new Complex();
			for (int i = 0; i < factors.length; i++) {
				Complex temp = Complex.ONE;
				for (int j = 0; j < factors.length - 1 - i; j++) {
					temp = temp.multiply(number);
				}
				temp = temp.multiply(factors[i]);
				solution = solution.add(temp);
			}
			return solution;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			if (factors.length > 1) {
				builder.append("(" + factors[0].toString() + ")z^"
						+ (factors.length - 1));
				for (int i = 1; i < factors.length - 1; i++) {
					builder.append("+(" + factors[i].toString() + ")z^"
							+ (factors.length - 1 - i));
				}
			}
			builder.append("+(" + factors[factors.length - 1] + ")");
			return builder.toString();
		}

	}

}
