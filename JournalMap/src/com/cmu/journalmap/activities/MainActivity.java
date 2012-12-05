package com.cmu.journalmap.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Kathy and Archer
 * 
 */
public class MainActivity extends Activity {
	public static final String TAG = MainActivity.class.getName();
	Button but_pin;
	Button but_exif;
	Button but_nfc;
	Button but_gps;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		but_pin = (Button) findViewById(R.id.main_button_add_loc_pin);
		but_exif = (Button) findViewById(R.id.main_button_add_loc_exif);
		but_nfc = (Button) findViewById(R.id.main_button_add_loc_nfc);
		but_gps = (Button) findViewById(R.id.main_button_add_loc_gps);

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

	private OnClickListener getOnClickListener(final int tap) {
		OnClickListener listener = new OnClickListener() {
			public void onClick(View view) {
				// show MapActivity
				Intent intent = new Intent(view.getContext(), ActivityMap.class);
				intent.putExtra("isTapAllowed", tap);
				//startActivity(intent);
				startActivityForResult(intent,1);
			}
		};
		return listener;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {				
				Intent returnIntent = new Intent();
				returnIntent.putExtra("lag", data.getIntExtra("lag", Integer.MAX_VALUE));
				returnIntent.putExtra("lon", data.getIntExtra("lon", Integer.MAX_VALUE));
				Log.e(TAG,data.getIntExtra("lag", Integer.MAX_VALUE)+" "+data.getIntExtra("lon", Integer.MAX_VALUE));
				setResult(Activity.RESULT_OK, returnIntent);
				finish();
			}
			if (resultCode == RESULT_CANCELED) {
				return;
			}
		}
	}

}