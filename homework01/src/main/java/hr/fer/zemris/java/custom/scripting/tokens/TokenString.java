package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * {@link TokenString} is {@link Token} meant to represent a string.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class TokenString extends Token {

	private final String value;

	/**
	 * Constructor for {@link TokenString} class.
	 * 
	 * @param pvalue
	 *            Value to be stored in an instance of {@link TokenString}
	 *            object.
	 */
	public TokenString(final String pvalue) {
		super();
		this.value = pvalue;
	}

	@Override
	public String asText() {
		return "\"" + value + "\"";
	}
}
