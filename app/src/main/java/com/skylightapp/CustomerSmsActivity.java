package com.skylightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.klinker.android.logger.Log;
import com.klinker.android.logger.OnLogListener;
import com.klinker.android.send_message.Utils;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Settings;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.twilio.Twilio;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.skylightapp.Classes.Customer.CUSTOMER_FIRST_NAME;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_SURNAME;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_CODE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DATE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_NUMBER_OF_DAYS;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_TOTAL;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;

public class CustomerSmsActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 10;
    String phoneNo;
    String message;
    int randomNumber;

    DBHelper dbHelper;
    AppCompatButton confirmButton;
    //AppCompatEditText reportID,skylightCode;
    String correct;
    int packageId;
    int reportId;
    long customerId;
    String customerPhoneN;
    SmsManager smsManager;

    SharedPreferences userPreferences;
    Gson gson;
    String json;
    String smsToC ;
    Profile userProfile;
    private Settings settings;
    private RecyclerView log;
    //private LogAdapter logAdapter;
    AppCompatSpinner customerSpinner;
    long profileID;
    long customerID;
    Customer customer;
    String manager;
    long packageID, savingsID;
    double totalAmount,packageBalance;
    String date,skylightCode,cusNames,cusFirstName,days,cusSurname;
    AppCompatTextView txtCusName,txtPackageID,txtSavingsID,txtNoOfDays,txtDate,txtTotal;
    private ArrayList<PaymentCode> paymentCodeArrayList;

    long savingsCode;
    CustomerDailyReport report;
    AppCompatEditText edtCode;
    private SkyLightPackage skyLightPackage;
    public static final String ACCOUNT_SID = System.getenv("SK0bf25b7e113908fb03287c9736f41922");
    public static final String AUTH_TOKEN = System.getenv("ZHRGw9EpQeSehku6qRlifwlco5kmmUb2");
    Bundle extras;
    private GroupSavings groupSavings;
    private GroupAccount groupAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_sms);
        extras = getIntent().getExtras();
        dbHelper=new DBHelper(this);
        if (extras != null) {
            customerID= extras.getLong(CUSTOMER_ID);
            cusFirstName= extras.getString(CUSTOMER_FIRST_NAME);
            cusSurname= extras.getString(CUSTOMER_SURNAME);
            userProfile= extras.getParcelable("Profile");
            customer= extras.getParcelable("Customer");
            skyLightPackage= extras.getParcelable("Package");
            groupAccount= extras.getParcelable("GroupAccount");
            groupSavings= extras.getParcelable("GroupSavings");
            cusSurname= extras.getString(CUSTOMER_SURNAME);
            cusNames= cusSurname+","+ cusSurname;
            packageID= extras.getLong(PACKAGE_ID);
            savingsID= extras.getLong(REPORT_ID);
            totalAmount= extras.getDouble(REPORT_TOTAL);
            date= extras.getString(REPORT_DATE);
            days= extras.getString(REPORT_NUMBER_OF_DAYS);
            savingsCode=extras.getLong(REPORT_CODE);
            report= extras.getParcelable("Savings");
            txtCusName =  findViewById(R.id.nameCus);
            txtCusName.setText(MessageFormat.format("Name{0}", cusNames));
            txtPackageID =  findViewById(R.id.IDPackage);
            txtPackageID.setText(MessageFormat.format("PackageID:{0}", packageID));
            txtSavingsID =  findViewById(R.id.IDSavings);
            txtSavingsID.setText(MessageFormat.format("Savings ID:{0}", savingsID));
            txtDate =  findViewById(R.id.DateSavings);
            txtDate.setText(MessageFormat.format("Date{0}", date));
            edtCode =  findViewById(R.id.add_skySMS);
            txtTotal =  findViewById(R.id.TotalAmt);
            txtTotal.setText(MessageFormat.format("Total day: N{0}/daysdays", totalAmount));
        }

        if (userProfile != null) {
            profileID =userProfile.getPID();
        }
        FloatingActionButton homeBack =findViewById(R.id.HomeSMS);

        manager =userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();

        Twilio.init("SK0bf25b7e113908fb03287c9736f41922", "ZHRGw9EpQeSehku6qRlifwlco5kmmUb2");
        //smsManager = SmsManager.getDefault();
        confirmButton = findViewById(R.id.confirm_customer0);

        //BroadcastUtils.sendExplicitBroadcast(this, new Intent(), "skylight sms action");
        //smsManager = SmsManager.getDefault();

        /*Intent mIntent = getIntent();
        if (mIntent !=null){
            packageId = mIntent.getLongExtra("packageId", 0);
            reportId = mIntent.getLongExtra("reportID", 0);
            customerId = mIntent.getLongExtra("customerId", 0);
            customerPhoneN = mIntent.getStringExtra("phoneNumber");

        }*/





        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmSavings(extras);

            }
        });
        homeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeMe();

            }
        });



    }


    private void homeMe() {
        Intent usersIntent = new Intent(CustomerSmsActivity.this,
                LoginDirAct.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    }
    private void confirmSavings(Bundle extras) {
        if (extras != null) {
            customerID= extras.getLong(CUSTOMER_ID);
            cusFirstName= extras.getString(CUSTOMER_FIRST_NAME);
            cusSurname= extras.getString(CUSTOMER_SURNAME);
            userProfile= extras.getParcelable("Profile");
            customer= extras.getParcelable("Customer");
            skyLightPackage= extras.getParcelable("Package");
            packageBalance=skyLightPackage.getPackageBalance();
        }
        packageBalance=skyLightPackage.getPackageBalance();

        for (int i = 0; i < paymentCodeArrayList.size(); i++) {
            if (paymentCodeArrayList.get(i).getCodeDate().equalsIgnoreCase(date) && paymentCodeArrayList.get(i).getCodeStatus().equalsIgnoreCase("Completed")) {
                Toast.makeText(this, "This savings has already been updated", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
                String codeDate = mdformat.format(calendar.getTime());
                skylightCode = Objects.requireNonNull(edtCode.getText()).toString();
                //PaymentCode paymentCode= new PaymentCode(customerID,savingsID,savingsCode,CODE_DATE);
                //dbHelper.saveNewSavingsCode(paymentCode);
                //customer.addPaymentCode(profileID,customerPhoneN, String.valueOf(savingsCode),codeDate,"not complete",manager);
                try {
                    if(skylightCode.equalsIgnoreCase(String.valueOf(savingsCode))){
                        dbHelper.updateSavingsStatus(packageId,reportId,packageBalance,"Completed");
                        customer.addCusTimeLine("Savings Code Update","The savings code for"+savingsID+"was confirmed"+"@"+codeDate);
                        userProfile.addPTimeLine("Code Update","You confirmed the savings code for"+savingsID+"@"+codeDate);
                        dbHelper.insertTimeLine("Code Update","The savings code for"+savingsID+""+"of"+cusNames+"was confirmed",codeDate,null);
                        new Intent(this,
                                LoginDirAct.class);
                        Toast.makeText(this, "Savings status has been updated Successfully", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(this, "Skylight-Customer security code is not correct", Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception NoSuchAlgorithmException) {
                    Toast.makeText(this, "Something went wrong as you try to input the code", Toast.LENGTH_SHORT).show();

                }


            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                smsManager.sendTextMessage(customerPhoneN, null, smsToC, null, null);
                Toast.makeText(this, "SMS sent.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            }
        }

    }
    private void initSettings() {
        settings = Settings.get(this);
        TextUtils.isEmpty(settings.getMmsc());//initApns();


    }


    private void initActions() {
        Utils.isDefaultSmsApp(this);

    }

    private void initLogging() {
        Log.setDebug(true);
        Log.setPath("messenger_log.txt");
        Log.setLogListener(new OnLogListener() {
            @Override
            public void onLogged(String tag, String message) {
                //logAdapter.addItem(tag + ": " + message);
            }
        });

    }
}