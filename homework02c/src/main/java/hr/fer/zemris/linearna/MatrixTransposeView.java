package hr.fer.zemris.linearna;

/**
 * Class representing Live View to a Transposed Matrix of the given Matrix.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class MatrixTransposeView extends AbstractMatrix {

	private IMatrix orig;

	/**
	 * Default constructor.
	 * 
	 * @param original
	 *            Original Matrix.
	 */
	public MatrixTransposeView(IMatrix original) {
		super();
		this.orig = original;
	}

	@Override
	public int getRowsCount() {
		return orig.getColsCount();
	}

	@Override
	public int getColsCount() {
		return orig.getRowsCount();
	}

	@Override
	public double get(int row, int col) throws IndexOutOfBoundsException {
		return orig.get(col, row);
	}

	@Override
	public IMatrix set(int row, int col, double value)
			throws IndexOutOfBoundsException {
		return orig.set(col, row, value);
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
