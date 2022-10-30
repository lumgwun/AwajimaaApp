package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.CusSpinnerAdapter;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Adapters.ProfileSimpleAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.StockTransferAdapter;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.Inventory.StocksArrayAdapter;
import com.skylightapp.SuperAdmin.Awajima;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MyInvAct extends AppCompatActivity {
    Bundle itemBundle;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;
    Profile managerProfile,recipientProfile;
    Gson gson,gson2;
    String json,json2;
    Profile userProfile;
    Awajima awajima;
    DBHelper dbHelper;
    private ArrayList<Stocks> stocksArrayListTeller;
    private ArrayList<Stocks> stocksListBranch;
    private ArrayList<Stocks> stocksArraySkylight;
    TextView txtTotalSCForDate,txtTotalSCForToday,txtTotalSCTotal;
    double totalSCForDate,totalSCForToday,totalSC;
    private StockTransferAdapter adapterTeller;
    private StockTransferAdapter adapterAdmin;
    private StockTransferAdapter adapterSkylight;
    StocksArrayAdapter stocksArrayAdapter;
    CustomerManager customerManager;
    Random ran;
    SecureRandom random;
    Spinner spnReceipient,spnStocks,spnCus,spnTeller,spnBranch;
    String selectedRecipient;
    String transferDate;
    OfficeBranch selectedBranch;
    String transferAccepter;
    String transferer;
    Stocks selectedStocks;
    String stocksName;
    private RecyclerView recyclerViewST,recyclerViewStocks;

    long transferID,selectedStocksID,profileID,receiverID,recipientStockID,newStockId,customerID,transferCode;
    Calendar calendar;
    int qty,newQuantity,recipientQty,recipientNewQty;
    Customer selectedCustomer;
    private  Stocks recipientStock;
    private ArrayList<StockTransfer> stockTransferArrayList;
    private ArrayList<Stocks> stocksArrayList;
    private ArrayList<Stocks> stocksArrayListForRecipient;
    private ArrayList<Profile> tellers;
    private ArrayList<Customer> customers;
    private ArrayList<Profile> branches;
    private ArrayList<OfficeBranch> officeBranchArrayList;
    private RecyclerView recyclerViewAll;

    AppCompatButton btnCus,btnTeller,btnBranch,btnTranxToCus,btnTranxToTeller,btnTranxToBranch;
    AppCompatEditText edtQty;
    StockTransferAdapter stocksAdapter;
    ProfileSimpleAdapter profileAdapter;
    CusSpinnerAdapter customerAdapter;
    LinearLayoutCompat layoutCompatCus,layoutCompatTeller,layoutCompatBranch;
    String machine,selectedOffice;
    OfficeBranch officeBranch;
    private int recipientStockQty;
    private OfficeAdapter officeAdapter;
    String from,to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_inv);
        checkInternetConnection();
        dbHelper= new DBHelper(this);
        gson = new Gson();
        gson2 = new Gson();
        recipientStock = new Stocks();
        officeBranch= new OfficeBranch();
        selectedCustomer=new Customer();
        stocksArrayListTeller =new ArrayList<>();
        stockTransferArrayList =new ArrayList<>();
        stocksListBranch =new ArrayList<>();
        stocksArraySkylight =new ArrayList<>();
        stocksArrayList =new ArrayList<>();
        tellers =new ArrayList<>();
        customers =new ArrayList<>();
        branches =new ArrayList<>();
        recyclerViewST = findViewById(R.id.recyclerST);
        recyclerViewStocks = findViewById(R.id.recyclerMyStocks);
        stocksArrayListForRecipient =new ArrayList<>();
        customerManager = new CustomerManager();
        officeBranchArrayList = new ArrayList<OfficeBranch>();
        recipientProfile = new Profile();
        userProfile= new Profile();
        calendar = Calendar.getInstance();
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        recipientProfile = gson.fromJson(json, Profile.class);

        if(recipientProfile !=null){
            profileID=recipientProfile.getPID();
        }
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("No internet connection");
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