package com.cmu.journalmap.map;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import com.cmu.journalmap.activities.R;
import com.cmu.journalmap.activities.ShowPlace;
import com.cmu.journalmap.models.Place;
import com.cmu.journalmap.storage.Places;
import com.cmu.journalmap.utilities.PictureUtility;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class GoalLocationOverLay extends ItemizedOverlay<OverlayItem> {

	//private static final String TAG = "GoalLocationOverLay";
	private MapView mapView;
	private RelativeLayout _bubbleLayout;
	private boolean isTapAllowed = false;
	private Activity mapActivity = null;
	
	
	public GoalLocationOverLay(Drawable marker, MapView mapView,
			Boolean isTapAllowed, Activity mapActivity) {
		super(marker);
		// this totally depends on the img we have
		marker.setBounds(-marker.getIntrinsicWidth() / 4,
				-marker.getIntrinsicHeight(),
				marker.getIntrinsicWidth() * 3 / 4, 0);
		this.mapView = mapView;
		populate();
		this.isTapAllowed = isTapAllowed;
		this.mapActivity = mapActivity;
		
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
		Places.getItems().add(new Place(point, "", buf.toString()));
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
		Place item = Places.getItems().get(index);
		if (_bubbleLayout == null) {
			if (item.getGeoLocation()!=null) {
				createBubble(item);
			} else {
				Intent returnIntent = new Intent();
				returnIntent.putExtra("lag", item.getGeoLocation().getLatitudeE6());
				returnIntent.putExtra("lon", item.getGeoLocation().getLongitudeE6());
				this.mapActivity.setResult(Activity.RESULT_OK, returnIntent);
				this.mapActivity.finish();
			}
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
		return Places.getItems().get(i);
	}

	@Override
	public int size() {
		return Places.getItems().size();
	}

	private GeoPoint getPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	private void createBubble(final Place item) {
		LayoutInflater inflater = (LayoutInflater) mapView.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		_bubbleLayout = (RelativeLayout) inflater.inflate(
				com.cmu.journalmap.activities.R.layout.bubble, mapView, false);
		_bubbleLayout.setOnClickListener(new OnClickListener(){
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), ShowPlace.class);
				intent.putExtra("pictureLoc", item.getPhotoLocation());
				intent.putExtra("audioLoc", item.getAudioLocation());
				intent.putExtra("videoLoc", item.getVideoLocation());
				intent.putExtra("note", item.getNote());
				view.getContext().startActivity(intent);				
			}			
		});
		MapView.LayoutParams params = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				item.getPoint(), MapView.LayoutParams.BOTTOM_CENTER);

		_bubbleLayout.setLayoutParams(params);

		// Locate the TextView
		//TextView locationNameText = (TextView) _bubbleLayout
		//		.findViewById(com.cmu.journalmap.activities.R.id.locationName);
		ImageView imgView = (ImageView) _bubbleLayout
				.findViewById(R.id.locationImage);
		
		Bitmap ThumbImage = PictureUtility.decodeSampledBitmapFromPath(item.getPhotoLocation(), 200 , 200);		
		imgView.setImageBitmap(ThumbImage);
		
//		imgView.setImageDrawable(mapView.getContext().getResources()
//				.getDrawable(R.drawable.jm_launcher));
		// Set the Text
		//locationNameText.setText(item.getTitle()+" "+item.getSnippet());
		// Add the view to the Map
		mapView.addView(_bubbleLayout);

	}
}
