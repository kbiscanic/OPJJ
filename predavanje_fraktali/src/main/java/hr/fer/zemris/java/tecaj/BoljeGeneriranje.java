package hr.fer.zemris.java.tecaj;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.java.tecaj.fraktali.Mandelbrot;
import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

public class BoljeGeneriranje {

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

			BlockingQueue<PosaoDretve> red = new LinkedBlockingQueue<>();

			int brojRadnika = Runtime.getRuntime().availableProcessors();
			Thread[] radnici = new Thread[brojRadnika];

			for (int i = 0; i < brojRadnika; i++) {
				radnici[i] = new Thread(new Radnik(red));
				radnici[i].start();
			}

			int visinaTrake = 16;
			int y0 = 0;
			while (y0 < height) {
				int y1 = Math.min(y0 + visinaTrake, height) - 1;
				PosaoDretve posao = new PosaoDretve(reMin, reMax, imMin, imMax,
						width, height, y0, y1, limit, data);
				while (true) {
					try {
						red.put(posao);
						break;
					} catch (InterruptedException e) {
						continue;
					}
				}
				y0 = y1 + 1;
			}

			for (int i = 0; i < radnici.length; i++) {
				try {
					red.put(PosaoDretve.OTROV);
				} catch (InterruptedException e) {
				}
			}

			for (int i = 0; i < radnici.length; i++) {
				try {
					radnici[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			observer.acceptResult(data, limit, requestNo);

		}

	}

	static class Radnik implements Runnable {
		BlockingQueue<PosaoDretve> red;

		public Radnik(BlockingQueue<PosaoDretve> red) {
			super();
			this.red = red;
		}

		@Override
		public void run() {
			while (true) {
				PosaoDretve posao = null;

				try {
					posao = red.take();
				} catch (InterruptedException e) {
					continue;
				}

				if (posao == PosaoDretve.OTROV) {
					break;
				}

				posao.run();
			}

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

		public static final PosaoDretve OTROV = new PosaoDretve();

		private PosaoDretve() {
		}

		@Override
		public void run() {
			Mandelbrot.generiraj(reMin, reMax, imMin, imMax, width, height,
					yMin, yMax, limit, data);

		}

	}

}
