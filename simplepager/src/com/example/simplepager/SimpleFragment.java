package com.example.simplepager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleFragment extends Fragment {
	private String content;
	
	public static SimpleFragment newInstance(String content) {
		SimpleFragment fragment = new SimpleFragment();
		fragment.content = content;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_text, null);
		TextView text = (TextView) main.findViewById(R.id.textView1);
		text.setText(content);
		return main;
	}
}
