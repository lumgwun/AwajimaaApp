package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MarketHub extends AppCompatActivity implements MarketAdapter.OnMarketItemsClickListener {
    List<Market> marketArrayList;
    private EditText etSearch;
    private ImageView btn_close1,btn_search1,btn_close2;
    private TextView tv_languages;
    private RelativeLayout lout_2,lout_1;
    private String keyWord;
    private MarketAdapter mAdapter;
    private DBHelper dbHelper;
    private int marketCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_hub);
        dbHelper= new DBHelper(this);
        RecyclerView marketRecyclerView = (RecyclerView)findViewById(R.id.markets_list);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        marketRecyclerView.setLayoutManager(gridLayout);
        marketRecyclerView.setHasFixedSize(true);
        marketArrayList = new ArrayList<Market>();
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
        getTestData();
    }
    public List<Market> getTestData() {
        marketArrayList = new ArrayList<Market>();
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Creek Road Market", "Fish and Sea food Market","Phalga","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Onitsha Market", "Fish and Sea food Market","Oni","Anambra State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Ariaria Market", "Fabic Market","Aba","Abia State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Oil mill Market", "Mixed Market","Phalga","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Bush Market", "Food Items Market","Etche","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Wuse Market", "Mixed Market","Phalga","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Nyanya Market", "General Market","Phalga","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Sabon geri Market", "Food Stuff Market","Sabon","Kano State"));
        marketArrayList.add(new Market(R.drawable.lagos_state, "Alaba Int'l Market", "Tech Items, And Computer","Ikeja","Lagos"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Mararaba Market", "Mixed Market","","FCT"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Nkwo Nnewi Market", "Mixed Market","Nnewi","Anambra"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Jos Main Market", "Mixed Market","Plateau","Plateau"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Kurmi Market", "Mixed Market","Kano","Kano"));
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


        return marketArrayList;
    }
    private void listeners() {

        dbHelper= new DBHelper(this);
        marketArrayList = new ArrayList<Market>();
        dbHelper.openDataBase();
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
                    searchCommodities();
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


    private void searchCommodities() {
        if(marketArrayList !=null){
            marketArrayList.clear();

        }


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
        mAdapter.updateItems(marketArrayList);
    }


    @Override
    public void onItemClick(Market market) {
        //market = marketArrayList.get(position);

    }
}