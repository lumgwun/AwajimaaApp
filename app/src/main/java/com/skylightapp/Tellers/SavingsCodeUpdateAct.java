package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class SavingsCodeUpdateAct extends AppCompatActivity {
    private Bundle bundle;
    private CustomerDailyReport customerDailyReport;
    private int reportID,customerID,reportProfileID;
    private long reportCode;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    private Profile userProfile,cusProfile;
    private int profileID,packageID;
    AppCompatButton btnUpdate;
    AppCompatEditText edtCode;
    private Gson gson,gson1;
    private String json,json1;
    String code;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserPassword,noOfDays, status,stringNoOfSavings,office, customerPhoneNo,officeBranch,dateOfReport,nameOfCustomer, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_savings_code);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile= new Profile();
        cusProfile= new Profile();
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        customerDailyReport=new CustomerDailyReport();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            customerDailyReport=bundle.getParcelable("CustomerDailyReport");

        }
        if(customerDailyReport !=null){
            customerID=customerDailyReport.getCustomerId();
            packageID=customerDailyReport.getPackageID();
            cusProfile=customerDailyReport.getProfile();
        }
        SharedPrefUserName=sharedpreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=sharedpreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=sharedpreferences.getString("machine", "");
        SharedPrefProfileID=sharedpreferences.getString("PROFILE_ID", "");
        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        if(cusProfile !=null){
            reportProfileID=cusProfile.getPID();

        }
        btnUpdate = findViewById(R.id.SavingsCodeButton);
        edtCode = findViewById(R.id.savingsCodeID);
        btnUpdate.setOnClickListener(this::confirmCode);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    code = Objects.requireNonNull(edtCode.getText()).toString();
                } catch (Exception e) {
                    System.out.println("Oops!");
                    edtCode.requestFocus();
                }
                if(code.equalsIgnoreCase(String.valueOf(reportCode))){
                    dbHelper.updatePackageRecord(reportProfileID,customerID,packageID,reportID, "Confirmed");

                }
            }
        });
    }

    public void confirmCode(View view) {
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            customerDailyReport=bundle.getParcelable("CustomerDailyReport");

        }
        if(customerDailyReport !=null){
            customerID=customerDailyReport.getCustomerId();
            packageID=customerDailyReport.getPackageID();
            cusProfile=customerDailyReport.getProfile();
        }

        try {
            code = Objects.requireNonNull(edtCode.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
            edtCode.requestFocus();
        }
        if(code.equalsIgnoreCase(String.valueOf(reportCode))){
            dbHelper.updatePackageRecord(reportProfileID,customerID,packageID,reportID, "Confirmed");

        }

    }
}