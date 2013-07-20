package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import junit.framework.Assert;

import org.junit.Test;

public class SquareTest {

	@Test
	public void testGettersSetters() {
		Square sq = new Square(0, 0, 10);
		Assert.assertEquals(10, sq.getHeight());
		Assert.assertEquals(10, sq.getWidth());
		sq.setSize(14);
		Assert.assertEquals(14, sq.getSize());
		Assert.assertEquals(0, sq.getStartX());
		Assert.assertEquals(0, sq.getStartY());
		sq.setStartX(5);
		sq.setStartY(-1);
		Assert.assertEquals(5, sq.getStartX());
		Assert.assertEquals(-1, sq.getStartY());

	}

	@Test
	public void testSquare() {
		boolean exc = false;
		try {
			new Square(0, 0, -1);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}

	@Test
	public void testSetSize() {
		boolean exc = false;
		try {
			Square sq = new Square(0, 0, 4);
			sq.setSize(-2);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}

	@Test
	public void testDrawing() {
		Square sq = new Square(0, 0, 5);
		BWRaster raster = new BWRasterMem(10, 10);
		sq.draw(raster);

		Assert.assertTrue(sq.containsPoint(0, 0));
		Assert.assertFalse(sq.containsPoint(0, 10));
		Assert.assertFalse(sq.containsPoint(10, 10));
		Assert.assertFalse(sq.containsPoint(10, 0));
		Assert.assertFalse(sq.containsPoint(0, -10));
		Assert.assertFalse(sq.containsPoint(-10, 10));
		Assert.assertFalse(sq.containsPoint(-10, -10));
	}

}
