package hr.fer.zemris.java.tecaj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import hr.fer.zemris.java.tecaj.fraktali.Mandelbrot;
import hr.fer.zemris.java.tecaj_06.fractals.FractalViewer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

public class GeneriranjePrekoExecutorsa {

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

			ExecutorService service = Executors.newFixedThreadPool(Runtime
					.getRuntime().availableProcessors());

			List<PosaoDretve> poslovi = new ArrayList<>();

			int visinaTrake = 16;
			int y0 = 0;
			while (y0 < height) {
				int y1 = Math.min(y0 + visinaTrake, height) - 1;
				PosaoDretve posao = new PosaoDretve(reMin, reMax, imMin, imMax,
						width, height, y0, y1, limit, data);
				poslovi.add(posao);
				y0 = y1 + 1;
			}

			List<Future<Void>> rezultati;
			try {
				rezultati = service.invokeAll(poslovi);
			} catch (InterruptedException e) {
				return;
			}

			for (Future<Void> rezultat : rezultati) {
				try {
					rezultat.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			observer.acceptResult(data, limit, requestNo);

			service.shutdown();

		}

	}

	static class PosaoDretve implements Callable<Void> {

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
		public Void call() {
			Mandelbrot.generiraj(reMin, reMax, imMin, imMax, width, height,
					yMin, yMax, limit, data);
			return null;
		}

	}

}
