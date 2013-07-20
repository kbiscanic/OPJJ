package hr.fer.zemris.java.graphics.shapes;

/**
 * Class used to represent Square in our {@link GeometricShape} inheritance
 * tree.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class Square extends RectangleLike {

	private int size;
	private static final int ONE = 1;

	/**
	 * Constructor for new {@link Square} object.
	 *
	 * @param startX
	 *            X-coordinate of the top-left point for the object.
	 * @param startY
	 *            Y-coordinate of the top-left point for the object.
	 * @param size
	 *            Length of one side of the object.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>size</code> is <code>< 1</code>.
	 */
	public Square(final int startX, final int startY, final int size) {
		super(startX, startY);
		if (size < ONE) {
			throw new IllegalArgumentException("Size must be >= 1!");
		}
		this.size = size - 1;
	}

	@Override
	public int getHeight() {
		return size + 1;
	}

	@Override
	public int getWidth() {
		return size + 1;
	}

	/**
	 * Setter method for <code>size</code> property.
	 *
	 * @param size
	 *            new value of <code>size</code>.
	 * @throws IllegalArgumentException
	 *             if <code>size</code> is <code>< 1</code>.
	 */
	public void setSize(final int size) {
		if (size < ONE) {
			throw new IllegalArgumentException();
		}
		this.size = size - 1;
	}

	/**
	 * Getter method for <code>size</code> property.
	 *
	 * @return value of <code>size</code> property.
	 */
	public int getSize() {
		return size + 1;
	}

}
