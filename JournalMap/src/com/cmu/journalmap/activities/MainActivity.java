package com.cmu.journalmap.activities;

import droid.monkeys.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Kathy and Archer
 * 
 */
public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button but_pin = (Button) findViewById(R.id.main_button_add_loc_pin);
		Button but_exif = (Button) findViewById(R.id.main_button_add_loc_exif);
		Button but_nfc = (Button) findViewById(R.id.main_button_add_loc_nfc);
		Button but_gps = (Button) findViewById(R.id.main_button_add_loc_gps);
		
		but_pin.setOnClickListener(getOnClickListener(1));
		but_exif.setOnClickListener(getOnClickListener(0));
		but_nfc.setOnClickListener(getOnClickListener(0));
		but_gps.setOnClickListener(getOnClickListener(0));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private OnClickListener getOnClickListener(final int tap){
		
		OnClickListener listener  = new OnClickListener() {
			public void onClick(View view) {
				// show MapActivity
				
				Intent intent = new Intent(view.getContext(), ActivityMap.class);
				intent.putExtra("isTapAllowed",tap);
				startActivity(intent);
			}
		};
		return listener;
		
	}
}
