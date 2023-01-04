package com.skylightapp.Bookings;

import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Markets.MarketDetailsAct;
import com.skylightapp.R;

import java.util.ArrayList;

public class TransportComAct extends AppCompatActivity {
    ArrayList<MarketBusiness> marketBizS;
    private EditText etSearch;
    private ImageView btn_close1,btn_search1,btn_close2;
    private TextView txt_search_For;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transport_com);
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        marketBizS = new ArrayList<>();
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
        mAdapter = new MBizRecyA(TransportComAct.this, marketBizS);
        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if(marketBizDAO !=null){
                try {
                    marketBizS=marketBizDAO.getAllBusFromSubAndType("active","Transport Company");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }


        }
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
        marketBizS.add(new MarketBusiness("", "GUO Transport Co."));
        marketBizS.add(new MarketBusiness("", "Khome Global Logistics Ltd"));
        marketBizS.add(new MarketBusiness("", "Rivers Transport Company Limited"));
        marketBizS.add(new MarketBusiness("", "God is Good Motors"));
        marketBizS.add(new MarketBusiness("", "ABC Transport"));
        marketBizS.add(new MarketBusiness("", "Chisco Transport Company"));
        marketBizS.add(new MarketBusiness("", "Young Shall Grow Motors Ltd."));
        marketBizS.add(new MarketBusiness("", "Cross Country Motors"));
        marketBizS.add(new MarketBusiness("", "Ifesinachi Transport Ltd."));
        marketBizS.add(new MarketBusiness("", "Peace Mass transit"));
        marketBizS.add(new MarketBusiness("", "Bonny Way Motor"));
        marketBizS.add(new MarketBusiness("", "Benue Link"));
        marketBizS.add(new MarketBusiness("", "Iyare Motors"));
        marketBizS.add(new MarketBusiness("", "Libra Motors"));
        marketBizS.add(new MarketBusiness("", "E.Ekesons Transports"));
        marketBizS.add(new MarketBusiness("", "Agofure Transports"));
        marketBizS.add(new MarketBusiness("", "Delta line Motors"));
        marketBizS.add(new MarketBusiness("", "Efex Transports"));

        marketBizS.add(new MarketBusiness("", "Delta line Motors"));


        marketBizS.add(new MarketBusiness("", "Mediterranean Shipping Com. Nig Ltd"));
        marketBizS.add(new MarketBusiness("", "Bonny Way Motor"));
        marketBizS.add(new MarketBusiness("", "Metro Ferry"));
        marketBizS.add(new MarketBusiness("", "Toron Nigeria Limited"));
        marketBizS.add(new MarketBusiness("", "Transport Services Limited"));
        marketBizS.add(new MarketBusiness("", "Alibe & Sons Transport Com."));
        marketBizS.add(new MarketBusiness("", "Blue Cheetah Services"));
        marketBizS.add(new MarketBusiness("", "Ekili Haulage"));
        marketBizS.add(new MarketBusiness("", "Faith Motors"));
        marketBizS.add(new MarketBusiness("", "LAGBUS"));
        marketBizS.add(new MarketBusiness("", "Maids To Run Services"));
        marketBizS.add(new MarketBusiness("", "Marvel Transport"));
        marketBizS.add(new MarketBusiness("", "Mushilab Nigeria Limited"));
        marketBizS.add(new MarketBusiness("", "NoblePat Group"));
        marketBizS.add(new MarketBusiness("", "Safe Motorway"));
        marketBizS.add(new MarketBusiness("", "Saima Nigeria"));

        RecyclerView marketRecyclerView = (RecyclerView)findViewById(R.id.tc_recyler_list);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        marketRecyclerView.addItemDecoration(new GridSpacingDeco(spanCount, spacing, includeEdge));
        marketRecyclerView.setLayoutManager(gridLayout);
        marketRecyclerView.setHasFixedSize(true);
        marketRecyclerView.setAdapter(mAdapter);
        Bundle bundle= new Bundle();
        if(mAdapter !=null){
            mAdapter.setWhenClickListener(new MBizRecyA.OnItemsClickListener() {
                @Override
                public void onBizClick(MarketBusiness marketBusiness) {
                    bundle.putParcelable("MarketBusiness",marketBusiness);
                    Intent myIntent = new Intent(TransportComAct.this, LogisticDetailsAct.class);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                }


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

}