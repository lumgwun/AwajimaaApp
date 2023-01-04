package com.skylightapp.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidquery.AQuery;
import com.skylightapp.R;

import java.util.ArrayList;

public class VehicleTypeListAd extends BaseAdapter {

    private ArrayList<VehicalType> listVehicalType;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private AQuery aQuery;
    public static int seletedPosition = 0;
    MapFragment mapfrag;

    public VehicleTypeListAd(Context context,
                             ArrayList<VehicalType> listVehicalType, MapFragment mapfrag) {
        this.listVehicalType = listVehicalType;
        this.mapfrag = mapfrag;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listVehicalType.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.vehicle_type, parent,
                    false);
            holder = new ViewHolder();
            holder.tvType = convertView.findViewById(R.id.tvType);
            holder.ivIcon =  convertView.findViewById(R.id.ivIcon);
            holder.ivSelectService =  convertView
                    .findViewById(R.id.ivSelectService);

            // holder.viewSeprater = (View) convertView
            // .findViewById(R.id.seprateView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        aQuery = new AQuery(convertView);
        holder.tvType.setText(listVehicalType.get(position).getName() + "");
        holder.ivIcon.setTag(position);
        aQuery.id(holder.ivIcon).image(listVehicalType.get(position).getIcon(),
                true, true);
        if (listVehicalType.get(position).isSelected) {
            holder.ivSelectService.setVisibility(View.VISIBLE);
            holder.ivSelectService
                    .setImageResource(R.drawable.selected_indicator);
        } else {
            // holder.ivSelectService.setBackgroundColor(Color.TRANSPARENT);
            holder.ivSelectService.setVisibility(View.INVISIBLE);

        }

        // if (position == listVehicalType.size() - 1) {
        // holder.viewSeprater.setVisibility(View.GONE);
        // } else {
        // holder.viewSeprater.setVisibility(View.VISIBLE);
        // }

        return convertView;
    }

    private class ViewHolder {
        AppCompatTextView tvType;
        AppCompatImageView ivIcon, ivSelectService;
        // View viewSeprater;
    }

}
