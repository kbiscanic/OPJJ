package hr.infinum.fer.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class FerPreferences {

	private static final String LAST_CALLED_NUMBER = "LAST_CALLED_NUMBER";
	private Context mContext;

	public void setLastCalledNumber(String number) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(LAST_CALLED_NUMBER, number);
		editor.commit();
	}

	public String getLastCalledNumber() {
		return getSharedPreferences().getString(LAST_CALLED_NUMBER, "");
	}

	public FerPreferences(Context context) {
		this.mContext = context;
	}

	public SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(mContext);
	}

}
