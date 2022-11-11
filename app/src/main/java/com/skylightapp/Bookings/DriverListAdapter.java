package com.skylightapp.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.skylightapp.R;

import java.util.ArrayList;

public class DriverListAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    private ViewHolder holder;
    private ArrayList<Driver> listDriver;

    public DriverListAdapter(Context context, ArrayList<Driver> listDriver) {
        this.listDriver = listDriver;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return listDriver.size();
    }

    @Override
    public Object getItem(int position) {
        return listDriver.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.driver_item, parent, false);
            holder = new ViewHolder();

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder {

    }
}
