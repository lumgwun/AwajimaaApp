package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.util.Random;

public class UpdateTranxAct extends AppCompatActivity {
    private Bundle bundle;
    private Transaction transaction;
    private long transactionID;
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    Random ran ;
    DBHelper dbHelper;
    long profileID,grpAcctID;
    AppCompatTextView txtTittle,txtPurpose;
    private GroupAccount groupAccount;
    private AppCompatEditText edtTXCode,edtFirstName,edtPhone;
    AppCompatButton btnUpdate;
    String details,managerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_tranx);
        dbHelper = new DBHelper(this);
        userProfile=new Profile();
        //edtTXCode =  findViewById(R.id.grpUpdateSurname);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        bundle= getIntent().getExtras();
        if(bundle !=null){
            transaction=bundle.getParcelable("Transaction");
            transactionID= transaction.getTransactionID();
        }
    }
}