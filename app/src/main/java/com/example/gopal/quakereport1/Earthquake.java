package com.example.gopal.quakereport1;

/**
 * Created by Gopal on 12/25/2018.
 */

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private long mDate;
    private String mUrl;

    public Earthquake(double mMagnitude, String mLocation, long mDate, String mUrl) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mDate = mDate;
        this.mUrl = mUrl;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}