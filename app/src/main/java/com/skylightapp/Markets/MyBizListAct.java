package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyBizListAct extends AppCompatActivity implements MarketBizRecyAdapter.OnBizClickListener {
    List<MarketBusiness> marketBusinessList;
    List<MarketBusiness> myMarketarketBusinesses;
    private EditText etSearch;
    private ImageView btn_close1,btn_search1,btn_close2;
    private TextView tv_languages;
    private RelativeLayout lout_2,lout_1;
    private String keyWord;
    private MarketBizRecyAdapter mAdapter;
    private DBHelper dbHelper;
    private int marketCount;
    private SwipyRefreshLayout allMarketRefreshLayout,myMarketRefreshLayout;
    private RecyclerView marketRecyclerView,myMarketRecyclerView;
    private static final String PREF_NAME = "awajima";
    SharedPreferences userPreferences;
    Gson gson, gson1;
    String json, json1, nIN;
    Profile userProfile, customerProfile, lastProfileUsed;
    private int profID;
    private MarketBizRecyAdapter marketBizRecyAdapter;
    private MarketBizDAO marketBizDAO;
    private TextView text_No;
    private Awajima awajima;
    private int marketBizCount,myMarketBizCount;
    String selectedCountry, selectedBank, bankName, bankNumber, officePref, userNamePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_biz_list);
        dbHelper= new DBHelper(this);
        marketBizDAO= new MarketBizDAO(this);
        marketRecyclerView = findViewById(R.id.markets_list);
        myMarketRecyclerView = findViewById(R.id.my_market_Biz);
        text_No = findViewById(R.id.text_No);
        awajima= new Awajima();
        marketBusinessList = new ArrayList<MarketBusiness>();
        myMarketarketBusinesses = new ArrayList<MarketBusiness>();

        //mAdapter = new MarketBizRecyAdapter(this, getTestData());
        //marketRecyclerView.setAdapter(mAdapter);
        allMarketRefreshLayout = findViewById(R.id.market_refresh_l);
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        if(userProfile !=null){
            profID=userProfile.getPID();
        }
        if(marketBizDAO !=null){
            try {
                marketBusinessList=marketBizDAO.getBusinessesFromProfile(profID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        myMarketRecyclerView.setLayoutManager(new LinearLayoutManager(MyBizListAct.this, LinearLayoutManager.HORIZONTAL, false));
        Collections.shuffle(marketBusinessList, new Random(System.currentTimeMillis()));
        mAdapter = new MarketBizRecyAdapter(MyBizListAct.this, marketBusinessList);
        myMarketRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myMarketRecyclerView.setNestedScrollingEnabled(false);
        myMarketRecyclerView.setClickable(true);
        myMarketRecyclerView.setAdapter(mAdapter);


        allMarketRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                loadMarketsFromQb();
            }
        });

        allMarketRefreshLayout.setColorSchemeResources(R.color.color_new_blue, R.color.random_color_2, R.color.random_color_3, R.color.random_color_7);

    }
    public void loadMarketsFromQb() {
        marketRecyclerView = findViewById(R.id.markets_list);
        text_No = findViewById(R.id.text_No);
        marketBusinessList= new ArrayList<>();
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        awajima= new Awajima();
        marketBizDAO= new MarketBizDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson1.fromJson(json1, Awajima.class);
        if(userProfile !=null){
            profID=userProfile.getPID();
        }
        if(marketBizDAO !=null){
            try {
                marketBusinessList=marketBizDAO.getBusinessesFromProfile(profID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        marketRecyclerView.setLayoutManager(new LinearLayoutManager(MyBizListAct.this, LinearLayoutManager.VERTICAL, false));
        Collections.shuffle(marketBusinessList, new Random(System.currentTimeMillis()));
        marketBizRecyAdapter = new MarketBizRecyAdapter(MyBizListAct.this, marketBusinessList);
        marketRecyclerView.setItemAnimator(new DefaultItemAnimator());
        marketRecyclerView.setNestedScrollingEnabled(false);
        marketRecyclerView.setClickable(true);
        marketRecyclerView.setAdapter(marketBizRecyAdapter);
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
    protected void onStart() {
        super.onStart();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }


    @Override
    public void onBizClick(MarketBusiness marketBusiness) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("MarketBusiness",marketBusiness);
        Intent loginRIntent = new Intent(MyBizListAct.this, BizDetailsAct.class);
        loginRIntent.putExtras(bundle);
        loginRIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginRIntent);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        if(position>0){
            bundle.putInt("position",position);
            Intent loginRIntent = new Intent(MyBizListAct.this, BizDetailsAct.class);
            loginRIntent.putExtras(bundle);
            loginRIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginRIntent);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);

        }


    }
}