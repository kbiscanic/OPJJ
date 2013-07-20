package hr.infinum.fer;

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

public class PhoneActivity extends Activity {
	private Button btnCall;
	private Button btnShow;
	private EditText etInput;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);

		btnCall = (Button) findViewById(R.id.btnCall);
		btnShow = (Button) findViewById(R.id.btnShow);
		etInput = (EditText) findViewById(R.id.etInput);
		btnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showCallDialog(etInput.getText().toString().trim());
			}
		});
		btnShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				displayAllContacts();
			}
		});

	}

	private void displayAllContacts() {
		
		final ArrayList<String> contacts = new ArrayList<String>();

		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER };

		Cursor people = getContentResolver().query(uri, projection, null, null, null);

		int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

		people.moveToFirst();
		do {
			String name = people.getString(indexName);
			String number = people.getString(indexNumber);
			contacts.add(name + " : " + number);
		} while (people.moveToNext());

		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(R.string.ok);
		builder.setItems(getContactsArray(contacts), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				showCallDialog(contacts.get(which).split(":")[1].trim());

			}
		});
		builder.show();

	}

	private CharSequence[] getContactsArray(ArrayList<String> list) {
		String[] array = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	protected void showCallDialog(final String number) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(R.string.app_name);
		builder.setMessage(String.format(getString(R.string.do_you_want_to_call), number));
		builder.setPositiveButton(R.string.app_name, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
				startActivity(intent);

			}
		});
		builder.setNegativeButton("Odustani", null);
		builder.show();
	}

}