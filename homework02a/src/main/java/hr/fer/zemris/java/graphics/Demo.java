package hr.fer.zemris.java.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.shapes.Triangle;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Program used to demonstrate drawing of various {@link GeometricShape}s.
 * Program expects user to provide either a single argument or 2 arguments.
 * Those arguments are interpreted as size of a raster. User can tell the
 * program what he wants to create by typing, 1 shape per line. First line must
 * contain a number of commands that will follow.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class Demo {

	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;

	public static void main(final String[] args) {
		try {
			doWork(args);
		} catch (RuntimeException ex) {
			System.out.println(ex.getLocalizedMessage());
			System.exit(-1);
		} catch (IOException io) {
			System.out.println("There was an IO error!");
			System.exit(-1);
		}

	}

	private static void doWork(final String[] args) throws IOException {
		BWRaster raster;
		boolean mode = false;

		if (args.length == ONE) {
			final int size = Integer.parseInt(args[0]);
			if (size < ONE) {
				throw new IllegalArgumentException("Size must be >= 1!");
			}
			raster = new BWRasterMem(size, size);
		} else if (args.length == TWO) {
			int width;
			int height;
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException(
						"Height and width must be integer values!");
			}
			if (width < ONE || height < ONE) {
				throw new IllegalArgumentException(
						"Height and width must be >= 1!");
			}
			raster = new BWRasterMem(width, height);
		} else {
			throw new IllegalArgumentException("Invalid number of arguments!");
		}

		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, Charset.forName("UTF-8")));

		int numberOfInputs;
		try {
			numberOfInputs = Integer.parseInt(reader.readLine());
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("Invalid number entered!");
		}
		if (numberOfInputs < ONE) {
			throw new IllegalArgumentException(
					"Invalid number of commands entered!");
		}
		GeometricShape[] array = new GeometricShape[numberOfInputs];

		for (int i = 0; i < numberOfInputs; i++) {
			final String input = reader.readLine();
			if (input == null) {
				throw new IllegalArgumentException("Input is null!");
			}
			if (input.matches("FLIP")) {
				array[i] = null;
			} else if (input.matches("RECTANGLE.*")) {
				String[] ulaz = input.split(" ");
				array[i] = new Rectangle(Integer.parseInt(ulaz[1]),
						Integer.parseInt(ulaz[TWO]),
						Integer.parseInt(ulaz[THREE]),
						Integer.parseInt(ulaz[FOUR]));
			} else if (input.matches("SQUARE.*")) {
				final String[] ulaz = input.split(" ");
				array[i] = new Square(Integer.parseInt(ulaz[1]),
						Integer.parseInt(ulaz[TWO]),
						Integer.parseInt(ulaz[THREE]));
			} else if (input.matches("ELLIPSE.*")) {
				final String[] ulaz = input.split(" ");
				array[i] = new Ellipse(Integer.parseInt(ulaz[1]),
						Integer.parseInt(ulaz[TWO]),
						Integer.parseInt(ulaz[THREE]),
						Integer.parseInt(ulaz[FOUR]));

			} else if (input.matches("TRIANGLE.*")) {
				final String[] ulaz = input.split(" ");
				array[i] = new Triangle(Integer.parseInt(ulaz[1]),
						Integer.parseInt(ulaz[TWO]),
						Integer.parseInt(ulaz[THREE]),
						Integer.parseInt(ulaz[FOUR]),
						Integer.parseInt(ulaz[FIVE]),
						Integer.parseInt(ulaz[SIX]));
			} else if (input.matches("CIRCLE.*")) {
				final String[] ulaz = input.split(" ");
				array[i] = new Circle(Integer.parseInt(ulaz[1]),
						Integer.parseInt(ulaz[TWO]),
						Integer.parseInt(ulaz[THREE]));
			} else {
				throw new IllegalArgumentException("Illegal command!");
			}
		}

		for (int i = 0; i < numberOfInputs; i++) {
			if (array[i] == null) {
				if (mode) {
					raster.disableFlipMode();
					mode = false;
				} else {
					raster.enableFlipMode();
					mode = true;
				}
			} else {
				array[i].draw(raster);
			}
		}

		final RasterView view = new SimpleRasterView();
		view.produce(raster);
	}

	private Demo() {
	}
}
