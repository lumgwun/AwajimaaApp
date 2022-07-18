package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class UpdateGrpAcct extends AppCompatActivity {
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
    private AppCompatEditText edtSurname,edtFirstName,edtPhone;
    AppCompatButton btnUpdate;
    String details,managerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_grp_acct);

        txtTittle =  findViewById(R.id.grpUpdateTittle);
        txtPurpose =  findViewById(R.id.grpUpdatePurpose);
        edtSurname =  findViewById(R.id.grpUpdateSurname);
        edtFirstName =  findViewById(R.id.grpUpdateFirstName);
        edtPhone =  findViewById(R.id.grpUpdatePhone);
        btnUpdate =  findViewById(R.id.UpdateGrpAcct);
        dbHelper = new DBHelper(this);
        userProfile=new Profile();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Bundle bundle = getIntent().getExtras() ;
        if(bundle !=null){
            bundle = this.getIntent().getExtras();
            groupAccount = bundle.getParcelable("GroupAccount");
            txtTittle.setText(MessageFormat.format("Grp Tittle:{0}", groupAccount.getGrpTittle()));
            txtPurpose.setText(MessageFormat.format("Grp Purpose:{0}", groupAccount.getGrpPurpose()));

        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userProfile !=null){
                    managerName=userProfile.getProfileLastName()+""+userProfile.getProfileFirstName();
                }
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String updateDate = mdformat.format(calendar.getTime());
                details=managerName+""+"updated some details of group Acct."+groupAccount.getGrpAcctNo();
                grpAcctID=groupAccount.getGrpAcctNo();
                tittle=txtTittle.getText().toString();
                purpose=txtPurpose.getText().toString();
                surname=edtSurname.getText().toString();
                firstName=edtFirstName.getText().toString();
                phoneNo=edtPhone.getText().toString();
                dbHelper.updateGrpAcct(grpAcctID,tittle,purpose,surname,firstName,phoneNo);
                dbHelper.insertTimeLine("Group Acct updated",details,updateDate,null);

            }
        });
    }
}