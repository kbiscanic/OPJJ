package hr.fer.zemris.java.tecaj_2.jcomp.impl;

import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.ExecutionUnit;
import hr.fer.zemris.java.tecaj_2.jcomp.Instruction;

/**
 * Execution unit for given {@link Computer}. This is a class that is executing
 * actual program.
 *
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	/**
	 * Method executing a program contained in {@link MemoryImpl} of given
	 * {@link Computer}.
	 *
	 * @param computer
	 *            {@link Computer} in whose {@link Memory} the program is.
	 *
	 * @returns <code>true</code> if program ended as intended;
	 *          <code>false</code> if exception occurred.
	 */
	@Override
	public boolean go(Computer computer) {
		try {
			computer.getRegisters().setProgramCounter(0);
			while (true) {
				int programCounter = computer.getRegisters().getProgramCounter();
				Instruction currentInstr = (Instruction) computer.getMemory()
						.getLocation(programCounter);
				computer.getRegisters().incrementProgramCounter();

				if (currentInstr.execute(computer)) {
					break;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
