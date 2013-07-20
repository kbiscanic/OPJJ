package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;

import org.junit.Assert;
import org.junit.Test;

public class MemoryImplTest {

	@Test
	public void testMemoryImpl() {
		Memory mem = new MemoryImpl(10);
		mem.setLocation(0, Integer.valueOf(5));
		Assert.assertEquals(5, mem.getLocation(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMemoryImplEx() {
		new MemoryImpl(-5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckLocationEx() {
		Memory mem = new MemoryImpl(5);
		mem.getLocation(-3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCheckLocationEx2() {
		Memory mem = new MemoryImpl(5);
		mem.getLocation(10);
	}

}
