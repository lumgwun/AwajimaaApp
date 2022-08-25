package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MarketHub extends AppCompatActivity implements MarketAdapter.OnItemsClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_hub);
        RecyclerView marketRecyclerView = (RecyclerView)findViewById(R.id.markets_list);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        marketRecyclerView.setLayoutManager(gridLayout);
        marketRecyclerView.setHasFixedSize(true);
        MarketAdapter mAdapter = new MarketAdapter(this, getTestData());
        marketRecyclerView.setAdapter(mAdapter);
    }
    public List<Market> getTestData() {
        List<Market> marketArrayList = new ArrayList<Market>();
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Creek Road Market", "Fish and Sea food Market","Phalga","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Onitsha Market", "Fish and Sea food Market","Oni","Anambra State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Ariaria Market", "Fabic Market","Aba","Abia State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Oil mill Market", "Mixed Market","Phalga","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Bush Market", "Food Items Market","Etche","Rivers State"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Wuse Market", "Mixed Market","Phalga","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Nyanya Market", "General Market","Phalga","Abuja, FCT"));
        marketArrayList.add(new Market(R.drawable.ic_add_business, "Sabon geri Market", "Food Stuff Market","Sabon","Kano State"));
        return marketArrayList;
    }

    @Override
    public void onItemClick(Market market) {

    }
}