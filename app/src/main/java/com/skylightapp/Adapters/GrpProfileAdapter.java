package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MyGrpSavingsUsers;
import com.skylightapp.R;

import java.util.ArrayList;

public class GrpProfileAdapter extends RecyclerView.Adapter<GrpProfileAdapter.RecyclerViewHolder> {

    private ArrayList<Profile> users;
    private Context mcontext;
    private OnItemsClickListener listener;

    public GrpProfileAdapter(ArrayList<Profile> recyclerDataArrayList, Context mcontext) {
        this.users = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public GrpProfileAdapter(MyGrpSavingsUsers myGrpSavingsUsers, ArrayList<Profile> profileArrayList) {
        this.users = profileArrayList;
        this.mcontext = mcontext;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grp_profile_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Profile profile = (Profile) users.get(position);
        holder.names.setText(profile.getProfileLastName()+","+profile.getProfileFirstName());
        holder.dateJoined.setText(profile.getProfileDateJoined());
        holder.phoneNumber.setText(profile.getProfilePhoneNumber());
        holder.status.setText(profile.getProfileStatus());
        holder.profileID.setText("User ID:"+profile.getPID());
        holder.dateJoined.setText(profile.getProfileDateJoined());
        //holder.userPicture.setImageResource(profile.getUProfilePicture());
        //Glide.with(context).load(ImageUrl).placeholder(drawable)
        // .error(drawable).animate(R.anim.base_slide_right_in)
        //.centerCrop().into(holder.imagView);
    }

    @Override
    public int getItemCount() {
        return (null != users ? users.size() : 0);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView names;
        private final BezelImageView userPicture;
        private TextView phoneNumber;
        private TextView dateJoined;
        private TextView status,profileID;
        public Profile mItem;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.nameOfUserS);
            phoneNumber = itemView.findViewById(R.id.grpProfilePhone);
            status = itemView.findViewById(R.id.grpProfileStatus);
            profileID = itemView.findViewById(R.id.grpProfileID);
            dateJoined = itemView.findViewById(R.id.grpProfileJoined);
            userPicture = itemView.findViewById(R.id.user_pix1155);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setPaymentList(ArrayList<? extends Profile> profile) {
        users.clear();
        users.addAll(profile);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLast(Profile profile) {
        users.add(profile);
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(Profile profile);
    }
}
