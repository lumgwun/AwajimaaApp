package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketDAO;
import com.skylightapp.MarketClasses.GridSpacingDeco;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MarketHub extends AppCompatActivity implements MarketAdapter.OnMarketItemsClickListener {
    ArrayList<Market> marketArrayList;
    private EditText etSearch;
    private ImageView btn_close1,btn_search1,btn_close2;
    private TextView tv_languages;
    private RelativeLayout lout_2,lout_1;
    private String keyWord;
    private static MarketAdapter mAdapter;
    private DBHelper dbHelper;
    private int marketCount;
    int spanCount = 3; // 3 columns
    int spacing = 50; // 50px
    boolean includeEdge = true;
    private SQLiteDatabase sqLiteDatabase;
    private static final String PREF_NAME = "skylight";
    SharedPreferences userPreferences;
    Gson gson;
    String json,dateOfTranx,SharedPrefRole,SharedPrefUserName,SharedPrefUser,SharedPrefPassword;
    int profileUID,SharedPrefProfileID,SharedPrefCusID;
    private Profile userProfile;
    private MarketDAO marketDAO;
    public static final int SELECT_MARKET = 10;
    public static final int MANAGE_MARKET = 190;

    private int mode ;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_hub);
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        marketDAO= new MarketDAO(this);
        RecyclerView marketRecyclerView = (RecyclerView)findViewById(R.id.markets_list);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        marketRecyclerView.addItemDecoration(new GridSpacingDeco(spanCount, spacing, includeEdge));
        marketRecyclerView.setLayoutManager(gridLayout);
        marketRecyclerView.setHasFixedSize(true);
        marketArrayList = new ArrayList<Market>();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUser=userPreferences.getString("machine", "");
        SharedPrefRole = userPreferences.getString("PROFILE_ROLE", "");
        mAdapter = new MarketAdapter(this, getTestData());
        marketRecyclerView.setAdapter(mAdapter);
        etSearch = findViewById(R.id.et_search_market);
        btn_close1 = findViewById(R.id.btn_close_market);
        btn_close2 = findViewById(R.id.btnMarket_close_);
        btn_search1 = findViewById(R.id.btn_search_market);
        lout_2 = findViewById(R.id.lout_2_market);
        lout_1 = findViewById(R.id.lout_1_market);
        tv_languages = findViewById(R.id.tv_lang);
        listeners();
        //getTestData();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();


        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void listeners() {

        dbHelper= new DBHelper(this);
        marketArrayList = new ArrayList<Market>();
        getTestData();
        mAdapter = new MarketAdapter(this, getTestData());
        marketCount=marketArrayList.size();
        etSearch = findViewById(R.id.et_searchCom);
        btn_close1 = findViewById(R.id.btn_close1);
        btn_close2 = findViewById(R.id.btn_close2);
        btn_search1 = findViewById(R.id.btn_search1);
        lout_2 = findViewById(R.id.lout_2);
        lout_1 = findViewById(R.id.lout_1);
        tv_languages = findViewById(R.id.tv_languages);


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
                    mAdapter.updateItems(marketArrayList);
                } else {
                    searchMarkets();
                }


            }
        });

        btn_search1.setOnClickListener(v -> {


            lout_1.setVisibility(View.GONE);
            lout_2.setVisibility(View.VISIBLE);


        });
        btn_close2.setOnClickListener(v -> {
            lout_1.setVisibility(View.VISIBLE);
            lout_2.setVisibility(View.GONE);

        });

        btn_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        if(mAdapter !=null){
            mAdapter.setWhenClickListener(new MarketAdapter.OnMarketItemsClickListener() {
                @Override
                public void onItemClick(Market market) {

                }
            });

        }


    }


    public List<Market> getTestData() {
        marketArrayList = new ArrayList<Market>();
        marketArrayList.add(new Market(R.drawable.ic_coll, "Creek Road Market", "Fish and Sea food Market","Phalga","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Onitsha Market", "Fish and Sea food Market","Oni","Anambra State"));
        marketArrayList.add(new Market(R.drawable.ic_coll, "Ariaria Market", "Fabic Market","Aba","Abia State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Oil mill Market", "Mixed Market","Phalga","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_coll, "Bush Market", "Food Items Market","Etche","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Wuse Market", "Mixed Market","Phalga","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.ic_coll, "Nyanya Market", "General Market","Phalga","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Sabon geri Market", "Food Stuff Market","Sabon","Kano State"));
        marketArrayList.add(new Market(R.drawable.ic_coll, "Alaba Int'l Market", "Tech Items, And Computer","Ikeja","Lagos"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Mararaba Market", "Mixed Market","","FCT"));
        marketArrayList.add(new Market(R.drawable.ic_coll, "Nkwo Nnewi Market", "Mixed Market","Nnewi","Anambra"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Jos Main Market", "Mixed Market","Plateau","Plateau"));
        marketArrayList.add(new Market(R.drawable.ic_coll, "Kurmi Market", "Mixed Market","Kano","Kano"));
        marketArrayList.add(new Market(R.drawable.oyo_state, "Oja-Oba Market", "Mixed Market","Ibadan","Oyo"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Ogbete Main Market", "Mixed Market","Enugu","Enugu"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Kasuwar Kwari Market", "Mixed Market","Kano","Kano"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Kaduna Central Market", "Mixed Market","","Kaduna"));
        marketArrayList.add(new Market(R.drawable.oyo_state, "Gbagi Market", "Mixed Market","Ibadan","Oyo"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Nchia Market", "Mixed Market","Eleme","Rivers"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Idumota Market", "Mixed Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Zaki Biam Market", "Yam Market","","Benue"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Nkwo Nnewi Market", "Mixed Market","","Anambra"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Maitama Market", "Farmer Market","","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Oluwo Market", "Fish Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Mile 1 Market", "Clothes Market","Phalga","Rivers"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "New Market", "Mixed Market","Aba","Abia"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Ajah Market", "Mixed Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.oyo_state, "Aleshinloye Market", "Mixed Market","Ibadan","Oyo"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Alade Market", "Mixed Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Ekpe Market", "Fish Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Bar Beach Market", "Mixed Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Mile 12 Market", "Mixed Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.oyo_state, "Bodija Market", "Mixed Market","","Oyo"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Ladipo Market", "Mixed Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Onyingbo Market", "Mixed Market","Yaba","Lagos"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Balogun Ajeniya Market", "Mixed Market","","Lagos"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Wuse Market", "Mixed Market","FCT","Abuja"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Gboko Market", "Yam Market","Gboko","Benue State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Ugba Int'l Market", "Yam Market","","Benue State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Garatu Market", "Yam Market","Minna","Niger State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Serikin Pawa Market", "Mixed Market","Phalga","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.nassarawa_state, "Pwada Market", "Mixed Market","","Nassarawa"));
        marketArrayList.add(new Market(R.drawable.nassarawa_state, "Kuta Market", "Mixed Market","","Nassarawa"));
        marketArrayList.add(new Market(R.drawable.nassarawa_state, "Beji Market", "Mixed Market","","Nassarawa"));
        marketArrayList.add(new Market(R.drawable.nassarawa_state, "Agyeragu Market", "Mixed Market","","Nassarawa"));
        marketArrayList.add(new Market(R.drawable.nassarawa_state, "Yam Store Market", "Yam Market","","Nassarawa"));
        marketArrayList.add(new Market(R.drawable.nassarawa_state, "Saki Market", "Mixed Market","","Nassarawa"));
        marketArrayList.add(new Market(R.drawable.oyo_state, "Beji Market", "Mixed Market","","Oyo State"));
        marketArrayList.add(new Market(R.drawable.nassarawa_state, "Bwari Market", "Mixed Market","","Nassarawa"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Beji Market", "Mixed Market","","Abuja"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Oron Market", "Sea Food Market","Oron","Akwa-Ibom"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Ikot-Abasi Market", "Mixed Market","Ikot-Abasi","Akwa-Ibom"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Ete Market", "Mixed Market","Ete","Akwa-Ibom"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Elokpon Seafood Market", "Sea Food Market","Eastern Obolo","Akwa-Ibom"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Kaa Market", "Sea Food and others ","Khana LGA","Rivers State "));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Fruit Garden Market", "Fruits Market","Port Harcourt","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Mile 3 Market", "Mixed Market ","Khana LGA","Rivers State "));
        saveInDatabase(marketArrayList);
        return marketArrayList;
    }
    protected void saveInDatabase(ArrayList<Market> marketArrayList){
        marketDAO= new MarketDAO(this);
        marketDAO.SaveMarketArrayList(marketArrayList);

    }
    public static  void refreshItem(int deSelect, int select){
        mAdapter.notifyItemChanged(deSelect);
        mAdapter.notifyItemChanged(select);
    }



    private void searchMarkets() {
        if(marketArrayList !=null){
            marketArrayList.clear();

        }


        if (marketArrayList != null) {
            for (int i = 0; i < marketArrayList.size(); i++) {

                if (marketArrayList.get(i).getMarketName().toLowerCase().contains(keyWord)) {
                    Market market = new Market();
                    market.setMarketName(marketArrayList.get(i).getMarketName());
                    market.setCommodityCount(marketArrayList.get(i).getCommodityCount());
                    market.setMarketLGA(marketArrayList.get(i).getMarketLGA());
                    market.setMarketAddress(marketArrayList.get(i).getMarketAddress());
                    market.setMarketRevenue(marketArrayList.get(i).getMarketRevenue());
                    market.setMarketLogo(marketArrayList.get(i).getMarketLogo());
                    market.setMarketID(marketArrayList.get(i).getMarketID());
                    mAdapter.addItem(market);
                }
            }
        }
        mAdapter.updateItems(marketArrayList);
    }


    @Override
    public void onItemClick(Market market) {
        //market = marketArrayList.get(position);
        Bundle bundle= new Bundle();
        bundle.putParcelable("Market",market);
        bundle.putInt("MODE",SELECT_MARKET);
        bundle.putInt("Manage",MANAGE_MARKET);
        Intent intent=new Intent(MarketHub.this, MarketDetailsAct.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);

    }
}