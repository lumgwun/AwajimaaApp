package com.skylightapp.Markets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ActionTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.interfaces.TouchListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.snackbar.Snackbar;
import com.skylightapp.Classes.SwipeToDeleteCallback;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketCommodity;
import com.skylightapp.MarketClasses.CommodityAdater;
import com.skylightapp.R;


import java.util.ArrayList;
import java.util.List;

public class CommodityAct extends AppCompatActivity implements CommodityAdater.OnCommodityClickListener,SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    CommodityAdater mAdapter,sliderAdapter,searchComAdapter;
    ArrayList<MarketCommodity> marketCommodityArrayList;
    ArrayList<MarketCommodity> marketCommoditySearchList;
    List<MarketCommodity> marketCommodityList;
    ScrollView scrollView;
    private Bundle bundle;
    private int marketID;
    private DBHelper dbHelper;
    private SearchView iSearchView;
    private MarketCommodity marketCommodity;

    private SearchManager manager;
    private androidx.appcompat.widget.SearchView searchView;
    //SearchView editsearch;
    private EditText etSearch;
    private ImageView btn_close1,btn_search1,btn_close2;
    private TextView tv_languages;
    private RelativeLayout lout_2,lout_1;
    private String keyWord;
    private CommodityAdater.OnCommodityClickListener listener;
    private ArrayList<SlideModel> slideModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_commodity);
        dbHelper= new DBHelper(this);
        etSearch = findViewById(R.id.et_searchCom);
        btn_close1 = findViewById(R.id.btn_close1);
        btn_close2 = findViewById(R.id.btn_close2);
        btn_search1 = findViewById(R.id.btn_search1);
        lout_2 = findViewById(R.id.lout_2);
        lout_1 = findViewById(R.id.lout_1);
        tv_languages = findViewById(R.id.tv_languages);
        recyclerView = findViewById(R.id.recyComm);
        scrollView = findViewById(R.id.cordComm);
        populateRecyclerView();
        slideModels= new ArrayList<>();
        bundle= new Bundle();
        enableSwipeToDeleteAndUndo();
        bundle=getIntent().getExtras();
        if(bundle !=null){
            marketID=bundle.getInt("MarketID");
        }
        listeners();
        marketCommodityArrayList = new ArrayList<>();
        marketCommoditySearchList = new ArrayList<>();
        marketCommodity = new MarketCommodity();

        ImageSlider sliderView = findViewById(R.id.commodity_Slider);
        manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        sliderAdapter = new CommodityAdater(CommodityAct.this, marketCommoditySearchList,listener);
        etSearch.setActivated(true);
        sliderView.setImageList(slideModels);
        sliderView.startSliding(3000);
        sliderView.setImageList(slideModels, ScaleTypes.CENTER_CROP);
        sliderView.animate();

        sliderView.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {

            }
        });
        sliderView.setTouchListener(new TouchListener() {
            @Override
            public void onTouched(@NonNull ActionTypes actionTypes) {
                if (actionTypes == ActionTypes.DOWN){
                    sliderView.stopSliding();
                } else if (actionTypes == ActionTypes.UP ) {
                    sliderView.startSliding(1000);
                }

            }
        });


    }
    private void populateRecyclerView() {
        marketCommodityArrayList = new ArrayList<>();
        dbHelper= new DBHelper(this);
        marketCommodityArrayList.add(new MarketCommodity("Yam","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        marketCommodityArrayList.add(new MarketCommodity("Onions","","it is rich in carbohydrate",R.drawable.ic__category));
        marketCommodityArrayList.add(new MarketCommodity("Green","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        marketCommodityArrayList.add(new MarketCommodity("Rice","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        marketCommodityArrayList.add(new MarketCommodity("Garlic","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        marketCommodityArrayList.add(new MarketCommodity("Gorontola","Native","it is rich in carbohydrate",R.drawable.ic__category));


        /*for (int i = 0; i < 10; i++) {
            marketCommodityArrayList.add(i+" -- \n"+getString(R.string.lorem_1));
        }*/

        mAdapter = new CommodityAdater(marketCommodityArrayList);
        recyclerView.setAdapter(mAdapter);

    }
    private void listeners() {
        marketCommoditySearchList = new ArrayList<>();
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
                    searchComAdapter.updateItems(marketCommoditySearchList);
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
        if(searchComAdapter !=null){
            searchComAdapter.setWhenClickListener(new CommodityAdater.OnCommodityClickListener() {
                @Override
                public void onCommodityClick(MarketCommodity marketCommodity) {

                }
            });

        }


    }


    private void searchCommodities() {
        if(marketCommoditySearchList !=null){
            marketCommoditySearchList.clear();

        }


        for (int i = 0; i < marketCommoditySearchList.size(); i++) {

            if (marketCommoditySearchList.get(i).getCommodityName().toLowerCase().contains(keyWord)) {
                MarketCommodity marketCommodity = new MarketCommodity();
                marketCommodity.setCommodityName(marketCommoditySearchList.get(i).getCommodityName());
                marketCommodity.setCommodityID(marketCommoditySearchList.get(i).getCommodityID());
                searchComAdapter.addItem(marketCommodity);
            }
        }
        searchComAdapter.updateItems(marketCommoditySearchList);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final MarketCommodity item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);
                Snackbar snackbar = Snackbar
                        .make(scrollView, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
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
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
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

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        mAdapter.filter(text);
        return false;
    }

    @Override
    public void onCommodityClick(MarketCommodity marketCommodity) {

    }
}