package com.example.contact;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.contactList);
		createContact();
	}

	private void createContact() {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);

		String[] from = new String[] { ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts._ID };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, from, to);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = (Cursor) parent.getItemAtPosition(position);
				int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
				long _id = c.getLong(idIndex);

				TextView idTextView = (TextView) view
						.findViewById(android.R.id.text2);
				long _id2 = Long.valueOf(idTextView.getText().toString());

				Intent intent = new Intent();
				intent.putExtra("id", _id);
				intent.setClass(MainActivity.this, PhoneActivity.class);
				startActivity(intent);
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
