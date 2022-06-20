package com.skylightapp.Classes;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.R;
import com.skylightapp.SuperAdmin.SuperAdminCountAct;

import java.text.MessageFormat;
import java.util.ArrayList;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.RecyclerViewHolder> {

    private ArrayList<Profile> profiles;
    private Context mcontext;

    public ProfileAdapter(ArrayList<Profile> recyclerDataArrayList, Context mcontext) {
        this.profiles = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public ProfileAdapter(SuperAdminCountAct superAdminCountAct, ArrayList<Profile> allUsers) {
        this.profiles = allUsers;
        this.mcontext = superAdminCountAct;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_user_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        Profile recyclerData = profiles.get(position);
        holder.names.setText(recyclerData.getProfileFirstName());
        holder.names.setText(MessageFormat.format("ID:{0}", recyclerData.getPID()));
        holder.names.setText(recyclerData.getProfileDateJoined());
        holder.dob.setText(recyclerData.getProfileDob());
        holder.email.setText(recyclerData.getProfileEmail());
        holder.names.setText(recyclerData.getProfileGender());
        holder.phoneNumber.setText(recyclerData.getProfilePhoneNumber());
        holder.location.setText(recyclerData.getProfileState());
        //holder.userPicture.setImageResource(recyclerData.getUProfilePicture());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView names;
        private ImageView userPicture;
        private TextView phoneNumber;
        private TextView dob;
        private TextView email;
        private TextView role;
        private TextView location;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.user_surname);

            phoneNumber = itemView.findViewById(R.id.user_first_phone);
            dob = itemView.findViewById(R.id.user_dob);
            email = itemView.findViewById(R.id.user_username);
            role = itemView.findViewById(R.id.user_joined_date);
            location = itemView.findViewById(R.id.user_id);
            userPicture = itemView.findViewById(R.id.user_image);
        }
    }
}
