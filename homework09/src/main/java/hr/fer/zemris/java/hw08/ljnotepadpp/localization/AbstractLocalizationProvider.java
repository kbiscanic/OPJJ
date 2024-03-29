package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements
		ILocalizationProvider {

	private List<ILocalizationListener> listeners;

	public AbstractLocalizationProvider() {
		super();
		listeners = new LinkedList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

	@Override
	public abstract String getString(String key);

}
