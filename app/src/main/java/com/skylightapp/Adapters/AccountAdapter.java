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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;

import com.blongho.country_data.Currency;
import com.google.android.material.card.MaterialCardView;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class AccountAdapter extends ArrayAdapter<Account> {

    private Context context;
    private int resource;
    private Currency currency;
    private String currencyCode,currencySymbol;
    private List<Account> accountList;

    public AccountAdapter(Context context, int resource, ArrayList<Account> accountList) {
        super(context, resource, accountList);
        this.context = context;
        this.resource = resource;
        this.accountList = accountList;
    }

    public AccountAdapter(FragmentActivity activity, int lst_accounts, ArrayList<Account> accounts) {
        super(activity, lst_accounts, accounts);
        this.context = activity;
        this.resource = lst_accounts;
        this.accountList = accounts;
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
        if(account !=null){
            currency=account.getCurrency();
        }
        currencyCode=currency.getCode();
        currencySymbol=currency.getSymbol();
        MaterialCardView cardView = convertView.findViewById(R.id.card_l1);
        AppCompatTextView txtAccountID = convertView.findViewById(R.id.account_id3);
        AppCompatTextView txtAccountBank = convertView.findViewById(R.id.account_bank1);
        AppCompatTextView txtAccountBalance = convertView.findViewById(R.id.account_balance2);

        AppCompatTextView txtAccountName = convertView.findViewById(R.id.account_name2);
        AppCompatTextView txtAccountCurrency = convertView.findViewById(R.id.account_currency);
        AppCompatTextView txtAccountNo = convertView.findViewById(R.id.account_no2);
        if (account != null) {
            txtAccountName.setText(account.getAccountName());
            txtAccountCurrency.setText(String.format("%s %s", "", currencySymbol));
            txtAccountID.setText(MessageFormat.format("", account.getAwajimaAcctNo()));
            txtAccountBank.setText(MessageFormat.format("Bank:{0}", account.getBankName()));
            txtAccountBalance.setText(MessageFormat.format("Account balance:", currencySymbol+String.format("%.2f", account.getAccountBalance())));
            txtAccountNo.setText(String.format("%s %s", context.getString(R.string.account_no), account.getAwajimaAcctNo()));

        }

        return convertView;
    }
}
