package com.cmu.journalmap.activities;

import com.cmu.journalmap.models.Place;
import com.cmu.journalmap.utilities.PictureUtility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPlace extends Activity {
	Button backButton;
	TextView commentBlock;
	Button playAudio;
	Button playVideo;
	Uri imageUri;
	Place newPlace;
	ImageView placePic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showplace);
		
		
		backButton = (Button) findViewById(R.id.bBackButton);
		commentBlock = (TextView) findViewById(R.id.showComments);
		playAudio = (Button) findViewById(R.id.bRecPlayButton);
		playVideo = (Button) findViewById(R.id.bVideoPlayButton);
		placePic = (ImageView) findViewById(R.id.ShowPlacePic);		
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});	
		Bitmap ThumbImage = PictureUtility.decodeSampledBitmapFromPath(getIntent().getStringExtra("pictureLoc"), 400 , 400);		
		placePic.setImageBitmap(ThumbImage);		
		commentBlock.setText(getIntent().getStringExtra("note"));		
	}	
}
