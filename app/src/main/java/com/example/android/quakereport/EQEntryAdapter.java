package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by hildegw on 5/19/17.
 */

public class EQEntryAdapter extends RecyclerView.Adapter<EQEntryAdapter.ViewHolder>  {

    private ArrayList<EarthQuakeEntry> mEqDataset;
    private LayoutInflater mInflator;
    private Context mContext;

    //ViewHolder class with constructor
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        private TextView magnitudeView;
        private TextView locationView;
        private TextView timeView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }

    // Constructor based on each earth quake entry
    public EQEntryAdapter(Activity context, ArrayList<EarthQuakeEntry> eqDataset) {
        //super(context, 0, eqDataset);         //todo ?? calling super/ArrayAdapter-constructor
        mContext = context;
        mInflator = LayoutInflater.from(context); //todo: ???
        mEqDataset = eqDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EQEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view (equals convertView)
        View view = mInflator.inflate(R.layout.earthquake_entry, parent, false);
        //todo: set the view's size, margins, paddings and layout parameters

        //create ViewHolder instance and find its views
        ViewHolder vh = new ViewHolder(view);
        vh.magnitudeView = (TextView)view.findViewById(R.id.eq_mag);
        vh.locationView = (TextView)view.findViewById(R.id.eq_location);
        vh.timeView = (TextView)view.findViewById(R.id.eq_time);
        //view.setTag(vh);                                                    //todo: needed??? if-else with getTag(vh)?
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get element from your dataset at this position and replace the contents of the view with that element
        final EarthQuakeEntry currentEntry = mEqDataset.get(position);
        holder.magnitudeView.setText(currentEntry.getMagnitude());
        holder.locationView.setText(currentEntry.getLocation());
        holder.timeView.setText(currentEntry.getTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //Log.i("item count ", String.valueOf(mEqDataset.size()));
        return mEqDataset.size();
    }
}



