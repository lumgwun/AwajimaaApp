package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.GrpProfileTraxs;
import com.skylightapp.R;

import java.util.ArrayList;

public class GrpTranxAdapter extends RecyclerView.Adapter<GrpTranxAdapter.RecyclerViewHolder> {

    private ArrayList<Transaction> transactions;
    private Context mcontext;
    private OnItemsClickListener listener;

    public GrpTranxAdapter(ArrayList<Transaction> recyclerDataArrayList, Context mcontext) {
        this.transactions = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public GrpTranxAdapter(GrpProfileTraxs grpProfileTraxs, ArrayList<Transaction> transactionArrayList) {
        this.transactions = transactionArrayList;
        this.mcontext = grpProfileTraxs;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grp_trans_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Transaction transaction = (Transaction) transactions.get(position);
        holder.type.setText("Tranx Type"+transaction.getTransactionType());
        holder.date.setText("Date:"+transaction.getDate());
        holder.destinationAcct.setText("Dest. Acct:"+transaction.getDestinationAccount());
        holder.sendingAcct.setText("Sending Acct:"+transaction.getSendingAccount());
        holder.transID.setText("ID:"+transaction.getTransactionID());
        holder.tranMethod.setText("Method:"+transaction.getMethodOfPay());
        holder.status.setText("Status:"+transaction.getDocStatus());
        holder.amount.setText("Amount:"+transaction.getRecordAmount());
    }

    @Override
    public int getItemCount() {
        return (null != transactions ? transactions.size() : 0);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView type;
        private final BezelImageView userPicture;
        private TextView destinationAcct;
        private TextView tranMethod;
        private TextView sendingAcct, date,transID,status,amount;
        public Profile mItem;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tranxType);
            destinationAcct = itemView.findViewById(R.id.tranxDestAcct);
            sendingAcct = itemView.findViewById(R.id.tranxSendingAcct);
            transID = itemView.findViewById(R.id.TranxID);
            date = itemView.findViewById(R.id.DateOfPayment);
            tranMethod = itemView.findViewById(R.id.tranxMethodOfPayment);
            status = itemView.findViewById(R.id.TranxStatus);
            amount = itemView.findViewById(R.id.TranxAmt);
            userPicture = itemView.findViewById(R.id.user_pix1155);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setPaymentList(ArrayList<? extends Transaction> transaction) {
        transactions.clear();
        transactions.addAll(transaction);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLast(Transaction transaction) {
        transactions.add(transaction);
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(Transaction transaction);
    }
}
