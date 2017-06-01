package com.example.android.quakereport;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by hildegw on 5/19/17.
 */

public class EarthQuakeEntry {

    private Double mMag;
    private String mPlace;
    private Date mDateEpoch;
    private Context mContext;
    private static final String LOCATION_SEPARATOR = " of ";

    public EarthQuakeEntry(Context context, Double mag, String place, Date dateEpoch){
        mContext = context;
        mMag = mag;
        mPlace = place;
        mDateEpoch = dateEpoch;
    }

    public String getMagnitude() {
        DecimalFormat formatter = new DecimalFormat("0.0");
        String magnitude = formatter.format(mMag);
        return magnitude;
    }

    public int setColor() {
        int magnitudeColor;
        //adapt background color with magnitude
        Long L = Math.round(mMag);
        int i = Integer.valueOf(L.intValue());
        switch (i){
            case 0:
            case 1:
                magnitudeColor = mContext.getColor(R.color.magnitude1);
                break;
            case 2:
                magnitudeColor = mContext.getColor(R.color.magnitude2);
                break;
            case 3:
                magnitudeColor = mContext.getColor(R.color.magnitude3);
                break;
            case 4:
                magnitudeColor = mContext.getColor(R.color.magnitude4);
                break;
            case 5:
                magnitudeColor = mContext.getColor(R.color.magnitude5);
                break;
            case 6:
                magnitudeColor = mContext.getColor(R.color.magnitude6);
                break;
            case 7:
                magnitudeColor = mContext.getColor(R.color.magnitude7);
                break;
            case 8:
                magnitudeColor = mContext.getColor(R.color.magnitude8);
                break;
            case 9:
                magnitudeColor = mContext.getColor(R.color.magnitude9);
                break;
            default:
                magnitudeColor = mContext.getColor(R.color.magnitude10plus);

        }
        return magnitudeColor;
    }

    public String getLocation() {
        if (mPlace.contains(LOCATION_SEPARATOR)) {
            String[] splitLocation = mPlace.trim().split(LOCATION_SEPARATOR);
            if (splitLocation.length > 1) {
                return splitLocation[splitLocation.length-1].trim();
            } else {
                return "";
            }
        }
        return mPlace;
    }

    public String getNearToLocation() {
        if (mPlace.contains(LOCATION_SEPARATOR)) {
            String[] splitLocation = mPlace.trim().split(LOCATION_SEPARATOR);
            if (splitLocation.length > 1) {
                return splitLocation[splitLocation.length-2].trim() + LOCATION_SEPARATOR;
            } else {
                return "";
            }
        }
        return mContext.getResources().getString(R.string.near);
    }

    public String getDate() {
        //format Date object into date String only
        String date = new SimpleDateFormat("dd.MM.yyyy").format(mDateEpoch);
        return date;
    }

    public String getTime() {
        String time = new SimpleDateFormat("hh:mm").format(mDateEpoch);
        return time;
    }
}
