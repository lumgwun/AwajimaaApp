package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.String.valueOf;

public class MyBirthdayRowItemRecyclerViewAdapter extends RecyclerView.Adapter<MyBirthdayRowItemRecyclerViewAdapter.ViewHolder> {
    private static String name;
    // DummyItem =Birthday

    private ArrayList<Birthday> mValues;
    Context context;
    String statusSwitch1;
    String phoneNumber;


    public MyBirthdayRowItemRecyclerViewAdapter(Context context, ArrayList<Birthday> birthdays) {
        this.context = context;
        this.mValues = birthdays;
    }

    public MyBirthdayRowItemRecyclerViewAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frag_birthday_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        final Birthday birthday = mValues.get(position);
        final Profile profile = mValues.get(position);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(calendar.getTime());
        String myDate = birthday.getBirthDay();
        name = birthday.getbFirstName();
        phoneNumber = birthday.getProfilePhoneNumber();
        holder.nameOfCelebrant.setText(MessageFormat.format("{0},{1}", profile.getProfileLastName(), profile.getProfileFirstName()));

        holder.phoneNumberOfCelebrant.setText(MessageFormat.format("Phone No:{0}", profile.getProfilePhoneNumber()));
        holder.emailOfCelebrant.setText(MessageFormat.format("Email:{0}", profile.getProfileEmail()));
        holder.addressOfCelebrant.setText(MessageFormat.format("Address:{0}", valueOf(profile.getProfileAddress())));
        holder.genderOfCelebrant.setText(MessageFormat.format("Gender:{0}", profile.getProfileGender()));

        holder.daysBtwn.setText(MessageFormat.format("Days Btwn:{0}", birthday.getDaysInBetween(todayDate, myDate)));
        holder.daysRemaining.setText(MessageFormat.format("Days Rem:{0}", birthday.getBirthDay()));

        holder.dateJoined.setText(profile.getProfileDateJoined());
        holder.dateOfCelebrant.setText(valueOf(birthday.getbDate()));
        holder.switchCompat.setText("");

        if (holder.switchCompat.isChecked())
            holder.statusOfCelebrant.setText("Celebrated");
        else
            holder.statusOfCelebrant.setText("Not Celebrated");

    }

    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameOfCelebrant;
        public final TextView phoneNumberOfCelebrant;
        public final TextView emailOfCelebrant;
        public final TextView addressOfCelebrant;
        public final BezelImageView pictureOfCelebrant;
        public final TextView genderOfCelebrant;
        public final TextView dateOfCelebrant;
        public final TextView statusOfCelebrant;
        public final TextView dateJoined;
        public final TextView daysBtwn;
        public final TextView daysRemaining;
        private AppCompatButton birthday_more, sendMessage;
        public final SwitchCompat switchCompat;

        private LinearLayoutCompat l1, l2, l3;

        public Birthday mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            l1 = view.findViewById(R.id.birth_l1a);
            l2 = view.findViewById(R.id.birth_l2);
            l3 = view.findViewById(R.id.loan_l51);
            nameOfCelebrant = (TextView) view.findViewById(R.id.nameOfCelebrant);
            phoneNumberOfCelebrant = (TextView) view.findViewById(R.id.phoneNumberOfCelebrant);
            emailOfCelebrant = (TextView) view.findViewById(R.id.emailOfCelebrant);
            addressOfCelebrant = (TextView) view.findViewById(R.id.addressOfCelebrant);
            genderOfCelebrant = (TextView) view.findViewById(R.id.genderOfCelebrant);
            pictureOfCelebrant = (BezelImageView) view.findViewById(R.id.celebrant_pix);
            dateJoined = (TextView) view.findViewById(R.id.dateJoined);
            dateOfCelebrant = (TextView) view.findViewById(R.id.dateOfCelebrant);
            statusOfCelebrant = (TextView) view.findViewById(R.id.statusOfCelebrant);
            daysBtwn = (TextView) view.findViewById(R.id.daysB);
            sendMessage = view.findViewById(R.id.sendMessage);

            birthday_more = view.findViewById(R.id.birthday_more);
            daysRemaining = (TextView) view.findViewById(R.id.daysR);
            switchCompat = (SwitchCompat) view.findViewById(R.id.switch_birthday_action);

            birthday_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                    l3.setVisibility(View.VISIBLE);

                }
            });
            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendBirthdayMessage();


                }
            });
        }


        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + phoneNumberOfCelebrant.getText() + "'";
        }
    }

    public interface CurrentAdapterClickListener {
        void birthdayClicked(int position);
    }

    public static void sendBirthdayMessage() {
        String birthdayMessage = "Hello" + name + "Skylight wishes you a very blessed new year, continue to save small, small,to achieve big things, Hurray!";


    }
}
