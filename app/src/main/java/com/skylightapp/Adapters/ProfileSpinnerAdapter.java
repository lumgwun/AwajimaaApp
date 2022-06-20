package com.skylightapp.Adapters;

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

public class ProfileSpinnerAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    String[] depositorNames;
    LayoutInflater inflter;
    int layout;
    private List<Profile> profileList;
    private ArrayList<Profile> profileArrayList;
    private ArrayList<String> stringArrayList;

    public ProfileSpinnerAdapter(Context applicationContext, int layout, ArrayList<String> stringArrayList) {
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
        view = inflter.inflate(R.layout.teller_spn_row, null);
        final String depositor = stringArrayList.get(position);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(R.drawable.user3);
        //view.setOnClickListener(this);
        names.setText(MessageFormat.format("Teller:{0}{1}", depositor));
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
