package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {

	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
				super.windowOpened(e);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
				super.windowClosed(e);
			}

		});
	}

}
