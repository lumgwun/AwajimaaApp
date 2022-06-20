package com.skylightapp.Inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class StockNameAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    LayoutInflater inflter;
    int layout;
    private ArrayList<String> stringArrayList;

    public StockNameAdapter(Context applicationContext, int layout, ArrayList<String> stringArrayList) {
        this.context = applicationContext;
        this.layout = layout;
        this.stringArrayList = stringArrayList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return (null != stringArrayList ? stringArrayList.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.stock_spn_row, null);
        final String name = stringArrayList.get(position);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView44);
        TextView names = (TextView) view.findViewById(R.id.textStocks);
        icon.setImageResource(R.drawable.user3);
        //view.setOnClickListener(this);
        names.setText(MessageFormat.format("Stock:{0}{1}", name));
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
