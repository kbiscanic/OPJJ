package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * {@link EchoNode} is a class used to represent an empty tag of a document. It
 * can hold as many Tokens as needed.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class EchoNode extends Node {

	private final Token[] tokens;

	/**
	 * Constructor for {@link EchoNode} class.
	 * 
	 * @param ptokens
	 *            Array of all tokens contained in {@link EchoNode}.
	 */
	public EchoNode(final Token[] ptokens) {
		super();
		this.tokens = ptokens.clone();
	}

	@Override
	public String getString() {
		final StringBuilder strBuild = new StringBuilder();
		strBuild.append("[$= ");
		for (int i = 0; i < tokens.length; i++) {
			strBuild.append(tokens[i].asText() + " ");
		}
		strBuild.append("$]");
		return strBuild.toString();
	}

}
