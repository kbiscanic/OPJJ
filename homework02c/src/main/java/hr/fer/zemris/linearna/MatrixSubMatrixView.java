package hr.fer.zemris.linearna;

/**
 * Class representing Live View to a Sub Matrix of the given Matrix.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class MatrixSubMatrixView extends AbstractMatrix {

	private int[] rowIndexes;
	private int[] colIndexes;
	private IMatrix orig;

	/**
	 * Default constructor. Creating Live View to a Matrix without
	 * <code>row</code> row and <code>col</code> column.
	 * 
	 * @param original
	 *            Original Matrix.
	 * @param row
	 *            Row we are hiding.
	 * @param col
	 *            Column we are hiding.
	 */
	public MatrixSubMatrixView(IMatrix original, int row, int col) {
		this.orig = original;
		this.rowIndexes = new int[orig.getRowsCount() - 1];
		int curr = orig.getRowsCount() - 1;
		for (int i = orig.getRowsCount() - 2; i >= 0; i--) {
			if (curr == row) {
				curr--;
				i++;
				continue;
			}
			this.rowIndexes[i] = curr--;
		}

		this.colIndexes = new int[orig.getColsCount() - 1];
		curr = orig.getColsCount() - 1;
		for (int i = orig.getColsCount() - 2; i >= 0; i--) {
			if (curr == col) {
				curr--;
				i++;
				continue;
			}
			this.colIndexes[i] = curr--;
		}

	}

	@Override
	public int getRowsCount() {
		return this.rowIndexes.length;
	}

	@Override
	public int getColsCount() {
		return this.colIndexes.length;
	}

	@Override
	public double get(int row, int col) throws IndexOutOfBoundsException {
		return orig.get(rowIndexes[row], colIndexes[col]);
	}

	@Override
	public IMatrix set(int row, int col, double value)
			throws IndexOutOfBoundsException {
		return orig.set(rowIndexes[row], colIndexes[col], value);
	}

	@Override
	public IMatrix copy() {
		return orig.copy();
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return orig.newInstance(rows, cols);
	}

}
