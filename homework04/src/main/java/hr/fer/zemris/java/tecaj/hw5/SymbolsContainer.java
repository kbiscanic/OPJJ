package hr.fer.zemris.java.tecaj.hw5;

public class SymbolsContainer {

	private char promptSymbol = '>';
	private char moreLinesSymbol = '\\';
	private char multiLinesSymbol = '|';

	public char getPromptSymbol() {
		return promptSymbol;
	}

	public void setPromptSymbol(char promptSymbol) {
		this.promptSymbol = promptSymbol;
	}

	public char getMoreLinesSymbol() {
		return moreLinesSymbol;
	}

	public void setMoreLinesSymbol(char moreLinesSymbol) {
		this.moreLinesSymbol = moreLinesSymbol;
	}

	public char getMultiLinesSymbol() {
		return multiLinesSymbol;
	}

	public void setMultiLinesSymbol(char multiLinesSymbol) {
		this.multiLinesSymbol = multiLinesSymbol;
	}

}
