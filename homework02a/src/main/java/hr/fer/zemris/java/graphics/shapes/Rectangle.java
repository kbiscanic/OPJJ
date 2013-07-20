package hr.fer.zemris.java.graphics.shapes;

/**
 * Class used to represent Rectangle in our {@link GeometricShape} inheritance
 * tree.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class Rectangle extends RectangleLike {

	private int width;
	private int height;
	private static final int ONE = 1;

	/**
	 * Constructor for new {@link Rectangle} object.
	 *
	 * @param startX
	 *            X-coordinate of the top-left point for the object.
	 * @param startY
	 *            Y-coordinate of the top-left point for the object.
	 * @param width
	 *            Width of the object.
	 * @param height
	 *            Height of the object.
	 *
	 * @throws IllegalArgumentException
	 *             if <code>width</code> or <code>height</code> are
	 *             <code>< 1</code>.
	 */
	public Rectangle(final int startX, final int startY, final int width,
			final int height) {
		super(startX, startY);
		if (width < ONE || height < ONE) {
			throw new IllegalArgumentException(
					"Both width and hegiht must be >=1!");
		}
		this.width = width - 1;
		this.height = height - 1;
	}

	@Override
	public int getHeight() {
		return height + 1;
	}

	@Override
	public int getWidth() {
		return width + 1;
	}

	/**
	 * Setter method for <code>width</code> property.
	 *
	 * @param width
	 *            new value of <code>width</code>.
	 * @throws IllegalArgumentException
	 *             if <code>width</code> is <code>< 1</code>.
	 */
	public void setWidth(final int width) {
		if (width < ONE) {
			throw new IllegalArgumentException();
		}
		this.width = width - 1;
	}

	/**
	 * Setter method for <code>height</code> property.
	 *
	 * @param height
	 *            new value of <code>height</code>.
	 * @throws IllegalArgumentException
	 *             if <code>height</code> is <code>< 1</code>.
	 */
	public void setHeight(final int height) {
		if (height < ONE) {
			throw new IllegalArgumentException();
		}
		this.height = height - 1;
	}

}
