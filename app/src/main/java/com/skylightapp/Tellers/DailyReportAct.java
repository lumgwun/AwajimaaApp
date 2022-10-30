package com.skylightapp.Tellers;

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
import com.skylightapp.Accountant.BranchMPayments;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DailyReportAct extends AppCompatActivity {
    private static final String TAG = DailyReportAct.class.getSimpleName();
    public final static int KEY_EXTRA_REPORT_ID = 122;
    Date currentDate;

    private DBHelper selector;
    private Profile userProfile;
    private CustomerManager customerManager;
    //private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle messageBundle;
    private  int sendeeProfileID,customerID,SharedPrefAdminID;
    private  String purpose;
    private Spinner spnPurpose ,spnOffice;
    private  String selectedPurpose,sendee;
    private Customer customer;
    DatePicker picker;
    private  Spinner spnNoOfClients,spnMyBiz;
    private AppCompatButton btnConfirmation;
    EditText edtAmounts;
    private double amountEntered;
    private  ArrayList<TellerReport> tellerReports;
    private int tellerID;
    private int noOfSavings;
    DBHelper applicationDb;
    int officeBranchID;
    int selectedNoIndex,SharedPrefProfileID;
    private static final String PREF_NAME = "skylight";
    SQLiteDatabase sqLiteDatabase;
    private ArrayList<OfficeBranch> bizOffices;
    private MarketBusiness marketBiz;
    private int selectedOfficeIndex,selectedBizIndex;
    private OfficeBranch officeB;
    private OfficeAdapter officeBranchAdapter;
    private ArrayList<MarketBusiness> marketBusinesses;
    private MarketBizArrayAdapter bizAdapter;
    String bizName,SharedPrefUserPassword, stringNoOfSavings, officeBranch,dateOfReport, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,adminName;
    Spinner.OnItemSelectedListener spnClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.getId() == spnNoOfClients.getId()) {
                selectedNoIndex = i;
                try {
                    stringNoOfSavings = (String) adapterView.getSelectedItem();
                    if(stringNoOfSavings !=null){
                        noOfSavings=Integer.parseInt(stringNoOfSavings);

                    }

                    try {
                        if(selectedNoIndex==0){
                            spnNoOfClients.setFocusable(true);
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }
                stringNoOfSavings = (String) adapterView.getSelectedItem();
                if (stringNoOfSavings != null) {
                    noOfSavings=Integer.parseInt(stringNoOfSavings);
                }

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_daily_report);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        customerManager=new CustomerManager();
        currentDate= new Date();
        tellerReports=new ArrayList<>();
        marketBusinesses= new ArrayList<>();
        bizOffices= new ArrayList<>();
        applicationDb = new DBHelper(this);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
        if(customerManager !=null){
            cmFirstName =customerManager.getTFirstName();
            cmLastName =customerManager.getTSurname();
            cmName=cmLastName+""+cmFirstName;
            tellerID=customerManager.getTID();
            marketBusinesses=customerManager.gettMarketBusinesses();
        }
        picker=(DatePicker)findViewById(R.id.dob_date_Report);
        spnNoOfClients = findViewById(R.id.spinnerNoOfClients);
        spnMyBiz = findViewById(R.id.spinnerBiz_My);

        spnNoOfClients.setOnItemSelectedListener(spnClickListener);

        bizAdapter = new MarketBizArrayAdapter(DailyReportAct.this,R.layout.item_market_biz, marketBusinesses);
        spnMyBiz.setAdapter(bizAdapter);
        spnMyBiz.setSelection(0);
        selectedBizIndex = spnMyBiz.getSelectedItemPosition();

        try {
            marketBiz = marketBusinesses.get(selectedBizIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        edtAmounts = findViewById(R.id.clientAmount);
        spnOffice = findViewById(R.id.spinnerOffice);
        spnOffice.setOnItemSelectedListener(spnClickListener);
        if(marketBiz !=null){
            bizOffices=marketBiz.getOfficeBranches();

        }

        officeBranchAdapter = new OfficeAdapter(DailyReportAct.this, bizOffices);
        spnOffice.setAdapter(officeBranchAdapter);
        spnOffice.setSelection(0);
        selectedOfficeIndex = spnOffice.getSelectedItemPosition();

        try {
            officeB = bizOffices.get(selectedOfficeIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(bizOffices.size()==0){
            if(userProfile !=null){
                officeB=userProfile.getProfileOfficeBranch();

            }
        }
        if(officeB !=null){
            officeBranchID=officeB.getOfficeBranchID();
            bizName=officeB.getOfficeBranchName();
        }

        btnConfirmation = findViewById(R.id.confirmReportSub);
        btnConfirmation.setOnClickListener(this::sendReport);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfReport);
            }
        });
        dateOfReport = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        btnConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processReport(customerManager, dateOfReport,SharedPrefProfileID,userProfile,tellerReports,noOfSavings,officeBranchID,bizName);

            }
        });
    }

    private void chooseDate(String dateOfReport) {
        dateOfReport = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

    }
    private void processReport(CustomerManager customerManager, String dateOfReport, int SharedPrefProfileID, Profile userProfile, ArrayList<TellerReport> tellerReports, int noOfSavings, int officeBranchID,String bizName){
        String reportDate=null;
        tellerReports = null;
        if(dateOfReport ==null){
            Calendar calendar = Calendar.getInstance();
            currentDate = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault());

            dateOfReport=dateFormat.format(currentDate);
            reportDate = dateOfReport;


        }

        if(customerManager !=null){
            cmFirstName =customerManager.getTFirstName();
            cmLastName =customerManager.getTSurname();
            cmName=cmLastName+""+cmFirstName;
        }
        try {
            amountEntered = Double.parseDouble(edtAmounts.getText().toString().trim());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }


        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timelineTittle="Teller Report Submission";
        String timelineTittle2="Your Report Submission";
        String timelineDetails=cmName+" just Submitted Report";

        String timelineDetails2=" You just Submitted Report @"+ Utils.formatDateTime(currentDate);
        Location location=null;
        //tellerReports=null;
        applicationDb = new DBHelper(this);
        TReportDAO tReportDAO= new TReportDAO(this);

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = applicationDb.getWritableDatabase();
            try {
                tellerReports=tReportDAO.getTellerReportsAll();
            } catch (Exception e) {
                System.out.println("Oops!");
            }


        }

        try {
            for (int i = 0; i < tellerReports.size(); i++) {
                String date = sdf.format(tellerReports.get(i).getTellerReportDate());
                if (tellerReports.get(i).getTrMarketer().equalsIgnoreCase(cmName)&& date.equalsIgnoreCase(dateOfReport)||tellerReports.get(i).getTr_Office_Branch().equalsIgnoreCase(officeBranch)) {
                    Toast.makeText(DailyReportAct.this, "There is a similar report" , Toast.LENGTH_LONG).show();
                    return;

                }else {
                    if (customerManager != null) {
                        userProfile.addPTimeLine(timelineTittle2,timelineDetails2);
                        userProfile.addPTellerReport(KEY_EXTRA_REPORT_ID,bizName,officeBranch,amountEntered, noOfSavings,dateOfReport);
                        customerManager.addTimeLine(timelineTittle2,timelineDetails2);
                        customerManager.addTellerReport(KEY_EXTRA_REPORT_ID,bizName,officeBranch,amountEntered, noOfSavings,dateOfReport);
                    }
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        sqLiteDatabase = applicationDb.getWritableDatabase();
                        TimeLineClassDAO timeLineClassDAO= new TimeLineClassDAO(this);
                        try {
                            tReportDAO.insertTellerReport1( officeBranchID,0,tellerID,reportDate,amountEntered, noOfSavings,0.00,0.00,0.00,officeBranch,cmName,"");
                            startNotification();
                            Toast.makeText(DailyReportAct.this, "Report submission was successful" , Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        sqLiteDatabase = applicationDb.getWritableDatabase();
                        TimeLineClassDAO timeLineClassDAO= new TimeLineClassDAO(this);
                        try {
                            timeLineClassDAO.insertTimeLine(timelineTittle,timelineDetails,dateOfReport,location);
                            Toast.makeText(DailyReportAct.this, "Report submission was successful" , Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }


                }
            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


    }
    private void startNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("New Teller Report Alert")
                        .setContentText("A Teller just submitted a new Report");

        Intent notificationIntent = new Intent(this, TellerHomeChoices.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(TellerHomeChoices.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void sendReport(View view) {
        processReport(customerManager, dateOfReport,SharedPrefProfileID, userProfile, tellerReports, noOfSavings, officeBranchID,bizName);
    }


}