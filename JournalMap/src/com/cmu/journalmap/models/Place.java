package com.cmu.journalmap.models;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Place extends OverlayItem
{
	private String photoLocation;
	private String note;
	private String audioLocation;
	private String videoLocation;

	public Place(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		// TODO Auto-generated constructor stub
	}
	
	
}
