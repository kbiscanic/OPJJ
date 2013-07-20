package hr.fer.zemris.java.graphics.shapes;

import junit.framework.Assert;

import org.junit.Test;

public class EllipseTest {

	@Test
	public void testGetrX() {
		Ellipse eli = new Ellipse(2, 7, 4, 9);
		Assert.assertEquals(4, eli.getrX());
		Assert.assertEquals(9, eli.getrY());
		eli.setrX(2);
		eli.setrY(91);
		Assert.assertEquals(2, eli.getrX());
		Assert.assertEquals(91, eli.getrY());

	}

	@Test
	public void testEllipse() {
		boolean exc = false;
		try {
			new Ellipse(0, 0, -1, 0);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);

		exc = false;
		try {
			new Ellipse(0, 0, -1, 4);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			new Ellipse(0, 0, 4, 0);
		} catch (IllegalArgumentException e) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}
}
