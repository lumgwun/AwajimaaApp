package com.skylightapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

import com.skylightapp.R;
import com.skylightapp.Transactions.BillModel;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;



public class BillsAdapter extends ArrayAdapter<BillModel> {

    private Context context;
    List<BillModel> billModelList;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.ENGLISH);

    public BillsAdapter(Context context, List<BillModel> models) {

        super(context, R.layout.bill_item, models);
        this.context = context;
        this.billModelList = models;
    }

    private class ViewHolder {
        AppCompatTextView idTxt;
        AppCompatTextView nameTxt;
        AppCompatTextView billerName;
        AppCompatTextView itemCode;
        AppCompatTextView customerId;
        AppCompatTextView country;
        AppCompatTextView amount;
        AppCompatTextView date;
        AppCompatTextView reference;
        AppCompatTextView recurringType;
        AppCompatTextView billStatus;
        SwitchCompat switchBillAction;

    }

    @Override
    public int getCount() {
        return (null != billModelList ? billModelList.size() : 0);
    }

    @Override
    public BillModel getItem(int position) {
        return billModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bill_layout, null);
            holder = new ViewHolder();

            holder.idTxt =  convertView.findViewById(R.id.txt_bill_id);
            holder.nameTxt = convertView
                    .findViewById(R.id.txt_bill_name);
            holder.billerName = convertView
                    .findViewById(R.id.txt_emp_dob);
            holder.itemCode = convertView
                    .findViewById(R.id.txt_itemCode);
            holder.customerId =  convertView
                    .findViewById(R.id.txt_bill_cus_id);

            holder.country =  convertView.findViewById(R.id.txt_bill_country);

            holder.amount =  convertView.findViewById(R.id.txt_bill_amount);

            holder.billStatus =  convertView.findViewById(R.id.txt_bill_status);

            holder.date =  convertView.findViewById(R.id.txt_bill_date);
            holder.reference =  convertView.findViewById(R.id.txt_ref);
            holder.recurringType =  convertView.findViewById(R.id.txt_recurringCode);
            holder.switchBillAction =  convertView.findViewById(R.id.txt_action);
            holder.recurringType =  convertView.findViewById(R.id.txt_recurringCode);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillModel billModel = (BillModel) getItem(position);
        holder.idTxt.setText(MessageFormat.format("ID:{0}", billModel.getId()));
        holder.nameTxt.setText(MessageFormat.format("Name:{0}", billModel.getName()));
        holder.billerName.setText(MessageFormat.format("Biller Name:{0}", billModel.getBiller_name()));
        holder.itemCode.setText(MessageFormat.format("Item Code:{0}", billModel.getItem_code()));
        holder.customerId.setText(MessageFormat.format("Customer ID:{0}", billModel.getCustomerId()));
        holder.country.setText(MessageFormat.format("Country:{0}", billModel.getCountry()));
        holder.amount.setText(MessageFormat.format("Amount:{0}", billModel.getAmount()));
        holder.reference.setText(MessageFormat.format("Ref:{0}", billModel.getReference()));
        holder.date.setText(MessageFormat.format("Ref:{0}", billModel.getBillDate()));
        //holder.date.setText(formatter.format(()));
        holder.recurringType.setText(billModel.getRecurringType());

        return convertView;
    }

    @Override
    public void add(BillModel billModel) {
        billModelList.add(billModel);
        notifyDataSetChanged();
        super.add(billModel);
    }

    @Override
    public void remove(BillModel billModel) {
        billModelList.remove(billModel);
        notifyDataSetChanged();
        super.remove(billModel);
    }


}
