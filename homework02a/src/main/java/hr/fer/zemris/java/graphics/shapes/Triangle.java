package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class used to represent Triangle in our {@link GeometricShape} inheritance
 * tree.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class Triangle extends GeometricShape {

	private int x1, y1, x2, y2, x3, y3;

	/**
	 * Constructor for {@link Triangle} object.
	 *
	 * @param x1
	 *            X-coordinate of the first vertex.
	 * @param y1
	 *            Y-coordinate of the first vertex.
	 * @param x2
	 *            X-coordinate of the second vertex.
	 * @param y2
	 *            Y-coordinate of the second vertex.
	 * @param x3
	 *            X-coordinate of the third vertex.
	 * @param y3
	 *            Y-coordinate of the third vertex.
	 */
	public Triangle(final int x1, final int y1, final int x2, final int y2,
			final int x3, final int y3) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;

	}

	@Override
	public boolean containsPoint(final int x, final int y) {
		if (!checkOrder(x1, y1, x2, y2, x3, y3)) {
			int temp = this.x3;
			this.x3 = this.x2;
			this.x2 = temp;
			temp = this.y3;
			this.y3 = this.y2;
			this.y2 = temp;
		}

		return checkOrder(x1, y1, x2, y2, x, y)
				&& checkOrder(x2, y2, x3, y3, x, y)
				&& checkOrder(x3, y3, x1, y1, x, y);
	}

	/**
	 * Private method used to determine in which order are vertexes 1, 2 and 3.
	 *
	 * @return <code>true</code> if order is CCW; <code>false</code> otherwise.
	 */
	private boolean checkOrder(final int x1, final int y1, final int x2,
			final int y2, final int x3, final int y3) {
		return (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1) <= 0;
	}

	@Override
	public void draw(final BWRaster r) {

		final int areaHeightTop = Math.max(0, Math.min(y1, Math.min(y2, y3)));
		final int areaHeightBot = Math.min(r.getHeight(),
				Math.max(y1, Math.max(y2, y3)));

		final int areaWidthL = Math.max(0, Math.min(x1, Math.min(x2, x3)));
		final int areaWidthR = Math.min(r.getWidth(),
				Math.max(x1, Math.max(x2, x3)));

		for (int i = areaHeightTop; i < areaHeightBot; i++) {
			for (int j = areaWidthL; j < areaWidthR; j++) {
				if (this.containsPoint(j, i)) {
					r.turnOn(j, i);
				}
			}
		}
	}

	/**
	 * Getter method for <code>x1</code> property.
	 *
	 * @return value of <code>x1</code> property.
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Setter method for <code>x1</code> property.
	 *
	 * @param x1
	 *            new value of <code>x1</code> property.
	 */
	public void setX1(final int x1) {
		this.x1 = x1;
	}

	/**
	 * Getter method for <code>y1</code> property.
	 *
	 * @return value of <code>y1</code> property.
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Setter method for <code>y1</code> property.
	 *
	 * @param y1
	 *            new value of <code>y1</code> property.
	 */
	public void setY1(final int y1) {
		this.y1 = y1;
	}

	/**
	 * Getter method for <code>x2</code> property.
	 *
	 * @return value of <code>x2</code> property.
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Setter method for <code>x2</code> property.
	 *
	 * @param x2
	 *            new value of <code>x2</code> property.
	 */
	public void setX2(final int x2) {
		this.x2 = x2;
	}

	/**
	 * Getter method for <code>y2</code> property.
	 *
	 * @return value of <code>y2</code> property.
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Setter method for <code>y2</code> property.
	 *
	 * @param y2
	 *            new value of <code>y2</code> property.
	 */
	public void setY2(final int y2) {
		this.y2 = y2;
	}

	/**
	 * Getter method for <code>x3</code> property.
	 *
	 * @return value of <code>x3</code> property.
	 */
	public int getX3() {
		return x3;
	}

	/**
	 * Setter method for <code>x3</code> property.
	 *
	 * @param x3
	 *            new value of <code>x3</code> property.
	 */
	public void setX3(final int x3) {
		this.x3 = x3;
	}

	/**
	 * Getter method for <code>y3</code> property.
	 *
	 * @return value of <code>y3</code> property.
	 */
	public int getY3() {
		return y3;
	}

	/**
	 * Setter method for <code>y3</code> property.
	 *
	 * @param y3
	 *            new value of <code>y3</code> property.
	 */
	public void setY3(final int y3) {
		this.y3 = y3;
	}

}
