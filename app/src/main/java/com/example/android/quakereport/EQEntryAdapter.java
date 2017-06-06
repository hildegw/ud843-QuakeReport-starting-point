package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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
        private View mView;
        private TextView magnitudeView;
        private GradientDrawable magnitudeCircle;
        private TextView locationView;
        private TextView nearToView;
        private TextView timeView;
        private TextView dateView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }

    // Constructor based on each earth quake entry
    public EQEntryAdapter(Activity context, ArrayList<EarthQuakeEntry> eqDataset) {
        mContext = context;
        mInflator = LayoutInflater.from(context);
        mEqDataset = eqDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EQEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view (equals convertView)
        View view = mInflator.inflate(R.layout.earthquake_entry, parent, false);

        //create ViewHolder instance and find its views
        final ViewHolder vh = new ViewHolder(view);
        vh.magnitudeView = (TextView)view.findViewById(R.id.eq_mag);
        vh.magnitudeCircle = (GradientDrawable) vh.magnitudeView.getBackground();  // Fetch the background from the TextView, which is a GradientDrawable.
        vh.locationView = (TextView)view.findViewById(R.id.eq_location);
        vh.nearToView = (TextView)view.findViewById(R.id.eq_near_to);
        vh.dateView = (TextView)view.findViewById(R.id.eq_date);
        vh.timeView = (TextView)view.findViewById(R.id.eq_time);

        //add click listener for each enetry
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int position = vh.getAdapterPosition();
                final EarthQuakeEntry currentEntry = mEqDataset.get(position);
                String urlForEarthquake = currentEntry.getUrlForEarthQuake();
                Intent goToUSGSWebSite = new Intent(Intent.ACTION_VIEW);
                goToUSGSWebSite.setData(Uri.parse(urlForEarthquake));
                mContext.startActivity(goToUSGSWebSite);
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get element from your dataset at this position and replace the contents of the view with that element
        final EarthQuakeEntry currentEntry = mEqDataset.get(position);
        holder.magnitudeView.setText(currentEntry.getMagnitude());
        holder.magnitudeCircle.setColor(currentEntry.setColor());    // Set the color on the magnitude circle
        holder.nearToView.setText(currentEntry.getNearToLocation());
        holder.locationView.setText(currentEntry.getLocation());
        holder.dateView.setText(currentEntry.getDate());
        holder.timeView.setText(currentEntry.getTime());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mEqDataset.size();
    }
}



