package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Adapters.CusSpinnerAdapter;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Adapters.ProfileSpinnerAdapter;
import com.skylightapp.Classes.AlarmReceiver;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
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
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.StockTransferAdapter;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SuperAllSTList extends AppCompatActivity implements StockTransferAdapter.OnItemsClickListener, AdapterView.OnItemSelectedListener{
    private RecyclerView recyclerViewCustomDate, recyclerToCustomers, recyclerViewFromBranch;
    private RecyclerView recyclerViewSkylight;
    private RecyclerView recyclerViewFromTeller,recyclerViewAll;
    public static final String IMPORTANT_LIST_ID = "IMP_Id";
    private RecyclerView recyclerViewToday;
    private ArrayList<StockTransfer> sTArrayListToday;
    private ArrayList<String> workersNames;
    private ArrayList<StockTransfer> sTCustomDateArrayList;
    private ArrayList<StockTransfer> branchSTArrayList;
    private ArrayList<StockTransfer> tellerSTArrayList;
    private ArrayList<StockTransfer> tellerArrayListWithDate;
    private ArrayList<StockTransfer> arrayListAllST;
    private ArrayList<StockTransfer> arrayListAllSTWithDate;

    private ArrayList<StockTransfer> skylightSTArrayList;
    private ArrayList<StockTransfer> toCustomerArrayList;
    private ArrayList<StockTransfer> toCategoryOfSkylightUsersWithDate;
    private ArrayList<StockTransfer> fromCategoryOfSkylightUsersWithDate;


    ArrayAdapter<String> adapterAdmin;
    private ProfileSpinnerAdapter profileSpinnerAdapter;

    ArrayAdapter<String> stringArrayAdapter;
    private StockTransferAdapter adapterToday;
    private StockTransferAdapter adapterCustomDate;
    private StockTransferAdapter adapterTeller;
    private StockTransferAdapter adapterCustomer;
    private StockTransferAdapter adapterAll;
    private StockTransferAdapter adapterBranch;
    private StockTransferAdapter adapterSkylight;
    private ArrayList<OfficeBranch> officeBranchArrayList;
    private ArrayList<Customer> customers;


    private Toolbar mToolbar;
    Context context;

    private AlarmReceiver mAlarmReceiver;

    DBHelper dbHelper;
    private String dateOfCustomDays;
    private Customer selectedCustomer;
    private OfficeBranch selectedBranch;
    private String selectedTo;
    private String selectedTeller;
    private String dateOfST;
    private String[] mDateSplit;
    private String selectedOffice;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private Date mBirthDate;
    private TextView txtTodayTotal;
    int todaySTAmount, tCCountPayer, tCCountPayee, tCCountAll;
    private  Date customDayDate;
    int selectedTellerIndex, selectedCustomerIndex, selectedBranchIndex,selectedToIndex;
    AppCompatSpinner spnTellers, spnBranch, spnSTToCus;
    private AppCompatButton btnBySkylight, btnByTeller,btnByDate, btnToCustomersLayout, buttonToCustomer, buttonFromBranch, btnLayoutSkylight, btnFromBranchLayout, btnFromTellerLayout,btnLayoutDate,btnAllDB;
    private  Bundle bundle;
    DatePicker datePicker;
    CusSpinnerAdapter customerAdapter;
    private OfficeAdapter officeAdapter;
    double tCTotalPayer,tCTotalBranch,tCTotalPayee;
    long customerProfileID;
    private Profile customerProfile;
    Gson gson,gson2;
    String json,json2;
    Profile userProfile;
    private Awajima awajima;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;
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
    String from,to,bizPhoneNo,json1,userRole,bizName;
    private long bizID;
    Gson gson3;
    String json3;

    private SQLiteDatabase sqLiteDatabase;
    private static final String PREF_NAME = "awajima";
    private MarketBusiness marketBiz;
    private MarketBizArrayAdapter mBizAdapter;
    private  ArrayList<MarketBusiness> marketBusinessList;
    private  ArrayList<MarketBusiness> marketBizOld;
    private MarketBizDAO marketBizDAO;
    String tellerName, cusName;
    private int spnIndex=0;

    LinearLayout layoutCustomDate, layoutFromTeller, layoutAllST, layoutSkylightTittle, layoutToCustomers, layoutFromBranch;
    CardView dateCard, cardTellerBtn, allTCCard, cardLayoutToCus, cardBtnToCus, cardBtnSkyLayout, dateCardBtn, cardLayoutBtnBranch, cardLayoutFromBranch, cardTellerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_all_stlist);
        datePicker = findViewById(R.id.stocksT_datePicker);
        spnBranch = findViewById(R.id.spnSTFromBranch);
        spnSTToCus = findViewById(R.id.spnSTToCus);
        spnTellers = findViewById(R.id.spnTeller_St);
        awajima = new Awajima();
        gson = new Gson();
        gson2 = new Gson();
        gson3= new Gson();
        officeBranchDAO= new OfficeBranchDAO(this);

        stockTransferDAO= new StockTransferDAO(this);
        stocksDAO= new StocksDAO(this);
        cusDAO= new CusDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        grpProfileDAO= new GrpProfileDAO(this);
        marketBiz= new MarketBusiness();

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar.getTime());
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userRole = userPreferences.getString("machine", "");

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson2.fromJson(json2, MarketBusiness.class);
        json = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson.fromJson(json, Awajima.class);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfST);
            }
        });
        customers =new ArrayList<>();
        selectedCustomer=new Customer();
        customerProfile= new Profile();

        dateOfST = datePicker.getDayOfMonth()+"/"+ (datePicker.getMonth() + 1)+"/"+ datePicker.getYear();

        dbHelper= new DBHelper(this);
        sTArrayListToday =new ArrayList<>();
        officeBranchArrayList = new ArrayList<OfficeBranch>();

        arrayListAllSTWithDate =new ArrayList<>();
        tellerSTArrayList =new ArrayList<>();
        tellerArrayListWithDate =new ArrayList<>();
        arrayListAllST =new ArrayList<>();
        branchSTArrayList =new ArrayList<>();
        skylightSTArrayList =new ArrayList<>();
        sTCustomDateArrayList =new ArrayList<>();
        toCustomerArrayList =new ArrayList<>();

        workersNames =new ArrayList<>();
        officeAdapter = new OfficeAdapter(SuperAllSTList.this, officeBranchArrayList);
        spnBranch.setAdapter(officeAdapter);
        spnBranch.setSelection(0);

        spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
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
            selectedOffice=selectedBranch.getOfficeBranchName();
        }


        try {

            profileSpinnerAdapter = new ProfileSpinnerAdapter(this, android.R.layout.simple_spinner_item, workersNames);
            spnTellers.setAdapter(profileSpinnerAdapter);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        spnTellers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;

                }else {
                    selectedTeller = parent.getItemAtPosition(position).toString();
                    Toast.makeText(SuperAllSTList.this, "Teller: "+ selectedTeller,Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedTeller !=null){
            tellerName=selectedTeller;
        }

        try {

            customerAdapter = new CusSpinnerAdapter(SuperAllSTList.this,  customers);
            spnSTToCus.setAdapter(customerAdapter);
            spnSTToCus.setSelection(0);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        spnSTToCus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;

                }else {
                    selectedCustomer = (Customer) parent.getSelectedItem();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(selectedCustomer !=null){
            customerProfile=selectedCustomer.getCusProfile();
            cusName=selectedCustomer.getCusSurname()+""+selectedCustomer.getCusFirstName();


        }
        if(customerProfile !=null){
            customerProfileID =customerProfile.getPID();

        }
        try {
            try {
                customers=cusDAO.getAllCustomersNames();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        try {
            try {
                workersNames =workersDAO.getAllWorkers();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        try {
            try {
                sTArrayListToday =stockTransferDAO.getStocksTransferAtDate(todayDate);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                tellerSTArrayList=stockTransferDAO.getStocksTransferFromTeller(tellerName);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                tellerArrayListWithDate=stockTransferDAO.getStocksTransferFromTellerWithDate(tellerName,dateOfST);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                arrayListAllST=stockTransferDAO.getAllStocksTransfers();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                branchSTArrayList=stockTransferDAO.getStocksTransferFromBranch(selectedOffice);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                skylightSTArrayList=stockTransferDAO.getStocksTransferFromAwajima("Awajima");

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                sTCustomDateArrayList=stockTransferDAO.getStocksTransferAtDate(dateOfST);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                toCustomerArrayList=stockTransferDAO.getStocksToCustomer(cusName);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            try {
                todaySTAmount =dbHelper.getAllSkylightCashCountForDate(todayDate);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        btnFromTellerLayout = findViewById(R.id.buttonSTFromTellerBtn);
        btnFromBranchLayout = findViewById(R.id.buttonSTFromBranch);
        btnLayoutDate = findViewById(R.id.buttonSTByDate);
        btnLayoutSkylight = findViewById(R.id.buttonFromSkylight);
        btnToCustomersLayout = findViewById(R.id.buttonToCustomers);
        recyclerViewToday = findViewById(R.id.recyclerSTToday);
        layoutCustomDate = findViewById(R.id.layoutSTDate);
        recyclerViewCustomDate = findViewById(R.id.recyclerSTCDate);

        layoutFromTeller = findViewById(R.id.layoutFromTeller);
        recyclerViewFromTeller = findViewById(R.id.recyclerSTTeller);
        cardTellerLayout = findViewById(R.id.cardTellerLayout);
        cardTellerBtn = findViewById(R.id.cardTellerBtn);
        btnByTeller = findViewById(R.id.buttonSTByTeller);
        layoutFromBranch = findViewById(R.id.layoutFromBranch);
        recyclerViewFromBranch = findViewById(R.id.recyclerFromBranch);
        cardLayoutFromBranch = findViewById(R.id.cardLayoutFromBranch);

        spnBranch = findViewById(R.id.spnSTFromBranch);
        cardLayoutBtnBranch = findViewById(R.id.cardStBtnBranch);
        buttonFromBranch = findViewById(R.id.buttonFromBranch);
        layoutToCustomers = findViewById(R.id.layoutToCustomers);
        recyclerToCustomers = findViewById(R.id.recyclerToCus);
        cardLayoutToCus = findViewById(R.id.cardLayoutToCus);
        spnSTToCus = findViewById(R.id.spnSTToCus);
        cardBtnToCus = findViewById(R.id.cardBtnToCus);
        layoutAllST = findViewById(R.id.layoutAllST);
        recyclerViewAll = findViewById(R.id.recyclerAllST);
        allTCCard = findViewById(R.id.cardAllST);
        btnAllDB = findViewById(R.id.buttonAllST);
        layoutSkylightTittle = findViewById(R.id.layoutSkylightST);
        recyclerViewSkylight = findViewById(R.id.recyclerSTSky);
        //cardLayoutSkylight = findViewById(R.id.cardLayoutSky);
        btnBySkylight = findViewById(R.id.buttonSTSkylight);
        btnByDate = findViewById(R.id.btn_ST_Date);
        spnTellers = findViewById(R.id.spnTeller_St);
        buttonToCustomer = findViewById(R.id.buttonToSTCus);
        cardBtnSkyLayout = findViewById(R.id.cardSkyLayout);
        dateCardBtn = findViewById(R.id.cardStDates);
        dateCard = findViewById(R.id.cardSTDatePicker);
        spnTellers.setOnItemSelectedListener(this);
        try {
            try {
                workersNames =workersDAO.getAllWorkers();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            try {
                officeBranchArrayList=dbHelper.getAllBranchOffices();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        bundle= new Bundle();

        btnFromTellerLayout.setOnClickListener(this::revealFromTellerLayout);
        btnLayoutSkylight.setOnClickListener(this::revealFromSkylightLayout);
        btnLayoutDate.setOnClickListener(this::revealDateLayout);
        btnFromBranchLayout.setOnClickListener(this::revealFromBranchLayout);
        btnToCustomersLayout.setOnClickListener(this::revealToCustomersLayout);
        buttonFromBranch.setOnClickListener(this::getSTByBranch);
        btnByTeller.setOnClickListener(this::getSTByTeller);
        btnBySkylight.setOnClickListener(this::getSkylightST);
        btnByDate.setOnClickListener(this::getSTByCustomDate);
        btnAllDB.setOnClickListener(this::getAllST);

        btnLayoutSkylight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSkylightTittle.setVisibility(View.VISIBLE);
                recyclerViewSkylight.setVisibility(View.VISIBLE);
                cardBtnSkyLayout.setVisibility(View.VISIBLE);
                layoutCustomDate.setVisibility(View.GONE);
                recyclerViewCustomDate.setVisibility(View.GONE);
                dateCard.setVisibility(View.GONE);
                dateCardBtn.setVisibility(View.GONE);
                layoutFromTeller.setVisibility(View.GONE);
                recyclerViewFromTeller.setVisibility(View.GONE);
                cardTellerLayout.setVisibility(View.GONE);
                cardTellerBtn.setVisibility(View.GONE);
                layoutFromBranch.setVisibility(View.GONE);
                recyclerViewFromBranch.setVisibility(View.GONE);
                cardLayoutFromBranch.setVisibility(View.GONE);
                buttonFromBranch.setVisibility(View.GONE);
                layoutToCustomers.setVisibility(View.GONE);
                recyclerToCustomers.setVisibility(View.GONE);
                cardLayoutToCus.setVisibility(View.GONE);
                cardBtnToCus.setVisibility(View.GONE);
                layoutAllST.setVisibility(View.GONE);
                recyclerViewAll.setVisibility(View.GONE);
                allTCCard.setVisibility(View.GONE);



            }
        });
        btnLayoutSkylight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutSkylightTittle.setVisibility(View.GONE);
                recyclerViewSkylight.setVisibility(View.GONE);
                cardBtnSkyLayout.setVisibility(View.GONE);
                return false;
            }
        });

        btnToCustomersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutToCustomers.setVisibility(View.VISIBLE);
                recyclerToCustomers.setVisibility(View.VISIBLE);
                cardLayoutToCus.setVisibility(View.VISIBLE);
                cardBtnToCus.setVisibility(View.VISIBLE);
                layoutCustomDate.setVisibility(View.GONE);
                recyclerViewCustomDate.setVisibility(View.GONE);
                dateCard.setVisibility(View.GONE);
                dateCardBtn.setVisibility(View.GONE);
                layoutFromTeller.setVisibility(View.GONE);
                recyclerViewFromTeller.setVisibility(View.GONE);
                cardTellerLayout.setVisibility(View.GONE);
                cardTellerBtn.setVisibility(View.GONE);
                layoutFromBranch.setVisibility(View.GONE);
                recyclerViewFromBranch.setVisibility(View.GONE);
                cardLayoutFromBranch.setVisibility(View.GONE);
                buttonFromBranch.setVisibility(View.GONE);
                layoutAllST.setVisibility(View.GONE);
                recyclerViewAll.setVisibility(View.GONE);
                allTCCard.setVisibility(View.GONE);
                layoutSkylightTittle.setVisibility(View.GONE);
                recyclerViewSkylight.setVisibility(View.GONE);
                cardBtnSkyLayout.setVisibility(View.GONE);


            }
        });
        btnToCustomersLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutToCustomers.setVisibility(View.GONE);
                recyclerToCustomers.setVisibility(View.GONE);
                cardLayoutToCus.setVisibility(View.GONE);
                cardBtnToCus.setVisibility(View.GONE);
                return false;
            }
        });
        btnFromTellerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutFromTeller.setVisibility(View.VISIBLE);
                recyclerViewFromTeller.setVisibility(View.VISIBLE);
                cardTellerLayout.setVisibility(View.VISIBLE);
                cardTellerBtn.setVisibility(View.VISIBLE);

                layoutToCustomers.setVisibility(View.GONE);
                recyclerToCustomers.setVisibility(View.GONE);
                cardLayoutToCus.setVisibility(View.GONE);
                cardBtnToCus.setVisibility(View.GONE);
                layoutCustomDate.setVisibility(View.GONE);
                recyclerViewCustomDate.setVisibility(View.GONE);
                dateCard.setVisibility(View.GONE);
                dateCardBtn.setVisibility(View.GONE);
                layoutFromBranch.setVisibility(View.GONE);
                recyclerViewFromBranch.setVisibility(View.GONE);
                cardLayoutFromBranch.setVisibility(View.GONE);
                buttonFromBranch.setVisibility(View.GONE);
                layoutAllST.setVisibility(View.GONE);
                recyclerViewAll.setVisibility(View.GONE);
                allTCCard.setVisibility(View.GONE);
                layoutSkylightTittle.setVisibility(View.GONE);
                recyclerViewSkylight.setVisibility(View.GONE);
                cardBtnSkyLayout.setVisibility(View.GONE);

                recyclerViewFromTeller.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterTeller = new StockTransferAdapter(SuperAllSTList.this, tellerSTArrayList);
                recyclerViewFromTeller.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFromTeller.setAdapter(adapterTeller);
                recyclerViewFromTeller.setNestedScrollingEnabled(false);
                recyclerViewFromTeller.setClickable(true);




            }
        });
        btnFromTellerLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutFromTeller.setVisibility(View.GONE);
                recyclerViewFromTeller.setVisibility(View.GONE);
                cardTellerLayout.setVisibility(View.GONE);
                cardTellerBtn.setVisibility(View.GONE);
                return false;
            }
        });
        btnFromBranchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutFromBranch.setVisibility(View.VISIBLE);
                recyclerViewFromBranch.setVisibility(View.VISIBLE);
                cardLayoutFromBranch.setVisibility(View.VISIBLE);
                buttonFromBranch.setVisibility(View.VISIBLE);
                layoutToCustomers.setVisibility(View.GONE);
                recyclerToCustomers.setVisibility(View.GONE);
                cardLayoutToCus.setVisibility(View.GONE);
                cardBtnToCus.setVisibility(View.GONE);
                layoutCustomDate.setVisibility(View.GONE);
                recyclerViewCustomDate.setVisibility(View.GONE);
                dateCard.setVisibility(View.GONE);
                dateCardBtn.setVisibility(View.GONE);
                layoutFromTeller.setVisibility(View.GONE);
                recyclerViewFromTeller.setVisibility(View.GONE);
                cardTellerLayout.setVisibility(View.GONE);
                cardTellerBtn.setVisibility(View.GONE);
                layoutAllST.setVisibility(View.GONE);
                recyclerViewAll.setVisibility(View.GONE);
                allTCCard.setVisibility(View.GONE);
                layoutSkylightTittle.setVisibility(View.GONE);
                recyclerViewSkylight.setVisibility(View.GONE);
                cardBtnSkyLayout.setVisibility(View.GONE);



            }
        });
        btnFromBranchLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutFromBranch.setVisibility(View.GONE);
                recyclerViewFromBranch.setVisibility(View.GONE);
                cardLayoutFromBranch.setVisibility(View.GONE);
                buttonFromBranch.setVisibility(View.GONE);
                return false;
            }
        });

        btnLayoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutCustomDate.setVisibility(View.VISIBLE);
                recyclerViewCustomDate.setVisibility(View.VISIBLE);
                dateCard.setVisibility(View.VISIBLE);
                dateCardBtn.setVisibility(View.VISIBLE);
                layoutFromBranch.setVisibility(View.GONE);
                recyclerViewFromBranch.setVisibility(View.GONE);
                cardLayoutFromBranch.setVisibility(View.GONE);
                buttonFromBranch.setVisibility(View.GONE);
                layoutToCustomers.setVisibility(View.GONE);
                recyclerToCustomers.setVisibility(View.GONE);
                cardLayoutToCus.setVisibility(View.GONE);
                cardBtnToCus.setVisibility(View.GONE);
                layoutFromTeller.setVisibility(View.GONE);
                recyclerViewFromTeller.setVisibility(View.GONE);
                cardTellerLayout.setVisibility(View.GONE);
                cardTellerBtn.setVisibility(View.GONE);
                layoutAllST.setVisibility(View.GONE);
                recyclerViewAll.setVisibility(View.GONE);
                allTCCard.setVisibility(View.GONE);
                layoutSkylightTittle.setVisibility(View.GONE);
                recyclerViewSkylight.setVisibility(View.GONE);
                cardBtnSkyLayout.setVisibility(View.GONE);

            }
        });
        btnLayoutDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutCustomDate.setVisibility(View.GONE);
                recyclerViewCustomDate.setVisibility(View.GONE);
                dateCard.setVisibility(View.GONE);
                dateCardBtn.setVisibility(View.GONE);
                return false;
            }
        });


        adapterToday = new StockTransferAdapter(this, sTArrayListToday);
        LinearLayoutManager linearLayoutManagerToday = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManagerToday);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(adapterToday);
        recyclerViewToday.setNestedScrollingEnabled(false);
        recyclerViewToday.setClickable(true);



        buttonFromBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewFromBranch.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterBranch = new StockTransferAdapter(SuperAllSTList.this, branchSTArrayList);
                recyclerViewFromBranch.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFromBranch.setAdapter(adapterBranch);
                recyclerViewFromBranch.setNestedScrollingEnabled(false);
                recyclerViewFromBranch.setClickable(true);

            }
        });
        buttonToCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerToCustomers.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterCustomer = new StockTransferAdapter(SuperAllSTList.this, toCustomerArrayList);
                recyclerToCustomers.setItemAnimator(new DefaultItemAnimator());
                recyclerToCustomers.setAdapter(adapterCustomer);
                recyclerToCustomers.setNestedScrollingEnabled(false);
                recyclerToCustomers.setClickable(true);

            }
        });
        btnBySkylight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewSkylight.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterSkylight = new StockTransferAdapter(SuperAllSTList.this, skylightSTArrayList);
                recyclerViewSkylight.setItemAnimator(new DefaultItemAnimator());
                recyclerViewSkylight.setAdapter(adapterSkylight);
                recyclerViewSkylight.setNestedScrollingEnabled(false);
                recyclerViewSkylight.setClickable(true);

            }
        });
        btnByTeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewFromTeller.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterTeller = new StockTransferAdapter(SuperAllSTList.this, tellerArrayListWithDate);
                recyclerViewFromTeller.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFromTeller.setAdapter(adapterTeller);
                recyclerViewFromTeller.setNestedScrollingEnabled(false);
                recyclerViewFromTeller.setClickable(true);

            }
        });
        btnByTeller.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewFromTeller.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterTeller = new StockTransferAdapter(SuperAllSTList.this, tellerSTArrayList);
                recyclerViewFromTeller.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFromTeller.setAdapter(adapterTeller);
                recyclerViewFromTeller.setNestedScrollingEnabled(false);
                recyclerViewFromTeller.setClickable(true);
                return false;
            }
        });
        adapterCustomDate = new StockTransferAdapter(this, R.layout.skylight_cash_row, sTCustomDateArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCustomDate.setLayoutManager(linearLayoutManager);
                recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
                recyclerViewCustomDate.setAdapter(adapterCustomDate);
                recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(SuperAllSTList.this,DividerItemDecoration.VERTICAL));
                recyclerViewCustomDate.setNestedScrollingEnabled(false);
                //SnapHelper snapHelperCDate = new PagerSnapHelper();
                //snapHelperCDate.attachToRecyclerView(recyclerViewCustomDate);
                recyclerViewCustomDate.setClickable(true);

            }
        });
        adapterAll = new StockTransferAdapter(this, R.layout.skylight_cash_row, arrayListAllST);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.addItemDecoration(new DividerItemDecoration(SuperAllSTList.this,DividerItemDecoration.VERTICAL));
        recyclerViewAll.setNestedScrollingEnabled(false);
        recyclerViewAll.setClickable(true);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewAll);


    }
    private void chooseDate(String dateOfST) {
        dateOfST = datePicker.getDayOfMonth()+"/"+ (datePicker.getMonth() + 1)+"/"+ datePicker.getYear();


    }
    public void revealDateLayout(View view) {
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == spnSTToCus.getId()) {
            selectedCustomerIndex = i;
            if(spnIndex==i){
                spnSTToCus.setFocusable(true);
                return;

            }else {
                selectedCustomer = (Customer) adapterView.getSelectedItem();

            }


        }
        if (adapterView.getId() == spnBranch.getId()) {
            selectedBranchIndex = i;

            if(spnIndex==i){
                spnBranch.setFocusable(true);
                return;

            }else {
                selectedBranch = (OfficeBranch) adapterView.getSelectedItem();

            }


        }

        else if (adapterView.getId() == spnTellers.getId()) {
            selectedTellerIndex = i;
            if(spnIndex==i){
                spnTellers.setFocusable(true);
                return;

            }else {
                selectedTeller = (String) adapterView.getSelectedItem();

            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {



    }

    @Override
    public void onItemClick(StockTransfer stockTransfer) {
        bundle= new Bundle();
        bundle.putParcelable("StockTransfer", stockTransfer);
        Intent intent = new Intent(SuperAllSTList.this, UpdateSTCode.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

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


    @Override
    public void onItemClick(int adapterPosition) {

    }

    public void revealFromTellerLayout(View view) {
        layoutFromTeller.setVisibility(View.VISIBLE);
        recyclerViewFromTeller.setVisibility(View.VISIBLE);
        cardTellerLayout.setVisibility(View.VISIBLE);
        cardTellerBtn.setVisibility(View.VISIBLE);

        layoutToCustomers.setVisibility(View.GONE);
        recyclerToCustomers.setVisibility(View.GONE);
        cardLayoutToCus.setVisibility(View.GONE);
        cardBtnToCus.setVisibility(View.GONE);
        layoutCustomDate.setVisibility(View.GONE);
        recyclerViewCustomDate.setVisibility(View.GONE);
        dateCard.setVisibility(View.GONE);
        dateCardBtn.setVisibility(View.GONE);
        layoutFromBranch.setVisibility(View.GONE);
        recyclerViewFromBranch.setVisibility(View.GONE);
        cardLayoutFromBranch.setVisibility(View.GONE);
        buttonFromBranch.setVisibility(View.GONE);
        layoutAllST.setVisibility(View.GONE);
        recyclerViewAll.setVisibility(View.GONE);
        allTCCard.setVisibility(View.GONE);
        layoutSkylightTittle.setVisibility(View.GONE);
        recyclerViewSkylight.setVisibility(View.GONE);
        cardBtnSkyLayout.setVisibility(View.GONE);

        recyclerViewFromTeller.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
        adapterTeller = new StockTransferAdapter(SuperAllSTList.this, tellerSTArrayList);
        recyclerViewFromTeller.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFromTeller.setAdapter(adapterTeller);
        recyclerViewFromTeller.setNestedScrollingEnabled(false);
        recyclerViewFromTeller.setClickable(true);
    }

    public void revealFromBranchLayout(View view) {
        layoutFromBranch.setVisibility(View.VISIBLE);
        recyclerViewFromBranch.setVisibility(View.VISIBLE);
        cardLayoutFromBranch.setVisibility(View.VISIBLE);
        buttonFromBranch.setVisibility(View.VISIBLE);
        layoutToCustomers.setVisibility(View.GONE);
        recyclerToCustomers.setVisibility(View.GONE);
        cardLayoutToCus.setVisibility(View.GONE);
        cardBtnToCus.setVisibility(View.GONE);
        layoutCustomDate.setVisibility(View.GONE);
        recyclerViewCustomDate.setVisibility(View.GONE);
        dateCard.setVisibility(View.GONE);
        dateCardBtn.setVisibility(View.GONE);
        layoutFromTeller.setVisibility(View.GONE);
        recyclerViewFromTeller.setVisibility(View.GONE);
        cardTellerLayout.setVisibility(View.GONE);
        cardTellerBtn.setVisibility(View.GONE);
        layoutAllST.setVisibility(View.GONE);
        recyclerViewAll.setVisibility(View.GONE);
        allTCCard.setVisibility(View.GONE);
        layoutSkylightTittle.setVisibility(View.GONE);
        recyclerViewSkylight.setVisibility(View.GONE);
        cardBtnSkyLayout.setVisibility(View.GONE);
    }

    public void revealSTDateLayout(View view) {
        layoutCustomDate.setVisibility(View.VISIBLE);
        recyclerViewCustomDate.setVisibility(View.VISIBLE);
        dateCard.setVisibility(View.VISIBLE);
        dateCardBtn.setVisibility(View.VISIBLE);
        layoutFromBranch.setVisibility(View.GONE);
        recyclerViewFromBranch.setVisibility(View.GONE);
        cardLayoutFromBranch.setVisibility(View.GONE);
        buttonFromBranch.setVisibility(View.GONE);
        layoutToCustomers.setVisibility(View.GONE);
        recyclerToCustomers.setVisibility(View.GONE);
        cardLayoutToCus.setVisibility(View.GONE);
        cardBtnToCus.setVisibility(View.GONE);
        layoutFromTeller.setVisibility(View.GONE);
        recyclerViewFromTeller.setVisibility(View.GONE);
        cardTellerLayout.setVisibility(View.GONE);
        cardTellerBtn.setVisibility(View.GONE);
        layoutAllST.setVisibility(View.GONE);
        recyclerViewAll.setVisibility(View.GONE);
        allTCCard.setVisibility(View.GONE);
        layoutSkylightTittle.setVisibility(View.GONE);
        recyclerViewSkylight.setVisibility(View.GONE);
        cardBtnSkyLayout.setVisibility(View.GONE);

    }

    public void revealFromSkylightLayout(View view) {
        layoutSkylightTittle.setVisibility(View.VISIBLE);
        recyclerViewSkylight.setVisibility(View.VISIBLE);
        cardBtnSkyLayout.setVisibility(View.VISIBLE);
        layoutCustomDate.setVisibility(View.GONE);
        recyclerViewCustomDate.setVisibility(View.GONE);
        dateCard.setVisibility(View.GONE);
        dateCardBtn.setVisibility(View.GONE);
        layoutFromTeller.setVisibility(View.GONE);
        recyclerViewFromTeller.setVisibility(View.GONE);
        cardTellerLayout.setVisibility(View.GONE);
        cardTellerBtn.setVisibility(View.GONE);
        layoutFromBranch.setVisibility(View.GONE);
        recyclerViewFromBranch.setVisibility(View.GONE);
        cardLayoutFromBranch.setVisibility(View.GONE);
        buttonFromBranch.setVisibility(View.GONE);
        layoutToCustomers.setVisibility(View.GONE);
        recyclerToCustomers.setVisibility(View.GONE);
        cardLayoutToCus.setVisibility(View.GONE);
        cardBtnToCus.setVisibility(View.GONE);
        layoutAllST.setVisibility(View.GONE);
        recyclerViewAll.setVisibility(View.GONE);
        allTCCard.setVisibility(View.GONE);
    }

    public void revealToCustomersLayout(View view) {
        layoutToCustomers.setVisibility(View.VISIBLE);
        recyclerToCustomers.setVisibility(View.VISIBLE);
        cardLayoutToCus.setVisibility(View.VISIBLE);
        cardBtnToCus.setVisibility(View.VISIBLE);
        layoutCustomDate.setVisibility(View.GONE);
        recyclerViewCustomDate.setVisibility(View.GONE);
        dateCard.setVisibility(View.GONE);
        dateCardBtn.setVisibility(View.GONE);
        layoutFromTeller.setVisibility(View.GONE);
        recyclerViewFromTeller.setVisibility(View.GONE);
        cardTellerLayout.setVisibility(View.GONE);
        cardTellerBtn.setVisibility(View.GONE);
        layoutFromBranch.setVisibility(View.GONE);
        recyclerViewFromBranch.setVisibility(View.GONE);
        cardLayoutFromBranch.setVisibility(View.GONE);
        buttonFromBranch.setVisibility(View.GONE);
        layoutAllST.setVisibility(View.GONE);
        recyclerViewAll.setVisibility(View.GONE);
        allTCCard.setVisibility(View.GONE);
        layoutSkylightTittle.setVisibility(View.GONE);
        recyclerViewSkylight.setVisibility(View.GONE);
        cardBtnSkyLayout.setVisibility(View.GONE);

    }

    public void getSTByCustomDate(View view) {
        adapterCustomDate = new StockTransferAdapter(this, R.layout.skylight_cash_row, sTCustomDateArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCustomDate.setLayoutManager(linearLayoutManager);
        recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCustomDate.setAdapter(adapterCustomDate);
        recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(SuperAllSTList.this,DividerItemDecoration.VERTICAL));
        recyclerViewCustomDate.setNestedScrollingEnabled(false);
        //SnapHelper snapHelperCDate = new PagerSnapHelper();
        //snapHelperCDate.attachToRecyclerView(recyclerViewCustomDate);
        recyclerViewCustomDate.setClickable(true);
    }

    public void getSTByTeller(View view) {
        recyclerViewFromTeller.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
        adapterTeller = new StockTransferAdapter(SuperAllSTList.this, tellerArrayListWithDate);
        recyclerViewFromTeller.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFromTeller.setAdapter(adapterTeller);
        recyclerViewFromTeller.setNestedScrollingEnabled(false);
        recyclerViewFromTeller.setClickable(true);
    }

    public void getSTByBranch(View view) {
        recyclerViewFromBranch.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
        adapterBranch = new StockTransferAdapter(SuperAllSTList.this, branchSTArrayList);
        recyclerViewFromBranch.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFromBranch.setAdapter(adapterBranch);
        recyclerViewFromBranch.setNestedScrollingEnabled(false);
        recyclerViewFromBranch.setClickable(true);
    }

    public void getSTForCus(View view) {
        recyclerToCustomers.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
        adapterCustomer = new StockTransferAdapter(SuperAllSTList.this, toCustomerArrayList);
        recyclerToCustomers.setItemAnimator(new DefaultItemAnimator());
        recyclerToCustomers.setAdapter(adapterCustomer);
        recyclerToCustomers.setNestedScrollingEnabled(false);
        recyclerToCustomers.setClickable(true);
    }

    public void getAllST(View view) {
        adapterAll = new StockTransferAdapter(this, R.layout.skylight_cash_row, arrayListAllST);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.addItemDecoration(new DividerItemDecoration(SuperAllSTList.this,DividerItemDecoration.VERTICAL));
        recyclerViewAll.setNestedScrollingEnabled(false);
        recyclerViewAll.setClickable(true);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewAll);
    }

    public void getSkylightST(View view) {
        recyclerViewSkylight.setLayoutManager(new LinearLayoutManager(SuperAllSTList.this, LinearLayoutManager.HORIZONTAL, false));
        adapterSkylight = new StockTransferAdapter(SuperAllSTList.this, skylightSTArrayList);
        recyclerViewSkylight.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSkylight.setAdapter(adapterSkylight);
        recyclerViewSkylight.setNestedScrollingEnabled(false);
        recyclerViewSkylight.setClickable(true);
    }
}

