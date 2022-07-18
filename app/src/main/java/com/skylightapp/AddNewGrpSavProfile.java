package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class AddNewGrpSavProfile extends AppCompatActivity {
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    Random ran ;
    DBHelper dbHelper;
    long profileID,userID;
    AppCompatTextView txtNoSavings;
    AppCompatTextView txtTittle,txtPurpose;
    private GroupAccount groupAccount;
    private AppCompatEditText edtUserID,edtFirstName,edtPhone;
    AppCompatButton btnAddNewSaver;
    long grpAcctID,grpAccountProfileNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_new_grps_profile);
        dbHelper = new DBHelper(this);
        txtTittle =  findViewById(R.id.grpUpdateTittle);
        txtPurpose =  findViewById(R.id.grpUpdatePurpose);
        edtUserID =  findViewById(R.id.grpAcctUserID);
        edtPhone =  findViewById(R.id.grpAcctUserPhone);
        btnAddNewSaver =  findViewById(R.id.grpAcctBtn);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        Bundle bundle = getIntent().getExtras() ;
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bundle !=null){
            groupAccount = bundle.getParcelable("GroupAccount");
            txtTittle.setText(MessageFormat.format("Grp Tittle:{0}", groupAccount.getGrpTittle()));
            txtPurpose.setText(MessageFormat.format("Grp Purpose:{0}", groupAccount.getGrpPurpose()));

        }
        btnAddNewSaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                grpAccountProfileNo = (long) random.nextInt((int) (Math.random() * 909) + 1119);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String joinedDate = mdformat.format(calendar.getTime());
                grpAcctID=groupAccount.getGrpAcctNo();
                tittle=txtTittle.getText().toString();
                purpose=txtPurpose.getText().toString();
                userID= Long.parseLong(edtUserID.getText().toString());
                phoneNo=edtPhone.getText().toString();
                userProfile.getGroupAccount(grpAcctID).addGrpProfile((int) grpAccountProfileNo,userID,"new");
                dbHelper.insertGroupAccountProfile(grpAcctID,grpAccountProfileNo,userID,phoneNo,joinedDate);

            }
        });
    }
    public void goHome(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}