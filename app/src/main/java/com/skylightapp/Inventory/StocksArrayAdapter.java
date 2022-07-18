package com.skylightapp.Inventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class StocksArrayAdapter extends ArrayAdapter implements SpinnerAdapter {

    private ArrayList<Stocks> stocksArrayList;
    private Context mcontext;
    private int layout;

    Context context;
    int flags[];

    LayoutInflater inflter;

    public StocksArrayAdapter(Context applicationContext, int layout, ArrayList<Stocks> stocksArrayList1) {
        super(applicationContext,layout,stocksArrayList1);
        this.context = applicationContext;
        this.stocksArrayList = stocksArrayList1;
        this.layout = layout;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public int getCount() {
        return (null != stocksArrayList ? stocksArrayList.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.stock_row2, null);
        Stocks stocks = this.stocksArrayList.get(position);
        AppCompatImageView icon = (BezelImageView) view.findViewById(R.id.stock_icon);
        TextView names = (TextView) view.findViewById(R.id.stocks_Names_);
        TextView qty = (TextView) view.findViewById(R.id.stocksQty);

        names.setText(MessageFormat.format("Stock Name:{0}", stocks.getStockName()));
        qty.setText(MessageFormat.format("Qty:{0}", stocks.getStockItemQty()));

        /*for ( position = 0; position < stocksArrayList.size(); position++) {
            names.setText((CharSequence) stocksArrayList.get(position));

        }*/

        return view;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
