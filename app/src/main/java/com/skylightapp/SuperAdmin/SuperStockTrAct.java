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
import android.database.sqlite.SQLiteDatabase;
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
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.StockTransferDAO;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.StockTransferAdapter;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.Inventory.StocksArrayAdapter;
import com.skylightapp.LoginDirAct;
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
    Gson gson,gson2;
    String json,json2;
    Profile userProfile;
    Skylight skylight;
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
    private int recipientStockQty;
    private OfficeAdapter officeAdapter;
    String from,to;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_stock_tr);
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
        stocksArrayListForRecipient =new ArrayList<>();
        customerManager = new CustomerManager();
        skylight = new Skylight();
        officeBranchArrayList = new ArrayList<OfficeBranch>();
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
        btnTranxToCus = findViewById(R.id.super_buttonCusStocks);
        btnTranxToTeller = findViewById(R.id.super_buttonTeller);
        btnTranxToBranch = findViewById(R.id.super_buttonBranchStocks);
        layoutCompatCus = findViewById(R.id.layoutSuperCus);
        layoutCompatTeller = findViewById(R.id.layoutSuperTeller);
        layoutCompatBranch = findViewById(R.id.layoutSuperBranch);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        skylight = gson.fromJson(json, Skylight.class);
        ran = new Random();
        random = new SecureRandom();
        spnReceipient = findViewById(R.id.gender);
        transferID = random.nextInt((int) (Math.random() * 250) + 111);
        newStockId = random.nextInt((int) (Math.random() * 150) + 1015);
        transferCode = (long) random.nextInt((int) (Math.random() * 101) + 101);
        from="Skylight";

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        transferDate = mdformat.format(calendar.getTime());
        if(skylight !=null){
            userProfile=skylight.getSkyProfile();
        }
        itemBundle=getIntent().getExtras();
        if(itemBundle !=null){
            userProfile=itemBundle.getParcelable("Profile");
        }
        OfficeBranchDAO branchDAO = new OfficeBranchDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            dbHelper= new DBHelper(this);
            officeBranchArrayList=dbHelper.getAllBranchOffices();
        }


        if(userProfile !=null){
            transferer=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();
            profileID=userProfile.getPID();
        }

        try {
            if(officeBranchArrayList.size()==0){
                spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedOffice = spnBranch.getSelectedItem().toString();
                        selectedOffice = (String) parent.getSelectedItem();
                        Toast.makeText(SuperStockTrAct.this, "Office Branch Selected: " + selectedOffice, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


            }else {
                officeAdapter = new OfficeAdapter(SuperStockTrAct.this, officeBranchArrayList);
                spnBranch.setAdapter(officeAdapter);
                spnBranch.setSelection(0);

                spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBranch = (OfficeBranch) parent.getSelectedItem();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                if(selectedBranch !=null){
                    selectedOffice=selectedBranch.getOfficeBranchName();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        if(selectedBranch !=null){
            selectedOffice=selectedBranch.getOfficeBranchName();
        }
        StockTransferDAO stockTransferDAO= new StockTransferDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            stocksArrayList =stockTransferDAO.getAllStockForProfile(profileID);
        }


        //stocksArrayListForRecipient=dbHelper.getAllStockForProfile(receiverID);

        stocksArrayAdapter = new StocksArrayAdapter(this, android.R.layout.simple_spinner_item, stocksArrayList);
        spnStocks.setAdapter(stocksArrayAdapter);
        spnStocks.setSelection(0);
        spnStocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStocks = (Stocks) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedStocks !=null){
            selectedStocksID=selectedStocks.getStockID();
            stocksName=selectedStocks.getStockName();
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            recipientStock =stockTransferDAO.getRecipientAndStock(receiverID,stocksName);
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
                        //selectedStocks = spnStocks.getSelectedItem().toString();
                        selectedCustomer = (Customer) parent.getSelectedItem();
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
                ProfDAO profDAO = new ProfDAO(SuperStockTrAct.this);
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    tellers=profDAO.getTellersFromMachine(machine);
                }
                profileAdapter = new ProfileSimpleAdapter(SuperStockTrAct.this, android.R.layout.simple_spinner_item, tellers);
                spnTeller.setAdapter(profileAdapter);
                spnTeller.setSelection(0);

                spnTeller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        recipientProfile = (Profile) parent.getSelectedItem();
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
                ProfDAO officeBranchDAO = new ProfDAO(SuperStockTrAct.this);
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    branches=officeBranchDAO.getBranchFromMachine(machine);

                }
                profileAdapter = new ProfileSimpleAdapter(SuperStockTrAct.this, android.R.layout.simple_spinner_item, branches);
                spnBranch.setAdapter(profileAdapter);
                spnBranch.setSelection(0);

                spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBranch = (OfficeBranch) parent.getSelectedItem();
                        //transferAccepter=selectedBranch;
                        //Toast.makeText(StocksTransferAct.this, "Branch Office: "+ selectedBranch,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                if(selectedBranch !=null){
                    receiverID=selectedBranch.getProfile().getPID();
                    transferAccepter=selectedBranch.getOfficeBranchName();
                }



            }
        });
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
                } catch (Exception e) {
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
                        StockTransferDAO stocksDAO = new StockTransferDAO(SuperStockTrAct.this);
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            stocksDAO.saveNewStocksTransfer(transferID,selectedStocksID,profileID,receiverID,stocksName,qty,transferer,transferAccepter,from,to,transferDate, this.transferCode,"UnVerified");


                        }
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            stocksDAO.updateStocksQty(selectedStocksID,newQuantity);

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
}