package hr.fer.zemris.linearna;

import junit.framework.Assert;

import org.junit.Test;

public class MatrixTest {

	@Test
	public void testGetRowsCount() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);

		Assert.assertEquals(2, mat.getRowsCount());
	}

	@Test
	public void testGetColsCount() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);

		Assert.assertEquals(3, mat.getColsCount());
	}

	@Test
	public void testGet() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);

		Assert.assertEquals(4., mat.get(1, 1), 10E-6);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetEx1() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.get(-1, 2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetEx2() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.get(6, 2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetEx3() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.get(0, -2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetEx4() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.get(0, 6);
	}

	@Test
	public void testSet() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.set(1, 1, 0);

		Assert.assertEquals(0., mat.get(1, 1), 10E-6);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetEx1() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.set(-1, 2, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetEx2() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.set(6, 2, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetEx3() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.set(0, -2, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetEx4() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		mat.set(0, 6, 0);
	}

	@Test
	public void testCopy() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		IMatrix b = mat.copy();

		Assert.assertEquals(1., b.get(0, 0), 10E-6);
		Assert.assertEquals(2., b.get(0, 1), 10E-6);
		Assert.assertEquals(3., b.get(0, 2), 10E-6);
		Assert.assertEquals(3., b.get(1, 0), 10E-6);
		Assert.assertEquals(4., b.get(1, 1), 10E-6);
		Assert.assertEquals(5., b.get(1, 2), 10E-6);
	}

	@Test
	public void testNewInstance() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, true);
		IMatrix b = mat.newInstance(2, 3);

		Assert.assertEquals(0., b.get(1, 2), 10E-6);
	}

	@Test
	public void testMatrixIntInt() {
		IMatrix mat = new Matrix(1, 1);

		Assert.assertEquals(0., mat.get(0, 0), 10E-6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatrixIntIntEx1() {
		new Matrix(-1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatrixIntIntEx2() {
		new Matrix(1, -1);
	}

	@Test
	public void testMatrixIntIntDoubleArrayArrayBoolean() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		IMatrix mat = new Matrix(2, 3, elems, false);

		Assert.assertEquals(1., mat.get(0, 0), 10E-6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatrixIntIntDoubleArrayArrayBooleanEx1() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		new Matrix(-2, 3, elems, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatrixIntIntDoubleArrayArrayBooleanEx2() {
		double[][] elems = { { 1, 2, 3 }, { 3, 4, 5 } };
		new Matrix(2, -3, elems, false);
	}

	@Test
	public void testParseSimple() {
		IMatrix mat = Matrix.parseSimple("1 2 3 | 4 5 6");
		Assert.assertEquals(2, mat.getRowsCount());
		Assert.assertEquals(3, mat.getColsCount());

		Assert.assertEquals(2., mat.get(0, 1), 10E-6);
		Assert.assertEquals(6., mat.get(1, 2), 10E-6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseSimpleEx() {
		Matrix.parseSimple(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseSimpleEx2() {
		Matrix.parseSimple("1 2 | 3 | 4 5");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseSimpleEx3() {
		Matrix.parseSimple("1 2 | 3 a | 4 5");

	}

	@Test
	public void testNTranspose() {
		IMatrix mat = Matrix.parseSimple("1 | 2").nTranspose(false);

		Assert.assertEquals(1, mat.getRowsCount());
		Assert.assertEquals(2, mat.getColsCount());

		Assert.assertEquals(1., mat.get(0, 0), 10E-6);
		Assert.assertEquals(2., mat.get(0, 1), 10E-6);

	}

	@Test
	public void testNTranspose2() {
		IMatrix mat = Matrix.parseSimple("1 | 2").nTranspose(true);

		Assert.assertEquals(1, mat.getRowsCount());
		Assert.assertEquals(2, mat.getColsCount());

		Assert.assertEquals(1., mat.get(0, 0), 10E-6);
		Assert.assertEquals(2., mat.get(0, 1), 10E-6);

		((MatrixTransposeView) mat).copy();
		((MatrixTransposeView) mat).newInstance(2, 4);

	}

	@Test
	public void testAdd() {
		IMatrix mat = Matrix.parseSimple("1 2 | 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 0 | 0 -1");

		mat.add(mat2);

		Assert.assertEquals(0., mat.get(0, 0), 10E-6);
		Assert.assertEquals(2., mat.get(0, 1), 10E-6);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testAddEx() {
		IMatrix mat = Matrix.parseSimple("1 2 | 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 | -1 | 4");

		mat.add(mat2);
	}

	@Test
	public void testNAdd() {
		IMatrix mat = Matrix.parseSimple("1 2 | 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 0 | 0 -1");

		IMatrix mat3 = mat.nAdd(mat2);

		Assert.assertEquals(0., mat3.get(0, 0), 10E-6);
		Assert.assertEquals(2., mat3.get(0, 1), 10E-6);
	}

	@Test
	public void testSub() {
		IMatrix mat = Matrix.parseSimple("1 2 | 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 0 | 0 -1");

		mat.sub(mat2);

		Assert.assertEquals(2., mat.get(0, 0), 10E-6);
		Assert.assertEquals(2., mat.get(0, 1), 10E-6);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testSubEx() {
		IMatrix mat = Matrix.parseSimple("1 2 | 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 | -1 | 4");

		mat.sub(mat2);
	}

	@Test
	public void testNSub() {
		IMatrix mat = Matrix.parseSimple("1 2 | 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 0 | 0 -1");

		IMatrix mat3 = mat.nSub(mat2);

		Assert.assertEquals(2., mat3.get(0, 0), 10E-6);
		Assert.assertEquals(5., mat3.get(1, 1), 10E-6);
	}

	@Test
	public void testNMultiply() {
		IMatrix mat = Matrix.parseSimple("1 2 | 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 0 | 0 -1");

		IMatrix mat3 = mat.nMultiply(mat2);

		Assert.assertEquals(-1., mat3.get(0, 0), 10E-6);
		Assert.assertEquals(-4., mat3.get(1, 1), 10E-6);

	}

	@Test(expected = IncompatibleOperandException.class)
	public void testNMultiplyEx() {
		IMatrix mat = Matrix.parseSimple("1 2 3 | 2 3 4");
		IMatrix mat2 = Matrix.parseSimple("-1 0 | 0 -1");

		mat.nMultiply(mat2);

	}

	@Test
	public void testDeterminant() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10 | 5 5 5");

		Assert.assertEquals(160, mat.determinant(), 10E-6);
	}

	@Test
	public void testDeterminant2() {
		IMatrix mat = Matrix.parseSimple("1");

		Assert.assertEquals(1, mat.determinant(), 10E-6);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testDeterminantEx() {
		IMatrix mat = Matrix.parseSimple("1 10 | 10 2 | 5 5");

		mat.determinant();
	}

	@Test
	public void testSubMatrix() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10 | 5 5 5");

		IMatrix mat2 = mat.subMatrix(1, 2, false);
		Assert.assertEquals(1., mat2.get(0, 0), 10E-6);
		Assert.assertEquals(10., mat2.get(0, 1), 10E-6);
		Assert.assertEquals(5., mat2.get(1, 0), 10E-6);
		Assert.assertEquals(5., mat2.get(1, 1), 10E-6);
	}

	@Test
	public void testNInvert() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10 | 5 5 5");

		IMatrix mat2 = mat.nInvert();

		Assert.assertEquals(0., mat2.get(1, 0), 10E-6);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testNInvertEx() {
		IMatrix mat = Matrix.parseSimple("1 1 1 | 1 1 1 | 1 1 1");

		mat.nInvert();
	}

	@Test
	public void testToArray() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10 | 5 5 5");
		double[][] array = mat.toArray();

		Assert.assertEquals(1., array[0][0], 10E-6);
		Assert.assertEquals(10., array[0][1], 10E-6);
		Assert.assertEquals(10., array[1][0], 10E-6);
		Assert.assertEquals(5., array[2][1], 10E-6);
	}

	@Test
	public void testToVector() {
		IMatrix mat = Matrix.parseSimple("1 10 5 10 2 10");
		IVector vec = mat.toVector(false);
		String s = "(1, 10, 5, 10, 2, 10)";
		Assert.assertEquals(s, ((AbstractVector) vec).toString(0));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void testToVectorEx() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10");
		mat.toVector(false);
	}

	@Test
	public void testToVector2() {
		IMatrix mat = Matrix.parseSimple("1 | 10 | 5 | 10 | 2 | 10");
		IVector vec = mat.toVector(false);
		String s = "(1, 10, 5, 10, 2, 10)";
		Assert.assertEquals(s, ((AbstractVector) vec).toString(0));
	}

	@Test
	public void testNScalarMultiply() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10 | 5 5 5");
		IMatrix mat2 = mat.nScalarMultiply(2);

		Assert.assertEquals(2., mat2.get(0, 0), 10E-6);
		Assert.assertEquals(20., mat2.get(0, 1), 10E-6);
		Assert.assertEquals(20., mat2.get(1, 0), 10E-6);
		Assert.assertEquals(10., mat2.get(2, 1), 10E-6);
	}

	@Test
	public void testScalarMultiply() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10 | 5 5 5");
		mat.scalarMultiply(2);

		Assert.assertEquals(2., mat.get(0, 0), 10E-6);
		Assert.assertEquals(20., mat.get(0, 1), 10E-6);
		Assert.assertEquals(20., mat.get(1, 0), 10E-6);
		Assert.assertEquals(10., mat.get(2, 1), 10E-6);
	}

	@Test
	public void testMakeIdentity() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10 | 5 5 5");
		mat.makeIdentity();

		Assert.assertEquals(1., mat.get(0, 0), 10E-6);
		Assert.assertEquals(0., mat.get(0, 1), 10E-6);
		Assert.assertEquals(0., mat.get(1, 0), 10E-6);
		Assert.assertEquals(0., mat.get(2, 1), 10E-6);
	}

	@Test
	public void testToString() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10");
		String s = "[1.000, 10.000, 5.000]\n[10.000, 2.000, 10.000]";

		Assert.assertEquals(s, ((AbstractMatrix) mat).toString());
	}

	@Test
	public void testToStringInt() {
		IMatrix mat = Matrix.parseSimple("1 10 5 | 10 2 10");
		String s = "[1, 10, 5]\n[10, 2, 10]";

		Assert.assertEquals(s, ((AbstractMatrix) mat).toString(0));
	}

}
