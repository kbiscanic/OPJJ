package hr.fer.zemris.java.graphics.raster;

/**
 * Interface used to represent Black-and-White raster devices of fixed width and
 * height for which each pixel can be painted with only two colors: black (when
 * pixel is turned off) and white (when pixel is turned on).
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public interface BWRaster {

	/**
	 * Method returning Width of a raster.
	 *
	 * @return Width of a raster.
	 */
	int getWidth();

	/**
	 * Method returning Height of a raster.
	 *
	 * @return Height of a raster.
	 */
	int getHeight();

	/**
	 * Method used to turn off all pixels in raster.
	 */
	void clear();

	/**
	 * Method used to turn on the pixel at specified location. If
	 * <code>FlipMode</code> is <code>true</code> it changes current state of
	 * specified pixel. Otherwise, this method sets the selected pixel to On.
	 *
	 * @param x
	 *            X-coordinate of the pixel.
	 * @param y
	 *            Y-coordinate of the pixel.
	 */
	void turnOn(int x, int y);

	/**
	 * Method used to turn off the pixel at specified location.
	 *
	 * @param x
	 *            X-coordinate of the pixel.
	 * @param y
	 *            Y-coordinate of the pixel.
	 */
	void turnOff(int x, int y);

	/**
	 * Method used to enable <code>FlipMode</code>.
	 */
	void enableFlipMode();

	/**
	 * Method used to disable <code>FlipMode</code>.
	 */
	void disableFlipMode();

	/**
	 * Method used to determine if the pixel at the given location is turned on.
	 *
	 * @param x
	 *            X-coordinate of the pixel.
	 * @param y
	 *            Y-coordinate of the pixel.
	 * @return <code>true</code> if the pixel state is <code>On</code>;
	 *         <code>false</code> otherwise.
	 */
	boolean isTurnedOn(int x, int y);

}
