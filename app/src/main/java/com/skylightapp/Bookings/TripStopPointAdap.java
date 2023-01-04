package com.skylightapp.Bookings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;


import com.google.android.material.card.MaterialCardView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TripStopPointAdap extends ArrayAdapter<TripStopPoint> {

    private Context context;
    private int resource;
    private String placeName,placeState,placeStatus;
    private List<TripStopPoint> stopPoints;

    public TripStopPointAdap(Context context, int resource, ArrayList<TripStopPoint> stopPoints) {
        super(context, resource, stopPoints);
        this.context = context;
        this.resource = resource;
        this.stopPoints = stopPoints;
    }

    public TripStopPointAdap(FragmentActivity activity, int trip_stop_point_item, ArrayList<TripStopPoint> tripStopPoints) {
        super(activity, trip_stop_point_item, tripStopPoints);
        this.context = activity;
        this.resource = trip_stop_point_item;
        this.stopPoints = tripStopPoints;
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        TripStopPoint tripStopPoint = getItem(position);
        if(tripStopPoint !=null){
            placeName=tripStopPoint.getbTSPName();
            placeStatus=tripStopPoint.getbTSPStatus();
            placeState=tripStopPoint.getbTSPState();
        }
        MaterialCardView cardView = convertView.findViewById(R.id.tsp_card);
        AppCompatTextView txtStatus = convertView.findViewById(R.id.tsp_statusQ);
        AppCompatTextView txtPlaceName = convertView.findViewById(R.id.tsp_name);
        AppCompatTextView txtState = convertView.findViewById(R.id.tsp_state);
        txtPlaceName.setText(MessageFormat.format("Place Name:{0}", placeName));
        txtState.setText(MessageFormat.format("Place State:{0}", placeState));
        txtStatus.setText(MessageFormat.format("Place Status:{0}", placeStatus));
        return convertView;
    }
}
