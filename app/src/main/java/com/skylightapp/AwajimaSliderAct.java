package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.Gson;
import com.skylightapp.Adapters.SkyLightPackageShowCaseAdapter;
import com.skylightapp.Adapters.SkylightPackageSliderAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.MarketClasses.MarketBizPackModel;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AwajimaSliderAct extends AppCompatActivity implements SkylightPackageSliderAdapter.OnItemsClickListener,SkylightPackageSliderAdapter.TouchInterface, SearchView.OnQueryTextListener {
    private ArrayList<MarketBizPackModel> skyLightPackage_2s;
    private List<MarketBizPackModel> itemsListFilter = new ArrayList<>();
    SkylightPackageSliderAdapter adapter;
    SkylightPackageSliderAdapter searchAdapter;
    private MarketBizPackage marketBizPackage;
    private SearchView iSearchView;
    private ArrayList<MarketBizPackModel> searchPackageList;

    private SearchManager manager;
    Context context = AwajimaSliderAct.this;
    private Customer customer,customer1;
    private int customerID,packageID,profileID;
    private androidx.appcompat.widget.SearchView searchView;
    Gson gson, gson1,gson2;
    String json, json1,json2;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "awajima";
    SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Profile managerProfile;
    private CustomerManager customerManager;
    private SharedPreferences userPreferences;
    private String type,tittle;
    private int duration,id;
    private double amount,grandTotal;
    private  SkylightPackageSliderAdapter.OnItemsClickListener onItemsClickListener;
    EditText editsearch;
    private MarketBizPackModel lightPackage;
    private  View.OnTouchListener listener;
    String keyWord;
    private RecyclerView searchRecycler;
    private ImageView btnClose2,btnClose1,btnSearch1;
    private EditText etSearch;
    private TextView tv_languages;
    private RelativeLayout lout1,lout2;
    SkyLightPackageShowCaseAdapter recyPackAdapter;
    private ArrayList<SlideModel> sliderList;
    private  ImageSlider sliderView;
    private SkyLightPackageShowCaseAdapter.OnItemsClickListener callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_skylight_slider);
        setTitle("Packages");
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        customer= new Customer();
        customerManager= new CustomerManager();
        managerProfile= new Profile();
        searchRecycler = findViewById(R.id.rv_package);
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        customer1 = gson.fromJson(json, Customer.class);
        json = userPreferences.getString("LastCustomerProfileUsed", "");
        packageID = ThreadLocalRandom.current().nextInt(122, 1631);
        json1 = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson1.fromJson(json1, Profile.class);
        json2 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson2.fromJson(json2, CustomerManager.class);

        skyLightPackage_2s = new ArrayList<>() ;
        marketBizPackage = new MarketBizPackage();
        searchPackageList = new ArrayList<>() ;
        sliderList= new ArrayList<>();
        sliderView = findViewById(R.id.sliderPAC);


        //sliderList.add(SlideModel("https:https://drive.google.com/file/d/1_Tid7XsiM4erLQDVMCuABLJnMZ32SEDv/view?usp=sharing", "High performance 16GB , 1TB SSD HP Laptop"));
        //sliderList.add( SlideModel("https://drive.google.com/file/d/1CUjS0I6ze0waIIefwAB7k7emc6rzpjJz/view?usp=sharing", "High class 32Gb, 2TB SSD ,HP Laptop"));
        //sliderList.add(SlideModel("https://drive.google.com/file/d/13pdMZoJYLpRvdFL--iX-kskv4GVLP_hj/view?usp=sharing", "Triple Monitor"));
        //sliderList.startSliding();

        manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        fillPackageList();

        adapter = new SkylightPackageSliderAdapter(AwajimaSliderAct.this, skyLightPackage_2s,onItemsClickListener);

        //sliderView.setSliderAdapter(sliderList);


        adapter.setCallback(onItemsClickListener);
        editsearch = findViewById(R.id.search);
        adapter.notifyDataSetChanged();
        editsearch.setActivated(true);
        //editsearch.setQueryHint("Type your keyword here");
        //editsearch.onActionViewExpanded();
        //editsearch.setIconified(false);

        tv_languages = findViewById(R.id.tv_languages);
        lout1 = findViewById(R.id.lout_1);
        lout2 = findViewById(R.id.lout_2);
        btnSearch1 = findViewById(R.id.btn_search1);
        btnClose2 = findViewById(R.id.btn_close2);
        btnClose1 = findViewById(R.id.btn_close1);
        listeners();

    }
    private void listeners() {
        searchPackageList = new ArrayList<>() ;


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
            }

            @Override
            public void afterTextChanged(Editable s) {
                keyWord = s.toString();

                if (keyWord.isEmpty()) {
                    recyPackAdapter.updateItems(searchPackageList);
                } else {
                    searchCity();
                }


            }
        });

        btnSearch1.setOnClickListener(v -> {


            lout1.setVisibility(View.GONE);
            lout2.setVisibility(View.VISIBLE);


        });
        btnClose2.setOnClickListener(v -> {
            lout1.setVisibility(View.VISIBLE);
            lout2.setVisibility(View.GONE);

        });

        btnClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyPackAdapter.setCallback(callback);

    }

    private void searchCity() {
        searchPackageList = new ArrayList<>() ;
        searchRecycler = findViewById(R.id.rv_package);
        recyPackAdapter = new SkyLightPackageShowCaseAdapter(AwajimaSliderAct.this, searchPackageList,callback);
        searchRecycler.setAdapter(recyPackAdapter);

        searchPackageList.clear();

        for (int i = 0; i < searchPackageList.size(); i++) {

            if (searchPackageList.get(i).getpMItemName().toLowerCase().contains(keyWord)) {
                MarketBizPackModel model = new MarketBizPackModel();
                model.setpMItemName(searchPackageList.get(i).getpMItemName());
                model.setpModeID(searchPackageList.get(i).getpModeID());
                searchPackageList.add(model);
            }
        }
        recyPackAdapter.updateItems(searchPackageList);
        recyPackAdapter.notifyDataSetChanged();
        searchRecycler.setAdapter(recyPackAdapter);
    }


    private void fillPackageList() {
        skyLightPackage_2s = new ArrayList<>();


        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "", "Savings", 500, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 500, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 500, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 500, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 500, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 500, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 500, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 500, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 500, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 500, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 500, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 500, "", 12));


        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 1000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 1000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 1000, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 1000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 1000, "", 12));



        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 2000, "",1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 2000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 2000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 2000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 2000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 2000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 2000, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 2000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 2000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 2000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 2000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 2000, "", 12));

        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 3000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 3000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 3000, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 3000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 3000, "", 12));

        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 4000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "",6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 4000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "",9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 4000, "",10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 4000, "",11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 4000, "", 12));



        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 5000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 5000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 5000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 5000, "", 12));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 6000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 6000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 6000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 6000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "",7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 6000, "", 12));

        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "",11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 50000, "", 12));

        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 100000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "",2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 11 ));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 100000, "", 12 ));



    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    /*private void doOptions(Bundle bundle){
        dbHelper= new DBHelper(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(AwajimaSliderAct.this);
        builder.setTitle("Choose an Option");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"Pay Now", "Pay Later"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(AwajimaSliderAct.this, "packageType Package, selected", Toast.LENGTH_SHORT).show();
                                Intent savingsIntent = new Intent(AwajimaSliderAct.this, PayNowActivity.class);
                                savingsIntent.putExtras(bundle);
                                savingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(savingsIntent);
                                break;
                            case 1:
                                Toast.makeText(builder.getContext(), "Pay later,selected", Toast.LENGTH_SHORT).show();
                                dbHelper.insertPackage(profileID,customerID,packageID,tittle,duration,amount,);
                                break;

                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();

    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.slider_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.searchMenus)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchMenus) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        customer= new Customer();

        if(skyLightPackage_2s.size() >0){
            lightPackage = skyLightPackage_2s.get(position);

        }

        if(customer !=null){
            customerID=customer.getCusUID();
        }
        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }
        if(lightPackage !=null){
            type=lightPackage.getpMType();
            duration=lightPackage.getpMDuration();
            amount=lightPackage.getpMPrice();
            grandTotal=duration*amount;
            id=lightPackage.getpModeID();
            tittle=lightPackage.getpMItemName();
        }


        marketBizPackage = new MarketBizPackage(id,customerID,tittle,amount,grandTotal,type,duration);
        bundle.putParcelable("MarketBizPackage", marketBizPackage);
        Intent payIntent = new Intent(AwajimaSliderAct.this, PayNowActivity.class);
        payIntent.putExtras(bundle);
        payIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(payIntent);

    }

    @Override
    public void onItemClick(View view, int position) {
        lightPackage = skyLightPackage_2s.get(position);
        Bundle bundle = new Bundle();
        customer= new Customer();

        if(skyLightPackage_2s.size() >0){
            lightPackage = skyLightPackage_2s.get(position);

        }

        if(customer !=null){
            customerID=customer.getCusUID();
        }
        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }
        if(lightPackage !=null){
            type=lightPackage.getpMType();
            duration=lightPackage.getpMDuration();
            amount=lightPackage.getpMPrice();
            grandTotal=duration*amount;
            id=lightPackage.getpModeID();
            tittle=lightPackage.getpMItemName();
        }


        marketBizPackage = new MarketBizPackage(id,customerID,tittle,amount,grandTotal,type,duration);
        bundle.putParcelable("MarketBizPackage", marketBizPackage);
        Intent payIntent = new Intent(AwajimaSliderAct.this, PayNowActivity.class);
        payIntent.putExtras(bundle);
        payIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(payIntent);


    }

    @Override
    public void touch(View.OnTouchListener onTouchListener) {
        this.listener = onTouchListener;
        adapter = new SkylightPackageSliderAdapter(AwajimaSliderAct.this, skyLightPackage_2s,onItemsClickListener);

        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down");

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");
                        break;
                }

                adapter.notifyDataSetChanged();

                return false;
            }
        };

    }
}