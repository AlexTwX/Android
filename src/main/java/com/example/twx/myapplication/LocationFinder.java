package com.example.twx.myapplication;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by twx on 28/09/14.
 */
public class LocationFinder {
    public LocationFinder() {

    }

    public List<Station> sortStation(Context context, List<Station> stations) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            float lat = (float) location.getLatitude();
            float lng = (float) location.getLongitude();
            List<Station> stationTrier = new LinkedList<Station>();
            for (int i=1; i<stations.size(); i++) {
                Station tmp = stations.get(i);
                Location point = new Location("Station");
                point.setLatitude(Float.valueOf(tmp.getLatitude()));
                point.setLongitude(Float.valueOf(tmp.getLongitude()));
                tmp.setDistance(location.distanceTo(point));
                if (stationTrier.size() == 0) {
                    stationTrier.add(tmp);
                }
                int j=0;
                for (; j < stationTrier.size(); j++) {
                    if (tmp.getDistance() < stationTrier.get(j).getDistance()) {
                        break;
                    }
                }
                stationTrier.add(j, tmp);
            }
            return stationTrier;
        }
        return stations;
    }

}
