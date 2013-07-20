package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;

public interface ColorChangeListener {

	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
