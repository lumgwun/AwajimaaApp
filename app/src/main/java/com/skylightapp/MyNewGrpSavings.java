package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.gson.Gson;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GroupAccountDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;


import static com.skylightapp.Classes.Profile.PROFILE_ID;




public class MyNewGrpSavings extends AppCompatActivity {
    Profile userProfile;
    int profileUID;
    long id;
    int grpAcctNo;
    private SharedPreferences userPreferences;
    AppCompatEditText edtTittle,edtPurpose,edtFirstName,edtSurName,edtAmount,edtEmail,edtPhoneNo;
    String ManagerSurname,grpDateDate,managerFirstName,managerPhoneNumber1,managerEmail, managerNIN,managerUserName;

    private Calendar calendar;
    protected DBHelper dbHelper;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    Random ran ;
    private  double amount;
    AppCompatSpinner spnFrequency,spnDuration;
    AppCompatButton btnCreateGroup;
    Bundle userBundle;
    private String TWILLO_ACCOUNT_SID= BuildConfig.T_ACCT_SID;
    private String TWILLO_AUTH_TOKEN= BuildConfig.T_AUTH_TOKEN;
    private SODAO sodao;
    private static final String PREF_NAME = "awajima";
    private int selectedCurrencyIndex;
    private AppCompatSpinner spnCurrency;
    private List<Currency>currencyList;
    private com.blongho.country_data.Currency currency;
    private String currencyCode,selectedFreq,selectedDuration;
    private CurrAdapter currencyAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private int freqInt,durationInt;
    private Uri grpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_my_new_grp_savings);
        dbHelper=new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        World.init(this);
        userProfile=new Profile();
        random= new SecureRandom();

        currencyList= new ArrayList<>();
        json = userPreferences.getString("LastProfileUsed", "");
        edtTittle =  findViewById(R.id.grpTittle);
        edtPurpose =  findViewById(R.id.grpPurpose);
        edtFirstName =  findViewById(R.id.grpFirstName);
        edtSurName =  findViewById(R.id.grpSurname);
        edtAmount =  findViewById(R.id.grpAmount);
        edtEmail =  findViewById(R.id.grpEmail);
        edtPhoneNo =  findViewById(R.id.grpPhoneNo);
        spnFrequency =  findViewById(R.id.grpFreq);
        spnCurrency =  findViewById(R.id.grpCurrency);
        spnDuration =  findViewById(R.id.grp_Duration);
        btnCreateGroup =  findViewById(R.id.grpCreateGrp);

        userProfile = gson.fromJson(json, Profile.class);
        userBundle=new Bundle();

        spnFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedOffice = office.getSelectedItem().toString();
                selectedFreq = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedOffice = office.getSelectedItem().toString();
                selectedDuration = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        currencyList= World.getAllCurrencies();
        currencyAdapter = new CurrAdapter(this, R.layout.list_currency, currencyList);
        spnCurrency.setAdapter(currencyAdapter);
        spnCurrency.setSelection(0);
        spnCurrency.setSelection(currencyAdapter.getPosition(currency));

        selectedCurrencyIndex = spnCurrency.getSelectedItemPosition();
        try {
            currency = currencyList.get(selectedCurrencyIndex);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(currency !=null){
            currencyCode=currency.getCode();
        }

        if(userProfile !=null){
            ManagerSurname = userProfile.getProfileLastName();
            managerFirstName = userProfile.getProfileFirstName();
            managerPhoneNumber1 = userProfile.getProfilePhoneNumber();
            managerEmail = userProfile.getProfileEmail();
            managerNIN = userProfile.getProfileIdentity();
            managerUserName = userProfile.getProfileUserName();
            machine = userProfile.getProfileMachine();
            profileUID = userProfile.getPID();
            userBundle.putString(PROFILE_ID, String.valueOf(profileUID));
            userBundle.putString("Machine",machine);

        }
        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();

        }
        grpAcctNo = ThreadLocalRandom.current().nextInt(1125, 10490);
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userProfile !=null){
                    ManagerSurname = userProfile.getProfileLastName();
                    managerFirstName = userProfile.getProfileFirstName();
                    managerPhoneNumber1 = userProfile.getProfilePhoneNumber();
                    managerEmail = userProfile.getProfileEmail();
                    managerNIN = userProfile.getProfileIdentity();
                    managerUserName = userProfile.getProfileUserName();
                    String userRole = userProfile.getProfileMachine();
                    profileUID = userProfile.getPID();
                    userBundle.putString(PROFILE_ID, String.valueOf(profileUID));
                    userBundle.putString("Machine",userRole);



                }
                calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                grpDateDate = mdformat.format(calendar.getTime());
                Date currentDate = calendar.getTime();


                try {
                    tittle = edtTittle.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    purpose = edtPurpose.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }


                /*try {
                    firstName = edtFirstName.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }*/
                try {
                    amount = Double.parseDouble(edtAmount.getText().toString());
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }
                /*try {
                    surname = edtSurName.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    emailAddress = edtEmail.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    phoneNo = edtPhoneNo.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }*/
                try {
                    grpAcctNo = random.nextInt((int) (Math.random() * 902304) + 136197);

                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }

                try {
                    if (String.valueOf(purpose).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "Purpose can not be left empty", Toast.LENGTH_LONG).show();


                    }
                    /*if (String.valueOf(firstName).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "First Name can not be left empty", Toast.LENGTH_LONG).show();
                    }*/
                    if (String.valueOf(amount).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "Amount can not be left empty", Toast.LENGTH_LONG).show();
                    }
                    /*if (String.valueOf(surname).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "Surname can not be left empty", Toast.LENGTH_LONG).show();


                    }*/else {
                        String status3 = "Subscription in progress";
                        String status1 = "Savings Unconfirmed";

                        try {
                            freqInt= Integer.parseInt(selectedFreq);
                            durationInt= Integer.parseInt(selectedDuration);

                        } catch (NumberFormatException e) {
                            System.out.println("Oops!");
                        }
                        grpDateDate = mdformat.format(calendar.getTime());
                        String tittle = "New Group Savings alert";
                        String timelineDetails1 = "A new Group savings of "+currencyCode+ amount + "was started @"+""+currentDate;
                        String timelineDetails = "A new Group savings of " +currencyCode+ amount + "was started";
                        TimeLineClassDAO timeLineClassDAO= new TimeLineClassDAO(MyNewGrpSavings.this);
                        GroupAccountDAO groupAccountDAO= new GroupAccountDAO(MyNewGrpSavings.this);
                        GroupAccount groupAccount= new GroupAccount(grpAcctNo,profileUID, tittle,purpose,managerFirstName,ManagerSurname,managerPhoneNumber1,managerEmail,grpDateDate,amount,currencyCode,freqInt,durationInt,0.00);


                        try {

                            timeLineClassDAO.insertTimeLine(tittle, timelineDetails, grpDateDate, null);
                            groupAccountDAO.insertGroupAccount(grpAcctNo, profileUID, tittle, purpose,managerFirstName,ManagerSurname,managerPhoneNumber1,managerEmail,grpDateDate,amount,currencyCode,freqInt,durationInt,0.00,grpLink,null,"new");
                            doNotification();

                        } catch (SQLiteException e) {
                            System.out.println("Oops!");
                        }

                        if(userProfile !=null){
                            userProfile.addPTimeLine(tittle,timelineDetails1);
                            userProfile.addPSavingsGrpAcct(groupAccount);


                        }


                        String paymentMessage = "Congratulations" + surname+","+ firstName + " a new Group Savings on the Awajima App,is for You";

                        Twilio.init(TWILLO_ACCOUNT_SID, TWILLO_AUTH_TOKEN);
                        Message message = Message.creator(
                                new com.twilio.type.PhoneNumber(phoneNo),
                                new com.twilio.type.PhoneNumber("234"+phoneNo),
                                paymentMessage)
                                .create();
                        //message.getStatus();

                        Intent amTIntent = new Intent(MyNewGrpSavings.this, LoginDirAct.class);
                        amTIntent.putExtras(userBundle);
                        amTIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    }

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }
        });
        btnCreateGroup.setOnClickListener(this::DoGrpAcctCreation);
    }
    private void doNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Group Creation")
                        .setContentText("A new Group was successfully created");

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void DoGrpAcctCreation(View view) {
    }
}