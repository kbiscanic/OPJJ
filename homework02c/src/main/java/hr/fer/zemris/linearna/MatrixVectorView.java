package hr.fer.zemris.linearna;

/**
 * Class representing Live View to a Vector pretending to be Matrix.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class MatrixVectorView extends AbstractMatrix {

	private boolean asRowMatrix;
	private IVector orig;

	/**
	 * Default constructor.
	 * 
	 * @param original
	 *            Original Vector.
	 * @param asRowMatrix
	 *            determines whether view should be RowMatrix or ColumnMatrix.
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		super();
		this.orig = original;
		this.asRowMatrix = asRowMatrix;
	}

	@Override
	public int getRowsCount() {
		if (asRowMatrix) {
			return 1;
		} else {
			return orig.getDimension();
		}
	}

	@Override
	public int getColsCount() {
		if (asRowMatrix) {
			return orig.getDimension();
		} else {
			return 1;
		}
	}

	@Override
	public double get(int row, int col) throws IndexOutOfBoundsException {
		if (asRowMatrix) {
			if (row != 0) {
				throw new IndexOutOfBoundsException("Index row out of bounds!");
			}
			return orig.get(col);
		} else {
			if (col != 0) {
				throw new IndexOutOfBoundsException("Index col out of bounds!");
			}
			return orig.get(row);
		}
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if (asRowMatrix) {
			if (row != 0) {
				throw new IndexOutOfBoundsException("Index row out of bounds!");
			}
			return orig.set(col, value).toRowMatrix(true);
		} else {
			if (col != 0) {
				throw new IndexOutOfBoundsException("Index col out of bounds!");
			}
			return orig.set(row, value).toColumnMatrix(true);
		}
	}

	@Override
	public IMatrix copy() {
		if (asRowMatrix) {
			return orig.copy().toRowMatrix(false);
		} else {
			return orig.copy().toColumnMatrix(false);
		}
	}

	@Override
	public IMatrix newInstance(int rows, int cols)
			throws IndexOutOfBoundsException {
		if (asRowMatrix) {
			if (rows != 1) {
				throw new IndexOutOfBoundsException("Index row out of bounds!");
			}
			return orig.newInstance(cols).toRowMatrix(false);
		} else {
			if (cols != 1) {
				throw new IndexOutOfBoundsException("Index col out of bounds!");
			}
			return orig.newInstance(rows).toColumnMatrix(false);
		}
	}

}
