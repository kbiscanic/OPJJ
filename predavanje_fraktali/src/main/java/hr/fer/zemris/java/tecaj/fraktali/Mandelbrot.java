package hr.fer.zemris.java.tecaj.fraktali;

public class Mandelbrot {

	public static void generiraj(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int yMin, int yMax,
			short limit, short[] data) {

		int offset = yMin * width;

		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				double z0re = 0;
				double z0im = 0;
				double cRe = x * (reMax - reMin) / (width - 1) + reMin;
				double cIm = (height - 1 - y) * (imMax - imMin) / (height - 1)
						+ imMin;
				double module = 0;
				short iteracija = 0;
				do {
					double z1re = z0re * z0re - z0im * z0im + cRe;
					double z1im = 2 * z0re * z0im + cIm;
					module = z1re * z1re + z1im * z1im;
					iteracija++;
					z0re = z1re;
					z0im = z1im;
				} while (iteracija < limit && module < 4);
				data[offset] = iteracija;
				offset++;
			}
		}

	}

}
