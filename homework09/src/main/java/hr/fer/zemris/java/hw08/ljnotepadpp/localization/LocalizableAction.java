package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public abstract class LocalizableAction extends AbstractAction implements
		ILocalizationListener {

	private static final long serialVersionUID = 9057028847551657006L;
	private String key;
	private ILocalizationProvider provider;

	public LocalizableAction(String key, ILocalizationProvider provider) {
		super();
		this.key = key;
		this.provider = provider;
		provider.addLocalizationListener(this);
		putValue(NAME, provider.getString(key));
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	@Override
	public void localizationChanged() {
		putValue(NAME, provider.getString(key));
	}

}
