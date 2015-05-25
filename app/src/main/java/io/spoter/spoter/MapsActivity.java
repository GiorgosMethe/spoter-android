package io.spoter.spoter;


import android.content.Context;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.location.Criteria;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.maps.android.ui.IconGenerator;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MapsActivity extends FragmentActivity implements OnMarkerClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    int spots = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


//        new Thread()
//        {
//            public void run()
//            {
//                HttpURLConnection connection = null;
//                try
//                {
//                    URL myUrl = new URL("http://google.ca");
//                    connection = (HttpURLConnection)myUrl.openConnection();
//
//                    InputStream iStream = connection.getInputStream();
//
//                    String response = IOUtils.toString(iStream);
//
//                    final TextView fview = (TextView)findViewById()
//                }
//                catch (MalformedURLException ex)
//                {
//                    Log.e(TAG, "Invalid URL", ex);
//                }
//                catch (IOException ex)
//                {
//                    Log.e(TAG, "IO / Connection Error", ex);
//                }
//                finally
//                {
//                    if(connection==null)
//                        connection.disconnect();
//                }
//            }
//        }.start();



    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(getMyLocation()));
        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        // Add markers to the Google Map
        addMarkers();
    }

    private void addMarkers(){
        if(mMap != null){
            IconGenerator iconGenerator = new IconGenerator(this);
            iconGenerator.setStyle(IconGenerator.STYLE_PURPLE);
            Bitmap iconBitmap = iconGenerator.makeIcon("Spot it");
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).position(getMyLocation()).title("Spots").snippet(Integer.toString(spots)));
            // Add click listener to the markers
            mMap.setOnMarkerClickListener(this);
        }
    }

    private LatLng getMyLocation(){
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // Get latitude longitude of the current location
        double currLatitude = myLocation.getLatitude();
        double currLongitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        return new LatLng(currLatitude, currLongitude);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        spots = spots + 1;
        return false;
    }

}
