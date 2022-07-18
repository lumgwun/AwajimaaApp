package com.skylightapp.Adapters;

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
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ProfileSimpleAdapter extends ArrayAdapter implements SpinnerAdapter {

    private ArrayList<Profile> profileArrayList;
    private Context mcontext;
    private int layout;

    Context context;
    int flags[];

    LayoutInflater inflter;

    public ProfileSimpleAdapter(Context applicationContext, int layout, ArrayList<Profile> profileArrayList) {
        super(applicationContext,layout,profileArrayList);
        this.context = applicationContext;
        this.profileArrayList = profileArrayList;
        this.layout = layout;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public int getCount() {
        return (null != profileArrayList ? profileArrayList.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.profile_row, null);
        Profile profile = this.profileArrayList.get(position);
        BezelImageView icon = (BezelImageView) view.findViewById(R.id.profile_pix);
        TextView names = (TextView) view.findViewById(R.id.nameOnProfile);
        TextView status = (TextView) view.findViewById(R.id.statusOfProfile);
        //TextView role = (TextView) view.findViewById(R.id.roleOProfile);
        names.setText("Name:"+profile.getProfileLastName()+","+profile.getProfileFirstName());
        status.setText("Status:"+profile.getProfileStatus());

        /*for ( position = 0; position < profileArrayList.size(); position++) {
            names.setText((CharSequence) profileArrayList.get(position));

        }*/

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
