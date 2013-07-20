package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ObjectMultistack {
	private Map<String, MultistackEntry> stackList;

	public ObjectMultistack() {
		stackList = new TreeMap<>();
	}

	public void push(String name, ValueWrapper valueWrapper) {
		if (!stackList.containsKey(name)) {
			stackList.put(name, new MultistackEntry(null, valueWrapper));
		} else {
			stackList.put(name, new MultistackEntry(stackList.get(name),
					valueWrapper));
		}
	}

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

	public ValueWrapper peek(String name) {
		if (!stackList.containsKey(name)) {
			throw new NoSuchElementException("There is no " + name + " stack!");
		} else if (stackList.get(name) == null) {
			throw new EmptyStackException();
		}

		return stackList.get(name).value;
	}

	public boolean isEmpty(String name) {
		if (!stackList.containsKey(name)) {
			throw new NoSuchElementException("There is no " + name + " stack!");
		}

		return stackList.get(name) == null;
	}

	private static class MultistackEntry {
		private MultistackEntry previous;
		private ValueWrapper value;

		public MultistackEntry(MultistackEntry prev, ValueWrapper value) {
			this.value = value;
			this.previous = prev;
		}
	}

}
