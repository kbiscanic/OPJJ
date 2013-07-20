package hr.fer.zemris.linearna;

import java.text.DecimalFormat;

/**
 * Abstract class implementing {@link IMatrix} interface. This class provides
 * common methods for all possible implementations.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public abstract class AbstractMatrix implements IMatrix {

	/**
	 * Delta value for double comparisons.
	 */
	private static final double DELTA = 10E-6;

	/**
	 * Default constructor for {@link AbstractMatrix}. Does nothing.
	 */
	public AbstractMatrix() {
		super();
	}

	public abstract int getRowsCount();

	public abstract int getColsCount();

	public abstract double get(int row, int col);

	public abstract IMatrix set(int row, int col, double value);

	public abstract IMatrix copy();

	public abstract IMatrix newInstance(int rows, int cols);

	@Override
	public IMatrix nTranspose(boolean liveView) {
		if (liveView) {
			return new MatrixTransposeView(this);
		} else {
			IMatrix matrix = this.newInstance(getColsCount(), getRowsCount());
			for (int i = this.getColsCount() - 1; i >= 0; i--) {
				for (int j = this.getRowsCount() - 1; j >= 0; j--) {
					matrix.set(i, j, this.get(j, i));
				}
			}
			return matrix;
		}
	}

	@Override
	public IMatrix add(IMatrix other) throws IncompatibleOperandException {
		if (this.getColsCount() != other.getColsCount()
				|| this.getRowsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Both matrices must have same dimensions!");
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	@Override
	public IMatrix sub(IMatrix other) throws IncompatibleOperandException {
		if (this.getColsCount() != other.getColsCount()
				|| this.getRowsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Both matrices must have same dimensions!");
		}
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	@Override
	public IMatrix nMultiply(IMatrix other) throws IncompatibleOperandException {
		if (this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException(
					"Matrices aren't compatible for multiplication!");
		}
		int common = this.getColsCount();
		int rows = this.getRowsCount();
		int cols = other.getColsCount();
		IMatrix nMul = this.newInstance(rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double value = 0;
				for (int k = 0; k < common; k++) {
					value += this.get(i, k) * other.get(k, j);
				}
				nMul.set(i, j, value);
			}
		}
		return nMul;
	}

	@Override
	public double determinant() throws IncompatibleOperandException {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException("Matrix must be square!");
		}
		int size = this.getColsCount();
		if (size == 1) {
			return this.get(0, 0);
		} else if (size == 2) {
			return this.get(0, 0) * this.get(1, 1) - this.get(0, 1)
					* this.get(1, 0);
		}
		double determinant = 0;
		for (int curr = 0; curr < size; curr++) {
			determinant += this.get(0, curr) * Math.pow(-1., curr)
					* this.subMatrix(0, curr, true).determinant();
		}
		return determinant;
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(this, row, col);
		} else {
			IMatrix matrix = this.newInstance(this.getRowsCount() - 1,
					this.getColsCount() - 1);
			int pi = 0;
			for (int i = this.getRowsCount() - 1; i >= 0; i--) {
				if (i == row) {
					pi = 1;
					continue;
				}
				int pj = 0;
				for (int j = this.getColsCount() - 1; j >= 0; j--) {
					if (j == col) {
						pj = 1;
						continue;
					}
					matrix.set(i - 1 + pi, j - 1 + pj, this.get(i, j));
				}
			}
			return matrix;
		}
	}

	@Override
	public IMatrix nInvert() throws IncompatibleOperandException {
		if (this.determinant() - 0 < DELTA) {
			throw new IncompatibleOperandException("Matrix ins't invertible!");
		}
		IMatrix cofactors = this.newInstance(this.getRowsCount(),
				this.getColsCount());
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				cofactors.set(i, j,
						Math.pow(-1, i + j)
								* this.subMatrix(i, j, true).determinant());
			}
		}
		IMatrix adj = cofactors.nTranspose(true);
		return adj.scalarMultiply(1. / this.determinant());
	}

	@Override
	public double[][] toArray() {
		double[][] array = new double[this.getRowsCount()][this.getColsCount()];
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				array[i][j] = this.get(i, j);
			}
		}
		return array;
	}

	@Override
	public IVector toVector(boolean liveView) {
		if (this.getColsCount() != 1 && this.getRowsCount() != 1) {
			throw new IncompatibleOperandException(
					"Matrix must have 1 column or row!");
		}
		if (liveView) {
			return new VectorMatrixView(this);
		} else {
			double[] array = new double[Math
					.max(getRowsCount(), getColsCount())];
			if (getRowsCount() == 1) {
				for (int i = getColsCount() - 1; i >= 0; i--) {
					array[i] = this.get(0, i);
				}
			} else {
				for (int i = getRowsCount() - 1; i >= 0; i--) {
					array[i] = this.get(i, 0);
				}
			}
			return new Vector(array);
		}
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}

	@Override
	public IMatrix makeIdentity() {
		for (int i = this.getRowsCount() - 1; i >= 0; i--) {
			for (int j = this.getColsCount() - 1; j >= 0; j--) {
				if (i == j) {
					this.set(i, j, 1);
				} else {
					this.set(i, j, 0);
				}
			}
		}
		return this;
	}

	public String toString() {
		return this.toString(3);
	}

	/**
	 * Method used to generate a textual representation of the given Matrix.
	 * 
	 * @param precision
	 *            Precision for double numbers.
	 * @return String containing textual representation of the given Matrix.
	 */
	public String toString(int precision) {
		StringBuilder stringB = new StringBuilder();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(precision);
		df.setMinimumFractionDigits(precision);

		int rows = this.getRowsCount();
		int cols = this.getColsCount();

		for (int i = 0; i < rows; i++) {
			stringB.append('[');
			stringB.append(df.format(this.get(i, 0)));
			for (int j = 1; j < cols; j++) {
				stringB.append(", " + df.format(this.get(i, j)));
			}
			stringB.append("]\n");
		}
		stringB.deleteCharAt(stringB.length() - 1);

		return stringB.toString();
	}
}
