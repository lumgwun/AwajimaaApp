package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.firebase.ui.auth.ui.phone.SpacedEditText;
import com.google.gson.Gson;
import com.skylightapp.Adapters.MySkylightCashA;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.AppCash;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MyCashList extends AppCompatActivity implements MyTellerCashAdapter.OnItemClickListener{
    Bundle bundle;
    AppCash appCash;
    private AppCompatButton btnRunUpdate;
    DatePicker picker;
    String selectedStatus,dateOfApproval,superAdminName,tellerConfirmationCode, officeBranch;
    int selectedDepositIndex;
    SpacedEditText edtCode;
    DBHelper dbHelper;
    long tellerCashID;
    long code;
    long tellerCashCode;
    int profileID;
    TextView txtDepositID;
    Bundle userBundle;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;
    Profile managerProfile;
    Gson gson,gson1;
    String json,json1,dateOfCash;
    Profile userProfile;
    String machine;
    private CustomerManager customerManager;
    private UserSuperAdmin superAdmin;
    private AdminUser adminUser;
    private RecyclerView recyclerViewToday,recyclerViewCustomDate,recyclerViewAll,recyclerMyTellerCash;
    private MySkylightCashA adapterToday;
    private MySkylightCashA adapterDate;
    private MySkylightCashA adapterAll;
    private ArrayList<AppCash> appCashArrayListToday;
    private ArrayList<AppCash> appCashArrayDate;
    private ArrayList<TellerCash> tellerCashArrayList;
    private ArrayList<AppCash> appCashArrayAll;
    private AppCompatButton btnByDate;
    MyTellerCashAdapter myTellerCashAdapter;
    TextView txtTotalSCForDate,txtTotalSCForToday,txtTotalSCTotal;
    double totalSCForDate,totalSCForToday,totalSC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_cash_list);
        appCash = new AppCash();
        appCashArrayDate =new ArrayList<>();
        appCashArrayListToday =new ArrayList<>();
        appCashArrayAll =new ArrayList<>();
        tellerCashArrayList =new ArrayList<>();
        gson = new Gson();
        gson1 = new Gson();
        txtTotalSCForDate = findViewById(R.id.CashD);
        txtTotalSCTotal = findViewById(R.id.txtTotalTC);
        txtTotalSCForToday = findViewById(R.id.cashToday);
        recyclerViewCustomDate = findViewById(R.id.recyclerTellerCashD);
        recyclerViewToday = findViewById(R.id.recyclerAllToday);
        recyclerViewAll = findViewById(R.id.recyclerTCAll);
        recyclerMyTellerCash = findViewById(R.id.recyclerTCMy);

        picker = findViewById(R.id.sc_date_picker);
        btnByDate = findViewById(R.id.buttonCashDate);
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        managerProfile= new Profile();
        customerManager=new CustomerManager();
        dbHelper= new DBHelper(this);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
        userBundle= new Bundle();

        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfCash);
            }
        });

        dateOfCash = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(calendar1.getTime());
        if(dateOfCash ==null){
            dateOfCash=todayDate;
        }
        appCashArrayListToday =dbHelper.getSkylightCashForProfileAtDate(profileID,todayDate);
        appCashArrayDate =dbHelper.getSkylightCashForProfileAtDate(profileID,dateOfCash);
        appCashArrayAll =dbHelper.getAllSkylightCashForProfile(profileID);
        totalSCForDate =dbHelper.getSkylightCashTotalForProfileAndDate(profileID,dateOfCash);
        totalSCForToday =dbHelper.getSkylightCashTotalForProfileAndDate(profileID,todayDate);
        TCashDAO tCashDAO= new TCashDAO(this);
        totalSC=dbHelper.getSkylightCashTotalForProfile(profileID);
        tellerCashArrayList=tCashDAO.getTellerCashForTeller(profileID);

        if(totalSC >0){
            txtTotalSCTotal.setText("Total  N:"+ totalSC);

        }else{
            if(totalSC ==0){
                txtTotalSCTotal.setText("Oops! no Skylight Cash for this profile");

            }

        }
        if(totalSCForToday >0){
            txtTotalSCForToday.setText("Today Cash  N:"+ totalSCForToday);

        }else{
            if(totalSCForToday ==0){
                txtTotalSCForToday.setText("Oops! no Skylight Cash for Today");

            }

        }
        if(totalSCForDate >0){
            txtTotalSCForDate.setText("Selected Date Cash  N:"+ totalSCForDate);

        }else{
            if(totalSCForDate ==0){
                txtTotalSCForDate.setText("Oops! no Skylight Cash for the Date");

            }

        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(MyCashList.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerMyTellerCash.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerMyTellerCash.getContext(),
                layoutManager.getOrientation());
        recyclerMyTellerCash.addItemDecoration(dividerItemDecoration);
        recyclerMyTellerCash.setItemAnimator(new DefaultItemAnimator());
        myTellerCashAdapter = new MyTellerCashAdapter(MyCashList.this,tellerCashArrayList);
        //recyclerMyTellerCash.setHasFixedSize(true);
        recyclerMyTellerCash.setAdapter(myTellerCashAdapter);
        myTellerCashAdapter.setOnItemClickListener(this);


        adapterToday = new MySkylightCashA(this, appCashArrayListToday);
        LinearLayoutManager linearLayoutManagerT = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManagerT);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(adapterToday);
        recyclerViewToday.setNestedScrollingEnabled(false);
        recyclerViewToday.setClickable(true);



        adapterAll = new MySkylightCashA(this, appCashArrayAll);
        LinearLayoutManager linearLayoutManagerAll = new LinearLayoutManager(this);
        recyclerViewAll.setLayoutManager(linearLayoutManagerAll);
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.setNestedScrollingEnabled(false);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewAll);
        recyclerViewAll.setClickable(true);
        btnByDate.setOnClickListener(this::getTCByDate);

        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterDate = new MySkylightCashA(MyCashList.this, appCashArrayDate);
                recyclerViewCustomDate.setLayoutManager(new LinearLayoutManager(MyCashList.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
                recyclerViewCustomDate.setAdapter(adapterDate);
                recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(MyCashList.this,DividerItemDecoration.VERTICAL));
                recyclerViewCustomDate.setNestedScrollingEnabled(false);
                recyclerViewCustomDate.setClickable(true);

            }
        });
    }
    private void chooseDate(String dateOfCash) {
        dateOfCash = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();


    }

    public void getTCByDate(View view) {
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onShowItemClick(int position) {

    }

    @Override
    public void onDeleteItemClick(int position) {

    }
}