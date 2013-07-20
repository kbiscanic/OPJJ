package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Class implementing {@link Computer} interface.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class ComputerImpl implements Computer {

	/**
	 * Property representing {@link Memory} of given {@link Computer}.
	 */
	private Memory memory;
	/**
	 * Property representing {@link Registers} of given {@link Computer}.
	 */
	private Registers registers;

	/**
	 * Constructor for {@link ComputerImpl} object.
	 *
	 * @param memSize
	 *            Size of {@link Memory} required.
	 * @param regNum
	 *            Number of {@link Registers} required.
	 */
	public ComputerImpl(int memSize, int regNum) {
		memory = new MemoryImpl(memSize);
		registers = new RegistersImpl(regNum);
	}

	/**
	 * Method returning {@link Registers} of {@link Computer}.
	 *
	 * @return registers
	 */
	@Override
	public Registers getRegisters() {
		return registers;
	}

	/**
	 * Method returning {@link Memory} of {@link Computer}.
	 *
	 * @return memory
	 */
	@Override
	public Memory getMemory() {
		return memory;
	}

}
