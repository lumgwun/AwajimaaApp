package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.GrpTranxAdapter;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class GrpProfileTraxs extends AppCompatActivity implements  GrpTranxAdapter.OnItemsClickListener{
    private GrpTranxAdapter grpTranxAdapter;
    private GrpTranxAdapter grpTranxAdap;

    private ArrayList<Transaction> transactionArrayList;
    private ArrayList<Transaction> tranxArrayList;

    DBHelper dbHelper;
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    long profileID, grpAcctID;
    Random ran ;
    AppCompatTextView txtNoGrpSavingsUsers;
    private GroupAccount groupAccount;
    private TranXDAO tranXDAO;
    private  int tranxCount;
    private static final String PREF_NAME = "awajima";
    private RecyclerView recyclerView;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_grp_profile_traxs);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkInternetConnection();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_GrpSTx);
        txtNoGrpSavingsUsers =  findViewById(R.id.noGrpSavingsTx);

        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();
        tranXDAO= new TranXDAO(this);
        transactionArrayList= new ArrayList<>();
        tranxArrayList= new ArrayList<>();
        dbHelper = new DBHelper(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        Bundle bundle = getIntent().getExtras() ;

        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getReadableDatabase();
            tranxArrayList = tranXDAO.getAllTransactionAdmin();

        }
        grpTranxAdap = new GrpTranxAdapter(GrpProfileTraxs.this, tranxArrayList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_GrpSTx);
        txtNoGrpSavingsUsers =  findViewById(R.id.noGrpSavingsTx);
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        recyclerView.setAdapter(grpTranxAdap);
        recyclerView.setClickable(true);

        if(grpTranxAdap !=null){
            tranxCount =grpTranxAdap.getItemCount();

        }
        if(tranxCount==0){
            txtNoGrpSavingsUsers.setVisibility(View.VISIBLE);
            txtNoGrpSavingsUsers.setText("Sorry no Group Transactions, yet!");
            recyclerView.setVisibility(View.GONE);

        }
        if(tranxCount>0){
            txtNoGrpSavingsUsers.setVisibility(View.VISIBLE);
            txtNoGrpSavingsUsers.setText("Group Tranx:"+tranxCount);
            recyclerView.setVisibility(View.VISIBLE);

        }




        if(bundle !=null){
            groupAccount = bundle.getParcelable("GroupAccount");
            grpAcctID=groupAccount.getGrpAcctNo();
            if(dbHelper !=null){
                sqLiteDatabase = dbHelper.getReadableDatabase();
                transactionArrayList = tranXDAO.getAllGrpAcctTranxs(grpAcctID);

            }

            grpTranxAdapter = new GrpTranxAdapter(GrpProfileTraxs.this, transactionArrayList);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_GrpSTx);
            txtNoGrpSavingsUsers =  findViewById(R.id.noGrpSavingsTx);
            final CarouselLayoutManager layoutManagerCar = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
            recyclerView.setLayoutManager(layoutManagerCar);
            recyclerView.addOnScrollListener(new CenterScrollListener());
            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

            recyclerView.setAdapter(grpTranxAdapter);
            recyclerView.setClickable(true);
            if(grpTranxAdapter !=null){
                tranxCount =grpTranxAdapter.getItemCount();

            }
            if(tranxCount==0){
                txtNoGrpSavingsUsers.setVisibility(View.VISIBLE);
                txtNoGrpSavingsUsers.setText("Sorry no Group Transactions for this User, yet!");
                recyclerView.setVisibility(View.GONE);

            }
            if(tranxCount>0){
                txtNoGrpSavingsUsers.setVisibility(View.VISIBLE);
                txtNoGrpSavingsUsers.setText("Group Tranx:"+tranxCount);
                recyclerView.setVisibility(View.VISIBLE);

            }
            recyclerView.addOnItemTouchListener(new MyTouchListener(this,
                    recyclerView,
                    new MyTouchListener.OnTouchActionListener() {
                        @Override
                        public void onLeftSwipe(View view, int position) {//code as per your need
                        }

                        @Override
                        public void onRightSwipe(View view, int position) {//code as per your need
                        }

                        @Override
                        public void onClick(View view, int position) {//code as per your need
                        }
                    }));


        }




    }

    public void goHome(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }


    @Override
    public void onItemClick(Transaction transaction) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        bundle.putParcelable("Transaction", transaction);
        Intent intent = new Intent(this, GrpProfileTraxs.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }
    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }
}