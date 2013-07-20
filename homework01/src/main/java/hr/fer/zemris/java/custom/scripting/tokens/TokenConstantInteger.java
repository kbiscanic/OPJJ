package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * {@link TokenConstantInteger} is {@link Token} meant to represent a constant
 * integer number.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class TokenConstantInteger extends Token {

	private final int value;

	/**
	 * Constructor for {@link TokenConstantInteger} class.
	 * 
	 * @param pvalue
	 *            Value to be stored in an instance of
	 *            {@link TokenConstantInteger} object.
	 */
	public TokenConstantInteger(final int pvalue) {
		super();
		this.value = pvalue;
	}

	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
}
