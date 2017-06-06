package com.example.android.quakereport;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

//AsyncTask Loader for fetching the data from USGS, cannot be sup-class of Main Activity
public class FetchEQDataLoader extends AsyncTaskLoader<ArrayList<EarthQuakeEntry>> {

    private Activity mAContext;

    public FetchEQDataLoader(Context context, Activity aContext) {
        super(context);
        mAContext = aContext;
    }

    @Override
    protected void onStartLoading() {
        Log.i("forceLoad", "done");
        forceLoad();
    }

    @Override
    public ArrayList<EarthQuakeEntry> loadInBackground() {
        Log.i("loadInBackground", "done");
        ArrayList<EarthQuakeEntry> events = QueryUtils.fetchEarthquakeData(mAContext);
        return events;
    }
}
