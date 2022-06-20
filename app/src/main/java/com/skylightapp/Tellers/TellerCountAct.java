package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TellerCountAct extends AppCompatActivity {
    private TextView txtTellerPaymentT,txtCustomerPaymentToday,txtTellerTotalPayment,txtTellerNewCus;
    TextView txtTotalForTheDay,txtDurationInDays,txtTotalPaymentToday, txtTotalManualPaymentToday,txtNewCusToday,totalSavings2Today, txttotalSaving,totalSoCountToday,txtNewPackageCountToday, txtNewTXToday,txtTotalSavingsToday,txtAllProfileCount;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson;
    String json,branchName,branchName1,branchName2,tellerIDString,tellerIDString1,tellerIDString2,customerIDString;
    double totalSavings2Today33, paymentTotalForCustomer,totalSavings,totalManualPaymentToday;
    int totalSavingsToday,soCount,txCountToday,newPackCount,countPackageToday,customerCountToday,newCusCount,customersForTeller;
    private Profile userProfile;
    private Date date;
    private int tellerID;
    private long tellerID1;
    private int customerID;
    private long tellerID2;
    private AppCompatButton btnCusIDToGetPayment;
    private AppCompatEditText edtCustomerPaymentToday;
    Date newDate1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_count);
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        newDate1= new Date();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        try {
            newDate1=sdf.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            tellerID=userProfile.getPID();
        }
        txtTotalSavingsToday =  findViewById(R.id.txtTellerTotalSavingsToday);
        totalSavings2Today33=dbHelper.getTotalSavingsTodayForTeller(tellerID,newDate1);
        txtTotalSavingsToday.setText(MessageFormat.format("Total savings,today: N{0}", totalSavings2Today33));
        txttotalSaving =  findViewById(R.id.TellertotalSavingAll);
        totalSavings=dbHelper.getTotalSavingsForTeller(tellerID);
        txttotalSaving.setText(MessageFormat.format("Total Savings N{0}", totalSavings));

        txtTellerNewCus = findViewById(R.id.txtTellerNewCusToday);
        newCusCount=dbHelper.getNewCustomersCountForTodayTeller(tellerID,todayDate);
        txtTellerNewCus.setText(MessageFormat.format("My New Customers,today{0}", newCusCount));
        txtNewPackageCountToday =  findViewById(R.id.txtTellerNewPackageCountToday);
        newPackCount=dbHelper.getNewPackageCountForTellerToday(tellerID,todayDate);
        txtNewPackageCountToday.setText(MessageFormat.format("New Pack Count:{0}", newPackCount));
        txtNewTXToday = findViewById(R.id.txtTellerNewTXToday);
        txtTotalManualPaymentToday =  findViewById(R.id.TotalTellerManualPayments);
        totalManualPaymentToday=dbHelper.getTotalPaymentTodayForTeller(tellerID,newDate1);
        txtTotalManualPaymentToday.setText(MessageFormat.format("Total Manual Payment N{0}", totalManualPaymentToday));

        txtCustomerPaymentToday = findViewById(R.id.TellerCustomerPaymentToday);
        edtCustomerPaymentToday =  findViewById(R.id.edtTellerCustomerPaymentToday);
        btnCusIDToGetPayment =  findViewById(R.id.buttonTellerCustomerPayment);
        Date finalNewDate = newDate1;
        btnCusIDToGetPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    customerIDString = edtCustomerPaymentToday.getText().toString().trim();
                    customerID= Integer.parseInt((customerIDString));
                } catch (Exception e) {
                    System.out.println("Oops!");
                }
                paymentTotalForCustomer =dbHelper.getTotalPaymentTodayForCustomer(customerID, finalNewDate);
                txtCustomerPaymentToday.setText(MessageFormat.format("Customer Payment today{0}", paymentTotalForCustomer));

            }
        });

    }

    public void getCusTellerPaymentToday(View view) {
    }
}