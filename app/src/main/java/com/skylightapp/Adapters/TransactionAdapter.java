package com.skylightapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Classes.User;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;



public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> implements ListAdapter {

    private Context context;
    private int resource;
    String statusSwitch1;
    private final ArrayList<Transaction> transactions;
    DBHelper applicationDb;
    String status;
    Profile userProfile;
    Customer customer;
    User user;
    private TranXDAO tranXDAO;
    SharedPreferences userPreferences;
    private Gson gson;
    private OnItemsClickListener listener;


    public TransactionAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super();
        this.context = context;
        this.resource = resource;
        this.transactions = transactions;
    }

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions2) {
        this.context = context;
        this.transactions = transactions2;
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trans_list_row, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return (null != transactions ? transactions.size() : 0);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTransactionTitle;
        public final TextView txtTransactionTimestamp;
        public final TextView txtTransactionAmount;
        public final TextView packageID;
        public final AppCompatImageView imgTransactionIcon;
        public final TextView refNo;
        public final CardView cardView;
        public final TextView package_startDate;
        public final TextView payee;
        public final TextView destinationAccount;
        public final TextView sendingAccount;
        public final TextView savingsID;
        public final TextView noOfDays;
        public final TextView status;
        public final SwitchCompat switchCompat;

        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cardView = view.findViewById(R.id.card_view_transaction);
             imgTransactionIcon = view.findViewById(R.id.txId);
            txtTransactionTitle = view.findViewById(R.id.txt_transaction_type);
            refNo = view.findViewById(R.id.ref_id1);
            package_startDate = view.findViewById(R.id.package_start_date3);
            txtTransactionAmount = view.findViewById(R.id.transaction_amount2);
            packageID = view.findViewById(R.id.txt_package_id);
            txtTransactionTimestamp = view.findViewById(R.id.txt_transact_timestamp);
            payee = view.findViewById(R.id.payee);
            sendingAccount = view.findViewById(R.id.sendingAccount);
            savingsID = view.findViewById(R.id.savingsID);
            destinationAccount = view.findViewById(R.id.destinationAccount);
            noOfDays = view.findViewById(R.id.noOfDays);
            status = view.findViewById(R.id.status11);
            //txtTransactionInfo.setVisibility(View.VISIBLE);
            switchCompat = (SwitchCompat) view.findViewById(R.id.switch_tx_action);

        }


    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = transactions.get(position);
        final Transaction transaction = transactions.get(position);
        final CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        final SkyLightPackage skyLightPackage = new SkyLightPackage();
        final Customer customer = new Customer();
        long customerID = customer.getCusUID();
        final Payment payee = new Payment();
        tranXDAO= new TranXDAO(context.getApplicationContext());
        holder.txtTransactionTitle.setText(MessageFormat.format("Type: {0}{1}", transaction.getTranXType(), transaction.getTransactionID()));
        holder.txtTransactionAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", transaction.getRecordAmount())));
        holder.destinationAccount.setText(MessageFormat.format("Destination Acct: {0}", transaction.getTranxDestAcct()));
        holder.txtTransactionTimestamp.setText(MessageFormat.format("Date: {0}", transaction.getTranxDate()));
        holder.sendingAccount.setText(MessageFormat.format("Sending Acct: {0}", transaction.getTranxSendingAcct()));
        holder.package_startDate.setText(MessageFormat.format("Ref ID: {0}", skyLightPackage.getPackageDateStarted()));
        //holder.pictureOfCelebrant.setImageURI(profile.getProfilePicture());
        holder.payee.setText(MessageFormat.format("Payee: {0}", transaction.getTranxPayee()));
        holder.packageID.setText(MessageFormat.format("Package ID: {0}-{1}-{2}", skyLightPackage.getPackID(), skyLightPackage.getPackageDuration(), skyLightPackage.getPackageDateStarted()));
        holder.savingsID.setText(MessageFormat.format("Savings ID: {0}", customerDailyReport.getUID()));
        holder.noOfDays.setText(MessageFormat.format("No. of Days: {0}", customerDailyReport.getRecordNumberOfDays()));
        holder.status.setText(MessageFormat.format("Status: {0}", customerDailyReport.getRecordStatus()));

        holder.txtTransactionTitle.setText(MessageFormat.format("{0} - {1}", transaction.getTranXType().toString(), transaction.getTransactionID()));
        if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            holder.imgTransactionIcon.setImageResource(R.drawable.ic_account_balance_background);
            holder.txtTransactionTitle.setText(MessageFormat.format("To Payee: {0}", transaction.getTranxPayee()));
            holder.txtTransactionAmount.setTextColor(Color.RED);
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
            holder.imgTransactionIcon.setImageResource(R.drawable.transfer3);

            holder.txtTransactionTitle.setText(MessageFormat.format("From: {0} - To: {1}", transaction.getTranxSendingAcct(), transaction.getTranxDestAcct()));
            //holder.txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_light));
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            holder.imgTransactionIcon.setImageResource(R.drawable.deposit);
            //holder.txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
        }
        holder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.status.setText("Approved");
                tranXDAO.updateTransactionStatus((customerDailyReport.getRecordStatus()),transaction);

            } else {
                holder.status.setText("Not approved");
            }
        });


    }
    public interface OnItemsClickListener{
        void onItemClick(Transaction transaction);
    }



}
