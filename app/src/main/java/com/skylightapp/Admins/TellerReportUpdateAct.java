package com.skylightapp.Admins;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Accountant.BranchMPayments;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Classes.AdminUser;
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
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SMSAct;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;

public class TellerReportUpdateAct extends AppCompatActivity {
    private static final String TAG = TellerReportUpdateAct.class.getSimpleName();

    private Bundle updateBundle;
    private TellerReport tellerReport;
    private DBHelper applicationDb;
    private  int adminID,tellerReportID;
    private Profile userProfile,tellerProfile;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private AdminUser adminUser;
    DatePicker picker;
    Date currentDate;
    private Spinner spnNoOfClients,spnReportOffice;
    private Button btnConfirmUpdate;
    private double amountPaidOnline,amountEntered,amountExpected,balance;
    EditText edtUpdateAmounts,edxtamtPaid;
    private TextView txtName, txtUpdateID,txtUpdateReportDate,txtamtExt,txtBalance;
    int officeBranchID,updateCount;
    int selectedNoIndex, noOfCustomers, tellerID;
    private CustomerManager teller;
    private  ArrayList<TellerReport> tellerReports;
    private  Date today;
    Random ran;
    SecureRandom random;
    private int managerID;
    private long superAdminCode;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserPassword;
    String phone_number;
    String tellerString;
    String stringNoOfCusString;
    String officeBranch;
    String dateOfReport;
    String cmFirstName;
    String cmLastName;
    String cmName;
    String SharedPrefUserMachine;
    String phoneNo;
    String SharedPrefUserName;
    int SPAdminProfileID,selectedOfficeIndex;
    private TReportDAO tReportDAO;
    private TimeLineClassDAO timeLineClassDAO;
    String adminName, bizName;
    private ArrayList<MarketBusiness> marketBizS;
    private MarketBusiness marketBusiness;
    private Customer customer;
    private ArrayList<OfficeBranch> bizOffices;
    SQLiteDatabase sqLiteDatabase;
    private OfficeBranch officeB;
    private OfficeAdapter officeBranchAdapter;
    Gson gson2,gson3;
    String json2,json3;
    private long bizID;
    private MarketBusiness marketBiz;
    Spinner.OnItemSelectedListener spnClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.getId() == spnNoOfClients.getId()) {
                selectedNoIndex = i;
                try {
                    stringNoOfCusString = (String) adapterView.getSelectedItem();
                    noOfCustomers =Integer.parseInt(stringNoOfCusString);

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
                stringNoOfCusString = (String) adapterView.getSelectedItem();
                noOfCustomers =Integer.parseInt(stringNoOfCusString);

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private final ActivityResultLauncher<Intent> startCodeConfirmationForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            doReportUpdate(adminUser, dateOfReport, SPAdminProfileID,userProfile,tellerReport,officeBranchID, noOfCustomers,teller,managerID,tellerProfile,adminID,balance,superAdminCode, phone_number, bizID);
                            Toast.makeText(TellerReportUpdateAct.this, "Permission Code rhymed", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(TellerReportUpdateAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_report_update);
        applicationDb =new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson2= new Gson();
        userProfile=new Profile();
        currentDate= new Date();
        adminUser= new AdminUser();
        marketBusiness= new MarketBusiness();
        teller= new CustomerManager();
        customer= new Customer();
        tellerProfile= new Profile();
        bizOffices= new ArrayList<>();
        tellerReports=new ArrayList<>();
        marketBizS=new ArrayList<>();
        officeB= new OfficeBranch();
        marketBiz= new MarketBusiness();

        ran = new Random();
        random = new SecureRandom();
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SPAdminProfileID =userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        phone_number ="07038843102";
        json1 = userPreferences.getString("LastAdminProfileUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson2.fromJson(json2, MarketBusiness.class);

        if(marketBiz !=null){
            bizID=marketBiz.getBusinessID();
            bizOffices=marketBiz.getOfficeBranches();
        }

        updateBundle=getIntent().getExtras();
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd/", Locale.getDefault());

        try {
            dateOfReport=dateFormat.format(currentDate);
            today = dateFormat.parse(dateOfReport);


        } catch (ParseException ignored) {
        }
        marketBizS=adminUser.getAdminMarketBizArrayList();

        if(updateBundle !=null){
            tellerReport=updateBundle.getParcelable("TellerReport");
            teller=updateBundle.getParcelable("CustomerManager");
            customer=updateBundle.getParcelable("Customer");
            marketBusiness=updateBundle.getParcelable("MarketBusiness");


        }
        if(marketBusiness !=null){
            bizOffices=marketBusiness.getOfficeBranches();
        }
        if(teller !=null){
            managerID=teller.getTellerProfile().getPID();
            tellerProfile=teller.getTellerProfile();

        }
        if(tellerReport !=null){
            tellerReportID=tellerReport.getTellerReportID();
            tellerString=tellerReport.getTrManager();
            balance=tellerReport.getTr_Balance();

        }
        if(adminUser !=null){
            cmFirstName =adminUser.getAdminFirstName();
            cmLastName =adminUser.getAdminSurname();
            cmName=cmLastName+""+cmFirstName;
            adminID=adminUser.getAdminID();
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = applicationDb.getReadableDatabase();
            try {
                amountExpected=applicationDb.getTotalSavingsTodayForTeller(managerID,today);
            } catch (Exception e) {
                System.out.println("Oops!");
            }


        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = applicationDb.getReadableDatabase();
            try {
                noOfCustomers =applicationDb.getSavingsCusCountTodayForTeller(managerID,today);
            } catch (Exception e) {
                System.out.println("Oops!");
            }


        }


        btnConfirmUpdate = findViewById(R.id.confirmReportUpdate);
        txtName = findViewById(R.id.updateReportName);
        txtUpdateID = findViewById(R.id.updateID5);
        txtamtExt = findViewById(R.id.expAmount);
        txtBalance = findViewById(R.id.expBalance);

        txtamtExt.setText(String.format("Amount Expected: N%s", amountExpected));
        txtBalance.setText(MessageFormat.format("Report Balance: N{0}", balance));
        spnNoOfClients = findViewById(R.id.spinnerNoOfSAVings);
        spnReportOffice = findViewById(R.id.spinnerOffice4);
        edtUpdateAmounts = findViewById(R.id.updateAmount);
        edxtamtPaid = findViewById(R.id.onlineAmount);
        picker = findViewById(R.id.update_date_Report);

        officeBranchAdapter = new OfficeAdapter(TellerReportUpdateAct.this, bizOffices);
        spnReportOffice.setAdapter(officeBranchAdapter);
        spnReportOffice.setSelection(0);
        selectedOfficeIndex = spnReportOffice.getSelectedItemPosition();

        try {
            officeB = bizOffices.get(selectedOfficeIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(officeB !=null){
            officeBranch=officeB.getOfficeBranchName();
            officeBranchID=officeB.getOfficeBranchID();
        }

        spnNoOfClients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNoIndex = position;
                try {
                    stringNoOfCusString = (String) parent.getSelectedItem();
                    noOfCustomers =Integer.parseInt(stringNoOfCusString);

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
                stringNoOfCusString = (String) parent.getSelectedItem();
                noOfCustomers =Integer.parseInt(stringNoOfCusString);
                Toast.makeText(TellerReportUpdateAct.this, "No. of Clients: "  + officeBranch, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfReport);
            }
        });
        dateOfReport = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

        btnConfirmUpdate.setOnClickListener(this::updateReport);
        btnConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                superAdminCode = random.nextInt((int) (Math.random() * 12398) + 14281);
                updateCount=updateCount+1;
                if(updateCount==1) {
                    doReportUpdate(adminUser, dateOfReport, SPAdminProfileID,userProfile,tellerReport,officeBranchID, noOfCustomers,teller,managerID,tellerProfile,adminID,balance,superAdminCode,phone_number,bizID);

                    Toast.makeText(TellerReportUpdateAct.this,"Button clicked first time!", Toast.LENGTH_LONG).show();
                }
                else if(updateCount>1){
                    Toast.makeText(TellerReportUpdateAct.this,"You need the Super Admin code to Continue", Toast.LENGTH_LONG).show();
                    sendSMSMessage(phone_number);
                    Intent intent = new Intent(TellerReportUpdateAct.this,TellerReportUpdateConf.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Code", String.valueOf(superAdminCode));
                    intent.putExtras(bundle);
                    //startActivity(intent);
                    startCodeConfirmationForResult.launch(new Intent(intent));
                }

            }
        });




        txtUpdateReportDate = findViewById(R.id.UpdateReportDate);
        txtUpdateReportDate.setOnClickListener(this::showDatePicker);
        txtUpdateReportDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                picker.setVisibility(View.GONE);
                return false;
            }
        });



    }
    protected void sendSMSMessage(String phone_number) {
        Bundle smsBundle = new Bundle();
        superAdminCode = random.nextInt((int) (Math.random() * 12398) + 14281);
        String reportCode="Teller Report Update Code is:"+superAdminCode;
        this.phone_number ="07038843102";
        smsBundle.putString(PROFILE_PHONE, phone_number);
        smsBundle.putString("USER_PHONE", phone_number);
        smsBundle.putString("smsMessage", reportCode);
        smsBundle.putString("from", "");
        smsBundle.putString("to", phone_number);
        Intent itemPurchaseIntent = new Intent(TellerReportUpdateAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //System.out.println(message2.getSid());;

    }

    private void chooseDate(String dateOfReport) {
        dateOfReport = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

    }

    private void doReportUpdate(AdminUser adminUser, String dateOfReport, int sharedPrefProfileID, Profile userProfile, TellerReport tellerReport, int officeBranchID, int noOfSavings, CustomerManager teller, int managerID, Profile tellerProfile, int adminID, double balance, long superAdminCode, String phone_number, long bizID) {

        if(teller !=null){
            tellerID=teller.getTID();

        }

        superAdminCode = random.nextInt((int) (Math.random() * 12398) + 14281);
        try {
            tellerReport.setTr_SuperCode(superAdminCode);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        if(adminUser !=null){
            adminName=adminUser.getAdminSurname()+""+adminUser.getAdminFirstName();
        }
        try {
            amountEntered = Double.parseDouble(edtUpdateAmounts.getText().toString().trim());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            amountPaidOnline = Double.parseDouble(edxtamtPaid.getText().toString().trim());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        Date reportDate=null;
        tellerReports = null;
        if(dateOfReport ==null){
            Calendar calendar = Calendar.getInstance();
            currentDate = calendar.getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


            try {
                dateOfReport=dateFormat.format(currentDate);
                reportDate = dateFormat.parse(dateOfReport);


            } catch (ParseException ignored) {
            }

        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timelineTittle="Teller Report Update alert";
        String timelineTittle2="Your Report update alert";
        String timelineTittle3="Your action on a Report update alert";
        String timelineDetails=cmName+" just updated a Report";
        String timelineDetailAdmin=" just updatted a Tellers' Report @"+ Utils.formatDateTime(currentDate);
        String timelineDetailsTeller=" Admin  just updatted your Report @"+ Utils.formatDateTime(currentDate);

        String timelineDetails2=" You just Submitted Report @"+ Utils.formatDateTime(currentDate);
        Location location=null;
        //tellerReports=null;
        applicationDb = new DBHelper(this);
        tReportDAO= new TReportDAO(this);
        tellerReports=tReportDAO.getTellerReportsAll();
       if(tellerReport !=null){
           bizName=tellerReport.getTr_BizName();
           tellerReportID=tellerReport.getTellerReportID();
           tellerProfile.addPTimeLine(timelineTittle2,timelineDetailsTeller);



           //tellerProfile.uPdatePTellerReport(tellerReportID,bizName,officeBranch,amountEntered, noOfCustomers,dateOfReport);

       }

        String status=null;

        if(amountEntered==amountExpected){
            status="Completed";

        }else {
            status="InCompleted";

        }
        try {
            if (adminUser != null) {
                adminUser.addTimeLine(timelineTittle3,timelineDetailAdmin);
                tReportDAO= new TReportDAO(this);
                adminUser.addTellerReport(tellerReportID,bizID,officeBranch,amountEntered,amountExpected, noOfCustomers,dateOfReport,status);
            }
            timeLineClassDAO= new TimeLineClassDAO(this);
            tReportDAO.updateTellerReport(tellerReportID,tellerID,adminName,noOfCustomers,amountExpected,amountEntered,dateOfReport,status);
            timeLineClassDAO.insertTimeLine(timelineTittle,timelineDetails,dateOfReport,location);
            Toast.makeText(TellerReportUpdateAct.this, "Report Update was successful" , Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

    }

    public void showDatePicker(View view) {
        picker.setVisibility(View.VISIBLE);
    }

    public void updateReport(View view) {
    }
}