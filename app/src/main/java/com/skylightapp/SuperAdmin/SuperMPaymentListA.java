package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.PaymentAdapterSuper;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.R;
import com.skylightapp.Tellers.ManualPCodeUpdate;
import com.skylightapp.Tellers.ManualPaymentList;

import java.util.ArrayList;

public class SuperMPaymentListA extends AppCompatActivity implements PaymentAdapterSuper.OnItemsClickListener{
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private Button home, settings;

    private RecyclerView recyclerView;

    private ArrayList<Payment> paymentArrayList;
    private PaymentAdapterSuper mAdapter;
    private SQLiteDatabase sqLiteDatabase;

    DBHelper dbHelper;
    String json;
    long profileID;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_payment_list);
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.notification_Super);
        //profileID =userProfile.getuID();
        recyclerView = findViewById(R.id.recycler_vPayment);
        paymentArrayList = new ArrayList<Payment>();
        PaymentDAO paymentDAO = new PaymentDAO(this);

        dbHelper = new DBHelper(this);



        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            try {
                if(paymentDAO !=null){
                    paymentArrayList = paymentDAO.getALLPaymentsSuper();

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        mAdapter = new PaymentAdapterSuper(this, R.layout.super_payment_row, paymentArrayList);




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(Payment payment) {
        Bundle userBundle = new Bundle();
        userBundle.putParcelable("Payment",payment);
        Intent listIntentSper = new Intent(SuperMPaymentListA.this, ManualPCodeUpdate.class);
        listIntentSper.putExtras(userBundle);
        startActivity(listIntentSper);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.due_payment_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchPayment)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        if (id == R.id.action_searchPayment) {
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
}