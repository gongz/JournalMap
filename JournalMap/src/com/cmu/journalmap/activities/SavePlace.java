package com.cmu.journalmap.activities;

import java.io.File;

import com.cmu.journalmap.models.Place;
import com.cmu.journalmap.utilities.PictureUtility;
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

public class SavePlace extends Activity
{
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
		
		ImageView placePic= (ImageView) findViewById(R.id.ivPlacePic);
		
		// Get intent, action and MIME type
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null)
		{
			if (type.startsWith("image/"))
			{
				handleSendImage(intent); // Handle single image being sent
			}
		}
		
		
		//String photoPath = getIntent().getStringExtra("placePhotoPath");
		
		//Log.e("SAVEPLACE", "placePhotoPath: " + imageUri.toString());
		if(placePic!=null)
		placePic.setImageURI(imageUri);	


		
		
		savePlace.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    newPlace = new Place(new GeoPoint(37510486, -122059769), "Archer's house", "Coolest Place EVER");
				Intent intent = new Intent(v.getContext(), ActivityMap.class);
				intent.putExtra("isTapAllowed", 0);
				intent.putExtra("MyClass", newPlace); 
				startActivity(intent);
			}
		});

	}
	
	void handleSendImage(Intent intent) {
		imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
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

				} else
				{
					// EXIF Coords were found
					Log.i("PictureUtlity", "EXIF Coords were found!");
					//but_pin.setVisibility(View.INVISIBLE);

//					try
//					{
//						Class ourClass = Class
//								.forName("");
//						Intent ourIntent = new Intent(SavePlace.this,
//								ourClass);
//						Log.e("MAIN", "picPath: " + picPath);
//						//intent.putExtra("placePhotoPath", picPath);
//						startActivity(ourIntent);
//					} catch (ClassNotFoundException e)
//					{
//						e.printStackTrace();
//					}
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
