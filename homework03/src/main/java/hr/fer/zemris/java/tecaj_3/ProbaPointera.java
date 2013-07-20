package hr.fer.zemris.java.tecaj_3;

/**
 * Solution to problem 6 of homework03.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class ProbaPointera {

	/**
	 * Main method for this program. Used to test {@link Pointer} class.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		Integer broj1 = Integer.valueOf(10);

		Pointer<Integer> pBroj1 = new Pointer<Integer>(broj1);
		System.out.println("Broj1 (prije) = " + broj1);
		uvecaj2(pBroj1);
		broj1 = pBroj1.getObject();
		System.out.println("Broj1 (nakon) = " + broj1);

	}

	/**
	 * Private static void method used to increase value of Integer object by 1.
	 * 
	 * @param pBroj1
	 *            Pointer to the object.
	 */
	private static void uvecaj2(Pointer<Integer> pBroj1) {
		pBroj1.setObject(Integer.valueOf(pBroj1.getObject().intValue() + 1));
	}

}
