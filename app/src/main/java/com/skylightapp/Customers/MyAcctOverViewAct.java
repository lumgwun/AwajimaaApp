package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.gson.Gson;
import com.skylightapp.Adapters.AccountRecylerAdap;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class MyAcctOverViewAct extends AppCompatActivity implements  AccountRecylerAdap.OnAcctClickListener{
    private RecyclerView recyclerView;

    private ArrayList<Account> accountArrayList;
    private AccountRecylerAdap mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private Customer customer;
    private static final String PREF_NAME = "awajima";
    private Gson gson1;
    private String json1;
    private Profile userProfile;
    private int profileID,cusID;
    private SQLiteDatabase sqLiteDatabase;
    private AcctDAO acctDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_accts);
        recyclerView = findViewById(R.id.recycler_OverviewAcct);
        userProfile=new Profile();
        customer=new Customer();
        acctDAO= new AcctDAO(this);
        accountArrayList= new ArrayList<>();
        dbHelper = new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        profileID=userPreferences.getInt("PROFILE_ID",0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        if(customer !=null){
            cusID=customer.getCusUID();

        }

        if (dbHelper != null) {
            sqLiteDatabase = dbHelper.getReadableDatabase();
            try {
                accountArrayList=acctDAO.getAccountsFromCurrentCustomer(cusID);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        mAdapter = new AccountRecylerAdap(this, R.layout.account_list_row,accountArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onAcctClicked(Account account) {

    }

    @Override
    public void onListItemClick(int index) {

    }
}