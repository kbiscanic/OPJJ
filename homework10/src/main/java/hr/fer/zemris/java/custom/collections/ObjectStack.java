package hr.fer.zemris.java.custom.collections;

/**
 * {@link ObjectStack} is an adaptor class implementing a stack using
 * {@link ArrayBackedIndexedCollection}. It is designed to provide traditional
 * stack operations on a collection. <code>null</code> references are not
 * allowed.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class ObjectStack {
	private final ArrayBackedIndexedCollection collection;

	/**
	 * Default constructor for {@link ObjectStack} class. Creates new
	 * {@link ArrayBackedIndexedCollection} object with default capacity.
	 */
	public ObjectStack() {
		this.collection = new ArrayBackedIndexedCollection();
	}

	/**
	 * Constructor for {@link ObjectStack} class with custom initial capacity.
	 * Creates new {@link ArrayBackedIndexedCollection} object with that
	 * capacity.
	 * 
	 * @param initialCapacity
	 *            Base capacity for stack we are creating.
	 */
	public ObjectStack(final int initialCapacity) {
		this.collection = new ArrayBackedIndexedCollection(initialCapacity);
	}

	/**
	 * Method used to determine whether a stack is empty or not.
	 * 
	 * @return <code>true</code> if stack is empty; <code>false</code>
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Method used to add a value on top of the stack.
	 * 
	 * @param value
	 *            Value that needs to be added.
	 */
	public void push(final Object value) {
		collection.add(value);
	}

	/**
	 * Method used to return and remove a value from top of the stack.
	 * 
	 * @return Value that was on top of the stack.
	 * @throws EmptyStackException
	 *             in case there was attempt to use this method on an empty
	 *             stack.
	 */
	public Object pop() {
		if (collection.isEmpty()) {
			throw new EmptyStackException();
		}
		final Object objekt = collection.get(collection.size() - 1);
		collection.remove(collection.size() - 1);
		return objekt;
	}

	/**
	 * Method used to return a value from top of the stack. Value is not removed
	 * from the stack.
	 * 
	 * @return Value that is on top of the stack.
	 * @throws EmptyStackException
	 *             in case there was attempt to use this method on an empty
	 *             stack.
	 */
	public Object peek() {
		if (collection.isEmpty()) {
			throw new EmptyStackException();
		}
		return collection.get(collection.size() - 1);
	}

	/**
	 * Method used to clear current stack. All elements are removed. Capacity
	 * isn't changed.
	 */
	public void clear() {
		collection.clear();
	}

}
