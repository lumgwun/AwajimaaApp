package com.skylightapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
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
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class NewCusTranxAdapter extends RecyclerView.Adapter<NewCusTranxAdapter.ViewHolder> implements ListAdapter {

    private Context context;
    private int resource;
    String statusSwitch1;
    private final ArrayList<Transaction> transactions;
    DBHelper applicationDb;
    String status;
    Profile userProfile;
    Customer customer;
    User user;
    SharedPreferences userPreferences;
    private Gson gson;
    private OnItemsClickListener listener;


    public NewCusTranxAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super();
        this.context = context;
        this.resource = resource;
        this.transactions = transactions;
    }

    public NewCusTranxAdapter(Context context, ArrayList<Transaction> transactions2) {
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
                .inflate(R.layout.new_tranx_row, parent, false);
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
        public final AppCompatImageView imgTransactionIcon;
        public final TextView refNo;
        public final CardView cardView;
        public final TextView tx_ApprovalDate;
        public final TextView payee;
        public final TextView destinationAccount;
        public final TextView savingsID;
        public final TextView noOfDays;
        public final TextView status;

        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cardView = view.findViewById(R.id.card_view_Cus);
            imgTransactionIcon = view.findViewById(R.id.imgCusTx);
            txtTransactionTitle = view.findViewById(R.id.cus_transaction_type);
            refNo = view.findViewById(R.id.cus_tx_id1);
            txtTransactionTimestamp = view.findViewById(R.id.cus_transact_date);
            txtTransactionAmount = view.findViewById(R.id.tranxCus_amount2);
            tx_ApprovalDate = view.findViewById(R.id.tx_approvalDate);
            status = view.findViewById(R.id.tx_status);
            noOfDays = view.findViewById(R.id.TxnoOfDays);
            savingsID = view.findViewById(R.id.tx_savingsID);
            destinationAccount = view.findViewById(R.id.tx_destAccount);
            payee = view.findViewById(R.id.TXpayee);


        }


    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = transactions.get(position);
        final Transaction transaction = transactions.get(position);
        CustomerDailyReport customerDailyReport = (CustomerDailyReport) getItem(position);
        int reportID = customerDailyReport.getRecordID();
        //final CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        final SkyLightPackage skyLightPackage = new SkyLightPackage();
        final Customer customer = new Customer();
        final Payment payee = new Payment();
        holder.txtTransactionTitle.setText(MessageFormat.format("Type: {0}{1}", transaction.getTransactionType(), transaction.getTransactionID()));
        holder.txtTransactionAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", transaction.getRecordAmount())));
        holder.destinationAccount.setText(MessageFormat.format("Destination Acct: {0}", transaction.getDestinationAccount()));
        holder.txtTransactionTimestamp.setText(MessageFormat.format("Date: {0}", transaction.getDate()));
        holder.tx_ApprovalDate.setText(MessageFormat.format("Ref ID: {0}", transaction.getTxApprovalDate()));
        //holder.pictureOfCelebrant.setImageURI(profile.getProfilePicture());
        holder.payee.setText(MessageFormat.format("Payee: {0}", transaction.getPayee()));
        holder.savingsID.setText(MessageFormat.format("Savings ID: {0}", customerDailyReport.getUID()));
        holder.noOfDays.setText(MessageFormat.format("No. of Days: {0}", customerDailyReport.getRecordNumberOfDays()));
        holder.status.setText(MessageFormat.format("Status: {0}", transaction.getTransactionStatus()));

        holder.txtTransactionTitle.setText(MessageFormat.format("{0} - {1}", transaction.getTransactionType().toString(), transaction.getTransactionID()));
        if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            holder.imgTransactionIcon.setImageResource(R.drawable.ic_account_balance_background);
            holder.txtTransactionTitle.setText(MessageFormat.format("To Payee: {0}", transaction.getPayee()));
            holder.txtTransactionAmount.setTextColor(Color.RED);
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
            holder.imgTransactionIcon.setImageResource(R.drawable.transfer3);

            holder.txtTransactionTitle.setText(MessageFormat.format("From: {0} - To: {1}", transaction.getSendingAccount(), transaction.getDestinationAccount()));
            //holder.txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_light));
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            holder.imgTransactionIcon.setImageResource(R.drawable.deposit);
            //holder.txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
        }
        holder.imgTransactionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(v.getContext());
                Uri paymentDoc =dbHelper.getDocPicturePath(reportID);
                //Bitmap doc = BitmapFactory.decodeFile(String.valueOf(paymentDoc));
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(v.getContext().openFileInput((String.valueOf(paymentDoc))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                assert false;
                holder.imgTransactionIcon.setImageBitmap(bitmap);

            }
        });




    }
    public interface OnItemsClickListener{
        void onItemClick(Transaction transaction);
    }

}
