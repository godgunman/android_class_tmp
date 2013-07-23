package com.example.simplepager;

import com.viewpagerindicator.CirclePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private ViewPager viewPager;
	private CirclePageIndicator indicator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);
	
		viewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager()));
		indicator.setViewPager(viewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class SimplePagerAdapter extends FragmentPagerAdapter {
		private String[] data = new String[]{"1", "2" , "3" ,"4", "5"};
		public SimplePagerAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int pos) {
			return SimpleFragment.newInstance(data[pos]);
		}
		@Override
		public int getCount() {
			return data.length;
		}
	}
}
