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

import com.blongho.country_data.Currency;
import com.google.android.material.card.MaterialCardView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BoatTripRouteAdap extends ArrayAdapter<BoatTripRoute> {

    private Context context;
    private int resource;
    private Currency currency;
    private String currencyCode,currencySymbol;
    private List<BoatTripRoute> boatTripRouteList;

    public BoatTripRouteAdap(Context context, int resource, ArrayList<BoatTripRoute> boatTripRouteList) {
        super(context, resource, boatTripRouteList);
        this.context = context;
        this.resource = resource;
        this.boatTripRouteList = boatTripRouteList;
    }

    public BoatTripRouteAdap(FragmentActivity activity, int boat_route_item, ArrayList<BoatTripRoute> routes) {
        super(activity, boat_route_item, routes);
        this.context = activity;
        this.resource = boat_route_item;
        this.boatTripRouteList = routes;
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }
        BoatTripRoute boatTripRoute = getItem(position);
        currencyCode=currency.getCode();
        currencySymbol=currency.getSymbol();
        AppCompatTextView txtFrom = convertView.findViewById(R.id.route_from);
        AppCompatTextView txtTo = convertView.findViewById(R.id.route_To);
        AppCompatTextView txtThrough = convertView.findViewById(R.id.route_Through);
        AppCompatTextView txtCountry = convertView.findViewById(R.id.route_Country);
        AppCompatTextView txtStatus = convertView.findViewById(R.id.route_Status);
        MaterialCardView cardView = convertView.findViewById(R.id.card_l1);


        if (boatTripRoute != null) {
            txtCountry.setText(MessageFormat.format("Country{0}", boatTripRoute.getBtRouteCountry()));
            txtStatus.setText(MessageFormat.format("Status:{0}", boatTripRoute.getBtRouteStatus()));
            txtTo.setText(MessageFormat.format("To", boatTripRoute.getBtRouteTo()));
            txtFrom.setText(MessageFormat.format("From", boatTripRoute.getBtRouteFrom()));
            txtThrough.setText(MessageFormat.format("Through:{0}", boatTripRoute.getBtRouteThrough()));

        }

        return convertView;
    }
}
