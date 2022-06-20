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

import androidx.annotation.NonNull;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Inventory.StocksTransferAct;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.OfficeCreatorAct;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class OfficeAdapter extends BaseAdapter implements SpinnerAdapter {
    Context context;
    int flags[];
    //String[] countryNames;
    LayoutInflater inflter;
    private List<OfficeBranch> officeBranchList;
    int layout;
    public OfficeAdapter() {
        super();

    }

    public OfficeAdapter(Context applicationContext, ArrayList<OfficeBranch> officeBranchList) {
        this.context = applicationContext;
        this.officeBranchList = officeBranchList;
        inflter = (LayoutInflater.from(applicationContext));
    }


    public OfficeAdapter(@NonNull Context context, int resource) {
        super();
    }

    public OfficeAdapter(OfficeCreatorAct officeCreatorAct, int simple_spinner_item, ArrayList<OfficeBranch> officeBranchArrayList) {
        this.context = officeCreatorAct;
        this.officeBranchList = officeBranchArrayList;
        this.layout = simple_spinner_item;
    }

    public OfficeAdapter(StocksTransferAct stocksTransferAct, int simple_spinner_item, ArrayList<OfficeBranch> officeBranchArrayList) {
        this.context = stocksTransferAct;
        this.officeBranchList = officeBranchArrayList;
        this.layout = simple_spinner_item;
    }

    @Override
    public int getCount() {
        return (null != officeBranchList ? officeBranchList.size() : 0);
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
        view = inflter.inflate(R.layout.office_row, null);
        OfficeBranch officeBranch = officeBranchList.get(position);
        ImageView icon = (ImageView) view.findViewById(R.id.img);
        TextView names = (TextView) view.findViewById(R.id.officeTxtView);
        icon.setImageResource(R.drawable.user3);
        names.setText(MessageFormat.format("Office Name:{0}{1}", officeBranch.getOfficeBranchName()));
        return view;
    }
}
