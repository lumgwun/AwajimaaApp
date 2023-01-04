package com.skylightapp.Bookings;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.skylightapp.R;

import java.util.ArrayList;

public class DrawerAdapter extends BaseAdapter {
    private int images[] = { R.drawable.ic_admin_user, R.drawable.transfer3,
            R.drawable.support, R.drawable.ic_share };
    private String items[];
    private ViewHolder holder;
    private LayoutInflater inflater;
    ArrayList<AppPages> listMenu;
    private AQuery aQuery;
    private ImageOptions imageOptions;

    public DrawerAdapter(Context context, ArrayList<AppPages> listMenu) {
        // TODO Auto-generated constructor stub
        this.listMenu = listMenu;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aQuery = new AQuery(context);
        imageOptions = new ImageOptions();
        imageOptions.fileCache = true;
        imageOptions.memCache = true;
        imageOptions.fallback = R.drawable.awajima_logo;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.trip_drawer_item, parent, false);
            holder = new ViewHolder();
            holder.tvMenuItem =  convertView
                    .findViewById(R.id.tvMenuItem);
            holder.ivMenuImage = convertView
                    .findViewById(R.id.ivMenuImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            aQuery.id(holder.ivMenuImage).image(R.drawable.ic_profile);
        } else if (position == 1) {
            aQuery.id(holder.ivMenuImage).image(R.drawable.transfer3);
        } else if (position == 2) {
            aQuery.id(holder.ivMenuImage).image(R.drawable.timeline1);
        } else if (position == 3) {
            aQuery.id(holder.ivMenuImage).image(R.drawable.searchpreference_ic_history);
        } else if (position == (listMenu.size() - 1)) {
            aQuery.id(holder.ivMenuImage).image(R.drawable.ic_logout);
        } else {
            if (TextUtils.isEmpty(listMenu.get(position).getIcon())) {
                aQuery.id(holder.ivMenuImage).image(R.drawable.awajima_logo);
            } else {
                aQuery.id(holder.ivMenuImage).image(
                        listMenu.get(position).getIcon());
            }

        }
        holder.tvMenuItem.setText(listMenu.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        public AppCompatTextView tvMenuItem;
        public AppCompatImageView ivMenuImage;
    }
}
