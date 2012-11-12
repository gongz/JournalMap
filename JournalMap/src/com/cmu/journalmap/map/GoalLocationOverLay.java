package com.cmu.journalmap.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.cmu.journalmap.models.Place;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import droid.monkeys.R;

public class GoalLocationOverLay extends ItemizedOverlay<OverlayItem> {

	private static final String TAG = "GoalLocationOverLay";
	private MapView mapView;

	// need to store this somewhere
	private static List<Place> items = new ArrayList<Place>();
	private RelativeLayout _bubbleLayout;
	private boolean isTapAllowed = false;

	public GoalLocationOverLay(Drawable marker, MapView mapView,
			Boolean isTapAllowed) {
		super(marker);
		// this totally depends on the img we have
		marker.setBounds(-marker.getIntrinsicWidth() / 4,
				-marker.getIntrinsicHeight(),
				marker.getIntrinsicWidth() * 3 / 4, 0);
		this.mapView = mapView;
		populate();
		this.isTapAllowed = isTapAllowed;
	}

	// method to draw marker at point(lat,lon) or GeoPoint

	public void addPoint(GeoPoint point) {
		StringBuffer buf = new StringBuffer();
		buf.append("Lat: ")
				.append(String.valueOf(point.getLatitudeE6() / 1000000.0))
				.append(" ");
		buf.append("Lon: ")
				.append(String.valueOf(point.getLongitudeE6() / 1000000.0))
				.append(" ");
		items.add(new Place(point, "", buf.toString()));
		populate();

	}

	public void redraw() {
		populate();
	}

	public void addPoint(double lat, double lon) {
		GeoPoint point = getPoint(lat, lon);
		addPoint(point);
	}

	// make a toast, add marker and move to there

	@Override
	protected boolean onTap(int index) {
		Place item = items.get(index);
		if (_bubbleLayout == null) {
			createBubble(item);
		} else {
			// if the user tapped on the marker
			// and there is a bubble showing, clear the current bubble
			mapView.removeView(_bubbleLayout);
			_bubbleLayout = null;
		}
		return true;
	}

	@Override
	public boolean onTap(GeoPoint point, MapView mapView) {
		boolean tapped = super.onTap(point, mapView);
		// here we check if tap is allowed, the used tapped on the map
		// instead of on a marker and there is no bubble showing

		if (isTapAllowed && !tapped && _bubbleLayout == null) {
			Toast.makeText(mapView.getContext(), geoToString(point),
					Toast.LENGTH_SHORT).show();
			addPoint(point);
			mapView.getController().animateTo(point);
			return true;
		}

		// if the user tapped on the map, clear the current bubble
		if (!tapped && _bubbleLayout != null) {
			mapView.removeView(_bubbleLayout);
			_bubbleLayout = null;
		}
		mapView.getController().animateTo(point);
		return true;
	}

	public String geoToString(GeoPoint point) {
		StringBuffer buf = new StringBuffer();
		buf.append("Lat: ")
				.append(String.valueOf(point.getLatitudeE6() / 1000000.0))
				.append(" ");
		buf.append("Lon: ")
				.append(String.valueOf(point.getLongitudeE6() / 1000000.0))
				.append(" ");
		return buf.toString();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return items.get(i);
	}

	@Override
	public int size() {
		return items.size();
	}

	private GeoPoint getPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	private void createBubble(Place item) {
		LayoutInflater inflater = (LayoutInflater) mapView.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		_bubbleLayout = (RelativeLayout) inflater.inflate(
				droid.monkeys.R.layout.bubble, mapView, false);

		MapView.LayoutParams params = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				item.getPoint(), MapView.LayoutParams.BOTTOM_CENTER);

		_bubbleLayout.setLayoutParams(params);

		// Locate the TextView
		TextView locationNameText = (TextView) _bubbleLayout
				.findViewById(droid.monkeys.R.id.locationName);
		ImageView imgView = (ImageView) _bubbleLayout
				.findViewById(R.id.locationImage);
		imgView.setImageDrawable(mapView.getContext().getResources()
				.getDrawable(R.drawable.jm_launcher));
		// Set the Text
		locationNameText.setText(geoToString(item.getPoint()));
		// Add the view to the Map
		mapView.addView(_bubbleLayout);

	}
}
