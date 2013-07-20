package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class used to execute Smart Script.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class SmartScriptEngine {

	/**
	 * Node representing document we are executing.
	 */
	private DocumentNode documentNode;
	/**
	 * Request context for executing given script.
	 */
	private RequestContext requestContext;
	/**
	 * Multistack helping us execute given script.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Visitor object used to visit document parsed by {@link SmartScriptParser}
	 * .
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getString());
			} catch (IOException e) {
				System.err.println("Error printing text.. Trying again..");
				node.accept(this);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			try {
				String variable = node.getVariable().getName();
				Token step = node.getStepExpression();
				if (step == null) {
					step = new TokenConstantInteger(1);
				}
				multistack.push(variable, new ValueWrapper(node
						.getStartExpression().getValue()));
				while (multistack.peek(variable).numCompare(
						node.getEndExpression().getValue()) <= 0) {
					int cnt = node.numberOfChildren();
					for (int i = 0; i < cnt; i++) {
						node.getChild(i).accept(this);
					}
					ValueWrapper current = multistack.pop(variable);
					current.increment(step.getValue());
					multistack.push(variable, current);
				}
				multistack.pop(variable);
			} catch (Exception ex) {
				System.err.println("Syntax error.");
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			try {
				Stack<Object> tempStack = new Stack<>();
				Token[] tokens = node.getTokens();
				for (Token t : tokens) {
					if (t instanceof TokenConstantInteger
							|| t instanceof TokenConstantDouble
							|| t instanceof TokenString) {
						tempStack.push(t.getValue());
					} else if (t instanceof TokenVariable) {
						tempStack.push(multistack.peek(
								((TokenVariable) t).getName()).getValue());
					} else if (t instanceof TokenOperator) {
						ValueWrapper o2 = new ValueWrapper(tempStack.pop());
						ValueWrapper o1 = new ValueWrapper(tempStack.pop());
						String operation = ((TokenOperator) t).getSymbol();
						switch (operation) {
						case "+":
							o1.increment(o2.getValue());
							tempStack.push(o1.getValue());
							break;
						case "-":
							o1.decrement(o2.getValue());
							tempStack.push(o1.getValue());
							break;
						case "*":
							o1.multiply(o2.getValue());
							tempStack.push(o1.getValue());
							break;
						case "/":
							o1.divide(o2.getValue());
							tempStack.push(o1.getValue());
							break;
						default:
							throw new IllegalArgumentException(
									"Unsupported operation.");

						}
					} else if (t instanceof TokenFunction) {
						String function = ((TokenFunction) t).getName();
						switch (function) {
						case "sin":
							sin(tempStack);
							break;
						case "decfmt":
							decfmt(tempStack);
							break;
						case "dup":
							dup(tempStack);
							break;
						case "swap":
							swap(tempStack);
							break;
						case "setMimeType":
							setMimeType(tempStack);
							break;

						case "paramGet":
							paramGet(tempStack);
							break;

						case "pparamGet":
							pparamGet(tempStack);
							break;

						case "pparamSet":
							pparamSet(tempStack);
							break;
						case "pparamDel":
							pparamDel(tempStack);
							break;
						case "tparamGet":
							tparamGet(tempStack);
							break;

						case "tparamSet":
							tparamSet(tempStack);
							break;
						case "tparamDel":
							tparamDel(tempStack);
							break;

						default:
							break;
						}

					}
				}

				Stack<Object> stack = new Stack<>();
				while (!tempStack.isEmpty()) {
					stack.push(tempStack.pop());
				}

				while (!stack.isEmpty()) {
					requestContext.write(stack.pop().toString());
				}

			} catch (IOException ex) {
				System.err.println("Syntax error in Echo Node.");
			}
		}

		/**
		 * Method used to execute <code>tParamDel</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void tparamDel(Stack<Object> tempStack) {
			String name = (String) tempStack.pop();
			requestContext.removeTemporaryParameter(name);

		}

		/**
		 * Method used to execute <code>tParamSet</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void tparamSet(Stack<Object> tempStack) {
			String name = (String) tempStack.pop();
			Object value = tempStack.pop();
			requestContext.setTemporaryParameter(name, value.toString());
		}

		/**
		 * Method used to execute <code>tParamGet</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void tparamGet(Stack<Object> tempStack) {
			Object defValue = tempStack.pop();
			String name = (String) tempStack.pop();
			Object value = requestContext.getTemporaryParameter(name);
			tempStack.push(value == null ? defValue : value);

		}

		/**
		 * Method used to execute <code>pParamDel</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void pparamDel(Stack<Object> tempStack) {
			String name = (String) tempStack.pop();
			requestContext.removePersistentParameter(name);

		}

		/**
		 * Method used to execute <code>pParamSet</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void pparamSet(Stack<Object> tempStack) {
			String name = (String) tempStack.pop();
			Object value = tempStack.pop();
			requestContext.setPersistentParameter(name, value.toString());
		}

		/**
		 * Method used to execute <code>pParamGet</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void pparamGet(Stack<Object> tempStack) {
			Object defValue = tempStack.pop();
			String name = (String) tempStack.pop();
			Object value = requestContext.getPersistentParameter(name);
			tempStack.push(value == null ? defValue : value);

		}

		/**
		 * Method used to execute <code>pParamGet</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void paramGet(Stack<Object> tempStack) {
			Object defValue = tempStack.pop();
			String name = (String) tempStack.pop();
			Object value = requestContext.getParameter(name);
			tempStack.push(value == null ? defValue : value);
		}

		/**
		 * Method used to execute <code>setMimeType</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void setMimeType(Stack<Object> tempStack) {
			String x = (String) tempStack.pop();
			requestContext.setMimeType(x);
		}

		/**
		 * Method used to execute <code>swap</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void swap(Stack<Object> tempStack) {
			Object x = tempStack.pop();
			Object y = tempStack.pop();
			tempStack.push(x);
			tempStack.push(y);
		}

		/**
		 * Method used to execute <code>dup</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void dup(Stack<Object> tempStack) {
			Object x = tempStack.peek();
			tempStack.push(x);

		}

		/**
		 * Method used to execute <code>decfmt</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void decfmt(Stack<Object> tempStack) {
			String form = (String) tempStack.pop();
			ValueWrapper x = new ValueWrapper(tempStack.pop());
			double xval = readValue(x);

			DecimalFormat df = new DecimalFormat(form);
			tempStack.push(df.format(xval));

		}

		/**
		 * Method used to execute <code>readValue</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private double readValue(ValueWrapper x) {
			double val;
			if (x.getType() == ValueWrapper.Type.INT) {
				val = (int) x.getValue();
			} else {
				val = (double) x.getValue();
			}
			return val;
		}

		/**
		 * Method used to execute <code>sin</code> function.
		 * 
		 * @param tempStack
		 *            Temporary stack used to execute this function.
		 */
		private void sin(Stack<Object> tempStack) {
			ValueWrapper x = new ValueWrapper(tempStack.pop());
			double val = readValue(x);
			x.setValue(Math.sin(Math.toRadians(val)));
			tempStack.push(x.getValue());

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int cnt = node.numberOfChildren();
			for (int i = 0; i < cnt; i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * Default constructor for this class.
	 * 
	 * @param documentNode
	 *            Document node.
	 * @param requestContext
	 *            Request context.
	 */
	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Method that needs to be called when we want to execute given script.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
