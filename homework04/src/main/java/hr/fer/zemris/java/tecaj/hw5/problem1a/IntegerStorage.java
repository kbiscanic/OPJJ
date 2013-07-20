package hr.fer.zemris.java.tecaj.hw5.problem1a;

public class IntegerStorage {

	private int value;
	private IntegerStorageObserver observer;

	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	public void setObserver(IntegerStorageObserver newObserver) {
		this.observer = newObserver;
	}

	public void clearObserver() {
		this.observer = null;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int newValue) {
		if (this.value != newValue) {
			this.value = newValue;
			if (observer != null) {
				observer.valueChanged(this);
			}
		}
	}

}
