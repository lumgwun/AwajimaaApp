package com.skylightapp.Customers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.gridlayout.widget.GridLayout;

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
import android.widget.AdapterView;


import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Adapters.AccountAdapter;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.CameraActivity;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.LoginActivity;
import com.skylightapp.Markets.MarketChatTab;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.MyGrpSavingsTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.PasswordRecovAct;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SkylightSliderAct;
import com.skylightapp.MapAndLoc.UserReportEmergAct;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineAct;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewCustomerDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static final String KEY = "NewCustomerDrawer.KEY";
    GridLayout maingrid;
    private SharedPreferences userPreferences;
    private Gson gson,gson1;
    private String json,json1;
    private LinearLayout linearLayout;

    private AppCompatImageView imgTime;

    private Profile userProfile;
    private int profileID;
    float yValues[]={10,20,30,0,40,60};
    CardView cardViewPackges,cardViewGrpSavings,cardViewHistory, cardViewStandingOrders, cardViewOrders, cardViewSupport;

    AppCompatTextView extName, textID,textAcctNo,textBalance,textSavings;
    CircleImageView profileImage;
    private Profile profile;

    private AppCompatTextView txtBankName, balance, sPackages, accountNo,txtSO,  txtUserName;
    private Customer customer;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Account account;
    private ActionBarDrawerToggle toggle;
    private String surname,firstName,names,cusUserName;
    ImageButton hideLayouts;
    private int accountN, savings,skPackages;
    private double accountBalance;
    CircleImageView imgProfilePic;
    private  int SOCount,customerID,loanCount,txCount;
    private StandingOrderAcct standingOrderAcct;
    private static final String PREF_NAME = "awajima";
    AppCompatButton btnToPacks;
    private Bundle homeBundle;
    private Spinner spnAccounts;
    AccountAdapter accountArrayAdapter;
    private ArrayList<Account> accountArrayList;
    private AppCompatTextView txtBankAcctBalance;
    ChipNavigationBar chipNavigationBar;
    private Currency currency;
    private String currencySymbol;
    private List<Country> countries;
    private List<Currency> currencies;
    private World world;
    private CurrAdapter currencyAdapter;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;
    ActivityResultLauncher<Intent> startCusPictureActivityForResult = registerForActivityResult(
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
                            Toast.makeText(NewCustomerDrawer.this, "successful ", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(NewCustomerDrawer.this, "cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_cus_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar_cus);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        World.init(this);
        homeBundle= new Bundle();
        countries=new ArrayList<>();
        accountArrayList= new ArrayList<>();
        gson1 = new Gson();
        countries=World.getAllCountries();
        currencies= new ArrayList<>();
        //currency= new Currency();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        homeBundle=getIntent().getExtras();
        standingOrderAcct= new StandingOrderAcct();
        btnToPacks = findViewById(R.id.cust_PackBtn);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        txtUserName = findViewById(R.id.cus_username);
        txtBankAcctBalance = findViewById(R.id._BankBalance4);
        spnAccounts = findViewById(R.id.spnA);
        txtSO = findViewById(R.id.savingsToday);
        extName = findViewById(R.id.cus_name3);
        textID = findViewById(R.id.cus_id2);
        textAcctNo = findViewById(R.id.allCus_Super);
        textBalance = findViewById(R.id.balance_normalCus);
        textSavings = findViewById(R.id.savingsCus);
        txtBankName = findViewById(R.id.cus_BankN);
        balance = findViewById(R.id.cus_BankBalance4444);
        accountNo = findViewById(R.id.cus_BankNo33);

        imgProfilePic = findViewById(R.id.profile_image_cus);
        cardViewPackges=findViewById(R.id.cardCusPackages);
        cardViewHistory=findViewById(R.id.cardHistoryCus);
        cardViewGrpSavings=findViewById(R.id.cardGrpSavings);
        cardViewStandingOrders =findViewById(R.id.cardStandingOrdersCus);
        cardViewOrders =findViewById(R.id.cardOrdersCus);
        cardViewSupport =findViewById(R.id.cardCusHelp);
        linearLayout=findViewById(R.id.linearGrid);
        maingrid=(GridLayout) findViewById(R.id.ViewCus);
        imgTime = findViewById(R.id.cusGreetings);

        //currencies=World.getAllCurrencies();

        if(userProfile ==null){
            if(homeBundle !=null){
                userProfile=homeBundle.getParcelable("Profile");

            }
            if (userProfile != null) {
                accountArrayList=userProfile.getProfileAccounts();
            }
            accountArrayAdapter = new AccountAdapter(NewCustomerDrawer.this, android.R.layout.simple_spinner_item, accountArrayList);
            accountArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnAccounts.setAdapter(accountArrayAdapter);
            spnAccounts.setSelection(accountArrayAdapter.getPosition(account));

        }


        spnAccounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                account = (Account) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(account !=null){
            currency=account.getCurrency();

        }
        if(currency !=null){
            currencySymbol=currency.getSymbol();
        }
        if(account !=null){
            txtBankAcctBalance.setText(""+currencySymbol+account.getAccountBalance());

        }



        chipNavigationBar = findViewById(R.id.bottom_nav_barC);
        chipNavigationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.cHome:
                                Intent myIntent = new Intent(NewCustomerDrawer.this, NewCustomerDrawer.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.cTimeLine:

                                Intent chat = new Intent(NewCustomerDrawer.this, MyTimelineAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.cGeneralShop:

                                Intent shop = new Intent(NewCustomerDrawer.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.cPackageT:

                                Intent pIntent = new Intent(NewCustomerDrawer.this, PackListTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.cSupport:
                                Intent helpIntent = new Intent(NewCustomerDrawer.this, CustomerHelpActTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(helpIntent);
                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });



        ProfDAO applicationDb = new ProfDAO(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
            Bitmap bitmap = applicationDb.getProfilePicture(profileID);
            imgProfilePic.setImageBitmap(bitmap);
            names=surname+","+ firstName;

            extName.setText("Welcome"+""+names);

        }
        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCusPictureActivityForResult.launch(new Intent(NewCustomerDrawer.this, CameraActivity.class));

            }
        });
        if(customer !=null){
            surname= customer.getCusSurname();
            firstName= customer.getCusFirstName();

            account=customer.getCusAccount();
            standingOrderAcct=customer.getCusStandingOrderAcct();
            if(account !=null){
                accountBalance = account.getAccountBalance();
                accountN = account.getAwajimaAcctNo();

            }
            String nBank=customer.getCusBank();
            double nBankB=customer.getCusBankBalance();
            String nBankN=customer.getCusBankAcctNo();
            txtBankName.setText(MessageFormat.format("Bank :{0}", nBank));
            balance.setText(MessageFormat.format("Balance :{0}", nBankB));
            accountNo.setText(MessageFormat.format("Acct :{0}", nBankN));

            savings = customer.getCusDailyReports().size();
            skPackages = customer.getCusSkyLightPackages().size();
            SOCount= customer.getCusStandingOrders().size();
            if(SOCount>0){
                txtSO.setText("Standing Orders:"+SOCount);

            }else if(SOCount==0){
                txtSO.setText("No Standing Orders, yet");

            }

            if(accountBalance>0){
                balance.setText(MessageFormat.format("NGN{0}", accountBalance));

            }if (accountBalance <0) {
                balance.setText(MessageFormat.format("NGN-{0}", accountBalance));

            }else if(accountBalance==0){
                balance.setText("NGN" + "0.00");

            }

            accountNo.setText(MessageFormat.format("E-wallet No{0}", accountN));
            if(skPackages>0){
                sPackages.setText(MessageFormat.format("No of Packages{0}", skPackages));
            }
            if (skPackages == 0) {
                sPackages.setText("You do not have any packs yet");

            }


        }

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_cus2);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.cus_drawer2);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Customer BackOffice");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__category);



        setSingleEvent(maingrid);
        StringBuilder welcomeString = new StringBuilder();


        Calendar calendar = Calendar.getInstance();

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
                .append("Awajima Customer")
                .append("How are you? ")
                .append(getString(R.string.happy))
                .append(dow);


        setupHeader();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_bottom_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home_customer:
                Intent mainIntent = new Intent(this, NewCustomerDrawer.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(mainIntent);
                return true;
            case R.id.action_profile_customer:
                Intent pIntent = new Intent(this, UserPrefActivity.class);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(pIntent);
                return true;

            case R.id.action_notification_customer:
                Intent p1Intent = new Intent(this, UserTimeLineAct.class);
                p1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(p1Intent);
                return true;
            case R.id.my_subscriptions:
                Intent so1Intent = new Intent(this, PackageTab.class);
                so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(so1Intent);
                return true;
            case R.id.my_packs:
                Intent timelineIntent = new Intent(this, PackListTab.class);
                timelineIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(timelineIntent);
                return true;
            case R.id.utility:
                Intent cIntent = new Intent(this, CustUtilTab.class);
                cIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(cIntent);
                return true;

            case R.id.orderTab:
                Intent uIntent = new Intent(this, CusOrderTab.class);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(uIntent);
                return true;
            case R.id.loanTab:
                Intent loanIntent = new Intent(this, CusLoanTab.class);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(loanIntent);
                return true;

            case R.id.action_help_customer:
                Intent payNowIntent = new Intent(this, CustomerHelpActTab.class);
                payNowIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(payNowIntent);
                return true;



            case R.id.so_menu:
                Intent grpIntent = new Intent(this, SOTab.class);
                grpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(grpIntent);
                return true;


            case R.id.package_slider:
                Intent tIntent = new Intent(this, SkylightSliderAct.class);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(tIntent);
                return true;

            case R.id.all_new:
                Intent wIntent = new Intent(this, NewPackCusAct.class);
                wIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(wIntent);
                return true;


            case R.id.privacy_policy_customer:
                Intent w1Intent = new Intent(this, PrivacyPolicy_Web.class);
                w1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(w1Intent);
                return true;
            case R.id.admin_pswd2:
                Intent impIntent = new Intent(this, PasswordRecovAct.class);
                impIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(impIntent);
                return true;

            case R.id.action_customer_logout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        drawer = findViewById(R.id.cus_drawer2);
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

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_cus2);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.cus_drawer2);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Customer BackOffice");

        View headerView = navigationView.getHeaderView(0);
        btnToPacks = headerView.findViewById(R.id.cust_PackBtn);
        btnToPacks.setOnClickListener(this::getMyPacks);
        btnToPacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent so1Intent = new Intent(NewCustomerDrawer.this, PackageTab.class);
                so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(so1Intent);

            }
        });
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);

        ProfDAO applicationDb = new ProfDAO(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
            Bitmap bitmap = applicationDb.getProfilePicture(profileID);
            CircleImageView imgProfilePic = headerView.findViewById(R.id.profile_image_cus);
            imgProfilePic.setImageBitmap(bitmap);
        }
        Currency currency=null;
        String acctCurrency=null;
        if(customer !=null){
            surname= customer.getCusSurname();
            firstName= customer.getCusFirstName();
            customerID=customer.getCusUID();
            cusUserName=customer.getCusUserName();

            account=customer.getCusAccount();
            standingOrderAcct=customer.getCusStandingOrderAcct();
            if(account !=null){
                accountBalance = account.getAccountBalance();
                accountN = account.getAwajimaAcctNo();
                currency=account.getCurrency();


            }
            if(currency !=null){
                acctCurrency=currency.getSymbol();

            }else {
                acctCurrency="NGN";
            }
            if (accountBalance == 0.00) {
                balance.setText(acctCurrency + 0.00);

            }
            if(accountBalance>0){
                balance.setText(acctCurrency + accountBalance);
            }
            if(accountN>0){
                accountNo.setText("E-wallet No" + accountN);

            }


            savings = customer.getCusDailyReports().size();
            skPackages = customer.getCusSkyLightPackages().size();
            SOCount= customer.getCusStandingOrders().size();
            txCount = customer.getCusTransactions().size();
            loanCount = customer.getCusLoans().size();
            if(SOCount>0){
                txtSO.setText("Standing Orders:"+SOCount);

            }else if(SOCount==0){
                txtSO.setText("No Standing Orders, yet");

            }


            if(accountBalance>0){
                balance.setText(MessageFormat.format(acctCurrency, accountBalance));

            }if (accountBalance <0) {
                balance.setText(MessageFormat.format(acctCurrency, accountBalance));

            }else if(accountBalance==0){
                balance.setText(acctCurrency + "0.00");

            }

            accountNo.setText(MessageFormat.format("E-wallet No{0}", accountN));
            if(skPackages>0){
                sPackages.setText(MessageFormat.format("No of Packages{0}", skPackages));
            }
            if (skPackages == 0) {
                sPackages.setText("You do not have any packages yet");

            }
            if (savings == 0) {
                textSavings.setText("You dont have any savings yet");

            }

            //name = customerProfile.getFirstName() + " " + customerProfile.getSurname();
            textSavings.setText(MessageFormat.format("Saving:{0}", savings));
            textID.setText(MessageFormat.format("User Id:{0}", customerID));
            txtUserName.setText(MessageFormat.format("{0}{1}", cusUserName));


        }




    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void setSingleEvent(GridLayout maingrid)
    {
        for(int i=0;i<maingrid.getChildCount();i++)
        {
            CardView cardView=(CardView) maingrid.getChildAt(i);
            final int finalI=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent intent=new Intent(MainActivity.this,places.class);
                    String tosend="";
                    if(finalI==0)
                    {
                        tosend="Packages";
                        Intent intent=new Intent(NewCustomerDrawer.this, SkylightSliderAct.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==1)
                    {
                        tosend="History";
                        Intent intent=new Intent(NewCustomerDrawer.this, CusPacksAct.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==2)
                    {
                        tosend="Customer Pay";
                        Intent intent=new Intent(NewCustomerDrawer.this, CustomerPayAct.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==3) {
                        tosend = "Standing Orders";
                        Intent intent=new Intent(NewCustomerDrawer.this, SOTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==4) {
                        tosend = "Orders";
                        Intent intent=new Intent(NewCustomerDrawer.this, CusOrderTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==5) {
                        tosend = "My Package";
                        Intent intent=new Intent(NewCustomerDrawer.this, PackageTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==6) {
                        tosend = "My Sub. Packs";
                        Intent intent=new Intent(NewCustomerDrawer.this, CusSubPackTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==7) {
                        tosend = "Pack List";
                        Intent intent=new Intent(NewCustomerDrawer.this, PackListTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==8) {
                        tosend = "Items List";
                        Intent intent=new Intent(NewCustomerDrawer.this, CusStocksListAct.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==9) {
                        tosend = "Group Savings";
                        Intent intent=new Intent(NewCustomerDrawer.this, MyGrpSavingsTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==10) {
                        tosend = "My Sessions";
                        Intent intent=new Intent(NewCustomerDrawer.this, MarketChatTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==10) {
                        tosend = "Emergency Sessions";
                        Intent intent=new Intent(NewCustomerDrawer.this, UserReportEmergAct.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }


                }
            });
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_dashboard44:

                Intent intent = new Intent(NewCustomerDrawer.this, NewCustomerDrawer.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.nav_act_package44:
                Intent profile = new Intent(NewCustomerDrawer.this, SkylightSliderAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(profile);
                break;
            case R.id.nav_my_package44:
                Intent active = new Intent(NewCustomerDrawer.this, CusPacksAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                active.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(active);
                break;
            case R.id.nav_my_package44_payment:
                Intent history = new Intent(NewCustomerDrawer.this, CusPackForPayment.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                history.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                history.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(history);
                break;

            case R.id.timeline_cus24:
                Intent chat = new Intent(NewCustomerDrawer.this, MyTimelineAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
                break;

            case R.id.navsupport:
                Intent supportInt = new Intent(NewCustomerDrawer.this, CustomerHelpActTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(supportInt);
                break;
            case R.id.nav_pref:
                Intent intPref = new Intent(NewCustomerDrawer.this, UserPrefActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intPref.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intPref.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intPref);
                break;
            case R.id.nav_so0994:
                Intent intSO = new Intent(NewCustomerDrawer.this, SOTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intSO.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intSO.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intSO);
                break;
            case R.id.my_packageTab:
                Intent pIntent = new Intent(NewCustomerDrawer.this, PackageTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pIntent);
                break;

            case R.id.nav_loan6:
                Intent lIntent = new Intent(NewCustomerDrawer.this, CusLoanRepaymentAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lIntent);
                break;
            case R.id.my_Loan_Tab:
                Intent loanIntent = new Intent(NewCustomerDrawer.this, CusLoanTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loanIntent);
                break;
            case R.id.nav_Utils:
                Intent uIntent = new Intent(NewCustomerDrawer.this, CustUtilTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uIntent);
                break;


            case R.id.nav_logout4:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(NewCustomerDrawer.this, SignTabMainActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
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
            case R.id.btnStatementMy:
                Intent active = new Intent(NewCustomerDrawer.this, CusStatementAct.class);
                active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(active);
                break;
            case R.id.btnMyLoans:
                Intent loanTab = new Intent(NewCustomerDrawer.this, CusLoanTab.class);
                loanTab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loanTab);
                break;
            case R.id.buttonSupprt:
                Intent helpTab = new Intent(NewCustomerDrawer.this, CustomerHelpActTab.class);
                helpTab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(helpTab);
                break;

        }

    }

    public void getAcctStatement(View view) {
        Intent active = new Intent(NewCustomerDrawer.this, CusStatementAct.class);
        active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(active);
    }

    public void getMyLoansCus(View view) {
        Intent loanTab = new Intent(NewCustomerDrawer.this, CusLoanTab.class);
        loanTab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loanTab);
    }

    public void getMyMessages(View view) {
        Intent helpTab = new Intent(NewCustomerDrawer.this, CustomerHelpActTab.class);
        helpTab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(helpTab);
    }

    public void getMyPacks(View view) {
        Intent so1Intent = new Intent(NewCustomerDrawer.this, PackageTab.class);
        so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(so1Intent);
    }
}