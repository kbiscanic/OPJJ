package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * {@link SmartScriptParser} is a class used to parse a string representing a
 * document. Document is divided into a tree of {@link Node}s representing it's
 * structure. Each node can hold 0 or more {@link Token}s representing
 * expressions.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class SmartScriptParser {
	private DocumentNode docuNode;
	private ObjectStack stack;
	private static final int THREE = 3;

	/**
	 * Public constructor for {@link SmartScriptParser} class.
	 * {@link SmartScriptParser} is accepting a string containing document body.
	 * All actual parsing work is delegated to another private method.
	 * 
	 * @param documentBody
	 *            String containing document body.
	 */
	public SmartScriptParser(final String documentBody) {
		smartScriptParserMethod(documentBody);
	}

	/**
	 * Public getter for {@link DocumentNode} property of
	 * {@link SmartScriptParser}.
	 * 
	 * @return {@link DocumentNode} representing mother-node for document being
	 *         parsed.
	 */
	public DocumentNode getDocumentNode() {
		return docuNode;
	}

	/**
	 * Private method doing the actual parsing work.
	 * 
	 * @param pdocumentBody
	 *            String containing document body.
	 */
	private void smartScriptParserMethod(final String pdocumentBody) {
		stack = new ObjectStack();
		this.docuNode = new DocumentNode();
		stack.push(docuNode);

		String documentBody = pdocumentBody;

		documentBody = documentBody.trim();
		while (documentBody.length() != 0) {
			if (documentBody.matches("\\[\\$\\s*[fF][oO][Rr].*")) {
				documentBody = parseForLoop(documentBody);
			} else if (documentBody.matches("\\[\\$\\s*=.*")) {
				documentBody = parseEcho(documentBody);
			} else if (documentBody.matches("\\[\\$[Ee][Nn][Dd].*")) {
				documentBody = parseEnd(documentBody);
			} else {
				documentBody = parseText(documentBody);
			}
		}
	}

	/**
	 * Private method parsing a chunk of string that begins with [$= ...
	 * 
	 * @param pinput
	 *            String containing a substring of document body.
	 * @return a substring of input string, containing unprocessed part of that
	 *         string.
	 */
	private String parseEcho(final String pinput) {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();

		String input = pinput;
		input = input.substring(input.indexOf("[$") + 2);
		input = input.trim();
		input = input.substring(1);
		input = input.trim();

		while (input.charAt(0) != '$' && input.charAt(1) != ']') {
			if (input.matches("[+-]?[0-9]*\\.[0-9]*.*")) {
				String str = input.substring(0,
						Math.min(input.indexOf(" "), input.indexOf("$")));
				double doubleValue = Double.parseDouble(str);
				col.add(new TokenConstantDouble(doubleValue));
			} else if (input.matches("[+-]?[0-9][0-9]*[^\\.].*")) {
				String str = input.substring(0,
						Math.min(input.indexOf(" "), input.indexOf("$")));
				int intValue = Integer.parseInt(str);
				col.add(new TokenConstantInteger(intValue));
			} else if (input.matches("[+\\-/*]\\s*.*")) {
				col.add(new TokenOperator(input.substring(0, 1)));
			} else if (input.matches("@[A-Za-z][A-Za-z0-9_]*.*")) {
				input = input.substring(1);
				StringBuilder strBuild = new StringBuilder();
				while (input.charAt(0) != ' ' && input.charAt(0) != '$') {
					strBuild.append(input.charAt(0));
					input = input.substring(1);
				}
				col.add(new TokenFunction(strBuild.toString()));
			} else if (input.matches("[A-Za-z][A-Za-z0-9_]*.*")) {
				StringBuilder strBuild = new StringBuilder();
				while (input.charAt(0) != ' ' && input.charAt(0) != '$') {
					strBuild.append(input.charAt(0));
					input = input.substring(1);
				}
				col.add(new TokenVariable(strBuild.toString()));
			} else if (input.charAt(0) == '\"') {
				input = input.substring(1);
				StringBuilder strBuild = new StringBuilder();
				while (input.charAt(0) != '\"') {
					if (input.charAt(0) == '\\') {
						strBuild.append(input.substring(0, 1));
						input = input.substring(1);
					}
					strBuild.append(input.charAt(0));
					input = input.substring(1);
				}

				col.add(new TokenString(strBuild.toString()));
			} else {
				throw new SmartScriptParserException();
			}
			while (input.charAt(0) != ' ' && input.charAt(0) != '$') {
				input = input.substring(1);
			}
			input = input.trim();

		}
		Token[] tok = new Token[col.size()];
		int temp = col.size();
		for (int i = 0; i < temp; i++) {
			tok[i] = (Token) col.get(0);
			col.remove(0);
		}
		((Node) stack.peek()).addChildNode(new EchoNode(tok));
		return input.substring(input.indexOf("$]") + 2).trim();
	}

	/**
	 * Private method parsing a chunk of string that begins with [$END ...
	 * 
	 * @param pinput
	 *            String containing a substring of document body.
	 * @return a substring of input string, containing unprocessed part of that
	 *         string.
	 */
	private String parseEnd(final String input) {
		stack.pop();
		return input.substring(input.indexOf("$]") + 2).trim();
	}

	/**
	 * Private method parsing a chunk of string that begins with [$FOR ...
	 * 
	 * @param pinput
	 *            String containing a substring of document body.
	 * @return a substring of input string, containing unprocessed part of that
	 *         string.
	 */
	private String parseForLoop(final String pinput) {

		TokenVariable var = null;
		Token[] tok = new Token[THREE];
		StringBuilder sb = new StringBuilder();
		String input = pinput;

		input = input.trim();

		input = input.substring(input.indexOf("[$") + 2);
		input = input.trim();
		input = input.substring(THREE);
		input = input.trim();

		if (input.matches("[A-Za-z][A-Za-z0-9_]*\\s*.*")) {
			while (input.charAt(0) != ' ') {
				sb.append(input.charAt(0));
				input = input.substring(1);
			}
		} else {
			throw new SmartScriptParserException();

		}
		var = new TokenVariable(sb.toString());

		for (int i = 0; i < THREE; i++) {
			input = input.trim();
			if (i == 2 && input.charAt(0) == '$') {
				tok[2] = null;
				break;
			}
			if (input.matches("[+-]?[0-9]*\\.[0-9]*.*")) {
				String str = input.substring(0,
						Math.min(input.indexOf(" "), input.indexOf("$")));
				double doubleValue = Double.parseDouble(str);
				tok[i] = new TokenConstantDouble(doubleValue);
			} else if (input.matches("[+-]?[0-9][0-9]*[^\\.].*")) {
				String str = input.substring(0,
						Math.min(input.indexOf(" "), input.indexOf("$")));
				int intValue = Integer.parseInt(str);
				tok[i] = new TokenConstantInteger(intValue);
			} else if (input.matches("[+\\-/*]\\s*.*")) {
				tok[i] = new TokenOperator(input.substring(0, 1));
			} else if (input.matches("@[A-Za-z][A-Za-z0-9_]*\\s.*")) {
				input = input.substring(1);
				StringBuilder strBuilder = new StringBuilder();
				while (input.charAt(0) != ' ') {
					strBuilder.append(input.charAt(0));
					input = input.substring(1);
				}
				tok[i] = new TokenFunction(strBuilder.toString());
			} else if (input.matches("[A-Za-z][A-Za-z0-9_]*.*")) {
				StringBuilder strBuilder = new StringBuilder();
				while (input.charAt(0) != ' ') {
					strBuilder.append(input.charAt(0));
					input = input.substring(1);
				}
				tok[i] = new TokenVariable(strBuilder.toString());
			} else if (input.charAt(0) == '\"') {
				input = input.substring(1);
				StringBuilder strBuilder = new StringBuilder();
				while (input.charAt(0) != '\"') {
					if (input.charAt(0) == '\\') {
						strBuilder.append(input.substring(0, 1));
						input = input.substring(1);
					}
					strBuilder.append(input.charAt(0));
					input = input.substring(1);
				}

				tok[i] = new TokenString(strBuilder.toString());
			} else {
				throw new SmartScriptParserException();
			}
			while (input.charAt(0) != ' ' && input.charAt(0) != '$') {
				input = input.substring(1);
			}
		}
		input = input.substring(input.indexOf("$]") + 2);

		ForLoopNode fln = new ForLoopNode(var, tok[0], tok[1], tok[2]);
		((Node) stack.peek()).addChildNode(fln);
		stack.push(fln);

		input = input.trim();
		return input;

	}

	/**
	 * Private method parsing a chunk of string that begins without [$ tag.
	 * 
	 * @param pinput
	 *            String containing a substring of document body.
	 * @return a substring of input string, containing unprocessed part of that
	 *         string.
	 */
	private String parseText(final String pinput) {
		StringBuilder sb = new StringBuilder();
		String input = pinput;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != '[') {
				sb.append(input.charAt(i));
			} else if (input.charAt(i - 1) == '\\') {
				sb.append(input.charAt(i));
			} else {
				((Node) stack.peek()).addChildNode(new TextNode((sb.toString()
						.trim())));
				return input.substring(i);
			}
		}
		((Node) stack.peek()).addChildNode(new TextNode(sb.toString()));
		return "";
	}

}
