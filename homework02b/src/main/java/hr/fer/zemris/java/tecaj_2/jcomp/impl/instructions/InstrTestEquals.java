package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Class implementing {@link Instruction} interface and representing instruction
 * of comparing (<code>flag=(Rx==Ry)</code>).
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {
	private int indexReg1;
	private int indexReg2;

	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 2; i++) {
			if (!arguments.get(i).isRegister()) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + i);
			}
		}
		this.indexReg1 = ((Integer) arguments.get(0).getValue()).intValue();
		this.indexReg2 = ((Integer) arguments.get(1).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object arg1 = computer.getRegisters().getRegisterValue(indexReg1);
		Object arg2 = computer.getRegisters().getRegisterValue(indexReg2);

		computer.getRegisters().setFlag(arg1.equals(arg2));

		return false;
	}
}
