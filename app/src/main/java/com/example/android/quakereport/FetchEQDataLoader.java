package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

//AsyncTask Loader for fetching the data from USGS, cannot be sup-class of Main Activity
public class FetchEQDataLoader extends AsyncTaskLoader<ArrayList<EarthQuakeEntry>> {

    private Activity mAContext;

    public FetchEQDataLoader(Context context, Activity aContext) {
        super(context);
        mAContext = aContext;
    }

    @Override
    public ArrayList<EarthQuakeEntry> loadInBackground() {
        ArrayList<EarthQuakeEntry> events = QueryUtils.fetchEarthquakeData(mAContext);
        return events;
    }
}
