package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * {@link TokenFunction} is {@link Token} meant to represent a function. Valid
 * name of function starts with symbol @ and letter and is then followed by 0 or
 * more letters, digits or undescores.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class TokenFunction extends Token {

	private final String name;

	/**
	 * Constructor for {@link TokenFunction} class.
	 * 
	 * @param pname
	 *            Name of function that is represented by an instance of
	 *            {@link TokenFunction} object.
	 */
	public TokenFunction(final String pname) {
		super();
		this.name = pname;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

}
