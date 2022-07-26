package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Adapters.SkylightPackageSliderAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.SuperAdmin.Skylight;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SkylightSliderAct extends AppCompatActivity implements SkylightPackageSliderAdapter.OnItemsClickListener, SearchView.OnQueryTextListener {
    private ArrayList<SkyLightPackModel> skyLightPackage_2s;
    private List<SkyLightPackModel> itemsListFilter = new ArrayList<>();
    SkylightPackageSliderAdapter adapter;
    private SkyLightPackage skyLightPackage;
    private SearchView iSearchView;

    private SearchManager manager;
    Context context = SkylightSliderAct.this;
    private Customer customer,customer1;
    private int customerID,packageID,profileID;
    private androidx.appcompat.widget.SearchView searchView;
    Gson gson, gson1,gson2;
    String json, json1,json2;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "skylight";
    SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Profile managerProfile;
    private CustomerManager customerManager;
    private SharedPreferences userPreferences;
    private String type,tittle;
    private int duration,id;
    private double amount,grandTotal;
    private  SkylightPackageSliderAdapter.OnItemsClickListener onItemsClickListener;
    SearchView editsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_skylight_slider);
        setTitle("Packages Slider");
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
        json2 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson2.fromJson(json2, CustomerManager.class);
        SliderView sliderView = findViewById(R.id.slider);
        skyLightPackage_2s = new ArrayList<>() ;
        skyLightPackage= new SkyLightPackage();
        manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        fillPackageList();
        adapter = new SkylightPackageSliderAdapter(SkylightSliderAct.this, skyLightPackage_2s,onItemsClickListener);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setSliderAdapter(adapter);
        adapter.setCallback(onItemsClickListener);
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        adapter.notifyDataSetChanged();
        editsearch.setActivated(true);
        editsearch.setQueryHint("Type your keyword here");
        editsearch.onActionViewExpanded();
        editsearch.setIconified(false);

    }
    private void fillPackageList() {
        skyLightPackage_2s = new ArrayList<>();

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




        /*skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 7000, "", "12 "));




        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 8000, "", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 9000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 9000, "", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 10000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 10000, "", "12 "));


        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "","Savings", 20000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings,"", "Savings", 20000, "", "12 "));*/


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



        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Package A","Item Purchase", 50, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_b, "Package B","Item Purchase", 100, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_c, "Package C","Item Purchase", 150, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_d, "Package D","Item Purchase", 200, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_e, "Package E","Item Purchase", 100, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_f, "Package F","Item Purchase", 200, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_g, "Package G","Item Purchase", 400, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_h, "Package H","Item Purchase", 300, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_i, "Package I", "Item Purchase",200, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 1","Item Purchase", 300, "1 Big Goat", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 2","Item Purchase", 1200, "1 Cow", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 3","Item Purchase", 300, "Full bag of Rice", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 4","Item Purchase", 100, "1/2 bag of Rice", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.next_level, "NEXT LEVEL PACKAGE","Item Purchase", 500, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 1","Item Purchase", 500, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 2","Item Purchase", 250, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.provision_package, "PROVISION PACKAGE","Item Purchase", 300, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Blender(3 in 1)", "Item Purchase",250, "As it is in the picture", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinder, "Corona manual Grinder","Item Purchase", 200, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinding_machine_mil, "Hammer Mill grinding machine", "Item Purchase",500, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.home_theater_600w, "Home theatre 600W", "Item Purchase",500, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double Burner Hot plate","Item Purchase", 200, "As it is in the picture", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke Bus","Item Purchase", 2500, "As it is in the picture", 17));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "Lenovo Laptop Idea Pad", "Item Purchase",700, "Intel core i3, 2.0 Ghz, 6Gb RAM, 500Gb HDD", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "HP Laptop 15.6","Item Purchase", 800, "110-15isk Intel core i3,6100U, 2.3 Ghz, 5Gb RAM, 1000TB HDD,15 Inches", 8));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.non_sticky_pot, "Generic Non sticky pot with frying pan","Item Purchase", 200, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.oven, "Microwave Oven","Item Purchase", 400, "As it is in the picture", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.sewing_maching, "BUTTERFLY SEWING MACHINE", "Item Purchase",400, "As it is in the picture", 6));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_stove, "TWO BURNER STOVE","Item Purchase", 200, "As it is in the picture", 3));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman(SPG 8.800EZ) Petrol Gen","Item Purchase", 800, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line (RDS 900 EX) Petrol Gen","Item Purchase", 800, "As it is in the picture", 12));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Ecological Line Keno Gravity(Eco 1990s) Petrol Gen","Item Purchase", 500, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line(3910 EX) Petrol Gen", "Item Purchase",500, "As it is in the picture", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Rechargeable Blender", "Item Purchase",150, "As it is in the picture", 1));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (1.5HP)","Item Purchase", 700, "As it is in the picture", 7));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (2HP)","Item Purchase", 1000, "As it is in the picture", 2));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_iron, "Original Philips pressing iron","Item Purchase", 150, "As it is in the picture", 3));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gass_cylinder, "12.5KG Cylinder","Item Purchase", 200, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gas_cylinder_and_burner, "8Kg Cylinder & Burner","Item Purchase", 150, "As it is in the picture", 3));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double burner hot plate","Item Purchase", 200, "As it is in the picture", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.emel_ind_sewing_machind, "Emel Ind sewing machine", "Item Purchase",600, "As it is in the picture", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_table_gas, "Two burner table gas","Item Purchase", 200, "As it is in the picture", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.water_dispenser, "Water dispenser", "Item Purchase",400, "As it is in the picture", 6 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Standard standing fan","Item Purchase", 200, "As it is in the picture", 4));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Ox Ind. fan", "Item Purchase",400, "As it is in the picture", 5));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (100L, 2ft)","Item Purchase", 700, "As it is in the picture", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (200L, 3ft)", "Item Purchase",800, "As it is in the picture", 7));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (Beko)","Item Purchase", 700, "Biggest size,one door", 12));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hanging_hair_dryer, "Hanging hair dryer", "Item Purchase",450, "As it is in the picture", 5 ));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke bus","Item Purchase", 2500, "As it is in the picture", 17));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_kettle, "Electric Kettle","Item Purchase", 200, "As it is in the picture", 2));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.stove_round, "Cooking stove (big round)","Item Purchase", 200, "As it is in the picture", 3));


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
        if(lightPackage !=null){
            type=lightPackage.getpMType();
            duration=lightPackage.getpMDuration();
            amount=lightPackage.getpMPrice();
            grandTotal=duration*amount;
            id=lightPackage.getpModeID();
            tittle=lightPackage.getpMItemName();
        }


        skyLightPackage = new SkyLightPackage(id,customerID,tittle,amount,grandTotal,type,duration);
        bundle.putParcelable("SkyLightPackage", skyLightPackage);
        Intent payIntent = new Intent(SkylightSliderAct.this, PayNowActivity.class);
        payIntent.putExtras(bundle);
        payIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(payIntent);
        //doOptions(bundle);
    }
    /*private void doOptions(Bundle bundle){
        dbHelper= new DBHelper(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(SkylightSliderAct.this);
        builder.setTitle("Choose an Option");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"Pay Now", "Pay Later"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(SkylightSliderAct.this, "packageType Package, selected", Toast.LENGTH_SHORT).show();
                                Intent savingsIntent = new Intent(SkylightSliderAct.this, PayNowActivity.class);
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
}