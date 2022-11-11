package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.BirthdayViewHolder;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Comparator;


public class UpcomingBirthdayAdapter1 extends RecyclerView.Adapter<BirthdayViewHolder> {

    private ArrayList<Birthday> birthdays;
    private Context context;

    // Constructor
    public UpcomingBirthdayAdapter1() {
        birthdays = new ArrayList<>();
    }

    public UpcomingBirthdayAdapter1(Context context) {
    }

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.birthday_list_view, viewGroup, false);

        return new BirthdayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BirthdayViewHolder viewHolder, final int position) {
        final Birthday birthday = birthdays.get(position);
        final Profile userProfile = birthday.getBProfile();

        viewHolder.setTag(birthday);
        viewHolder.showView();
        viewHolder.setText("Celebrant's Name"+birthday.getbFirstName());
        viewHolder.setDOB("DOB:"+birthday.getBirthDay());
        if(userProfile !=null){
            viewHolder.setGender("Gender"+userProfile.getProfileGender());
            viewHolder.setOffice("Office"+userProfile.getProfOfficeName());

        }

        viewHolder.setPhoneNo("Phone No:"+birthday.getbPhoneNumber());
        //viewHolder.setDaysRemaining(birthday.getFormattedDaysRemainingString(theDate,birthday.getBirthDay()));
    }

    @Override
    public void onViewRecycled(BirthdayViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearBirthdays() {
        birthdays.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Birthday> getBirthdays() {
        return birthdays;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Birthday> birthdays) {
        this.birthdays = birthdays;

        Context context = AppController.getInstance();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        // Get users sort preference
        if (Integer.parseInt(sharedPref.getString(context.getString(R.string.pref_sort_by_key), "0")) == 1) {
            sortBirthdaysByName();
        } else {
            sortBirthdaysByDate();
        }
        notifyDataSetChanged();
    }

    // Sort Birthday array by closest date
    private void sortBirthdaysByDate() {
        for (Birthday b : birthdays) {
            b.setYearOfDate(Birthday.getYearOfNextBirthday(b.getbDate()));
        }

        birthdays.sort(new Comparator<Birthday>() {
            @Override
            public int compare(Birthday b1, Birthday b2) {
                return b1.getbDate().compareTo(b2.getbDate());
            }
        });
    }

    // Sort Birthday array by first name
    private void sortBirthdaysByName() {
        birthdays.sort(new Comparator<Birthday>() {
            @Override
            public int compare(Birthday b1, Birthday b2) {
                return b1.getbFirstName().compareTo(b2.getbFirstName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != birthdays ? birthdays.size() : 0);
    }

    public ArrayList<Birthday> getData() {
        return birthdays;
    }
    public interface UpcomingAdapterClickListener {
        void birthdayClicked(int position);
    }
}
