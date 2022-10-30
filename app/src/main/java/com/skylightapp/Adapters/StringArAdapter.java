package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class StringArAdapter extends ArrayAdapter implements SpinnerAdapter {

    private ArrayList<String> stateArrayList;
    private Context mcontext;
    private int layout;

    Context context;
    int flags[];

    LayoutInflater inflter;
    Uri profPic;

    public StringArAdapter(Context applicationContext, int item_string, ArrayList<String> stringArrayList) {
        super(applicationContext,item_string,stringArrayList);
        this.context = applicationContext;
        this.stateArrayList = stringArrayList;
        this.layout = item_string;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public int getCount() {
        return (null != stateArrayList ? stateArrayList.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_string, null);
        String state = this.stateArrayList.get(position);
        BezelImageView icon = (BezelImageView) view.findViewById(R.id.state_img);
        TextView txtState = view.findViewById(R.id.report_state);

        txtState.setText(MessageFormat.format("Selection:{0}", state));

        Glide.with(context)
                .load(profPic)
                .error(R.drawable.ic_admin_panel)
                .override(50, 50)
                .centerCrop() // scale to fill the ImageView and crop any extra
                .into(icon);

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
