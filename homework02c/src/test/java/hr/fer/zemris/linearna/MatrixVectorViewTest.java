package hr.fer.zemris.linearna;

import org.junit.Assert;
import org.junit.Test;

public class MatrixVectorViewTest {

	@Test
	public void testNewInstance() {
		IVector vec = Vector.parseSimple("1 2 3 4");
		IMatrix m = new MatrixVectorView(vec, true);

		IMatrix m2 = m.newInstance(1, 3);
		Assert.assertEquals(0., m2.get(0, 2), 10E-6);
	}

	@Test
	public void testNewInstance2() {
		IVector vec = Vector.parseSimple("1 2 3 4");
		IMatrix m = new MatrixVectorView(vec, false);

		IMatrix m2 = m.newInstance(3, 1);
		Assert.assertEquals(0., m2.get(2, 0), 10E-6);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNewInstanceEx() {
		IVector vec = Vector.parseSimple("1 2 3 4");
		IMatrix m = new MatrixVectorView(vec, true);

		m.newInstance(2, 3);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testNewInstanceEx2() {
		IVector vec = Vector.parseSimple("1 2 3 4");
		IMatrix m = new MatrixVectorView(vec, false);

		m.newInstance(2, 3);
	}

}
