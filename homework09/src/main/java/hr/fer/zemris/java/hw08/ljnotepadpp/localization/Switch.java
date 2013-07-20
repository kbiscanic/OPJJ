package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

import java.awt.event.ActionEvent;

public class Switch extends LocalizableAction {

	private static final long serialVersionUID = 3934471635256130924L;
	String language;

	public Switch(String key, ILocalizationProvider provider) {
		super(key, provider);
		if (key.equals("English")) {
			language = "en";
		} else if (key.equals("Croatian")) {
			language = "hr";
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(language);

	}

}
