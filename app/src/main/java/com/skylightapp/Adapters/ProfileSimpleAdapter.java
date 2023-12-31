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

@SuppressWarnings("ALL")
public class ProfileSimpleAdapter extends ArrayAdapter implements SpinnerAdapter {

    private ArrayList<Profile> profileArrayList;
    private Context mcontext;
    private int layout;

    Context context;
    int flags[];

    LayoutInflater inflter;
    Uri profPic;

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
        if(profile !=null){
            profPic=profile.getProfilePicture();


        }
        BezelImageView icon = (BezelImageView) view.findViewById(R.id.profile_pix);
        TextView names = (TextView) view.findViewById(R.id.nameOnProfile);
        TextView role = (TextView) view.findViewById(R.id.roleOUser);
        TextView status = (TextView) view.findViewById(R.id.statusOfProfile);
        if (profile != null) {
            names.setText(MessageFormat.format("Name:{0},{1}", profile.getProfileLastName(), profile.getProfileFirstName()));
        }
        if (profile != null) {
            status.setText(MessageFormat.format("Status:{0}", profile.getProfileStatus()));
        }
        if (profile != null) {
            role.setText(MessageFormat.format("Role:{0}", profile.getProfileRole()));
        }

        Glide.with(context)
                .load(profPic)
                .error(R.drawable.ic_admin_user)
                .override(50, 50)
                .centerCrop() // scale to fill the ImageView and crop any extra
                .into(icon);

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
