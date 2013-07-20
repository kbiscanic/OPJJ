package hr.fer.zemris.java.tecaj;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.tecaj.fraktali.Mandelbrot;
import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

public class CopyOfGeneriranjePrekoForkJoin {

	public static void main(String[] args) {
		FractalViewer.show(new Generiranje());

	}

	static class Generiranje implements IFractalProducer {

		private ForkJoinPool pool;

		public Generiranje() {
			pool = new ForkJoinPool();
		}

		@Override
		public void produce(double reMin, double reMax, double imMin,
				double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer) {

			short limit = 16 * 16 * 16;
			short[] data = new short[width * height];

			pool.invoke(new PosaoDretve(reMin, reMax, imMin, imMax, width,
					height, 0, height - 1, limit, data));

			observer.acceptResult(data, limit, requestNo);

		}

	}

	static class PosaoDretve extends RecursiveAction {

		private static final long serialVersionUID = 6979469456873329183L;
		private double reMin, reMax, imMin, imMax;
		private int width, height, yMin, yMax;
		private short limit;
		private short[] data;
		private static final int PRAG = 16;

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
		protected void compute() {
			int brojRedaka = yMax - yMin + 1;
			if (brojRedaka <= PRAG) {
				Mandelbrot.generiraj(reMin, reMax, imMin, imMax, width, height,
						yMin, yMax, limit, data);
			} else {
				invokeAll(new PosaoDretve(reMin, reMax, imMin, imMax, width,
						height, yMin, (yMin + yMax) / 2 - 1, limit, data),
						new PosaoDretve(reMin, reMax, imMin, imMax, width,
								height, (yMin + yMax) / 2, yMax, limit, data));
			}
		}
	}

}
