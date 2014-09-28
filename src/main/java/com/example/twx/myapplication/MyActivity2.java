package com.example.twx.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyActivity2 extends Activity {
    Station station;
    TextView t_nom;
    TextView t_adress;
    TextView t_bike;
    TextView t_attach;
    MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_activity2);

        this.station = new Station();

        Bundle b = getIntent().getExtras();
        this.station.setName(b.getString("name"));
        this.station.setLat(b.getString("lat"));
        this.station.setLng(b.getString("lng"));
        this.station.setId(b.getString("id"));

        this.t_nom = (TextView) findViewById(R.id.nom);
        this.t_adress = (TextView) findViewById(R.id.adresse);
        this.t_attach = (TextView) findViewById(R.id.attach);
        this.t_bike = (TextView) findViewById(R.id.bike);

        this.map = (MapView) findViewById(R.id.map);
        this.map.onCreate(savedInstanceState);
        MapsInitializer.initialize(this);
        LatLng annemasse = new LatLng(Float.valueOf(this.station.getLatitude()),
                                      Float.valueOf(this.station.getLongitude()));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                VLilleWebService service = new VLilleWebService();
                station = service.moreInformationsStation(station);
            }
        });
        thread.start();
        try {
            thread.join();
            this.t_adress.setText(station.getAddress());
            this.t_bike.setText(station.getBike());
            this.t_attach.setText(station.getAttach());
            this.t_nom.setText(station.getName());
            GoogleMap googleMap = map.getMap();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(annemasse));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            googleMap.addMarker(new MarkerOptions().position(annemasse)
                    .title(this.station.getName())
                    .snippet("bikes: " + this.station.getBike() + " attachs: " + this.station.getAttach()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showDirections(View view) {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        final Intent intent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr=" + location.getLatitude() + "," + location.getLongitude() + "&daddr=" + station.getLatitude() + "," +
                station.getLongitude()));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);

    }
    @Override
    public void onResume() {
        map.onResume();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        map.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my_activity2, container, false);
            return rootView;
        }
    }
}
