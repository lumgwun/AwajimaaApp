package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;


public class ManualPCodeUpdate extends AppCompatActivity {
    private Bundle bundle;

    private Payment payment;
    private int paymentID;
    private long paymentCode;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    private Profile userProfile;
    private long profileID;
    AppCompatButton btnUpdate;
    AppCompatEditText edtCode;
    private Gson gson,gson1;
    private String json,json1;
    long code;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserPassword,noOfDays, status,stringNoOfSavings,office, customerPhoneNo,officeBranch,dateOfReport,nameOfCustomer, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_manual_pcode_update);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile= new Profile();
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        payment=new Payment();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            payment=bundle.getParcelable("Payment");
        }
        if(payment !=null){
            paymentCode=payment.getPaymentCode();
        }
        SharedPrefUserName=sharedpreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=sharedpreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=sharedpreferences.getString("machine", "");
        SharedPrefProfileID=sharedpreferences.getString("PROFILE_ID", "");
        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        btnUpdate = findViewById(R.id.codeButton);
        edtCode = findViewById(R.id.codeID);
        btnUpdate.setOnClickListener(this::confirmCode);
    }

    public void confirmCode(View view) {
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            payment=bundle.getParcelable("Payment");
        }
        if(payment !=null){
            paymentCode=payment.getPaymentCode();
            paymentID=payment.getPaymentID();
        }
        try {
            code = Long.parseLong(Objects.requireNonNull(edtCode.getText()).toString());
        } catch (Exception e) {
            System.out.println("Oops!");
            edtCode.requestFocus();
        }
        PaymentDAO paymentDAO= new PaymentDAO(this);
        if(code==paymentCode){
            paymentDAO.updatePayment(paymentID,paymentCode,"Confirmed");

        }
    }
}