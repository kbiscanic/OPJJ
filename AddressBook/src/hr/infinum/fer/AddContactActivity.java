package hr.infinum.fer;

import hr.infinum.fer.kb46456.R;
import hr.infinum.fer.models.Contact;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity used to add new contact into our AddressBook.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class AddContactActivity extends Activity {

	private Button saveBtn;
	private Button cancelBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);

		saveBtn = (Button) findViewById(R.id.save);
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = ((EditText) findViewById(R.id.nameET)).getText()
						.toString();
				String phone = ((EditText) findViewById(R.id.phoneET))
						.getText().toString();
				String email = ((EditText) findViewById(R.id.emailET))
						.getText().toString();
				String fbprofile = ((EditText) findViewById(R.id.fbpET))
						.getText().toString();

				Contact contact = new Contact(name, phone, email, null, null,
						fbprofile);
				Intent intent = new Intent();
				intent.putExtra("contact", contact);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		cancelBtn = (Button) findViewById(R.id.cancel);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
