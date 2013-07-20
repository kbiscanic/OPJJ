package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;

import org.junit.Test;

public class ComputerImplTest {

	@Test
	public void testComputerImpl() {
		new ComputerImpl(10, 20);
	}

	@Test
	public void testGetRegisters() {
		Computer comp = new ComputerImpl(10, 20);
		comp.getRegisters();
	}

	@Test
	public void testGetMemory() {
		Computer comp = new ComputerImpl(10, 20);
		comp.getMemory();
	}

}
