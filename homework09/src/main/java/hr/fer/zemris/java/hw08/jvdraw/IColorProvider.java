package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;

public interface IColorProvider {

	Color getCurrentColor();

	void addColorChangeListener(ColorChangeListener l);

	void removeColorChangeListener(ColorChangeListener l);
}
