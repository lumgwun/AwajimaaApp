package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.GroupAcctAdapter;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class GrpSavingsAct extends AppCompatActivity implements GroupAcctAdapter.ItemListener{
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private Button home, settings;

    private RecyclerView recyclerView;

    private ArrayList<GroupAccount> groupAccountArrayList;
    private GroupAcctAdapter mAdapter;

    DBHelper dbHelper;
    String json;
    TextView getTxtTitleMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_grp_acct);
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.grp_fab2);
        long profileID =userProfile.getPID();
        recyclerView = findViewById(R.id.recycler_view_grpAcct);
        getTxtTitleMessage = findViewById(R.id.grp_tittle_txt);
        groupAccountArrayList = new ArrayList<GroupAccount>();
        mAdapter = new GroupAcctAdapter(this, R.layout.grp_acct_row, groupAccountArrayList);

        dbHelper = new DBHelper(this);

        groupAccountArrayList = dbHelper.getAllGroupAcctList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.isAnimating();
    }

    @Override
    public void onItemClick(GroupAccount groupAccount) {

    }
}