package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.TellerReportAdapter;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyReportListAct extends AppCompatActivity {
    Context context = DailyReportListAct.this;

    private RecyclerView itemsRecycler, itemsRecyclerDate;

    private ArrayList<TellerReport> tellerReports;
    private ArrayList<TellerReport> tellerReportArrayList;

    private SearchView iSearchView;

    private SearchManager manager;

    private RecyclerView.LayoutManager itemLayoutManager;
    private TellerReportAdapter itemAdapter,reportAdapter;
    DBHelper dbHelper;
    private  Profile userProfile;
    CustomerManager teller;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1,SharedPrefSuperUser;
    private Date today;
    int tellerID;
    Date currentDate;
    DBHelper applicationDb;
    AppCompatTextView txtReportCount;
    int reportCount,reportCountAll;
    DatePicker picker;
    int profileID;
    protected DatePickerDialog datePickerDialog;
    private AppCompatButton btnSearchDB;
    private static final String PREF_NAME = "skylight";

    String SharedPrefUserPassword,dateOfReport,dateOfToday,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_daily_report_list);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        teller =new CustomerManager();
        tellerReportArrayList=new ArrayList<TellerReport>();
        tellerReports = new ArrayList<>();
        applicationDb = new DBHelper(this);
        txtReportCount =findViewById(R.id.txt_ReportCount);
        picker =findViewById(R.id.d_date_Report);
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefSuperUser=userPreferences.getString("Teller", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json1 = userPreferences.getString("LastProfileUsed", "");
        json = userPreferences.getString("LastTellerProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        teller = gson.fromJson(json, CustomerManager.class);
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        dateOfReport = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

        btnSearchDB = findViewById(R.id.buttonSearchReport);
        itemsRecyclerDate = (RecyclerView) findViewById(R.id.recyclerViewDaily);
        itemsRecycler = (RecyclerView) findViewById(R.id.recycler_viewReport);
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();


        try {
            tellerReportArrayList=dbHelper.getTellerReportsForDate(profileID,dateOfReport);

            tellerReports = dbHelper.getTellerReportForTeller(profileID);
            reportCountAll =dbHelper.getTellerReportCountAll(profileID);

        } catch (Exception e) {
            e.printStackTrace();
        }

        manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //itemsRecycler.setHasFixedSize(true);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(itemsRecycler);
        itemAdapter = new TellerReportAdapter(DailyReportListAct.this,tellerReports);
        itemsRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        itemsRecycler.setAdapter(itemAdapter);
        //itemAdapter.filterall(s.tostring());
        //generateObjects();
        getItemsFromSQLite();

        if(reportCountAll >0){
            txtReportCount.setText("My Reports:"+ reportCountAll);

        }else if(reportCountAll ==0){
            txtReportCount.setText("No Reports for Now");

        }

        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                itemsRecycler.setVisibility(View.GONE);
                reportCount=dbHelper.getTellerReportCountForDate(profileID,dateOfReport);
                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(DailyReportListAct.this, LinearLayoutManager.HORIZONTAL, false);
                itemsRecyclerDate.setLayoutManager(layoutManagerC);
                //itemsRecyclerDate.setHasFixedSize(true);
                reportAdapter = new TellerReportAdapter(DailyReportListAct.this, tellerReportArrayList);
                itemsRecyclerDate.setAdapter(reportAdapter);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(itemsRecyclerDate.getContext(),
                        layoutManagerC.getOrientation());
                itemsRecyclerDate.addItemDecoration(dividerItemDecoration7);

                if(reportCount >0){
                    txtReportCount.setText("My Reports:"+ reportCount);

                }else if(reportCount ==0){
                    txtReportCount.setText("No Reports for Now");

                }

            }
        });
        btnSearchDB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemsRecycler.setVisibility(View.VISIBLE);
                itemsRecyclerDate.setVisibility(View.GONE);
                return false;
            }
        });

    }


    private void generateObjects() {
        tellerReports = new ArrayList<>();
        dbHelper = new DBHelper(this);
        itemAdapter = new TellerReportAdapter(this,tellerReports);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(this);
        itemsRecycler.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(itemsRecycler);
        itemsRecycler.setAdapter(itemAdapter);
    }
    private void chooseDate() {
        dateOfReport = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

    }

    private void getItemsFromSQLite() {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        teller =new CustomerManager();
        DBHelper applicationDb = new DBHelper(this);
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefSuperUser=userPreferences.getString("Teller", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        teller = gson.fromJson(json, CustomerManager.class);
        if(teller !=null){
            tellerID=teller.getTID();

        }
        try {
            tellerReports.clear();
            tellerReports.addAll(dbHelper.getTellerReportForTeller(tellerID));
        } catch (RuntimeException e) {
            System.out.println("Oops!");
        }
        /*new AsyncTask<Void, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    tellerReports.clear();
                    tellerReports.addAll(dbHelper.getTellerReportForTeller(tellerID));
                } catch (RuntimeException e) {
                    System.out.println("Oops!");
                }




                return null;
            }

            protected void onPostExecute(Void params) {
                super.onPostExecute(params);
                itemAdapter.notifyDataSetChanged();
            }
        }.execute();*/
    }

    //Building Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report_menu, menu);

        final MenuItem item = menu.findItem(R.id.searchMenuReport);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)) {

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //CharSequence charSequence;

                //ArrayList<TellerReport> itemList = new ArrayList<>();

                String searchChr = s.toString().toLowerCase();
                itemAdapter = new TellerReportAdapter(DailyReportListAct.this,tellerReports);

                List<TellerReport> resultData = new ArrayList<>();

                for(TellerReport tellerReport: tellerReports){
                    if(tellerReport.getReportStatus().toLowerCase().contains(searchChr)){
                        resultData.add(tellerReport);
                    }
                    if(tellerReport.getAdminName().toLowerCase().contains(searchChr)){
                        resultData.add(tellerReport);
                    }
                }



                return true;
            }
        });
        return true;

    }
}