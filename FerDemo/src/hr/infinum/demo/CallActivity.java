package hr.infinum.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CallActivity extends Activity {

	private EditText etInputNumber;
	private Button btnCall;
	private Button btnShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);

		etInputNumber = (EditText) findViewById(R.id.etInputNumber);
		btnCall = (Button) findViewById(R.id.btnCall);
		btnShow = (Button) findViewById(R.id.btnShow);

		btnCall.setOnClickListener(callListener());
		btnShow.setOnClickListener(showListener());
	}

	private OnClickListener showListener() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<String> contacts = new ArrayList<String>();
				Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
				String[] projection = new String[] {
						ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
						ContactsContract.CommonDataKinds.Phone.NUMBER };

				Cursor people = getContentResolver().query(uri, projection,
						null, null, null);

				int indexName = people
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
				int indexNumber = people
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

				people.moveToFirst();

				do {
					String name = people.getString(indexName);
					String number = people.getString(indexNumber);
					contacts.add(name + ":" + number);
				} while (people.moveToNext());

				AlertDialog.Builder builder = new Builder(CallActivity.this);
				builder.setTitle(R.string.app_name);
				builder.setItems(getContactsArray(contacts),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								showCallDialog("1");

							}
						});
				builder.show();

			}
		};
	}

	private CharSequence[] getContactsArray(ArrayList<String> list) {
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	private OnClickListener callListener() {
		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showCallDialog(etInputNumber.getText().toString());

			}

		};
	}

	protected void showCallDialog(final String number) {
		AlertDialog.Builder builder = new Builder(CallActivity.this);
		builder.setTitle(R.string.app_name);
		builder.setMessage("Zelite li obaviti razgovor?");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ number));
				startActivity(i);

			}
		});
		builder.setCancelable(false);
		builder.setNegativeButton("Odustani", null);
		builder.show();
	}
}
