package com.example.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

public class LoginActivity extends Activity {

	private EditText accountEditText;
	private EditText passwordEditText;
	private Button signupButton;
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Parse.initialize(this, "6GIweBfY6S45aUHHhzAkw4cgo6Cb7PlvUyYYwJFs",
				"nEFIK6PmEiidO3qnyvPa04WCi9rJCECOvN8qg5vf");

		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		ParseAnalytics.trackAppOpened(getIntent());

		accountEditText = (EditText) findViewById(R.id.accountEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		loginButton = (Button) findViewById(R.id.loginButton);
		signupButton = (Button) findViewById(R.id.signupButton);

		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String account = accountEditText.getText().toString();
				String passwrod = passwordEditText.getText().toString();
				Log.d("debug", "login:"+account);

				ParseUser.logInInBackground(account, passwrod,
						new LogInCallback() {
							@Override
							public void done(ParseUser user,
									com.parse.ParseException e) {
								if (user != null && e == null) {
									goToMainActivity();
								}
								if (e != null) {
									e.printStackTrace();
								}
							}
						});
			}
		});
		signupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String account = accountEditText.getText().toString();
				String passwrod = passwordEditText.getText().toString();
				
				Log.d("debug", "signup:"+account);

				final ParseUser user = new ParseUser();
				user.setUsername(account);
				user.setPassword(passwrod);

				user.signUpInBackground(new SignUpCallback() {
					@Override
					public void done(com.parse.ParseException e) {
						if (e == null) {
							goToMainActivity();
						} else {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}

	private void goToMainActivity() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}

}
