package hr.infinum.demo;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnCall;
	private Button btnDetails;
	private Button btnAssets;
	private Button btnCamera;
	private Button btnList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnCall = (Button) findViewById(R.id.btnCall);
		btnDetails = (Button) findViewById(R.id.btnDetails);
		btnAssets = (Button) findViewById(R.id.btnAssets);
		btnCamera = (Button) findViewById(R.id.btnCamera);
		btnList = (Button) findViewById(R.id.btnList);

		btnCall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CallActivity.class);
				startActivity(i);
			}
		});

		btnList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, ListActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
