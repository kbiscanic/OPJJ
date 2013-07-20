package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;

/**
 * Class implementing {@link Instruction} interface and representing instruction
 * of adding (<code>Rx <- Ry + Rz</code>).
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class InstrAdd implements Instruction {
	private int indexReg1;
	private int indexReg2;
	private int indexReg3;

	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister()) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i);
			}
		}
		this.indexReg1 = ((Integer) arguments.get(0).getValue()).intValue();
		this.indexReg2 = ((Integer) arguments.get(1).getValue()).intValue();
		this.indexReg3 = ((Integer) arguments.get(2).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object arg1 = computer.getRegisters().getRegisterValue(indexReg2);
		Object arg2 = computer.getRegisters().getRegisterValue(indexReg3);
		computer.getRegisters().setRegisterValue(
				indexReg1,
				Integer.valueOf(((Integer) arg1).intValue()
						+ ((Integer) arg2).intValue()));
		return false;
	}

}
