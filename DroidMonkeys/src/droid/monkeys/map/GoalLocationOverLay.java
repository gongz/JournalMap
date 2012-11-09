package droid.monkeys.map;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class GoalLocationOverLay extends ItemizedOverlay<OverlayItem> {

	private static final String TAG = "GoalLocationOverLay";
	private MapView mapView;
	private List<OverlayItem> items = new ArrayList<OverlayItem>();

	public GoalLocationOverLay(Drawable marker, MapView mapView) {
		super(marker);

		// this totally depends on the img we have
		marker.setBounds(-marker.getIntrinsicWidth() / 4,
				-marker.getIntrinsicHeight(),
				marker.getIntrinsicWidth() * 3 / 4, 0);
		this.mapView = mapView;
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
		items.add(new OverlayItem(point, "", buf.toString()));
		populate();
	}

	public void addPoint(double lat, double lon) {
		GeoPoint point = getPoint(lat, lon);
		addPoint(point);
	}

	// make a toast, add marker and move to there
	@Override
	public boolean onTap(GeoPoint point, MapView mapView) {

		StringBuffer buf = new StringBuffer();
		buf.append("Lat: ")
				.append(String.valueOf(point.getLatitudeE6() / 1000000.0))
				.append(" ");
		buf.append("Lon: ")
				.append(String.valueOf(point.getLongitudeE6() / 1000000.0))
				.append(" ");

		Toast.makeText(mapView.getContext(), buf.toString(), Toast.LENGTH_SHORT)
				.show();
		addPoint(point);
		mapView.getController().animateTo(point);
		return true;
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

}
