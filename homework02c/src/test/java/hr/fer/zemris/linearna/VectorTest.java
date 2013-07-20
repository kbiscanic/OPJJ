package hr.fer.zemris.linearna;

import junit.framework.Assert;

import org.junit.Test;

public class VectorTest {

	@Test
	public void testGet() {
		IVector a = new Vector(1);
		Assert.assertEquals(a.get(0), Double.valueOf(1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetEx1() {
		new Vector(1).get(3);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetEx2() {
		new Vector(1).get(-1);
	}

	@Test
	public void testSet() {
		IVector a = new Vector(1);
		a.set(0, -1);
		Assert.assertEquals(a.get(0), Double.valueOf(-1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetEx1() {
		new Vector(1).set(-1, -1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetEx2() {
		new Vector(1).set(4, -1);
	}

	@Test(expected = UnmodifiableObjectException.class)
	public void testSetEx3() {
		new Vector(true, false, 1, 2).set(0, -1);
	}

	@Test
	public void testGetDimension() {
		IVector a = new Vector(1, 0, -1);
		Assert.assertEquals(3, a.getDimension());
	}

	@Test
	public void testCopy() {
		IVector a = new Vector(1, 0, 4);
		IVector b = a.copy();
		Assert.assertEquals(b.getDimension(), a.getDimension());
		Assert.assertEquals(a.get(0), b.get(0));
		Assert.assertEquals(a.get(1), b.get(1));
		Assert.assertEquals(a.get(2), b.get(2));
	}

	@Test
	public void testNewInstance() {
		IVector a = new Vector(1, 2);
		IVector b = a.newInstance(4);
		Assert.assertEquals(4, b.getDimension());
	}

	@Test
	public void testVectorDoubleArray() {
		IVector a = new Vector(-2, 4, 1);
		Assert.assertEquals(a.get(0), Double.valueOf(-2));
		Assert.assertEquals(a.get(1), Double.valueOf(4));
		Assert.assertEquals(a.get(2), Double.valueOf(1));
	}

	@Test
	public void testVectorConst() {
		IVector a = new Vector(true, true, -2, 4, 1);
		Assert.assertEquals(a.get(0), Double.valueOf(-2));
		Assert.assertEquals(a.get(1), Double.valueOf(4));
		Assert.assertEquals(a.get(2), Double.valueOf(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVectorDoubleArrayEx() {
		new Vector(null);
	}

	@Test
	public void testParseSimple() {
		IVector a = Vector.parseSimple("3 1 -3");
		Assert.assertEquals(a.get(0), Double.valueOf(3));
		Assert.assertEquals(a.get(1), Double.valueOf(1));
		Assert.assertEquals(a.get(2), Double.valueOf(-3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseSimpleEx1() {
		Vector.parseSimple("3 1 a");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseSimpleEx2() {
		Vector.parseSimple(null);
	}

	@Test
	public void testAdd() {
		IVector a = new Vector(-2, 4, 1);
		a.add(new Vector(1, 1, 1));
		Assert.assertEquals(a.get(0), Double.valueOf(-1));
		Assert.assertEquals(a.get(1), Double.valueOf(5));
		Assert.assertEquals(a.get(2), Double.valueOf(2));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testAddEx() {
		IVector a = new Vector(-2, 4, 1);
		a.add(new Vector(1, 1, 1, 4));
	}

	@Test
	public void testNAdd() {
		IVector a = new Vector(-2, 4, 1);
		IVector b = a.nAdd(new Vector(1, 1, 1));

		Assert.assertEquals(a.get(0), Double.valueOf(-2));
		Assert.assertEquals(a.get(1), Double.valueOf(4));
		Assert.assertEquals(a.get(2), Double.valueOf(1));

		Assert.assertEquals(b.get(0), Double.valueOf(-1));
		Assert.assertEquals(b.get(1), Double.valueOf(5));
		Assert.assertEquals(b.get(2), Double.valueOf(2));
	}

	@Test
	public void testSub() {
		IVector a = new Vector(-2, 4, 1);
		a.sub(new Vector(1, 1, 1));
		Assert.assertEquals(a.get(0), Double.valueOf(-3));
		Assert.assertEquals(a.get(1), Double.valueOf(3));
		Assert.assertEquals(a.get(2), Double.valueOf(0));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testSubEx() {
		IVector a = new Vector(-2, 4, 1);
		a.sub(new Vector(1, 1));
	}

	@Test
	public void testNSub() {
		IVector a = new Vector(-2, 4, 1);
		IVector b = a.nSub(new Vector(1, 1, 1));

		Assert.assertEquals(a.get(0), Double.valueOf(-2));
		Assert.assertEquals(a.get(1), Double.valueOf(4));
		Assert.assertEquals(a.get(2), Double.valueOf(1));

		Assert.assertEquals(b.get(0), Double.valueOf(-3));
		Assert.assertEquals(b.get(1), Double.valueOf(3));
		Assert.assertEquals(b.get(2), Double.valueOf(0));
	}

	@Test
	public void testScalarMultiply() {
		IVector a = new Vector(-1, 0, 4);
		a.scalarMultiply(2);

		Assert.assertEquals(a.get(0), Double.valueOf(-2));
		Assert.assertEquals(a.get(1), Double.valueOf(0));
		Assert.assertEquals(a.get(2), Double.valueOf(8));
	}

	@Test
	public void testNScalarMultiply() {
		IVector a = new Vector(-2, 4, 1);
		IVector b = a.nScalarMultiply(2);

		Assert.assertEquals(a.get(0), Double.valueOf(-2));
		Assert.assertEquals(a.get(1), Double.valueOf(4));
		Assert.assertEquals(a.get(2), Double.valueOf(1));

		Assert.assertEquals(b.get(0), Double.valueOf(-4));
		Assert.assertEquals(b.get(1), Double.valueOf(8));
		Assert.assertEquals(b.get(2), Double.valueOf(2));
	}

	@Test
	public void testNorm() {
		IVector a = new Vector(-3, 4, 0);
		Assert.assertEquals(5, a.norm(), 10E-4);
		a.set(2, 1);
		Assert.assertEquals(5.09902, a.norm(), 10E-4);
	}

	@Test
	public void testNormalize() {
		IVector a = new Vector(-3, 4, 0);
		a.normalize();

		Assert.assertEquals(-3. / 5, a.get(0), 10E-6);
		Assert.assertEquals(4. / 5, a.get(1), 10E-6);
		Assert.assertEquals(0, a.get(2), 10E-6);
	}

	@Test
	public void testNNormalize() {
		IVector a = new Vector(-3, 4, 0);
		IVector b = a.nNormalize();

		Assert.assertEquals(a.get(0), Double.valueOf(-3));
		Assert.assertEquals(a.get(1), Double.valueOf(4));
		Assert.assertEquals(a.get(2), Double.valueOf(0));

		Assert.assertEquals(-3. / 5, b.get(0), 10E-6);
		Assert.assertEquals(4. / 5, b.get(1), 10E-6);
		Assert.assertEquals(0, b.get(2), 10E-6);
	}

	@Test
	public void testCosine() {
		IVector a = new Vector(1, 0);
		Assert.assertEquals(1, a.cosine(new Vector(3, 0)), 10E-6);
	}

	@Test
	public void testScalarProduct() {
		IVector a = new Vector(1, 0);
		Assert.assertEquals(0, a.cosine(new Vector(0, 1)), 10E-6);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testScalarProductEx() {
		IVector a = new Vector(1, 0);
		a.cosine(new Vector(0, 1, 4));
	}

	@Test
	public void testNVectorProduct() {
		IVector a = new Vector(1, 2, 3);
		IVector sol = a.nVectorProduct(new Vector(3, 4, 5));
		Assert.assertEquals(-2, sol.get(0), 10E-4);
		Assert.assertEquals(4, sol.get(1), 10E-4);
		Assert.assertEquals(-2, sol.get(2), 10E-4);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testNVectorProductEx1() {
		IVector a = new Vector(1, 2, 3);
		a.nVectorProduct(new Vector(3, 4, 5, 6));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testNVectorProductEx2() {
		IVector a = new Vector(1, 2, 3, 4);
		a.nVectorProduct(new Vector(3, 4, 5, 6));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testNVectorProductEx3() {
		IVector a = new Vector(1, 2, 3, 4);
		a.nVectorProduct(new Vector(3, 4, 5));
	}

	@Test
	public void testNFromHomogeneus() {
		IVector x = new Vector(1, 2, 3, 4);
		IVector a = x.nFromHomogeneus();
		Assert.assertEquals(1. / 4, a.get(0), 10E-4);
		Assert.assertEquals(2. / 4, a.get(1), 10E-4);
		Assert.assertEquals(3. / 4, a.get(2), 10E-4);
	}

	@Test(expected = ArithmeticException.class)
	public void testNFromHomogeneusEx() {
		IVector x = new Vector(1, 2, 3, 0);
		x.nFromHomogeneus();
	}

	@Test
	public void testToArray() {
		IVector a = new Vector(1, 2, 3);
		double[] x = a.toArray();
		Assert.assertEquals(1., x[0], 10E-4);
		Assert.assertEquals(2., x[1], 10E-4);
		Assert.assertEquals(3., x[2], 10E-4);
	}

	@Test
	public void testCopyPart() {
		IVector a = new Vector(-1, 0, -1);
		IVector b = a.copyPart(1);
		Assert.assertEquals(1, b.getDimension());
		Assert.assertEquals(-1, b.get(0), 10E-4);
	}

	@Test
	public void testCopyPart2() {
		IVector a = new Vector(-1, 0, 1);
		IVector b = a.copyPart(5);
		Assert.assertEquals(5, b.getDimension());
		Assert.assertEquals(-1, b.get(0), 10E-4);
		Assert.assertEquals(0, b.get(1), 10E-4);
		Assert.assertEquals(1, b.get(2), 10E-4);
		Assert.assertEquals(0, b.get(3), 10E-4);
		Assert.assertEquals(0, b.get(4), 10E-4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCopyPartEx() {
		IVector a = new Vector(-1, 0, 1);
		a.copyPart(-2);
	}

	@Test
	public void testToRowMatrixLive() {
		IVector a = new Vector(1, 3, 4);
		IMatrix mat = a.toRowMatrix(true);
		Assert.assertEquals(1, mat.getRowsCount());
		Assert.assertEquals(3, mat.getColsCount());
	}

	@Test
	public void testToRowMatrix() {
		IVector a = new Vector(1, 3, 4);
		IMatrix mat = a.toRowMatrix(false);
		Assert.assertEquals(1, mat.getRowsCount());
		Assert.assertEquals(3, mat.getColsCount());
	}

	@Test
	public void testToColMatrixLive() {
		IVector a = new Vector(1, 3, 4);
		IMatrix mat = a.toColumnMatrix(true);
		Assert.assertEquals(3, mat.getRowsCount());
		Assert.assertEquals(1, mat.getColsCount());
	}

	@Test
	public void testToColMatrix() {
		IVector a = new Vector(1, 3, 4);
		IMatrix mat = a.toColumnMatrix(false);
		Assert.assertEquals(3, mat.getRowsCount());
		Assert.assertEquals(1, mat.getColsCount());
	}

	@Test
	public void testToStringInt() {
		IVector a = new Vector(2.5, 3, 4.9921);
		String s = "(2.50, 3.00, 4.99)";
		Assert.assertEquals(s, ((AbstractVector) a).toString(2));
	}

	@Test
	public void testToString() {
		IVector a = new Vector(2.5, 3, 4.9921);
		String s = "(2.500, 3.000, 4.992)";
		Assert.assertEquals(s, ((AbstractVector) a).toString());
	}

}
