package hr.fer.zemris.java.custom.collections;

/**
 * Class {@link ArrayBackedIndexedCollection} is custom collection based on
 * array. By default, capacity of collection is set to 16 an doubled whenever it
 * is necessary. Duplicate elements are allowed, <code>null</code> references
 * are not allowed.
 * 
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class ArrayBackedIndexedCollection {

	private int size;
	private int capacity;
	private Object[] elements;

	private static final int DEFAULTCAPACITY = 16;

	/**
	 * Default constructor for {@link ArrayBackedIndexedCollection} class. By
	 * default capacity is set to 16 and new empty array of that capacity is
	 * created.
	 * 
	 */
	public ArrayBackedIndexedCollection() {
		this.size = 0;
		this.capacity = DEFAULTCAPACITY;
		this.elements = new Object[this.capacity];
	}

	/**
	 * Constructor for {@link ArrayBackedIndexedCollection} class. Capacity is
	 * set to custom value and new array of that capacity is created.
	 * 
	 * @param initialCapacity
	 *            Base capacity of collection being created.
	 * @throws IllegalArgumentException
	 *             when initialCapacity is less than 1.
	 */
	public ArrayBackedIndexedCollection(final int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.size = 0;
		this.capacity = initialCapacity;
		this.elements = new Object[this.capacity];
	}

	/**
	 * Method used to determine if collection is empty.
	 * 
	 * @return <code>true</code> if current size of collection is 0;
	 *         <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Public getter for current size of collection.
	 * 
	 * @return {@link Integer} value representing current size of collection.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Private method used to reallocate collection to new array if current
	 * capacity is less then needed. New array is created and capacity of
	 * collection is doubled.
	 */
	private void reallocate() {
		this.capacity *= 2;
		Object[] newElements = new Object[this.capacity];

		for (int i = 0; i < size; i++) {
			newElements[i] = this.elements[i];
		}

		this.elements = newElements;
	}

	/**
	 * Method used to add reference into collection. Reference is added at the
	 * end of collection.
	 * 
	 * @param value
	 *            Reference that needs to be added.
	 * @throws IllegalArgumentException
	 *             when attempting to add <code>null</code>.
	 */
	public void add(final Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		if (this.size == this.capacity) {
			reallocate();
		}
		elements[size] = value;
		size++;
	}

	/**
	 * Method used to get reference from collection by it's index.
	 * 
	 * @param index
	 *            Index of reference that needs to be returned. Index must be
	 *            {@link Integer} with value between 0 and current size of
	 *            collection.
	 * @return Reference from collection stored on that index.
	 * @throws IndexOutOfBoundsException
	 *             if index is negative or greater than current size - 1.
	 */
	public Object get(final int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * Method used to remove reference from collection.
	 * 
	 * @param index
	 *            Index of reference that needs to be removed. Index must be
	 *            {@link Integer} with value between 0 and current size of
	 *            collection.
	 * @throws IndexOutOfBoundsException
	 *             if index is negative or greater than current size - 1.
	 */
	public void remove(final int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;
		size--;
	}

	/**
	 * Method used to insert reference into collection at specified position.
	 * All existing elements in collection with index >= requested position are
	 * shifted 1 place up.
	 * 
	 * @param value
	 *            Reference that needs to be inserted.
	 * @param position
	 *            Position where value needs to be inserted. Allowed positions
	 *            are <0,size>.
	 * @throws IllegalArgumentException
	 *             when requested position isn't allowed.
	 * @throws IllegalArgumentException
	 *             when value = <code>null</code>
	 */
	public void insert(final Object value, final int position) {
		if (position < 0 || position > size) {
			throw new IllegalArgumentException();
		}
		if (value == null) {
			throw new IllegalArgumentException();
		}
		if (this.size == this.capacity) {
			reallocate();
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = value;
		size++;

	}

	/**
	 * Method used to find first occurrence of a reference in collection.
	 * 
	 * @param value
	 *            Reference that we are searching for.
	 * @return Position of that reference, -1 if it isn't stored in our
	 *         collection.
	 */
	public int indexOf(final Object value) {
		int position = -1;
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				position = i;
				break;
			}
		}
		return position;
	}

	/**
	 * Method used to determine whether a reference is stored in collection or
	 * isn't.
	 * 
	 * @param value
	 *            Reference that we are searching for.
	 * @return <code>true</code> if requested reference is a part of collection;
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(final Object value) {
		boolean found = false;
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				found = true;
				break;
			}
		}
		return found;
	}

	/**
	 * Method used to clear current collection. Size is set to 0 and new empty
	 * array is created. Capacity isn't changed.
	 */
	public void clear() {
		this.size = 0;
		this.elements = new Object[capacity];
	}
}
