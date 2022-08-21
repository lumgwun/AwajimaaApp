package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.PayClientActivity;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static java.lang.String.valueOf;


public class StandingOrderAdapterC extends RecyclerView.Adapter<StandingOrderAdapterC.ViewHolder> {

    private ArrayList<StandingOrder> standingOrders;
    Context context;
    String statusSwitch1;
    Profile adminProfile;
    Customer customer;
    String firstName,surName,phone, email;
    long customerID;


    public StandingOrderAdapterC(Context context, ArrayList<StandingOrder> standingOrders) {
        this.context = context;
        this.standingOrders = standingOrders;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.so_list_row_user, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StandingOrder standingOrder = standingOrders.get(position);
        long uid =standingOrder.getUID();
        String status ="Completed";
        customer=standingOrder.getSo_Customer();
        if(customer !=null){
            customerID=customer.getCusUID();
            firstName=customer.getCusFirstName();
            email=customer.getCusEmailAddress();

        }

        double expectedAmount =standingOrder.getSo_ExpectedAmount();
        double amountReceived =standingOrder.getSo_ReceivedAmount();
        double dailyAmount =standingOrder.getSoDailyAmount();
        Account standingOrderAcct =standingOrder.getSo_Account();
        double soAcctBalance =standingOrder.getSo_Account().getAccountBalance();
        double diff =0.00;
        String dateOfAction;
        Location location=null;
        dateOfAction = valueOf(Calendar.getInstance().getTime());
        String adminName = adminProfile.getProfileLastName()+","+ adminProfile.getProfileLastName();
        String tittle = "Standing Order:"+uid+","+ "was Updated";
        String details = adminProfile.getProfileLastName()+","+ adminProfile.getProfileLastName()+"updated Standing order Status ,to Completed";
        //Message message = new Message();

        holder.soID.setText(MessageFormat.format("SO ID:{0}", standingOrder.getUID()));
        holder.soAcctNo.setText(MessageFormat.format("SO No:{0}", standingOrder.getSo_Acct_No()));
        holder.soDailyAmount.setText(MessageFormat.format("Daily Amount:{0}", standingOrder.getSoDailyAmount()));
        holder.soStartDate.setText(MessageFormat.format("Start Date:{0}", valueOf(standingOrder.getSoStartDate())));
        holder.soExpectedAmount.setText(MessageFormat.format("Expected Total:{0}", standingOrder.getSo_ExpectedAmount()));
        holder.receivedAmount.setText(MessageFormat.format("Amount Received:{0}", standingOrder.getSo_ReceivedAmount()));
        holder.amountDiff.setText(MessageFormat.format("Amount Diff:{0}", standingOrder.getSo_AmountDiff()));
        holder.status.setText(standingOrder.getSoStatus());
        holder.soPix.setImageResource(R.drawable.savings);
        if(holder.requestForPayment.isActivated()||holder.requestForPayment.isChecked()){
            Toast.makeText(context.getApplicationContext(), "You have requested for Payment", Toast.LENGTH_SHORT).show();
            if(amountReceived==expectedAmount){
                startPaymentActivity();

            }
            Bundle bundle = new Bundle();
            bundle.putString(valueOf(amountReceived), valueOf(amountReceived));
            bundle.putString("USER_FIRSTNAME", firstName);
            bundle.putString("USER_PHONE", phone);
            bundle.putString("Machine", "Customer");
            bundle.putString("USER_EMAIL", email);
            //bundle.putString("USER_DOB", dateOfBirth);
            //bundle.putString("USER_GENDER", userGender);
            //bundle.putString("USER_ADDRESS", userAddress);
            bundle.putString("State", "");
            bundle.putString("USER_NEXT_OF_KIN", "");
            bundle.putString("USER_DATE_JOINED", "");
           // bundle.putString("USERNAME", userName);
            //bundle.putString("PASSWORD", password);
            bundle.putString("USER_ROLE", "2");
            bundle.putString("Profile Picture", "");
            bundle.putString(PROFILE_ID, valueOf(customerID));
        }
        holder.endDate.setText(valueOf(standingOrder.getSoEndDate()));
        if (status.equals(context.getString(R.string.completed))) {
            holder.soPix.setImageResource(R.drawable.completed);
        }
        else {
            holder.soPix.setImageResource(R.drawable.uncompleted);

        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView soID;
        public final TextView soAcctNo;
        public final TextView soDailyAmount;
        public final TextView soStartDate;
        public final BezelImageView soPix;
        public final TextView soExpectedAmount;
        public final TextView amountDiff;
        public final TextView status;
        public final TextView receivedAmount;
        public final TextView endDate;
        private SwitchCompat requestForPayment;
        public StandingOrder mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            soID = (TextView) view.findViewById(R.id.so_id3);
            soAcctNo = (TextView) view.findViewById(R.id.so_acctNo4);
            soDailyAmount = (TextView) view.findViewById(R.id.so_amount5);
            soStartDate = (TextView) view.findViewById(R.id.so_start4);
            soExpectedAmount = (TextView) view.findViewById(R.id.so_exp_amount4);
            soPix = (BezelImageView) view.findViewById(R.id.so_status_pic3);
            receivedAmount = (TextView) view.findViewById(R.id.so_received4);
            amountDiff = (TextView) view.findViewById(R.id.so_diff4);
            status = (TextView) view.findViewById(R.id.so_status4);
            endDate = (TextView) view.findViewById(R.id.so_end_Date4);
            requestForPayment = (SwitchCompat) view.findViewById(R.id.btn_more);
        }


        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + soAcctNo.getText() + "'";
        }
    }
    public interface OrderAdapterClickListener {
        void standingOrderClicked(int position);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<StandingOrder> standingOrders) {
        this.standingOrders = standingOrders;

        Context context = AppController.getInstance();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        // Get users sort preference
        if (Integer.parseInt(sharedPref.getString(context.getString(R.string.pref_sort_by_key), "0")) == 1) {
            sortSOsByName();
        }
        notifyDataSetChanged();
    }

    private void sortSOsByName() {
        standingOrders.sort(new Comparator<StandingOrder>() {
            @Override
            public int compare(StandingOrder b1, StandingOrder b2) {
                return b1.getSoStatus().compareTo(b2.getSoStatus());
            }
        });
    }
    private void startPaymentActivity() {
        Intent intent = new Intent(context, PayClientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


    @Override
    public int getItemCount() {
        return (null != standingOrders ? standingOrders.size() : 0);
    }

    public ArrayList<StandingOrder> getData() {
        return standingOrders;
    }
}
