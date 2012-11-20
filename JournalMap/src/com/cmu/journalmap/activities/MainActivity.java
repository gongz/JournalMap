package com.cmu.journalmap.activities;

import com.cmu.journalmap.utilities.PictureUtility;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Kathy and Archer
 * 
 */
public class MainActivity extends Activity
{
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

		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null)
		{
			if ("text/plain".equals(type))
			{
				handleSendText(intent); // Handle text being sent
			} else if (type.startsWith("image/"))
			{
				handleSendImage(intent); // Handle single image being sent
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private OnClickListener getOnClickListener(final int tap) {

		OnClickListener listener = new OnClickListener()
		{
			public void onClick(View view) {
				// show MapActivity

				Intent intent = new Intent(view.getContext(), SavePlace.class);
				//intent.putExtra("isTapAllowed", tap);
				startActivity(intent);
			}
		};
		return listener;

	}

	void handleSendText(Intent intent) {
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (sharedText != null)
		{
			// Update UI to reflect text being shared
			Log.i("Main", "Text is being shared");
		}
	}

	void handleSendImage(Intent intent) {
		Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null)
		{
			// Update UI to reflect image being shared
			Log.i("Main", "Image is being shared");

			// check for EXIF data
			PictureUtility picUtil = new PictureUtility();
			try
			{
				String picPath = getRealPathFromURI(imageUri);
				System.out.printf("Path is:", picPath);
				double[] tempCoords = picUtil.getCoordsFromPhoto(picPath);
				if ((Double.isNaN(tempCoords[0]))
						|| (Double.isNaN(tempCoords[1])))
				{
					// EXIF Coords were NOT found
					Log.i("PictureUtlity", "EXIF Coords were NOT found");
					but_exif.setVisibility(View.INVISIBLE);
				} else
				{
					// EXIF Coords were found
					Log.i("PictureUtlity", "EXIF Coords were found!");
					but_pin.setVisibility(View.INVISIBLE);
					but_exif.setVisibility(View.INVISIBLE);
					but_nfc.setVisibility(View.INVISIBLE);
					but_gps.setVisibility(View.INVISIBLE);
					
					try
					{
						Class ourClass = Class
								.forName("");
						Intent ourIntent = new Intent(MainActivity.this,
								ourClass);
						Log.e("MAIN", "picPath: " + picPath);
						intent.putExtra("placePhotoPath", picPath);
						startActivity(ourIntent);
					} catch (ClassNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			} catch (Exception e)
			{
				Log.i("IMAGE Getter", "Could not get image");
			}
		}
	}

	// Source:
	// http://stackoverflow.com/questions/3401579/get-filename-and-path-from-uri-from-mediastore
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}