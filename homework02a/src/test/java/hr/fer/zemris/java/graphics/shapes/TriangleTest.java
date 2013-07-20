package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import junit.framework.Assert;

import org.junit.Test;

public class TriangleTest {

	@Test
	public void testDraw() {
		Triangle tri = new Triangle(0, 1, 10, 8, 15, 20);
		BWRaster raster = new BWRasterMem(40, 30);
		tri.draw(raster);
	}

	@Test
	public void testTriangle() {
		Triangle tri = new Triangle(0, 0, 10, 10, 0, 10);
		Assert.assertEquals(0, tri.getX1());
		Assert.assertEquals(0, tri.getY1());
		Assert.assertEquals(10, tri.getX2());
		Assert.assertEquals(10, tri.getY2());
		Assert.assertEquals(0, tri.getX3());
		Assert.assertEquals(10, tri.getY3());
		tri.setX1(0);
		tri.setX2(0);
		tri.setX3(0);
		tri.setY1(0);
		tri.setY2(0);
		tri.setY3(0);
		Assert.assertEquals(0, tri.getX1());
		Assert.assertEquals(0, tri.getY1());
		Assert.assertEquals(0, tri.getX2());
		Assert.assertEquals(0, tri.getY2());
		Assert.assertEquals(0, tri.getX3());
		Assert.assertEquals(0, tri.getY3());
	}

}
