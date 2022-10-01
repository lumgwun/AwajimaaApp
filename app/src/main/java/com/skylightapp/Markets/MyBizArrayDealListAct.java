package com.skylightapp.Markets;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyBizArrayDealListAct extends AppCompatActivity implements MarketBizRecyAdapter.OnItemsClickListener,MarketBizRecyAdapter.OnClickListener{
    private ArrayList<BusinessDeal> businessDealArrayList;
    private MarketBizRecyAdapter.OnClickListener mbListener;
    List<BusinessDeal> businessDealList;
    private MarketAdapter marketAdapter;

    private MarketBizRecyAdapter marketBizAdapter;

    private RecyclerView recyclerViewDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_biz_deal_list);
        businessDealList = new ArrayList<>();
        //recyclerViewDeals = findViewById(R.id.fab_ex);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(MarketBusiness marketBusiness) {

    }
}