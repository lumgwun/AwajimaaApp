package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.skylightapp.Adapters.PaymentListAd;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Transactions.PayStackCard;

import java.util.ArrayList;

public class ViewPayAct extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewPayment;
    private PaymentListAd adapter;
    private ArrayList<PayStackCard> listPayStackCards;
    private int REQUEST_ADD_CARD = 1;
    private AppCompatImageView tvNoHistory, ivCredit, ivCash;
    private AppCompatTextView tvHeaderText;
    private View v;
    private AppCompatTextView btnAddNewPayment;
    private int paymentMode;
    private LinearLayout llPaymentList;
    ContentLoadingProgressBar progressBar;
    private ProgressDialog progressDialog;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_pay);
        setTitle("Your Payment Cards");
        prefManager= new PrefManager(this);
        listViewPayment = (ListView) findViewById(R.id.listViewPayment);
        llPaymentList = (LinearLayout) findViewById(R.id.llPaymentList);
        tvNoHistory =  findViewById(R.id.ivEmptyView);
        tvHeaderText = findViewById(R.id.tvHeaderText);
        btnAddNewPayment = findViewById(R.id.tvAddNewPayment);

        ivCash = findViewById(R.id.ivCash);
        ivCredit = findViewById(R.id.ivCredit);

        btnAddNewPayment.setOnClickListener(this);
        paymentMode = (int) new PrefManager(this).getPaymentMode();
        v = findViewById(R.id.line);
        listPayStackCards = new ArrayList<PayStackCard>();
        adapter = new PaymentListAd(this, listPayStackCards, new PrefManager(this).getDefaultCard());
        if (listPayStackCards.size() > 0) {
            llPaymentList.setVisibility(View.VISIBLE);
            tvNoHistory.setVisibility(View.GONE);
            paymentMode = 1;
            tvHeaderText.setVisibility(View.VISIBLE);
        } else {
            llPaymentList.setVisibility(View.GONE);
            tvNoHistory.setVisibility(View.VISIBLE);
            tvHeaderText.setVisibility(View.GONE);
            paymentMode = 0;
        }
        adapter.notifyDataSetChanged();
        listViewPayment.setAdapter(adapter);
        getCards();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddNewPayment:
                startActivityForResult(new Intent(this, AddPayMCardAct.class), REQUEST_ADD_CARD);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }


    private void getCards() {
        showProgressDialog();
        prefManager= new PrefManager(this);
        /*new PrefManager(this).getUserId() + "&"
                + TOKEN + "="
                + new PrefManager(this).getSessionToken();*/
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//you can cancel it by pressing back button
        progressDialog.setMessage("signing up wait ...");
        progressBar.show();//displays the progress bar
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                getCards();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }

    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }
}