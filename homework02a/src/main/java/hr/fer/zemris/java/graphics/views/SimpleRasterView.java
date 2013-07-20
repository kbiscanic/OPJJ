package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Simple implementation of {@link RasterView} interface.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class SimpleRasterView implements RasterView {

	/**
	 * Character representing a point in the <code>On</code> state.
	 */
	private char pointOn;
	/**
	 * Character representing a point in the <code>Off</code> state.
	 */
	private char pointOff;

	/**
	 * Constructor for {@link SimpleRasterView} object. Allows user to specify
	 * <code>pointOn</code> and <code>pointOff</code> characters.
	 *
	 * @param pointOn
	 *            Character representing a point in the <code>On</code> state.
	 * @param pointOff
	 *            Character representing a point in the <code>Off</code> state.
	 */
	public SimpleRasterView(final char pointOn, final char pointOff) {
		super();
		this.pointOn = pointOn;
		this.pointOff = pointOff;
	}

	/**
	 * Default constructor for {@link SimpleRasterView} object. Delegates work
	 * to first constructor with <code>*</code> and <code>.</code> arguments.
	 */
	public SimpleRasterView() {
		this('*', '.');
	}

	@Override
	public void produce(final BWRaster raster) {
		final int rwidth = raster.getWidth();
		final int rheight = raster.getHeight();

		for (int i = 0; i < rheight; i++) {
			for (int j = 0; j < rwidth; j++) {
				if (raster.isTurnedOn(j, i)) {
					System.out.print(pointOn);
				} else {
					System.out.print(pointOff);

				}
			}
			System.out.println();
		}

	}
}
