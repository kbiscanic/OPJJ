package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * {@link TokenConstantDouble} is {@link Token} meant to represent a constant
 * double number.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class TokenConstantDouble extends Token {

	private final double value;

	/**
	 * Constructor for {@link TokenConstantDouble} class.
	 * 
	 * @param pvalue
	 *            Value to be stored in an instance of
	 *            {@link TokenConstantDouble} object.
	 */
	public TokenConstantDouble(final double pvalue) {
		super();
		this.value = pvalue;
	}

	@Override
	public String asText() {
		return Double.toString(this.value);
	}

	public Object getValue() {
		return value;
	}

}
