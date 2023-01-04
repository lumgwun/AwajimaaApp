package com.skylightapp.StateDir;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.skylightapp.R;

import java.util.ArrayList;

public class RouteAdapter extends ArrayAdapter<ClusterUserList> {

    private Context mContext;
    private ArrayList<ClusterUserList> clusterUserLists;

    RouteAdapter(@NonNull Context context, ArrayList<ClusterUserList> objects) {
        super(context, 0, objects);
        mContext = context;
        clusterUserLists = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        Log.e(GeocodeConstants.TAG_MAIN,"Check3");
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.route_items,parent,false);

        ClusterUserList currentUser = clusterUserLists.get(position);

        AppCompatTextView cluster = listItem.findViewById(R.id.cluster);
        Log.e(GeocodeConstants.TAG_MAIN,"name : " + currentUser.getCluster());
        cluster.setText(currentUser.getCluster());

        return listItem;
    }
}
