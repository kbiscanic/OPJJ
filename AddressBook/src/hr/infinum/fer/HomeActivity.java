package hr.infinum.fer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import hr.infinum.fer.adapters.ContactsAdapter;
import hr.infinum.fer.kb46456.R;
import hr.infinum.fer.models.Contact;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Home activity for our AddressBook application. Displays button for adding new
 * contacts and list of existing contacts.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class HomeActivity extends Activity {

	private static final int ADD_REQUEST = 12345;

	private Button addBtn;
	private ListView listView;
	protected static ArrayList<Contact> contacts = new ArrayList<Contact>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		addBtn = (Button) findViewById(R.id.btnAdd);
		addBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						AddContactActivity.class);
				startActivityForResult(intent, ADD_REQUEST);

			}
		});

		if (contacts.isEmpty()) {
			AssetManager am = getAssets();

			try {
				JsonReader reader = new JsonReader(new InputStreamReader(
						am.open("people.json")));

				Gson myGson = new Gson();

				JsonParser parser = new JsonParser();
				JsonArray contacts_array = parser.parse(reader)
						.getAsJsonArray();

				for (JsonElement contact : contacts_array) {
					Contact newContact = myGson
							.fromJson(contact, Contact.class);
					contacts.add(newContact);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new ContactsAdapter(this, contacts));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Contact item = contacts.get(arg2);
				Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
				i.putExtra("contact", item);
				startActivity(i);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
			Contact contact = (Contact) data.getExtras().getSerializable(
					"contact");
			contacts.add(contact);
			listView.requestLayout();
		}
	}

}
