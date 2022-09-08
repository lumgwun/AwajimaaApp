package com.skylightapp.Markets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MyStockAdapter;
import com.skylightapp.R;

import java.util.List;

public class MyMStocksListAct extends ListActivity {
    private DBHelper db;
    private MyStockAdapter myStockAdapter;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    int profileUID;
    Bundle bundle;
    private static final String PREF_NAME = "skylight";
    private Profile userProfile;
    private FloatingActionButton floatingActionButtonAdd;
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;

    private AdapterView.OnItemLongClickListener onListItemLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
            final ListView l = (ListView)parent;
            final int position = pos;

            new AlertDialog.Builder(MyMStocksListAct.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.delete_item)
                    .setMessage(R.string.delete_item_confrm_msg)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteMarketStock((MarketStock)l.getItemAtPosition(position));

                            regenerateProductsList(null);
                        }
                    })
                    .show();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_mstocks_list);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");

        this.db = new DBHelper(this);
        this.db.open();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View emptyList = inflater.inflate(R.layout.empty_list, null);
        floatingActionButtonAdd = findViewById(R.id.homeFabS);
        ListView lv = getListView();

        lv.setEmptyView(emptyList);
        lv.setOnItemLongClickListener(onListItemLongClick);
        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(MyMStocksListAct.this, AddMyStockAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(helpIntent);
            }
        });

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(emptyList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_stock_list_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                regenerateProductsList(null);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                regenerateProductsList(query);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.db.close();
        super.onStop();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, ProductDetailsAct.class);
        ((AppController) getApplication()).setCurrentProd((MarketStock) l.getItemAtPosition(position));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_stock: {
                Intent intent = new Intent(this, AddMyStockAct.class);
                startActivity(intent);
                break;
            }

            case R.id.action_stock_settings:{
                Intent intent = new Intent(this, StockSettingAct.class);
                startActivity(intent);
                break;
            }


            case R.id.action_stock_stats: {
                Intent intent = new Intent(this, TopStatsAct.class);
                startActivity(intent);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        regenerateProductsList(null);
        super.onResume();
    };

    private void regenerateProductsList(String filter) {
        List<MarketStock> products = db.getProductsList(filter);
        this.myStockAdapter = new MyStockAdapter(this, android.R.layout.simple_list_item_1, products);
        getListView().setAdapter(this.myStockAdapter);

    }
}