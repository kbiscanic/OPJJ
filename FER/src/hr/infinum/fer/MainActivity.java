package hr.infinum.fer;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btnDetails;
	private Button btnPhone;
	private Button btnList;
	private Button btnCamera;
	private Button btnAssets;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnDetails = (Button) findViewById(R.id.btnDetails);
		btnDetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
				intent.putExtra(DetailsActivity.EXTRAS_NAME, "nixa");
				startActivityForResult(intent, 100);
			}
		});

		btnPhone = (Button) findViewById(R.id.btnCall);
		btnPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
				startActivity(intent);
			}
		});

		btnList = (Button) findViewById(R.id.btnList);
		btnList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ListActivity.class);
				startActivityForResult(intent, 314);
			}
		});

		btnCamera = (Button) findViewById(R.id.btnCamera);
		btnCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivity(intent);
			}
		});

		btnAssets = (Button) findViewById(R.id.btnAssets);
		btnAssets.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AssetsActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100 && resultCode == RESULT_OK) {

			Builder b = new Builder(MainActivity.this);
			b.setMessage("Bok " + data.getStringExtra(DetailsActivity.DATA_NAME));
			b.show();
		} else if (requestCode == 314 && resultCode == RESULT_OK) {
			Builder b = new Builder(MainActivity.this);
			b.setMessage("Odabran " + data.getStringExtra(ListActivity.EXTRAS_PERSON));
			b.show();
		}
	}

}