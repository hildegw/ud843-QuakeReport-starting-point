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
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<EarthQuakeEntry>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;  //comes as part of recycler view
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        //check for Internet connection
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        //if no connection - hide loader and set TextView to "no connection"
        if (isConnected) {
            //start Background task via Loader
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else {
            Log.i("no Internet", "nonono");
            ProgressBar pg = (ProgressBar) findViewById(R.id.loading);
            pg.setVisibility(View.GONE);
            mEmptyStateTextView = (TextView) findViewById(R.id.no_data);
            mEmptyStateTextView.setText(R.string.no_Internet);
        }

        //start Recycler
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_entries);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    } //end of onCreate


    //Adding Options Menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Loader starts fetching data when created
    @Override
    public android.content.Loader<ArrayList<EarthQuakeEntry>> onCreateLoader(int id, Bundle args) {
        Log.i("onCreateLoader", "done");
        return new FetchEQDataLoader(EarthquakeActivity.this, this);
    }

    //UI is being populated with EQ events
    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<EarthQuakeEntry>> loader, ArrayList<EarthQuakeEntry> events) {
        if (events == null) {
            return;
        }
        //hide loading bar
        ProgressBar pg = (ProgressBar) findViewById(R.id.loading);
        pg.setVisibility(View.GONE);
        // specify an adapter
        Log.i("onLoadFinished", "done");
        EQEntryAdapter eqEntryAdapter = new EQEntryAdapter(this, events);
        mRecyclerView.setAdapter(eqEntryAdapter);
        //in case there is no data to display, set TextView from earthquake_activity.xml
        mEmptyStateTextView = (TextView) findViewById(R.id.no_data);
        mEmptyStateTextView.setText(R.string.no_data_found);
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<EarthQuakeEntry>> loader) {
        // start with no data whe Activity is killed
        Log.i("onLoadReset", "done");
        EQEntryAdapter eqEntryAdapter = new EQEntryAdapter(this, null); //todo: better way to clear adapter?
        mRecyclerView.setAdapter(eqEntryAdapter);
    }
}
