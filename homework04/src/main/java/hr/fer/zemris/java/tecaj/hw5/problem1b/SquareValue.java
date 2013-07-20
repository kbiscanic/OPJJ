package hr.fer.zemris.java.tecaj.hw5.problem1b;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange storageChange) {
		int value = storageChange.getNewValue();
		System.out.println("Provided new value: " + value + ", square is "
				+ value * value);

	}
}
