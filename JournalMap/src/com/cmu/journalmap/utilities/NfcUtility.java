package com.cmu.journalmap.utilities;

public class NfcUtility
{
	public static double[] tagToCoords(String strCoords)
	{
		double[] coords = {0,0};
		
		String[] coordsArr = strCoords.split(",");
		
		coords[0] = Double.valueOf(coordsArr[0]);
		coords[1] = Double.valueOf(coordsArr[1]);
		
		return coords;
	}
}
