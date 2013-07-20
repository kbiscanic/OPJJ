package hr.fer.zemris.linearna;

import java.text.DecimalFormat;

/**
 * Abstract class implementing common methods from {@link IVector} interface.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public abstract class AbstractVector implements IVector {
	/**
	 * Default constructor for {@link AbstractVector}. Does nothing.
	 */
	public AbstractVector() {
		super();
	}

	/**
	 * Delta value for double comparisons.
	 */
	private static final double DELTA = 10E-6;

	public abstract double get(int index);

	public abstract IVector set(int index, double value);

	public abstract int getDimension();

	public abstract IVector copy();

	public abstract IVector newInstance(int dimension);

	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Vectors must have same dimensions!");
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}

	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Vectors must have same dimensions!");
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}

	@Override
	public IVector scalarMultiply(double byValue) {
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}

	@Override
	public IVector nScalarMultiply(double byValue) {
		return this.copy().scalarMultiply(byValue);
	}

	@Override
	public double norm() {
		double norm = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			double current = this.get(i);
			norm += current * current;
		}
		return Math.sqrt(norm);
	}

	@Override
	public IVector normalize() {
		return this.scalarMultiply(1. / this.norm());
	}

	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}

	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		return this.scalarProduct(other) / (this.norm() * other.norm());
	}

	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException(
					"Vectors must have same dimensions!");
		}
		double scalarP = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			scalarP += this.get(i) * other.get(i);
		}

		return scalarP;
	}

	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if (3 != other.getDimension() || this.getDimension() != 3) {
			throw new IncompatibleOperandException(
					"Both vectors must have 3 dimensions!");
		}
		IVector nVP = this.newInstance(3);
		nVP.set(0, this.get(1) * other.get(2) - this.get(2) * other.get(1));
		nVP.set(1, -(this.get(0) * other.get(2) - this.get(2) * other.get(0)));
		nVP.set(2, this.get(0) * other.get(1) - this.get(1) * other.get(0));
		return nVP;
	}

	@Override
	public IVector nFromHomogeneus() throws ArithmeticException {
		IVector nFH = this.newInstance(this.getDimension() - 1);
		double num = this.get(this.getDimension() - 1);
		if (num - 0 < DELTA) {
			throw new ArithmeticException("Division by 0!");
		}
		for (int i = this.getDimension() - 2; i >= 0; i--) {
			nFH.set(i, this.get(i) / num);
		}
		return nFH;

	}

	@Override
	public double[] toArray() {
		double[] arr = new double[this.getDimension()];
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			arr[i] = this.get(i);
		}
		return arr;
	}

	@Override
	public IVector copyPart(int n) throws IllegalArgumentException {
		if (n < 1) {
			throw new IllegalArgumentException(
					"You must copy at least 1 element!");
		}
		IVector cP = this.newInstance(n);
		int dimension = this.getDimension();
		for (int i = n - 1; i >= 0; i--) {
			if (i >= dimension) {
				cP.set(i, 0);
			} else {
				cP.set(i, this.get(i));
			}
		}
		return cP;
	}

	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(this, true);
		} else {
			IMatrix matrix = new Matrix(1, getDimension());
			for (int i = this.getDimension() - 1; i >= 0; i--) {
				matrix.set(0, i, this.get(i));
			}
			return matrix;
		}
	}

	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(this, false);
		} else {
			IMatrix matrix = new Matrix(getDimension(), 1);
			for (int i = this.getDimension() - 1; i >= 0; i--) {
				matrix.set(i, 0, this.get(i));
			}
			return matrix;
		}
	}

	/**
	 * Method used to generate a textual representation of the given Vector.
	 * 
	 * @param precision
	 *            Precision for double numbers.
	 * @return String containing textual representation of the given Vector.
	 */
	public String toString(int precision) {
		StringBuilder stringB = new StringBuilder();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(precision);
		df.setMinimumFractionDigits(precision);
		stringB.append('(');
		stringB.append(df.format(this.get(0)));
		int dimension = this.getDimension();
		for (int i = 1; i < dimension; i++) {
			stringB.append(", " + df.format(this.get(i)));
		}
		stringB.append(')');
		return stringB.toString();
	}

	@Override
	public String toString() {
		return this.toString(3);
	}

}
