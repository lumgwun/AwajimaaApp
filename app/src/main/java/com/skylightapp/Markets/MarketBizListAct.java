package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MarketBizListAct extends AppCompatActivity {
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
    private static final String PREF_NAME = "skylight";
    SharedPreferences userPreferences;
    Gson gson, gson1;
    String json, json1, nIN;
    Profile userProfile, customerProfile, lastProfileUsed;
    private int profID;
    private MarketBizRecyAdapter marketBizRecyAdapter;
    private MarketBizDAO marketBizDAO;
    private TextView text_No;
    private int marketBizCount,myMarketBizCount;
    String selectedCountry, selectedBank, bankName, bankNumber, officePref, userNamePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_biz_list);
        dbHelper= new DBHelper(this);
        marketBizDAO= new MarketBizDAO(this);
        marketRecyclerView = findViewById(R.id.markets_list);
        myMarketRecyclerView = findViewById(R.id.my_market_Biz);
        text_No = findViewById(R.id.text_No);
        /*GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        marketRecyclerView.setLayoutManager(gridLayout);
        marketRecyclerView.setHasFixedSize(true);*/
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

        marketBusinessList=marketBizDAO.getBusinessesFromProfile(profID);

        myMarketRecyclerView.setLayoutManager(new LinearLayoutManager(MarketBizListAct.this, LinearLayoutManager.HORIZONTAL, false));
        Collections.shuffle(marketBusinessList, new Random(System.currentTimeMillis()));
        mAdapter = new MarketBizRecyAdapter(MarketBizListAct.this, marketBusinessList);
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
        marketBizDAO= new MarketBizDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            profID=userProfile.getPID();
        }

        marketBusinessList=marketBizDAO.getBusinessesFromProfile(profID);
        marketRecyclerView.setLayoutManager(new LinearLayoutManager(MarketBizListAct.this, LinearLayoutManager.VERTICAL, false));
        Collections.shuffle(marketBusinessList, new Random(System.currentTimeMillis()));
        marketBizRecyAdapter = new MarketBizRecyAdapter(MarketBizListAct.this, marketBusinessList);
        marketRecyclerView.setItemAnimator(new DefaultItemAnimator());
        marketRecyclerView.setNestedScrollingEnabled(false);
        marketRecyclerView.setClickable(true);
        marketRecyclerView.setAdapter(marketBizRecyAdapter);
    }
}