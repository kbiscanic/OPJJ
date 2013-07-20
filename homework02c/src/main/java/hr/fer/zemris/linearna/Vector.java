package hr.fer.zemris.linearna;

/**
 * {@link Vector} is a class extending {@link AbstractVector} and saving
 * elements into private double array.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 * */
public class Vector extends AbstractVector {
	private double[] elements;
	private int dimensions;
	private boolean readOnly;

	/**
	 * Default constructor creating mutable {@link Vector} with given elements.
	 * 
	 * @param elements
	 *            Variable number of elements.
	 */
	public Vector(double... elements) {
		this(false, false, elements);
	}

	/**
	 * Constructor for creating new {@link Vector} object.
	 * 
	 * @param readOnly
	 *            Determines whether newly created {@link Vector} should be
	 *            mutable or not.
	 * @param useGiven
	 *            Determines if newly created {@link Vector} can just store
	 *            reference to the given array of elements-
	 * @param elements
	 *            Variable number of elements used to create new {@link Vector}
	 *            object.
	 */
	public Vector(boolean readOnly, boolean useGiven, double... elements)
			throws IllegalArgumentException {
		super();
		if (elements == null) {
			throw new IllegalArgumentException("Elements is null!");
		}
		this.readOnly = readOnly;
		this.dimensions = elements.length;
		if (useGiven) {
			this.elements = elements;
		} else {
			this.elements = new double[dimensions];
			for (int i = dimensions - 1; i >= 0; i--) {
				this.elements[i] = elements[i];
			}
		}
	}

	@Override
	public double get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > dimensions - 1) {
			throw new IndexOutOfBoundsException("Index is out of bounds!");
		}
		return elements[index];
	}

	@Override
	public IVector set(int index, double value)
			throws IndexOutOfBoundsException, UnmodifiableObjectException {
		if (index < 0 || index > dimensions - 1) {
			throw new IndexOutOfBoundsException("Index is out of bounds!");
		} else if (readOnly) {
			throw new UnmodifiableObjectException("This vector is read-only!");
		}
		elements[index] = value;
		return this;
	}

	@Override
	public int getDimension() {
		return dimensions;
	}

	@Override
	public IVector copy() {
		return new Vector(readOnly, false, elements);
	}

	@Override
	public IVector newInstance(int dimension) {
		double[] nElem = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			nElem[i] = 0;
		}
		return new Vector(nElem);
	}

	/**
	 * Static method used to parse string into a new {@link Vector}.
	 * 
	 * @param s
	 *            String that needs to be parsed.
	 * @return New {@link Vector}.
	 * @throws IllegalArgumentException
	 *             if given string isn't correctly formatted or is
	 *             <code>null</code>.
	 */
	public static Vector parseSimple(String s) throws IllegalArgumentException {
		if (s == null) {
			throw new IllegalArgumentException("String is null!");
		}
		String[] splitted = s.split(" ");
		double[] elems = new double[splitted.length];
		for (int i = splitted.length - 1; i >= 0; i--) {
			try {
				elems[i] = Double.parseDouble(splitted[i]);
			} catch (NumberFormatException nex) {
				throw new IllegalArgumentException(
						"String isn't correctly formatted!");
			}
		}
		return new Vector(false, false, elems);
	}
}
