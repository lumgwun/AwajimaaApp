package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.skylightapp.Classes.Account;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;



public class AccountAdapter extends ArrayAdapter<Account> {

    private Context context;
    private int resource;

    public AccountAdapter(Context context, int resource, ArrayList<Account> accounts) {
        super(context, resource, accounts);

        this.context = context;
        this.resource = resource;
    }

    public AccountAdapter(FragmentActivity activity, int lst_accounts, ArrayList<Account> accounts) {
        super(activity, lst_accounts, accounts);
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        Account account = getItem(position);

        TextView txtAccountName = convertView.findViewById(R.id.txt_account_name);
        assert account != null;
        txtAccountName.setText(account.getAccountName());
        TextView txtAccountNo = convertView.findViewById(R.id.txt_acc_no);
        txtAccountNo.setText(String.format("%s %s", context.getString(R.string.account_no), account.getSkyLightAcctNo()));
        TextView txtAccountBalance = convertView.findViewById(R.id.txt_balance);
        txtAccountBalance.setText(MessageFormat.format("Account balance: NGN{0}", String.format("%.2f", account.getAccountBalance())));

        return convertView;
    }
}
