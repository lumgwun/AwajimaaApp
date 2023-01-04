package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.Currency;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Account;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class AccountRecylerAdap extends RecyclerView.Adapter<AccountRecylerAdap.RecyclerViewHolder> {

    private ArrayList<Account> accountArrayList;
    private Context mcontext;
    int resources;
    FragmentActivity activity;
    private OnAcctClickListener listener;
    private int mNumberItems = 0;
    private List<Account> mAccountList;
    private Account recyclerData;
    private Currency currency;
    private String currencyCode;

    public AccountRecylerAdap(ArrayList<Account> recyclerDataArrayList, Context mcontext) {
        this.accountArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public AccountRecylerAdap(Context context, int resources, ArrayList<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
        this.mcontext = context;
        this.resources = resources;

    }
    public void setWhenClickListener(OnAcctClickListener listener){
        this.listener = listener;
    }
    public AccountRecylerAdap(Context context, ArrayList<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
        this.mcontext = context;

    }
    public AccountRecylerAdap(OnAcctClickListener itemClickListener) {
        listener = itemClickListener;
    }
    public void setAccountList(List<Account> accountList) {
        mAccountList = accountList;
        mNumberItems = mAccountList.size();
        notifyDataSetChanged();
    }

    public Account getAccount(int i) {
        return mAccountList.get(i);
    }

    public AccountRecylerAdap(FragmentActivity activity, ArrayList<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        recyclerData = accountArrayList.get(position);
        if(recyclerData !=null){
            currency= recyclerData.getCurrency();
            if(currency !=null){
                currencyCode=currency.getCode();
            }
            holder.txtAccountName.setText(MessageFormat.format("Acct. Name{0}", recyclerData.getAccountName()));
            holder.txtAccountID.setText(MessageFormat.format("Acct No:{0}", recyclerData.getAcctNo()));
            holder.txtAccountBank.setText(MessageFormat.format("Bank:{0}", recyclerData.getBankName()));
            holder.txtAccountBalance.setText(MessageFormat.format("Account balance: {0}{1}", recyclerData.getAccountCurrSymbol(), String.format("%.2f", recyclerData.getAccountBalance())));
            holder.txtAccountType.setText(MessageFormat.format("Acct Type:{0}", String.valueOf(recyclerData.getAcctType())));


        }



    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != accountArrayList ? accountArrayList.size() : 0);
    }

    public  class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            itemView.setOnClickListener(this);


        }
        void bind(Account account) {
            txtAccountName.setText(account.getAccountName());
            txtAccountBank.setText(account.getBankName());
            txtAccountNo.setText(account.getBankAcct_No());
        }
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            listener.onListItemClick(clickedPosition);
        }
    }
    public interface OnAcctClickListener {
        void onAcctClicked(Account account);
        void onListItemClick(int index);
    }
}
