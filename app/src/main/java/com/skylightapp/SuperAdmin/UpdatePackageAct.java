package com.skylightapp.SuperAdmin;

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
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class UpdatePackageAct extends AppCompatActivity {
    private Bundle bundle;

    private CustomerDailyReport customerDailyReport;
    private SkyLightPackage skyLightPackage;
    private long reportID;
    private int customerID;
    private int reportProfileID;
    private Long packageCode;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    private Profile userProfile;
    private long profileID;
    private int packageID;
    private long codeLong;
    AppCompatButton btnUpdate;
    AppCompatEditText edtCode;
    private Gson gson,gson1;
    private String json,json1;
    String code;
    int itemRemCount,newItemCount;
    String SharedPrefUserPassword,noOfDays,officeBranch, itemName,status,stringNoOfSavings,office, customerPhoneNo,dateOfReport,nameOfCustomer, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_package);
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile= new Profile();
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        customerDailyReport=new CustomerDailyReport();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            skyLightPackage=bundle.getParcelable("SkyLightPackage");
            itemRemCount=bundle.getInt("ItemCount");
            officeBranch=bundle.getString("Office");
            itemName=bundle.getString("ItemName");

        }
        if(itemRemCount>0){
            newItemCount=itemRemCount-1;
        }
        if(skyLightPackage !=null){
            reportProfileID=skyLightPackage.getProfile().getPID();
            customerID=skyLightPackage.getCustomerId();
            packageID=skyLightPackage.getPackageID();
        }
        SharedPrefUserName=sharedpreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=sharedpreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=sharedpreferences.getString("machine", "");
        SharedPrefProfileID=sharedpreferences.getString("PROFILE_ID", "");
        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        btnUpdate = findViewById(R.id.btnPackageCode);
        edtCode = findViewById(R.id.packageCodeI);
        btnUpdate.setOnClickListener(this::confirmPackageCode);
    }

    public void confirmPackageCode(View view) {
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            skyLightPackage=bundle.getParcelable("SkyLightPackage");

        }
        if(skyLightPackage !=null){
            reportProfileID=skyLightPackage.getProfile().getPID();
            customerID=skyLightPackage.getCustomerId();
            packageID=skyLightPackage.getPackageID();
            packageCode =skyLightPackage.getPackageCode();
        }

        try {
            code = Objects.requireNonNull(edtCode.getText()).toString();
            codeLong= Long.parseLong(code);
        } catch (Exception e) {
            System.out.println("Oops!");
            edtCode.requestFocus();
        }
        if(codeLong==packageCode){
            dbHelper.updatePackageForCollection(reportProfileID,customerID,packageID,"Collected");
            dbHelper.updateBranchStockCount(officeBranch,itemName,newItemCount);

        }


    }
}