package hr.fer.zemris.linearna;

/**
 * Class extending {@link AbstractMatrix}. This class stores Matrix as 2D double
 * array.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Matrix extends AbstractMatrix {

	/**
	 * Container containing actual elements.
	 */
	private double[][] elements;
	/**
	 * Number of rows in the matrix.
	 */
	private int rows;
	/**
	 * Number of columns in the matrix.
	 */
	private int cols;

	/**
	 * Default constructor. Creating <code>rowx</code>x<code>cols</code>
	 * {@link Matrix} filled with 0.
	 * 
	 * @param rows
	 *            Number of rows needed.
	 * @param cols
	 *            Number of columns needed.
	 * @throws IllegalArgumentException
	 *             if <code> rows < 1 </code> or <code> cols < 1 </code>
	 */
	public Matrix(int rows, int cols) {
		super();
		if (rows < 1) {
			throw new IllegalArgumentException(
					"Matrix must have at least 1 row!");
		}
		if (cols < 1) {
			throw new IllegalArgumentException(
					"Matrix must have at least 1 column!");
		}
		this.rows = rows;
		this.cols = cols;
		this.elements = new double[rows][cols];
	}

	/**
	 * Alternative constructor. Creates new {@link Matrix} from given 2D array.
	 * 
	 * @param rows
	 *            Number of rows needed.
	 * @param cols
	 *            Number of columns needed.
	 * @param elements
	 *            Starting elements.
	 * @param useGiven
	 *            Permission to use given array.
	 */
	public Matrix(int rows, int cols, double[][] elements, boolean useGiven) {
		super();
		if (rows < 1) {
			throw new IllegalArgumentException(
					"Matrix must have at least 1 row!");
		}
		if (cols < 1) {
			throw new IllegalArgumentException(
					"Matrix must have at least 1 column!");
		}
		this.rows = rows;
		this.cols = cols;
		if (useGiven) {
			this.elements = elements;
		} else {
			this.elements = elements.clone();
		}
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int row, int col) throws IndexOutOfBoundsException {
		if (row < 0 || row > rows) {
			throw new IndexOutOfBoundsException("Argument row out of bounds!");
		}
		if (col < 0 || col > cols) {
			throw new IndexOutOfBoundsException("Argument col out of bounds!");
		}
		return this.elements[row][col];
	}

	@Override
	public IMatrix set(int row, int col, double value)
			throws IndexOutOfBoundsException {
		if (row < 0 || row > rows) {
			throw new IndexOutOfBoundsException("Argument row out of bounds!");
		}
		if (col < 0 || col > cols) {
			throw new IndexOutOfBoundsException("Argument col out of bounds!");
		}
		this.elements[row][col] = value;
		return this;
	}

	@Override
	public IMatrix copy() {
		return new Matrix(rows, cols, elements, false);
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * Static method used to parse string into a new {@link Matrix}.
	 * 
	 * @param s
	 *            String that needs to be parsed.
	 * @return New {@link Matrix}.
	 * @throws IllegalArgumentException
	 *             if given string isn't correctly formatted or is
	 *             <code>null</code>.
	 */
	public static Matrix parseSimple(String s) throws IllegalArgumentException {
		if (s == null) {
			throw new IllegalArgumentException("String is null!");
		}
		String[] columns = s.split("\\|");
		int rows = columns.length;
		int cols = columns[0].split(" ").length;
		double[][] elems = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			columns[i] = columns[i].trim();
			String[] red = columns[i].split(" ");
			if (red.length != cols) {
				throw new IllegalArgumentException(
						"Unable to parse string! Illegal input!");
			}
			for (int j = 0; j < cols; j++) {
				try {
					elems[i][j] = Double.parseDouble(red[j]);
				} catch (NumberFormatException nex) {
					throw new IllegalArgumentException(
							"String isn't correctly formatted!");
				}
			}
		}
		return new Matrix(rows, cols, elems, true);
	}

}
