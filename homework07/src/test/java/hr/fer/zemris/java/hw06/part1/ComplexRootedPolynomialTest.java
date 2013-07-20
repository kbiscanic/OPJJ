package hr.fer.zemris.java.hw06.part1;

import junit.framework.Assert;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.Complex;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.ComplexPolynomial;
import hr.fer.zemris.java.hw06.part1.ComplexImplementations.ComplexRootedPolynomial;

import org.junit.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void testComplexRootedPolynomial() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		Assert.assertEquals("(z -(-1.0))(z -(1.0))", poly.toString());
	}

	@Test
	public void testApply() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(Complex.ONE,
				Complex.ONE_NEG);
		Complex number = poly.apply(Complex.ZERO);
		Assert.assertEquals(-1., number.getReal(), 10E-6);
		Assert.assertEquals(0., number.getImag(), 10E-6);
	}

	@Test
	public void testToComplexPolynom() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(Complex.ONE,
				Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		ComplexPolynomial poly2 = poly.toComplexPolynom();

		Assert.assertEquals("(1.0)z^4+(0)z^3+(0)z^2+(0)z^1+(-1.0)",
				poly2.toString());
	}

	@Test
	public void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(Complex.ONE,
				Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);

		Assert.assertEquals(1, poly.indexOfClosestRootFor(Complex.ONE, 10E-3));
	}

}
