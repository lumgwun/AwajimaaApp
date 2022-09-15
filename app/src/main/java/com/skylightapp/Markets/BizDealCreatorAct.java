package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.BizDealDAO;
import com.skylightapp.MarketClasses.Business;
import com.skylightapp.R;
import com.skylightapp.SignUpAct;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class BizDealCreatorAct extends AppCompatActivity {
    SharedPreferences userPreferences;
    private double accountBalance;
    SecureRandom random;
    int profileID,  businessAcctNo;
    int virtualAccountNumber;
    String creationDate;
    AppCompatSpinner state, office, spnDealType;
    SupportSQLiteDatabase supportSQLiteDatabase;
    private static final String PREF_NAME = "skylight";
    Gson gson, gson1;
    String json, json1, bDTittle,selectedDealType;
    private Business business;
    private Profile adminProfile;
    private Calendar calendar;
    private AppCompatEditText edtDealTittle;
    private BizDealDAO bizDealDAO;
    private MaterialCardView btnCreate;
    private long bdID;
    private int bdFromID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_deal_creator);
        setTitle("Business Deal Creation");
        gson1 = new Gson();
        gson = new Gson();
        business = new Business();
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
        business = gson1.fromJson(json1, Business.class);
        businessAcctNo = ThreadLocalRandom.current().nextInt(112537, 1040045);
        calendar = Calendar.getInstance();
        if (adminProfile != null) {
            profileID = adminProfile.getPID();

        }
        if (business != null) {
            bdFromID = business.getBusIdentity();

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
                bdID = bizDealDAO.saveBizDeal(businessAcctNo, profileID, bdFromID, bDTittle, creationDate, selectedDealType, "new");

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