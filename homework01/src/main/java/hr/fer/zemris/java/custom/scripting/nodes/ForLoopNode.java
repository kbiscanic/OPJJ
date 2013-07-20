package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * {@link ForLoopNode} is a class used to represent a single for-loop construct.
 * This type of nodes must contain 1 {@link TokenVariable} and 2 or 3
 * {@link Token}s.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class ForLoopNode extends Node {
	private final TokenVariable variable;
	private final Token startExpression;
	private final Token endExpression;
	private final Token stepExpression;

	/**
	 * Constructor for {@link ForLoopNode} class.
	 * 
	 * @param pvariable
	 *            {@link TokenVariable} contained in {@link ForLoopNode}.
	 * @param pstartExpression
	 *            {@link Token} contained in {@link ForLoopNode}.
	 * @param pendExpression
	 *            {@link Token} contained in {@link ForLoopNode}.
	 * @param pstepExpression
	 *            {@link Token} contained in {@link ForLoopNode}. Can be
	 *            <code>null</code>.
	 */
	public ForLoopNode(final TokenVariable pvariable,
			final Token pstartExpression, final Token pendExpression,
			final Token pstepExpression) {
		super();
		this.variable = pvariable;
		this.startExpression = pstartExpression;
		this.endExpression = pendExpression;
		this.stepExpression = pstepExpression;
	}

	@Override
	public String getString() {
		if (stepExpression == null) {
			return "[$FOR " + variable.asText() + " "
					+ startExpression.asText() + " " + endExpression.asText()
					+ " $]";
		} else {
			return "[$FOR " + variable.asText() + " "
					+ startExpression.asText() + " " + endExpression.asText()
					+ " " + stepExpression.asText() + " $]";
		}
	}

	@Override
	public boolean needsEnd() {
		return true;
	}

}
