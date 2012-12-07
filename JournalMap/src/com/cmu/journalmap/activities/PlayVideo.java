package com.cmu.journalmap.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;


/**
 * @author Kathy and Archer
 * 
 */
public class PlayVideo extends Activity {
	public static final String TAG = PlayVideo.class.getName();
	private Button but_back;
	private VideoView vdDisplay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playvideo);
		
		but_back = (Button) findViewById(R.id.bBackButton);
		vdDisplay = (VideoView) findViewById(R.id.videoDisplay);
		vdDisplay.setMediaController(new MediaController(this));
		vdDisplay.setVideoPath(getIntent().getStringExtra("videoLoc"));
		vdDisplay.start();
		but_back.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
	}
}