package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * {@link Node} is a class used for representation of structured documents.
 * Every {@link Node} object can have 0 or more children in internally managed
 * collection.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public abstract class Node {

	private ArrayBackedIndexedCollection collection;

	/**
	 * Default constructor for {@link Node} class objects.
	 */
	public Node() {
		this.collection = null;
	}

	/**
	 * Method used to return String representation of current Node.
	 * 
	 * @return String representation.
	 */
	public String getString() {
		return "";
	}

	/**
	 * Method used to add given child to a collection of children. An instance
	 * of {@link ArrayBackedIndexedCollection} is used for that purpose.
	 * 
	 * @param child
	 *            {@link Node} that needs to be added as a child of current
	 *            {@link Node}.
	 */
	public void addChildNode(final Node child) {
		if (this.collection == null) {
			this.collection = new ArrayBackedIndexedCollection();
		}

		collection.add(child);

	}

	/**
	 * Method used to return a number of direct children for a current
	 * {@link Node}.
	 * 
	 * @return Number of direct children.
	 */
	public int numberOfChildren() {
		int childs = 0;
		if (this.collection != null) {
			childs = this.collection.size();
		}
		return childs;
	}

	/**
	 * Method used to return selected child from internal collection.
	 * 
	 * @param index
	 *            Index of a child.
	 * @return {@link Node} Child stored at given index.
	 * @throws IllegalArgumentException
	 *             if there is no child at given index.
	 */
	public Node getChild(final int index) {
		if (this.collection == null) {
			throw new IllegalArgumentException();
		}
		return (Node) collection.get(index);
	}

	/**
	 * Method used to determine whether current node needs an END tag.
	 * 
	 * @return <code>true</code> if END tag is required; <code>false</code>
	 *         otherwise.
	 */
	public boolean needsEnd() {
		return false;
	}

	public abstract void accept(INodeVisitor visitor);

}
