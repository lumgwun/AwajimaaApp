package com.skylightapp.Markets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Adapters.ProductPageAdapter;
import com.skylightapp.AwajimaSliderAct;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.HomeFragment;
import com.skylightapp.MarketClasses.MarketBizPackModel;
import com.skylightapp.Classes.MyAccountFragment;
import com.skylightapp.Classes.MyCartFragment;
import com.skylightapp.Classes.MyOrdersFragment;
import com.skylightapp.Classes.ProductPageModel;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SliderModel;
import com.skylightapp.Classes.WishlistModel;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineAct;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView categoryRecyclerView;
    private List<ProductPageModel> homePageModelFakeList = new ArrayList<>();
    private ProductPageAdapter adapter;
    private List<MarketBizPackModel> itemsListFilter = new ArrayList<>();
    private List<MarketBizPackModel> skyLightPackage_2s;
    private ProductPageModel loadCategoriesNames;
    long no_of_banners;
    private SliderModel sliderModel;
    private MarketBizPackModel skyLightPackModel;
    private WishlistModel wishlistModel;

    public static List<ProductPageModel> categoryModelList;
    private List<WishlistModel> viewAllProductList;
    int index;

    public static List<List<ProductPageModel>> lists;
    public List<SliderModel> sliderModelList;
    SharedPreferences userPreferences;
    PreferenceManager preferenceManager;
    private static final String PREF_NAME = "awajima";
    DBHelper applicationDb;
    Gson gson, gson1,gson2;
    String json, json1,json2, nIN;
    Profile userProfile, customerProfile, lastProfileUsed;
    private CustomerManager customerManager;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    public static Boolean showCart = false;
    private FrameLayout frameLayout;
    private ImageView actionBarLogo;
    private int currentFragment = -1;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    ChipNavigationBar chipNavigationBar;
    private String title,SharedPrefUserName;
    private MarketBusiness marketBusiness;
    com.google.android.material.floatingactionbutton.FloatingActionButton fabHome;
    //public static List<String> loadCategoriesNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_products);
        setTitle("Agric Market Shop");
        userProfile= new Profile();
        gson= new Gson();
        gson1= new Gson();
        gson2= new Gson();
        marketBusiness= new MarketBusiness();
        customerManager= new CustomerManager();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerManagerUsed", "");
        customerManager = gson1.fromJson(json1, CustomerManager.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        categoryRecyclerView=findViewById(R.id.category_recyclerview);
        DBHelper applicationDb = new DBHelper(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_shop);

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_viewShop);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.shop_DLayout);
        frameLayout = findViewById(R.id.main_Shop_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (showCart) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //setFragment(new HomeFragment(), HOME_FRAGMENT);
        }


        sliderModel= new SliderModel();
        skyLightPackModel= new MarketBizPackModel();
        wishlistModel= new WishlistModel();
        sliderModelList = new ArrayList<>();
        skyLightPackage_2s = new ArrayList<>();
        viewAllProductList = new ArrayList<>();
        title = getIntent().getStringExtra("CategoryName");
        categoryModelList = new ArrayList<>();
        lists = new ArrayList<>();
        if(index >=0){
            try {
                index=loadCategoriesNames.size()-1;

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }

        /*fabHome = findViewById(R.id.fab_Shop);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ProductsAct.this, AwajimaSliderAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);

            }
        });*/

        /*Teliver.identifyUser(new UserBuilder(SharedPrefUserName).
                setUserType(UserBuilder.USER_TYPE.CONSUMER).registerPush().build());*/


        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));



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


        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 7000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 7000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 7000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 7000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "",7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 7000, "", 12));


        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 8000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 8000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 8000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 8000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "",7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 8000, "", 12));


        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 9000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 9000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 9000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 9000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "",7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 9000, "", 12));

        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 10000, "", 1));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 10000, "", 2));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "", 3));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "", 4));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 10000, "", 5));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings, "","Savings", 10000, "", 6));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "",7));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "", 8));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "", 9));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "", 10));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "", 11));
        skyLightPackage_2s.add(new MarketBizPackModel(R.drawable.savings,"", "Savings", 10000, "", 12));



        homePageModelFakeList.add(new ProductPageModel(0, sliderModelFakeList ));
        homePageModelFakeList.add(new ProductPageModel(1, "","#FFFFFF"));
        homePageModelFakeList.add(new ProductPageModel(2, "","#FFFFFF",skyLightPackage_2s,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new ProductPageModel(3, "","#FFFFFF",skyLightPackage_2s));

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        adapter = new ProductPageAdapter(homePageModelFakeList);


        /*int listPosition=0;
        if(loadCategoriesNames !=null){

            if(index >=0){
                try {
                    for(int x =0;x<loadCategoriesNames.size();x++){
                        if(loadCategoriesNames.get(x).equals(title.toUpperCase())){
                            listPosition=x;
                        }


                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }


        }

        try {
            if(listPosition==0){
                loadCategoriesNames.add(title.toUpperCase());
                lists.add(new ArrayList<ProductPageModel>());
                //loadFragmentData(index,title);
            }else{
                adapter = new ProductPageAdapter(lists.get(listPosition));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }*/


        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    @SuppressLint("NotifyDataSetChanged")
    public void loadFragmentData(final int index, String title){
       if(index==0){
           sliderModel= new SliderModel();
           List<SliderModel> sliderModelList = new ArrayList<>();
           try {
               for (int x = 0; x < sliderModelList.size(); x++) {
                   sliderModelList.add(new SliderModel(sliderModel));
               }
               lists.get(index).add(new ProductPageModel(0, sliderModelList));

           } catch (NullPointerException e) {
               e.printStackTrace();
           }


       }
        if(index==2){
            wishlistModel= new WishlistModel();
            viewAllProductList = new ArrayList<>();
            try {
                for (long x = 1; x < viewAllProductList.size(); x++) {
                    viewAllProductList.add(new WishlistModel(wishlistModel));
                }
                lists.get(index).add(new ProductPageModel(2, "","#FFFFFF",skyLightPackage_2s,new ArrayList<WishlistModel>()));

            } catch (NullPointerException e) {
                e.printStackTrace();
            }



        }
        if(index==3){
            skyLightPackage_2s = new ArrayList<>();
            skyLightPackModel= new MarketBizPackModel();

            for (long x = 1; x < skyLightPackage_2s.size(); x++) {
                skyLightPackage_2s.add(new MarketBizPackModel(skyLightPackModel));
                skyLightPackage_2s.add(
                        new MarketBizPackModel(skyLightPackModel));
            }
            lists.get(index).add(new ProductPageModel(3, "","#FFFFFF",skyLightPackage_2s));

        }


        ProductPageAdapter adapter = new ProductPageAdapter(lists.get(index));

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        adapter = new ProductPageAdapter(homePageModelFakeList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //HomeFragment.swipeRefreshLayout.setRefreshing(false);


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
        drawer = findViewById(R.id.shop_DLayout);
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
    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.shop_DLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                if (showCart) {
                    showCart = false;
                    finish();
                } else {
                    //actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_Home) {
            //actionBarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        } else if (id == R.id.nav_my_orders) {
            gotoFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
        } else if (id == R.id.nav_my_rewards) {
           // gotoFragment("My Rewards", new MyRewardsFragment(), REWARDS_FRAGMENT);
        } else if (id == R.id.nav_my_cart) {
            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);

        } else if (id == R.id.nav_my_wishlist) {
            //gotoFragment("My Wishlist", new MyWishlistFragment(), WISHLIST_FRAGMENT);
        } else if (id == R.id.nav_my_account) {
            gotoFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
        } else if (id == R.id.nav_sign_out) {

        }

        drawer = findViewById(R.id.shop_DLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment, int fragmentNo) {

        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }

    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        //actionBarLogo.setVisibility(View.GONE);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.product_menu) {
            return true;
        }
        if (currentFragment == HOME_FRAGMENT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            //getMenuInflater().inflate(R.menu.customer_main_menu, menu);
        }
        return super.onOptionsItemSelected(item);

    }
}