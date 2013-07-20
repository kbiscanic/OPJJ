package hr.fer.zemris.java.graphics.shapes;

/**
 * Class used to represent Ellipse in our {@link GeometricShape} inheritance
 * tree.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class Ellipse extends EllipseLike {

	private int rX;
	private int rY;
	private static final int ONE = 1;

	/**
	 * Constructor for the {@link Ellipse} object.
	 *
	 * @param cenX
	 *            X-coordinate of the center.
	 * @param cenY
	 *            Y-coordinate of the center.
	 * @param rX
	 *            Radius on the X-axis.
	 * @param rY
	 *            Radius on the Y-axis.
	 * @throws IllegalArgumentException
	 *             if <code>rX</code> or <code>rY</code> are <code>< 1</code>.
	 */
	public Ellipse(final int cenX, final int cenY, final int rX, final int rY) {
		super(cenX, cenY);
		if (rX < ONE || rY < ONE) {
			throw new IllegalArgumentException("Both radiuses must be >= 1!");
		}
		this.rX = rX - 1;
		this.rY = rY - 1;
	}

	@Override
	public int getrX() {
		return rX + 1;
	}

	@Override
	public int getrY() {
		return rY + 1;
	}

	/**
	 * Setter method for X-axis radius.
	 *
	 * @param rX
	 *            X-axis radius.
	 * @throws IllegalArgumentException
	 *             if <code>rX</code> is <code>< 1</code>;
	 */
	public void setrX(final int rX) {
		if (rX < ONE) {
			throw new IllegalArgumentException("Radius must be >= 1!");
		}
		this.rX = rX - 1;
	}

	/**
	 * Setter method for Y-axis radius.
	 *
	 * @param rY
	 *            Y-axis radius.
	 * @throws IllegalArgumentException
	 *             if <code>rY</code> is <code>< 1</code>;
	 */
	public void setrY(final int rY) {
		if (rY < ONE) {
			throw new IllegalArgumentException("Radius must be >= 1!");
		}
		this.rY = rY - 1;
	}

}
