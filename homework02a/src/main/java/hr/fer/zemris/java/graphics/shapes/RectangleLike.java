package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class used to represent any Rectangle-Like object. It is extending
 * {@link GeometricShape} and providing more efficient methods for working with
 * Rectangle-Like objects.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public abstract class RectangleLike extends GeometricShape {

	private int startX;
	private int startY;

	/**
	 * Constructor for {@link RectangleLike} objects.
	 *
	 * @param startX
	 *            X-coordinate of the top-left point for the object.
	 * @param startY
	 *            Y-coordinate of the top-left point for the object.
	 */
	public RectangleLike(final int startX, final int startY) {
		super();
		this.startX = startX;
		this.startY = startY;
	}

	/**
	 * Getter method for <code>Height</code> property.
	 *
	 * @return value of <code>Height</code>.
	 */
	public abstract int getHeight();

	/**
	 * Getter method for <code>Width</code> property.
	 *
	 * @return value of <code>Width</code>.
	 */
	public abstract int getWidth();

	@Override
	public boolean containsPoint(final int x, final int y) {
		return !(x < startX || x > startX + this.getWidth() || y < startY || y > startY
				+ this.getHeight());
	}

	@Override
	public void draw(final BWRaster r) {
		final int areaHeight = Math.min(r.getHeight(),
				startY + this.getHeight());
		final int areaWidth = Math.min(r.getWidth(), startX + this.getWidth());
		final int sX = Math.max(0, startY);
		final int sY = Math.max(0, startX);

		for (int i = sY; i < areaHeight; i++) {
			for (int j = sX; j < areaWidth; j++) {
				r.turnOn(j, i);
			}
		}
	}

	/**
	 * Getter method for <code>startX</code> property.
	 *
	 * @return value of <code>startX</code>.
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Setter method for <code>startX</code> property.
	 *
	 * @param startX
	 *            new value of <code>startX</code>.
	 */
	public void setStartX(final int startX) {
		this.startX = startX;
	}

	/**
	 * Getter method for <code>startY</code> property.
	 *
	 * @return value of <code>startY</code>
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * Setter method for <code>startY</code> property.
	 *
	 * @param startY
	 *            new value of <code>startY</code>.
	 */
	public void setStartY(final int startY) {
		this.startY = startY;
	}

}
