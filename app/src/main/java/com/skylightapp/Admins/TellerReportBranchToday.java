package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.TellerReportAdapter;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Interfaces.TellerReportDao;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TellerReportBranchToday extends AppCompatActivity implements TellerReportAdapter.OnItemsClickListener{
    private static final String TAG = TellerReportBranchToday.class.getSimpleName();

    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private AdminUser adminUser;
    private CustomerManager customerManager;

    private RecyclerView recyclerView;
    private SearchView iSearchView;

    private SearchManager manager;

    private ArrayList<TellerReport> tellerReportArrayList;
    private TellerReportAdapter mAdapter;

    DBHelper dbHelper;
    String json;
    String currentDate;
    Gson gson1,gson2;
    String json1,json2,officeBranch;
    long profileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_report);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        userProfile = new Profile();
        adminUser = new AdminUser();
        dbHelper = new DBHelper(this);
        customerManager = new CustomerManager();
        tellerReportArrayList = new ArrayList<TellerReport>();
        manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        json2 = userPreferences.getString("LastAdminUserUsed", "");
        json = userPreferences.getString("LastProfileUsed", "");
        adminUser = gson2.fromJson(json2, AdminUser.class);
        userProfile = gson.fromJson(json, Profile.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(adminUser !=null){
            officeBranch=adminUser.getAdminOffice();
        }
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.rep_fab88);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminHome(adminUser,userProfile);
            }
        });

        if(userProfile !=null){
            profileID =userProfile.getPID();

        }


        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = mdformat.format(calendar.getTime());
        TReportDAO tellerReportDao= new TReportDAO(this);
        recyclerView = findViewById(R.id.recycler_view_tellerR);
        tellerReportDao.getTellerReportsForBranch(currentDate, officeBranch);
        mAdapter = new TellerReportAdapter(this, R.layout.teller_report_row, tellerReportArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new MyTouchListener(this,
                recyclerView,
                new MyTouchListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position) {
                    }

                    @Override
                    public void onRightSwipe(View view, int position) {
                    }

                    @Override
                    public void onClick(View view, int position) {
                    }
                }));
    }
    private void adminHome(AdminUser adminUser, Profile userProfile) {
        Bundle extras = new Bundle();
        extras.putParcelable("Profile",userProfile);
        extras.putParcelable("AdminUser",adminUser);
        extras.putString("USER_OFFICE",officeBranch);
        Intent intent = new Intent(this, AdminDrawerActivity.class);
        intent.putExtras(extras);
        startActivity(intent);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_branch, menu);
        final MenuItem item = menu.findItem(R.id.searchMenub);
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
                ArrayList<String> itemList = new ArrayList<>();

                for (String items : itemList) {
                    if (items.toLowerCase().contains(s.toLowerCase())) ;
                    itemList.add(items);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (TellerReportBranchToday.this,
                                android.R.layout.simple_list_item_1, itemList);

                return true;
            }
        });
        return true;
    }


    @Override
    public void onItemClick(TellerReport tellerReport, String officeBranch) {
        Bundle extras = new Bundle();
        extras.putParcelable("TellerReport",tellerReport);
        extras.putString("USER_OFFICE",officeBranch);
        Intent intent = new Intent(this, TellerReportUpdateAct.class);
        intent.putExtras(extras);
        startActivity(intent);

    }
}