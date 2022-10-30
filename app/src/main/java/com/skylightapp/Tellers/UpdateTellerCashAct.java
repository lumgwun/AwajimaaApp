package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.AppCash;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class UpdateTellerCashAct extends AppCompatActivity {
    Bundle bundle;
    AppCash appCash;
    private AppCompatButton btnRunUpdate;
    DatePicker picker;
    String superAdminName,tellerConfirmationCode, officeBranch;
    Spinner spnDepositStatus;
    DBHelper dbHelper;
    int tellerCashID;
    long code;
    long tellerCashCode;
    long profileID;
    long tellerCashProfileID;
    TextView txtDepositID;
    Bundle userBundle;
    SharedPreferences userPreferences;
    Profile managerProfile;
    Gson gson,gson1;
    String json,json1,nIN;
    CustomerManager customerManager;
    private AppCompatEditText edtCode;
    private static final String PREF_NAME = "awajima";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_teller_cash);
        appCash = new AppCash();
        managerProfile= new Profile();
        customerManager=new CustomerManager();
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerManagerUsed", "");
        customerManager = gson1.fromJson(json1, CustomerManager.class);
        userBundle= new Bundle();
        spnDepositStatus = findViewById(R.id.spinnerUpdateS);
        edtCode = findViewById(R.id.code_edict_);
        txtDepositID = findViewById(R.id.txtDepositID);
        btnRunUpdate = findViewById(R.id.buttonTCC);
        btnRunUpdate.setOnClickListener(this::getSubmitCode);
        superAdminName="Super Admin";
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar1.getTime());
        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }
        bundle=getIntent().getExtras();
        if(bundle !=null){
            appCash =bundle.getParcelable("TellerCash");

        }
        if(appCash !=null){
            tellerCashID = appCash.getSkylightCashID();
            officeBranch= appCash.getSCPayee();
            tellerCashCode= appCash.getSkylightCashCode();
            tellerCashProfileID= appCash.getSCash_ProfileID();
        }

        txtDepositID.setText("Teller Cash ID:"+ tellerCashID);
        btnRunUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tellerConfirmationCode = Objects.requireNonNull(edtCode.getText()).toString();
                } catch (Exception e) {
                    System.out.println("Oops!");
                    edtCode.requestFocus();
                }
                code= Long.parseLong(tellerConfirmationCode);

                if(code==tellerCashCode){
                    if(tellerCashProfileID==profileID){
                        dbHelper.updateTellerCashWithCode(officeBranch,tellerCashID,code,todayDate);
                    }


                }else {
                    Toast.makeText(UpdateTellerCashAct.this, "Please, input the right code for this Teller Cash" , Toast.LENGTH_LONG).show();
                    edtCode.requestFocus();
                }

            }
        });
    }

    public void getSubmitCode(View view) {

    }

    public void showMyReports(View view) {
        managerProfile= new Profile();
        customerManager=new CustomerManager();
        dbHelper= new DBHelper(this);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }
        userBundle.putParcelable("Profile",managerProfile);
        userBundle.putLong("PROFILE_ID",profileID);
        Intent intent8 = new Intent(this, TellerReportTab.class);
        intent8.putExtras(userBundle);
        startActivity(intent8);
        Toast.makeText(UpdateTellerCashAct.this, "Taking you to your Teller Dashboard", Toast.LENGTH_LONG).show();
    }

    public void showMyCashList(View view) {
        managerProfile= new Profile();
        customerManager=new CustomerManager();
        dbHelper= new DBHelper(this);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }
        userBundle.putParcelable("Profile",managerProfile);
        userBundle.putLong("PROFILE_ID",profileID);
        Intent intent8 = new Intent(this, MyCashList.class);
        intent8.putExtras(userBundle);
        startActivity(intent8);
        Toast.makeText(UpdateTellerCashAct.this, "Taking you to your Teller Dashboard", Toast.LENGTH_LONG).show();
    }

    public void showMyDashboard(View view) {
        managerProfile= new Profile();
        customerManager=new CustomerManager();
        dbHelper= new DBHelper(this);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }
        userBundle.putParcelable("Profile",managerProfile);
        userBundle.putLong("PROFILE_ID",profileID);
        Intent intent8 = new Intent(this, TellerHomeChoices.class);
        intent8.putExtras(userBundle);
        startActivity(intent8);
        Toast.makeText(UpdateTellerCashAct.this, "Taking you to your Teller Dashboard", Toast.LENGTH_LONG).show();
    }
}