package droid.monkeys.map;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import droid.monkeys.R;

public class ActivityMap extends MapActivity {
	private static final String TAG = "MapActivity";
	private static RelativeLayout mapLayout;
	private static MapView mapView;
	private static MyLocationOverlay currentLocationOverLay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		setUpControlUI();
		populateMap();
	}

	private void populateMap() {
		mapLayout = (RelativeLayout) findViewById(R.id.map_layout);
		mapView = (MapView) findViewById(R.id.mapView);

		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);

		mapView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getPointerCount() > 1)
					return true;
				return false;
			}
		});
		currentLocationOverLay = new MyLocationOverlay(this, mapView);
		currentLocationOverLay.enableMyLocation();
		currentLocationOverLay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						currentLocationOverLay.getMyLocation());

			}
		});
		mapView.getOverlays().add(currentLocationOverLay);
		mapView.getController().setZoom(18);
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
		currentLocationOverLay.disableMyLocation();
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		currentLocationOverLay.enableMyLocation();
	}

}
