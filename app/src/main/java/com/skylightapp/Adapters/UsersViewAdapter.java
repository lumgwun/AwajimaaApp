package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.User;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;


public class UsersViewAdapter extends RecyclerView.Adapter<UsersViewAdapter.RecyclerViewHolder> {

    private ArrayList<User> users;
    private Context mcontext;

    public UsersViewAdapter(ArrayList<User> recyclerDataArrayList, Context mcontext) {
        this.users = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row_2, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        User recyclerData = users.get(position);
        //Profile profile = (Profile) users.get(position);
        /*holder.names.setText(MessageFormat.format("{0},{1}", recyclerData.getUSurname(), recyclerData.getUFirstName()));
        holder.dateJoined.setText(recyclerData.getUDateUserJoined());
        holder.phoneNumber.setText(recyclerData.getUPhoneNumber());
        holder.dob.setText(recyclerData.getUDob());
        holder.email.setText(recyclerData.getUEmailAddress());
        holder.typeOfUser.setText(recyclerData.getUType());
        holder.gender.setText(recyclerData.getUGender());
        holder.status.setText(profile.getProfileStatus());
        //holder.address.setText(valueOf(recyclerData.getAddress()));
        holder.dateJoined.setText(recyclerData.getUDateUserJoined());
        holder.userName.setText(recyclerData.getUUserName());*/
        //holder.userPicture.setImageResource(recyclerData.getUProfilePicture());
        /*Glide.with(holder.userPicture)
                .load(recyclerData.getProfilePicture())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_alert)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(holder.userPicture);*/
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != users ? users.size() : 0);
    }

    // View Holder Class to handle Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView names;
        private final BezelImageView userPicture;
        private TextView phoneNumber;
        private TextView dob;
        private TextView email;
        private TextView typeOfUser;
        private TextView address;
        private TextView dateJoined;
        private TextView location;
        private TextView userName;
        private TextView gender;
        private TextView status;
        private SwitchCompat statusSetter;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.nameOfUser);

            phoneNumber = itemView.findViewById(R.id.phoneNumberOfUser);
            dob = itemView.findViewById(R.id.dateOfBirth);
            email = itemView.findViewById(R.id.emailOfUser);
            typeOfUser = itemView.findViewById(R.id.roleOUser);
            gender = itemView.findViewById(R.id.genderOUser);
            status = itemView.findViewById(R.id.user_status3);
            userName = itemView.findViewById(R.id.usernameOfUser);
            //location = itemView.findViewById(R.id.iditemTitle);
            address = itemView.findViewById(R.id.addressOfUser);
            dateJoined = itemView.findViewById(R.id.dateOfUser2);
            statusSetter = itemView.findViewById(R.id.switch_user_status);
            userPicture = itemView.findViewById(R.id.user_pix11);
        }
    }
}
