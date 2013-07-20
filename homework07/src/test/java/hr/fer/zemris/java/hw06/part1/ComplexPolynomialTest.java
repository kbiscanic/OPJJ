package hr.fer.zemris.java.hw06.part1;

import junit.framework.Assert;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.Complex;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.ComplexPolynomial;

import org.junit.Test;

public class ComplexPolynomialTest {

	@Test
	public void testComplexPolynomial() {
		ComplexPolynomial poly = new ComplexPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		Assert.assertEquals(1, poly.order());
	}

	@Test
	public void testMultiply() {
		ComplexPolynomial poly = new ComplexPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		poly = poly.multiply(poly);

		Assert.assertEquals("(1.0)z^2+(-2.0)z^1+(1.0)", poly.toString());
	}

	@Test
	public void testDerive() {
		ComplexPolynomial poly = new ComplexPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		poly = poly.derive();

		Assert.assertEquals("+(1.0)", poly.toString());
	}

	@Test
	public void testApply() {
		ComplexPolynomial poly = new ComplexPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		Complex number = poly.apply(Complex.ONE);

		Assert.assertEquals(0., number.getReal(), 10E-6);
		Assert.assertEquals(0., number.getImag(), 10E-6);
	}

	// @Test
	// public void testToString() {
	// fail("Not yet implemented");
	// }

}
