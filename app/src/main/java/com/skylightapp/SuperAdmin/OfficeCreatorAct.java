package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.gson.Gson;
import com.klinker.android.send_message.BroadcastUtils;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.AwardDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GrpProfileDAO;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.StockTransferDAO;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.Inventory.InStocksAct;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SMSAct;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

public class OfficeCreatorAct extends AppCompatActivity {
    SharedPreferences userPreferences;
    AppCompatEditText edtOfficeName;
    AppCompatEditText edtAddress;
    protected DatePickerDialog datePickerDialog;
    Random ran;
    SecureRandom random;
    Context context;
    String dateOfBirth;
    PreferenceManager preferenceManager;
    private Bundle bundle;
    String uPhoneNumber;
    private ProgressDialog progressDialog;
    int superID, tellerID;
    //private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    String superSurname;
    String superUserName;
    String selectedGender, selectedOffice, selectedState;
    Birthday birthday;
    String uSurname, uAddress,superFirstName;

    int virtualAccountNumber;
    int customerID;
    int profileID1,selectedOfficeIndex;

    AppCompatSpinner  spnBiz;
    DBHelper dbHelper;

    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
    String timeLineTime = mdformat.format(calendar.getTime());
    Profile superProfile;
    AppCompatButton btnSubmitNewOffice,btnAddNewEdt,btnRegisterNewOffice,btn_Office_update;
    AccountTypes accountTypeStr;
    ContentLoadingProgressBar progressBar;
    Gson gson, gson1;
    String json, json1, nIN;
    Profile userProfile;
    Long profileID2;
    int officeID;
    int superAdminID;
    SharedPreferences.Editor editor;
    Uri selectedImage;
    String userType, userRole;

    CircleImageView profilePix;
    AppCompatImageView imgGreetings;
    private UserSuperAdmin userSuperAdmin;
    private OfficeAdapter officeAdapter;
    Profile newUserProfileL;
    private UserSuperAdmin superAdmin;
    private OfficeBranch selectedBranch,officeBranch;
    private static final String PREF_NAME = "skylight";
    private SQLiteDatabase sqLiteDatabaseObj;

    private MessageDAO messageDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TimeLineClassDAO timeLineClassDAO;
    private GrpProfileDAO grpProfileDAO;
    private OfficeBranchDAO officeBranchDAO;
    private ArrayList<MarketBusiness> marketBizS;
    private ArrayList<OfficeBranch> bizOffices;

    private MarketBizArrayAdapter mBizAdapter;
    private int branchID,profileID,selectedBizIndex;
    private long bizID;
    private MarketBizDAO marketBizDao;
    private MarketBusiness marketBusiness;
    private SQLiteDatabase sqLiteDatabase;
    private String bizPhoneNo,officeName,officeAddress;
    private DBHelper applicationDb;
    private CardView cardSelect,cardSelMain;
    private  AppCompatSpinner spnOfficeB;



    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_office_creator);
        progressBar = findViewById(R.id.progressBar);
        spnBiz = findViewById(R.id.oelection_Biz);
        spnOfficeB = findViewById(R.id.officeSetion);
        cardSelect = findViewById(R.id.cSelectn);
        cardSelMain = findViewById(R.id.offSen);

        checkInternetConnection();
        applicationDb= new DBHelper(this);
        gson1 = new Gson();
        gson = new Gson();
        random= new SecureRandom();
        marketBusiness= new MarketBusiness();
        marketBizDao= new MarketBizDAO(this);
        dbHelper= new DBHelper(this);
        officeBranch=new OfficeBranch();
        marketBizS= new ArrayList<>();
        bizOffices= new ArrayList<>();
        officeBranchDAO= new OfficeBranchDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        messageDAO= new MessageDAO(this);
        btnRegisterNewOffice = findViewById(R.id.button_Create_Office);
        btnAddNewEdt = findViewById(R.id.btnAddNewEdt);

        edtOfficeName = findViewById(R.id.officehhName);
        edtAddress = findViewById(R.id.officeAddress2w);

        btnSubmitNewOffice = findViewById(R.id.submitNewOffice);
        btn_Office_update = findViewById(R.id.btn_Office_update);

        superProfile = new Profile();
        userSuperAdmin = new UserSuperAdmin();
        superAdmin = new UserSuperAdmin();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        superProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastSuperProfileUsed", "");
        userSuperAdmin = gson1.fromJson(json, UserSuperAdmin.class);
        ran = new Random();
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        imgGreetings = findViewById(R.id.GreetingsOffice);
        customerID = random.nextInt((int) (Math.random() * 1203) + 1101);
        if(superProfile !=null){
            userSuperAdmin= superProfile.getProfile_SuperAdmin();

            profileID=superProfile.getPID();
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                sqLiteDatabase = applicationDb.getReadableDatabase();
                try {
                    marketBizS=marketBizDao.getAllBusinessesForProfile(profileID);
                } catch (Exception e) {
                    System.out.println("Oops!");
                }


            }

        }
        mBizAdapter = new MarketBizArrayAdapter(OfficeCreatorAct.this,R.layout.item_market_biz, marketBizS);
        spnBiz.setAdapter(mBizAdapter);
        spnBiz.setSelection(0);
        selectedBizIndex = spnBiz.getSelectedItemPosition();

        try {
            marketBusiness = marketBizS.get(selectedBizIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(marketBusiness !=null){
            //bizOffices=marketBusiness.getOfficeBranches();
            bizID=marketBusiness.getBusinessID();
            bizPhoneNo=marketBusiness.getBizPhoneNo();

        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = applicationDb.getReadableDatabase();
            try {
                bizOffices=officeBranchDAO.getOfficesForBusiness(profileID);
            } catch (Exception e) {
                System.out.println("Oops!");
            }


        }
        if(userSuperAdmin !=null){
            superAdminID=userSuperAdmin.getSuperID();
        }


        officeAdapter = new OfficeAdapter(OfficeCreatorAct.this, R.layout.stock_row2,bizOffices);
        spnOfficeB.setAdapter(officeAdapter);
        spnOfficeB.setSelection(0);
        selectedOfficeIndex = spnOfficeB.getSelectedItemPosition();

        try {
            officeBranch = bizOffices.get(selectedOfficeIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(officeBranch !=null){
            bizID=officeBranch.getOfficeBranchID();
        }

        btnSubmitNewOffice.setOnClickListener(this::doOfficeReg);
        btnAddNewEdt.setOnClickListener(this::showAddNewOffice);
        btnRegisterNewOffice.setOnClickListener(this::addNewOB);
        btnRegisterNewOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardSelect.setVisibility(View.GONE);
                cardSelMain.setVisibility(View.GONE);
                btnAddNewEdt.setActivated(false);


            }
        });


        btnAddNewEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardSelect.setVisibility(View.VISIBLE);
                cardSelMain.setVisibility(View.VISIBLE);
                btn_Office_update.setActivated(false);

            }
        });

        Calendar calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());


        if (userSuperAdmin != null) {
            superSurname = userSuperAdmin.getSSurname();
            superFirstName = userSuperAdmin.getSFirstName();
            superID = userSuperAdmin.getSuperID();
            userRole = userSuperAdmin.getSAdminRole();
        }

        virtualAccountNumber = random.nextInt((int) (Math.random() * 102000) + 100876);
        officeID = random.nextInt((int) (Math.random() * 10319) + 1113);


        btnSubmitNewOffice.setOnClickListener(view -> {
            if(selectedOffice==null){
                try {
                    selectedOffice = edtOfficeName.getText().toString().trim();
                } catch (Exception e) {
                    System.out.println("Oops!");
                }

            }
            try {
                uAddress = edtAddress.getText().toString();
            } catch (Exception e) {
                System.out.println("Oops!");
                edtAddress.requestFocus();
            }

            startProfileActivity(selectedOffice,uAddress ,officeID,userSuperAdmin,virtualAccountNumber,currentDate,bizID,bizOffices);

        });
        btn_Office_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selectedOffice = edtOfficeName.getText().toString().trim();
                } catch (Exception e) {
                    System.out.println("Oops!");
                }
                try {
                    uAddress = edtAddress.getText().toString();
                } catch (Exception e) {
                    System.out.println("Oops!");
                    edtAddress.requestFocus();
                }
                if(TextUtils.isEmpty(uAddress)){
                    if(TextUtils.isEmpty(selectedOffice)){
                        edtAddress.setFocusable(true);
                        edtOfficeName.setFocusable(true);
                        return;

                    }
                } else {
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        sqLiteDatabase = applicationDb.getReadableDatabase();
                        try {
                            officeBranchDAO.updateOfficeBranch(bizID,selectedOffice,uAddress);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }
                }

            }
        });

    }
    public void startProfileActivity(String selectedOffice, String uAddress, int officeID, UserSuperAdmin userSuperAdmin, int virtualAccountNumber, String currentDate, long bizID, ArrayList<OfficeBranch> bizOffices) {
        ran = new Random();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        sqLiteDatabaseObj = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        accountTypeStr = null;
        dbHelper = new DBHelper(this);
        //superAdminArrayList = dbHelper.getAllCustomersManagers();
        if (userSuperAdmin != null) {
            superSurname = userSuperAdmin.getSSurname();
            superFirstName = userSuperAdmin.getSFirstName();
            superID = userSuperAdmin.getSuperID();
            superUserName = userSuperAdmin.getSuperUserName();
            userType = userSuperAdmin.getSAdminRole();
            userRole = userSuperAdmin.getSAdminRole();

        }
        String name =superSurname+","+ superFirstName;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String joinedDate = mdformat.format(calendar.getTime());
        AcctDAO acctDAO= new AcctDAO(this);
        accountTypeStr = AccountTypes.EWALLET;
        String interestRate = "0.0";

        double accountBalance = 0.00;
        Account account= new Account(virtualAccountNumber,selectedOffice,0.00, accountTypeStr);
        long dbId=0;


        for (int i = 0; i < bizOffices.size(); i++) {
            try {
                if (bizOffices.get(i).getOfficeBranchName().equalsIgnoreCase(selectedOffice)) {
                    Toast.makeText(OfficeCreatorAct.this, "A similar office Branch, already exist", Toast.LENGTH_LONG).show();
                    return;

                }else{
                    if (sqLiteDatabaseObj == null || !sqLiteDatabaseObj.isOpen()) {
                        sqLiteDatabaseObj = dbHelper.getWritableDatabase();

                        acctDAO.insertAccount(officeID, 0, "", selectedOffice, virtualAccountNumber, 0.00, accountTypeStr);
                        dbId=officeBranchDAO.insertOfficeBranch(officeID,superAdminID,bizID,selectedOffice,joinedDate,uAddress,name,"Approved");
                        Profile officeProfile= new Profile(profileID1,selectedOffice,"Branch");

                        if(dbId>0){
                            officeBranch =  new OfficeBranch(officeID,selectedOffice,"Approved");
                            if(officeBranch !=null){
                                officeBranch.setAccountNumber(virtualAccountNumber);
                                officeBranch.setAccountBalance(0.00);
                                officeBranch.setOfficeBranchID(officeID);
                                officeBranch.setAccount(account);
                                officeBranch.setProfile(officeProfile);
                            }
                            Toast.makeText(OfficeCreatorAct.this, "Office Creation ,successful", Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(OfficeCreatorAct.this, "Office Creation ,Failed", Toast.LENGTH_LONG).show();
                        }

                        Intent otpIntent = new Intent(OfficeCreatorAct.this, LoginDirAct.class);
                        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(otpIntent);


                    }

                }
            } catch (Exception e) {
                System.out.println("Oops! Something went wrong, and we would check");
            }


        }


        BroadcastUtils.sendExplicitBroadcast(this, new Intent(), "Awajima Branch Sign up action");

    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }
    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }

    public void showAddNewOffice(View view) {
    }
    public void addNewOB(View view) {
    }
    public void updateOffice(View view) {
    }


    public void doOfficeReg(View view) {
    }
}