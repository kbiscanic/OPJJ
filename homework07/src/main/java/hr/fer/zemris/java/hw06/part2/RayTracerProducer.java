package hr.fer.zemris.java.hw06.part2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.tecaj_06.rays.GraphicalObject;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.IRayTracerResultObserver;
import hr.fer.zemris.java.tecaj_06.rays.LightSource;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.Ray;
import hr.fer.zemris.java.tecaj_06.rays.RayIntersection;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;
import hr.fer.zemris.java.tecaj_06.rays.Scene;

/**
 * Class implementing a {@link IRayTracerProducer} interface.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class RayTracerProducer implements IRayTracerProducer {

	/**
	 * Determines whether to use single or multiple thread solution.
	 */
	private boolean multiThreading;
	/**
	 * {@link ForkJoinPool} used if <code>multiThreading</code> is
	 * <code>true</code>. Unused otherwise.
	 */
	private ForkJoinPool pool;
	/**
	 * Threshold representing maximum number of lines a thread is permitted to
	 * compute.
	 */
	private static final int THRESHOLD = 16;

	/**
	 * Default constructor creating a single thread {@link RayTracerProducer}.
	 */
	public RayTracerProducer() {
		this(false);
	}

	/**
	 * Constructor creating a {@link RayTracerProducer}.
	 * 
	 * @param multiThread
	 *            Determines whether to use single or multiple thread solution.
	 */
	public RayTracerProducer(boolean multiThread) {
		this.multiThreading = multiThread;
		if (multiThreading) {
			this.pool = new ForkJoinPool();
		}
	}

	@Override
	public void produce(Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width, int height,
			long requestNo, IRayTracerResultObserver observer) {

		System.out.println("Starting calculations...");
		short[] red = new short[width * height];
		short[] green = new short[width * height];
		short[] blue = new short[width * height];

		Point3D zAxis = eye.negate().add(view).normalize();
		Point3D yAxis = viewUp
				.normalize()
				.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp
						.normalize()))).normalize();
		Point3D xAxis = zAxis.vectorProduct(yAxis).normalize().negate();

		Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
				.sub(yAxis.scalarMultiply(vertical / 2));

		Scene scene = RayTracerViewer.createPredefinedScene();

		if (multiThreading) {
			pool.invoke(new ThreadJob(red, green, blue, zAxis, yAxis, xAxis,
					screenCorner, scene, eye, view, viewUp, horizontal,
					vertical, width, height, 0, height - 1));
		} else {
			actualJob(red, green, blue, zAxis, yAxis, xAxis, screenCorner,
					scene, eye, view, viewUp, horizontal, vertical, width,
					height, 0, height - 1);
		}
		System.out.println("Calculations done...");
		observer.acceptResult(red, green, blue, requestNo);
		System.out.println("Report done...");

	}

	/**
	 * Method used to do actual job of computing this problem.
	 */
	private static void actualJob(short[] red, short[] green, short[] blue,
			Point3D zAxis, Point3D yAxis, Point3D xAxis, Point3D screenCorner,
			Scene scene, Point3D eye, Point3D view, Point3D viewUp,
			double horizontal, double vertical, int width, int height,
			int yMin, int yMax) {
		short[] rgb = new short[3];
		int offset = yMin * width;
		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {

				double xOff = ((double) x / width - 1.0) * (double) horizontal;
				double yOff = ((double) y / height - 1.0) * (double) vertical;

				Point3D screenPoint = screenCorner.sub(
						xAxis.scalarMultiply(xOff)).sub(
						yAxis.scalarMultiply(yOff));
				Ray ray = Ray.fromPoints(eye, screenPoint);

				RayIntersection closestIntersection = findClosestIntersection(
						ray, scene);
				if (closestIntersection == null) {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
				} else {
					rgb = determineColor(closestIntersection, scene, eye);
				}

				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

				offset++;
			}
		}
	}

	/**
	 * Method used to determine which color should be applied to given pixel.
	 * 
	 * @param intersection
	 *            Intersection.
	 * @param scene
	 *            Current scene.
	 * @param eye
	 *            Point from which scene is being viewed.
	 * @return Array of colors for each pixel.
	 */
	private static short[] determineColor(RayIntersection intersection,
			Scene scene, Point3D eye) {
		short[] rgb = new short[] { 15, 15, 15 };

		for (LightSource light : scene.getLights()) {
			Ray rr = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			RayIntersection closest = findClosestIntersection(rr, scene);

			if (closest == null
					|| intersection.getPoint().sub(light.getPoint()).norm() > closest
							.getPoint().sub(light.getPoint()).norm() + 10E-3) {
				continue;
			}

			for (short i = 0; i < 3; i++) {
				rgb[i] += getDiff(i, light, intersection)
						+ getRef(i, light, intersection, eye);
			}
		}
		return rgb;
	}

	/**
	 * Method used to get reflective component of light on given intersection.
	 */
	private static short getRef(short clr, LightSource light,
			RayIntersection intersection, Point3D eye) {
		Point3D v1 = intersection.getPoint().sub(light.getPoint());
		Point3D v2 = intersection.getPoint().sub(eye).normalize();
		Point3D v3 = intersection.getNormal().scalarMultiply(2)
				.scalarMultiply(v1.scalarProduct(intersection.getNormal()))
				.sub(v1);
		double coeff;
		int color;
		if (clr == 0) {
			coeff = intersection.getKrr();
			color = light.getR();
		} else if (clr == 1) {
			coeff = intersection.getKrg();
			color = light.getG();
		} else {
			coeff = intersection.getKrb();
			color = light.getB();
		}
		return (short) (color * coeff * Math.pow(
				v3.normalize().scalarProduct(v2.normalize()),
				intersection.getKrn()));
	}

	/**
	 * Method used to get diffusive component of light on given intersection.
	 */
	private static short getDiff(short clr, LightSource light,
			RayIntersection intersection) {
		double coeff;
		int color;
		if (clr == 0) {
			coeff = intersection.getKdr();
			color = light.getR();
		} else if (clr == 1) {
			coeff = intersection.getKdg();
			color = light.getG();
		} else {
			coeff = intersection.getKdb();
			color = light.getB();
		}
		return (short) (coeff * color * intersection.getPoint()
				.sub(light.getPoint()).normalize()
				.scalarProduct(intersection.getNormal().normalize()));
	}

	/**
	 * Method used to find the closest intersection of given ray and scene.
	 * 
	 * @param ray
	 *            Ray we are observing.
	 * @param scene
	 *            Scene we are observing.
	 * @return Closest intersection. <code>null</code> if there is no such
	 *         intersection.
	 */
	private static RayIntersection findClosestIntersection(Ray ray, Scene scene) {
		RayIntersection closest = null;
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection intersect = object.findClosestRayIntersection(ray);
			if (intersect == null) {
				continue;
			}
			if (closest == null
					|| intersect.getDistance() < closest.getDistance()) {
				closest = intersect;
			}
		}
		return closest;
	}

	/**
	 * Recursive job for each thread if solution is using multiple threads.
	 * Unused otherwise.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	private static class ThreadJob extends RecursiveAction {

		private static final long serialVersionUID = 6797952806848330387L;
		private short[] red;
		private short[] green;
		private short[] blue;
		private Point3D zAxis;
		private Point3D yAxis;
		private Point3D xAxis;
		private Point3D screenCorner;
		private Scene scene;
		private Point3D eye;
		private Point3D view;
		private Point3D viewUp;
		private double horizontal;
		private double vertical;
		private int width;
		private int height;
		private int yMin;
		private int yMax;

		/**
		 * Default constructor for {@link ThreadJob}. Accepts all needed
		 * parameters for computing.
		 */
		public ThreadJob(short[] red, short[] green, short[] blue,
				Point3D zAxis, Point3D yAxis, Point3D xAxis,
				Point3D screenCorner, Scene scene, Point3D eye, Point3D view,
				Point3D viewUp, double horizontal, double vertical, int width,
				int height, int yMin, int yMax) {
			super();
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.zAxis = zAxis;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.eye = eye;
			this.view = view;
			this.viewUp = viewUp;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		@Override
		protected void compute() {
			if (yMax - yMin - 1 <= THRESHOLD) {
				actualJob(red, green, blue, zAxis, yAxis, xAxis, screenCorner,
						scene, eye, view, viewUp, horizontal, vertical, width,
						height, yMin, yMax);
			} else {
				invokeAll(new ThreadJob(red, green, blue, zAxis, yAxis, xAxis,
						screenCorner, scene, eye, view, viewUp, horizontal,
						vertical, width, height, yMin, (yMax + yMin) / 2 - 1),
						new ThreadJob(red, green, blue, zAxis, yAxis, xAxis,
								screenCorner, scene, eye, view, viewUp,
								horizontal, vertical, width, height,
								(yMax + yMin) / 2, yMax));
			}

		}

	}
}
