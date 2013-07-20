package hr.fer.zemris.java.graphics.raster;

/**
 * The class {@link BWRasterMem} is implementation of the {@link BWRaster}
 * interface. This class keeps all of its data in computer memory, in one 2D
 * array. Default condition of all pixels is <code>On</code>.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class BWRasterMem implements BWRaster {

	/**
	 * Current state of <code>FlipMode</code>.
	 */
	private boolean flipMode;
	/**
	 * 2D array representing the whole raster.
	 */
	private boolean[][] image;
	/**
	 * Width of the created raster.
	 */
	private final int imageWidth;
	/**
	 * Height of the created raster.
	 */
	private final int imageHeight;

	/**
	 * Constructor for {@link BWRasterMem} class. This constructor creates new
	 * raster with given <code>imageWidth</code> and <code>imageHeight</code> as
	 * dimension. By default, <code>FlipMode</code> is <code>false</code> and
	 * all the pixels are in the <code>Off</code> state.
	 * 
	 * @param imageWidth
	 *            Width of the raster trying to be created.
	 * @param imageHeight
	 *            Height of the raster trying to be created.
	 * @throws IllegalArgumentException
	 *             If <code>imageWidth</code> or <code>imageHeight</code> are
	 *             smaller than 1.
	 */
	public BWRasterMem(final int imageWidth, final int imageHeight) {
		if (imageHeight < 1 || imageWidth < 1) {
			throw new IllegalArgumentException(
					"Height and Width of a raster must be >= 1!");
		}
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.image = new boolean[imageHeight][imageWidth];
		this.flipMode = false;
		clear();
	}

	@Override
	public int getWidth() {
		return imageWidth;
	}

	@Override
	public int getHeight() {
		return imageHeight;
	}

	@Override
	public void clear() {
		for (int i = 0; i < imageHeight; i++) {
			for (int j = 0; j < imageWidth; j++) {
				image[i][j] = false;
			}
		}

	}

	@Override
	public void turnOn(final int x, final int y) {
		if (x < 0 || x >= imageWidth || y < 0 || y >= imageHeight) {
			throw new IllegalArgumentException("Requested position is invalid!");
		}
		if (flipMode) {
			image[y][x] ^= true;
		} else {
			image[y][x] = true;
		}
	}

	@Override
	public void turnOff(final int x, final int y) {
		if (x < 0 || x >= imageWidth || y < 0 || y >= imageHeight) {
			throw new IllegalArgumentException("Requested position is invalid!");
		}
		image[y][x] = false;

	}

	@Override
	public void enableFlipMode() {
		flipMode = true;

	}

	@Override
	public void disableFlipMode() {
		flipMode = false;

	}

	@Override
	public boolean isTurnedOn(final int x, final int y) {
		if (x < 0 || x >= imageWidth || y < 0 || y >= imageHeight) {
			throw new IllegalArgumentException("Requested position is invalid!");
		}
		return image[y][x];
	}

}
