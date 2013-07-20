package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * {@link TokenVariable} is {@link Token} meant to represent a variable. Valid
 * variable name starts by letter and is then followed by 0 or more
 * letters,digits and underscores.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class TokenVariable extends Token {

	private final String name;

	/**
	 * Constructor for {@link TokenVariable} class.
	 * 
	 * @param pname
	 *            Name of variable that is represented by an instance of
	 *            {@link TokenVariable} object.
	 */
	public TokenVariable(final String pname) {
		super();
		this.name = pname;
	}

	@Override
	public String asText() {
		return this.name;
	}

}
