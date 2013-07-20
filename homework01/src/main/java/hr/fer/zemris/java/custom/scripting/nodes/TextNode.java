package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * {@link TextNode} is class used to represent a piece of text data. It can only
 * hold a string of text.
 * 
 * @author Kristijan Biscanic
 */
public class TextNode extends Node {

	private final String text;

	/**
	 * Constructor for {@link TextNode} class.
	 * 
	 * @param ptext
	 *            {@link String} which value will be saved into created
	 *            {@link TextNode}.
	 */
	public TextNode(final String ptext) {
		super();
		this.text = ptext;
	}

	@Override
	public String getString() {
		return text;
	}

}
