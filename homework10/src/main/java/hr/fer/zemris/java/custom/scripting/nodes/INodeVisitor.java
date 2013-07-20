package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface for visitors of {@link Node} class.
 */
public interface INodeVisitor {

	/**
	 * Method that needs to be called when visitor is currently visiting
	 * {@link TextNode}.
	 * 
	 * @param node
	 *            {@link TextNode} currently being visited.
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Method that needs to be called when visitor is currently visiting
	 * {@link ForLoopNode}.
	 * 
	 * @param node
	 *            {@link ForLoopNode} currently being visited.
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Method that needs to be called when visitor is currently visiting
	 * {@link EchoNode}.
	 * 
	 * @param node
	 *            {@link EchoNode} currently being visited.
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Method that needs to be called when visitor is currently visiting
	 * {@link DocumentNode}.
	 * 
	 * @param node
	 *            {@link DocumentNode} currently being visited.
	 */
	public void visitDocumentNode(DocumentNode node);

}
