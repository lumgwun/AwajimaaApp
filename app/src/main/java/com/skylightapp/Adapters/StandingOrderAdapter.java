package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import static java.lang.String.valueOf;


public class StandingOrderAdapter extends RecyclerView.Adapter<StandingOrderAdapter.ViewHolder> {

    private ArrayList<StandingOrder> standingOrders;
    Context context;
    String statusSwitch1;
    Profile adminProfile;
    private OnItemsClickListener listener;


    public StandingOrderAdapter(Context context, ArrayList<StandingOrder> standingOrders) {
        this.context = context;
        this.standingOrders = standingOrders;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.so_list_row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StandingOrder standingOrder = standingOrders.get(position);
        int uid =standingOrder.getUID();
        String status ="Completed";
        double amountReceived =standingOrder.getExpectedAmount();
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
        holder.soExpectedAmount.setText(MessageFormat.format("Expected Total:{0}", standingOrder.getExpectedAmount()));
        holder.receivedAmount.setText(MessageFormat.format("Amount Received:{0}", standingOrder.getReceivedAmount()));
        holder.amountDiff.setText(MessageFormat.format("Amount Diff:{0}", standingOrder.getAmountDiff()));
        holder.status.setText(standingOrder.getSoStatus());
        holder.soPix.setImageResource(R.drawable.savings);
        //holder.updateSO.getTextOn();
        holder.endDate.setText(valueOf(standingOrder.getSoEndDate()));
        if (holder.updateSO.isChecked()) {
            holder.status.setText("Completed");
            DBHelper dbHelper = new DBHelper(context.getApplicationContext());
            dbHelper.updateStandingOrder(uid,status,amountReceived,diff);
            dbHelper.insertTimeLine(tittle,details,dateOfAction,location);
            holder.soPix.setImageResource(R.drawable.completed);
        }
        else {
            holder.status.setText("Not Completed");
            holder.soPix.setImageResource(R.drawable.uncompleted);

        }
    }

    @Override
    public int getItemCount() {
        return (null != standingOrders ? standingOrders.size() : 0);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
        private SwitchCompat updateSO;
        public StandingOrder mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            soID = (TextView) view.findViewById(R.id.so_id);
            soAcctNo = (TextView) view.findViewById(R.id.so_acctNo);
            soDailyAmount = (TextView) view.findViewById(R.id.so_amount);
            soStartDate = (TextView) view.findViewById(R.id.so_start);
            soExpectedAmount = (TextView) view.findViewById(R.id.so_exp_amount);
            soPix = (BezelImageView) view.findViewById(R.id.imgSo2);
            receivedAmount = (TextView) view.findViewById(R.id.so_received);
            amountDiff = (TextView) view.findViewById(R.id.so_diff);
            status = (TextView) view.findViewById(R.id.so_status);
            endDate = (TextView) view.findViewById(R.id.so_end_Date);
            updateSO = (SwitchCompat) view.findViewById(R.id.status_so);
        }


        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + soAcctNo.getText() + "'";
        }

        @Override
        public void onClick(View view) {

        }
    }
    public interface OrderAdapterClickListener {
        void standingOrderClicked(int position);
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
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

    /*@Override
    public int getItemCount() {
        return standingOrders.size();
    }*/

    public ArrayList<StandingOrder> getData() {
        return standingOrders;
    }
    public interface OnItemsClickListener{
        void onItemClick(StandingOrder standingOrder);
    }
}
