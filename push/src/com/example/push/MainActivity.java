package com.example.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

public class MainActivity extends Activity {

	private EditText editText;
	private Button button;
	private TextView textView;
	private Spinner spinner;
	public static LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");
		// Parse.initialize(this, "hCJ3YM593qsoFGt3CNVa0XRECus3Vbrz56HdyUvD",
		// "WRUIsQkcyj0fgv8inoF6hSeo0rftbr2WTKPWLE09");

		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseAnalytics.trackAppOpened(getIntent());

		register();

		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "device_id" + getDeviceId(),
				MainActivity.class);

		spinner = (Spinner) findViewById(R.id.spinner1);
		textView = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText1);
		linearLayout = (LinearLayout) findViewById(R.id.messagesLinearLayout);
		button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String id = (String) spinner.getSelectedItem();
				JSONObject data = new JSONObject();
				try {
					data.put("action", "com.example.UPDATE_STATUS");
					data.put("device_id", getDeviceId());
					data.put("alert", editText.getText().toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ParsePush push = new ParsePush();
				push.setData(data);
				push.setChannel("device_id" + id);
				// push.setMessage(editText.getText().toString());
				push.sendInBackground();
			}
		});
		textView.setText(getDeviceId());
		setDeviceId();

		// Intent intent = new Intent();
		// intent.setAction("com.example.UPDATE_STATUS");
		// sendBroadcast(intent);
	}

	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	private void setDeviceId() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("info");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				Set<String> idSet = new HashSet<String>();
				for (ParseObject obj : objects) {
					if (obj.getString("device_id") != null) {
						idSet.add(obj.getString("device_id"));
					}
				}
				List<String> ids = new ArrayList<String>();
				for (String id : idSet) {
					ids.add(id);
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, ids);
				spinner.setAdapter(adapter);
			}
		});
	}

	private void register() {
		ParseObject object = new ParseObject("info");
		object.put("device_id", getDeviceId());
		object.saveInBackground();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_refresh) {
			setDeviceId();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
