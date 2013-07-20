package hr.fer.zemris.linearna;

/**
 * Class representing Live View to a Matrix pretending to be Vector.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class VectorMatrixView extends AbstractVector {

	private int dimension;
	private boolean rowMatrix;
	private IMatrix orig;

	/**
	 * Default constructor.
	 * 
	 * @param original
	 *            Original Matrix.
	 */
	public VectorMatrixView(IMatrix original) {
		super();
		this.orig = original;
		if (original.getColsCount() != 1 && original.getRowsCount() != 1) {
			throw new IncompatibleOperandException(
					"Matrix must have 1 column or row!");
		}
		this.rowMatrix = orig.getRowsCount() == 1;
		if (rowMatrix) {
			this.dimension = orig.getColsCount();
		} else {
			this.dimension = orig.getRowsCount();
		}
	}

	@Override
	public double get(int index) throws IndexOutOfBoundsException {
		if (rowMatrix) {
			return orig.get(0, index);
		} else {
			return orig.get(index, 0);
		}
	}

	@Override
	public IVector set(int index, double value)
			throws IndexOutOfBoundsException {
		if (rowMatrix) {
			return orig.set(0, index, value).toVector(true);
		} else {
			return orig.set(index, 0, value).toVector(true);
		}
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public IVector copy() {
		return this.orig.copy().toVector(false);
	}

	@Override
	public IVector newInstance(int dimension) {
		if (rowMatrix) {
			return this.orig.newInstance(1, dimension).toVector(false);
		} else {
			return this.orig.newInstance(dimension, 1).toVector(false);
		}

	}

}
