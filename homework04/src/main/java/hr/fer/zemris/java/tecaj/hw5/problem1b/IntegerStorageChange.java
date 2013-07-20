package hr.fer.zemris.java.tecaj.hw5.problem1b;

public class IntegerStorageChange {
	private IntegerStorage istorage;
	private int oldValue;
	private int newValue;

	public IntegerStorageChange(IntegerStorage istorage, int oldValue,
			int newValue) {
		super();
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public IntegerStorage getIstorage() {
		return istorage;
	}

	public int getOldValue() {
		return oldValue;
	}

	public int getNewValue() {
		return newValue;
	}

}
