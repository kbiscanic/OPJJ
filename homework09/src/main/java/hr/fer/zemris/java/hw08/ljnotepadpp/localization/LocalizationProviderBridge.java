package hr.fer.zemris.java.hw08.ljnotepadpp.localization;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationProvider parent;
	private ILocalizationListener l = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			LocalizationProviderBridge.this.fire();
		}
	};

	public LocalizationProviderBridge(ILocalizationProvider parent) {
		super();
		this.parent = parent;
		this.connected = false;
	}

	public void connect() {
		if (connected) {
			return;
		}
		connected = true;

		parent.addLocalizationListener(l);

	}

	public void disconnect() {
		if (!connected) {
			return;
		}
		parent.removeLocalizationListener(l);
		connected = false;

	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
