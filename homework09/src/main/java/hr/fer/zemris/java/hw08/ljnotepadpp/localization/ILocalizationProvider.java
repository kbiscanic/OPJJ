package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

public interface ILocalizationProvider {

	void addLocalizationListener(ILocalizationListener l);

	void removeLocalizationListener(ILocalizationListener l);

	String getString(String key);

}
