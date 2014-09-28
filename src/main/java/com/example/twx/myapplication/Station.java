package com.example.twx.myapplication;

/**
 * Created by twx on 21/09/14.
 */

public class Station {
    String id;
    String lat;
    String lng;
    String name;
    String address;
    String bike;
    String attach;
    float distance;

    public void setId(String id) {
        this.id = id;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) { this.address = address; }
    public void setBike(String bike) { this.bike = bike; }
    public void setAttach(String attach) { this.attach = attach; }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getName() {
        return this.name;
    }
    public String getLatitude() {
        return this.lat;
    }
    public String getId() {
        return this.id;
    }
    public String getLongitude() {
        return this.lng;
    }
    public String getAddress() { return this.address; }
    public String getBike() { return this.bike; }
    public String getAttach() { return this.attach; }

    public float getDistance() {
        return distance;
    }
}
