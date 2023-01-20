package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Adapters.CusSpinnerAdapter;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Adapters.ProfileSimpleAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.StockTransferDAO;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.StockTransferAdapter;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.Inventory.StocksArrayAdapter;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.Tellers.TellerHomeChoices;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class SuperStockTrAct extends AppCompatActivity {
    Bundle itemBundle;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;
    Profile managerProfile,recipientProfile;
    Gson gson,gson2,gson3;
    String json,json2,json3;
    Profile userProfile;
    Awajima awajima;
    DBHelper dbHelper;
    private ArrayList<Stocks> stocksArrayListTeller;
    private ArrayList<Stocks> stocksListBranch;
    private ArrayList<Stocks> stocksArrayAwajima;
    TextView txtTotalSCForDate,txtTotalSCForToday,txtTotalSCTotal;
    double totalSCForDate,totalSCForToday,totalSC;
    private StockTransferAdapter adapterTeller;
    private StockTransferAdapter adapterAdmin;
    private StockTransferAdapter adapterAwajima;
    StocksArrayAdapter stocksArrayAdapter;
    CustomerManager customerManager;
    Random ran;
    SecureRandom random;
    Spinner spnStocks,spnCus,spnTeller,spnBranch;
    String selectedRecipient;
    String transferDate;
    OfficeBranch selectedBranch;
    String transferAccepter;
    String transferer;
    Stocks selectedStocks;
    String stocksName;
    long transferCode;
    Calendar calendar;
    int qty,transferID,selectedStocksID,profileID,receiverID,recipientStockID,newStockId,customerID,newQuantity,recipientQty,recipientNewQty;
    Customer selectedCustomer;
    private  Stocks recipientStock;
    private ArrayList<StockTransfer> stockTransferArrayList;
    private ArrayList<Stocks> stocksArrayList;
    private ArrayList<Stocks> stocksArrayListForRecipient;
    private ArrayList<Profile> tellers;
    private ArrayList<Customer> customers;
    private ArrayList<Profile> branches;
    private ArrayList<OfficeBranch> officeBranchArrayList;

    AppCompatButton btnCus,btnTeller,btnBranch,btnTranxToCus,btnTranxToTeller,btnTranxToBranch;
    AppCompatEditText edtQty;
    StockTransferAdapter stocksAdapter;
    ProfileSimpleAdapter profileAdapter;
    CusSpinnerAdapter customerAdapter;
    LinearLayoutCompat layoutCompatCus,layoutCompatTeller,layoutCompatBranch;
    String machine,selectedOffice;
    OfficeBranch officeBranch;
    private int recipientStockQty,selectedBizIndex;
    private OfficeAdapter officeAdapter;
    String from,to,bizPhoneNo,json1,userRole,bizName;
    private long bizID;
    private SQLiteDatabase sqLiteDatabase;
    private static final String PREF_NAME = "awajima";
    private MarketBusiness marketBiz;
    private MarketBizArrayAdapter mBizAdapter;
    private  ArrayList<MarketBusiness> marketBusinessList;
    private  ArrayList<MarketBusiness> marketBizOld;
    private MarketBizDAO marketBizDAO;
    private Spinner spnBiz;
    private StocksDAO stocksDAO;
    private WorkersDAO workersDAO;
    private StockTransferDAO stockTransferDAO;
    private OfficeBranchDAO officeBranchDAO;
    private CusDAO cusDAO;
    private ProfDAO profDAO;
    private int branchIndex=0;
    private Profile branchProfile,bizProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_stock_tr);
        checkInternetConnection();
        dbHelper= new DBHelper(this);
        gson = new Gson();
        gson2 = new Gson();
        gson3= new Gson();
        branchProfile= new Profile();
        bizProfile= new Profile();
        cusDAO= new CusDAO(this);
        marketBiz= new MarketBusiness();
        stockTransferDAO= new StockTransferDAO(this);
        stocksDAO= new StocksDAO(this);
        recipientStock = new Stocks();
        officeBranch= new OfficeBranch();
        selectedCustomer=new Customer();
        stocksArrayListTeller =new ArrayList<>();
        stockTransferArrayList =new ArrayList<>();
        stocksListBranch =new ArrayList<>();
        stocksArrayAwajima =new ArrayList<>();

        officeBranchDAO= new OfficeBranchDAO(this);
        stocksArrayList =new ArrayList<>();
        tellers =new ArrayList<>();
        customers =new ArrayList<>();
        branches =new ArrayList<>();
        stocksArrayListForRecipient =new ArrayList<>();
        customerManager = new CustomerManager();
        awajima = new Awajima();
        officeBranchArrayList = new ArrayList<OfficeBranch>();
        marketBizDAO=new MarketBizDAO(this);
        profDAO = new ProfDAO(SuperStockTrAct.this);
        marketBusinessList= new ArrayList<>();
        marketBiz= new MarketBusiness();
        recipientProfile = new Profile();
        userProfile= new Profile();
        calendar = Calendar.getInstance();
        btnCus = findViewById(R.id.btn_SuperCustomer);
        btnTeller = findViewById(R.id.btn_SuperTeller);
        btnBranch = findViewById(R.id.btn_SuperBranch);
        spnStocks = findViewById(R.id.spn_SuperStocks);
        edtQty = findViewById(R.id.edtQTY_Super);
        spnCus = findViewById(R.id.spn_CusSuper);
        spnTeller = findViewById(R.id.spn_SuperTeller);
        spnBranch = findViewById(R.id.spnSuperBranch);
        spnBiz = findViewById(R.id.spn_SuperS_BizS);
        btnTranxToCus = findViewById(R.id.super_buttonCusStocks);
        btnTranxToTeller = findViewById(R.id.super_buttonTeller);
        btnTranxToBranch = findViewById(R.id.super_buttonBranchStocks);
        layoutCompatCus = findViewById(R.id.layoutSuperCus);
        layoutCompatTeller = findViewById(R.id.layoutSuperTeller);
        layoutCompatBranch = findViewById(R.id.layoutSuperBranch);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        json = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson.fromJson(json, Awajima.class);
        userRole = userPreferences.getString("machine", "");

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson2.fromJson(json2, MarketBusiness.class);

        ran = new Random();
        random = new SecureRandom();
        transferID = random.nextInt((int) (Math.random() * 250) + 111);
        newStockId = random.nextInt((int) (Math.random() * 150) + 1015);
        transferCode = (long) random.nextInt((int) (Math.random() * 101) + 101);
        if(marketBiz !=null){
            bizID=marketBiz.getBusinessID();
            bizProfile=marketBiz.getmBusProfile();

            if(bizProfile !=null){
                marketBusinessList=bizProfile.getProfile_Businesses();
            }
        }else {
            if(awajima !=null){
                marketBusinessList=awajima.getAwajimaMarketBusinesses();
            }
        }

        mBizAdapter = new MarketBizArrayAdapter(SuperStockTrAct.this,R.layout.item_market_biz, marketBusinessList);
        spnBiz.setAdapter(mBizAdapter);
        spnBiz.setSelection(0);
        selectedBizIndex = spnBiz.getSelectedItemPosition();

        try {
            marketBiz = marketBusinessList.get(selectedBizIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(marketBiz !=null){
            stocksArrayList=marketBiz.getmBStockList();
            officeBranchArrayList=marketBiz.getOfficeBranches();
            bizID=marketBiz.getBusinessID();
            bizPhoneNo=marketBiz.getBizPhoneNo();
            bizName=marketBiz.getBizBrandname();

        }

        if(userRole !=null){
            if(userRole.equalsIgnoreCase("AwajimaSuperAdmin")){
                if(stocksDAO !=null){
                    try {
                        stocksArrayList=stocksDAO.getALLStocksSuper();
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }
                }
                if(cusDAO !=null){
                    try {
                        customers=cusDAO.getAwajimaCusRole("awajima");
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }

                }

                if(officeBranchDAO !=null){
                    try {
                        officeBranchArrayList=officeBranchDAO.getAllOfficeBranches("awajima");
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }

                }
                if(marketBizDAO !=null){
                    try {
                        marketBusinessList=marketBizDAO.getAllBusinesses();
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }

                }

                from="Awajima";

            }

            if(userRole.equalsIgnoreCase("AwajimaAdmin")){

                if(stocksDAO !=null){
                    try {
                        stocksArrayList=stocksDAO.getALLStocksSuper();
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }

                }
                if(cusDAO !=null){
                    try {
                        customers=cusDAO.getAwajimaCusRole("awajima");
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }

                }
                if(officeBranchDAO !=null){
                    try {
                        officeBranchArrayList=officeBranchDAO.getAllOfficeBranches("awajima");
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }

                }
                if(marketBizDAO !=null){
                    try {
                        marketBusinessList=marketBizDAO.getAllBusinesses();
                    } catch (SQLException e) {
                        System.out.println("Oops!");
                    }

                }

                from="Awajima";

            }




        }



        officeAdapter = new OfficeAdapter(SuperStockTrAct.this, officeBranchArrayList);
        spnBranch.setAdapter(officeAdapter);
        spnBranch.setSelection(0);

        spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(branchIndex==position){
                    return;
                }else {
                    selectedBranch = (OfficeBranch) parent.getSelectedItem();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        transferDate = mdformat.format(calendar.getTime());
        if(awajima !=null){
            userProfile= awajima.getAwajimaProf();
        }
        itemBundle=getIntent().getExtras();
        if(itemBundle !=null){
            userProfile=itemBundle.getParcelable("Profile");
        }

        if(selectedBranch !=null){
            selectedOffice=selectedBranch.getOfficeBranchName();
        }

        if(stockTransferDAO !=null){
            try {
                stocksArrayList =stockTransferDAO.getAllStockForProfile(profileID);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }


        //stocksArrayListForRecipient=dbHelper.getAllStockForProfile(receiverID);

        stocksArrayAdapter = new StocksArrayAdapter(this, android.R.layout.simple_spinner_item, stocksArrayList);
        spnStocks.setAdapter(stocksArrayAdapter);
        spnStocks.setSelection(0);
        spnStocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(branchIndex==position){
                    return;
                }else {
                    selectedStocks = (Stocks) parent.getSelectedItem();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedStocks !=null){
            selectedStocksID=selectedStocks.getStockID();
            stocksName=selectedStocks.getStockName();
        }
        if(stockTransferDAO !=null){
            try {
                recipientStock =stockTransferDAO.getRecipientAndStock(receiverID,stocksName);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }



        if(userProfile !=null){
            customers=userProfile.getProfileCustomers();
        }
        if(recipientStock !=null){
            recipientStockQty=recipientStock.getStockItemQty();
        }

        btnCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerAdapter = new CusSpinnerAdapter(SuperStockTrAct.this,  customers);
                spnCus.setAdapter(customerAdapter);
                spnCus.setSelection(0);
                spnCus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(branchIndex==position){
                            return;
                        }else {
                            selectedCustomer = (Customer) parent.getSelectedItem();

                        }
                        //Toast.makeText(StocksTransferAct.this, "Selected Stocks: "+ selectedStocks,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                if(selectedCustomer !=null){
                    transferAccepter =selectedCustomer.getCusSurname()+","+ selectedCustomer.getCusFirstName();
                    receiverID=selectedCustomer.getCusProfile().getPID();

                }
            }
        });
        if(selectedCustomer !=null){
            customerID=selectedCustomer.getCusUID();


        }
        if(recipientStock !=null){
            recipientQty= recipientStock.getStockItemQty();
        }


        btnTeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                machine="Teller";
                layoutCompatTeller.setVisibility(View.VISIBLE);
                layoutCompatBranch.setVisibility(View.GONE);
                layoutCompatCus.setVisibility(View.GONE);

                if(profDAO !=null){
                    try {
                        tellers=profDAO.getTellersFromMachineAndBiz(machine,bizID);
                    } catch (SQLiteException e) {
                        e.printStackTrace();
                    }

                }



                profileAdapter = new ProfileSimpleAdapter(SuperStockTrAct.this, android.R.layout.simple_spinner_item, tellers);
                spnTeller.setAdapter(profileAdapter);
                spnTeller.setSelection(0);

                spnTeller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(branchIndex==position){
                            return;
                        }else {
                            recipientProfile = (Profile) parent.getSelectedItem();

                        }

                        //transferAccepter=selectedRecipient;
                        Toast.makeText(SuperStockTrAct.this, "Receiving Teller: "+ selectedRecipient,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                if(recipientProfile !=null){
                    transferAccepter =recipientProfile.getProfileLastName()+","+ recipientProfile.getProfileFirstName();
                    receiverID=recipientProfile.getPID();

                }

            }

        });
        if(recipientProfile !=null){
            receiverID=recipientProfile.getPID();
        }
        if(recipientStock !=null){
            recipientQty= recipientStock.getStockItemQty();
            recipientStockID= recipientStock.getStockID();
        }else {
            recipientQty=qty;
            recipientStock = new Stocks(newStockId,stocksName,recipientQty);
        }
        btnTeller.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutCompatTeller.setVisibility(View.GONE);
                layoutCompatBranch.setVisibility(View.VISIBLE);
                layoutCompatCus.setVisibility(View.VISIBLE);
                return false;
            }
        });
        btnBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                machine="Branch";
                layoutCompatBranch.setVisibility(View.VISIBLE);
                layoutCompatTeller.setVisibility(View.GONE);
                layoutCompatCus.setVisibility(View.GONE);

                if(profDAO !=null){
                    branches=profDAO.getBizProfiles(bizID);

                }

                officeAdapter = new OfficeAdapter(SuperStockTrAct.this, officeBranchArrayList);
                spnBranch.setAdapter(officeAdapter);
                spnBranch.setSelection(0);

                spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(branchIndex==position){
                            return;
                        }else {
                            selectedBranch = (OfficeBranch) parent.getSelectedItem();

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                if(selectedBranch !=null){
                    branchProfile=selectedBranch.getProfile();
                    transferAccepter=selectedBranch.getOfficeBranchName();
                }



            }
        });
        if(branchProfile !=null){
            receiverID=branchProfile.getPID();

        }
        btnBranch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutCompatBranch.setVisibility(View.GONE);
                return false;
            }
        });
        btnTranxToTeller.setOnClickListener(this::doTeller);
        btnTranxToTeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to="Teller";
                try {
                    qty = Integer.parseInt(Objects.requireNonNull(edtQty.getText()).toString());
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                    edtQty.requestFocus();
                }
                if(selectedStocks !=null){
                    selectedStocksID=selectedStocks.getStockID();
                    stocksName=selectedStocks.getStockName();
                    newQuantity=selectedStocks.getStockItemQty()-qty;
                }
                if(recipientProfile !=null){
                    transferAccepter =recipientProfile.getProfileLastName()+","+ recipientProfile.getProfileFirstName();
                    receiverID=recipientProfile.getPID();

                }

                doStocksTransferProcessing(qty,receiverID,transferID,customerID,selectedStocksID,stocksName,selectedStocks,transferAccepter,selectedCustomer, recipientStock,transferCode,recipientProfile);

            }
        });
        btnTranxToTeller.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        btnTranxToCus.setOnClickListener(this::doCustomer);
        btnTranxToCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to="Customer";
                try {
                    qty = Integer.parseInt(Objects.requireNonNull(edtQty.getText()).toString());
                } catch (Exception e) {
                    System.out.println("Oops!");
                    edtQty.requestFocus();
                }
                if(selectedStocks !=null){
                    selectedStocksID=selectedStocks.getStockID();
                    stocksName=selectedStocks.getStockName();
                    newQuantity=selectedStocks.getStockItemQty()-qty;
                }
                if(selectedCustomer !=null){
                    customerID=selectedCustomer.getCusUID();
                    transferAccepter=selectedCustomer.getCusSurname()+","+selectedCustomer.getCusFirstName();
                    userProfile=selectedCustomer.getCusProfile();
                    if(userProfile !=null){
                        receiverID=userProfile.getPID();
                    }
                }
                doStocksTransferProcessing(qty,receiverID,transferID,customerID,selectedStocksID,stocksName,selectedStocks,transferAccepter,selectedCustomer, recipientStock,transferCode,recipientProfile);



            }
        });
        btnTranxToCus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        btnTranxToBranch.setOnClickListener(this::doBranch);
        btnTranxToBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to="Branch";
                try {
                    qty = Integer.parseInt(Objects.requireNonNull(edtQty.getText()).toString());
                } catch (Exception e) {
                    System.out.println("Oops!");
                    edtQty.requestFocus();
                }
                if(selectedStocks !=null){
                    selectedStocksID=selectedStocks.getStockID();
                    stocksName=selectedStocks.getStockName();
                    newQuantity=selectedStocks.getStockItemQty()-qty;
                }
                if(selectedBranch !=null){
                    transferAccepter=selectedBranch.getOfficeBranchName();
                    receiverID=selectedBranch.getProfile().getPID();
                }
                doStocksTransferProcessing(qty,receiverID,transferID,customerID,selectedStocksID,stocksName,selectedStocks,transferAccepter,selectedCustomer, recipientStock,transferCode,recipientProfile);


            }
        });
        btnTranxToBranch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        btnCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutCompatCus.setVisibility(View.VISIBLE);
                layoutCompatBranch.setVisibility(View.GONE);
                layoutCompatTeller.setVisibility(View.GONE);

            }
        });
        btnCus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutCompatCus.setVisibility(View.GONE);
                return false;
            }
        });



    }

    private void doStocksTransferProcessing(int qty, int receiverID, int transferID, int customerID, int selectedStocksID, String stocksName, Stocks selectedStocks, String transferAccepter, Customer selectedCustomer, Stocks receivedStockNew, long transferCode, Profile recipientProfile) {
        try {
            for (int i = 0; i < this.stockTransferArrayList.size(); i++) {
                try {
                    stockTransferDAO = new StockTransferDAO(SuperStockTrAct.this);
                    if (this.stockTransferArrayList.get(i).getStockName().equalsIgnoreCase(stocksName)&& this.stockTransferArrayList.get(i).getStockTransferer().equalsIgnoreCase(transferer)|| this.stockTransferArrayList.get(i).getStockTransferDate().equalsIgnoreCase(transferDate)|| this.stockTransferArrayList.get(i).getStockAccepter().equalsIgnoreCase(transferAccepter)) {
                        Toast.makeText(SuperStockTrAct.this, "A Similar Stock transfer was already executed" , Toast.LENGTH_LONG).show();
                        return;

                    }
                    else {
                        selectedStocks.setStockCode(transferCode);
                        if(selectedCustomer !=null){
                            selectedCustomer.addCusStocks(selectedStocksID,stocksName,null,qty,transferCode,"",transferDate,"UnVerified");

                        }
                        if(recipientProfile !=null){
                            recipientProfile.addPStocks(selectedStocksID,stocksName,null,qty,transferCode,"",transferDate,"UnVerified");

                        }

                        if(stockTransferDAO !=null){
                            stockTransferDAO.saveNewStocksTransfer(transferID,selectedStocksID,profileID,receiverID,stocksName,qty,transferer,transferAccepter,from,to,transferDate, this.transferCode,"UnVerified");
                            stockTransferDAO.updateStocksQty(selectedStocksID,newQuantity);
                        }


                        selectedStocks.setStockItemQty(newQuantity);
                        startTellerTransferNoti();
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }


            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
    }

    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    private void startTellerTransferNoti() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("New Item Transfer Alert")
                        .setContentText("A Teller just transfered an Item");

        Intent notificationIntent = new Intent(this, TellerHomeChoices.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
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


    public void catchTeller(View view) {
    }

    public void catchBranch(View view) {
    }

    public void catchCustomer(View view) {
    }

    public void doTeller(View view) {
    }

    public void doCustomer(View view) {
    }

    public void doBranch(View view) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
}