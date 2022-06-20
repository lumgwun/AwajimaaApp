package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Account;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;



public class AccountAdapter2 extends RecyclerView.Adapter<AccountAdapter2.RecyclerViewHolder> {

    private ArrayList<Account> accountArrayList;
    private Context mcontext;
    int resources;
    FragmentActivity activity;
    private OnItemsClickListener listener;

    public AccountAdapter2(ArrayList<Account> recyclerDataArrayList, Context mcontext) {
        this.accountArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public AccountAdapter2(Context context, int resources, ArrayList<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
        this.mcontext = context;
        this.resources = resources;

    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }
    public AccountAdapter2(Context context, ArrayList<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
        this.mcontext = context;

    }

    public AccountAdapter2(FragmentActivity activity, ArrayList<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Account recyclerData = accountArrayList.get(position);
        holder.txtAccountName.setText(MessageFormat.format("Acct Name:{0}", recyclerData.getAccountName()));
        holder.txtAccountID.setText(MessageFormat.format("Acct No:{0}", recyclerData.getAcctID()));
        holder.txtAccountBank.setText(MessageFormat.format("Bank:{0}", recyclerData.getAcctID()));
        holder.txtAccountBalance.setText(MessageFormat.format("Account balance: NGN{0}", String.format("%.2f", recyclerData.getAccountBalance())));
        holder.txtAccountType.setText(MessageFormat.format("Acct Type:{0}", String.valueOf(recyclerData.getType())));
        holder.txtAccountID.setText(MessageFormat.format("Acct ID:{0}", String.valueOf(recyclerData.getAcctID())));


    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != accountArrayList ? accountArrayList.size() : 0);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtAccountName;
        private TextView txtAccountID;
        private final BezelImageView userPicture;
        private TextView txtAccountNo;
        private TextView txtAccountBank;
        private TextView txtAccountBalance;
        private TextView txtAccountType;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAccountName = itemView.findViewById(R.id.account_name4);
            txtAccountBank = itemView.findViewById(R.id.account_bank4);
            txtAccountType = itemView.findViewById(R.id.txt_account_type4);
            txtAccountNo = itemView.findViewById(R.id.account_no4);
            txtAccountID = itemView.findViewById(R.id.account_id4);
            txtAccountBalance = itemView.findViewById(R.id.account_balance4);
            userPicture = itemView.findViewById(R.id.img_account4);

        }
    }
    public interface OnItemsClickListener{
        void onItemClick(Account account);
    }
}
