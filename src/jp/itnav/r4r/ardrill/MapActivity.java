package jp.itnav.r4r.ardrill;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class MapActivity extends FragmentActivity {
	private GoogleMap mMap = null;
	private MySQLite mSql;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mSql = new MySQLite(this);
		mMap.setMyLocationEnabled(true);

		ArrayList<LatLng> latlng = new ArrayList<LatLng>();
		latlng.addAll(mSql.getLatLng());
		for (int i = 0; i < latlng.size(); i++) {
			if (i != 0) {
				addPolyLine(latlng.get(i), latlng.get(i - 1));
			}
		}
		try {
			addMarker(latlng.get(0));
			addMarker(latlng.get(latlng.size() - 1));
			moveCamera(latlng.get(latlng.size() - 1), 15);
		} catch (IndexOutOfBoundsException e) {
			Toast.makeText(this, "NoData", Toast.LENGTH_SHORT).show();
		}

	}

	private void moveCamera(LatLng latlng, float zoom) {
		LatLng location = new LatLng(latlng.latitude, latlng.longitude);
		CameraPosition cameraPos = new CameraPosition.Builder()
				.target(location).zoom(zoom).build();
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
	}

	private void addMarker(LatLng latlng) {
		MarkerOptions options = new MarkerOptions();
		LatLng location = new LatLng(latlng.latitude, latlng.longitude);
		options.position(location);
		options.draggable(false);
		mMap.addMarker(options);
	}

	private void addPolyLine(LatLng point1, LatLng point2) {
		PolylineOptions options = new PolylineOptions();
		options.add(point1);
		options.add(point2);
		options.color(0xcc00ffff);
		options.width(10);
		options.geodesic(true);
		mMap.addPolyline(options);
	}

}
