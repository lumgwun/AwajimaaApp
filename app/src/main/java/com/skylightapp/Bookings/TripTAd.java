package com.skylightapp.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.R;

import java.util.List;

public class TripTAd extends RecyclerView.Adapter<TripTAd.MyViewHolder> {

    private List<Trip> tripList;

    private View.OnClickListener listener;

    private LayoutInflater inflater;

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView trackingId, tripId, stop;

        MyViewHolder(View view) {
            super(view);
            trackingId = view.findViewById(R.id.trackingId);
            stop = view.findViewById(R.id.stop);
            tripId =  view.findViewById(R.id.tripId);
        }
    }

    public TripTAd(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Trip> trips, View.OnClickListener listener) {
        this.tripList = trips;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.trip_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.trackingId.setText(trip.getTripId());
        holder.stop.setTag(trip.getTripId());
        holder.stop.setOnClickListener(listener);
        holder.tripId.setText(trip.getTripId());
    }
    @Override
    public int getItemCount() {
        return (null != tripList ? tripList.size() : 0);
    }

}
