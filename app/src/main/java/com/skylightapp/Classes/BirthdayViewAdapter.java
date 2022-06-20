package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Admins.MoroBirthdays;
import com.skylightapp.Admins.SevenDayBD;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Comparator;


public class BirthdayViewAdapter extends RecyclerView.Adapter<BirthdayViewHolder> {

    private ArrayList<Birthday> birthdays;
    Context context;

    // Constructor
    public BirthdayViewAdapter() {
        birthdays = new ArrayList<>();
    }

    public BirthdayViewAdapter(MoroBirthdays moroBirthdays, ArrayList<Birthday> birthdayArrayList) {
        this.context=moroBirthdays;
        this.birthdays=birthdayArrayList;

    }
    public BirthdayViewAdapter(Context context, ArrayList<Birthday> birthdayArrayList) {
        this.context=context;
        this.birthdays=birthdayArrayList;

    }

    public BirthdayViewAdapter(SevenDayBD sevenDayBD, ArrayList<Birthday> birthdayArrayList) {
        this.context=sevenDayBD;
        this.birthdays=birthdayArrayList;

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
        viewHolder.setTag(birthday);
        viewHolder.showView();
        viewHolder.setText("Celebrant's Name:"+birthday.getbFirstName());
        viewHolder.setDOB("DOB:"+birthday.getProfileDob());
        viewHolder.setGender("Gender:"+birthday.getProfileDob());
        viewHolder.setOffice("Office:"+birthday.getProfileDob());
        viewHolder.setPhoneNo("Phone No::"+birthday.getProfileDob());
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
        return birthdays.size();
    }

    public ArrayList<Birthday> getData() {
        return birthdays;
    }
    public interface todayAdapterClickListener {
        void birthdayClicked(int position);
    }
}
