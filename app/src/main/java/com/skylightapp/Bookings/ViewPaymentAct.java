package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.TAG;
import static com.skylightapp.Bookings.BookingConstant.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.skylightapp.AddPayMCardAct;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.Card;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPaymentAct extends ActionBarBaseAct {
    private ListView listViewPayment;
    private PaymentListAd adapter;
    private ArrayList<Card> listCards;
    private int REQUEST_ADD_CARD = 1;
    private AppCompatImageView  ivCredit, ivCash;
    private AppCompatTextView tvHeaderText,tvNoHistory,btnAddNewPayment;
    private View v;
    private int paymentMode;
    private LinearLayout llPaymentList;
    private RequestQueue requestQueue;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson,gson1;
    String json,json1;
    Profile userProfile;
    long profileUID11;
    Customer customer;

    private static final String PREF_NAME = "awajima";
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserName,SharedPrefUserMachine;
    private int SharedPrefProfileID;
    private PrefManager prefManager;
    private int paymentCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_payment);
        setTitle("Payment Instruments");
        setIcon(R.drawable.ic_arrow_back_black_24dp);
        setIconMenu(R.drawable.ic_cards);
        userProfile= new Profile();
        gson = new Gson();
        gson1 = new Gson();
        customer= new Customer();
        prefManager= new PrefManager(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString("USER_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);

        requestQueue = Volley.newRequestQueue(this);
        listViewPayment = (ListView) findViewById(R.id.listPayment);
        llPaymentList = (LinearLayout) findViewById(R.id.paymentList);
        tvNoHistory =  findViewById(R.id.ivEmptyV);
        tvHeaderText =  findViewById(R.id.payment_Tittle);
        btnAddNewPayment =  findViewById(R.id.add_Payment);

        ivCash =  findViewById(R.id.payment_Cash);
        ivCredit =  findViewById(R.id.payment_Credit);

        btnAddNewPayment.setOnClickListener(this);
        if(prefManager !=null){
            paymentMode=prefManager.getPaymentMode();
            paymentCard=prefManager.getDefaultCard();

        }
        //paymentMode = (int) new PrefManager(this).getPaymentMode();
        v = findViewById(R.id.line);
        listCards = new ArrayList<Card>();
        if(paymentCard >0){
            adapter = new PaymentListAd(this, listCards, paymentCard);

        }

        listViewPayment.setAdapter(adapter);
        try {
            getCards();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActionNotification:
                onBackPressed();
                break;
            case R.id.tvAddNewPayment:
                startActivityForResult(new Intent(this,
                        AddPayMCardAct.class), REQUEST_ADD_CARD);
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


    @Override
    protected boolean isValidate() {
        // TODO Auto-generated method stub
        return false;
    }

    private void getCards() {
        UtilsExtra.showCustomProgressDialog(this,
                getString(R.string.loading1), false, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL,
                BookingConstant.ServiceType.GET_CARDS + BookingConstant.Params.ID + "="
                        + new PrefManager(this).getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "="
                        + new PrefManager(this).getSessionToken());
        // new HttpRequester(this, map, Const.ServiceCode.GET_CARDS, true,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_CARDS, this, this));
    }


    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        UtilsExtra.removeCustomProgressDialog();
        switch (serviceCode) {
            case BookingConstant.ServiceCode.GET_CARDS:
                if (new ParseContent(this).isSuccess(response)) {
                    listCards.clear();
                    new ParseContent(this).parseCards(response, listCards);
                    AppLog.Log("AwajimaViewPayment", "listCards : " + listCards.size());
                    if (listCards.size() > 0) {
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
                }
                break;
        }
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
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub
        AppLog.Log(TAG, error.getMessage());

    }
}