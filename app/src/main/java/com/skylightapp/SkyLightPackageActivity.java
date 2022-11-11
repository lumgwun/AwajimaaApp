package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.skylightapp.Adapters.NewSkylightPackageSlider;
import com.skylightapp.Adapters.SkylightPackageSliderAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class SkyLightPackageActivity extends AppCompatActivity implements  NewSkylightPackageSlider.OnItemsClickListener{
    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_SKYLIGHT_ID";
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewTwo;
    private ArrayList<SkyLightPackage> skyLightPackages;
    //private ArrayList<SkyLightPackage_List_Model> skyLightPackage_2s;
    private List<SkyLightPackModel> skyLightPackage_2s;
    NewSkylightPackageSlider adapter;
    private ArrayList<SkyLightPackage> recyclerDataArrayList;
    Bundle paymentBundle;
    private ViewPager page;
    private TabLayout tabLayout;
    private List<SkyLightPackModel> itemsListFilter = new ArrayList<>();
    private SkyLightPackage skyLightPackage;

    private SearchManager manager;
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
    private NewSkylightPackageSlider.OnItemsClickListener listener;
    MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sky_light_package);
        setTitle("Packages Slider");
        fillPackageList();
        //setUpRecyclerView();
        page = findViewById(R.id.my_pager) ;
        tabLayout = findViewById(R.id.my_tablayout);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        customer= new Customer();
        customerManager= new CustomerManager();
        managerProfile= new Profile();
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        customer1 = gson.fromJson(json, Customer.class);
        json = userPreferences.getString("LastCustomerProfileUsed", "");
        packageID = ThreadLocalRandom.current().nextInt(122, 1631);
        json1 = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson1.fromJson(json1, Profile.class);
        json2 = userPreferences.getString("LastCustomerManagerUsed", "");
        customerManager = gson2.fromJson(json2, CustomerManager.class);
        adapter = new NewSkylightPackageSlider(this, skyLightPackage_2s,listener);
        page.setAdapter(adapter);
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(),1000,2000);
        tabLayout.setupWithViewPager(page,true);
        //ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        //SliderView sliderView = findViewById(R.id.slider);
        skyLightPackage_2s = new ArrayList<>() ;

        /*sliderDataArrayList.add(new SliderData(R.drawable.p_a));
        sliderDataArrayList.add(new SliderData(R.drawable.p_b));
        sliderDataArrayList.add(new SliderData(R.drawable.p_c));
        sliderDataArrayList.add(new SliderData(R.drawable.p_d));
        sliderDataArrayList.add(new SliderData(R.drawable.p_e));
        sliderDataArrayList.add(new SliderData(R.drawable.p_f));
        sliderDataArrayList.add(new SliderData(R.drawable.p_g));
        sliderDataArrayList.add(new SliderData(R.drawable.p_h));
        sliderDataArrayList.add(new SliderData(R.drawable.p_i));
        sliderDataArrayList.add(new SliderData(R.drawable.special_package));
        sliderDataArrayList.add(new SliderData(R.drawable.blender));
        sliderDataArrayList.add(new SliderData(R.drawable.grinder));
        sliderDataArrayList.add(new SliderData(R.drawable.generator));
        sliderDataArrayList.add(new SliderData(R.drawable.provision_package));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));*/

        //SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);


        /*sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();*/


        /*final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        paymentBundle= new Bundle();
        mRecyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());*/


        /*for (int i = 0; i < skyLightPackage_2s.size(); i++) {


        }*/



    }
    private void fillPackageList() {
        skyLightPackage_2s = new ArrayList<>();

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "", "Savings", 500, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 500, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 500, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 500, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 500, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 500, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 500, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 500, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 500, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 500, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 500, "", 11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 500, "", 12));


        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 1000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 1000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 1000, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 1000, "", 11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 1000, "", 12));



        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 2000, "",1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 2000, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 2000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 2000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 2000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 2000, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 2000, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 2000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 2000, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 2000, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 2000, "", 11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 2000, "", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 3000, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 3000, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 3000, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 3000, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 3000, "", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 4000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "",6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 4000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "",9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 4000, "",10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 4000, "",11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 4000, "", 12));



        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 5000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 5000, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 5000, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 5000, "", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 6000, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 6000, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 6000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 6000, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "",7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "", 11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 6000, "", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "",11));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 50000, "", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 100000, "", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "",2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 9));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 10));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 11 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 100000, "", 12 ));


        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hp_16gb_1tb_ssd_608, "HP, 16Gb, 1Tb Laptop", "Gadget Purchase",1110000, "Intel core i7, 16Gb RAM, 1TGb HDD with SSD", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hp_32gb_2tb_ssd_, "HP 32Gb, 2TB Laptop","Gadget Purchase", 1620000, "Intel core i7, 32Gb RAM, 2TGb HDD with SSD,15 Inches", 8));



    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //adapter = new ExampleAdapter(exampleList);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);
      SnapHelper snapHelper = new PagerSnapHelper();
      snapHelper.attachToRecyclerView(recyclerView);
    }
    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {

            SkyLightPackageActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page.getCurrentItem()< skyLightPackage_2s.size()-1) {
                        page.setCurrentItem(page.getCurrentItem()+1);
                    }
                    else
                        page.setCurrentItem(0);
                }
            });
        }
    }


    @Override
    public void onItemClick(SkyLightPackModel lightPackage) {

        Bundle bundle = new Bundle();
        customer= new Customer();
        if(customer !=null){
            customerID=customer.getCusUID();
        }
        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }
        String tittle=lightPackage.getpMItemName();
        String type=lightPackage.getpMType();
        int duration=lightPackage.getpMDuration();
        double amount=lightPackage.getpMPrice();
        double grandTotal=duration*amount;
        int id=lightPackage.getpModeID();
        skyLightPackage = new SkyLightPackage(id,customerID,tittle,amount,grandTotal,type,duration);
        bundle.putParcelable("SkyLightPackage", skyLightPackage);
        Intent payIntent = new Intent(SkyLightPackageActivity.this, PayNowActivity.class);
        payIntent.putExtras(bundle);
        payIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(payIntent);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.packs_menu, menu);
        searchItem = menu.findItem(R.id.actionPacks);
        try {
            if(searchItem !=null){
                searchView = (SearchView) searchItem.getActionView();
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });

            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        return true;
    }
}