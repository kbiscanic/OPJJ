package hr.fer.zemris.java.hw06.part1;

import hr.fer.zemris.java.hw06.part1.ComplexImplementations.Complex;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.ComplexPolynomial;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.ComplexRootedPolynomial;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalProducer;
import hr.fer.zemris.java.tecaj_06.fractals.IFractalResultObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Class used to generate Newton-Raphson fractal for a polynomial determined by
 * at least 2 of its roots. This class uses {@link ExecutorService} for speeding
 * up the process of computing fractal.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class NewtonGenerator implements IFractalProducer {

	/**
	 * Threshold representing if 2 numbers are close enough.
	 */
	private static final double CONTHRESHOLD = 1E-3;
	/**
	 * Threshold representing if current number is close enough to the root of
	 * polynomial.
	 */
	private static final double ROOTTHRESHOLD = 1E-3;
	/**
	 * Maximum number of permitted iterations for every number.
	 */
	private static final short MAXITER = 16 * 16;

	private ComplexRootedPolynomial rooted;
	private ComplexPolynomial polynomial;
	private ComplexPolynomial derived;
	private ExecutorService service;
	private int jobs;

	/**
	 * Public constructor for this generator from <code>roots</code>.
	 * 
	 * @param roots
	 *            {@link Complex} roots for polynomial.
	 */
	public NewtonGenerator(Complex... roots) {
		roots[0] = roots[0].sub(new Complex(10E-12, 0));
		this.rooted = new ComplexRootedPolynomial(roots);
		this.polynomial = this.rooted.toComplexPolynom();
		this.derived = this.polynomial.derive();
		this.service = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors(), new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setDaemon(true);
				return thread;
			}
		});
		this.jobs = 8 * Runtime.getRuntime().availableProcessors();

	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax,
			int width, int height, long requestNo,
			IFractalResultObserver observer) {

		short[] data = new short[width * height];

		List<ThreadJob> jobs = new ArrayList<>();

		int yRange = height / this.jobs + 1;
		int y0 = 0;
		while (y0 < height) {
			int y1 = Math.min(y0 + yRange, height) - 1;
			jobs.add(new ThreadJob(reMin, reMax, imMin, imMax, width, height,
					y0, y1, MAXITER, CONTHRESHOLD, ROOTTHRESHOLD, rooted,
					polynomial, derived, data));
			y0 = y1 + 1;
		}

		List<Future<Void>> results = null;

		try {
			results = service.invokeAll(jobs);
		} catch (InterruptedException e) {
			System.err.println("Some jobs were cancelled!");
		}

		for (Future<Void> result : results) {
			try {
				result.get();
			} catch (InterruptedException | ExecutionException e) {
				System.err.println("Exception occured!");
			}
		}

		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);

	}

	/**
	 * Class representing a job for every thread used by {@link ExecutorService}
	 * .
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	private static class ThreadJob implements Callable<Void> {
		private double reMin;
		private double reMax;
		private double imMin;
		private double imMax;
		private int width;
		private int height;
		private int yMin;
		private int yMax;
		private short maxIter;
		private double convergenceTreshold;
		private double rootTreshold;
		private ComplexRootedPolynomial rooted;
		private ComplexPolynomial polynomial;
		private ComplexPolynomial derived;
		private short[] data;

		/**
		 * Default and only constructor for {@link ThreadJob} class. This
		 * constructor accepts all parameters needed to compute Newton-Raphson
		 * fractal for given range of y-s.
		 */
		public ThreadJob(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax,
				short maxIter, double convergenceTreshold, double rootTreshold,
				ComplexRootedPolynomial rooted, ComplexPolynomial polynomial,
				ComplexPolynomial derived, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIter = maxIter;
			this.convergenceTreshold = convergenceTreshold;
			this.rootTreshold = rootTreshold;
			this.rooted = rooted;
			this.polynomial = polynomial;
			this.derived = derived;
			this.data = data;
		}

		@Override
		public Void call() throws Exception {
			NewtonRaphsonFractal.generate(reMin, reMax, imMin, imMax, width,
					height, yMin, yMax, maxIter, convergenceTreshold,
					rootTreshold, rooted, polynomial, derived, data);
			return null;
		}
	}
}
