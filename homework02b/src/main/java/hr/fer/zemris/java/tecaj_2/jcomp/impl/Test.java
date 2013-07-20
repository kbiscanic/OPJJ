package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionCreator;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.tecaj_2.jcomp.parser.ProgramParser;

/**
 * Class used to test implementations created in this package.
 * 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class Test {

	private static final int DEFAULTMEM = 256;
	private static final int DEFAULTREGS = 16;

	/**
	 * Main method for this tester.
	 * 
	 * @param args
	 *            Unused.
	 * @throws Exception
	 *             In case of exception.
	 */
	public static void main(String[] args) throws Exception {
		Computer comp = new ComputerImpl(DEFAULTMEM, DEFAULTREGS);

		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions");

		ProgramParser.parse("examples/asmProgram2.txt", comp, creator);

		ExecutionUnit exec = new ExecutionUnitImpl();

		exec.go(comp);

	}

}
