package com.cmu.journalmap.activities;

import java.io.File;

import com.cmu.journalmap.models.Place;
import com.cmu.journalmap.storage.Places;
import com.cmu.journalmap.utilities.PictureUtility;
import com.cmu.journalmap.utilities.PropertiesUtility;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SavePlace extends Activity {
	Button savePlace;
	EditText commentBlock;
	Button recordAudio;
	Button recordVideo;
	Uri imageUri;
	Place newPlace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addplace);

		savePlace = (Button) findViewById(R.id.bSaveButton);
		commentBlock = (EditText) findViewById(R.id.etComments);
		recordAudio = (Button) findViewById(R.id.bRecButton);
		recordVideo = (Button) findViewById(R.id.bVideoButton);

		ImageView placePic = (ImageView) findViewById(R.id.ivPlacePic);

		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if (type.startsWith("image/")) {
				handleSendImage(intent); // Handle single image being sent
			}
		}

		// String photoPath = getIntent().getStringExtra("placePhotoPath");

		Log.e("SAVEPLACE", "placePhotoPath: " + imageUri.toString());
		if (placePic != null) {
			placePic.setImageURI(imageUri);
		}

		savePlace.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				//Here~~ Kathy, we need the exif info
				//Don't create my house on the ocean.
				
				newPlace = new Place(new GeoPoint(7510486, -122059769),
						"Archer's house", "Coolest Place EVER");
				newPlace.setPhotoLocation(getRealPathFromURI(imageUri));
				//newPlace.setAudioLocation();
				//newPlace.setVideoLocation();
				
			    newPlace.setPhotoLocation(getRealPathFromURI(imageUri));
			    newPlace.setNote(commentBlock.getText().toString());
			    
			    PropertiesUtility pu = new PropertiesUtility();
			    pu.writePlaceToFile(v.getContext(), newPlace);

				Intent intent = new Intent(v.getContext(), ActivityMap.class);
				//Please check class: Places
				Places.getItems().add(newPlace);
				
				startActivity(intent);
			}
		});

	}

	void handleSendImage(Intent intent) {
		imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {
			// Update UI to reflect image being shared
			Log.i("Main", "Image is being shared");

			// check for EXIF data
			PictureUtility picUtil = new PictureUtility();
			try {
				String picPath = getRealPathFromURI(imageUri);
				System.out.printf("Path is:", picPath);
				double[] tempCoords = picUtil.getCoordsFromPhoto(picPath);
				if ((Double.isNaN(tempCoords[0]))
						|| (Double.isNaN(tempCoords[1]))) {
					// EXIF Coords were NOT found
					Log.i("PictureUtlity", "EXIF Coords were NOT found");

				} else {
					// EXIF Coords were found
					Log.i("PictureUtlity", "EXIF Coords were found!");
				}
			} catch (Exception e) {
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
