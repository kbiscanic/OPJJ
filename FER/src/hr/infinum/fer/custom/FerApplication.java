package hr.infinum.fer.custom;

import com.crittercism.app.Crittercism;

import android.app.Application;

public class FerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Crittercism.init(getApplicationContext(), "51d24a691386205269000002");
	}
}
