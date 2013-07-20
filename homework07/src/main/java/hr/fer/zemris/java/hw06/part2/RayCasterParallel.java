package hr.fer.zemris.java.hw06.part2;

import hr.fer.zemris.java.tecaj_06.rays.IRayTracerProducer;
import hr.fer.zemris.java.tecaj_06.rays.Point3D;
import hr.fer.zemris.java.tecaj_06.rays.RayTracerViewer;

/**
 * Program used to show a sample scene for this homework. This program is using
 * multi-threading for speeding up the process of computing.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class RayCasterParallel {

	/**
	 * Main method for this program.
	 * 
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(), new Point3D(0, 0, 10), 20, 20);

	}

	/**
	 * @return {@link RayTracerProducer} for producing a sample scene using
	 *         multiple threads.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new RayTracerProducer(true);
	}
}
