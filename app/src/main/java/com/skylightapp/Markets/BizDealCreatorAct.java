package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.BizDealDAO;
import com.skylightapp.MarketClasses.BusinessOthers;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class BizDealCreatorAct extends AppCompatActivity {
    SharedPreferences userPreferences;
    private double accountBalance;
    SecureRandom random;
    int profileID,  businessDealNo;
    int virtualAccountNumber;
    String creationDate;
    AppCompatSpinner state, office, spnDealType;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1;
    String json, json1, bDTittle,selectedDealType;
    private Profile adminProfile;
    private Calendar calendar;
    private AppCompatEditText edtDealTittle;
    private BizDealDAO bizDealDAO;
    private MaterialCardView btnCreate;
    private long bdID;
    private long bdFromID;
    private MarketBusiness marketBusiness;
    private boolean isHost=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_deal_creator);
        setTitle("Business Deal Creator");
        gson1 = new Gson();
        gson = new Gson();
        marketBusiness= new MarketBusiness();
        adminProfile = new Profile();
        bizDealDAO = new BizDealDAO(this);
        spnDealType = findViewById(R.id.Bd_type_selection);
        edtDealTittle = findViewById(R.id.biz_deal_tittle);
        btnCreate = findViewById(R.id.card_sub_bd);
        btnCreate.setOnClickListener(this::btnSubmitBizDeal);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        adminProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        businessDealNo = ThreadLocalRandom.current().nextInt(112537, 1040045);
        calendar = Calendar.getInstance();
        if (adminProfile != null) {
            profileID = adminProfile.getPID();

        }

        if(marketBusiness !=null){
            bdFromID=marketBusiness.getBusinessID();

        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        creationDate = mdformat.format(calendar.getTime());
        spnDealType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDealType = spnDealType.getSelectedItem().toString();
                selectedDealType = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bDTittle = edtDealTittle.getText().toString();
                isHost=true;
                bdID = bizDealDAO.saveBizDeal(businessDealNo, profileID, bdFromID, bDTittle, creationDate, selectedDealType, "new",isHost);

            }
        });
        if (bdID > 0) {
            Toast.makeText(BizDealCreatorAct.this, "Biz Deal creation, a Success", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(BizDealCreatorAct.this, "Biz Deal creation, failed!", Toast.LENGTH_SHORT).show();

        }
    }

    public void btnSubmitBizDeal(View view) {
    }
}