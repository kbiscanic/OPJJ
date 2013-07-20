package hr.fer.zemris.java.graphics.shapes;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

public class CircleTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Test
	public final void testGetrXY() {
		Circle cir = new Circle(0, 0, 10);
		Assert.assertEquals(10, cir.getrX());
		Assert.assertEquals(10, cir.getrY());
	}

	@Test
	public final void testCircle() {
		boolean exc = false;
		try {
			new Circle(0, 0, -1);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}

	@Test
	public final void testGetSetRad() {
		Circle cir = new Circle(0, 0, 10);
		Assert.assertEquals(10, cir.getRad());
		cir.setRad(5);
		Assert.assertEquals(5, cir.getRad());
	}

	@Test
	public final void testSetRad() {
		Circle cir = new Circle(0, 0, 10);
		boolean exc = false;
		try {
			cir.setRad(-5);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}

	@Test
	public final void testGetCen() {
		Circle cir = new Circle(-2, 4, 10);
		Assert.assertEquals(-2, cir.getCenX());
		Assert.assertEquals(4, cir.getCenY());
		cir.setCenX(1);
		cir.setCenY(-4);
		Assert.assertEquals(1, cir.getCenX());
		Assert.assertEquals(-4, cir.getCenY());

	}

	@Test
	public final void testSetCenX() {
	}

	@Test
	public final void testGetCenY() {
	}

	@Test
	public final void testSetCenY() {
	}

}
