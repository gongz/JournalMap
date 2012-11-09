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
			coordinates[0] = Double.parseDouble(getExifTag(exif,ExifInterface.TAG_GPS_LATITUDE));
			coordinates[1] = Double.parseDouble(getExifTag(exif,ExifInterface.TAG_GPS_LONGITUDE));
			
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
}
