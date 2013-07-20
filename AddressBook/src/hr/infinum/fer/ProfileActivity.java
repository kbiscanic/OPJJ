package hr.infinum.fer;

import java.io.IOException;

import hr.infinum.fer.kb46456.R;
import hr.infinum.fer.models.Contact;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity used to display information about selected contact.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class ProfileActivity extends Activity {

	private static final int CAMERA_REQUEST = 1234;

	private TextView nameTV;
	private TextView phoneTV;
	private TextView emailTV;
	private ImageView imageIV;
	private Button callBtn;
	private Button faceBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		final Contact contact = (Contact) getIntent().getExtras()
				.getSerializable("contact");

		nameTV = (TextView) findViewById(R.id.name);
		nameTV.setText(contact.getName());
		phoneTV = (TextView) findViewById(R.id.phone);
		phoneTV.setText(contact.getPhone());
		emailTV = (TextView) findViewById(R.id.email);
		emailTV.setText(contact.getEmail());
		imageIV = (ImageView) findViewById(R.id.image);
		if (contact.getImage() != null) {
			try {
				imageIV.setImageBitmap(BitmapFactory.decodeStream(getAssets()
						.open(contact.getImage())));
			} catch (IOException e) {
			}
		}
		imageIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMERA_REQUEST);
			}
		});

		callBtn = (Button) findViewById(R.id.call);
		callBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ contact.getPhone()));
				startActivity(intent);

			}
		});

		faceBtn = (Button) findViewById(R.id.face);
		faceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(contact.getFacebook_profile()));
				startActivity(intent);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap picture = (Bitmap) data.getExtras().get("data");
			imageIV.setImageBitmap(picture);
		}
	}

}
