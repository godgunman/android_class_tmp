package com.example.intentex;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.ByteArrayBuffer;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText phoneEditText;
	private EditText webEditText;
	private EditText emailEditText;
	private EditText smsEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		phoneEditText = (EditText) findViewById(R.id.phoneEditText);
		webEditText = (EditText) findViewById(R.id.webEditText);
		emailEditText = (EditText) findViewById(R.id.emailEditText);
		smsEditText = (EditText) findViewById(R.id.smsEditText);
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
		// Intent intent = new Intent(Intent.ACTION_CALL, uri);
		// startActivity(intent);
		
		String[] command = new String[] { 
				"am", "start", 
				"--user", "0", 
				"-a", Intent.ACTION_CALL, 
				"-d", uri.toString() };
		String result = execCommand(command);
		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
	}

	public void clickWebButton(View view) {
		String url = webEditText.getText().toString();
		Uri uri = Uri.parse("http://" + url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	public void clickEmailButton(View view) {
		String email = emailEditText.getText().toString();
		Uri uri = Uri.parse("mailto:" + email);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		startActivity(intent);
	}

	public void clickSmsButton(View view) {
		String phone = smsEditText.getText().toString();
		Uri uri = Uri.parse("smsto:" + phone);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", "hello world");
		startActivity(intent);
	}

	public void clickContactButton(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				ContactsContract.Contacts.CONTENT_URI);
		// Intent intent = new Intent(Intent.ACTION_VIEW,
		// People.CONTENT_URI);
		startActivity(intent);
	}

	public void sendBroadcast(View view) {
		Intent intent = new Intent();
		intent.setAction("com.example.intentex.broadcast");
		intent.putExtra("text", "hello");
		sendBroadcast(intent);
	}

	private String execCommand(String[] commands) {
		try {
			Process ps = Runtime.getRuntime().exec(commands);
			InputStream is = ps.getInputStream();
			byte[] buffer = new byte[1024];
			is.read(buffer);
			return new String(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
