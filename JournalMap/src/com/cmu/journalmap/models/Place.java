package com.cmu.journalmap.models;

import java.io.Serializable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Place extends OverlayItem implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String photoLocation;
	private String note;
	private String audioLocation;
	private String videoLocation;
	private double[] pppp;

	public Place(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		this.pppp = toDoublePoint(point);
	}

	public double[] getPppp() {
		return pppp;
	}

	public void setPppp(double[] pppp) {
		this.pppp = pppp;
	}
	
	private double[] toDoublePoint(GeoPoint geoP){
		double[] res = {19.240000, -99.120000};
		
		return res;
	}
	
}
