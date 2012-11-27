package com.cmu.journalmap.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cmu.journalmap.models.Place;
import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.util.Log;

public class PropertiesUtility
{
	String path = null;
	static String FILENAME = "journalmap.properties";
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
		properties.setProperty("geoLocationLat" + String.valueOf(numOfPlaces), String.valueOf(p.getGeoLocation().getLatitudeE6()));
		properties.setProperty("geoLocationLon" + String.valueOf(numOfPlaces), String.valueOf(p.getGeoLocation().getLongitudeE6()));
		properties.setProperty("numOfPlaces", String.valueOf(numOfPlaces+1));
		
	    FileOutputStream fos;
		try
		{
			//fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos = c.openFileOutput(FILENAME, Context.MODE_WORLD_READABLE);
			Log.i("LOCATION","Location is:" + c.getFilesDir().toString());
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
	
	public static List<Place> propertiesToPlaceList()
	{
		List<Place> placeList = new ArrayList<Place>();
		
		Properties props= new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(FILENAME);
			props.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Place(GeoPoint point, String title, String snippet)
		int numberOfPlaces = Integer.parseInt(props.getProperty("numOfPlaces"));
		
		for (int i = 0; i < numberOfPlaces; i++)
		{
			int tempLat = Integer.parseInt(props.getProperty("geoLocationLat" + i));
			int tempLon = Integer.parseInt(props.getProperty("geoLocationLon" + i));
			String tempPhotoLocation = props.getProperty("photoLocation" + i);
			String tempNote = props.getProperty("note" + i);
			String tempAudioLocation = props.getProperty("audioLocation" + i);
			String tempVideoLocation = props.getProperty("videoLocation" + i);
			
			Place tempPlace = new Place(new GeoPoint(tempLat, tempLon), "","");
			tempPlace.setPhotoLocation(tempPhotoLocation);
			tempPlace.setNote(tempNote);
			tempPlace.setAudioLocation(tempAudioLocation);
			tempPlace.setVideoLocation(tempVideoLocation);
	
			placeList.add(tempPlace);
			tempPlace = null;
		}

		return placeList;
	}
	
}