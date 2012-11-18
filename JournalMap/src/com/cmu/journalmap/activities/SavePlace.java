package com.cmu.journalmap.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SavePlace extends Activity
{
	Button savePlace;
	EditText commentBlock;
	Button recordAudio;
	Button recordVideo;
	ImageView placePic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addplace);
		
		savePlace = (Button) findViewById(R.id.bSaveButton);
		commentBlock = (EditText) findViewById(R.id.etComments);
		recordAudio = (Button) findViewById(R.id.bRecButton);
		recordVideo = (Button) findViewById(R.id.bVideoButton);
		placePic = (ImageView) findViewById(R.id.ivPlacePic);
		
		savePlace.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	
}
