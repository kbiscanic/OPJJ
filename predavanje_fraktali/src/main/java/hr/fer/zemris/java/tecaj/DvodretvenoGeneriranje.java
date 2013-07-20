package hr.fer.zemris.java.tecaj;

import hr.fer.zemris.java.tecaj.fraktali.Mandelbrot;
import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

public class DvodretvenoGeneriranje {

	public static void main(String[] args) {
		FractalViewer.show(new Generiranje());

	}

	static class Generiranje implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer) {

			short limit = 16 * 16 * 16;
			short[] data = new short[width * height];

			PosaoDretve prvi = new PosaoDretve(reMin, reMax, imMin, imMax,
					width, height, 0, height / 2 - 1, limit, data);
			PosaoDretve drugi = new PosaoDretve(reMin, reMax, imMin, imMax,
					width, height, height / 2, height - 1, limit, data);

			Thread[] dretve = new Thread[2];
			dretve[0] = new Thread(prvi);
			dretve[1] = new Thread(drugi);

			dretve[0].start();
			dretve[1].start();

			for (int i = 0; i < dretve.length; i++) {
				try {
					dretve[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			observer.acceptResult(data, limit, requestNo);

		}

	}

	static class PosaoDretve implements Runnable {

		private double reMin, reMax, imMin, imMax;
		private int width, height, yMin, yMax;
		private short limit;
		private short[] data;

		public PosaoDretve(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax,
				short limit, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.limit = limit;
			this.data = data;
		}

		@Override
		public void run() {
			Mandelbrot.generiraj(reMin, reMax, imMin, imMax, width, height,
					yMin, yMax, limit, data);

		}

	}

}
