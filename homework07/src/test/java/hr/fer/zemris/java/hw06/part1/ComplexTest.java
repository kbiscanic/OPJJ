package hr.fer.zemris.java.hw06.part1;

import hr.fer.zemris.java.hw06.part1.ComplexImplementations.Complex;

import org.junit.Assert;
import org.junit.Test;

public class ComplexTest {

	@Test
	public void testComplexDoubleDouble() {
		Complex number = new Complex(2, 4);
		Assert.assertEquals(2., number.getReal(), 10E-6);
		Assert.assertEquals(4., number.getImag(), 10E-6);
	}

	@Test
	public void testComplex() {
		Complex number = new Complex();
		Assert.assertEquals("0", number.toString());
	}

	@Test
	public void testModule() {
		Complex number = new Complex(3, 4);
		Assert.assertEquals(5., number.module(), 10E-6);
	}

	@Test
	public void testMultiply() {
		Complex number = new Complex(2, 3);
		number = number.multiply(new Complex(6, -7));

		Assert.assertEquals(33., number.getReal(), 10E-6);
		Assert.assertEquals(4., number.getImag(), 10E-6);
	}

	@Test
	public void testDivide() {
		Complex number = new Complex(2, 3);
		number = number.divide(new Complex(2, -3));

		Assert.assertEquals(-0.384615384615, number.getReal(), 10E-6);
		Assert.assertEquals(0.9230769230769, number.getImag(), 10E-6);
	}

	@Test
	public void testAdd() {
		Complex number = new Complex(2, 3);
		number = number.add(new Complex(2, -2));

		Assert.assertEquals(4., number.getReal(), 10E-6);
		Assert.assertEquals(1., number.getImag(), 10E-6);
	}

	@Test
	public void testSub() {
		Complex number = new Complex(2, 3);
		number = number.sub(new Complex(2, -2));

		Assert.assertEquals(0., number.getReal(), 10E-6);
		Assert.assertEquals(5., number.getImag(), 10E-6);
	}

	@Test
	public void testNegate() {
		Complex number = new Complex(2, 3);
		number = number.negate();

		Assert.assertEquals(-2., number.getReal(), 10E-6);
		Assert.assertEquals(-3., number.getImag(), 10E-6);
	}

	@Test
	public void testToString() {
		Complex number = new Complex(0, 5);
		Assert.assertEquals("i5.0", number.toString());

		number = new Complex(0, -5);
		Assert.assertEquals("-i5.0", number.toString());

		number = new Complex(5, 0);
		Assert.assertEquals("5.0", number.toString());
		
		number = new Complex(5, 3);
		Assert.assertEquals("5.0 +i3.0", number.toString());
		
		number = new Complex(5, -3);
		Assert.assertEquals("5.0 -i3.0", number.toString());
	}

}
