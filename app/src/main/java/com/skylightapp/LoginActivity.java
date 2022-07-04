package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Accountant.AcctantBackOffice;
import com.skylightapp.Admins.AdminDrawerActivity;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.LoginPresenter;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.SuperAdmin.SuperAdminOffice;
import com.skylightapp.Tellers.TellerDrawerAct;
import com.skylightapp.Tellers.TellerHomeChoices;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PASSWORD;
import static com.skylightapp.Classes.Profile.PICTURE_URI;
import static com.skylightapp.Classes.Profile.PROFILE_EMAIL;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;
import static com.skylightapp.Classes.Profile.PROFILE_ADDRESS;
import static com.skylightapp.Classes.Profile.PROFILE_DATE_JOINED;
import static com.skylightapp.Classes.Profile.PROFILE_DOB;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_GENDER;
import static com.skylightapp.Classes.Profile.PROFILE_NEXT_OF_KIN;
import static com.skylightapp.Classes.Profile.PROFILE_OFFICE;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_ROLE;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;
import static com.skylightapp.Database.DBHelper.DB_PATH;

@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_ID_EXTRA_KEY = "LoginActivity.LOGIN_ID_EXTRA_KEY";
    private Bundle bundle;
    private String username;
    private String password;
    private EditText edtUsername;
    private EditText edtPassword;
    private AppCompatButton btn_loginUserName;
    AppCompatTextView ic_admin_user;
    AppCompatTextView txt_account_msg;
    private AppCompatCheckBox chkRememberCred;
    private AppCompatButton btnCreateAccount,  btn_forgot_account_p, emailBtnLoginEmail,btn_forgot_account_Email;
    private Profile lastProfileUsed;
    private Customer lastCustomerProfileUsed;
    Customer customer1 ;
    AccountTypes accountTypeStr;
    SQLiteDatabase sqLiteDatabase;
    private Gson gson;
    private String json;
    DBHelper dbHelper;
    private SharedPreferences userPreferences;
    Customer skyLightCustomer;

    Account account;
    private String userID;
    private int customerID1,profileID2,accountNumber, profileID;
    public static SharedPreferences savedInfo;
    public static SharedPreferences.Editor savedInfoWriter;
    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");
    ArrayList<Profile> profiles;
    ArrayList<Customer> customers;
    RadioGroup userTypes, radioGroup1;
    AppCompatRadioButton checkBoxCustomer;
    private FirebaseAuth mFirebaseAuth;
    public static final int RC_SIGN_IN = 1;
    private ProgressBar loadingUserName, progressBar;
    AppCompatButton btn_email_loginUp, loginBtnWithUserName,  btn_loginEmail, signUpUserInstead;
    String email;
    ScrollView userScroll, emailScroll;
    AppCompatEditText email_edit, emailPassword,edtEmailAddress,edtEmailPassword;
    AppCompatTextView txt_account_Email, poweredByLS;
    AppCompatCheckBox chk_remember_Me;
    AppCompatRadioButton checkBoxteller,rbResponseTeam,checkBoxLogistice,checkBoxArtisian,checkBoxRealtor;
    RadioGroup radioGroup ,radioGroup2;
    FloatingActionButton FAb_Help;
    private Uri picture;

    String userName ,officeBranch,gender,state,address,dob,dateJoined;
    String sharedPrefUserName;
    String sharedPrefUserPassword;
    String sharedPrefCusID;
    String sharedPrefUserMachine;
    int sharedPrefProfileID;
    String sharedPrefSurName;
    String sharedPrefFirstName;
    String sharedPrefEmail;
    String sharedPrefPhone;
    String SharedPrefAddress;
    String SharedPrefDOB;
    String sharedPrefRole;
    String SharedPrefGender;
    String SharedPrefJoinedDate;
    String SharedPrefOffice;
    String SharedPrefState;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;
    FirebaseUser user;

    //private CallbackManager mCallbackManager;
    private String profilePhotoUrlLarge;
    private LoginPresenter presenter;
    Random random;
    double accountBalance; String accountName;
    LinearLayoutCompat  layout_UserName,layout_Email;
    FloatingActionButton fab;
    String userProfileType;
    private StandingOrderAcct standingOrderAcct;
    private  Profile userProfile;

    private static final String TAG = "EmailPassword";
    private static final String PREF_NAME = "skylight";
    String regEx =
            "^(([w-]+.)+[w-]+|([a-zA-Z]{1}|[w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[w-]+.)+[a-zA-Z]{2,4})$";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Access");
        random= new Random();
        SQLiteDataBaseBuild();
        standingOrderAcct = new StandingOrderAcct();
        customer1 = new Customer();
        skyLightCustomer= new Customer();
        gson = new Gson();
        userProfile= new Profile();
        profileID2 = random.nextInt((int) (Math.random() * 109) + 1119);
        /*userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            sharedPrefUserMachine=userPreferences.getString("Machine","");
            sharedPrefSurName=userPreferences.getString("USER_SURNAME","");
            sharedPrefFirstName=userPreferences.getString("USER_FIRSTNAME","");
            sharedPrefRole=userPreferences.getString("USER_ROLE","");
            sharedPrefProfileID=userPreferences.getInt("PROFILE_ID",0);
        }*/

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        profiles=new ArrayList<Profile>();
        customers=new ArrayList<Customer>();
        account= new Account();
        accountTypeStr = null;

        layout_UserName = findViewById(R.id.pswdUserName);
        btn_loginUserName = findViewById(R.id.user_name_login);

        layout_Email = findViewById(R.id.emailAddressLayout);
        btn_loginEmail = findViewById(R.id.email_login);
        btn_loginUserName.setOnClickListener(this::loginWithUserName);
        btn_loginEmail.setOnClickListener(this::useEmailForLogin);

        loginBtnWithUserName = findViewById(R.id.btn_login6);
        btn_loginUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_UserName.setVisibility(View.VISIBLE);
                layout_Email.setVisibility(View.GONE);

            }
        });
        btn_loginUserName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                    layout_UserName.setVisibility(View.GONE);
                return false;
            }
        });

        btn_loginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_Email.setVisibility(View.VISIBLE);
                layout_UserName.setVisibility(View.GONE);

            }
        });
        btn_loginEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layout_Email.setVisibility(View.GONE);
                return false;
            }
        });


        progressBar = findViewById(R.id.idPBLoading);

        radioGroup = findViewById(R.id.userChoice_6);
        checkBoxCustomer = findViewById(R.id.customer_6);

        emailBtnLoginEmail = findViewById(R.id.btn_WithEmail);
        edtEmailAddress = findViewById(R.id.userEmail);
        edtEmailPassword = findViewById(R.id.emailPassword);


        checkBoxteller = findViewById(R.id.teller_6);
        edtUsername = findViewById(R.id.edt_username5);
        edtPassword = findViewById(R.id.edt_password6);

        emailBtnLoginEmail.setOnClickListener(this::loginUserEmail);
        /*emailBtnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetOn();
                //email = Objects.requireNonNull(edtEmailAddress.getText()).toString().trim();
                 password = Objects.requireNonNull(edtEmailPassword.getText()).toString().trim();
                validateForm();
                //signIn(email, password);
                loginFireBase();
            }
        });*/

        chk_remember_Me = findViewById(R.id.chk_remember_6);
        btn_forgot_account_p = findViewById(R.id.btn_forgot_account_p6);
        txt_account_msg = findViewById(R.id.txt_account_msg);
        btnCreateAccount = findViewById(R.id.btn_create_account6);

        btn_forgot_account_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPasswordActivity();
            }
        });
        loginBtnWithUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetOn();
                validateForm();
                loginWithUserNameDbase();


            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();

            }
        });


    }
    public void SQLiteDataBaseBuild(){
        String myPath = DB_PATH + DATABASE_NAME;
        //sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((AppCompatRadioButton) view).isChecked();
        userProfileType="";
        switch(view.getId()) {
            case R.id.teller_6:
                if(checked)
                    userProfileType = "Teller";
                break;
            case R.id.customer_6:
                if(checked)
                    userProfileType = "Customer";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        Toast.makeText(this, userProfileType, Toast.LENGTH_SHORT).show();
    }




    private void updateUI(FirebaseUser user) {
        if (user != null) {
            gson = new Gson();
            userProfile= new Profile();
            customer1 = new Customer();
            skyLightCustomer= new Customer();
            //profileID2 = (long) random.nextInt((int) (Math.random() * 909) + 1119);
            userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            dbHelper = new DBHelper(LoginActivity.this);

            //  findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            //  findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            //  findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            // sign_up_textView.setText("sign out");
            //Toast.makeText(getApplicationContext(), "sign out", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("processing");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = edtEmailAddress.getText().toString();
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {

            edtEmailAddress.setError("Invalid Email");
        }

        if (TextUtils.isEmpty(email)) {
            edtEmailAddress.setError("Required.");

            valid = false;
        }

        String password = edtEmailPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            edtEmailPassword.setError("Required.");
            valid = false;
        } else {
            edtEmailPassword.setError(null);
        }

        return valid;
    }


    public final boolean isInternetOn() {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            // if connected with internet
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Make sure that your internet connection is on?")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Internet Connection Error");
            alert.show();
            return false;
        }
        return false;
    }

    private void loginWithUserNameDbase() {
        chk_remember_Me = findViewById(R.id.chk_remember_6);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("processing Login");
        progressDialog.setCancelable(true);
        progressDialog.show();
        dbHelper= new DBHelper(LoginActivity.this);
        boolean isAdmin = true;
        skyLightCustomer= new Customer();
        gson = new Gson();
        userProfile= new Profile();
        customer1= new Customer();
        profiles=new ArrayList<Profile>();
        customers=new ArrayList<Customer>();
        profiles = dbHelper.getAllProfiles();
        customers = dbHelper.getAllCustomers11();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();

        userName = Objects.requireNonNull(edtUsername.getText()).toString().trim();
        password = Objects.requireNonNull(edtPassword.getText()).toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "empty username", Toast.LENGTH_LONG).show();
            edtUsername.setError("Please Enter Your User Name");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "empty username", Toast.LENGTH_LONG).show();
            edtPassword.setError("Please Enter the password");
            return;
        }
        progressBar.setVisibility(View.GONE);
        sharedPrefRole=dbHelper.getProfileRoleByUserNameAndPassword(userName,password);

        profileID=dbHelper.getProfileIDByUserNameAndPassword(userName,password);
        sharedPrefUserMachine= dbHelper.getProfileRoleByUserNameAndPassword(userName,password);
        userProfile=dbHelper.getProfileFromUserNameAndPassword(userName,password);

        if(userProfile !=null){
            profileID=userProfile.getPID();
            customer1=userProfile.getProfileCus();
            sharedPrefPhone=userProfile.getProfilePhoneNumber();
            sharedPrefEmail=userProfile.getProfileEmail();
            sharedPrefRole=userProfile.getProfileRole();
            sharedPrefFirstName=userProfile.getProfileFirstName();
            sharedPrefSurName=userProfile.getProfileLastName();
            officeBranch=userProfile.getProfileOffice();
            picture=userProfile.getProfilePicture();
            gender=userProfile.getProfileGender();
            state=userProfile.getProfileState();
            address=userProfile.getProfileAddress();
            dob=userProfile.getProfileDob();
            dateJoined=userProfile.getProfileDateJoined();

        }else {

            profileID = userPreferences.getInt("PROFILE_ID", 0);
            userName = userPreferences.getString("PROFILE_USERNAME", "");
            officeBranch = userPreferences.getString("PROFILE_OFFICE", "");
            state = userPreferences.getString("PROFILE_STATE", "");
            sharedPrefRole = userPreferences.getString("PROFILE_ROLE", "");
            password = userPreferences.getString("PROFILE_PASSWORD", "");
            dateJoined = userPreferences.getString("PROFILE_DATE_JOINED", "");
            sharedPrefSurName = userPreferences.getString("PROFILE_SURNAME", "");
            email = userPreferences.getString("PROFILE_EMAIL", "");
            sharedPrefPhone = userPreferences.getString("PROFILE_PHONE", "");
            sharedPrefFirstName = userPreferences.getString("PROFILE_FIRSTNAME", "");
            dob = userPreferences.getString("PROFILE_DOB", "");
            customerID1 = userPreferences.getInt("CUSTOMER_ID", 0);
            gender = userPreferences.getString("PROFILE_GENDER", "");
            address = userPreferences.getString("PROFILE_ADDRESS", "");
            picture = Uri.parse(userPreferences.getString("PICTURE_URI", ""));
            profileID = userPreferences.getInt("PROFILE_ID", 0);
            userName = userPreferences.getString("USER_NAME", "");
            officeBranch = userPreferences.getString("USER_OFFICE", "");
            state = userPreferences.getString("USER_STATE", "");
            sharedPrefRole = userPreferences.getString("USER_ROLE", "");

            dateJoined = userPreferences.getString("USER_DATE_JOINED", "");
            password = userPreferences.getString("USER_PASSWORD", "");
            sharedPrefSurName = userPreferences.getString("USER_SURNAME", "");
            email = userPreferences.getString("EMAIL_ADDRESS", "");
            sharedPrefPhone = userPreferences.getString("USER_PHONE", "");
            sharedPrefFirstName = userPreferences.getString("USER_FIRSTNAME", "");
            dob = userPreferences.getString("USER_DOB", "");
            customerID1 = userPreferences.getInt("CUSTOMER_ID", 0);
            gender = userPreferences.getString("USER_GENDER", "");
            address = userPreferences.getString("USER_ADDRESS", "");
            picture = Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        }
        if(customer1 !=null){
            customerID1=customer1.getCusUID();
        }
        PrefManager prefManager= new PrefManager(this);
        prefManager.saveLoginDetails(userName,password);
        editor.putString("USERNAME", userName);
        editor.putString("USER_PASSWORD", password);
        editor.putString("USER_SURNAME", sharedPrefSurName);
        editor.putString("USER_FIRSTNAME", sharedPrefFirstName);
        editor.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
        editor.putString("machine", sharedPrefRole);
        editor.putString("PICTURE_URI", "SuperAdmin");
        editor.putString("Role", sharedPrefRole);
        editor.putInt("PROFILE_ID", profileID);
        editor.putString("PROFILE_USERNAME", userName);
        editor.putString("PROFILE_PASSWORD", password);
        editor.putString("PROFILE_OFFICE", officeBranch);
        editor.putString("PROFILE_STATE", state);
        editor.putString("PROFILE_ROLE", sharedPrefRole);
        editor.putString("PROFILE_DATE_JOINED", dateJoined);
        editor.putString("PROFILE_EMAIL", sharedPrefEmail);
        editor.putString("PROFILE_PHONE", sharedPrefPhone);
        editor.putString("PROFILE_FIRSTNAME", sharedPrefFirstName);
        editor.putString("PROFILE_SURNAME", sharedPrefSurName);
        editor.putString("PROFILE_DOB", dob);
        editor.putInt("CUSTOMER_ID", customerID1);
        editor.putString("PROFILE_GENDER", gender);
        editor.putString("PROFILE_ADDRESS", address);
        editor.putInt(PROFILE_ID, profileID);
        editor.putString(PROFILE_USERNAME, userName);
        editor.putString(PROFILE_PASSWORD, password);
        editor.putString(PROFILE_OFFICE, officeBranch);
        editor.putString(PROFILE_STATE, state);
        editor.putString(PROFILE_ROLE, sharedPrefRole);
        editor.putString(PROFILE_DATE_JOINED, dateJoined);
        editor.putString(PROFILE_EMAIL, sharedPrefEmail);
        editor.putString(PROFILE_PHONE, sharedPrefPhone);
        editor.putString(PROFILE_FIRSTNAME, sharedPrefFirstName);
        editor.putString(PROFILE_SURNAME, sharedPrefSurName);
        editor.putString(PROFILE_DOB, dob);
        editor.putInt(CUSTOMER_ID, customerID1);
        editor.putString(PROFILE_GENDER, gender);
        editor.putString(PROFILE_ADDRESS, address);
        json = gson.toJson(lastProfileUsed);
        editor.putString("LastProfileUsed", json);
        editor.putBoolean("rememberMe", chkRememberCred.isChecked());
        editor.apply();


        boolean usernameTaken = false;
        boolean match = false;


        if(sharedPrefUserMachine.equalsIgnoreCase("Teller")){
            Intent tellerIntent = new Intent(this, TellerHomeChoices.class);
            tellerIntent.putExtra("PROFILE_ID", profileID);
            tellerIntent.putExtra("PROFILE_USERNAME", userName);
            tellerIntent.putExtra("PROFILE_PASSWORD", password);
            tellerIntent.putExtra("PROFILE_OFFICE", officeBranch);
            tellerIntent.putExtra("PROFILE_STATE", state);
            tellerIntent.putExtra("PROFILE_ROLE", sharedPrefRole);
            tellerIntent.putExtra("PROFILE_EMAIL", email);
            tellerIntent.putExtra("PROFILE_PHONE", sharedPrefPhone);
            tellerIntent.putExtra("PROFILE_FIRSTNAME", sharedPrefPhone);
            tellerIntent.putExtra("PROFILE_SURNAME", sharedPrefSurName);
            tellerIntent.putExtra("PROFILE_DOB", dob);
            tellerIntent.putExtra("PROFILE_DATE_JOINED", dateJoined);
            tellerIntent.putExtra("CUSTOMER_ID", customerID1);
            tellerIntent.putExtra("PROFILE_GENDER", gender);
            tellerIntent.putExtra("PROFILE_ADDRESS", address);
            tellerIntent.putExtra("PICTURE_URI", picture);
            tellerIntent.putExtra(PROFILE_ID, profileID);
            tellerIntent.putExtra(PROFILE_USERNAME, userName);
            tellerIntent.putExtra(PROFILE_PASSWORD, password);
            tellerIntent.putExtra(PROFILE_OFFICE, officeBranch);
            tellerIntent.putExtra(PROFILE_STATE, state);
            tellerIntent.putExtra(PROFILE_ROLE, sharedPrefRole);
            tellerIntent.putExtra(PROFILE_DATE_JOINED, dateJoined);
            tellerIntent.putExtra(PROFILE_PHONE, sharedPrefPhone);
            tellerIntent.putExtra(PROFILE_FIRSTNAME, sharedPrefFirstName);
            tellerIntent.putExtra(PROFILE_SURNAME, sharedPrefSurName);
            tellerIntent.putExtra(PROFILE_DOB, dob);
            tellerIntent.putExtra(CUSTOMER_ID, customerID1);
            tellerIntent.putExtra(PROFILE_GENDER, gender);
            tellerIntent.putExtra(PROFILE_ADDRESS, address);
            tellerIntent.putExtra(PICTURE_URI, picture);
            tellerIntent.putExtra("USER_NAME", userName);
            tellerIntent.putExtra("USER_PASSWORD", password);
            tellerIntent.putExtra("USER_OFFICE", officeBranch);
            tellerIntent.putExtra("USER_STATE", state);
            tellerIntent.putExtra("USER_ROLE", sharedPrefRole);
            tellerIntent.putExtra("USER_DATE_JOINED", dateJoined);
            tellerIntent.putExtra("EMAIL_ADDRESS", email);
            tellerIntent.putExtra("USER_PHONE", sharedPrefPhone);
            tellerIntent.putExtra("USER_FIRSTNAME", sharedPrefFirstName);
            tellerIntent.putExtra("USER_SURNAME", sharedPrefSurName);
            tellerIntent.putExtra("USER_DOB", dob);
            tellerIntent.putExtra("USER_DATE_JOINED", dateJoined);
            tellerIntent.putExtra("CUSTOMER_ID", customerID1);
            tellerIntent.putExtra("USER_GENDER", gender);
            tellerIntent.putExtra("USER_ADDRESS", address);
            tellerIntent.putExtra("PICTURE_URI", picture);
            startActivity(tellerIntent);

        }
        if(sharedPrefUserMachine.equalsIgnoreCase("Accountant")){
            Intent accountantIntent = new Intent(this, AcctantBackOffice.class);
            accountantIntent.putExtra("PROFILE_ID", profileID);
            accountantIntent.putExtra("PROFILE_USERNAME", userName);
            accountantIntent.putExtra("PROFILE_PASSWORD", password);
            accountantIntent.putExtra("PROFILE_OFFICE", officeBranch);
            accountantIntent.putExtra("PROFILE_STATE", state);
            accountantIntent.putExtra("PROFILE_ROLE", sharedPrefRole);
            accountantIntent.putExtra("PROFILE_EMAIL", email);
            accountantIntent.putExtra("PROFILE_PHONE", sharedPrefPhone);
            accountantIntent.putExtra("PROFILE_FIRSTNAME", sharedPrefPhone);
            accountantIntent.putExtra("PROFILE_SURNAME", sharedPrefSurName);
            accountantIntent.putExtra("PROFILE_DOB", dob);
            accountantIntent.putExtra("PROFILE_DATE_JOINED", dateJoined);
            accountantIntent.putExtra("CUSTOMER_ID", customerID1);
            accountantIntent.putExtra("PROFILE_GENDER", gender);
            accountantIntent.putExtra("PROFILE_ADDRESS", address);
            accountantIntent.putExtra("PICTURE_URI", picture);
            accountantIntent.putExtra(PROFILE_ID, profileID);
            accountantIntent.putExtra(PROFILE_USERNAME, userName);
            accountantIntent.putExtra(PROFILE_PASSWORD, password);
            accountantIntent.putExtra(PROFILE_OFFICE, officeBranch);
            accountantIntent.putExtra(PROFILE_STATE, state);
            accountantIntent.putExtra(PROFILE_ROLE, sharedPrefRole);
            accountantIntent.putExtra(PROFILE_DATE_JOINED, dateJoined);
            accountantIntent.putExtra(PROFILE_PHONE, sharedPrefPhone);
            accountantIntent.putExtra(PROFILE_FIRSTNAME, sharedPrefFirstName);
            accountantIntent.putExtra(PROFILE_SURNAME, sharedPrefSurName);
            accountantIntent.putExtra(PROFILE_DOB, dob);
            accountantIntent.putExtra(CUSTOMER_ID, customerID1);
            accountantIntent.putExtra(PROFILE_GENDER, gender);
            accountantIntent.putExtra(PROFILE_ADDRESS, address);
            accountantIntent.putExtra(PICTURE_URI, picture);
            accountantIntent.putExtra("USER_NAME", userName);
            accountantIntent.putExtra("USER_PASSWORD", password);
            accountantIntent.putExtra("USER_OFFICE", officeBranch);
            accountantIntent.putExtra("USER_STATE", state);
            accountantIntent.putExtra("USER_ROLE", sharedPrefRole);
            accountantIntent.putExtra("USER_DATE_JOINED", dateJoined);
            accountantIntent.putExtra("EMAIL_ADDRESS", email);
            accountantIntent.putExtra("USER_PHONE", sharedPrefPhone);
            accountantIntent.putExtra("USER_FIRSTNAME", sharedPrefFirstName);
            accountantIntent.putExtra("USER_SURNAME", sharedPrefSurName);
            accountantIntent.putExtra("USER_DOB", dob);
            accountantIntent.putExtra("USER_DATE_JOINED", dateJoined);
            accountantIntent.putExtra("CUSTOMER_ID", customerID1);
            accountantIntent.putExtra("USER_GENDER", gender);
            accountantIntent.putExtra("USER_ADDRESS", address);
            accountantIntent.putExtra("PICTURE_URI", picture);
            startActivity(accountantIntent);

        }
        if(sharedPrefUserMachine.equalsIgnoreCase("SuperAdmin")){
            Intent superAdminIntent = new Intent(this, SuperAdminOffice.class);
            superAdminIntent.putExtra("PROFILE_ID", profileID);
            superAdminIntent.putExtra("PROFILE_USERNAME", userName);
            superAdminIntent.putExtra("PROFILE_PASSWORD", password);
            superAdminIntent.putExtra("PROFILE_OFFICE", officeBranch);
            superAdminIntent.putExtra("PROFILE_STATE", state);
            superAdminIntent.putExtra("PROFILE_ROLE", sharedPrefRole);
            superAdminIntent.putExtra("PROFILE_EMAIL", email);
            superAdminIntent.putExtra("PROFILE_PHONE", sharedPrefPhone);
            superAdminIntent.putExtra("PROFILE_FIRSTNAME", sharedPrefPhone);
            superAdminIntent.putExtra("PROFILE_SURNAME", sharedPrefSurName);
            superAdminIntent.putExtra("PROFILE_DOB", dob);
            superAdminIntent.putExtra("PROFILE_DATE_JOINED", dateJoined);
            superAdminIntent.putExtra("CUSTOMER_ID", customerID1);
            superAdminIntent.putExtra("PROFILE_GENDER", gender);
            superAdminIntent.putExtra("PROFILE_ADDRESS", address);
            superAdminIntent.putExtra("PICTURE_URI", picture);
            superAdminIntent.putExtra(PROFILE_ID, profileID);
            superAdminIntent.putExtra(PROFILE_USERNAME, userName);
            superAdminIntent.putExtra(PROFILE_PASSWORD, password);
            superAdminIntent.putExtra(PROFILE_OFFICE, officeBranch);
            superAdminIntent.putExtra(PROFILE_STATE, state);
            superAdminIntent.putExtra(PROFILE_ROLE, sharedPrefRole);
            superAdminIntent.putExtra(PROFILE_DATE_JOINED, dateJoined);
            superAdminIntent.putExtra(PROFILE_PHONE, sharedPrefPhone);
            superAdminIntent.putExtra(PROFILE_FIRSTNAME, sharedPrefFirstName);
            superAdminIntent.putExtra(PROFILE_SURNAME, sharedPrefSurName);
            superAdminIntent.putExtra(PROFILE_DOB, dob);
            superAdminIntent.putExtra(CUSTOMER_ID, customerID1);
            superAdminIntent.putExtra(PROFILE_GENDER, gender);
            superAdminIntent.putExtra(PROFILE_ADDRESS, address);
            superAdminIntent.putExtra(PICTURE_URI, picture);
            superAdminIntent.putExtra("USER_NAME", userName);
            superAdminIntent.putExtra("USER_PASSWORD", password);
            superAdminIntent.putExtra("USER_OFFICE", officeBranch);
            superAdminIntent.putExtra("USER_STATE", state);
            superAdminIntent.putExtra("USER_ROLE", sharedPrefRole);
            superAdminIntent.putExtra("USER_DATE_JOINED", dateJoined);
            superAdminIntent.putExtra("EMAIL_ADDRESS", email);
            superAdminIntent.putExtra("USER_PHONE", sharedPrefPhone);
            superAdminIntent.putExtra("USER_FIRSTNAME", sharedPrefFirstName);
            superAdminIntent.putExtra("USER_SURNAME", sharedPrefSurName);
            superAdminIntent.putExtra("USER_DOB", dob);
            superAdminIntent.putExtra("USER_DATE_JOINED", dateJoined);
            superAdminIntent.putExtra("CUSTOMER_ID", customerID1);
            superAdminIntent.putExtra("USER_GENDER", gender);
            superAdminIntent.putExtra("USER_ADDRESS", address);
            superAdminIntent.putExtra("PICTURE_URI", picture);
            startActivity(superAdminIntent);

        }
        if(sharedPrefUserMachine.equalsIgnoreCase("Customer")){
            Intent tellerIntent = new Intent(this, NewCustomerDrawer.class);
            tellerIntent.putExtra("PROFILE_ID", profileID);
            tellerIntent.putExtra("PROFILE_USERNAME", userName);
            tellerIntent.putExtra("PROFILE_PASSWORD", password);
            tellerIntent.putExtra("PROFILE_OFFICE", officeBranch);
            tellerIntent.putExtra("PROFILE_STATE", state);
            tellerIntent.putExtra("PROFILE_ROLE", sharedPrefRole);
            tellerIntent.putExtra("PROFILE_EMAIL", email);
            tellerIntent.putExtra("PROFILE_PHONE", sharedPrefPhone);
            tellerIntent.putExtra("PROFILE_FIRSTNAME", sharedPrefPhone);
            tellerIntent.putExtra("PROFILE_SURNAME", sharedPrefSurName);
            tellerIntent.putExtra("PROFILE_DOB", dob);
            tellerIntent.putExtra("PROFILE_DATE_JOINED", dateJoined);
            tellerIntent.putExtra("CUSTOMER_ID", customerID1);
            tellerIntent.putExtra("PROFILE_GENDER", gender);
            tellerIntent.putExtra("PROFILE_ADDRESS", address);
            tellerIntent.putExtra("PICTURE_URI", picture);
            tellerIntent.putExtra(PROFILE_ID, profileID);
            tellerIntent.putExtra(PROFILE_USERNAME, userName);
            tellerIntent.putExtra(PROFILE_PASSWORD, password);
            tellerIntent.putExtra(PROFILE_OFFICE, officeBranch);
            tellerIntent.putExtra(PROFILE_STATE, state);
            tellerIntent.putExtra(PROFILE_ROLE, sharedPrefRole);
            tellerIntent.putExtra(PROFILE_DATE_JOINED, dateJoined);
            tellerIntent.putExtra(PROFILE_PHONE, sharedPrefPhone);
            tellerIntent.putExtra(PROFILE_FIRSTNAME, sharedPrefFirstName);
            tellerIntent.putExtra(PROFILE_SURNAME, sharedPrefSurName);
            tellerIntent.putExtra(PROFILE_DOB, dob);
            tellerIntent.putExtra(CUSTOMER_ID, customerID1);
            tellerIntent.putExtra(PROFILE_GENDER, gender);
            tellerIntent.putExtra(PROFILE_ADDRESS, address);
            tellerIntent.putExtra(PICTURE_URI, picture);
            tellerIntent.putExtra("USER_NAME", userName);
            tellerIntent.putExtra("USER_PASSWORD", password);
            tellerIntent.putExtra("USER_OFFICE", officeBranch);
            tellerIntent.putExtra("USER_STATE", state);
            tellerIntent.putExtra("USER_ROLE", sharedPrefRole);
            tellerIntent.putExtra("USER_DATE_JOINED", dateJoined);
            tellerIntent.putExtra("EMAIL_ADDRESS", email);
            tellerIntent.putExtra("USER_PHONE", sharedPrefPhone);
            tellerIntent.putExtra("USER_FIRSTNAME", sharedPrefFirstName);
            tellerIntent.putExtra("USER_SURNAME", sharedPrefSurName);
            tellerIntent.putExtra("USER_DOB", dob);
            tellerIntent.putExtra("USER_DATE_JOINED", dateJoined);
            tellerIntent.putExtra("CUSTOMER_ID", customerID1);
            tellerIntent.putExtra("USER_GENDER", gender);
            tellerIntent.putExtra("USER_ADDRESS", address);
            tellerIntent.putExtra("PICTURE_URI", picture);
            startActivity(tellerIntent);

        }
        if(sharedPrefUserMachine.equalsIgnoreCase("AdminUser")){
            Intent adminIntent = new Intent(this, AdminDrawerActivity.class);
            adminIntent.putExtra("PROFILE_ID", profileID);
            adminIntent.putExtra("PROFILE_USERNAME", userName);
            adminIntent.putExtra("PROFILE_PASSWORD", password);
            adminIntent.putExtra("PROFILE_OFFICE", officeBranch);
            adminIntent.putExtra("PROFILE_STATE", state);
            adminIntent.putExtra("PROFILE_ROLE", sharedPrefRole);
            adminIntent.putExtra("PROFILE_EMAIL", email);
            adminIntent.putExtra("PROFILE_PHONE", sharedPrefPhone);
            adminIntent.putExtra("PROFILE_FIRSTNAME", sharedPrefPhone);
            adminIntent.putExtra("PROFILE_SURNAME", sharedPrefSurName);
            adminIntent.putExtra("PROFILE_DOB", dob);
            adminIntent.putExtra("PROFILE_DATE_JOINED", dateJoined);
            adminIntent.putExtra("CUSTOMER_ID", customerID1);
            adminIntent.putExtra("PROFILE_GENDER", gender);
            adminIntent.putExtra("PROFILE_ADDRESS", address);
            adminIntent.putExtra("PICTURE_URI", picture);
            adminIntent.putExtra(PROFILE_ID, profileID);
            adminIntent.putExtra(PROFILE_USERNAME, userName);
            adminIntent.putExtra(PROFILE_PASSWORD, password);
            adminIntent.putExtra(PROFILE_OFFICE, officeBranch);
            adminIntent.putExtra(PROFILE_STATE, state);
            adminIntent.putExtra(PROFILE_ROLE, sharedPrefRole);
            adminIntent.putExtra(PROFILE_DATE_JOINED, dateJoined);
            adminIntent.putExtra(PROFILE_PHONE, sharedPrefPhone);
            adminIntent.putExtra(PROFILE_FIRSTNAME, sharedPrefFirstName);
            adminIntent.putExtra(PROFILE_SURNAME, sharedPrefSurName);
            adminIntent.putExtra(PROFILE_DOB, dob);
            adminIntent.putExtra(CUSTOMER_ID, customerID1);
            adminIntent.putExtra(PROFILE_GENDER, gender);
            adminIntent.putExtra(PROFILE_ADDRESS, address);
            adminIntent.putExtra(PICTURE_URI, picture);
            adminIntent.putExtra("USER_NAME", userName);
            adminIntent.putExtra("USER_PASSWORD", password);
            adminIntent.putExtra("USER_OFFICE", officeBranch);
            adminIntent.putExtra("USER_STATE", state);
            adminIntent.putExtra("USER_ROLE", sharedPrefRole);
            adminIntent.putExtra("USER_DATE_JOINED", dateJoined);
            adminIntent.putExtra("EMAIL_ADDRESS", email);
            adminIntent.putExtra("USER_PHONE", sharedPrefPhone);
            adminIntent.putExtra("USER_FIRSTNAME", sharedPrefFirstName);
            adminIntent.putExtra("USER_SURNAME", sharedPrefSurName);
            adminIntent.putExtra("USER_DOB", dob);
            adminIntent.putExtra("USER_DATE_JOINED", dateJoined);
            adminIntent.putExtra("CUSTOMER_ID", customerID1);
            adminIntent.putExtra("USER_GENDER", gender);
            adminIntent.putExtra("USER_ADDRESS", address);
            adminIntent.putExtra("PICTURE_URI", picture);
            startActivity(adminIntent);

        }


        if ((edtUsername.getText()).toString().equals("LS80069990") && edtPassword.getText().toString().equals("LS09996008")) {
            progressDialog.dismiss();
            Intent adminIntent = new Intent(this, SuperAdminOffice.class);
            adminIntent.putExtra("PROFILE_ID", 12000);
            adminIntent.putExtra("PROFILE_USERNAME", "LS80069990");
            adminIntent.putExtra("PROFILE_PASSWORD", "LS09996008");
            adminIntent.putExtra("PROFILE_OFFICE", officeBranch);
            adminIntent.putExtra("PROFILE_STATE", state);
            adminIntent.putExtra("PROFILE_ROLE", "SuperAdmin");
            adminIntent.putExtra("PROFILE_EMAIL", email);
            adminIntent.putExtra("PROFILE_PHONE", sharedPrefPhone);
            adminIntent.putExtra("PROFILE_FIRSTNAME", "Alpha");
            adminIntent.putExtra("PROFILE_SURNAME", "Skylight");
            adminIntent.putExtra("PROFILE_DOB", dob);
            adminIntent.putExtra("PROFILE_DATE_JOINED", dateJoined);
            adminIntent.putExtra("CUSTOMER_ID", 1111100001);
            adminIntent.putExtra("PROFILE_GENDER", gender);
            adminIntent.putExtra("PROFILE_ADDRESS", address);
            adminIntent.putExtra("PICTURE_URI", picture);
            adminIntent.putExtra(PROFILE_ID, 12000);
            adminIntent.putExtra(PROFILE_USERNAME, "LS80069990");
            adminIntent.putExtra(PROFILE_PASSWORD, "LS09996008");
            adminIntent.putExtra(PROFILE_OFFICE, officeBranch);
            adminIntent.putExtra(PROFILE_STATE, "Skylight");
            adminIntent.putExtra(PROFILE_ROLE, "SuperAdmin");
            adminIntent.putExtra(PROFILE_DATE_JOINED, dateJoined);
            adminIntent.putExtra(PROFILE_PHONE, sharedPrefPhone);
            adminIntent.putExtra(PROFILE_FIRSTNAME, "Alpha");
            adminIntent.putExtra(PROFILE_SURNAME, "Skylight");
            adminIntent.putExtra(PROFILE_DOB, dob);
            adminIntent.putExtra(CUSTOMER_ID, 1111100001);
            adminIntent.putExtra(PROFILE_GENDER, gender);
            adminIntent.putExtra(PROFILE_ADDRESS, address);
            adminIntent.putExtra(PICTURE_URI, picture);
            adminIntent.putExtra("USER_NAME", "LS80069990");
            adminIntent.putExtra("USER_PASSWORD", "LS09996008");
            adminIntent.putExtra("USER_OFFICE", officeBranch);
            adminIntent.putExtra("USER_STATE", state);
            adminIntent.putExtra("USER_ROLE", "SuperAdmin");
            adminIntent.putExtra("USER_DATE_JOINED", dateJoined);
            adminIntent.putExtra("EMAIL_ADDRESS", email);
            adminIntent.putExtra("USER_PHONE", sharedPrefPhone);
            adminIntent.putExtra("USER_FIRSTNAME", "Alpha");
            adminIntent.putExtra("USER_SURNAME", "Skylight");
            adminIntent.putExtra("USER_DOB", dob);
            adminIntent.putExtra("USER_DATE_JOINED", dateJoined);
            adminIntent.putExtra("CUSTOMER_ID", 1111100001);
            adminIntent.putExtra("USER_GENDER", gender);
            adminIntent.putExtra("USER_ADDRESS", address);
            adminIntent.putExtra("PICTURE_URI", picture);
            editor = userPreferences.edit();
            editor.putString("USERNAME", "LS80069990");
            editor.putString("USER_PASSWORD", "LS09996008");
            editor.putString("USER_SURNAME", "Skylight");
            editor.putString("USER_FIRSTNAME", "Alpha");
            editor.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
            editor.putString("machine", sharedPrefRole);
            editor.putString("PICTURE_URI", String.valueOf(picture));
            editor.putString("USER_ROLE", "SuperAdmin");
            editor.putInt("PROFILE_ID", 12000);
            editor.putString("PROFILE_USERNAME", "LS80069990");
            editor.putString("PROFILE_PASSWORD", "LS09996008");
            editor.putString("PROFILE_OFFICE", "Skylight");
            editor.putString("PROFILE_STATE", state);
            editor.putString("PROFILE_ROLE", "SuperAdmin");
            editor.putString("PROFILE_DATE_JOINED", dateJoined);
            editor.putString("PROFILE_EMAIL", sharedPrefEmail);
            editor.putString("PROFILE_PHONE", sharedPrefPhone);
            editor.putString("PROFILE_FIRSTNAME", sharedPrefFirstName);
            editor.putString("PROFILE_SURNAME", sharedPrefSurName);
            editor.putString("PROFILE_DOB", dob);
            editor.putInt("CUSTOMER_ID", 1111100001);
            editor.putString("PROFILE_GENDER", "Female");
            editor.putString("PROFILE_ADDRESS", address);
            editor.putInt(PROFILE_ID, 12000);
            editor.putString(PROFILE_USERNAME, "LS80069990");
            editor.putString(PROFILE_PASSWORD, "LS09996008");
            editor.putString(PROFILE_OFFICE, "Skylight");
            editor.putString(PROFILE_STATE, state);
            editor.putString(PROFILE_ROLE, "SuperAdmin");
            editor.putString(PROFILE_DATE_JOINED, dateJoined);
            editor.putString(PROFILE_EMAIL, sharedPrefEmail);
            editor.putString(PROFILE_PHONE, sharedPrefPhone);
            editor.putString(PROFILE_FIRSTNAME, "Alpha");
            editor.putString(PROFILE_SURNAME, "Skylight");
            editor.putString(PROFILE_DOB, dob);
            editor.putInt(CUSTOMER_ID, 1111100001);
            editor.putString(PROFILE_GENDER, gender);
            editor.putString(PROFILE_ADDRESS, address);
            userProfile= new Profile(12000,"Skylight", "Alpha", "08069524599", "urskylight@gmail.com", "", "female", "Skylight", "","Rivers", "Elelenwo", "19/04/2022","SuperAdmin", "LS80069990", "LS09996008","Confirmed","");
            dbHelper.saveNewProfile(userProfile);
            json = gson.toJson(lastProfileUsed);
            startActivity(adminIntent);
            editor.putString("LastProfileUsed", json);
            editor.putBoolean("rememberMe", chkRememberCred.isChecked());
            editor.apply();

        }

    }
    private void loginWithPref() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("processing Login");
        progressDialog.setCancelable(false);
        progressDialog.show();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        sharedPrefUserName = userPreferences.getString("USER_NAME", "");
        sharedPrefUserPassword = userPreferences.getString("USER_PASSWORD", "");
        sharedPrefCusID = userPreferences.getString("CUSTOMER_ID", "");
        sharedPrefUserMachine = userPreferences.getString("machine", "");
        sharedPrefProfileID = userPreferences.getInt("PROFILE_ID", 0);
        sharedPrefEmail = userPreferences.getString("EMAIL_ADDRESS", "");
        sharedPrefRole = userPreferences.getString("USER_ROLE", "");
        SharedPrefJoinedDate = userPreferences.getString("USER_DATE_JOINED", "");
        SharedPrefOffice = userPreferences.getString("CHOSEN_OFFICE", "");
        sharedPrefFirstName = userPreferences.getString("USER_FIRSTNAME", "");
        sharedPrefEmail = userPreferences.getString("USER_EMAIL", "");
        sharedPrefPhone = userPreferences.getString("USER_PHONE", "");
        SharedPrefAddress = userPreferences.getString("USER_ADDRESS", "");
        SharedPrefDOB = userPreferences.getString("USER_DOB", "");

        if (edtUsername.getText().toString().isEmpty()) {
            edtUsername.setError("Please Enter Your User Name");
        }else if(edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError("Please Enter Your Password");
        }else {

            if ((edtUsername.getText()).toString().equals("LS80069990") && edtPassword.getText().toString().equals("LS09996008")) {
                progressDialog.dismiss();
                //match = true;
                //isAdmin = true;

                //SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                Intent intentBecky = new Intent(this, SuperAdminOffice.class);
                intentBecky.putExtra("id", "12");
                intentBecky.putExtra("USER_PASSWORD", "LS09996008");
                intentBecky.putExtra("machine", "UserSuperAdmin");
                intentBecky.putExtra("machine", "SuperAdmin");
                intentBecky.putExtra("Role", "SuperAdmin");
                intentBecky.putExtra("USERNAME", "LS80069990");
                intentBecky.putExtra("PROFILE_ID", LOGIN_ID_EXTRA_KEY);
                intentBecky.putExtra("USER_SURNAME", "Super");
                intentBecky.putExtra("USER_FIRSTNAME", "Admin");
                intentBecky.putExtra("USER_PASSWORD", "LS09996008");
                intentBecky.putExtra(PROFILE_USERNAME, "LS80069990");
                intentBecky.putExtra(PROFILE_ID, LOGIN_ID_EXTRA_KEY);
                intentBecky.putExtra(PROFILE_SURNAME, "Super");
                intentBecky.putExtra(PROFILE_FIRSTNAME, "Admin");
                startActivity(intentBecky);

                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putString("USERNAME", "LS80069990");
                editor.putString("USER_PASSWORD", "LS09996008");
                editor.putString("USER_SURNAME", "Super");
                editor.putString("USER_FIRSTNAME", "Admin");
                editor.putString("PROFILE_ID", LOGIN_ID_EXTRA_KEY);
                editor.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
                editor.putString(PROFILE_USERNAME, "LS80069990");
                editor.putString(PROFILE_PASSWORD, "LS09996008");
                editor.putString(PROFILE_SURNAME, "Super");
                editor.putString(PROFILE_FIRSTNAME, "Admin");
                editor.putString(PROFILE_ID, LOGIN_ID_EXTRA_KEY);
                editor.putString(LOGIN_ID_EXTRA_KEY, LOGIN_ID_EXTRA_KEY);
                editor.putString("machine", "UserSuperAdmin");
                editor.putString("machine", "SuperAdmin");
                editor.putString("Role", "SuperAdmin");
                gson = new Gson();
                json = gson.toJson(lastProfileUsed);
                editor.putString("LastProfileUsed", json).apply();
                userPreferences.edit().putBoolean("rememberMe", chkRememberCred.isChecked()).apply();
                editor.apply();

            }
            if (edtUsername.getText().toString().equals("LSG800690") && edtPassword.getText().toString().equals("LSG815501")) {
                progressDialog.dismiss();
                SharedPreferences.Editor editor2 = userPreferences.edit();
                editor2.putString("USERNAME", "LSG800690");
                editor2.putString("machine", "SuperAdmin");
                editor2.putString("USER_PASSWORD", "LSG815501");
                editor2.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
                editor2.putString("USER_SURNAME", "Alpha ");
                editor2.putString("USER_FIRSTNAME", "Super");
                editor2.putString("USER_ROLE", "SuperAdmin");

                editor2.putString(PROFILE_USERNAME, "LSG800690");
                editor2.putString("machine", "SuperAdmin");
                editor2.putString(PROFILE_PASSWORD, "LSG815501");
                editor2.putString(LOGIN_ID_EXTRA_KEY, LOGIN_ID_EXTRA_KEY);
                editor2.putString(PROFILE_SURNAME, "Alpha");
                editor2.putString(PROFILE_FIRSTNAME, "Super");
                editor2.putString(PROFILE_ROLE, "SuperAdmin");
                gson = new Gson();
                json = gson.toJson(lastProfileUsed);
                editor2.putString("LastProfileUsed", json).apply();
                userPreferences.edit().putBoolean("rememberMe", chkRememberCred.isChecked()).apply();
                editor2.apply();

                Intent intentBen = new Intent(this, SuperAdminOffice.class);
                intentBen.putExtra("id", "12");
                intentBen.putExtra("USER_PASSWORD", "LSG815501");
                intentBen.putExtra("machine", "UserSuperAdmin");
                intentBen.putExtra("machine", "SuperAdmin");
                intentBen.putExtra("Role", "SuperAdmin");
                intentBen.putExtra("USERNAME", "LSG800690");
                intentBen.putExtra("PROFILE_ID", LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra("USER_SURNAME", "Alpha");
                intentBen.putExtra("USER_FIRSTNAME", "Super");
                intentBen.putExtra("USER_PASSWORD", "LSG815501");
                intentBen.putExtra(PROFILE_PASSWORD, "LSG815501");
                intentBen.putExtra(PROFILE_USERNAME, "LSG800690");
                intentBen.putExtra(PROFILE_ID, LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra(PROFILE_ROLE, "Admin");
                intentBen.putExtra(PROFILE_FIRSTNAME, "Super");
                startActivity(intentBen);
            }
            if (edtUsername.getText().toString().equals("L1000690") && edtPassword.getText().toString().equals("L81550122")) {
                progressDialog.dismiss();
                SharedPreferences.Editor editor2 = userPreferences.edit();
                editor2.putString(PROFILE_USERNAME, "L1000690");
                editor2.putString("machine", "SuperAdmin");
                editor2.putString("USER_PASSWORD", "L81550122");
                editor2.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
                editor2.putString("USER_SURNAME", " ");
                editor2.putString("USER_FIRSTNAME", "Admin UOE Road");
                editor2.putString("USER_ROLE", "SuperAdmin");
                editor2.putString("USERNAME", "L1000690");
                editor2.putString("machine", "SuperAdmin");
                editor2.putString(PROFILE_PASSWORD, "L81550122");
                editor2.putString(LOGIN_ID_EXTRA_KEY, LOGIN_ID_EXTRA_KEY);
                editor2.putString(PROFILE_SURNAME, " Beta");
                editor2.putString(PROFILE_FIRSTNAME, "Super");
                editor2.putString(PROFILE_ROLE, "SuperAdmin");
                editor2.apply();

                Intent intentBen = new Intent(this, SuperAdminOffice.class);
//                    intentBen.putExtra("id", lastProfileUsed.getDbId());
                intentBen.putExtra("USER_PASSWORD", "L81550122");
                intentBen.putExtra("machine", "UserSuperAdmin");
                intentBen.putExtra("machine", "SuperAdmin");
                intentBen.putExtra("Role", "SuperAdmin");
                intentBen.putExtra("USERNAME", "L1000690");
                intentBen.putExtra("PROFILE_ID", LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra("USER_SURNAME", "Beta");
                intentBen.putExtra("USER_FIRSTNAME", "Super");
                intentBen.putExtra("USER_PASSWORD", "L81550122");
                intentBen.putExtra(PROFILE_PASSWORD, "L81550122");
                intentBen.putExtra(PROFILE_USERNAME, "L1000690");
                intentBen.putExtra(PROFILE_ID, LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra(PROFILE_ROLE, "SuperAdmin");
                intentBen.putExtra(PROFILE_FIRSTNAME, "Super");
                startActivity(intentBen);
            }
            if (edtUsername.getText().toString().equals("LS8006120") && edtPassword.getText().toString().equals("LS810231")) {
                progressDialog.dismiss();
                //SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = userPreferences.edit();
                editor2.putString(PROFILE_USERNAME, "LS8006120");
                editor2.putString("machine", "UserSuperAdmin");
                editor2.putString("machine", "SuperAdmin");
                editor2.putString(PROFILE_PASSWORD, "LS810231");
                editor2.putString(LOGIN_ID_EXTRA_KEY, LOGIN_ID_EXTRA_KEY);
                editor2.putString(PROFILE_SURNAME, "");
                editor2.putString(PROFILE_FIRSTNAME, "Admin 6");
                editor2.putString(PROFILE_ROLE, "SuperAdmin");
                editor2.putString("USER_PASSWORD", "LS810231");
                editor2.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
                editor2.putString("USER_SURNAME", "Tera");
                editor2.putString("USER_FIRSTNAME", "Super");
                editor2.putString("USER_ROLE", "SuperAdmin");
                editor2.apply();

                Intent intentBen = new Intent(this, SuperAdminOffice.class);
                intentBen.putExtra("id", lastProfileUsed.getPID());
                intentBen.putExtra("USER_PASSWORD", "LS810231");
                intentBen.putExtra("machine", "UserSuperAdmin");
                intentBen.putExtra("machine", "SuperAdmin");
                intentBen.putExtra("Role", "SuperAdmin");
                intentBen.putExtra("USERNAME", "LS8006120");
                intentBen.putExtra("PROFILE_ID", LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra("USER_SURNAME", "Tera");
                intentBen.putExtra("USER_FIRSTNAME", "Super");
                intentBen.putExtra("USER_PASSWORD", "LS810231");
                intentBen.putExtra("machine", "UserSuperAdmin");
                intentBen.putExtra("machine", "SuperAdmin");
                intentBen.putExtra(PROFILE_PASSWORD, "LS810231");
                intentBen.putExtra(PROFILE_USERNAME, "LS8006120");
                intentBen.putExtra(PROFILE_ID, LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra(PROFILE_ROLE, "SuperAdmin");
                intentBen.putExtra(PROFILE_FIRSTNAME, "Super");
                intentBen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentBen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentBen);
            }

            if (edtUsername.getText().toString().equals("AU100690") && edtPassword.getText().toString().equals("AU815012")) {
                progressDialog.dismiss();
                SharedPreferences.Editor editor2 = userPreferences.edit();
                editor2.putString(PROFILE_USERNAME, "Sky1000690");
                editor2.putString("machine", "UserSuperAdmin");
                editor2.putString("machine", "SuperAdmin");

                editor2.putString("USER_PASSWORD", "AU815012");
                editor2.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
                editor2.putString("USER_SURNAME", "Belle ");
                editor2.putString("USER_FIRSTNAME", "Super");
                editor2.putString("USER_ROLE", "SuperAdmin");

                editor2.putString(PROFILE_PASSWORD, "AU815012");
                editor2.putString(LOGIN_ID_EXTRA_KEY, LOGIN_ID_EXTRA_KEY);
                editor2.putString(PROFILE_SURNAME, " Belle");
                editor2.putString(PROFILE_FIRSTNAME, "Super");
                editor2.putString(PROFILE_ROLE, "SuperAdmin");
                editor2.apply();

                Intent intentBen = new Intent(this, SuperAdminOffice.class);
//                    intentBen.putExtra("id", lastProfileUsed.getDbId());
                intentBen.putExtra("id", "12");
                intentBen.putExtra("USER_PASSWORD", "AU815012");
                intentBen.putExtra("machine", "UserSuperAdmin");
                intentBen.putExtra("machine", "SuperAdmin");
                intentBen.putExtra("Role", "SuperAdmin");
                intentBen.putExtra("USERNAME", "AU100690");
                intentBen.putExtra("PROFILE_ID", LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra("USER_SURNAME", "Belle");
                intentBen.putExtra("USER_FIRSTNAME", "Super");
                intentBen.putExtra("USER_PASSWORD", "AU815012");
                intentBen.putExtra(PROFILE_PASSWORD, "AU815012");
                intentBen.putExtra(PROFILE_USERNAME, "AU100690");
                intentBen.putExtra(PROFILE_ID, LOGIN_ID_EXTRA_KEY);
                intentBen.putExtra(PROFILE_ROLE, "SuperAdmin");
                intentBen.putExtra(PROFILE_SURNAME, "Belle");
                intentBen.putExtra(PROFILE_FIRSTNAME, "Super");
                startActivity(intentBen);
            }

            if (edtUsername.getText().toString().equals("AU801920") && edtPassword.getText().toString().equals("AU28151")) {
                progressDialog.dismiss();
                SharedPreferences.Editor editor2 = userPreferences.edit();
                editor2.putString(PROFILE_USERNAME, "AU801920");
                editor2.putString("machine", "UserSuperAdmin");
                editor2.putString("machine", "SuperAdmin");
                editor2.putString(PROFILE_PASSWORD, "AU28151");
                editor2.putString(LOGIN_ID_EXTRA_KEY, LOGIN_ID_EXTRA_KEY);
                editor2.putString(PROFILE_SURNAME, "De");
                editor2.putString(PROFILE_FIRSTNAME, "Super");
                editor2.putString(PROFILE_ROLE, "SuperAdmin");

                editor2.putString("USER_PASSWORD", "AU28151");
                editor2.putString("LOGIN_ID_EXTRA_KEY", LOGIN_ID_EXTRA_KEY);
                editor2.putString("USER_SURNAME", "De");
                editor2.putString("USER_FIRSTNAME", "Super");
                editor2.putString("USER_ROLE", "SuperAdmin");
                editor2.apply();

                Intent intentAdmin = new Intent(this, SuperAdminOffice.class);
//                    intentBen.putExtra("id", lastProfileUsed.getDbId());
                intentAdmin.putExtra("id", "12");
                intentAdmin.putExtra("USER_PASSWORD", "AU28151");
                intentAdmin.putExtra("machine", "UserSuperAdmin");
                intentAdmin.putExtra("machine", "SuperAdmin");
                intentAdmin.putExtra("Role", "SuperAdmin");
                intentAdmin.putExtra("USERNAME", "AU801920");
                intentAdmin.putExtra("PROFILE_ID", LOGIN_ID_EXTRA_KEY);
                intentAdmin.putExtra("USER_SURNAME", "De");
                intentAdmin.putExtra("USER_FIRSTNAME", "Super");
                intentAdmin.putExtra("USER_PASSWORD", "AU28151");
                intentAdmin.putExtra(PROFILE_PASSWORD, "AU28151");
                intentAdmin.putExtra(PROFILE_USERNAME, "AU801920");
                intentAdmin.putExtra(PROFILE_ID, LOGIN_ID_EXTRA_KEY);
                intentAdmin.putExtra(PROFILE_ROLE, "SuperAdmin");
                intentAdmin.putExtra(PROFILE_FIRSTNAME, "De");
                intentAdmin.putExtra(PROFILE_SURNAME, "Super");
                intentAdmin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentAdmin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentAdmin);
            }


            if(userName.equals(sharedPrefUserName)&& password.equals(sharedPrefUserPassword)&& sharedPrefUserMachine.equals("Teller")){
                if (checkBoxteller.isChecked()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(this, TellerDrawerAct.class);
                    intent.putExtra(PROFILE_USERNAME, userName);
                    intent.putExtra(PROFILE_ID, String.valueOf(sharedPrefProfileID));
                    intent.putExtra(PASSWORD, password);
                    intent.putExtra(PROFILE_NEXT_OF_KIN, "");
                    intent.putExtra(PROFILE_DATE_JOINED, SharedPrefJoinedDate);
                    intent.putExtra("machine", "Teller");
                    startActivity(intent);
                }

            }
            if(userName.equals(sharedPrefUserName)&& password.equals(sharedPrefUserPassword)&& sharedPrefUserMachine.equals("Teller")){
                if (checkBoxCustomer.isChecked()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(this, NewCustomerDrawer.class);
                    intent.putExtra(PROFILE_USERNAME, userName);
                    intent.putExtra(PROFILE_ID, String.valueOf(sharedPrefProfileID));
                    intent.putExtra(CUSTOMER_ID,String.valueOf(sharedPrefCusID));
                    intent.putExtra(PASSWORD, password);
                    intent.putExtra(PROFILE_NEXT_OF_KIN, "");
                    intent.putExtra(PROFILE_DATE_JOINED, SharedPrefJoinedDate);
                    intent.putExtra("machine", "Teller");
                    startActivity(intent);
                }

            }
            if(userName.equals(sharedPrefUserName)&& password.equals(sharedPrefUserPassword)&& sharedPrefUserMachine.equals("Customer")){
                if (checkBoxCustomer.isChecked()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(this, NewCustomerDrawer.class);
                    intent.putExtra(PROFILE_USERNAME, userName);
                    intent.putExtra(PROFILE_ID, String.valueOf(sharedPrefProfileID));
                    intent.putExtra(CUSTOMER_ID,String.valueOf(sharedPrefCusID));

                    intent.putExtra(PASSWORD, password);
                    intent.putExtra(PROFILE_SURNAME, sharedPrefSurName);
                    intent.putExtra(PROFILE_FIRSTNAME, sharedPrefFirstName);
                    intent.putExtra(PROFILE_PHONE, sharedPrefPhone);
                    intent.putExtra(PROFILE_NEXT_OF_KIN, "");

                    intent.putExtra(PROFILE_DATE_JOINED, SharedPrefJoinedDate);
                    intent.putExtra("machine", "Customer");


                    intent.putExtra(PASSWORD, password);
                    intent.putExtra(PROFILE_NEXT_OF_KIN, "");
                    intent.putExtra(PROFILE_DATE_JOINED, SharedPrefJoinedDate);
                    intent.putExtra("machine", "Customer");
                    startActivity(intent);
                }

            }


        }


    }
    protected void sendSMSMessage() {
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        sharedPrefUserName = userPreferences.getString("USER_NAME", "");
        sharedPrefUserPassword = userPreferences.getString("USER_PASSWORD", "");
        sharedPrefCusID = userPreferences.getString("CUSTOMER_ID", "");
        sharedPrefUserMachine = userPreferences.getString("machine", "");
        sharedPrefProfileID = userPreferences.getInt("PROFILE_ID", 0);
        sharedPrefSurName = userPreferences.getString("USER_SURNAME", "");
        sharedPrefFirstName = userPreferences.getString("USER_FIRSTNAME", "");
        sharedPrefEmail = userPreferences.getString("EMAIL_ADDRESS", "");
        sharedPrefPhone = userPreferences.getString("USER_PHONE", "");
        SharedPrefAddress = userPreferences.getString("USER_ADDRESS", "");
        SharedPrefDOB = userPreferences.getString("USER_DOB", "");
        sharedPrefRole = userPreferences.getString("USER_ROLE", "");
        SharedPrefGender = userPreferences.getString("USER_GENDER", "");
        SharedPrefJoinedDate = userPreferences.getString("USER_DATE_JOINED", "");
        SharedPrefOffice = userPreferences.getString("USER_OFFICE", "");
        SharedPrefState = userPreferences.getString("USER_STATE", "");

        sharedPrefUserMachine = userPreferences.getString("machine", "");
        Bundle smsBundle= new Bundle();
        String smsMessage="You just Signed into the Skylight App @ "+""+"@"+System.currentTimeMillis();
        smsBundle.putString(PROFILE_PHONE,sharedPrefPhone);
        smsBundle.putString("USER_PHONE",sharedPrefPhone);
        smsBundle.putString("smsMessage",smsMessage);
        smsBundle.putString("from","Skylight");
        smsBundle.putString("to",sharedPrefPhone);
        Intent itemPurchaseIntent = new Intent(LoginActivity.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }
    private void SignUpNewUser() {

        Intent intent = new Intent(this, SignTabMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(this, "Taking you to sign up/in Board", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    private void startPasswordActivity() {


        Intent intent = new Intent(this, PasswordRecoveryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Taking you to the Dashboard Area", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    private void createAccount() {

        Intent intent = new Intent(this, SignTabMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(this, "Taking you to sign up/in Board", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        this.finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        sharedPrefUserName = userPreferences.getString("USER_NAME", "");
        sharedPrefUserPassword = userPreferences.getString("USER_PASSWORD", "");
        sharedPrefCusID = userPreferences.getString("CUSTOMER_ID", "");
        sharedPrefUserMachine = userPreferences.getString("machine", "");
        sharedPrefProfileID = userPreferences.getInt("PROFILE_ID", 0);
        sharedPrefSurName = userPreferences.getString("USER_SURNAME", "");
        sharedPrefFirstName = userPreferences.getString("USER_FIRSTNAME", "");
        sharedPrefEmail = userPreferences.getString("EMAIL_ADDRESS", "");
        sharedPrefPhone = userPreferences.getString("USER_PHONE", "");
        SharedPrefAddress = userPreferences.getString("USER_ADDRESS", "");
        SharedPrefDOB = userPreferences.getString("USER_DOB", "");
        sharedPrefRole = userPreferences.getString("USER_ROLE", "");
        SharedPrefGender = userPreferences.getString("USER_GENDER", "");
        SharedPrefJoinedDate = userPreferences.getString("USER_DATE_JOINED", "");
        SharedPrefOffice = userPreferences.getString("USER_OFFICE", "");
        SharedPrefState = userPreferences.getString("USER_STATE", "");

        sharedPrefUserPassword = userPreferences.getString(PROFILE_PASSWORD, "");
        sharedPrefCusID = userPreferences.getString(CUSTOMER_ID, "");
        sharedPrefUserMachine = userPreferences.getString("machine", "");

        if (userProfile != null) {
            Intent tellerIntent = new Intent(LoginActivity.this, LoginDirAct.class);
            tellerIntent.putExtra(PROFILE_ID, profileID);
            tellerIntent.putExtra(PROFILE_PASSWORD, sharedPrefUserPassword);
            tellerIntent.putExtra(PROFILE_OFFICE, SharedPrefOffice);
            tellerIntent.putExtra(PROFILE_STATE, SharedPrefState);
            tellerIntent.putExtra(PROFILE_ROLE, sharedPrefRole);
            tellerIntent.putExtra(PROFILE_DATE_JOINED, SharedPrefJoinedDate);
            tellerIntent.putExtra(PROFILE_PHONE, sharedPrefPhone);
            tellerIntent.putExtra(PROFILE_FIRSTNAME, sharedPrefFirstName);
            tellerIntent.putExtra(PROFILE_SURNAME, sharedPrefSurName);
            tellerIntent.putExtra(PROFILE_DOB, SharedPrefDOB);
            tellerIntent.putExtra(PROFILE_DATE_JOINED, SharedPrefJoinedDate);
            tellerIntent.putExtra(CUSTOMER_ID, sharedPrefCusID);
            tellerIntent.putExtra(PROFILE_GENDER, SharedPrefGender);
            tellerIntent.putExtra(PROFILE_ADDRESS, SharedPrefAddress);

            tellerIntent.putExtra("USER_NAME", sharedPrefUserName);
            tellerIntent.putExtra("USER_PASSWORD", sharedPrefUserPassword);
            tellerIntent.putExtra("USER_OFFICE", SharedPrefOffice);
            tellerIntent.putExtra("USER_STATE", SharedPrefState);
            tellerIntent.putExtra("USER_ROLE", sharedPrefRole);
            tellerIntent.putExtra("USER_DATE_JOINED", SharedPrefJoinedDate);
            tellerIntent.putExtra("EMAIL_ADDRESS", sharedPrefEmail);
            tellerIntent.putExtra("USER_PHONE", sharedPrefPhone);
            tellerIntent.putExtra("USER_FIRSTNAME", sharedPrefFirstName);
            tellerIntent.putExtra("USER_SURNAME", sharedPrefSurName);
            tellerIntent.putExtra("USER_DOB", SharedPrefDOB);
            tellerIntent.putExtra("USER_DATE_JOINED", SharedPrefJoinedDate);
            tellerIntent.putExtra("CUSTOMER_ID", sharedPrefCusID);
            tellerIntent.putExtra("USER_GENDER", SharedPrefGender);
            tellerIntent.putExtra("USER_ADDRESS", SharedPrefAddress);
            tellerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            tellerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(tellerIntent);
        }
    }


    public void loginWithUserName(View view) {
    }

    public void useEmailForLogin(View view) {
    }

    public void loginUserEmail(View view) {
    }
}