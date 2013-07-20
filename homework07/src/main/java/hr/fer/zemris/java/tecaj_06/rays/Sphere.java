package hr.fer.zemris.java.tecaj_06.rays;

/**
 * Class extending {@link GraphicalObject} representing a sphere in a 3D scene,
 * determined by its center and radius.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Sphere extends GraphicalObject {

	private Point3D center;
	private double radius;
	private double kdr;
	private double kdg;
	private double kdb;
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	/**
	 * Default and only constructor for {@link Sphere} object.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D direction = ray.direction;
		Point3D rayStart = ray.start;
		Point3D centerDistance = rayStart.sub(center);

		double coeffB = direction.scalarMultiply(2).scalarProduct(
				centerDistance);
		double coeffC = centerDistance.scalarProduct(centerDistance) - radius
				* radius;

		Double distance1 = (-1.0 * coeffB + Math.sqrt(coeffB * coeffB - 4.0
				* coeffC)) / 2.0;
		Double distance2 = (-1.0 * coeffB - Math.sqrt(coeffB * coeffB - 4.0
				* coeffC)) / 2.0;

		if (distance1.equals(Double.NaN) && distance2.equals(Double.NaN)) {
			return null;
		}

		double minDistance;
		if (distance1 < 0) {
			minDistance = distance2.doubleValue();
		} else if (distance2 < 0) {
			minDistance = distance1.doubleValue();
		} else {
			minDistance = Math.min(distance1.doubleValue(),
					distance2.doubleValue());
		}

		Point3D point = rayStart.add(direction.scalarMultiply(minDistance));

		return new RayIntersection(point, minDistance, true) {

			@Override
			public Point3D getNormal() {
				return center.sub(this.getPoint()).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
