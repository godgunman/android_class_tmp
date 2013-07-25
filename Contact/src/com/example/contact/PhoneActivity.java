package com.example.contact;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ListView;

public class PhoneActivity extends Activity {
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.contactList);

		long id = getIntent().getLongExtra("id", -1);
		createPhone(id);
	}

	private void createPhone(long id) {
		Uri uri = Phone.CONTENT_URI;
		String selection = Phone.CONTACT_ID + "=?";
		String[] selectionArgs = new String[] { String.valueOf(id) };

		Cursor cursor = getContentResolver().query(uri, null, selection,
				selectionArgs, null);

		String[] from = new String[] { Phone.NUMBER, Phone.TYPE };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, from, to);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
