package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class TransAdapterCus extends ArrayAdapter<Transaction> {

    private Context context;
    private int resource;
    private ArrayList<Transaction> transactionArrayList;

    public TransAdapterCus(Context context, int resource, ArrayList<Transaction> transactions) {
        super(context, resource, transactions);

        this.context = context;
        this.resource = resource;
    }


    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        Transaction transaction = getItem(position);
        CustomerDailyReport customerDailyReport = getItem(position);
        int reportID = customerDailyReport.getRecordID();

        ImageView imgTransactionIcon = convertView.findViewById(R.id.thumbnail_tx);
        TextView txtCusName = convertView.findViewById(R.id.name_of_client);
        TextView txtCusID = convertView.findViewById(R.id.customer90);
        TextView txtTransactionAmount = convertView.findViewById(R.id.transaction_amount);
        TextView txtTransactionTimestamp = convertView.findViewById(R.id.transaction_date);
        TextView txtID = convertView.findViewById(R.id.tx_id);
        TextView txtSavingsID = convertView.findViewById(R.id.savingsId80);
        TextView txtType = convertView.findViewById(R.id.transaction_type);
        TextView txtPayee = convertView.findViewById(R.id.payee);
        TextView txtPayeeAcct = convertView.findViewById(R.id.destination_account);
        TextView txtTx_Ewallet = convertView.findViewById(R.id.payingAccount);

        ImageView addDoc = convertView.findViewById(R.id.tx_img);

        txtID.setText(MessageFormat.format("{0} - {1}", "Package ID:", customerDailyReport.getRecordPackageId()));
        txtType.setText(MessageFormat.format("{0} - {1}", "Package Type:", customerDailyReport.getRecordPackage().getPackageType()));
        txtSavingsID.setText(MessageFormat.format("{0} - {1}", "Report ID:", customerDailyReport.getRecordID()));
        txtTx_Ewallet.setText(MessageFormat.format("{0} - {1}", "Account ID:", customerDailyReport.getRecordCustomer().getCusAccount().getSkyLightAcctNo()));
        txtTransactionTimestamp.setText(MessageFormat.format("Date{0}", transaction.getTimestamp()));
        txtTransactionAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", transaction.getRecordAmount())));

        if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            imgTransactionIcon.setImageResource(R.drawable.transaction);
            txtCusName.setText(MessageFormat.format("From : {0}", " Skylight"));
            txtTransactionAmount.setTextColor(Color.RED);
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
            imgTransactionIcon.setImageResource(R.drawable.transfer3);
            txtCusName.setText(MessageFormat.format("From: {0} - To: {1}", "Destination Acct:", transaction.getDestinationAccount()));
            txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            imgTransactionIcon.setImageResource(R.drawable.deposit1);
            txtCusName.setText(MessageFormat.format("To: {0}", "Skylight"));
            txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        }
        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getContext());
                Uri paymentDoc =dbHelper.getDocPicturePath(reportID);
                //Bitmap doc = BitmapFactory.decodeFile(String.valueOf(paymentDoc));
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContext().openFileInput((String.valueOf(paymentDoc))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                assert false;
                addDoc.setImageBitmap(bitmap);

            }
        });

        return convertView;
    }
}
