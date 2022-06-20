package com.skylightapp.SuperAdmin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Accountant.AcctantBackOffice;
import com.skylightapp.Admins.AdminLoanList;
import com.skylightapp.Admins.AdminPackageActivity;
import com.skylightapp.Admins.AdminSupportAct;
import com.skylightapp.Admins.AdminTabActivity;
import com.skylightapp.Admins.AdminTimelineAct;
import com.skylightapp.Admins.AdminTransActivity;
import com.skylightapp.Admins.BirthdayTab;
import com.skylightapp.Admins.CustomerListActivity;
import com.skylightapp.Admins.SODueDateListAct;
import com.skylightapp.Admins.ImportDateTab;
import com.skylightapp.Admins.SendUserMessageAct;
import com.skylightapp.Admins.SkylightAllWeb;
import com.skylightapp.Admins.SkylightUsersActivity;
import com.skylightapp.BlockedUserAct;
import com.skylightapp.CameraActivity;
import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.GroupSavingsTab;
import com.skylightapp.Inventory.SuperInvTab;
import com.skylightapp.LoginActivity;
import com.skylightapp.PayClientActivity;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SkylightSliderAct;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuperAdminOffice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static final String KEY = "SuperAdminOffice.KEY";
    GridLayout maingrid;
    Button btnSOSuper, btnOurPrivacyPolicy,btnInvestment;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1,SharedPrefSuperUser;
    private LinearLayout linearLayout;

    private AppCompatImageView imgTime;

    private UserSuperAdmin superAdminProfile;

    private int profileID;
    Button btnMore;
    CardView cardViewPackges,cardViewGrpSavings,cardViewHistory, cardViewStandingOrders, cardViewOrders, cardViewSupport;

    AppCompatTextView extName, textID, textAllCustomers, textCustomersToday, textInvestments, textAllUsers;
    FrameLayout frameLayout1,frameLayout2;
    CircleImageView profileImage;
    private Profile profile;
    private  AdminBalance adminBalance;

    private AppCompatTextView txtMessage;
    private AppCompatTextView txtSuperBalance;
    private AppCompatTextView balance;
    private AppCompatTextView txtAllLoans;
    private AppCompatTextView txtSavingsToday;
    private AppCompatTextView txtAllSubscriptions;
    private String userName;
    private AppCompatTextView reports;
    private AppCompatTextView txtAllStandingOrders,txtUserName;

    private Customer customerProfile;
    Intent data;
    FloatingActionButton floatingActionButton;
    Customer customer;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    String surname,firstName,names, dateOfToday;
    ImageButton hideLayouts;
    long customersToday,saving, activePackagesToday;
    double grpSavingsToday;
    CircleImageView imgProfilePic;
    private  int SOCount;
    Button btnUtils, btnImpDates, btnTimeLines;
    private Profile userProfile;
    int allCus,allUsers,savingsToday,packagesToday,soToday;
    private Date today;
    Date currentDate;
    DBHelper applicationDb;
    double totalAdminBalance;
    private Uri pictureLink;
    private static final String PREF_NAME = "skylight";
    String machineUser, office,state,role,dbRole,joinedDate,password, email,phoneNO, dob,gender,address;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;

    ActivityResultLauncher<Intent> startSuperPictureActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            if (data != null) {
                                Uri pictureUri = data.getData();
                                Picasso.get().load(pictureUri).into(profileImage);
                            }
                            Bundle bundle = null;
                            if (data != null) {
                                bundle = result.getData().getExtras();
                            }

                            Bitmap thumbnail = null;
                            if (bundle != null) {
                                thumbnail = (Bitmap) bundle.get("data");
                            }
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            if (thumbnail != null) {
                                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                            }

                            File destination = new File(Environment.getExternalStorageDirectory(),
                                    System.currentTimeMillis() + ".jpg");
                            FileOutputStream fo;
                            try {
                                //destination.createNewFile();
                                fo = new FileOutputStream(destination);
                                try {
                                    fo.write(bytes.toByteArray());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fo.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                            //Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                            /*if (data != null) {
                                pictureUri = data.getData();
                                Picasso.get().load(pictureUri).into(photoPrOP);
                            }*/
                            Toast.makeText(SuperAdminOffice.this, "successful ", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(SuperAdminOffice.this, "cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_admin_office);
        toolbar = (Toolbar) findViewById(R.id.toolbar_super);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        adminBalance= new AdminBalance();
        superAdminProfile =new UserSuperAdmin();
        applicationDb = new DBHelper(this);
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefSuperUser=userPreferences.getString("UserSuperAdmin", "");
        SharedPrefSuperUser=userPreferences.getString("SuperAdmin", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        profileID = userPreferences.getInt("PROFILE_ID", 0);
        userName = userPreferences.getString("PROFILE_USERNAME", "");
        office = userPreferences.getString("PROFILE_OFFICE", "");
        state = userPreferences.getString("PROFILE_STATE", "");
        role = userPreferences.getString("PROFILE_ROLE", "");
        password = userPreferences.getString("PROFILE_PASSWORD", "");
        dbRole=applicationDb.getProfileRoleByUserNameAndPassword(userName,password);

        joinedDate = userPreferences.getString("PROFILE_DATE_JOINED", "");
        surname = userPreferences.getString("PROFILE_SURNAME", "");
        email = userPreferences.getString("PROFILE_EMAIL", "");
        phoneNO = userPreferences.getString("PROFILE_PHONE", "");
        firstName = userPreferences.getString("PROFILE_FIRSTNAME", "");
        dob = userPreferences.getString("PROFILE_DOB", "");
        gender = userPreferences.getString("PROFILE_GENDER", "");
        address = userPreferences.getString("PROFILE_ADDRESS", "");
        pictureLink = Uri.parse(userPreferences.getString("PICTURE_URI", ""));

        //json = userPreferences.getString("LastProfileUsed", "");
        json = userPreferences.getString("LastSuperAdminProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        superAdminProfile = gson.fromJson(json, UserSuperAdmin.class);
        txtUserName = findViewById(R.id.super_username);
        txtAllLoans = findViewById(R.id.super_loans);
        txtSuperBalance = findViewById(R.id.super_balance);

        try {
            totalAdminBalance=adminBalance.getTotalReceivedAmount();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if(totalAdminBalance>0){
            txtSuperBalance.setText("Skylight Commissions: N"+totalAdminBalance);


        }else {
            txtSuperBalance.setText("Sorry! N0 Skylight Commission, yet");


        }


        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());

        try {
            dateOfToday =dateFormat.format(currentDate);
            today = dateFormat.parse(dateOfToday);


        } catch (ParseException ignored) {
        }

        txtAllSubscriptions = findViewById(R.id.Super_all_packs);

        //sPackages = findViewById(R.id.cus_packages);
        txtAllStandingOrders = findViewById(R.id.super_SO);

        txtSavingsToday = findViewById(R.id.savingsToday);
        //txtSavingsToday.setOnClickListener(this);
        extName = findViewById(R.id.super_name);
        textID = findViewById(R.id.super_id);
        textAllCustomers = findViewById(R.id.allCus_Super);
        //textAllCustomers.setOnClickListener(this);
        try {
            allCus=applicationDb.getCustomersCountAdmin();
            savingsToday=applicationDb.getSavingsCountToday(dateOfToday);
            allUsers=applicationDb.getAllProfileCount();
            customersToday=applicationDb.getCusCountToday(dateOfToday);
            activePackagesToday = applicationDb.getPackagesCountAdmin();

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        textCustomersToday = findViewById(R.id.cus_today_Super);
        //textCustomersToday.setOnClickListener(this);

        textAllUsers = findViewById(R.id.allUsersSuper);

        //textAllUsers.setOnClickListener(this);
        imgProfilePic = findViewById(R.id.profile_image_super);
        //txtAllStandingOrders.setOnClickListener(this);

        if(superAdminProfile !=null){
            profileID= superAdminProfile.getUserID();
            Bitmap bitmap = applicationDb.getProfilePicture(profileID);
            imgProfilePic.setImageBitmap(bitmap);
        }
        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSuperPictureActivityForResult.launch(new Intent(SuperAdminOffice.this, CameraActivity.class));

            }
        });


        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_super);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.super_drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Super Admin BackOffice");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__category);

        btnSOSuper =findViewById(R.id.standingOrder3);


        btnImpDates =findViewById(R.id.ImportantDate);



        btnTimeLines =findViewById(R.id.superTimelinesA);
        linearLayout=findViewById(R.id.linearGrid);
        maingrid=(GridLayout) findViewById(R.id.ViewSuper);
        imgTime = findViewById(R.id.superGreetings);
        setSingleEvent(maingrid);
        try {
            txtAllLoans.setOnClickListener(this::goLoans);
            btnImpDates.setOnClickListener(this::importantDateSuper);
            btnTimeLines.setOnClickListener(this::TimelinesSuper);
            txtAllSubscriptions.setOnClickListener(this::gpPackages);
            btnSOSuper.setOnClickListener(this::standingOrdersSuper);
            SOCount=applicationDb.getStandingOrderCountToday(dateOfToday);
            txtAllStandingOrders.setText(MessageFormat.format("Standing Orders:{0}", SOCount));
            textCustomersToday.setText(MessageFormat.format("New Cus:{0}", customersToday));
            textAllCustomers.setText(MessageFormat.format("All Cus:{0}", allCus));
            textAllUsers.setText(MessageFormat.format("All Users:{0}", allUsers));
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        StringBuilder welcomeString = new StringBuilder();


        calendar = Calendar.getInstance();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            imgTime.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            welcomeString.append(getString(R.string.good_afternoon));
            imgTime.setImageResource(R.drawable.good_after1);
        } else {
            welcomeString.append(getString(R.string.good_evening));
            imgTime.setImageResource(R.drawable.good_even2);
        }



        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String[] days = getResources().getStringArray(R.array.days);
        String dow = "";

        switch (day) {
            case Calendar.SUNDAY:
                dow = days[0];
                break;
            case Calendar.MONDAY:
                dow = days[1];
                break;
            case Calendar.TUESDAY:
                dow = days[2];
                break;
            case Calendar.WEDNESDAY:
                dow = days[3];
                break;
            case Calendar.THURSDAY:
                dow = days[4];
                break;
            case Calendar.FRIDAY:
                dow = days[5];
                break;
            case Calendar.SATURDAY:
                dow = days[6];
                break;
            default:
                break;
        }
        welcomeString.append(", ")
                .append("Skylight Super Admin")
                .append("How are you? ")
                .append(getString(R.string.happy))
                .append(dow);


        setupHeader();


    }


    public void showDrawerButton() {

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();
    }

    public void showUpButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void setupDrawerListener() {
        drawer = findViewById(R.id.super_drawer);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

    private void setupHeader() {

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_super);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.super_drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Super Admin BackOffice");

        View headerView = navigationView.getHeaderView(0);

        DBHelper applicationDb = new DBHelper(this);
        if(superAdminProfile !=null){
            profileID= superAdminProfile.getUserID();
            Bitmap bitmap = applicationDb.getProfilePicture(profileID);
            CircleImageView imgProfilePic = headerView.findViewById(R.id.profile_image_cus);
            imgProfilePic.setImageBitmap(bitmap);
        }




    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setSingleEvent(GridLayout gridLayout) {
        for(int i = 0; i<gridLayout.getChildCount();i++){
            CardView cardView=(CardView)gridLayout.getChildAt(i);
            final int finalI= i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(finalI) {
                        case 0:
                            Intent myIntent = new Intent(SuperAdminOffice.this, AdminPackageActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntent);
                            break;
                        case 1:
                            Intent myIntent1 = new Intent(SuperAdminOffice.this, AdminTabActivity.class);
                            myIntent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntent1);
                            break;
                        case 2:
                            Intent myIntent2 = new Intent(SuperAdminOffice.this, AdminSupportAct.class);
                            myIntent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntent2);
                            break;
                        case 3:
                            Intent myIntent3 = new Intent(SuperAdminOffice.this, BirthdayTab.class);
                            myIntent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntent3);
                            break;
                        case 4:
                            Intent myIntent4 = new Intent(SuperAdminOffice.this, CustomerListActivity.class);
                            myIntent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntent4);
                            break;
                        case 5:
                            Intent myIntent5 = new Intent(SuperAdminOffice.this, SendUserMessageAct.class);
                            myIntent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntent5);
                            break;
                        case 6:
                            Intent myWeb = new Intent(SuperAdminOffice.this, SkylightAllWeb.class);
                            myWeb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myWeb);
                            break;
                        case 7:
                            Intent myIntentTeller = new Intent(SuperAdminOffice.this, CreationTab.class);
                            myIntentTeller.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntentTeller);
                            break;
                        case 8:
                            Intent myIntentTeller4 = new Intent(SuperAdminOffice.this, TellerReportActSuper.class);
                            myIntentTeller4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntentTeller4);
                            break;
                        case 9:
                            Intent myIntentTeller7 = new Intent(SuperAdminOffice.this, SkylightSliderAct.class);
                            myIntentTeller7.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntentTeller7);
                            break;

                        case 10:
                            Intent myIntentSper = new Intent(SuperAdminOffice.this, SuperAdminCountAct.class);
                            myIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(myIntentSper);
                            break;

                        case 11:
                            Intent listIntentSper = new Intent(SuperAdminOffice.this, SuperMPaymentListA.class);
                            listIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(listIntentSper);
                            break;

                        case 12:
                            Intent unConfirmedIntentSper = new Intent(SuperAdminOffice.this, SuperUnconfirmedSavings.class);
                            unConfirmedIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(unConfirmedIntentSper);
                            break;

                        case 13:
                            Intent emergencyIntentSper = new Intent(SuperAdminOffice.this, SuperEmergList.class);
                            emergencyIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(emergencyIntentSper);
                            break;
                        case 14:
                            Intent dueIntentSper = new Intent(SuperAdminOffice.this, DuePackagesAct.class);
                            dueIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dueIntentSper);
                            break;
                        case 15:
                            Intent acctantIntentSper = new Intent(SuperAdminOffice.this, AcctantBackOffice.class);
                            acctantIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(acctantIntentSper);
                            break;

                        case 16:
                            Intent updateIntent = new Intent(SuperAdminOffice.this, AdminRepostingAct.class);
                            updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(updateIntent);
                            break;
                        case 17:
                            Intent stockIntent = new Intent(SuperAdminOffice.this, SuperInvTab.class);
                            stockIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(stockIntent);
                            break;

                        case 18:
                            Intent depositIntent = new Intent(SuperAdminOffice.this, ADepositList.class);
                            depositIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(depositIntent);
                            break;

                        case 19:
                            Intent officeIntent = new Intent(SuperAdminOffice.this, StocksTab.class);
                            officeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(officeIntent);
                            break;

                        case 20:
                            Intent cusIntent = new Intent(SuperAdminOffice.this, CusByPackAct.class);
                            cusIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(cusIntent);
                            break;
                        case 21:
                            Intent payoutIntent = new Intent(SuperAdminOffice.this, PayOutRequestList.class);
                            payoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(payoutIntent);
                            break;

                        case 22:
                            Intent chartIntent = new Intent(SuperAdminOffice.this, ChartData.class);
                            chartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(chartIntent);
                            break;



                        default:
                    }
                    Toast.makeText(SuperAdminOffice.this,"Clicked at index "+ finalI,
                            Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.super_main_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dashboardSuper:
                Intent mainIntent = new Intent(this, SuperAdminOffice.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                return true;
            case R.id.timeline_Super:
                Intent timelineIntent = new Intent(this, AdminTimelineAct.class);
                timelineIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(timelineIntent);
                return true;

            case R.id.nav_SignupSuper:
                Intent active = new Intent(SuperAdminOffice.this, SuperUserCreator.class);
                startActivity(active);
                return true;
            case R.id.nav_my_packageSuper:
                Intent pIntent = new Intent(this, AdminPackageActivity.class);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pIntent);
                return true;


            case R.id.my_SuperTrans:
                Intent tIntent = new Intent(this, AdminTransActivity.class);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tIntent);
                return true;
            case R.id.nav_TellerReportsSuper:
                Intent supportInt = new Intent(SuperAdminOffice.this, TellerReportActSuper.class);
                startActivity(supportInt);
                return true;

            case R.id.nav_logoutSuper:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_dashboardSuper:

                Intent intent = new Intent(SuperAdminOffice.this, SuperAdminOffice.class);
                startActivity(intent);
                break;
            case R.id.timeline_Super:
                Intent timelineIntent = new Intent(this, AdminTimelineAct.class);
                timelineIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(timelineIntent);
                break;
            case R.id.nav_SignupSuper:
                Intent active = new Intent(SuperAdminOffice.this, SuperUserCreator.class);
                startActivity(active);
                break;
            case R.id.nav_my_packageSuper:
                Intent pIntent = new Intent(this, AdminPackageActivity.class);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pIntent);
                break;

            case R.id.my_SuperTrans:
                Intent tIntent = new Intent(this, AdminTransActivity.class);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tIntent);
                break;

            case R.id.nav_TellerReportsSuper:
                Intent supportInt = new Intent(SuperAdminOffice.this, TellerReportActSuper.class);
                startActivity(supportInt);
                break;
            case R.id.nav_ImpDateSuper:
                Intent aIntent = new Intent(this, SODueDateListAct.class);
                aIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(aIntent);
                break;
            case R.id.nav_soSuper:
                Intent so1Intent = new Intent(this, AdminSOTabAct.class);
                so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(so1Intent);
                break;
            case R.id.nav_superLoans:
                Intent loanIntent = new Intent(this, AdminLoanList.class);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loanIntent);
                break;

            case R.id.nav_payClientSuper:
                Intent payNowIntent = new Intent(this, PayClientActivity.class);
                payNowIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(payNowIntent);
                break;

            case R.id.navsupportSuper:
                Intent sIntent = new Intent(this, AdminSupportAct.class);
                sIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sIntent);
                break;

            case R.id.nav_UsersSuper:
                Intent uIntent = new Intent(this, SkylightUsersActivity.class);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(uIntent);
                break;


            case R.id.nav_logoutSuper:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(SuperAdminOffice.this, SignTabMainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }
        return true;

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.standingOrder3:
                Intent active = new Intent(SuperAdminOffice.this, AdminSOTabAct.class);
                active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(active);
                break;
            case R.id.ImportantDate:
                Intent history = new Intent(SuperAdminOffice.this, ImportDateTab.class);
                history.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(history);
                break;
            case R.id.superTimelinesA:
                Intent timelineIntent = new Intent(SuperAdminOffice.this, AdminTimelineAct.class);
                timelineIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(timelineIntent);
                break;

        }


    }

    public void standingOrdersSuper(View view) {
        Intent so1Intent = new Intent(this, AdminSOTabAct.class);
        so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(so1Intent);
    }

    public void importantDateSuper(View view) {
        Intent iIntent = new Intent(this, ImportDateTab.class);
        iIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(iIntent);
    }

    public void TimelinesSuper(View view) {
        Intent timelineIntent = new Intent(this, AdminTimelineAct.class);
        timelineIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(timelineIntent);
    }

    public void goLoans(View view) {
        Intent loanIntent = new Intent(this, AdminLoanList.class);
        loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loanIntent);
    }

    public void goSoSuper(View view) {
        Intent so1Intent = new Intent(this, AdminSOTabAct.class);
        so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(so1Intent);
    }

    public void gpPackages(View view) {
        Intent pIntent = new Intent(this, AdminTabActivity.class);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pIntent);
    }
}