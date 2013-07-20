package hr.fer.zemris.java.hw1;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class used to test {@link SmartScriptParser}.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public final class SmartScriptTester {

	/**
	 * Private constructor for {@link SmartScriptTester}.
	 */
	private SmartScriptTester() {

	}

	/**
	 * Main method for {@link SmartScriptTester} class. Calls
	 * {@link SmartScriptParser} to parse a document, then recreates that
	 * document from parsed node tree.
	 * 
	 * @param args
	 *            is unused.
	 */
	public static void main(final String[] args) {
		String docBody = "This is sample text.\\r\\n[$ FOR i 1 10 1 $]\\r\\n This is [$= i $]-th time this message is generated.\\r\\n[$END$]\\r\\n[$FOR i 0 10 2 $]\\r\\n sin([$=i$]^2) = [$= i i * @sin \"0.000\" @decfmt $]\\r\\n[$ENDS$]";
		SmartScriptParser parser = null;

		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out
					.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		final DocumentNode document = parser.getDocumentNode();
		String origDocBdy = createOriginalDocumentBody(document);
		System.out.println(origDocBdy);
	}

	/**
	 * Method used to recreate parsed document from node tree. Designed for
	 * recursive use.
	 * 
	 * @param doc
	 *            Current {@link Node}
	 * @return Document created from current {@link Node}.
	 */
	private static String createOriginalDocumentBody(final Node doc) {
		StringBuilder body = new StringBuilder();
		body.append(doc.getString());
		for (int i = 0; i < doc.numberOfChildren(); i++) {
			body.append(createOriginalDocumentBody(doc.getChild(i)));
		}
		if (doc.needsEnd()) {
			body.append("[$END$]");
		}
		return body.toString();
	}
}
