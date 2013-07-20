package hr.fer.zemris.java.tecaj_3;

/**
 * Class used to represents pointers.
 * 
 * @param <T>
 *            Type of object.
 */
public class Pointer<T> {

	private T object;

	/**
	 * Default constructor for pointer to the given object.
	 * 
	 * @param object
	 *            Object.
	 */
	public <X extends T> Pointer(X object) {
		super();
		this.object = object;
	}

	/**
	 * Method used to get the object on which pointer is referring.
	 * 
	 * @return Object.
	 */
	public T getObject() {
		return object;
	}

	/**
	 * Method used to update the object on which pointer is referring.
	 * 
	 * @param object
	 *            Object.
	 */
	public <X extends T> void setObject(X object) {
		this.object = object;
	}
}
