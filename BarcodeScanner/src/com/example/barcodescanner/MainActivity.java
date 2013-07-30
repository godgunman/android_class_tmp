package com.example.barcodescanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String BARCODE_SCANNER_ACTION = "com.google.zxing.client.android.SCAN";
	private static final int BARCODE_SCANNER_CODE = 0;

	private Button button;
	private TextView resultTextView;
	private TextView formatTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		resultTextView = (TextView) findViewById(R.id.resultTextView);
		formatTextView = (TextView) findViewById(R.id.formatTextView);

		button = (Button) findViewById(R.id.scanButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				openScanner();
			}
		});
	}

	private boolean checkInstall() {
		Intent intent = new Intent();
		intent.setAction(BARCODE_SCANNER_ACTION);
		List<ResolveInfo> result = getPackageManager().queryIntentActivities(
				intent, PackageManager.MATCH_DEFAULT_ONLY);
		return result.size() != 0;
	}

	protected void openScanner() {
		if (checkInstall() == false) {
			downloadApk();
			return;
		}
		Intent intent = new Intent();
		intent.setAction(BARCODE_SCANNER_ACTION);
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, BARCODE_SCANNER_CODE);
	}

	private void downloadApk() {
		String url = "https://zxing.googlecode.com/files/BarcodeScanner4.4.apk";
		try {
			URL apkUrl = new URL(url);
			URLConnection connection = apkUrl.openConnection();
			InputStream is = connection.getInputStream();
			FileOutputStream fos = openFileOutput("BarcodeScanner4.4.apk",
					Context.MODE_PRIVATE);

			byte buffer[] = new byte[1024];
			int size;
			while ((size = is.read(buffer)) != -1) {
				fos.write(buffer, 0, size);
			}

			File file = new File("BarcodeScanner4.4.apk");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == BARCODE_SCANNER_CODE && resultCode == RESULT_OK) {
			String result = data.getStringExtra("SCAN_RESULT");
			String format = data.getStringExtra("SCAN_RESULT_FORMAT");

			resultTextView.setText(result);
			formatTextView.setText(format);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
