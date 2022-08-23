package com.skylightapp;

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
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.mzule.fantasyslide.FantasyDrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Adapters.ProductPageAdapter;
import com.skylightapp.Admins.AdminSupportAct;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.HomeFragment;
import com.skylightapp.Classes.MyAccountFragment;
import com.skylightapp.Classes.MyCartFragment;
import com.skylightapp.Classes.MyOrdersFragment;
import com.skylightapp.Classes.ProductPageModel;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Classes.SliderModel;
import com.skylightapp.Classes.WishlistModel;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.SuperInvTab;
import com.skylightapp.SuperAdmin.ADepositList;
import com.skylightapp.SuperAdmin.PayOutRequestList;
import com.skylightapp.SuperAdmin.SuperAdminOffice;
import com.skylightapp.SuperAdmin.TellerReportActSuper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductsAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView categoryRecyclerView;
    private List<ProductPageModel> homePageModelFakeList = new ArrayList<>();
    private ProductPageAdapter adapter;
    private List<SkyLightPackModel> itemsListFilter = new ArrayList<>();
    private List<SkyLightPackModel> skyLightPackage_2s;
    private ProductPageModel loadCategoriesNames;
    long no_of_banners;
    private SliderModel sliderModel;
    private SkyLightPackModel skyLightPackModel;
    private WishlistModel wishlistModel;

    public static List<ProductPageModel> categoryModelList;
    private List<WishlistModel> viewAllProductList;
    int index;

    public static List<List<ProductPageModel>> lists;
    public List<SliderModel> sliderModelList;
    SharedPreferences userPreferences;
    PreferenceManager preferenceManager;
    private static final String PREF_NAME = "skylight";
    DBHelper applicationDb;
    Gson gson, gson1;
    String json, json1, nIN;
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
    com.google.android.material.floatingactionbutton.FloatingActionButton fabHome;
    //public static List<String> loadCategoriesNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_products);
        //setTitle("General E-Commerce Shop");
        userProfile= new Profile();
        gson= new Gson();
        gson1= new Gson();
        customerManager= new CustomerManager();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json1, CustomerManager.class);
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
        //getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("General E-Commerce Shop");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__category);
        if (showCart) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            //setFragment(new HomeFragment(), HOME_FRAGMENT);
        }


        sliderModel= new SliderModel();
        skyLightPackModel= new SkyLightPackModel();
        wishlistModel= new WishlistModel();
        sliderModelList = new ArrayList<>();
        skyLightPackage_2s = new ArrayList<>();
        viewAllProductList = new ArrayList<>();
        String title = getIntent().getStringExtra("CategoryName");
        categoryModelList = new ArrayList<>();
        lists = new ArrayList<>();
        if(index >=0){
            try {
                index=loadCategoriesNames.size()-1;

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        chipNavigationBar = findViewById(R.id.bottom_product_bar);
        fabHome = findViewById(R.id.fab_Shop);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ProductsAct.this, SkylightSliderAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);

            }
        });
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
                            case R.id.all_Home:
                                Intent myIntent = new Intent(ProductsAct.this, NewCustomerDrawer.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.shop_skylight:

                                Intent myIntentTeller4 = new Intent(ProductsAct.this, SkyLightPackageActivity.class);
                                myIntentTeller4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(myIntentTeller4);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntentTeller4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntentTeller4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            case R.id.timelines_user:

                                Intent depositIntent = new Intent(ProductsAct.this, UserTimeLineAct.class);
                                depositIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                depositIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                depositIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(depositIntent);

                            case R.id.my_settings:
                                Intent payoutIntent = new Intent(ProductsAct.this, UserPrefActivity.class);
                                payoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                payoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(payoutIntent);


                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });



        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new SliderModel("null", "#FFFFFF"));






        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Package A","Item Purchase", 50, "the duration is in Months", 12 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_b, "Package B","Item Purchase", 100, "the duration is in Months", 12 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_c, "Package C", "Item Purchase",150, "the duration is in Months", 12 ));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_d, "Package D","Item Purchase", 200, "the duration is in Months", 12 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_e, "Package E","Item Purchase", 100, "the duration is in Months", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_f, "Package F","Item Purchase", 200, "the duration is in Months", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_g, "Package G", "Item Purchase",400, "the duration is in Months", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_h, "Package H","Item Purchase", 300, "the duration is in Months", 5 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_i, "Package I","Item Purchase", 200, "the duration is in Months", 3 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "Item Purchase","CELEBRATION PACKAGE 1", 300, "1 Big Goat", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 2","Item Purchase", 1200, "1 Cow", 12 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 3","Item Purchase", 300, "Full bag of Rice", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 4","Item Purchase", 100, "1/2 bag of Rice", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.next_level, "NEXT LEVEL PACKAGE 1", "Item Purchase",1000, "the duration is in Months", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.next_level, "NEXT LEVEL PACKAGE 2", "Item Purchase",500, "the duration is in Months", 12 ));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 1","Item Purchase", 500, "the duration is in Months", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 2","Item Purchase", 250, "the duration is in Months", 12 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.provision_package, "PROVISION PACKAGE","Item Purchase", 300, "the duration is in Months", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Siena Car,model 2006","Item Purchase", 4000, "the duration is in Months", 18));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_b, "Keke Bus", "Item Purchase",2500, "the duration is in Months", 17));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_c, "Keke Tricycle", "Item Purchase",2300, "the duration is in Months", 12 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_d, "Electric Kettle","Item Purchase", 200, "As it is in the picture", 2 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_e, "Cooking Stove(Big round)","Item Purchase", 200, "As it is in the picture", 3 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_f, "Single Burner Hot Plate", "Item Purchase",200, "As it is in the picture", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_g, "Double Burner Hot Plate","Item Purchase", 200, "As it is in the picture", 2 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_h, "Two Burner Stove","Item Purchase", 200, "As it is in the picture", 4 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_i, "8Kg Cylinder and Burner","Item Purchase", 150, "As it is in the picture", 3 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "12.5 kg Cylinder","Item Purchase", 200, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Hammer mill Grinding Machine", "Item Purchase",500, "As it is in the picture", 5 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Butterfly Sewing Machine", "Item Purchase",400, "As it is in the picture", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 4","Item Purchase", 100, "As it is in the picture", 5 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 1","Item Purchase", 500, "As it is in the picture", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 2","Item Purchase", 250, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.provision_package, "PROVISION PACKAGE","Item Purchase", 300, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Blender(3 in 1)", "Item Purchase",250, "As it is in the picture", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinder, "Corona manual Grinder","Item Purchase", 200, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinding_machine_mil, "Hammer Mill grinding machine","Item Purchase", 500, "As it is in the picture", 5 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.home_theater_600w, "Home theatre 600W","Item Purchase", 500, "As it is in the picture", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double Burner Hot plate", "Item Purchase",200, "As it is in the picture", 2 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke Bus","Item Purchase", 2500, "As it is in the picture", 17 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "Lenovo Laptop Idea Pad","Item Purchase", 700, "Intel core i3, 2.0 Ghz, 6Gb RAM, 500Gb HDD", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "HP Laptop 15.6", "Item Purchase",800, "110-15isk Intel core i3,6100U, 2.3 Ghz, 5Gb RAM, 1000TB HDD,15 Inches", 8 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.non_sticky_pot, "Generic Non sticky pot with frying pan", "Item Purchase",200, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.oven, "Microwave Oven", "Item Purchase",400, "As it is in the picture", 4 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.sewing_maching, "BUTTERFLY SEWING MACHINE","Item Purchase", 400, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_stove, "TWO BURNER STOVE","Item Purchase", 200, "As it is in the picture", 3));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman(SPG 8.800EZ) Petrol Gen","Item Purchase", 800, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line (RDS 900 EX) Petrol Gen","Item Purchase", 800, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Ecological Line Keno Gravity(Eco 1990s) Petrol Gen","Item Purchase", 500, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line(3910 EX) Petrol Gen","Item Purchase", 500, "As it is in the picture", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Rechargeable Blender", "Item Purchase",150, "As it is in the picture", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (1.5HP)","Item Purchase", 700, "As it is in the picture", 7));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (2HP)","Item Purchase", 1000, "As it is in the picture", 2));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_iron, "Original Philips pressing iron","Item Purchase", 150, "As it is in the picture", 3));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gass_cylinder, "12.5KG Cylinder","Item Purchase", 200, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gas_cylinder_and_burner, "8Kg Cylinder & Burner","Item Purchase", 150, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double burner hot plate","Item Purchase", 200, "As it is in the picture", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.emel_ind_sewing_machind, "Emel Ind sewing machine","Item Purchase", 600, "As it is in the picture", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_table_gas, "Two burner table gas","Item Purchase", 200, "As it is in the picture", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.water_dispenser, "Water dispenser","Item Purchase", 400, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Standard standing fan","Item Purchase", 200, "As it is in the picture", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Ox Ind. fan","Item Purchase", 400, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (100L, 2ft)","Item Purchase", 700, "As it is in the picture", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (200L, 3ft)","Item Purchase", 800, "As it is in the picture", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (Beko)","Item Purchase", 700, "Biggest size,one door", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hanging_hair_dryer, "Hanging hair dryer","Item Purchase", 450, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke bus","Item Purchase", 2500, "As it is in the picture", 17));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_kettle, "Electric Kettle","Item Purchase", 200, "As it is in the picture", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.stove_round, "Cooking stove (big round)","Item Purchase", 200, "As it is in the picture", 3));


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






        homePageModelFakeList.add(new ProductPageModel(0, sliderModelFakeList ));
        homePageModelFakeList.add(new ProductPageModel(1, "","#FFFFFF"));
        homePageModelFakeList.add(new ProductPageModel(2, "","#FFFFFF",skyLightPackage_2s,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new ProductPageModel(3, "","#FFFFFF",skyLightPackage_2s));

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        adapter = new ProductPageAdapter(homePageModelFakeList);


        int listPosition=0;
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
                loadFragmentData(index,title);
            }else{
                adapter = new ProductPageAdapter(lists.get(listPosition));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
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
            skyLightPackModel= new SkyLightPackModel();

            for (long x = 1; x < skyLightPackage_2s.size(); x++) {
                skyLightPackage_2s.add(new SkyLightPackModel(skyLightPackModel));
                skyLightPackage_2s.add(
                        new SkyLightPackModel(skyLightPackModel));
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
                    actionBarLogo.setVisibility(View.VISIBLE);
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
            actionBarLogo.setVisibility(View.VISIBLE);
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
        actionBarLogo.setVisibility(View.GONE);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
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