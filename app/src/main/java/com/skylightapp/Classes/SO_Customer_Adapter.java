package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.R;
import com.skylightapp.UserPrefActivity;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;


public class SO_Customer_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<StandingOrder> standingOrders;
    Customer customer;
    Profile profile;

    public SO_Customer_Adapter(Context context, int simple_list_item_1, ArrayList<StandingOrder> orderArrayList) {

        this.context = context;
        this.standingOrders = orderArrayList;
    }


    @Override
    public int getCount() {
        return standingOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return standingOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            convertView = inflater.inflate(R.layout.so_list_row_user, null, true);

            holder.soID =  convertView.findViewById(R.id.so_id3);
            holder.acctNo = convertView.findViewById(R.id.so_acctNo4);
            holder.soDailyAmount =  convertView.findViewById(R.id.so_amount5);
            holder.soExpectedAmount = convertView.findViewById(R.id.so_exp_amount4);
            holder.soAmountDiff = convertView.findViewById(R.id.so_diff4);
            holder.status = convertView.findViewById(R.id.so_status4);
            holder.moreBtn =  convertView.findViewById(R.id.btn_more);
            holder.soExpectedAmount = convertView.findViewById(R.id.so_exp_amount4);
            holder.soReceivedAmount = convertView.findViewById(R.id.so_received4);
            holder.statusImage = convertView.findViewById(R.id.so_status_pic3);
            holder.soStartDate = convertView.findViewById(R.id.so_start4);
            holder.soEndDate = convertView.findViewById(R.id.so_end_Date4);
            holder.actionBtn = convertView.findViewById(R.id.btn_action_request);


            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        StandingOrder standingOrder =standingOrders.get(position);
        profile=standingOrder.getProfile();
        double dailyAmount = standingOrder.getSoDailyAmount();
        double amountReceived = standingOrder.getReceivedAmount();
        double amountExpected = standingOrder.getExpectedAmount();
        String soStatus = standingOrder.getSoStatus();
        double balance =standingOrder.getAccount().getAccountBalance();
        double newBalance =balance-amountReceived;
        long accountNo = (long) standingOrder.getAccount().getSkyLightAcctNo();
        long soID = standingOrder.getUID();
        Bundle payBundle = new Bundle();
        double newBalanceForCustomer =standingOrder.getAccount().setBalance(newBalance);
        if(profile !=null){
            payBundle.putString("USER_ROLE", String.valueOf(profile.getProfileType()));
            payBundle.putString("USER_OFFICE", profile.getProfileOffice());

        }

        Intent usersIntent = new Intent(context.getApplicationContext(), UserPrefActivity.class);
        payBundle.putString("TRANSACTION_AMOUNT", String.valueOf(amountReceived));
        payBundle.putString("USER_STATE", customer.getCusState());
        payBundle.putString(PROFILE_PASSWORD, customer.getCusPassword());
        usersIntent.putExtras(payBundle);

        // Start the next activity
        //UserSignUpActivity.this.startActivity(usersIntent);
        double amountDiff = standingOrder.getAmountDiff();
        holder.soID.setText(MessageFormat.format("SO ID: {0}", standingOrders.get(position).getUID()));
        holder.acctNo.setText(MessageFormat.format("SO ID: {0}", standingOrders.get(position).getSo_Acct_No()));
        holder.soExpectedAmount.setText(MessageFormat.format("Expected Amount: {0}", standingOrders.get(position).getExpectedAmount()));
        holder.soReceivedAmount.setText(MessageFormat.format("SO Received Amount: {0}", standingOrders.get(position).getReceivedAmount()));
        holder.soDailyAmount.setText(MessageFormat.format("SO Daily Amount: {0}", standingOrders.get(position).getSoDailyAmount()));
        holder.soStartDate.setText(MessageFormat.format("SO Start Date: {0}", standingOrders.get(position).getSoStartDate()));
        holder.soEndDate.setText(MessageFormat.format("SO End Date: {0}", standingOrders.get(position).getSoEndDate()));
        holder.soAmountDiff.setText(MessageFormat.format("SO Amount Remaining: {0}", standingOrders.get(position).getAmountDiff()));
        holder.status.setText(MessageFormat.format("SO Status: {0}", standingOrders.get(position).getSoStatus()));
        if(holder.status.equals("Uncompleted")){
            holder.statusImage.setImageResource(R.drawable.uncompleted);
        }
        if(holder.status.equals("Completed")){
            holder.statusImage.setImageResource(R.drawable.completed);
        }
        holder.actionBtn.setOnContextClickListener(new View.OnContextClickListener() {
            @Override
            public boolean onContextClick(View v) {
                return false;
            }
        });
        if(amountReceived==amountExpected){
            holder.actionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPaymentDialog();

                }
            });

        }

        if(amountReceived<amountExpected){
            holder.actionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDialog();

                }
            });

        }

        return convertView;
    }

    private static class ViewHolder {

        protected AppCompatTextView soID,soDailyAmount,acctNo, soStartDate,soEndDate,soExpectedAmount, soReceivedAmount,soAmountDiff,status;
        BezelImageView statusImage;
        AppCompatButton moreBtn,actionBtn;
    }
    public void startPaymentDialog(){
        final String[] soOptions = {
                "Request Payment", "Go Back to List","Go Back to Dashboard"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setTitle("Select an Option for Your Savings");
        builder.setItems(soOptions, new DialogInterface.OnClickListener() {@
                Override
        public void onClick(DialogInterface dialog, int which) {
            if ("Request Payment".equals(soOptions[which])) {
                Toast.makeText(context.getApplicationContext(), "you want to request for payment", Toast.LENGTH_SHORT).show();
            } else if ("Go Back to List".equals(soOptions[which])) {
                Toast.makeText(context.getApplicationContext(), "Taking you back to SO List", Toast.LENGTH_SHORT).show();
            } else if ("Go Back to Dashboard".equals(soOptions[which])) {
                Toast.makeText(context.getApplicationContext(), "Taking you back to Dashboard", Toast.LENGTH_SHORT).show();
                // the user clicked on colors[which]

            }
        }

        });
        builder.show();

    }

    public void startDialog(){
        final String[] soOptions = {"Do Manual Payment Completion ","Go Back to List","Go Back to Dashboard"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setTitle("Select an Option for Your Savings");
        builder.setItems(soOptions, new DialogInterface.OnClickListener() {@
                Override
        public void onClick(DialogInterface dialog, int which) {
            if ("Do Manual Payment Completion".equals(soOptions[which])) {
                Toast.makeText(context.getApplicationContext(), "Do Manual Payment to complete your savings", Toast.LENGTH_SHORT).show();
            } else if ("Go Back to List".equals(soOptions[which])) {
                Toast.makeText(context.getApplicationContext(), "Taking you back to SO List", Toast.LENGTH_SHORT).show();
            } else if ("Go Back to Dashboard".equals(soOptions[which])) {
                Toast.makeText(context.getApplicationContext(), "Taking you back to Dashboard", Toast.LENGTH_SHORT).show();

            }
        }

        });
        builder.show();

    }
}
