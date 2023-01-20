package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.skylightapp.Adapters.SuperSkylightPackageAdapter;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.String.valueOf;

public class DuePackagesAct extends AppCompatActivity implements SuperSkylightPackageAdapter.OnItemsClickListener {
    private List<MarketBizPackage> birthdays;
    private SuperSkylightPackageAdapter nowDueAdapter;
    private SuperSkylightPackageAdapter upcoming2Adapter;
    private SuperSkylightPackageAdapter upcoming7Adapter;
    SuperSkylightPackageAdapter itemRecyclerViewAdapter;
    private ArrayList<MarketBizPackage> nowPackageArrayList;
    private ArrayList<MarketBizPackage> package2ArrayList;
    private ArrayList<MarketBizPackage> package7ArrayList;
    private ArrayList<MarketBizPackage> packageCustomArrayList;
    int todayPackageCount, todaysPackageCount,sevenDaysPackageCount,customDayPackageCount;
    private DBHelper dbHelper;
    private Date today;
    Date currentDate,tomorrowDate,twoDaysDate,sevenDaysDate,customDayDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    int noOfDay;
    AppCompatSpinner spnDaysAhead;
    private AppCompatButton btnSearchDB;
    private SearchView searchView;
    private int spnIndex=0;
    ;
    TextView txtPackageToday, txtPackageInTwoDays,txtPackageSevenDays,txtPackageCustomDay;
    String dateOfToday,dateOfTomorrow,dateOfTwoDays,dateOfSevenDays,stringSpnDay,dateOfCustomDays;
    private SQLiteDatabase sqLiteDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_up_coming_packs);
        nowPackageArrayList = new ArrayList<MarketBizPackage>();
        package2ArrayList = new ArrayList<MarketBizPackage>();
        package7ArrayList = new ArrayList<MarketBizPackage>();
        packageCustomArrayList = new ArrayList<MarketBizPackage>();
        RecyclerView packagesToday = findViewById(R.id.recyclerViewNow);
        RecyclerView packagesInTwoDays = findViewById(R.id.recyclerViewTomoro);
        RecyclerView packages7Days = findViewById(R.id.recyclerView7Days);
        RecyclerView customPackages = findViewById(R.id.recyclerViewCustomDays);
        btnSearchDB = findViewById(R.id.buttonSearchDB);
        txtPackageCustomDay = findViewById(R.id.actP44);
        txtPackageToday = findViewById(R.id.actP11);
        txtPackageInTwoDays = findViewById(R.id.actP22);
        txtPackageSevenDays = findViewById(R.id.actP33);
        //SnapHelper snapHelper = new PagerSnapHelper();

        spnDaysAhead = findViewById(R.id.days_spn_);
        dbHelper= new DBHelper(this);
        spnDaysAhead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;
                }else {
                    stringSpnDay = (String) parent.getSelectedItem();

                }


                try {
                    noOfDay= Integer.parseInt(stringSpnDay);

                }catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                Toast.makeText(DuePackagesAct.this, "No. of Days Ahead: "+ stringSpnDay,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendar7 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendarCustom = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar1.add(Calendar.DAY_OF_YEAR, 0);
        calendar2.add(Calendar.DAY_OF_YEAR, 2);
        calendar7.add(Calendar.DAY_OF_YEAR, 7);

        currentDate = calendar1.getTime();
        //tomorrowDate = calendar1.getTime();
        twoDaysDate = calendar2.getTime();
        sevenDaysDate = calendar7.getTime();
        calendarCustom.add(Calendar.DAY_OF_YEAR, noOfDay);
        customDayDate = calendarCustom.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        try {
            dateOfToday =dateFormat.format(currentDate);
            //dateOfTomorrow =dateFormat.format(tomorrowDate);
            dateOfTwoDays =dateFormat.format(twoDaysDate);
            dateOfSevenDays =dateFormat.format(sevenDaysDate);
            dateOfCustomDays =dateFormat.format(customDayDate);
            today = dateFormat.parse(dateOfToday);


        } catch (ParseException ignored) {
        }

        if(dbHelper !=null){
            try {
                try {
                    sevenDaysPackageCount =dbHelper.getPackEnding7DaysCount(dateOfSevenDays);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        if(dbHelper !=null){
            try {
                try {
                    todaysPackageCount =dbHelper.getPackEndingTomoroCount(dateOfTomorrow);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        if(dbHelper !=null){
            try {
                try {
                    todayPackageCount =dbHelper.getPackEndingToDayCount(dateOfToday);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        if(dbHelper !=null){
            try {
                try {
                    package7ArrayList = dbHelper.getPackageEnding7Days(dateOfSevenDays);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        if(dbHelper !=null){

            try {
                try {
                    package2ArrayList = dbHelper.getPackageEndingIn3Days(dateOfTomorrow);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }



        if(dbHelper !=null){
            try {
                try {
                    nowPackageArrayList = dbHelper.getPackageEndingToday1(dateOfToday);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }





        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packagesToday.setVisibility(View.GONE);
                packagesInTwoDays.setVisibility(View.GONE);
                packages7Days.setVisibility(View.GONE);
                txtPackageSevenDays.setVisibility(View.GONE);
                txtPackageInTwoDays.setVisibility(View.GONE);
                txtPackageToday.setVisibility(View.GONE);

                if(dbHelper !=null){
                    try {
                        try {
                            customDayPackageCount =dbHelper.getPackageCountCustomDay(dateOfCustomDays);

                        } catch (SQLiteException e) {
                            e.printStackTrace();
                        }


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }

                if(dbHelper !=null){
                    try {
                        try {
                            packageCustomArrayList = dbHelper.getPackEndingCustomDay(dateOfCustomDays);

                        } catch (SQLiteException e) {
                            e.printStackTrace();
                        }


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }




                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(DuePackagesAct.this, LinearLayoutManager.HORIZONTAL, false);
                customPackages.setLayoutManager(layoutManagerC);
                itemRecyclerViewAdapter = new SuperSkylightPackageAdapter(DuePackagesAct.this,packageCustomArrayList);
                customPackages.setAdapter(itemRecyclerViewAdapter);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(customPackages.getContext(),
                        layoutManagerC.getOrientation());
                customPackages.addItemDecoration(dividerItemDecoration7);
                //snapHelper.attachToRecyclerView(customPackages);

                if(customDayPackageCount >0){
                    txtPackageCustomDay.setText("Packages:"+ customDayPackageCount);

                }else if(customDayPackageCount ==0){
                    txtPackageCustomDay.setText("Packages:0");

                }

            }
        });
        btnSearchDB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                packagesToday.setVisibility(View.VISIBLE);
                packagesInTwoDays.setVisibility(View.VISIBLE);
                packages7Days.setVisibility(View.VISIBLE);

                return false;
            }
        });



        if(todayPackageCount >0){
            txtPackageToday.setText("Packages Today:"+ todayPackageCount);

        }else if(todayPackageCount ==0){
            txtPackageToday.setText("Packages Today:0");

        }
        if(todaysPackageCount >0){
            txtPackageInTwoDays.setText("Packages in two days:"+ todaysPackageCount);

        }else if(todaysPackageCount ==0){
            txtPackageInTwoDays.setText("Packages in two days:0");

        }
        if(sevenDaysPackageCount >0){
            txtPackageSevenDays.setText(MessageFormat.format("Packages in Seven Days:{0}", sevenDaysPackageCount));

        }else if(sevenDaysPackageCount ==0){
            txtPackageSevenDays.setText("Packages in Seven Days:0");

        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        packagesToday.setLayoutManager(layoutManager);
        nowDueAdapter = new SuperSkylightPackageAdapter(DuePackagesAct.this,nowPackageArrayList);
        packagesToday.setAdapter(nowDueAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(packagesToday.getContext(),
                layoutManager.getOrientation());
        packagesToday.addItemDecoration(dividerItemDecoration);
        //snapHelper1.attachToRecyclerView(customPackages);


        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        packagesInTwoDays.setLayoutManager(layoutManager1);
        upcoming2Adapter = new SuperSkylightPackageAdapter(DuePackagesAct.this,package2ArrayList);
        packagesInTwoDays.setAdapter(upcoming2Adapter);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(packagesInTwoDays.getContext(),
                layoutManager.getOrientation());
        packagesInTwoDays.addItemDecoration(dividerItemDecoration2);
        //snapHelper2.attachToRecyclerView(customPackages);

        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        customPackages.setLayoutManager(layoutManager2);

        upcoming7Adapter = new SuperSkylightPackageAdapter(DuePackagesAct.this,package7ArrayList);

        //customPackages.setHasFixedSize(true);
        upcoming7Adapter = new SuperSkylightPackageAdapter(DuePackagesAct.this);
        packages7Days.setAdapter(upcoming7Adapter);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(packages7Days.getContext(),
                layoutManager.getOrientation());
        customPackages.addItemDecoration(dividerItemDecoration7);
        //snapHelper3.attachToRecyclerView(customPackages);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }
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
    public void onItemClick(MarketBizPackage lightPackage) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("MarketBizPackage", lightPackage);
        bundle.putParcelable("Package", lightPackage);
        Intent intent = new Intent(this, UpdatePackageAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.due_packs_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchPack)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nowDueAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                nowDueAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchPack) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}