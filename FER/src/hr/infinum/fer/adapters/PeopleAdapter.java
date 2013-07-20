/**
 * 
 */
package hr.infinum.fer.adapters;

import java.util.ArrayList;

import hr.infinum.fer.models.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Ana Baotić
 * 
 */
public class PeopleAdapter extends ArrayAdapter<Person> {

	private ArrayList<Person> people = new ArrayList<Person>();

	public PeopleAdapter(Context context, ArrayList<Person> objects) {
		super(context, android.R.layout.simple_list_item_2, objects);
		this.people = objects;
	}

	@Override
	public int getCount() {
		return people.size();
	}

	@Override
	public Person getItem(int position) {
		return people.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
		}

		TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
		TextView text2 = (TextView) convertView.findViewById(android.R.id.text2);

		text1.setText(getItem(position).getFirstname());
		text2.setText(getItem(position).getLastname());

		return convertView;
	}
}
