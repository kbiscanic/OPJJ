package hr.fer.zemris.linearna;

import junit.framework.Assert;

import org.junit.Test;

public class VectorMatrixViewTest {

	@Test
	public void testCopy() {
		IMatrix mat = Matrix.parseSimple("1 2 3");
		IVector vec = new VectorMatrixView(mat);

		IVector v2 = vec.copy();

		for (int i = 0; i < 3; i++) {
			Assert.assertTrue(vec.get(i) == v2.get(i));
		}
	}

	@Test
	public void testNewInstance() {
		IMatrix mat = Matrix.parseSimple("1 2 3");
		IVector vec = new VectorMatrixView(mat);

		IVector v2 = vec.newInstance(5);
		Assert.assertTrue(v2.getDimension() == 5);
	}

	@Test
	public void testNewInstance2() {
		IMatrix mat = Matrix.parseSimple("1 | 2 | 3");
		IVector vec = new VectorMatrixView(mat);

		IVector v2 = vec.newInstance(5);
		Assert.assertTrue(v2.getDimension() == 5);
	}

	@Test
	public void testVectorMatrixView() {
		IMatrix mat = Matrix.parseSimple("1 2 3");
		IVector vec = new VectorMatrixView(mat);

		Assert.assertEquals(3, vec.getDimension());
		vec.set(1, 5.3);
		Assert.assertEquals(5.3, vec.get(1), 10E-6);
	}

	@Test
	public void testVectorMatrixView2() {
		IMatrix mat = Matrix.parseSimple("1 | 2 | 3");
		IVector vec = new VectorMatrixView(mat);

		Assert.assertEquals(3, vec.getDimension());
		vec.set(1, 5.3);
		Assert.assertEquals(5.3, vec.get(1), 10E-6);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testVectorMatrixViewEx1() {
		IMatrix mat = Matrix.parseSimple("1 2 | 0 3");
		new VectorMatrixView(mat);
	}

}
