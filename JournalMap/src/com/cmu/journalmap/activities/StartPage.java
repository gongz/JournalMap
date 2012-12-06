package com.cmu.journalmap.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartPage extends Activity
{
	private Button viewMapButton = null;
	private Button viewPhotosButton = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscreen);
		viewMapButton = (Button) findViewById(R.id.viewMapButton);
		viewPhotosButton = (Button) findViewById(R.id.viewPhotosButton);
		
		viewMapButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					Class ourClass = Class.forName("com.cmu.journalmap.activities.ActivityMap");
					Intent ourIntent = new Intent(StartPage.this, ourClass);
					startActivity(ourIntent);
					}catch (ClassNotFoundException e){
						e.printStackTrace();
					}
			}
		});
		
		viewPhotosButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					Class ourClass = Class.forName("com.cmu.journalmap.activities.PhotoGallery");
					Intent ourIntent = new Intent(StartPage.this, ourClass);
					startActivity(ourIntent);
					}catch (ClassNotFoundException e){
						e.printStackTrace();
					}
			}
		});
	}
	
	
}
