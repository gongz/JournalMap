package com.cmu.journalmap.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.cmu.journalmap.models.Place;

import android.content.Context;
import android.util.Log;

public class PropertiesUtility
{
	String path = null;
	String FILENAME = "journalmap.properties";
	Properties properties = new Properties();
	
	public void writePlaceToFile(Context c, Place p)
	{
		// check to see if properties file exists
		FileInputStream fis;
		int numOfPlaces = 0;
				
		try
		{
			fis = c.openFileInput(FILENAME);
			properties.load(fis);
			String tempNumOfPlaces = properties.getProperty("numOfPlaces");

			if ((tempNumOfPlaces != null)&&(!tempNumOfPlaces.isEmpty()))
			{
				numOfPlaces = Integer.parseInt(tempNumOfPlaces);
			}
			fis.close();
			
		} catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			Log.i("PROPERTIES", "Properties file not found");
			
			//e1.printStackTrace();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("PROPERTIES", "numOfPlaces = " + numOfPlaces);
		properties.setProperty("photoLocation" + String.valueOf(numOfPlaces), p.getPhotoLocation());
		properties.setProperty("note" + String.valueOf(numOfPlaces), p.getNote());
		properties.setProperty("audioLocation" + String.valueOf(numOfPlaces), p.getAudioLocation());
		properties.setProperty("videoLocation" + String.valueOf(numOfPlaces), p.getVideoLocation());
		properties.setProperty("numOfPlaces", String.valueOf(numOfPlaces+1));
		
	    FileOutputStream fos;
		try
		{
			fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			properties.store(fos, "JournalMap properties");	
		    fos.close();
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}