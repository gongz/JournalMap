package com.cmu.journalmap.utilities;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Environment;

public class PictureUtility
{
	public static double[] getCoordsFromPhoto(String filename)
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
			String tempLatDir = getExifTag(exif,ExifInterface.TAG_GPS_LATITUDE_REF);
			String tempLonDir = getExifTag(exif,ExifInterface.TAG_GPS_LONGITUDE_REF);
			
			if (tempLat == "")
			{
				coordinates[0] = Double.NaN;
			}
			else
			{
				coordinates[0] = convertDmsToDecimal(tempLat);
				
				if (tempLatDir.equals("S"))
				{
					coordinates[0] = coordinates[0]*-1;
				}
				
			}
			if (tempLon == "")
			{
				coordinates[1] = Double.NaN;
			}
			else
			{
				coordinates[1] = convertDmsToDecimal(tempLon);
				
				if (tempLonDir.equals("W"))
				{
					coordinates[1] = coordinates[1]*-1;
				}
			}
				
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return coordinates;
	}
	
	// TODO: cite
	private static String getExifTag(ExifInterface exif,String tag){
		String attribute = exif.getAttribute(tag);

		return (null != attribute ? attribute : "");
	}
	
	private static double convertDmsToDecimal(String coordinate)
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
	
	public static Bitmap decodeSampledBitmapFromPath(String res,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;		
		BitmapFactory.decodeFile(res, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(res,options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
}
