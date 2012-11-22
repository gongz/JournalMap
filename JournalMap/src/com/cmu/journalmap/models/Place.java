package com.cmu.journalmap.models;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Place extends OverlayItem {

	private String photoLocation;
	private String note;
	private String audioLocation;
	private String videoLocation;
	private GeoPoint geoLocation;

	public Place(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		this.geoLocation = point;
	}

	public GeoPoint getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoPoint geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getPhotoLocation() {
		return photoLocation;
	}

	public void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAudioLocation() {
		return audioLocation;
	}

	public void setAudioLocation(String audioLocation) {
		this.audioLocation = audioLocation;
	}

	public String getVideoLocation() {
		return videoLocation;
	}

	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}

}
