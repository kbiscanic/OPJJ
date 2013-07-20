package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Class implementing {@link Instruction} interface and representing instruction
 * of jumping (<code>PC <- location</code>).
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class InstrJump implements Instruction {
	private int memAdr;

	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		this.memAdr = ((Integer) arguments.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(memAdr);
		return false;
	}

}
