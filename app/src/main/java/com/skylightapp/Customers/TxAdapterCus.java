package com.skylightapp.Customers;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class TxAdapterCus extends ArrayAdapter<Transaction> {

    private Context context;
    private int resource;
    ActivityResultLauncher activityResultLauncher;
    /*ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    Uri imageurl = data.getData();
                }
            });
    activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitmap);
            }
        }
    });*/

    public TxAdapterCus(Context context, int resource, ArrayList<Transaction> transactions) {
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
        TextView txtPackageID = convertView.findViewById(R.id.package_Id2);
        TextView txtType = convertView.findViewById(R.id.t_ype);
        AppCompatButton addDoc = convertView.findViewById(R.id.add_doc_btn);
        TextView txtSavingsID = convertView.findViewById(R.id.savings_id);
        TextView txtSkylightAcct = convertView.findViewById(R.id.txt_transaction_type_id);
        //TextView txtBankAccount = convertView.findViewById(R.id.tx_id3);
        TextView txtTransactionTimestamp = convertView.findViewById(R.id.transaction_date4);
        TextView txtTx_Ewallet = convertView.findViewById(R.id.e_acct);
        TextView txtTransactionInfo = convertView.findViewById(R.id.info_tx);
        TextView txtTransactionAmount = convertView.findViewById(R.id.total_amount3);

        AppCompatImageView docImage = convertView.findViewById(R.id.doc_pic);

        txtPackageID.setText(MessageFormat.format("{0} - {1}", "Package ID:", customerDailyReport.getRecordPackageId()));
        txtType.setText(MessageFormat.format("{0} - {1}", "Tx Type:", transaction.getTransactionType()));
        txtSavingsID.setText(MessageFormat.format("{0} - {1}", "Report ID:", customerDailyReport.getRecordID()));
        txtTx_Ewallet.setText(MessageFormat.format("{0} - {1}", "Acct No:", transaction.getAccountId()));
        txtTransactionInfo.setText(MessageFormat.format("{0} - {1}", "More Info:", transaction.getPayee()));
        txtTransactionTimestamp.setText(MessageFormat.format("Date{0}", transaction.getTimestamp()));
        txtTransactionAmount.setText(MessageFormat.format("Amount: NGN{0}", String.format("%.2f", transaction.getRecordAmount())));

        if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            imgTransactionIcon.setImageResource(R.drawable.transaction);
            txtTransactionInfo.setText(MessageFormat.format("From : {0}", " Skylight"));
            txtTransactionAmount.setTextColor(Color.RED);
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
            imgTransactionIcon.setImageResource(R.drawable.transfer3);
            txtTransactionInfo.setText(MessageFormat.format("From: {0} - To: {1}", "Destination Acct:", transaction.getDestinationAccount()));
            txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            imgTransactionIcon.setImageResource(R.drawable.deposit1);
            txtTransactionInfo.setText(MessageFormat.format("Deposit To: {0}", "Skylight"));
            txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        }
        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (docImage == null) {
                    //mGetContent.launch("image/*");
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
                    docImage.setImageBitmap(bitmap);


                }


            }
        });

        return convertView;
    }
}
