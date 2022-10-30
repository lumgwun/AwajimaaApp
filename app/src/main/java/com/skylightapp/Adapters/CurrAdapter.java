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
import com.skylightapp.Classes.Account;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrAdapter extends ArrayAdapter<Currency> {

    private Context context;
    private int resource;
    private Currency currency;
    private String currencyCode,currencySymbol;
    private List<Currency> currencyList;

    public CurrAdapter(Context context, int resource, List<Currency> currencies) {
        super(context, resource, currencies);
        this.context = context;
        this.resource = resource;
        this.currencyList = currencies;
    }


    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
            //list_currency
        }

        Currency currency = getItem(position);
        if(currency !=null){
            currencyCode= currency.getCode();
        }
        AppCompatTextView txtAccountCurrency = convertView.findViewById(R.id.curr);
        assert currency != null;

        txtAccountCurrency.setText(MessageFormat.format("{0}", currencyCode));

        return convertView;
    }
}
