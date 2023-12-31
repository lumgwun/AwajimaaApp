package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CusSimpleAdapter extends ArrayAdapter implements SpinnerAdapter {

    private ArrayList<Customer> users;
    private Context mcontext;
    private int layout;
    Context context;
    LayoutInflater inflter;

    public CusSimpleAdapter(Context applicationContext, int layout, ArrayList<Customer> customerArrayList) {
        super(applicationContext,layout,customerArrayList);
        this.context = applicationContext;
        this.users = customerArrayList;
        this.layout = layout;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public int getCount() {
        return (null != users ? users.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.cus_item, null);
        BezelImageView icon = (BezelImageView) view.findViewById(R.id.userP);
        TextView names = (TextView) view.findViewById(R.id.UCusName);
        TextView role = (TextView) view.findViewById(R.id.UCusRole);
        for ( i = 0; i < users.size(); i++) {
            names.setText((CharSequence) users.get(i));

        }
        return view;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
