package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class used to represent any Ellipse-Like object. It is extending
 * {@link GeometricShape} and providing more efficient methods for working with
 * Ellipse-Like objects.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public abstract class EllipseLike extends GeometricShape {

	/**
	 * X-coordinate of the center for the given object.
	 */
	private int cenX;
	/**
	 * Y-coordinate of the center for the given object.
	 */
	private int cenY;

	/**
	 * Constructor for {@link EllipseLike} objects.
	 *
	 * @param cenX
	 *            X-coordinate of the center for the given object.
	 * @param cenY
	 *            Y-coordinate of the center for the given object.
	 */
	public EllipseLike(final int cenX, final int cenY) {
		super();
		this.cenX = cenX;
		this.cenY = cenY;
	}

	@Override
	public boolean containsPoint(final int x, final int y) {
		final int rx = this.getrX();
		final int ry = this.getrY();
		return (x - cenX) * (x - cenX) * ry * ry + (y - cenY) * (y - cenY) * rx
				* rx <= rx * rx * ry * ry;
	}

	@Override
	public void draw(final BWRaster r) {
		final int rx = this.getrX();
		final int ry = this.getrY();
		final int abs = Math.abs(rx - ry);

		final int areaHeightTop = Math.max(0, cenY - abs - ry);
		final int areaHeightBot = Math.min(r.getHeight(), cenY + abs + ry + 1);

		final int areaWidthL = Math.max(0, cenX - abs - rx);
		final int areaWidthR = Math.min(r.getWidth(), cenX + abs + rx + 1);

		for (int i = areaHeightTop; i < areaHeightBot; i++) {
			for (int j = areaWidthL; j < areaWidthR; j++) {
				if (this.containsPoint(i, j)) {
					r.turnOn(i, j);
				}
			}
		}
	}

	/**
	 * Getter method for radius on the X-axis.
	 *
	 * @return value of the current X-axis radius.
	 */
	public abstract int getrX();

	/**
	 * Getter method for radius on the Y-axis.
	 *
	 * @return value of the current Y-axis radius.
	 */
	public abstract int getrY();

	/**
	 * Getter method for X-coordinate of the center of object.
	 *
	 * @return value of the current X-coordinate of the center.
	 */
	public int getCenX() {
		return cenX;
	}

	/**
	 * Setter method for X-coordinate of the center of object.
	 *
	 * @param cenX
	 *            value to which <code>cenX</code> is set.
	 */
	public void setCenX(final int cenX) {
		this.cenX = cenX;
	}

	/**
	 * Getter method for Y-coordinate of the center of object.
	 *
	 * @return value of the current Y-coordinate of the center.
	 */
	public int getCenY() {
		return cenY;
	}

	/**
	 * Setter method for Y-coordinate of the center of object.
	 *
	 * @param cenY
	 *            value to which <code>cenY</code> is set.
	 */
	public void setCenY(int cenY) {
		this.cenY = cenY;
	}

}
