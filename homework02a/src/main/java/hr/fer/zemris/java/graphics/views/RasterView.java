package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Classes which implement this interface will be responsible for visualization
 * of created images.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public interface RasterView {

	/**
	 * Method used to output the textual representation of image to standard
	 * output.
	 *
	 * @param raster
	 *            {@link BWRaster} containing the image that needs to be
	 *            produced as text.
	 */
	void produce(BWRaster raster);

}
