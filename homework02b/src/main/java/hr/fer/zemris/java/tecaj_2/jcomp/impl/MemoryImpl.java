package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Memory;

/**
 * Class implementing {@link Memory} interface. This implementation can store
 * any Java {@link Object}.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class MemoryImpl implements Memory {

	/**
	 * Array storing references to actual objects.
	 */
	private Object[] memory;
	/**
	 * Size of <code>memory</code> array.
	 */
	private int size;

	/**
	 * Constructor for creating an instance of {@link MemoryImpl} object.
	 *
	 * @param size
	 *            Size of required <code>memory</code> array. Must be >= 1.
	 * @throws IllegalArgumentException
	 *             if <code>size</code> is <code> < 1 </code>.
	 */
	public MemoryImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException(
					"Size of memory must be at least 1!");
		}
		this.size = size;
		memory = new Object[size];
	}

	/**
	 * Method used to set <code>value</code> into given <code>location</code>.
	 *
	 * @param location
	 *            memory location
	 * @param value
	 *            Object that needs to be stored.
	 */
	@Override
	public void setLocation(int location, Object value) {
		checkLocation(location);
		memory[location] = value;

	}

	/**
	 * Method used to get <code>value</code> from given <code>location</code>.
	 *
	 * @param location
	 *            memory location
	 * @return Object stored on <code>location</code>.
	 */
	@Override
	public Object getLocation(int location) {
		checkLocation(location);
		return memory[location];
	}

	/**
	 * Private method checking if <code>location</code> is valid memory
	 * location.
	 *
	 * @param location
	 * @throws IllegalArgumentException
	 *             if <code>location</code> is invalid.
	 */
	private void checkLocation(int location) {
		if (location < 0 || location >= size) {
			throw new IllegalArgumentException("Invalid memory location!");
		}
	}

}
