package com.skylightapp.Inventory;

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
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.AwardDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.Customer_TellerDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GrpProfileDAO;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.StockTransferDAO;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;
import com.skylightapp.Tellers.TellerHomeChoices;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class StocksTransferAct extends AppCompatActivity {
    Bundle itemBundle;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;
    Profile managerProfile,recipientProfile;
    Gson gson,gson1,gson2,gson3,gson4,gson5;
    String json,json1,json2,json4,json5;
    Profile userProfile;
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
    AdminUser adminUser;
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
    int transferID;
    int selectedStocksID;
    int profileID;
    int receiverID;
    int recipientStockID;
    int newStockId;
    int customerID;
    long transferCode;
    Calendar calendar;
    int qty,newQuantity,recipientQty,recipientNewQty;
    Customer selectedCustomer;
    private  Stocks recipientStock;
    private ArrayList<StockTransfer> stockTransferArrayList;
    private ArrayList<Stocks> stocksArrayList;
    private ArrayList<Stocks> stocksArrayListForRecipient;
    private ArrayList<Profile> profileArrayList;
    private ArrayList<CustomerManager> customerManagers;
    private ArrayList<Customer> customers;
    private ArrayList<OfficeBranch> officeBranchArrayList;

    AppCompatButton  btnRunStocksTransfer;
    AppCompatEditText edtQty;
    StockTransferAdapter stocksAdapter;
    ProfileSimpleAdapter profileAdapter;
    CusSpinnerAdapter customerAdapter;
    LinearLayoutCompat layoutCompatCus,layoutCompatTeller,layoutCompatBranch;
    String machine,selectedOffice,from,to;
    OfficeBranch officeBranch;
    private int recipientStockQty;
    private OfficeAdapter officeAdapter;
    private static final String PREF_NAME = "awajima";
    private SQLiteDatabase sqLiteDatabase;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private GrpProfileDAO grpProfileDAO;
    private StocksDAO stocksDAO;
    private WorkersDAO workersDAO;
    private StockTransferDAO stockTransferDAO;
    private OfficeBranchDAO officeBranchDAO;
    private BirthdayDAO birthdayDAO;
    private TransactionGrantingDAO grantingDAO;
    private AwardDAO awardDAO;
    private Customer_TellerDAO customer_tellerDAO;
    private  String userRole,json3,bizPhoneNo,officeName, receiverType,bizName,profName;

    private Spinner spnBiz, spnReceiver;
    private  ArrayList<MarketBusiness> marketBusinessList;
    private  ArrayList<MarketBusiness> marketBizOld;
    private MarketBusiness marketBiz;
    private MarketBizDAO marketBizDAO;
    private long bizID;
    private Awajima awajima;
    private int selectedBizIndex,selectedOfficeIndex,officeBranchID;
    private MarketBizArrayAdapter mBizAdapter;
    //private CusSpinnerAdapter cusAdapter;
    private int awajimaID,branchOfficeID, bizProfileID,cusProfileID,tellerProfileID;
    private Profile branchProfile,receiverProfile,awajimaProfile,branchOfficeProfile,bizProfile,cusProfile,tellerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_stocks_transfer);
        setTitle("Stocks Transfer");
        checkInternetConnection();
        dbHelper= new DBHelper(this);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();
        gson4= new Gson();
        gson5= new Gson();
        awajima= new Awajima();
        marketBizOld= new ArrayList<>();
        customerManagers= new ArrayList<>();
        receiverProfile= new Profile();
        customer_tellerDAO= new Customer_TellerDAO(this);
        stocksDAO= new StocksDAO(this);
        cusDAO= new CusDAO(this);
        branchProfile= new Profile();
        marketBizDAO=new MarketBizDAO(this);
        marketBiz= new MarketBusiness();
        awajimaProfile= new Profile();
        branchOfficeProfile= new Profile();
        bizProfile = new Profile();
        officeBranchDAO= new OfficeBranchDAO(this);
        stockTransferDAO= new StockTransferDAO(this);

        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);
        cusProfile= new Profile();
        tellerProfile= new Profile();
        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        recipientStock = new Stocks();
        officeBranch= new OfficeBranch();
        selectedCustomer=new Customer();
        stocksArrayListTeller =new ArrayList<>();
        stockTransferArrayList =new ArrayList<>();
        stocksListBranch =new ArrayList<>();
        stocksArraySkylight =new ArrayList<>();
        stocksArrayList =new ArrayList<>();
        marketBusinessList= new ArrayList<>();
        profileArrayList =new ArrayList<>();
        customers =new ArrayList<>();
        stocksArrayListForRecipient =new ArrayList<>();
        customerManager = new CustomerManager();
        adminUser = new AdminUser();
        userProfile = new Profile();
        officeBranchArrayList = new ArrayList<OfficeBranch>();
        recipientProfile = new Profile();
        calendar = Calendar.getInstance();

        spnStocks = findViewById(R.id.spn_stocks);
        spnBiz = findViewById(R.id.spn_biz_alto);
        edtQty = findViewById(R.id.edtQTY);
        spnCus = findViewById(R.id.spn_Cus);
        spnTeller = findViewById(R.id.spn_Teller);
        spnBranch = findViewById(R.id.spnBranch);

        spnReceiver = findViewById(R.id.spn_receiver);

        btnRunStocksTransfer = findViewById(R.id.button_Run_Transfer);
        layoutCompatCus = findViewById(R.id.layoutCus);
        layoutCompatCus.setVisibility(View.GONE);
        layoutCompatTeller = findViewById(R.id.layoutTeller);
        layoutCompatTeller.setVisibility(View.GONE);
        layoutCompatBranch = findViewById(R.id.layoutBranch);
        layoutCompatBranch.setVisibility(View.GONE);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userRole = userPreferences.getString("machine", "");
        userProfile = gson.fromJson(json, Profile.class);
        ran = new Random();
        random = new SecureRandom();
        json1 = userPreferences.getString("LastAdminUserUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);
        from="Teller";
        json2 = userPreferences.getString("LastTellerUsed", "");
        customerManager = gson2.fromJson(json2, CustomerManager.class);

        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson3.fromJson(json3, MarketBusiness.class);

        json4 = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson4.fromJson(json4, Awajima.class);
        if(awajima !=null){
            awajimaProfile=awajima.getSkyProfile();

        }
        if(awajimaProfile !=null){
            awajimaID=awajimaProfile.getPID();
        }

        spnReceipient = findViewById(R.id.gender);
        transferID = random.nextInt((int) (Math.random() * 250) + 111);
        newStockId = random.nextInt((int) (Math.random() * 150) + 1015);
        transferCode = (long) random.nextInt((int) (Math.random() * 101) + 101);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        transferDate = mdformat.format(calendar.getTime());

        itemBundle=getIntent().getExtras();
        if(itemBundle !=null){
            userProfile=itemBundle.getParcelable("Profile");
        }
        if(userProfile !=null){
            profName=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();
            profileID=userProfile.getPID();
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            marketBusinessList=marketBizDAO.getAllBusinessesForProfile(profileID);
        }

        mBizAdapter = new MarketBizArrayAdapter(StocksTransferAct.this,R.layout.item_market_biz, marketBusinessList);
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
            from=bizName;
            transferer=profName;
            customers=marketBiz.getMBCustomers();
            bizProfile=marketBiz.getmBusProfile();
            profileArrayList=marketBiz.getBizProfileList();

        }
        if(bizProfile !=null){
            bizProfileID=bizProfile.getPID();
        }
        profileAdapter = new ProfileSimpleAdapter(StocksTransferAct.this, android.R.layout.simple_spinner_item, profileArrayList);
        spnTeller.setAdapter(profileAdapter);
        spnTeller.setSelection(0);

        spnTeller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tellerProfile = (Profile) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(tellerProfile !=null){
            profName=tellerProfile.getProfileLastName()+","+tellerProfile.getProfileFirstName();
            tellerProfileID=tellerProfile.getPID();
        }

        officeAdapter = new OfficeAdapter(StocksTransferAct.this,R.layout.office_row, officeBranchArrayList);
        spnBranch.setAdapter(officeAdapter);
        spnBranch.setSelection(0);
        selectedOfficeIndex = spnBranch.getSelectedItemPosition();

        try {
            officeBranch = officeBranchArrayList.get(selectedOfficeIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(officeBranch !=null){
            officeName=officeBranch.getOfficeBranchName();
            officeBranchID=officeBranch.getOfficeBranchID();
            branchOfficeProfile=officeBranch.getProfile();
        }
        if(branchOfficeProfile !=null){
            branchOfficeID=branchOfficeProfile.getProfOfficeID();
        }

        selectedOffice=officeName;
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksArrayList =stockTransferDAO.getAllStockForProfile(profileID);
        }
        if(userRole !=null){
            if(userRole.equalsIgnoreCase("AwajimaSuperAdmin")){
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        stocksArrayList=stocksDAO.getALLStocksSuper();
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }


                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        officeBranchArrayList=officeBranchDAO.getAllOfficeBranches("awajima");
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                /*if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    dbHelper= new DBHelper(this);
                    officeBranchArrayList=dbHelper.getAllBranchOffices();
                }*/
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        marketBusinessList=marketBizDAO.getAllBusinesses();
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        customers=cusDAO.getAwajimaCusRole("awajima");
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                from="Awajima";
                transferer=profName;

            }
            if(userRole.equalsIgnoreCase("AwajimaAdmin")){

                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        stocksArrayList=stocksDAO.getALLStocksSuperAwajima("awajima");
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }

                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        customers=cusDAO.getAwajimaCusRole("awajima");
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        officeBranchArrayList=officeBranchDAO.getAllOfficeBranches("awajima");
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    try {
                        marketBusinessList=marketBizDAO.getAllBusinesses();
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                from="Awajima";
                transferer=profName;

            }


        }

        customerAdapter = new CusSpinnerAdapter(StocksTransferAct.this,  customers);
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
            //transferAccepter =selectedCustomer.getCusSurname()+","+ selectedCustomer.getCusFirstName();
            //receiverID=selectedCustomer.getCusProfile().getPID();
            cusProfile=selectedCustomer.getCusProfile();

        }
        if(cusProfile !=null){
            cusProfileID=cusProfile.getPID();
        }

        spnReceiver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                receiverType = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(receiverType !=null){
            if(receiverType.equalsIgnoreCase("Teller")){
                customerManagers =marketBiz.getBizTellers();
                layoutCompatTeller.setVisibility(View.VISIBLE);
                layoutCompatBranch.setVisibility(View.GONE);
                layoutCompatCus.setVisibility(View.GONE);
                to="Teller";
                receiverProfile=tellerProfile;
                receiverID=tellerProfileID;


            }
            if(receiverType.equalsIgnoreCase("Customer")){
                customers = marketBiz.getMBCustomers();
                layoutCompatCus.setVisibility(View.VISIBLE);
                layoutCompatTeller.setVisibility(View.GONE);
                layoutCompatBranch.setVisibility(View.GONE);
                to="Customer";
                receiverProfile=cusProfile;
                receiverID=cusProfileID;





            }
            if(receiverType.equalsIgnoreCase("Business")){
                marketBizOld=marketBiz.getmBMarketBusinesses();
                layoutCompatCus.setVisibility(View.GONE);
                layoutCompatTeller.setVisibility(View.GONE);
                to="Business";
                receiverProfile=branchProfile;
                receiverID=bizProfileID;


            }
            if(receiverType.equalsIgnoreCase("Awajima")){
                awajima =marketBiz.getMarketBizAwajima();
                layoutCompatCus.setVisibility(View.GONE);
                layoutCompatTeller.setVisibility(View.GONE);
                layoutCompatBranch.setVisibility(View.GONE);
                selectedOffice=officeName="Awajima";
                to="Awajima";
                receiverProfile=awajimaProfile;
                receiverID=awajimaID;

            }
            if(receiverType.equalsIgnoreCase("Branch Office")){
                officeBranchArrayList= marketBiz.getOfficeBranches();
                layoutCompatBranch.setVisibility(View.VISIBLE);
                layoutCompatCus.setVisibility(View.GONE);
                layoutCompatTeller.setVisibility(View.GONE);
                to="Branch";
                receiverProfile=branchOfficeProfile;
                receiverID=branchOfficeID;

            }
        }






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


        if(recipientProfile !=null){
            transferAccepter =recipientProfile.getProfileLastName()+","+ recipientProfile.getProfileFirstName();
            receiverID=recipientProfile.getPID();

        }







        btnRunStocksTransfer.setOnClickListener(this::doRunTransfer);
        btnRunStocksTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to="Branch";
                recipientStock =stockTransferDAO.getRecipientAndStock(receiverID,stocksName);

                if(recipientStock !=null){
                    recipientStockQty=recipientStock.getStockItemQty();
                }
                if(recipientStock !=null){
                    recipientQty= recipientStock.getStockItemQty();
                    recipientStockQty =recipientQty;

                }else {
                    recipientQty=qty;
                    recipientStock = new Stocks(newStockId,stocksName,recipientQty);
                }




                transferAccepter=selectedBranch.getOfficeBranchName();

                if(recipientProfile !=null){
                    transferAccepter =recipientProfile.getProfileLastName()+","+ recipientProfile.getProfileFirstName();
                    receiverID=recipientProfile.getPID();

                }
                if(selectedBranch !=null){
                    transferAccepter=selectedBranch.getOfficeBranchName();

                }

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

                doStocksTransferProcessing(qty,receiverID,transferID,customerID,selectedStocksID,stocksName,selectedStocks,transferAccepter,selectedCustomer, recipientStock,transferCode,recipientProfile,from,to);


            }
        });


    }

    private void doStocksTransferProcessing(int qty, int receiverID, int transferID, int customerID, int selectedStocksID, String stocksName, Stocks selectedStocks, String transferAccepter, Customer selectedCustomer, Stocks receivedStockNew, long transferCode, Profile recipientProfile, String from, String to) {
        try {
            for (int i = 0; i < this.stockTransferArrayList.size(); i++) {
                try {
                    if (this.stockTransferArrayList.get(i).getStockName().equalsIgnoreCase(stocksName)&& this.stockTransferArrayList.get(i).getStockTransferer().equalsIgnoreCase(transferer)|| this.stockTransferArrayList.get(i).getStockTransferDate().equalsIgnoreCase(transferDate)|| this.stockTransferArrayList.get(i).getStockAccepter().equalsIgnoreCase(transferAccepter)) {
                        Toast.makeText(StocksTransferAct.this, "A Similar Stock transfer was already executed" , Toast.LENGTH_LONG).show();
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
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            stockTransferDAO.saveNewStocksTransfer(transferID,selectedStocksID,profileID,receiverID,stocksName,qty,transferer,transferAccepter,from,to,transferDate, this.transferCode,"UnVerified");

                        }
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
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
    public void doRunTransfer(View view) {
    }
}