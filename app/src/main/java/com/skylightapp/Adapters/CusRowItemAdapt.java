package com.skylightapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Customer;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class CusRowItemAdapt extends RecyclerView.Adapter<CusRowItemAdapt.ViewHolder> {
    private final ArrayList<Customer> mValues;
    Context context;
    String statusSwitch1;


    public CusRowItemAdapt(Context context, ArrayList<Customer> birthdays) {
        this.context = context;
        this.mValues = birthdays;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cus_frag_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        final Customer customer = mValues.get(position);
        holder.nameOfCustomer.setText(MessageFormat.format("{0},{1}", customer.getCusSurname(), customer.getCusFirstName()));

        holder.phoneNumberOfCustomer.setText(customer.getCusPhoneNumber());
        holder.emailOfCustomer.setText(customer.getCusEmail());

        holder.emailOfCustomer.setText(customer.getCusEmailAddress());
        holder.genderOfCustomer.setText(customer.getCusGender());
        holder.dateOfJoining.setText(customer.getCusDateJoined());
        holder.pictureOfCustomer.setImageURI(Uri.parse(customer.getCusProfilePicture()));
        holder.state.setText("Birth Date: "+customer.getCusState());
        holder.userName.setText(customer.getCusUserName());
        holder.switchCompat.setText("");
        if (holder.switchCompat.isChecked())
            holder.switchCompat.setText("Approved");
        else
            holder.switchCompat.setText("Not Approved yet");
    }

    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameOfCustomer;
        public final TextView phoneNumberOfCustomer;
        public final TextView emailOfCustomer;
        public final TextView addressOfCustomer;
        public final BezelImageView pictureOfCustomer;
        public final TextView genderOfCustomer;
        public final TextView dateOfJoining;
        public final TextView userName;
        public final TextView state;
        public final SwitchCompat switchCompat;

        public Customer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameOfCustomer = (TextView) view.findViewById(R.id.nameOfCustomer);
            phoneNumberOfCustomer = (TextView) view.findViewById(R.id.phoneNumberOfCustomer);
            emailOfCustomer = (TextView) view.findViewById(R.id.emailOfCustomer);
            addressOfCustomer = (TextView) view.findViewById(R.id.addressOfCustomer);
            genderOfCustomer = (TextView) view.findViewById(R.id.genderOCustomer);
            pictureOfCustomer = (BezelImageView) view.findViewById(R.id.customer_pix);
            state = (TextView) view.findViewById(R.id.customer_state);
            dateOfJoining = (TextView) view.findViewById(R.id.dateOfCustomer2);
            userName = (TextView) view.findViewById(R.id.username2);
            switchCompat = (SwitchCompat) view.findViewById(R.id.switch_customer_action);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + nameOfCustomer.getText() + "'";
        }
    }
}
