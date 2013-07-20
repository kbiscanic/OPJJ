package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class implementing {@link Instruction} interface and representing instruction
 * of decrement (<code>Rx <- Rx - 1</code>).
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class InstrDecrement implements Instruction {
	private int indexReg;

	public InstrDecrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1");
		}
		this.indexReg = ((Integer) arguments.get(0).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object arg = computer.getRegisters().getRegisterValue(indexReg);
		Integer dest = ((Integer) arg).intValue() - 1;
		computer.getRegisters().setRegisterValue(indexReg, dest);
		return false;
	}
}
