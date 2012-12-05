package com.cmu.journalmap.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cmu.journalmap.map.GoalLocationOverLay;
import com.cmu.journalmap.models.Place;
import com.cmu.journalmap.storage.Places;
import com.cmu.journalmap.utilities.PropertiesUtility;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class ActivityMap extends MapActivity {
	private static final String TAG = "MapActivity";
	private static RelativeLayout mapLayout;
	private static MapView mapView;
	private static MyLocationOverlay currentLocationOverLay;
	private GoalLocationOverLay mapPins;
	private boolean isTapAllowed = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTapAllowed(getIntent().getIntExtra("isTapAllowed", 0));
		setContentView(R.layout.activity_map);
		setUpControlUI();
		populateMap();
	}

	private void populateMap() {
		mapLayout = (RelativeLayout) findViewById(R.id.map_layout);
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
		currentLocationOverLay = new MyLocationOverlay(this, mapView);
		currentLocationOverLay.enableMyLocation();
		Places.setItems(PropertiesUtility.propertiesToPlaceList(mapView.getContext()));
		currentLocationOverLay.runOnFirstFix(new Runnable() {
			public void run() {
				if (Places.getItems().size() > 0) {
					mapView.getController().animateTo(
							Places.getItems().get(Places.getItems().size() - 1)
									.getGeoLocation());
				} else {
					mapView.getController().animateTo(
							currentLocationOverLay.getMyLocation());
				}
			}
		});

		mapView.getOverlays().add(currentLocationOverLay);
		mapView.getController().setZoom(5);
		Drawable marker = getResources().getDrawable(R.drawable.map_pin);
		if (mapPins == null) {			
			mapPins = new GoalLocationOverLay(marker, mapView, isTapAllowed());
		} else {
			mapPins.redraw();
		}
		mapView.getOverlays().add(mapPins);

	}

	// save the markers
	@Override
	public Object onRetainNonConfigurationInstance() {
		return mapPins;
	}

	private void setUpControlUI() {
		Button bt_back = (Button) findViewById(R.id.map_button_back);
		Button bt_next = (Button) findViewById(R.id.map_button_next);
		bt_back.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				// close MapActivity
				finish();
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onStop() {
		if (currentLocationOverLay != null)
			currentLocationOverLay.disableMyLocation();
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (currentLocationOverLay != null)
			currentLocationOverLay.enableMyLocation();
	}

	public boolean isTapAllowed() {
		return isTapAllowed;
	}

	public void setTapAllowed(int isTapAllowed) {
		if (isTapAllowed != 0)
			this.isTapAllowed = true;
		else
			this.isTapAllowed = false;
	}
}
