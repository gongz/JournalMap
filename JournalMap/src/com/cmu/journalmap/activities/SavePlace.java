package com.cmu.journalmap.activities;

import com.cmu.journalmap.models.Place;
import com.cmu.journalmap.storage.Places;
import com.cmu.journalmap.utilities.AudioUtility;
import com.cmu.journalmap.utilities.PictureUtility;
import com.cmu.journalmap.utilities.PropertiesUtility;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
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
	private final static String TAG = "SavePlace";
	private Button savePlace = null;
	private EditText commentBlock = null;

	private Button recordAudio = null;
	private Button playAudio = null;
	private String audioLoc = "";

	private Button recordVideo = null;
	private String videoLoc = "";
	private Uri imageUri = null;
	private Place newPlace = null;

	private MediaRecorder mRecorder = null;
	private MediaPlayer mPlayer = null;

	private int[] currentCoordinates = { Integer.MAX_VALUE, Integer.MAX_VALUE };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addplace);

		savePlace = (Button) findViewById(R.id.bSaveButton);
		commentBlock = (EditText) findViewById(R.id.etComments);
		recordAudio = (Button) findViewById(R.id.bRecButton);
		playAudio = (Button) findViewById(R.id.bPlayButton);
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
		if (placePic != null) {
			Bitmap ThumbImage = PictureUtility.decodeSampledBitmapFromPath(
					getRealPathFromURI(imageUri), 400, 400);
			placePic.setImageBitmap(ThumbImage);
		}

		savePlace.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String picPath = getRealPathFromURI(imageUri);
				int[] tempCoords = PictureUtility
						.isCoordinatesValid(PictureUtility
								.getCoordsFromPhoto(picPath)) ? PictureUtility
						.getCoordsFromPhoto(picPath) : currentCoordinates;

				if (PictureUtility.isCoordinatesValid(tempCoords)) {
					newPlace = new Place(new GeoPoint(tempCoords[0],
							tempCoords[1]), "", "");
					newPlace.setAudioLocation(audioLoc);
					newPlace.setNote(commentBlock.getText().toString());
					newPlace.setVideoLocation(videoLoc);
					newPlace.setPhotoLocation(getRealPathFromURI(imageUri));
					Log.e(TAG, newPlace.toString());
					PropertiesUtility.writePlaceToFile(v.getContext(), newPlace);
					Intent intent = new Intent(v.getContext(),
							ActivityMap.class);
					Places.getItems().add(newPlace);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(v.getContext(),
							MainActivity.class);
					startActivityForResult(intent, 1);
				}
			}
		});

		recordAudio.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Button recBt = (Button) v;
				if (recBt.getText().equals(
						v.getResources().getString(R.string.rec_start_button))) {
					recBt.setText(v.getResources().getString(
							R.string.rec_stop_button));
					mRecorder = AudioUtility.getMediaRecorder();
					audioLoc = AudioUtility.startRecording(mRecorder);
				} else {
					recBt.setText(v.getResources().getString(
							R.string.rec_start_button));
					AudioUtility.stopRecording(mRecorder);
				}
			}
		});

		playAudio.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Button playBt = (Button) v;
				if (playBt.getText().equals(
						v.getResources().getString(R.string.rec_play_button))) {
					playBt.setText(v.getResources().getString(
							R.string.rec_stop_button));
					mPlayer = AudioUtility.startPlaying(audioLoc);
				} else {
					playBt.setText(v.getResources().getString(
							R.string.rec_play_button));
					AudioUtility.stopPlaying(mPlayer);
				}

			}
		});

		recordVideo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

			}
		});

	}

	void handleSendImage(Intent intent) {
		imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		if (imageUri != null) {
			// Update UI to reflect image being shared
			Log.i("Main", "Image is being shared");

			// check for EXIF data
			try {
				String picPath = getRealPathFromURI(imageUri);
				System.out.printf("Path is:", picPath);
				int[] tempCoords = PictureUtility.getCoordsFromPhoto(picPath);
				if ((tempCoords[0] == 0) || (tempCoords[1] == 0)) {
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				this.currentCoordinates[0] = data.getIntExtra("lag",
						Integer.MAX_VALUE);
				this.currentCoordinates[1] = data.getIntExtra("lon",
						Integer.MAX_VALUE);
			}
			if (resultCode == RESULT_CANCELED) {
				return;
			}
		}
	}
}
