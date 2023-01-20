package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.CusAdaptSuper;
import com.skylightapp.BlockedUserAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.MarketClasses.MarketBizPackAd;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Markets.SendCusMessAct;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;
import com.skylightapp.UnBlockUserAct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CusByPackAct extends AppCompatActivity implements  CusAdaptSuper.CustomerListener{
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView TxtPaymentCounts;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    PrefManager prefManager;
    private Gson gson1,gson2;
    private String json,json1,json2,SharedPrefSuperUser;
    private Button home, settings;

    private RecyclerView recyclerViewNoPacks, recyclerAllCus, recyclerBlockedCus, recyclerThisMCus, recyclerTodayCus, recyclerPackageCus;

    //private ArrayList<Customer> noPackCus;
    private ArrayList<Customer> cusByJoinDate;
    private ArrayList<Customer> allCustomers;
    private ArrayList<Customer> thisMonthCus;
    private ArrayList<Customer> blockedCus;
    private ArrayList<Customer> sByPTypeCustomers;
    //private CusAdaptSuper cusNoPackAdapt;
    private CusAdaptSuper cusThisMonthAdapt;
    private CusAdaptSuper cusAllCusAdapter;
    private CusAdaptSuper cusBlockedAdapter;
    private CusAdaptSuper cusByDateAdapter;
    private CusAdaptSuper cusByPackTypeAdapter;
    private DBHelper dbHelper;
    private Profile profile;
    private Customer customer;
    String cusUserName,cusUserPassword,userRole;
    int profileID;
    long bizID;
    private AppCompatButton btnSearch;
    private SearchView searchView;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;

    CusDAO cusDAO;
    PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private Uri pictureLink;
    private MarketBusiness marketBusiness;
    private UserSuperAdmin superAdmin;
    private ArrayList<MarketBizPackage>marketBizPackages;
    private AppCompatSpinner spinnerPackType;
    private MarketBizPackAd marketBizPackAd;
    private MarketBizPackage marketBizPackage;
    private Awajima awajima;
    private int spnIndex=0;
    String selectedPackageType, office,state,role,surname,dbRole,firstName,joinedDate,password, email,phoneNO, dob,gender,address;

    String SharedPrefUserPassword,userName,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;
    private Calendar calendar;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_by_pack);
        marketBizPackage= new MarketBizPackage();
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.AllPayments);
        //recyclerViewNoPacks = findViewById(R.id.recycler_cusNoPack);
        spinnerPackType = findViewById(R.id.spn_packs_type);
        recyclerAllCus = findViewById(R.id.recycler_All_cus);
        recyclerBlockedCus = findViewById(R.id.recycler_Blocked_Cus);
        recyclerThisMCus = findViewById(R.id.recycler_this_month_Cus);
        recyclerTodayCus = findViewById(R.id.recycler_cus_today);
        recyclerPackageCus = findViewById(R.id.recycler_packs_cus);
        customer= new Customer();
        awajima= new Awajima();
        dbHelper= new DBHelper(this);
        superAdmin= new UserSuperAdmin();
        marketBizPackages= new ArrayList<>();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        profile= new Profile();
        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
        }
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        profileID = userPreferences.getInt("PROFILE_ID", 0);
        userName = userPreferences.getString("PROFILE_USERNAME", "");
        office = userPreferences.getString("PROFILE_OFFICE", "");
        state = userPreferences.getString("PROFILE_STATE", "");
        role = userPreferences.getString("PROFILE_ROLE", "");
        password = userPreferences.getString("PROFILE_PASSWORD", "");
        joinedDate = userPreferences.getString("PROFILE_DATE_JOINED", "");
        surname = userPreferences.getString("PROFILE_SURNAME", "");
        email = userPreferences.getString("PROFILE_EMAIL", "");
        phoneNO = userPreferences.getString("PROFILE_PHONE", "");
        firstName = userPreferences.getString("PROFILE_FIRSTNAME", "");
        dob = userPreferences.getString("PROFILE_DOB", "");
        gender = userPreferences.getString("PROFILE_GENDER", "");
        address = userPreferences.getString("PROFILE_ADDRESS", "");
        pictureLink = Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        json = userPreferences.getString("LastSuperAdminUsed", "");
        json1 = userPreferences.getString("LastProfileUsed", "");
        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        superAdmin = gson.fromJson(json, UserSuperAdmin.class);
        userProfile = gson1.fromJson(json1, Profile.class);
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        cusDAO= new CusDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);
        calendar = Calendar.getInstance();
        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        todayDate = mdformat.format(calendar.getTime());
        if(marketBusiness !=null){
            marketBizPackages=marketBusiness.getMarketBizPackages();
        }
        marketBizPackAd = new MarketBizPackAd(CusByPackAct.this, R.layout.package_array_item,marketBizPackages);
        spinnerPackType.setAdapter(marketBizPackAd);
        spinnerPackType.setSelection(0);

        spinnerPackType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;
                }else {
                    marketBizPackage = (MarketBizPackage) parent.getSelectedItem();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(marketBizPackage !=null){
            selectedPackageType=marketBizPackage.getPackageType();
        }

        //noPackCus = new ArrayList<Customer>();
        cusByJoinDate = new ArrayList<Customer>();
        allCustomers = new ArrayList<Customer>();
        thisMonthCus = new ArrayList<Customer>();
        blockedCus = new ArrayList<Customer>();
        sByPTypeCustomers = new ArrayList<Customer>();
        dbHelper=new DBHelper(this);
        if(cusDAO !=null){
            try {
                try {
                    cusByJoinDate = cusDAO.getCustomersForBizToday(bizID,todayDate);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            try {
                try {
                    thisMonthCus =cusDAO.getCustomersForBizJoinedThisYear(bizID,todayDate);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            try {
                try {
                    allCustomers = cusDAO.getCustomerFromBiz(bizID);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            try {
                try {
                    blockedCus =cusDAO.getCustomersBlockedForBiz(bizID,"blocked");

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            try {
                try {
                    sByPTypeCustomers =cusDAO.getAllCusForSo();

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            //noPackCus = cusDAO.getCusWithoutPackage();




        }

       // cusNoPackAdapt = new CusAdaptSuper(CusByPackAct.this,noPackCus);
        cusThisMonthAdapt = new CusAdaptSuper(CusByPackAct.this, thisMonthCus);
        cusAllCusAdapter = new CusAdaptSuper(CusByPackAct.this, allCustomers);
        cusBlockedAdapter = new CusAdaptSuper(CusByPackAct.this, blockedCus);
        cusByDateAdapter = new CusAdaptSuper(CusByPackAct.this, cusByJoinDate);
        cusByPackTypeAdapter = new CusAdaptSuper(CusByPackAct.this, sByPTypeCustomers);


        /*LinearLayoutManager layoutManagerNo = new LinearLayoutManager(CusByPackAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNoPacks.setLayoutManager(layoutManagerNo);
        recyclerViewNoPacks.setAdapter(cusNoPackAdapt);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewNoPacks.getContext(),
                layoutManagerNo.getOrientation());
        recyclerViewNoPacks.addItemDecoration(dividerItemDecoration7);*/




        LinearLayoutManager layoutManagerItems
                = new LinearLayoutManager(CusByPackAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerAllCus.setLayoutManager(layoutManagerItems);
        recyclerAllCus.setItemAnimator(new DefaultItemAnimator());
        recyclerAllCus.setAdapter(cusAllCusAdapter);
        DividerItemDecoration dividerItemDecorationItems = new DividerItemDecoration(recyclerAllCus.getContext(),
                layoutManagerItems.getOrientation());
        recyclerAllCus.addItemDecoration(dividerItemDecorationItems);
        recyclerViewNoPacks.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerAllCus);


        LinearLayoutManager layoutManagerSavings
                = new LinearLayoutManager(CusByPackAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerPackageCus.setLayoutManager(layoutManagerSavings);
        recyclerPackageCus.setItemAnimator(new DefaultItemAnimator());
        recyclerPackageCus.setAdapter(cusByDateAdapter);
        DividerItemDecoration dividerItemDecorationSavings = new DividerItemDecoration(recyclerPackageCus.getContext(),
                layoutManagerSavings.getOrientation());
        recyclerPackageCus.addItemDecoration(dividerItemDecorationSavings);
        recyclerAllCus.setNestedScrollingEnabled(false);


        LinearLayoutManager layoutManagerInv
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerBlockedCus.setLayoutManager(layoutManagerInv);
        recyclerBlockedCus.setAdapter(cusThisMonthAdapt);
        DividerItemDecoration dividerItemDecorationInv = new DividerItemDecoration(recyclerBlockedCus.getContext(),
                layoutManagerInv.getOrientation());
        recyclerBlockedCus.addItemDecoration(dividerItemDecorationInv);



        LinearLayoutManager layoutManagerPromo
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerThisMCus.setLayoutManager(layoutManagerPromo);
        recyclerThisMCus.setAdapter(cusBlockedAdapter);
        DividerItemDecoration dividerItemDecorationPromo = new DividerItemDecoration(recyclerThisMCus.getContext(),
                layoutManagerPromo.getOrientation());
        recyclerThisMCus.addItemDecoration(dividerItemDecorationPromo);


        LinearLayoutManager layoutManagerSO
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerTodayCus.setLayoutManager(layoutManagerSO);
        recyclerTodayCus.setAdapter(cusByPackTypeAdapter);
        DividerItemDecoration dividerItemDecorationSO = new DividerItemDecoration(recyclerTodayCus.getContext(),
                layoutManagerSO.getOrientation());
        recyclerTodayCus.addItemDecoration(dividerItemDecorationSO);



    }

    @Override
    public void onItemClick(Customer customer) {
        dbHelper=new DBHelper(this);
        if(customer !=null){
            cusUserName=customer.getCusUserName();
            cusUserPassword=customer.getCusPassword();
            profile=customer.getCusProfile();

        }
        if(profileDao !=null){

            try {
                try {
                    userRole=profileDao.getProfileRoleByUserNameAndPassword(cusUserName,cusUserPassword);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }


        if(userRole !=null) {
            if (userRole.equalsIgnoreCase("BlockedUser")) {
                Intent tellerIntent = new Intent(this, UnBlockUserAct.class);
                tellerIntent.putExtra("PROFILE_ID", profileID);
                tellerIntent.putExtra("PROFILE_USERNAME", cusUserName);
                tellerIntent.putExtra("PROFILE_PASSWORD", cusUserPassword);
            } else {

            }
        }


        Bundle messageBundle= new Bundle();
        messageBundle.putParcelable("Customer", customer);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Next Action");
        String[] options = {"Send Customer Message", "Block Customer"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(CusByPackAct.this, SendCusMessAct.class);
                        intent.putExtras(messageBundle);
                        startActivity(intent);
                    case 1:
                        Intent blockIntent = new Intent(CusByPackAct.this, BlockedUserAct.class);
                        blockIntent.putExtras(messageBundle);
                        startActivity(blockIntent);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.super_cus_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchCus)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cusAllCusAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                cusAllCusAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchCus) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
            return;
        }
        super.onBackPressed();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}