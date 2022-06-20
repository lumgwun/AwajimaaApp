package com.skylightapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.R;

import java.util.ArrayList;


public class BorrowingAdapter extends RecyclerView.Adapter<BorrowingAdapter.RecyclerViewHolder> {

    private ArrayList<Loan> loanArrayList;
    private Context mcontext;
    int resources;

    public BorrowingAdapter(ArrayList<Loan> recyclerDataArrayList, Context mcontext) {
        this.loanArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public BorrowingAdapter(Context context, int resources, ArrayList<Loan> loanArrayList) {
        this.loanArrayList = loanArrayList;
        this.mcontext = context;
        this.resources = resources;

    }

    public BorrowingAdapter(Context context, ArrayList<Loan> loanList) {
        this.loanArrayList = loanList;
        this.mcontext = context;

    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.borrow_list_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Loan recyclerData = loanArrayList.get(position);
        holder.amount.setText(String.valueOf(recyclerData.getAmount1()));
        holder.date.setText(recyclerData.getDate());
        holder.bank.setText(String.valueOf(recyclerData.getBank()));
        holder.name.setText(String.valueOf(recyclerData.getAccountName()));
        holder.status.setText(String.valueOf(recyclerData.getStatus()));
        holder.acctNo.setText(String.valueOf(recyclerData.getAcctID()));
        holder.acctBalance.setText(String.valueOf(recyclerData.getAccountBalance()));


    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return loanArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView amount;
        private final BezelImageView userPicture;
        private TextView date;
        private TextView bank;
        private TextView name;
        private TextView status;
        private TextView acctNo;
        private TextView acctBalance;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.borrow_request_amount2);
            date = itemView.findViewById(R.id.loanDate_text1);
            bank = itemView.findViewById(R.id.borrow_bank_name2);
            name = itemView.findViewById(R.id.bName_of_customer);
            status = itemView.findViewById(R.id.borrow_status2);
            acctNo = itemView.findViewById(R.id.borrow_acct3);
            acctBalance = itemView.findViewById(R.id.balance_afterb);
            userPicture = itemView.findViewById(R.id.b_user);
        }
    }
}
