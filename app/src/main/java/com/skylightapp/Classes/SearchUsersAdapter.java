package com.skylightapp.Classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Interfaces.OnObjectChangedListener;

import java.util.ArrayList;
import java.util.List;


public class SearchUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = SearchUsersAdapter.class.getSimpleName();

    private List<Profile> itemsList = new ArrayList<>();

    //private UserViewHolder.Callback callback;
    private Activity activity;

    public SearchUsersAdapter(Activity activity) {
        this.activity = activity;
    }

    /*public void setCallback(UserViewHolder.Callback callback) {
        this.callback = callback;
    }*/

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //return new UserViewHolder(inflater.inflate(R.layout.user_list_row_2, parent, false),
              //  callback, activity);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //((UserViewHolder) holder).bindData(itemsList.get(position));
    }

    public void setList(List<Profile> list) {
        itemsList.clear();
        itemsList.addAll(list);
        notifyDataSetChanged();
    }

    public void updateItem(int position) {
        Profile profile = getItemByPosition(position);
        ProfileManager.getInstance(activity.getApplicationContext()).getProfileSingleValue(String.valueOf(profile.getPID()), new OnObjectChangedListener<Profile>() {
            //@Override
            public void onObjectChanged(Profile updatedProfile) {
                itemsList.set(position, updatedProfile);
                notifyItemChanged(position);
            }

            @Override
            public void onSavingsChanged(CustomerDailyReport obj) {

            }

            @Override
            public void onPackageChanged(SkyLightPackage obj) {

            }

            @Override
            public void onLoanChanged(Loan obj) {

            }

            @Override
            public void onManagerChanged(AdminUser obj) {

            }

            @Override
            public void onPhoneNumberChanged(int phoneNo) {

            }

            @Override
            public void onStandingOrgerChanged(StandingOrder obj) {

            }

            @Override
            public void onError(String errorText) {

            }

        });
    }

    public Profile getItemByPosition(int position) {
        return itemsList.get(position);
    }
}
