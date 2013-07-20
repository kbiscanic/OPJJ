package hr.fer.zemris.java.tecaj_3;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class PrimesCollectionTest {

	@Test
	public void testPrimesCollection() {
		PrimesCollection primCol = new PrimesCollection(5);
		int[] sol = new int[5];
		int i = 0;
		for (Integer prime : primCol) {
			sol[i] = Integer.valueOf(prime);
			i++;
		}
		int[] expected = { 2, 3, 5, 7, 11 };
		Assert.assertArrayEquals(expected, sol);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void removeException() {
		PrimesCollection primCol = new PrimesCollection(5);
		Iterator<Integer> it = primCol.iterator();
		it.remove();

	}
}
