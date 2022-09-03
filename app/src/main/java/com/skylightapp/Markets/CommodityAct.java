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

import com.google.android.material.snackbar.Snackbar;
import com.skylightapp.Classes.SwipeToDeleteCallback;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class CommodityAct extends AppCompatActivity implements CommodityAdater.OnItemsClickListener,SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    CommodityAdater mAdapter,sliderAdapter,searchComAdapter;
    ArrayList<Commodity> commodityArrayList;
    ArrayList<Commodity> commoditySearchList;
    List<Commodity> commodityList;
    ScrollView scrollView;
    private Bundle bundle;
    private int marketID;
    private DBHelper dbHelper;
    private SearchView iSearchView;
    private Commodity commodity;

    private SearchManager manager;
    private androidx.appcompat.widget.SearchView searchView;
    //SearchView editsearch;
    private EditText etSearch;
    private ImageView btn_close1,btn_search1,btn_close2;
    private TextView tv_languages;
    private RelativeLayout lout_2,lout_1;
    private String keyWord;
    private CommodityAdater.OnItemsClickListener listener;


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
        bundle= new Bundle();
        enableSwipeToDeleteAndUndo();
        bundle=getIntent().getExtras();
        if(bundle !=null){
            marketID=bundle.getInt("MarketID");
        }
        listeners();
        commodityArrayList = new ArrayList<>();
        commoditySearchList = new ArrayList<>();
        commodity= new Commodity();
        SliderView sliderView = findViewById(R.id.commodity_Slider);
        manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        sliderAdapter = new CommodityAdater(CommodityAct.this, commoditySearchList,listener);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        sliderAdapter.notifyDataSetChanged();
        etSearch.setActivated(true);


    }
    private void populateRecyclerView() {
        commodityArrayList = new ArrayList<>();
        dbHelper= new DBHelper(this);
        commodityArrayList.add(new Commodity("Yam","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        commodityArrayList.add(new Commodity("Onions","","it is rich in carbohydrate",R.drawable.ic__category));
        commodityArrayList.add(new Commodity("Green","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        commodityArrayList.add(new Commodity("Rice","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        commodityArrayList.add(new Commodity("Garlic","White Yam","it is rich in carbohydrate",R.drawable.ic__category));
        commodityArrayList.add(new Commodity("Gorontola","Native","it is rich in carbohydrate",R.drawable.ic__category));


        /*for (int i = 0; i < 10; i++) {
            commodityArrayList.add(i+" -- \n"+getString(R.string.lorem_1));
        }*/

        mAdapter = new CommodityAdater(commodityArrayList);
        recyclerView.setAdapter(mAdapter);

    }
    private void listeners() {
        commoditySearchList = new ArrayList<>();
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
                    searchComAdapter.updateItems(commoditySearchList);
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
            searchComAdapter.setWhenClickListener(new CommodityAdater.OnItemsClickListener() {
                @Override
                public void onItemClick(Commodity commodity) {

                }
            });

        }


    }


    private void searchCommodities() {
        if(commoditySearchList !=null){
            commoditySearchList.clear();

        }


        for (int i = 0; i < commoditySearchList.size(); i++) {

            if (commoditySearchList.get(i).getCommodityName().toLowerCase().contains(keyWord)) {
                Commodity commodity = new Commodity();
                commodity.setCommodityName(commoditySearchList.get(i).getCommodityName());
                commodity.setCommodityID(commoditySearchList.get(i).getCommodityID());
                searchComAdapter.addItem(commodity);
            }
        }
        searchComAdapter.updateItems(commoditySearchList);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Commodity item = mAdapter.getData().get(position);

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
    public void onItemClick(Commodity commodity) {

    }
}