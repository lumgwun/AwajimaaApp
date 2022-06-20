package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminDepositAct extends AppCompatActivity {
    Date currentDate;
    public final static int KEY_EXTRA_REPORT_ID = 1224;

    private DBHelper selector;
    private Profile userProfile;
    private AdminUser adminUser;
    //private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle messageBundle;
    private  int senderProfileID,adminDepositID,SharedPrefAdminID;
    private  String purpose;
    private Spinner spnPurpose ,spnOffice;
    private  String selectedPurpose,sendee;
    private Customer customer;
    DatePicker picker;
    private  Spinner spnBankToDeposit;
    private AppCompatButton btnConfirmation;
    EditText edtAmounts;
    private double amountEntered;
    private ArrayList<AdminBankDeposit> adminBankDeposits;
    private int adminID;
    private int noOfSavings;
    DBHelper applicationDb;
    int officeBranchID;
    int selectedNoIndex,intAdID;
    SecureRandom random;
    AdminBankDeposit adminBankDeposit;
    private static final String PREF_NAME = "skylight";
    private Calendar calendar;
    SQLiteDatabase sqLiteDatabase;
    String SharedPrefUserPassword,dateSting, selectedBank,stringNoOfSavings, officeBranch,dateOfReport, cmFirstName,cmLastName, adminOfficer,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;
    Spinner.OnItemSelectedListener spnClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.getId() == spnBankToDeposit.getId()) {
                selectedNoIndex = i;
                try {
                    stringNoOfSavings = (String) adapterView.getSelectedItem();
                    noOfSavings=Integer.parseInt(stringNoOfSavings);

                    try {
                        if(selectedNoIndex==0){
                            spnBankToDeposit.setFocusable(true);
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }
                stringNoOfSavings = (String) adapterView.getSelectedItem();
                noOfSavings=Integer.parseInt(stringNoOfSavings);

            }
            else if (adapterView.getId() == spnOffice.getId()) {
                officeBranch = (String) adapterView.getSelectedItem();
                if(officeBranch.equalsIgnoreCase("Trans-Amadi")){
                    officeBranchID=200;
                }
                if(officeBranch.equalsIgnoreCase("Elelenwo")){
                    officeBranchID=201;
                }
                if(officeBranch.equalsIgnoreCase("Wimpey")){
                    officeBranchID=202;
                }
                Toast.makeText(AdminDepositAct.this, "Office Branch: "  + officeBranch, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_deposit);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        adminUser =new AdminUser();
        adminBankDeposit= new AdminBankDeposit();
        random = new SecureRandom();
        adminBankDeposits =new ArrayList<>();
        applicationDb = new DBHelper(this);
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        adminUser = gson1.fromJson(json, AdminUser.class);
        if(adminUser !=null){
            cmFirstName = adminUser.getAdminFirstName();
            cmLastName = adminUser.getAdminSurname();
            adminName =cmLastName+","+cmFirstName;
            adminID = adminUser.getAdminID();
        }
        if(userProfile !=null){
            senderProfileID=userProfile.getPID();
        }
        picker=(DatePicker)findViewById(R.id.admin_date_deposit);
        spnBankToDeposit = findViewById(R.id.spinnerSelectBank);
        spnBankToDeposit.setOnItemSelectedListener(spnClickListener);

        spnBankToDeposit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedBank = (String) parent.getSelectedItem();
                Toast.makeText(AdminDepositAct.this, "No. of Customers for today: "+ AdminDepositAct.this.noOfSavings,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        edtAmounts = findViewById(R.id.adminDepositAmount);
        spnOffice = findViewById(R.id.spinnerAdminOffice);
        spnOffice.setOnItemSelectedListener(spnClickListener);

        spnOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                officeBranch = (String) parent.getSelectedItem();
                if(officeBranch.equalsIgnoreCase("Trans-Amadi")){
                    officeBranchID=200;
                }
                if(officeBranch.equalsIgnoreCase("Elelenwo")){
                    officeBranchID=201;
                }
                if(officeBranch.equalsIgnoreCase("Wimpey")){
                    officeBranchID=202;
                }
                Toast.makeText(AdminDepositAct.this, "Office Branch: "  + officeBranch, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConfirmation = findViewById(R.id.confirmSubmitAdminDe);
        btnConfirmation.setOnClickListener(this::sendReport);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfReport);
            }
        });
        if(dateOfReport ==null){
            calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault());
            dateSting = dateFormat.format(calendar.getTime());

            dateOfReport=dateSting;

        }
        dateOfReport = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


        btnConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    amountEntered = Double.parseDouble(edtAmounts.getText().toString().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }
                if(amountEntered<100){
                    edtAmounts.setFocusable(true);
                    return;

                }
                if(officeBranch.isEmpty()){
                    return;
                }
                if(selectedBank.isEmpty()){
                    return;
                }else {
                    adminDepositID = random.nextInt((int) (Math.random() * 1013) + 3511);
                    intAdID = random.nextInt((int) (Math.random() * 1012) + 1511);
                    processReport(adminDepositID,adminUser,senderProfileID, adminName,officeBranch,dateOfReport,userProfile, selectedBank,adminBankDeposits);


                }

            }
        });
    }

    private void chooseDate(String dateOfReport) {
        dateOfReport = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


    }
    private void processReport(int depositID, AdminUser adminUser, int senderProfileID, String adminName, String officeBranch , String dateOfReport, Profile userProfile, String selectedBank, ArrayList<AdminBankDeposit> adminDeposits){
        Date reportDate=null;
        adminDeposits = null;
        String date=null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateSting = dateFormat.format(calendar.getTime());
        applicationDb = new DBHelper(this);
        currentDate=calendar.getTime();


        String timelineTittle="Admin Deposit Report Alert";
        String timelineTittle2="Your Deposit Report Submission";
        String timelineDetails= adminName +" just Submitted Report";

        String timelineDetails2= null;
        try {
            timelineDetails2 = " You just Submitted Deposit Report @"+""+ Utils.formatDateTime(dateFormat.parse(dateSting));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Location location=null;
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            //dbHelper = new DBHelper(this);
            sqLiteDatabase = applicationDb.getWritableDatabase();
            try {
                adminDeposits=applicationDb.getAllAdminBankDeposit();
            } catch (Exception e) {
                System.out.println("Oops!");
            }


        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            reportDate = mdformat.parse(dateOfReport);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateSting = mdformat.format(calendar.getTime());
        adminDepositID = random.nextInt((int) (Math.random() * 1013) + 3511);
        intAdID = random.nextInt((int) (Math.random() * 1012) + 1511);
        try {
            currentDate=dateFormat.parse(dateSting);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < adminDeposits.size(); i++) {
                date = adminDeposits.get(i).getDepositDate();
                if (adminDeposits.get(i).getDepositor().equalsIgnoreCase(adminName)&& date.equalsIgnoreCase(dateOfReport)||adminDeposits.get(i).getDepositBank().equalsIgnoreCase(selectedBank)||adminDeposits.get(i).getDepositOfficeBranch().equalsIgnoreCase(officeBranch)) {
                    Toast.makeText(AdminDepositAct.this, "There is a similar Deposit report" , Toast.LENGTH_LONG).show();
                    return;

                }else {
                    if (adminUser != null) {
                        userProfile.addTimeLine(timelineTittle2,timelineDetails2);
                        userProfile.addTellerReport(intAdID,officeBranch,amountEntered, noOfSavings,dateSting);
                        adminUser.addTimeLine(timelineTittle2,timelineDetails2);
                        adminUser.addDepositReport(intAdID,adminName,officeBranch,selectedBank,amountEntered,reportDate);
                    }
                    adminBankDeposit= new AdminBankDeposit(depositID,senderProfileID,adminName,dateOfReport,officeBranch,selectedBank,amountEntered);
                    adminBankDeposit.setDepositBank(selectedBank);
                    adminBankDeposit.setDepositID(depositID);
                    adminBankDeposit.setDepositOfficeBranch(officeBranch);
                    adminBankDeposit.setDepositDate(dateOfReport);
                    adminBankDeposit.setDepositor(adminName);
                    adminBankDeposit.setProfileID(senderProfileID);
                    adminBankDeposit.setDepositAmount(amountEntered);
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        //dbHelper = new DBHelper(this);
                        sqLiteDatabase = applicationDb.getWritableDatabase();
                        applicationDb.saveNewAdminDeposit(depositID, senderProfileID,adminName,dateOfReport,officeBranch,selectedBank,amountEntered);
                        applicationDb.insertTimeLine(timelineTittle,timelineDetails,dateOfReport,location);


                    }


                    startNotification();
                    Toast.makeText(AdminDepositAct.this, "Report submission was successful" , Toast.LENGTH_LONG).show();

                }
            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


    }
    private void startNotification() {
        Date reportDate=null;
        String date=null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateSting = dateFormat.format(calendar.getTime());
        applicationDb = new DBHelper(this);
        try {
            currentDate=dateFormat.parse(dateSting);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("New Admin Deposit Submission Alert")
                        .setContentText(adminName+ ""+"just submitted a new Deposit Report @"+ Utils.formatDateTime(currentDate));

        Intent notificationIntent = new Intent(this, LoginDirectorActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirectorActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void sendReport(View view) {
        processReport(adminDepositID,adminUser,senderProfileID, adminName,officeBranch,dateOfReport,userProfile, selectedBank,adminBankDeposits);
    }
}