package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * {@link TokenOperator} is {@link Token} meant to represent a operator.
 * Currently supported operators are: + * - /
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class TokenOperator extends Token {

	private final String symbol;

	/**
	 * Constructor for {@link TokenOperator} class.
	 * 
	 * @param psymbol
	 *            Symbol of an operator stored in an instance of
	 *            {@link TokenOperator} object.
	 */
	public TokenOperator(final String psymbol) {
		super();
		this.symbol = psymbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	public String getSymbol() {
		return symbol;
	}

}
