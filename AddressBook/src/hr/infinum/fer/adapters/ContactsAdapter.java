package hr.infinum.fer.adapters;

import java.util.ArrayList;

import hr.infinum.fer.models.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter class used to adapt {@link Contact} into listview.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {

	private ArrayList<Contact> contacts = new ArrayList<Contact>();

	public ContactsAdapter(Context context, ArrayList<Contact> objects) {
		super(context, android.R.layout.simple_list_item_2, objects);
		this.contacts = objects;
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Contact getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);
		}

		TextView text1 = (TextView) convertView
				.findViewById(android.R.id.text1);
		TextView text2 = (TextView) convertView
				.findViewById(android.R.id.text2);

		text1.setText(getItem(position).getName());
		text2.setText(getItem(position).getPhone() + ", "
				+ getItem(position).getEmail());

		return convertView;
	}

}
