package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
/**
 * Class implementing {@link Instruction} interface and representing instruction
 * of loading (<code>Rx <- location</code>).
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class InstrLoad implements Instruction {
	private int indexReg;
	private int memAdr;

	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1");
		}
		this.indexReg = ((Integer) arguments.get(0).getValue()).intValue();
		this.memAdr = ((Integer) arguments.get(1).getValue()).intValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexReg,
				computer.getMemory().getLocation(memAdr));
		return false;
	}

}
