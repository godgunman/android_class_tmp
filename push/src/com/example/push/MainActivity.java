package com.example.push;

import java.util.ArrayList;
import java.util.Arrays;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.parse.ParseUser;
import com.parse.PushService;

public class MainActivity extends Activity {

	private EditText editText;
	private Button button;
	private TextView textView;
	private Spinner spinner;
	private CheckBox checkBox;
	public static LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "device_id" + getDeviceId(),
				MainActivity.class);

		spinner = (Spinner) findViewById(R.id.spinner1);
		textView = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText1);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
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
				if (checkBox.isChecked())
					push.setChannel("all");
				else
					push.setChannel("device_id" + id);
				// push.setMessage(editText.getText().toString());
				push.sendInBackground();
			}
		});

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				spinner.setEnabled(!isChecked);
			}
		});

		ParseUser user = ParseUser.getCurrentUser();

		textView.setText(String.format("Hi, %s (%s)", user.getUsername(),
				getDeviceId()));
		setDeviceId();

		// Intent intent = new Intent();
		// intent.setAction("com.example.UPDATE_STATUS");
		// sendBroadcast(intent);
	}

	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	private void setDeviceId() {
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> users, ParseException e) {

				List<String> data = new ArrayList<String>();
				for (ParseUser user : users) {
					data.add(user.getUsername());
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, data);
				spinner.setAdapter(adapter);
			}
		});
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
