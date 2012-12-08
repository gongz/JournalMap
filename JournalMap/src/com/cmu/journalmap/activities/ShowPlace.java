package com.cmu.journalmap.activities;


import com.cmu.journalmap.utilities.AudioUtility;
import com.cmu.journalmap.utilities.PictureUtility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPlace extends Activity {
	private Button backButton = null;
	private TextView commentBlock = null;
	private Button playAudio = null;
	private Button playVideo = null;	
	private ImageView placePic = null;	
	private MediaPlayer mPlayer = null;

	
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
		
		if(getIntent().getStringExtra("pictureLoc").isEmpty()){
			placePic.setImageResource(R.drawable.jm_logo);
		} else {
			Bitmap ThumbImage = PictureUtility.decodeSampledBitmapFromPath(getIntent().getStringExtra("pictureLoc"), 400 , 400);		
			placePic.setImageBitmap(ThumbImage);	
		}
		
			
		commentBlock.setText(getIntent().getStringExtra("note"));
		
		if(getIntent().getStringExtra("audioLoc").length() <= 5){
			playAudio.setVisibility(View.GONE);
		}
		if(getIntent().getStringExtra("videoLoc").length() <= 5){
			playVideo.setVisibility(View.GONE);
		}
		
		playAudio.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (playAudio.getText().equals(
						v.getResources().getString(R.string.rec_play_button))) {
					playAudio.setText(v.getResources().getString(
							R.string.rec_stop_button));					
					mPlayer = AudioUtility.startPlaying(getIntent().getStringExtra("audioLoc"));
				} else {
					playAudio.setText(v.getResources().getString(
							R.string.rec_play_button));
					AudioUtility.stopPlaying(mPlayer);
				}

			}
		});
		
		playVideo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (getIntent().getStringExtra("videoLoc").length() > 5) {
					Intent intent = new Intent(v.getContext(), PlayVideo.class);
					intent.putExtra("videoLoc", getIntent().getStringExtra("videoLoc"));
					startActivityForResult(intent, 0);
				}
			}
		});
	}	
}
