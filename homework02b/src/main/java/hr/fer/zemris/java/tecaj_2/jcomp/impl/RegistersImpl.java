package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

/**
 * Class implementing {@link Registers} interface.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class RegistersImpl implements Registers {

	/**
	 * Array of {@link Object}s representing registers.
	 */
	private Object[] registers;
	/**
	 * Integer representing next instruction that needs to be executed.
	 */
	private int programCounter;
	/**
	 * Flag register used for conditional instructions.
	 */
	private boolean flag;
	/**
	 * Number of registers.
	 */
	private int size;

	/**
	 * Constructor for {@link RegistersImpl} object. Creates new array and sets
	 * <code>flag</code> to <code>false</code> and <code>programCounter</code>
	 * to <code>0</code>.
	 *
	 * @param size
	 *            Number of registers required to create.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>size < 1</code>.
	 */
	public RegistersImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException(
					"Number of registers must be at least 1!");
		}
		this.size = size;
		registers = new Object[size];
		flag = false;
		programCounter = 0;
	}

	/**
	 * Method used to get {@link Object} stored in register at given
	 * <code>index</code>.
	 *
	 * @param index
	 *            number of register
	 * @return {@link Object} stored at register <code>index</code>.
	 */
	@Override
	public Object getRegisterValue(int index) {
		checkIndex(index);
		return registers[index];
	}

	/**
	 * Method used to set {@link Object} into register at given
	 * <code>index</code>.
	 *
	 * @param index
	 *            number of register
	 * @param value
	 *            {@link Object} that needs to be stored into register at given
	 *            <code>index</code>.
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		checkIndex(index);
		registers[index] = value;

	}

	/**
	 * Method used to get <code>programCounter</code> value.
	 *
	 * @return current value of <code>programCounter</code>.
	 */
	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	/**
	 * Method used to set <code>programCounter</code> value.
	 *
	 * @param value
	 *            new value of <code>programCounter</code>. Needs to be positive
	 *            integer.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>value < 0</code>
	 */
	@Override
	public void setProgramCounter(int value) {
		if (value < 0) {
			throw new IllegalArgumentException(
					"Program counter must be positive integer!");
		}
		programCounter = value;

	}

	/**
	 * Method used to increment <code>programCounter</code> by 1.
	 */
	@Override
	public void incrementProgramCounter() {
		programCounter += 1;

	}

	/**
	 * Method used to get value of <code>flag</code>.
	 *
	 * @return value of <code>flag</code>.
	 */
	@Override
	public boolean getFlag() {
		return flag;
	}

	/**
	 * Method used to set value of <code>flag</code>.
	 *
	 * @param value
	 *            new value of <code>flag</code>.
	 */
	@Override
	public void setFlag(boolean value) {
		flag = value;

	}

	/**
	 * Private method checking if <code>index</code> is valid register index.
	 *
	 * @param index
	 * @throws IllegalArgumentException
	 *             if <code>index</code> is invalid.
	 */
	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("Invalid register index!");
		}
	}

}
