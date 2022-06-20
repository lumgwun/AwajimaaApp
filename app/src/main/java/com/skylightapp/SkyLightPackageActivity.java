package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.skylightapp.Adapters.NewSkylightPackageSlider;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sky_light_package);
        fillPackageList();
        //setUpRecyclerView();
        page = findViewById(R.id.my_pager) ;
        tabLayout = findViewById(R.id.my_tablayout);
        adapter = new NewSkylightPackageSlider(this, skyLightPackage_2s);
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

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Package A", 50, "the duration is in Months", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_b, "Package B", 100, "the duration is in Months", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_c, "Package C", 150, "the duration is in Months", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_d, "Package D", 200, "the duration is in Months", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_e, "Package E", 100, "the duration is in Months", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_f, "Package F", 200, "the duration is in Months", "6"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_g, "Package G", 400, "the duration is in Months", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_h, "Package H", 300, "the duration is in Months", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_i, "Package I", 200, "the duration is in Months", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 1", 300, "1 Big Goat", "4"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 2", 1200, "1 Cow", "12 months"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 3", 300, "Full bag of Rice", "3"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 4", 100, "1/2 bag of Rice", "5"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.next_level, "NEXT LEVEL PACKAGE 1", 1000, "the duration is in Months", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.next_level, "NEXT LEVEL PACKAGE 2", 500, "the duration is in Months", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 1", 500, "the duration is in Months", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 2", 250, "the duration is in Months", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.provision_package, "PROVISION PACKAGE", 300, "the duration is in Months", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Siena Car,model 2006", 4000, "the duration is in Months", "18 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_b, "Keke Bus", 2500, "the duration is in Months", "17 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_c, "Keke Tricycle", 2300, "the duration is in Months", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_d, "Electric Kettle", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_e, "Cooking Stove(Big round)", 200, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_f, "Single Burner Hot Plate", 200, "As it is in the picture", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_g, "Double Burner Hot Plate", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_h, "Two Burner Stove", 200, "As it is in the picture", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_i, "8Kg Cylinder and Burner", 150, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "12.5 kg Cylinder", 200, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Hammer mill Grinding Machine", 500, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Butterfly Sewing Machine", 400, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 4", 100, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 1", 500, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 2", 250, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.provision_package, "PROVISION PACKAGE", 300, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Blender(3 in 1)", 250, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinder, "Corona manual Grinder", 200, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinding_machine_mil, "Hammer Mill grinding machine", 500, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.home_theater_600w, "Home theatre 600W", 500, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double Burner Hot plate", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke Bus", 2500, "As it is in the picture", "17 months"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "Lenovo Laptop Idea Pad", 700, "Intel core i3, 2.0 Ghz, 6Gb RAM, 500Gb HDD", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "HP Laptop 15.6", 800, "110-15isk Intel core i3,6100U, 2.3 Ghz, 5Gb RAM, 1000TB HDD,15 Inches", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.non_sticky_pot, "Generic Non sticky pot with frying pan", 200, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.oven, "Microwave Oven", 400, "As it is in the picture", "4 months"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.sewing_maching, "BUTTERFLY SEWING MACHINE", 400, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_stove, "TWO BURNER STOVE", 200, "As it is in the picture", "3 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman(SPG 8.800EZ) Petrol Gen", 800, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line (RDS 900 EX) Petrol Gen", 800, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Ecological Line Keno Gravity(Eco 1990s) Petrol Gen", 500, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line(3910 EX) Petrol Gen", 500, "As it is in the picture", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Rechargeable Blender", 150, "As it is in the picture", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (1.5HP)", 700, "As it is in the picture", "7 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (2HP)", 1000, "As it is in the picture", "2 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_iron, "Original Philips pressing iron", 150, "As it is in the picture", "3 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gass_cylinder, "12.5KG Cylinder", 200, "As it is in the picture", "3 months"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gas_cylinder_and_burner, "8Kg Cylinder & Burner", 150, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double burner hot plate", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.emel_ind_sewing_machind, "Emel Ind sewing machine", 600, "As it is in the picture", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_table_gas, "Two burner table gas", 200, "As it is in the picture", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.water_dispenser, "Water dispenser", 400, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Standard standing fan", 200, "As it is in the picture", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Ox Ind. fan", 400, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (100L, 2ft)", 700, "As it is in the picture", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (200L, 3ft)", 800, "As it is in the picture", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (Beko)", 700, "Biggest size,one door", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hanging_hair_dryer, "Hanging hair dryer", 450, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke bus", 2500, "As it is in the picture", "17 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_kettle, "Electric Kettle", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.stove_round, "Cooking stove (big round)", 200, "As it is in the picture", "3 "));


        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 500, "", "12 "));


        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 1000, "", "12 "));



        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 2000, "", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 3000, "", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 4000, "", "12 "));



        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 5000, "", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 6000, "", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 7000, "", "12 "));




        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 8000, "", "12 "));




        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 9000, "", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 10000, "", "12 "));


        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 20000, "", "12 "));


        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 50000, "", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "9 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "10 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "11 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.savings, "Savings", 100000, "", "12 "));



        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "Package A", 50, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_b, "Package B", 100, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_c, "Package C", 150, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_d, "Package D", 200, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_e, "Package E", 100, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_f, "Package F", 200, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_g, "Package G", 400, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_h, "Package H", 300, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_i, "Package I", 200, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 1", 300, "1 Big Goat", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 2", 1200, "1 Cow", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.p_a, "CELEBRATION PACKAGE 3", 300, "Full bag of Rice", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "CELEBRATION PACKAGE 4", 100, "1/2 bag of Rice", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.next_level, "NEXT LEVEL PACKAGE", 500, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 1", 500, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.special_package, "SPECIAL PACKAGE 2", 250, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.provision_package, "PROVISION PACKAGE", 300, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Blender(3 in 1)", 250, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinder, "Corona manual Grinder", 200, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.grinding_machine_mil, "Hammer Mill grinding machine", 500, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.home_theater_600w, "Home theatre 600W", 500, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double Burner Hot plate", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke Bus", 2500, "As it is in the picture", "17 months"));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "Lenovo Laptop Idea Pad", 700, "Intel core i3, 2.0 Ghz, 6Gb RAM, 500Gb HDD", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.laptop, "HP Laptop 15.6", 800, "110-15isk Intel core i3,6100U, 2.3 Ghz, 5Gb RAM, 1000TB HDD,15 Inches", "8 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.non_sticky_pot, "Generic Non sticky pot with frying pan", 200, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.oven, "Microwave Oven", 400, "As it is in the picture", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.sewing_maching, "BUTTERFLY SEWING MACHINE", 400, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_stove, "TWO BURNER STOVE", 200, "As it is in the picture", "3 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman(SPG 8.800EZ) Petrol Gen", 800, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line (RDS 900 EX) Petrol Gen", 800, "As it is in the picture", "12 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Ecological Line Keno Gravity(Eco 1990s) Petrol Gen", 500, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.generator, "Fireman Rugged Line(3910 EX) Petrol Gen", 500, "As it is in the picture", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.blender, "Rechargeable Blender", 150, "As it is in the picture", "1 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (1.5HP)", 700, "As it is in the picture", "7 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.a_c_1_5, "Air conditioner (2HP)", 1000, "As it is in the picture", "2 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_iron, "Original Philips pressing iron", 150, "As it is in the picture", "3 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gass_cylinder, "12.5KG Cylinder", 200, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.gas_cylinder_and_burner, "8Kg Cylinder & Burner", 150, "As it is in the picture", "3 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hot_plate_double_burner, "Double burner hot plate", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.emel_ind_sewing_machind, "Emel Ind sewing machine", 600, "As it is in the picture", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.two_burner_table_gas, "Two burner table gas", 200, "As it is in the picture", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.water_dispenser, "Water dispenser", 400, "As it is in the picture", "6 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Standard standing fan", 200, "As it is in the picture", "4 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.standing_fan, "Ox Ind. fan", 400, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (100L, 2ft)", 700, "As it is in the picture", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (200L, 3ft)", 800, "As it is in the picture", "7 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.deep_freezer, "Deep freezer (Beko)", 700, "Biggest size,one door", "12 "));

        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.hanging_hair_dryer, "Hanging hair dryer", 450, "As it is in the picture", "5 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.keke_bus, "Keke bus", 2500, "As it is in the picture", "17 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.electric_kettle, "Electric Kettle", 200, "As it is in the picture", "2 "));
        skyLightPackage_2s.add(new SkyLightPackModel(R.drawable.stove_round, "Cooking stove (big round)", 200, "As it is in the picture", "3 "));


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
        paymentBundle.putParcelable("Package1",lightPackage);
        paymentBundle.putDouble("Total",lightPackage.getPrice());
        paymentBundle.putLong("Package Id",lightPackage.getPackageID());
        Intent intent8 = new Intent(this, PayNowActivity.class);
        intent8.putExtras(paymentBundle);
        startActivity(intent8);


    }
}