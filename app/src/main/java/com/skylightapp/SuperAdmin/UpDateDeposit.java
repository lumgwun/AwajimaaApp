package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class UpDateDeposit extends AppCompatActivity {
    Bundle bundle;
    AdminBankDeposit adminBankDeposit;
    private AppCompatButton btnRunUpdate;
    DatePicker picker;
    String selectedStatus,dateOfApproval,superAdminName;
    Spinner spnDepositStatus;
    int selectedDepositIndex;
    AppCompatEditText edtAmount;
    DBHelper dbHelper;
    long adminDepositID;
    TextView txtDepositID;
    Gson gson,gson1;
    String json,json1,nIN;
    Profile userProfile;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;

    Spinner.OnItemSelectedListener spnClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.getId() == spnDepositStatus.getId()) {
                selectedDepositIndex = i;
                try {
                    selectedStatus = adapterView.getItemAtPosition(i).toString();

                    try {
                        if(selectedDepositIndex==0){
                            spnDepositStatus.setFocusable(true);
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }
                selectedStatus = adapterView.getItemAtPosition(i).toString();


            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_up_date_deposit);
        adminBankDeposit= new AdminBankDeposit();
        dbHelper= new DBHelper(this);
        spnDepositStatus = findViewById(R.id.spinnerUpdateS);
        edtAmount = findViewById(R.id.adminEdtAmount);
        txtDepositID = findViewById(R.id.txtDepositID);
        gson1 = new Gson();
        gson = new Gson();
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json = userPreferences.getString("LastTellerProfileUsed", "");

        btnRunUpdate = findViewById(R.id.confirmDepositUpdate);
        btnRunUpdate.setOnClickListener(this::updateDepositReport);
        superAdminName="Super Admin";
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(calendar1.getTime());
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfApproval);
            }
        });
        dateOfApproval = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

        spnDepositStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedStatus = parent.getItemAtPosition(position).toString();
                Toast.makeText(UpDateDeposit.this, "Deposit Status: "+ selectedStatus,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(dateOfApproval==null){
            dateOfApproval=todayDate;
        }

        bundle=getIntent().getExtras();
        if(bundle !=null){
            adminBankDeposit=bundle.getParcelable("AdminBankDeposit");

        }
        if(adminBankDeposit !=null){
            adminDepositID=adminBankDeposit.getDepositID();
        }

        txtDepositID.setText("Deposit ID:"+adminDepositID);
        btnRunUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.updateAdminDeposit(adminDepositID,selectedStatus,superAdminName,dateOfApproval);

            }
        });
    }
    private void chooseDate(String dateOfApproval) {
        dateOfApproval = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();


    }

    public void updateDepositReport(View view) {
    }
}