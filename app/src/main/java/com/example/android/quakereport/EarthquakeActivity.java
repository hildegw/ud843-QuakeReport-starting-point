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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;  //comes as part of recycler view
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_entries);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create a fake list of earthquake locations.
        ArrayList<EarthQuakeEntry> eqDataset = new ArrayList<>();
        eqDataset.add(new EarthQuakeEntry(7.2, "88 km N of Yelizovo, Russia", "Sat, 30 Jan 2016 03:25 GMT"));       //todo remove
        eqDataset.add(new EarthQuakeEntry(6.1, "94 km SSE of Taron, Papua New Guinea", "Mon, 25 Jan 2016 04:22 GMT"));
        eqDataset.add(new EarthQuakeEntry(6.3, "50 km NNE of Al Hoceima, Morocco", "Tue, 26 Jan 2016 03:10 GMT"));
        eqDataset.add(new EarthQuakeEntry(6.1, "94 km SSE of Taron, Papua New Guinea", "Mon, 25 Jan 2016 04:22 GMT"));
        eqDataset.add(new EarthQuakeEntry(6.3, "50 km NNE of Al Hoceima, Morocco", "Tue, 26 Jan 2016 03:10 GMT"));
        eqDataset.add(new EarthQuakeEntry(7.2, "88 km N of Yelizovo, Russia", "Sat, 30 Jan 2016 03:25 GMT"));       //todo remove
        eqDataset.add(new EarthQuakeEntry(6.1, "94 km SSE of Taron, Papua New Guinea", "Mon, 25 Jan 2016 04:22 GMT"));
        eqDataset.add(new EarthQuakeEntry(6.3, "50 km NNE of Al Hoceima, Morocco", "Tue, 26 Jan 2016 03:10 GMT"));
        eqDataset.add(new EarthQuakeEntry(7.2, "88 km N of Yelizovo, Russia", "Sat, 30 Jan 2016 03:25 GMT"));

        // specify an adapter (see also next example)
        EQEntryAdapter eqEntryAdapter = new EQEntryAdapter(this, eqDataset);

        mRecyclerView.setAdapter(eqEntryAdapter);
    }




}
