/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //todo: check correckt HTTP GET String to fetch data from USGS
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-06-01&endtime=2017-06-03&minmagnitude=4";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;  //comes as part of recycler view
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //start Background task
        FetchEQData fetchEQData = new FetchEQData();
        fetchEQData.execute(USGS_REQUEST_URL);

        //start Recycler
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_entries);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    //AsyncTask for fetching the data from USGS
    private class FetchEQData extends AsyncTask<String, Void, ArrayList<EarthQuakeEntry>> {

        @Override
        protected ArrayList<EarthQuakeEntry> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                Log.e("found it", "error!!!");
                return null;
            }
            ArrayList<EarthQuakeEntry> events = QueryUtils.fetchEarthquakeData(EarthquakeActivity.this, urls[0]);
            return events;
        }

        @Override
        protected void onPostExecute(ArrayList<EarthQuakeEntry> events) {
            if (events == null) {
                return;
            }
            // Update the information displayed to the user.
            updateUi(events);
        }
    }


    //extract data and call adapter to update UI
    private void updateUi(ArrayList<EarthQuakeEntry> events) {
        // specify an adapter
        EQEntryAdapter eqEntryAdapter = new EQEntryAdapter(this, events);
        mRecyclerView.setAdapter(eqEntryAdapter);
    }
}
