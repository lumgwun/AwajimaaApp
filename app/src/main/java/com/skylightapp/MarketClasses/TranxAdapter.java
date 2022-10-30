package com.skylightapp.MarketClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Classes.User;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Currency;

public class TranxAdapter extends RecyclerView.Adapter<TranxAdapter.ViewHolder> implements ListAdapter {

    private Context context;
    private int resource;
    String statusSwitch1;
    private  ArrayList<Transaction> transactions;
    DBHelper applicationDb;
    String status;
    Profile userProfile;
    Customer customer;
    User user;
    private TranXDAO tranXDAO;
    SharedPreferences userPreferences;
    private Gson gson;
    private OnTranxClickListener listener;
    private Currency currency;
    private String curSymbol;


    public TranxAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super();
        this.context = context;
        this.resource = resource;
        this.transactions = transactions;
    }

    public TranxAdapter(Context context, ArrayList<Transaction> transactions2) {
        this.context = context;
        this.transactions = transactions2;
    }
    public void setWhenClickListener(OnTranxClickListener listener){
        this.listener = listener;
    }

    @NotNull
    @Override
    public TranxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trans_row, parent, false);
        return new TranxAdapter.ViewHolder(view);
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
        public final AppCompatTextView txtTranxType;
        public final AppCompatTextView txtTransactionTimestamp;
        public final AppCompatTextView txtTransactionAmount;
        public final AppCompatImageView imgTransactionIcon;
        public final AppCompatTextView tranxID;
        public final CardView cardView;
        public final AppCompatTextView payer;
        public final AppCompatTextView destinationAccount;
        public final AppCompatTextView sendingAccount;
        public final AppCompatTextView status,txtOtherInfo;
        public final SwitchCompat switchCompat;

        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imgTransactionIcon = view.findViewById(R.id.Id_txImg);
            txtTranxType = view.findViewById(R.id.transaction_type54);
            tranxID = view.findViewById(R.id.tranx_ref_id);
            txtTransactionTimestamp = view.findViewById(R.id.transact_date);
            txtTransactionAmount = view.findViewById(R.id.transac_amt);
            status = view.findViewById(R.id.txt_status);
            payer = view.findViewById(R.id.Tranx_payer);
            sendingAccount = view.findViewById(R.id.Tranx_sendingAccount);
            destinationAccount = view.findViewById(R.id.tranx_des_Account);

            cardView = view.findViewById(R.id.card_view_tion);
            txtOtherInfo = view.findViewById(R.id.txt_other_tx);
            switchCompat = (SwitchCompat) view.findViewById(R.id.switch_tx_);


            //txtTransactionInfo.setVisibility(View.VISIBLE);


        }


    }
    @Override
    public void onBindViewHolder(final TranxAdapter.ViewHolder holder, final int position) {
        holder.mItem = transactions.get(position);
        Transaction transaction = transactions.get(position);

        tranXDAO= new TranXDAO(context.getApplicationContext());
        if(transaction !=null){
            currency=transaction.getTranxCurrency();
            if(currency !=null){
                curSymbol=currency.getSymbol();

            }
            holder.txtTranxType.setText(MessageFormat.format("Type:{0}", transaction.getTranXType()));
            holder.txtTransactionAmount.setText(MessageFormat.format("{0}{1}", curSymbol, transaction.getTranxAmount()));
            holder.destinationAccount.setText(MessageFormat.format("Dest Acct: {0}", transaction.getTranxDestAcct()));
            holder.txtTransactionTimestamp.setText(MessageFormat.format("", transaction.getTranxDate()));
            holder.sendingAccount.setText(MessageFormat.format("Sending Acct: {0}", transaction.getTranxSendingAcct()));
            holder.payer.setText(MessageFormat.format("Payer: {0}", transaction.getTranxPayer()));
            holder.txtOtherInfo.setText(MessageFormat.format("Other Info: {0}", transaction.getTranxExtraInfo()));
            holder.tranxID.setText(MessageFormat.format("", transaction.getTransactionID()));


            holder.txtTranxType.setText(MessageFormat.format("{0} - {1}", transaction.getTranXType().toString(), transaction.getTransactionID()));
            if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
                holder.imgTransactionIcon.setImageResource(R.drawable.ic_account_balance_background);
                holder.txtTransactionAmount.setTextColor(Color.RED);
            } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                holder.imgTransactionIcon.setImageResource(R.drawable.transfer3);

                holder.txtTranxType.setText(MessageFormat.format("From: {0} - To: {1}", transaction.getTranxSendingAcct(), transaction.getTranxDestAcct()));
                //holder.txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_light));
            } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
                holder.imgTransactionIcon.setImageResource(R.drawable.deposit);
                //holder.txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
            }
            holder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    holder.status.setText("Approved");
                    //tranXDAO.updateTransactionStatus((customerDailyReport.getRecordStatus()),transaction);

                } else {
                    holder.status.setText("Not approved");
                }
            });
        }



    }
    public interface OnTranxClickListener {
        void onTranxClick(Transaction transaction);
    }

}
