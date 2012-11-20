package com.cmu.journalmap.utilities;

import java.io.File;
import java.io.IOException;

import android.media.ExifInterface;
import android.os.Environment;

public class PictureUtility
{
	public double[] getCoordsFromPhoto(String filename)
	{
//		File sdcard = Environment.getExternalStorageDirectory();
//		//Get the text file
//		File file = new File(sdcard,"DCIM/Camera/IMG396.jpg");
//		String filePath = file.getAbsolutePath();
		
		double[] coordinates = {Double.NaN, Double.NaN};
		
		ExifInterface exif;
		try
		{
			exif = new ExifInterface(filename);
			String tempLat = getExifTag(exif,ExifInterface.TAG_GPS_LATITUDE);
			String tempLon = getExifTag(exif,ExifInterface.TAG_GPS_LONGITUDE);
			if (tempLat == "")
			{
				coordinates[0] = Double.NaN;
			}
			else
			{
				coordinates[0] = convertDmsToDecimal(tempLat);
			}
			if (tempLon == "")
			{
				coordinates[1] = Double.NaN;
			}
			else
			{
				coordinates[1] = convertDmsToDecimal(tempLon);
			}
				
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return coordinates;
	}
	
	// TODO: cite
	private String getExifTag(ExifInterface exif,String tag){
		String attribute = exif.getAttribute(tag);

		return (null != attribute ? attribute : "");
	}
	
	private double convertDmsToDecimal(String coordinate)
	{
		double finalCoord = 0.0f;
		String[] tempCoord, tempCoord2;
		//String sDeg, sMin, sSec;
		int iDeg, iMin, iSec;
		
		tempCoord = coordinate.split("/");
		iDeg = Integer.parseInt(tempCoord[0]);
		
		tempCoord2 = tempCoord[1].split(",");
		iMin = Integer.parseInt(tempCoord2[1]);
		
		tempCoord2 = tempCoord[2].split(",");
		iSec = Integer.parseInt(tempCoord2[1]);
		
		finalCoord = (double)(((iMin*60) + iSec))/3600 + iDeg;
		
		return finalCoord;
	}
}
