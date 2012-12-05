package com.cmu.journalmap.activities;

import com.cmu.journalmap.models.Place;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowPlace extends Activity {
	Button backButton;
	TextView commentBlock;
	Button recordAudio;
	Button recordVideo;
	Uri imageUri;
	Place newPlace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showplace);
		
		backButton = (Button) findViewById(R.id.bBackButton);
		commentBlock = (TextView) findViewById(R.id.showComments);
		recordAudio = (Button) findViewById(R.id.bRecPlayButton);
		recordVideo = (Button) findViewById(R.id.bVideoPlayButton);		
		backButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}	
}
