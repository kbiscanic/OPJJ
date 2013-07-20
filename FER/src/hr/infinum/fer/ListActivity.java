package hr.infinum.fer;

import hr.infinum.fer.adapters.PeopleAdapter;

import hr.infinum.fer.models.Person;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListActivity extends Activity {

	public static String EXTRAS_PERSON = "extras_person";
	private ListView list;
	ArrayList<Person> persons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		persons = new ArrayList<Person>();
		persons.add(new Person("Nikola", "Kapraljevic"));
		persons.add(new Person("Netko", "Kojic"));
		persons.add(new Person("Marko", "Cupic"));
		persons.add(new Person("Infinum", "Rocks!"));

		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new PeopleAdapter(this, persons));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String item = persons.get(arg2).toString();
				Intent i = new Intent();
				i.putExtra(EXTRAS_PERSON, item);
				setResult(RESULT_OK, i);
				finish();

			}
		});
	}
}
