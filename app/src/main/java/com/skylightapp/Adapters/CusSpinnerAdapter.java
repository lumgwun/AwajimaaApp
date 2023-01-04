package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.skylightapp.Classes.Customer;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class CusSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    Context context;
    int flags[];
    //String[] countryNames;
    LayoutInflater inflter;
    private List<Customer> customerList;
    private ArrayList<MarketBizPackage> marketBizPackageAll;
    private ArrayList<Customer> customers;
    int layout;

    public CusSpinnerAdapter(Context applicationContext,  ArrayList<Customer> customers) {
        this.context = applicationContext;
        this.customerList = customers;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public CusSpinnerAdapter(Context applicationContext, int simple_spinner_item, ArrayList<Customer> customers) {
        this.context = applicationContext;
        this.customerList = customers;
        this.layout = simple_spinner_item;
    }

    @Override
    public int getCount() {
        return (null != customers ? customers.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.cus_spn_row, null);
        Customer customer = customers.get(position);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(R.drawable.user3);
        names.setText(MessageFormat.format("Customer Name:{0}{1}", customer.getCusSurname(), customer.getCusFirstName()));
        return view;
    }
}
