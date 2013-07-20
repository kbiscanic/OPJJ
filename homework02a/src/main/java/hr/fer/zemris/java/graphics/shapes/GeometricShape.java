package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class used to be root of an inheritance tree for all geometric
 * shapes.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public abstract class GeometricShape {

	/**
	 * Method used to draw any given {@link GeometricShape} to the
	 * {@link BWRaster} <code>r</code>.
	 *
	 * @param r
	 *            {@link BWRaster} on which a {@link GeometricShape} needs to be
	 *            drawn.
	 */
	public void draw(final BWRaster r) {
		final int height = r.getHeight();
		final int width = r.getWidth();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (this.containsPoint(j, i)) {
					r.turnOn(j, i);
				}
			}
		}
	}

	/**
	 * Method <code>containsPoint</code> checks if given point belongs to
	 * specified {@link GeometricShape}.
	 *
	 * @param x
	 *            X-coordinate of the point being tested.
	 * @param y
	 *            Y-coordinate of the point being tested.
	 * @return <code>false</code> if the location is outside of the
	 *         {@link GeometricShape}; <code>true</code> otherwise.
	 */
	public abstract boolean containsPoint(int x, int y);

}
