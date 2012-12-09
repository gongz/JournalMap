package com.cmu.journalmap.activities;

import com.cmu.journalmap.service.CheckService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Intent intent = new Intent(this, CheckService.class);
		startService(intent);
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					sleep(3000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					Intent openStartingPoint = new Intent("com.cmu.journalmap.activities.STARTPAGE");
					startActivity(openStartingPoint);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	

}
