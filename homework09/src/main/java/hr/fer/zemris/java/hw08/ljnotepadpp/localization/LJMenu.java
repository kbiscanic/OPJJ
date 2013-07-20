package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

import javax.swing.JMenu;

public class LJMenu extends JMenu implements ILocalizationListener {

	private static final long serialVersionUID = -943899579183604268L;
	private String key;
	private ILocalizationProvider provider;

	public LJMenu(String key, ILocalizationProvider provider) {
		super();
		this.key = key;
		this.provider = provider;
		provider.addLocalizationListener(this);
		setText(provider.getString(key));
	}

	@Override
	public void localizationChanged() {
		setText(provider.getString(key));
	}
}
