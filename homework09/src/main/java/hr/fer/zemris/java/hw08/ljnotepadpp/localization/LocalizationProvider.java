package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public final class LocalizationProvider extends AbstractLocalizationProvider {

	private static final LocalizationProvider INSTANCE = new LocalizationProvider();

	private String language;
	private ResourceBundle bundle;

	private LocalizationProvider() {
		super();
		language = "en";
		updateBundle();
	}

	public static LocalizationProvider getInstance() {
		return INSTANCE;
	}

	public void setLanguage(String language) {
		this.language = language;
		updateBundle();
		fire();
	}

	private void updateBundle() {
		bundle = ResourceBundle
				.getBundle(
						"hr.fer.zemris.java.hw08.ljnotepadpp.translations.translations",
						Locale.forLanguageTag(language));
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
