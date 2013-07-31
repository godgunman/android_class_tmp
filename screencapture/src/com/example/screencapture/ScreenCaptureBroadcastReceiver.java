package com.example.screencapture;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ScreenCaptureBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String text = intent.getStringExtra("text");

		Toast.makeText(context, "onReceive:" + text, Toast.LENGTH_SHORT).show();
	}
}
