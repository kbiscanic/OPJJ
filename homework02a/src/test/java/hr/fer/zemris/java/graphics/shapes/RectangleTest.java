package hr.fer.zemris.java.graphics.shapes;

import junit.framework.Assert;

import org.junit.Test;

public class RectangleTest {

	@Test
	public void testGettersSetters() {
		Rectangle rec = new Rectangle(0, 2, 10, 4);
		Assert.assertEquals(4, rec.getHeight());
		Assert.assertEquals(10, rec.getWidth());
		rec.setHeight(5);
		rec.setWidth(5);
		Assert.assertEquals(5, rec.getHeight());
		Assert.assertEquals(5, rec.getWidth());
	}

	@Test
	public void testRectangle() {
		boolean exc = false;
		try {
			new Rectangle(0, 0, -1, 4);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			new Rectangle(0, 0, -1, -1);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			new Rectangle(0, 0, 4, 0);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}

	@Test
	public void testSettersExceptions() {
		boolean exc = false;
		try {
			Rectangle rec = new Rectangle(0, 0, 4, 9);
			rec.setHeight(-1);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);

		exc = false;
		try {
			Rectangle rec = new Rectangle(0, 0, 4, 9);
			rec.setWidth(-1);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}

}
