package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class implementing {@link Instruction} interface and representing instruction
 * of halting the computer.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class InstrHalt implements Instruction {

	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
