package com.skylightapp.MarketClasses;

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

import com.blongho.country_data.Currency;
import com.skylightapp.Classes.Account;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class BizDealAdapter extends ArrayAdapter<BusinessDeal> {

    private Context context;
    private int resource,dealAcctNo,dealID;
    private Currency currency;
    private String currencyCode,currencySymbol,bizDealStatus,dealStartDate,dealTitle;
    private ArrayList<BusinessDeal> businessDeals;
    private double dealAmount,bizDealBalance;
    private Currency bizDealCurrency;

    public BizDealAdapter(Context context, int resource, ArrayList<BusinessDeal> dealArrayList) {
        super(context, resource, dealArrayList);
        this.context = context;
        this.resource = resource;
        this.businessDeals = dealArrayList;
    }

    public BizDealAdapter(FragmentActivity activity, int deal_item, ArrayList<BusinessDeal> accounts) {
        super(activity, deal_item, accounts);
    }

    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        BusinessDeal businessDeal = getItem(position);
        if(businessDeal !=null){
            dealAcctNo=businessDeal.getDealAcctNumber();
            dealID=businessDeal.getDealID();
            dealStartDate=businessDeal.getDealStartDate();
            dealTitle=businessDeal.getDealTittle();
            dealAmount=businessDeal.getDealCostOfProduct();
            bizDealCurrency=businessDeal.getBizDealCurrency();
            bizDealBalance=businessDeal.getDealBalance();
            bizDealStatus=businessDeal.getDealStatus();
        }
        if(bizDealCurrency !=null){
            currencyCode=bizDealCurrency.getCode();
            currencySymbol=bizDealCurrency.getSymbol();

        }
        TextView txtAccountCurrency = convertView.findViewById(R.id.deal_currency);
        TextView txtStatus = convertView.findViewById(R.id.deal_status);
        TextView txtDealName = convertView.findViewById(R.id.deal_tittle);
        TextView txtAccountBalance = convertView.findViewById(R.id.aDeal_balance2);

        assert businessDeal != null;
        txtDealName.setText(dealTitle);
        TextView txtAccountNo = convertView.findViewById(R.id.account_deal_No);
        TextView txtStartDate = convertView.findViewById(R.id.date_deal);

        txtAccountCurrency.setText(String.format("%s %s", "", currencySymbol));
        txtStatus.setText(String.format("%s %s", "", bizDealStatus));
        txtStartDate.setText(String.format("%s %s", "", dealStartDate));
        txtDealName.setText(String.format("%s %s", "", dealTitle));

        txtAccountNo.setText(String.format("%s %s", context.getString(R.string.account_no), dealAcctNo));

        txtAccountBalance.setText(MessageFormat.format("Deal Balance:", currencySymbol+String.format("%.2f", bizDealBalance)));

        return convertView;
    }
}
