package com.skylightapp.Bookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.LogisticDetailsAct;
import com.skylightapp.MarketClasses.GridSpacingDeco;
import com.skylightapp.MarketClasses.MBizRecyA;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.util.ArrayList;

public class LogisticsComLAct extends AppCompatActivity {
    ArrayList<MarketBusiness> marketBizS;
    ArrayList<MarketBusiness> marketBizAll;
    private AppCompatEditText etSearch;
    private AppCompatImageView btn_close1,btn_search1,btn_close2;
    private AppCompatTextView txt_search_For;
    private RelativeLayout lout_2,lout_1;
    private String keyWord;
    private static MBizRecyA mAdapter;
    private DBHelper dbHelper;
    private int marketCount;
    int spanCount = 3; // 3 columns
    int spacing = 50; // 50px
    boolean includeEdge = true;
    private SQLiteDatabase sqLiteDatabase;
    private static final String PREF_NAME = "awajima";
    SharedPreferences userPreferences;
    Gson gson;
    String json,dateOfTranx,SharedPrefRole,SharedPrefUserName,SharedPrefUser,SharedPrefPassword;
    int profileUID,SharedPrefProfileID,SharedPrefCusID;
    private Profile userProfile;
    private MarketBizDAO marketBizDAO;
    public static final int SELECT_MARKET = 10;
    public static final int MANAGE_MARKET = 190;
    private int indexr=0;
    private RecyclerView marketRecyclerView;
    private boolean isSelected=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transport_com);
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        marketRecyclerView = (RecyclerView)findViewById(R.id.tc_recyler_list);
        gson = new Gson();
        marketBizS = new ArrayList<>();
        marketBizAll = new ArrayList<>();
        userProfile=new Profile();
        marketBizDAO= new MarketBizDAO(this);
        etSearch = findViewById(R.id.et_search_biz);
        btn_close1 = findViewById(R.id.btn_close_biz);
        btn_close2 = findViewById(R.id.btn_biz_close);
        btn_search1 = findViewById(R.id.btn_search_biz);
        lout_2 = findViewById(R.id.lout_2_biz);
        lout_1 = findViewById(R.id.lout_1_biz);
        txt_search_For = findViewById(R.id.txt_search);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUser=userPreferences.getString("machine", "");
        SharedPrefRole = userPreferences.getString("PROFILE_ROLE", "");

        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "GUO Transport Co.","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Khome Global Logistics Ltd","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Rivers Transport Company Limited","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "God is Good Motors","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "ABC Transport","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Chisco Transport Company","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Young Shall Grow Motors Ltd.","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Cross Country Motors","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Ifesinachi Transport Ltd.","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Peace Mass transit","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Bonny Way Motor","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Benue Link","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Iyare Motors","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Libra Motors","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "E.Ekesons Transports","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Agofure Transports","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Delta line Motors","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Efex Transports","Transport Company"));

        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Delta line Motors","Transport Company"));


        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Mediterranean Shipping Com. Nig Ltd","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Bonny Way Motor","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Metro Ferry","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Toron Nigeria Limited","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Transport Services Limited","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Alibe & Sons Transport Com.","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Blue Cheetah Services","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Ekili Haulage","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Faith Motors","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "LAGBUS","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Maids To Run Services","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Marvel Transport","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Mushilab Nigeria Limited","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "NoblePat Group","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Safe Motorway","Transport Company"));
        marketBizS.add(new MarketBusiness(R.drawable.ifesinachi, "Saima Nigeria","Transport Company"));


        mAdapter = new MBizRecyA(LogisticsComLAct.this,R.layout.biz_row, marketBizS);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        marketRecyclerView.addItemDecoration(new GridSpacingDeco(spanCount, spacing, includeEdge));
        marketRecyclerView.setLayoutManager(gridLayout);
        marketRecyclerView.setHasFixedSize(true);
        marketRecyclerView.setAdapter(mAdapter);

        try {
            try {
                marketBizAll=marketBizDAO.getAllBusFromSubAndType("active","Transport Company");



            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        /*if(marketBizAll !=null){
            marketBizS.addAll(marketBizAll);

            mAdapter = new MBizRecyA(LogisticsComLAct.this, marketBizS);
            GridLayoutManager gridLayout55 = new GridLayoutManager(this, 2);
            marketRecyclerView.addItemDecoration(new GridSpacingDeco(spanCount, spacing, includeEdge));
            marketRecyclerView.setLayoutManager(gridLayout55);
            marketRecyclerView.setHasFixedSize(true);
            marketRecyclerView.setAdapter(mAdapter);

        }*/


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
                    mAdapter.updateItems(marketBizS);
                } else {
                    searchForBiz();
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

        Bundle bundle= new Bundle();
        if(mAdapter !=null){
            mAdapter.setWhenClickListener(new MBizRecyA.OnItemsClickListener() {
                @Override
                public void onBizClick(MarketBusiness marketBusiness) {
                    if(isSelected){
                        bundle.putParcelable("MarketBusiness",marketBusiness);
                        Intent myIntent = new Intent(LogisticsComLAct.this, LogisticDetailsAct.class);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.putExtras(bundle);
                        startActivity(myIntent);

                    }

                }

                /*@Override
                public void onItemClick(int position) {
                    if(indexr==position){
                        return;
                    }else{
                        bundle.putInt("position",position);
                        Intent myIntent = new Intent(LogisticsComLAct.this, LogisticDetailsAct.class);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.putExtras(bundle);
                        startActivity(myIntent);

                    }

                }*/


            });

        }

    }
    private void searchForBiz() {
        if(marketBizS !=null){
            marketBizS.clear();

        }


        if (marketBizS != null) {
            for (int i = 0; i < marketBizS.size(); i++) {

                if (marketBizS.get(i).getBizBrandname().toLowerCase().contains(keyWord)) {
                    MarketBusiness business = new MarketBusiness();
                    business.setBizBrandname(marketBizS.get(i).getBizBrandname());
                    business.setBizPicture(marketBizS.get(i).getBizPicture());
                    business.setBizID(marketBizS.get(i).getBusinessID());
                    business.setBizDescription(marketBizS.get(i).getBizDescription());
                    mAdapter.addItem(business);
                }
            }
        }
        mAdapter.updateItems(marketBizS);
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}