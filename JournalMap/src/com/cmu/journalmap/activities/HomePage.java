package com.cmu.journalmap.activities;

import com.cmu.journalmap.service.CheckService;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomePage extends Activity
{
	private Button viewMapButton = null;
	private Button viewPhotosButton = null;
	private Button quitButton = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		viewMapButton = (Button) findViewById(R.id.viewMapButton);
		viewPhotosButton = (Button) findViewById(R.id.viewPhotosButton);
		quitButton = (Button) findViewById(R.id.bQuitButton);
		startService(new Intent(HomePage.this,CheckService.class));
		
		viewMapButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					Class<?> ourClass = Class.forName("com.cmu.journalmap.activities.ActivityMap");
					Intent ourIntent = new Intent(HomePage.this, ourClass);
					startActivity(ourIntent);
					}catch (ClassNotFoundException e){
						e.printStackTrace();
					}
			}
		});
		
		viewPhotosButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					Class<?> ourClass = Class.forName("com.cmu.journalmap.activities.PhotoGallery");
					Intent ourIntent = new Intent(HomePage.this, ourClass);
					startActivity(ourIntent);
					}catch (ClassNotFoundException e){
						e.printStackTrace();
					}
			}
		});
		
		quitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public void onDestroy() {
		stopService(new Intent(HomePage.this,CheckService.class));
		super.onDestroy();
	}
	
}
