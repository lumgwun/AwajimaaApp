package com.skylightapp.MarketClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.blongho.country_data.Currency;
import com.skylightapp.Classes.Account;
import com.skylightapp.R;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;

public class BizDealAcctAdapter extends ArrayAdapter<BizDealAccount> {

    private Context context;
    private int resource;
    private Currency currency;
    private String currencyCode,currencySymbol;

    public BizDealAcctAdapter(Context context, int resource, ArrayList<BizDealAccount> accounts) {
        super(context, resource, accounts);

        this.context = context;
        this.resource = resource;
    }

    public BizDealAcctAdapter(FragmentActivity activity, int item_biz_acct, ArrayList<BizDealAccount> accounts) {
        super(activity, item_biz_acct, accounts);
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        BizDealAccount account = getItem(position);
        if(account !=null){
            currency=account.getBdaCurrency();

        }
        currencyCode=currency.getCode();
        currencySymbol=currency.getSymbol();

        TextView txtAccountName = convertView.findViewById(R.id.account_biz_name);
        TextView txtAccountCurrency = convertView.findViewById(R.id.acct_biz_currency);
        ImageView img_currency = convertView.findViewById(R.id.img_acct_biz);
        assert account != null;
        txtAccountName.setText(account.getBdaAcctName());
        TextView txtAccountNo = convertView.findViewById(R.id.account_biz_No);
        txtAccountCurrency.setText(String.format("%s %s", "", currencyCode));
        txtAccountNo.setText(String.format("%s %s", context.getString(R.string.account_no), account.getBdAcctID()));
        Picasso.get().load(currencySymbol).into(img_currency);
        //TextView txtAccountBank = convertView.findViewById(R.id.account_biz_bank);
        //txtAccountBalance.setText(MessageFormat.format("Account balance:", currencySymbol+String.format("%.2f", account.getBdaAcctBalance())));

        return convertView;
    }
}
