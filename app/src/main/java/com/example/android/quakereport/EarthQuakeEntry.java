package com.example.android.quakereport;

/**
 * Created by hildegw on 5/19/17.
 */

public class EarthQuakeEntry {

    private Double mMagnitude;
    private String mLocation;
    private String mTime;

    public EarthQuakeEntry(Double magnitude, String location, String time){
        mMagnitude = magnitude;
        mLocation = location;
        mTime = time;
    }

    public String getMagnitude() {
        return mMagnitude.toString();
    }

    public String getLocation() {
        return mLocation;
    }

    public String getTime() {
        return mTime;
    }
}
