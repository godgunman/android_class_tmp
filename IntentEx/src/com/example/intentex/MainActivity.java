package com.example.intentex;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText phoneEditText;
	private EditText webEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		phoneEditText = (EditText) findViewById(R.id.phoneEditText);
		webEditText = (EditText) findViewById(R.id.webEditText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clickPhoneButton(View view) {
		String phone = phoneEditText.getText().toString();
		Uri uri = Uri.parse("tel:" + phone);
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		startActivity(intent);
	}

	public void clickWebButton(View view) {
		String url = webEditText.getText().toString();
		Uri uri = Uri.parse("http://" + url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	public void clickEmailButton(View view) {

	}

}
