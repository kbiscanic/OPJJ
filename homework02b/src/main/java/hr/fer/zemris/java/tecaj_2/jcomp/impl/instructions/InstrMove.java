package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class implementing {@link Instruction} interface and representing instruction
 * of moving (<code>Rx <- Ry</code>).
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class InstrMove implements Instruction {
	private int indexRegDest;
	private int indexRegSrc;

	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 2; i++) {
			if (!arguments.get(i).isRegister()) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i);
			}
		}
		this.indexRegDest = ((Integer) arguments.get(0).getValue()).intValue();
		this.indexRegSrc = ((Integer) arguments.get(1).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object arg = computer.getRegisters().getRegisterValue(indexRegSrc);
		computer.getRegisters().setRegisterValue(indexRegDest, arg);
		return false;
	}

}
