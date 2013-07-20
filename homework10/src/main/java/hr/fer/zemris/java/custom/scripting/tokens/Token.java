package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * {@link Token} is a class used to represent expressions while parsing text. It
 * extends on several other Token classes.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Token {

	/**
	 * Public method used to return String representation of given {@link Token}
	 * .
	 * 
	 * @return String representation of given {@link Token}.
	 */
	public String asText() {
		return "";
	}

	public Object getValue() {
		return null;
	}

}
