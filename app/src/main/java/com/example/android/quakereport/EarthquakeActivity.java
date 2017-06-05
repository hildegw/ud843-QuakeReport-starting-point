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

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<EarthQuakeEntry>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;  //comes as part of recycler view
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        //start Background task via Loader
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);

        //start Recycler
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_entries);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    //Loader starts fetching data when created
    @Override
    public android.content.Loader<ArrayList<EarthQuakeEntry>> onCreateLoader(int id, Bundle args) {
        return new FetchEQDataLoader(EarthquakeActivity.this, this);
    }

    //UI is being populated with EQ events
    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<EarthQuakeEntry>> loader, ArrayList<EarthQuakeEntry> events) {
        if (events == null) {
            return;
        }
        // specify an adapter
        EQEntryAdapter eqEntryAdapter = new EQEntryAdapter(this, events);
        mRecyclerView.setAdapter(eqEntryAdapter);
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<EarthQuakeEntry>> loader) {
        // start with no data whe Activity is killed
        EQEntryAdapter eqEntryAdapter = new EQEntryAdapter(this, null); //todo: better way to clear adapter?
        mRecyclerView.setAdapter(eqEntryAdapter);
    }
}
