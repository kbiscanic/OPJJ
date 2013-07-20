package hr.fer.zemris.java.tecaj.hw5.problem1b;

import java.util.HashSet;
import java.util.Set;

public class IntegerStorage {

	private int value;
	private Set<IntegerStorageObserver> observers;

	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new HashSet<IntegerStorageObserver>();
	}

	public int getValue() {
		return this.value;
	}

	public void addObserver(IntegerStorageObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	public void setValue(int newValue) {
		if (this.value != newValue) {
			IntegerStorageChange change = new IntegerStorageChange(this,
					this.value, newValue);
			this.value = newValue;
			if (observers.size() > 0) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(change);
				}
			}
		}
	}

}
