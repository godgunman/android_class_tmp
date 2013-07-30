package com.example.intentex;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clickPhoneButton(View view) {
		Uri uri = Uri.parse("tel:886233663366");
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		startActivity(intent);
	}

	public void clickWebButton(View view) {

	}

	public void clickEmailButton(View view) {

	}

}
