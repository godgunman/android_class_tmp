package com.example.screencapture;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ScreenCaptureBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		// /system/bin/screencap -p screen.png
		File file = new File(context.getFilesDir(), 
				(new Date()).getTime()+"_screen.png");
		
		execCommand(new String[] { 
				"/system/bin/screencap", 
				"-p", file.toString() });
		
		Toast.makeText(context, "save into : " + file.toString() , 
				Toast.LENGTH_SHORT).show();
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
