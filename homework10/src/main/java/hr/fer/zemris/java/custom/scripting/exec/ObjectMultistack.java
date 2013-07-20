package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Multistack for various objects.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class ObjectMultistack {
	private Map<String, MultistackEntry> stackList;

	/**
	 * Default constructor.
	 */
	public ObjectMultistack() {
		stackList = new TreeMap<>();
	}

	/**
	 * Method used to push value wrapped in {@link ValueWrapper} onto stack
	 * defined by <code>name</code>.
	 * 
	 * @param name
	 *            Defines on which stack should be pushed.
	 * @param valueWrapper
	 *            Value that needs to be pushed.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (!stackList.containsKey(name)) {
			stackList.put(name, new MultistackEntry(null, valueWrapper));
		} else {
			stackList.put(name, new MultistackEntry(stackList.get(name),
					valueWrapper));
		}
	}

	/**
	 * Returns the top element form stack defined by <code>name</code> and
	 * removes it from that stack.
	 * 
	 * @param name
	 *            Defines from which stack should we pop.
	 * @return Value wrapped in {@link ValueWrapper} object.
	 */
	public ValueWrapper pop(String name) {
		if (!stackList.containsKey(name)) {
			throw new NoSuchElementException("There is no " + name + " stack!");
		} else if (stackList.get(name) == null) {
			throw new EmptyStackException();
		}

		ValueWrapper value = stackList.get(name).value;
		stackList.put(name, stackList.get(name).previous);
		return value;
	}

	/**
	 * Returns the top element form stack defined by <code>name</code>.
	 * 
	 * @param name
	 *            Defines from which stack should we peek.
	 * @return Value wrapped in {@link ValueWrapper} object.
	 */
	public ValueWrapper peek(String name) {
		if (!stackList.containsKey(name)) {
			throw new NoSuchElementException("There is no " + name + " stack!");
		} else if (stackList.get(name) == null) {
			throw new EmptyStackException();
		}

		return stackList.get(name).value;
	}

	/**
	 * Method used to determine if stack given by <code>name</code> is empty.
	 * 
	 * @param name
	 *            Defines which stack are we observing.
	 * @return <code>true</code> if given stack is empty; <code>false</code>
	 *         otherwise.
	 */
	public boolean isEmpty(String name) {
		if (!stackList.containsKey(name)) {
			throw new NoSuchElementException("There is no " + name + " stack!");
		}

		return stackList.get(name) == null;
	}

	/**
	 * Inner class representing one node in linked list.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	private static class MultistackEntry {
		private MultistackEntry previous;
		private ValueWrapper value;

		/**
		 * Default constructor.
		 * 
		 * @param prev
		 * @param value
		 */
		public MultistackEntry(MultistackEntry prev, ValueWrapper value) {
			this.value = value;
			this.previous = prev;
		}
	}

}
