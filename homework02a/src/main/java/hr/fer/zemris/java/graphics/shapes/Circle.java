package hr.fer.zemris.java.graphics.shapes;

/**
 * Class used to represent Circle in our {@link GeometricShape} inheritance
 * tree.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class Circle extends EllipseLike {

	private int rad;
	private static final int ONE = 1;

	/**
	 * Constructor for the {@link Circle} object.
	 *
	 * @param cenX
	 *            X-coordinate of the center.
	 * @param cenY
	 *            Y-coordinate of the center.
	 * @param rad
	 *            Radius of the circle.
	 * @throws IllegalArgumentException
	 *             if <code>rad</code> is <code> < 1 </code>.
	 */
	public Circle(final int cenX, final int cenY, final int rad) {
		super(cenX, cenY);
		if (rad < ONE) {
			throw new IllegalArgumentException("Radius must be >=1!");
		}
		this.rad = rad - 1;
	}

	@Override
	public int getrX() {
		return rad + 1;
	}

	@Override
	public int getrY() {
		return rad + 1;
	}

	/**
	 * Getter method for the current radius.
	 *
	 * @return value of current radius.
	 */
	public int getRad() {
		return rad + 1;
	}

	/**
	 * Setter method for the radius.
	 *
	 * @param rad
	 *            New radius.
	 * @throws IllegalArgumentException
	 *             if <code>rad</code> is <code>< 1</code>.
	 */
	public void setRad(final int rad) {
		if (rad < ONE) {
			throw new IllegalArgumentException("Radius must be >=1!");
		}
		this.rad = rad - 1;
	}

}
