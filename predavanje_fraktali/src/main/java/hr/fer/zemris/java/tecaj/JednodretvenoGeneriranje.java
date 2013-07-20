package hr.fer.zemris.java.tecaj;

import hr.fer.zemris.java.tecaj.fraktali.Mandelbrot;
import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

public class JednodretvenoGeneriranje {

	public static void main(String[] args) {
		FractalViewer.show(new Generiranje());

	}

	static class Generiranje implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer) {

			short limit = 16*16*16;
			short[] data = new short[width * height];

			Mandelbrot.generiraj(reMin, reMax, imMin, imMax, width, height, 0,
					height - 1, limit, data);

			observer.acceptResult(data, limit, requestNo);

		}

	}

}
