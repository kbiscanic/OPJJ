package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import org.junit.Assert;
import org.junit.Test;

public class RegistersImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testRegistersImpl() {
		new RegistersImpl(5);
		new RegistersImpl(-3);
	}

	@Test
	public void testGetSetRegisterValue() {
		Registers reg = new RegistersImpl(3);
		reg.setRegisterValue(0, Integer.valueOf(3));
		Assert.assertEquals(3, reg.getRegisterValue(0));
	}

	@Test
	public void testGetProgramCounter() {
		Registers reg = new RegistersImpl(3);
		Assert.assertEquals(0, reg.getProgramCounter());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetProgramCounter() {
		Registers reg = new RegistersImpl(3);
		reg.setProgramCounter(5);
		reg.setProgramCounter(-3);
	}

	@Test
	public void testIncrementProgramCounter() {
		Registers reg = new RegistersImpl(3);
		reg.incrementProgramCounter();
		Assert.assertEquals(1, reg.getProgramCounter());
	}

	@Test
	public void testGetFlag() {
		Registers reg = new RegistersImpl(3);
		Assert.assertFalse(reg.getFlag());
	}

	@Test
	public void testSetFlag() {
		Registers reg = new RegistersImpl(3);
		reg.setFlag(true);
		Assert.assertTrue(reg.getFlag());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckIndex() {
		Registers reg = new RegistersImpl(3);
		reg.getRegisterValue(-3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckIndex2() {
		Registers reg = new RegistersImpl(3);
		reg.getRegisterValue(9);
	}

}
