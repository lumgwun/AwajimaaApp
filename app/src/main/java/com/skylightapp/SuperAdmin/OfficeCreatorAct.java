package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.klinker.android.send_message.BroadcastUtils;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    String superSurname;
    String superUserName;
    String selectedGender, selectedOffice, selectedState;
    Birthday birthday;
    String uSurname, uAddress,superFirstName;

    int virtualAccountNumber;
    int customerID;
    int profileID1;

    AppCompatSpinner spnOffice;
    DBHelper dbHelper;

    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
    String timeLineTime = mdformat.format(calendar.getTime());
    Profile managerProfile;
    AppCompatButton btnCreateNewOffice,btnAddNewEdt,btnRegisterNewOffice;
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
    ArrayList<OfficeBranch> officeBranchArrayList;
    CircleImageView profilePix;
    AppCompatImageView imgGreetings;
    private UserSuperAdmin userSuperAdmin;
    private OfficeAdapter officeAdapter;
    Profile newUserProfileL;
    private UserSuperAdmin superAdmin;
    private OfficeBranch selectedBranch,officeBranch;
    private static final String PREF_NAME = "skylight";
    private SQLiteDatabase sqLiteDatabaseObj;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_office_creator);
        progressBar = findViewById(R.id.progressBar);
        checkInternetConnection();
        gson1 = new Gson();
        gson = new Gson();
        officeBranch=new OfficeBranch();
        btnAddNewEdt = findViewById(R.id.btnAddNewEdt);
        edtOfficeName = findViewById(R.id.officeName);
        spnOffice = findViewById(R.id.officeSelection);
        edtAddress = findViewById(R.id.officeAddress);
        btnCreateNewOffice = findViewById(R.id.submitNewOffice);

        managerProfile = new Profile();
        userSuperAdmin = new UserSuperAdmin();
        superAdmin = new UserSuperAdmin();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastSuperProfileUsed", "");
        userSuperAdmin = gson1.fromJson(json, UserSuperAdmin.class);
        ran = new Random();
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        imgGreetings = findViewById(R.id.GreetingsOffice);
        customerID = random.nextInt((int) (Math.random() * 1203) + 1101);
        if(managerProfile !=null){
            userSuperAdmin=managerProfile.getProfile_SuperAdmin();

        }
        if(userSuperAdmin !=null){
            superAdminID=userSuperAdmin.getSuperID();
        }
        officeBranchArrayList = new ArrayList<>();

        btnCreateNewOffice.setOnClickListener(this::doOfficeReg);
        btnAddNewEdt.setOnClickListener(this::showAddNewOffice);

        btnAddNewEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtOfficeName.setVisibility(View.VISIBLE);
            }
        });
        Calendar calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());
        officeBranchArrayList = dbHelper.getAllBranchOffices();

        if (userSuperAdmin != null) {
            superSurname = userSuperAdmin.getSSurname();
            superFirstName = userSuperAdmin.getSFirstName();
            superID = userSuperAdmin.getSuperID();
            userRole = userSuperAdmin.getSAdminRole();
        }

        virtualAccountNumber = random.nextInt((int) (Math.random() * 102000) + 100876);
        officeID = random.nextInt((int) (Math.random() * 10319) + 1113);
        try {
            if(officeBranchArrayList.size()==0){
                spnOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedOffice = spnOffice.getSelectedItem().toString();
                        selectedOffice = (String) parent.getSelectedItem();
                        Toast.makeText(OfficeCreatorAct.this, "Office Branch Selected: " + selectedOffice, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


            }else {
                officeAdapter = new OfficeAdapter(OfficeCreatorAct.this, android.R.layout.simple_spinner_item, officeBranchArrayList);
                spnOffice.setAdapter(officeAdapter);
                spnOffice.setSelection(0);

                spnOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBranch = (OfficeBranch) parent.getSelectedItem();

                        //transferAccepter=selectedBranch;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                if(selectedBranch !=null){
                    selectedOffice=selectedBranch.getOfficeBranchName();
                }
            }
            if(selectedBranch !=null){
                selectedOffice=selectedBranch.getOfficeBranchName();
            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");

        }



        btnCreateNewOffice.setOnClickListener(view -> {
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

            startProfileActivity(selectedOffice,uAddress ,officeID,userSuperAdmin,virtualAccountNumber,currentDate);

        });

    }
    public void startProfileActivity(String selectedOffice, String uAddress, int officeID, UserSuperAdmin userSuperAdmin, int virtualAccountNumber, String currentDate) {
        ran = new Random();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        sqLiteDatabaseObj = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        virtualAccountNumber = random.nextInt((int) (Math.random() * 12304) + 10012);
        customerID = random.nextInt((int) (Math.random() * 1203) + 1101);
        accountTypeStr = null;
        dbHelper = new DBHelper(this);
        officeBranchArrayList = dbHelper.getAllBranchOffices();
        //superAdminArrayList = dbHelper.getAllCustomersManagers();
        if (userSuperAdmin != null) {
            superSurname = userSuperAdmin.getSSurname();
            superFirstName = userSuperAdmin.getSFirstName();
            superID = userSuperAdmin.getSuperID();
            superUserName = userSuperAdmin.getSuperUserName();
            userType = userSuperAdmin.getSAdminRole();
            userRole = userSuperAdmin.getSAdminRole();

        }

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String joinedDate = mdformat.format(calendar.getTime());


        for (int i = 0; i < officeBranchArrayList.size(); i++) {
            try {
                if (officeBranchArrayList.get(i).getOfficeBranchName().equalsIgnoreCase(selectedOffice)) {
                    Toast.makeText(OfficeCreatorAct.this, "A similar office Branch, already exist", Toast.LENGTH_LONG).show();
                    return;

                }else{
                    if (sqLiteDatabaseObj == null || !sqLiteDatabaseObj.isOpen()) {
                        sqLiteDatabaseObj = dbHelper.getWritableDatabase();

                        dbHelper.insertAccount(profileID1, customerID, "Skylight", selectedOffice, virtualAccountNumber, 0.00, accountTypeStr);
                        dbHelper.insertOfficeBranch(officeID,superAdminID,selectedOffice,joinedDate,uAddress,"Super Admin","Approved");
                        Toast.makeText(OfficeCreatorAct.this, "Data insertion ,successful", Toast.LENGTH_LONG).show();

                    }

                }
            } catch (Exception e) {
                System.out.println("Oops! Something went wrong, and we would check");
            }


        }


        BroadcastUtils.sendExplicitBroadcast(this, new Intent(), "skylight Account Sign up action");


        accountTypeStr = AccountTypes.EWALLET;
        String interestRate = "0.0";

        Random random = new Random();
        double accountBalance = 0.00;
        officeBranch =  new OfficeBranch(officeID,selectedOffice,"Approved");

        Account newAccount = new Account("Skylight", selectedOffice, virtualAccountNumber, accountBalance, accountTypeStr);
        Profile officeProfile= new Profile(profileID1,selectedOffice,"Branch");

        if(officeBranch !=null){
            officeBranch.setAccountNumber(virtualAccountNumber);
            officeBranch.setAccountBalance(0.00);
            officeBranch.setOfficeBranchID(officeID);
            officeBranch.setAccount(newAccount);
            officeBranch.setProfile(officeProfile);
        }
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

    public void doOfficeReg(View view) {
    }
}