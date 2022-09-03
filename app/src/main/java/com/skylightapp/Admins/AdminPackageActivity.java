package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.AccountAdapter2;
import com.skylightapp.Adapters.MySavingsCodeAdapter;
import com.skylightapp.Adapters.SkyLightPackageAdapter;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.SuperSavingsAdapter;
import com.skylightapp.Adapters.TransactionAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GrpProfileDAO;
import com.skylightapp.Database.LoanDAO;
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
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.TellerCashList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminPackageActivity extends AppCompatActivity implements SkyLightPackageAdapter.OnItemsClickListener,SuperSavingsAdapter.OnItemsClickListener, MySavingsCodeAdapter.OnItemsClickListener,TransactionAdapter.OnItemsClickListener,StandingOrderAdapter.OnItemsClickListener,AccountAdapter2.OnItemsClickListener {
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private String todayDate;
    //FloatingActionButton floatingActionButton;
    //CustomerTabAdapter adapter;
    private List<CustomerDailyReport> customerDailyReports;
    private StandingOrderAdapter standingOrderAdapter;
    private TransactionAdapter transactionAdapter;
    private AccountAdapter2 accountAdapter;
    private MySavingsCodeAdapter codeAdapter;
    private SuperSavingsAdapter savingsAdapter;
    SkyLightPackageAdapter packageAdapter;
    DBHelper dbHelper;
    private ArrayList<CustomerDailyReport> customerDailyReports3;
    private ArrayList<SkyLightPackage> skyLightPackages;
   private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions2;
    private ArrayList<StandingOrder> standingOrders;
    private ArrayList<PaymentCode> paymentCodeArrayList;
    private TextView txtSavings, txtCodes,txtPacks,txtSO,txtTx,txtAcct;
    int soInt,txInt,savingsInt,codeInt,packageInt,acctInt;
    SQLiteDatabase sqLiteDatabase;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_package);
        setTitle("PACKAGES INSIGHTS");
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = sdf.format(calendar.getTime().getDate());
        dbHelper= new DBHelper(this);
        txtSavings = findViewById(R.id.txtViewSavings);
        txtCodes = findViewById(R.id.txtViewCodes);
        txtPacks = findViewById(R.id.txtViewPackages);
        txtTx = findViewById(R.id.txtViewTx);
        txtSO = findViewById(R.id.txtViewSO);
        txtAcct = findViewById(R.id.txtViewAcct);
        standingOrders=new ArrayList<StandingOrder>();
        paymentCodeArrayList=new ArrayList<PaymentCode>();
        transactions2=new ArrayList<Transaction>();
        skyLightPackages=new ArrayList<SkyLightPackage>();
        customerDailyReports3=new ArrayList<CustomerDailyReport>();
        accounts=new ArrayList<Account>();
        workersDAO= new WorkersDAO(this);
        stocksDAO= new StocksDAO(this);
        cusDAO= new CusDAO(this);
        birthdayDAO= new BirthdayDAO(this);
        officeBranchDAO= new OfficeBranchDAO(this);
        stockTransferDAO= new StockTransferDAO(this);

        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        grpProfileDAO= new GrpProfileDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);

        RecyclerView recyclerPackages = findViewById(R.id.recyclerViewPackages);
        RecyclerView recyclerSavings = findViewById(R.id.recyclerViewSavings4);
        RecyclerView recyclerCodes = findViewById(R.id.recyclerViewCodes);
        RecyclerView rcyclerTransactions = findViewById(R.id.recyclerViewTx);
        RecyclerView recyclerAccounts = findViewById(R.id.recyclerViewAcct);
        RecyclerView recyclerStandingOrder = findViewById(R.id.recyclerViewSO);

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            //dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                standingOrders = sodao.getStandingOrdersToday(todayDate);
                paymentCodeArrayList=codeDAO.getAllSavingsCodes();

                skyLightPackages = dbHelper.getPackageEndingToday1(todayDate);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                customerDailyReports3 = dbHelper.getAllReportsAdmin();


            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            try {
                accounts = acctDAO.getAllAccounts();


            } catch (NullPointerException e) {
                e.printStackTrace();
            }



        }




        try {
            soInt=standingOrders.size();


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(soInt>0){
            txtSO.setText("Standing Order Count"+soInt);
        }else {
            txtSO.setText("No Standing Order yet");

        }


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerStandingOrder.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerStandingOrder.getContext(),
                layoutManager.getOrientation());
        recyclerStandingOrder.addItemDecoration(dividerItemDecoration);
        recyclerStandingOrder.setItemAnimator(new DefaultItemAnimator());
        standingOrderAdapter = new StandingOrderAdapter(AdminPackageActivity.this,standingOrders);
        recyclerStandingOrder.setAdapter(standingOrderAdapter);
        standingOrderAdapter.setWhenClickListener(this);
        //SnapHelper snapHelper1 = new PagerSnapHelper();
        //snapHelper1.attachToRecyclerView(recyclerStandingOrder);




        try {
            packageInt=skyLightPackages.size();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(packageInt>0){
            txtPacks.setText("Today Due Package Count"+packageInt);
        }else {
            txtPacks.setText("No Package yet");

        }
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerPackages.setLayoutManager(layoutManager1);
        skyLightPackages = dbHelper.getAllPackagesAdmin();
        packageAdapter = new SkyLightPackageAdapter(AdminPackageActivity.this,skyLightPackages);
        //recyclerPackages.setHasFixedSize(true);
        recyclerPackages.setAdapter(packageAdapter);
        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerPackages.getContext(),
                layoutManager.getOrientation());
        recyclerPackages.addItemDecoration(dividerItemDecoration1);
        recyclerPackages.setItemAnimator(new DefaultItemAnimator());
        //SnapHelper snapHelper = new PagerSnapHelper();
        //snapHelper.attachToRecyclerView(recyclerPackages);



        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        paymentCodeArrayList = codeDAO.getAllSavingsCodes();
        try {
            codeInt=paymentCodeArrayList.size();


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if(codeInt>0){
            txtCodes.setText("Code Count"+codeInt);
        }else {
            txtSavings.setText("No Code yet");

        }
        recyclerCodes.setLayoutManager(layoutManager2);
        codeAdapter = new MySavingsCodeAdapter(AdminPackageActivity.this,paymentCodeArrayList);
        //recyclerCodes.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerCodes.getContext(),
                layoutManager.getOrientation());
        recyclerCodes.addItemDecoration(dividerItemDecoration2);
        recyclerCodes.setItemAnimator(new DefaultItemAnimator());
        recyclerCodes.setAdapter(codeAdapter);
        //SnapHelper snapHelperCodes = new PagerSnapHelper();
        //snapHelperCodes.attachToRecyclerView(recyclerCodes);




        LinearLayoutManager layoutManager3
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rcyclerTransactions.setLayoutManager(layoutManager3);


        try {
            transactions2 = tranXDAO.getAllTransactionAdmin();


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            txInt=transactions2.size();


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        transactionAdapter = new TransactionAdapter(AdminPackageActivity.this,transactions2);
        //rcyclerTransactions.setHasFixedSize(true);
        rcyclerTransactions.setAdapter(transactionAdapter);
        rcyclerTransactions.setItemAnimator(new DefaultItemAnimator());

        //SnapHelper snapHelperTransaction = new PagerSnapHelper();
        //snapHelperTransaction.attachToRecyclerView(rcyclerTransactions);




        LinearLayoutManager layoutManager5
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerSavings.setLayoutManager(layoutManager5);

        recyclerSavings.setItemAnimator(new DefaultItemAnimator());

        try {
            savingsInt=customerDailyReports3.size();


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(savingsInt>0){
            txtSavings.setText("Savings Count"+savingsInt);
        }else {
            txtSavings.setText("Savings Count"+0);

        }
        LinearLayoutManager layoutManager6
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        savingsAdapter = new SuperSavingsAdapter(AdminPackageActivity.this,customerDailyReports3);
        recyclerSavings.setLayoutManager(layoutManager6);
        recyclerSavings.setAdapter(savingsAdapter);
        recyclerSavings.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerSavings.getContext(),
                layoutManager.getOrientation());
        recyclerSavings.addItemDecoration(dividerItemDecorationL);
        //recyclerSavings.setHasFixedSize(true);

        //SnapHelper snapHelperSavings= new PagerSnapHelper();
        //snapHelperSavings.attachToRecyclerView(recyclerSavings);




        try {
            acctInt=accounts.size();


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(acctInt>0){
            txtAcct.setText("Account Count"+acctInt);
        }else {
            txtAcct.setText("No Account yet");

        }

        LinearLayoutManager layoutManager90
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerAccounts.setLayoutManager(layoutManager90);
        recyclerAccounts.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoAcct = new DividerItemDecoration(recyclerAccounts.getContext(),
                layoutManager.getOrientation());
        recyclerAccounts.addItemDecoration(dividerItemDecoAcct);
        accountAdapter = new AccountAdapter2(AdminPackageActivity.this,accounts);
        //recyclerAccounts.setHasFixedSize(true);
        accountAdapter.setWhenClickListener(this);
        //SnapHelper snapHelperAcct = new PagerSnapHelper();
        //snapHelperAcct.attachToRecyclerView(recyclerAccounts);
    }

    @Override
    public void onItemClick(Transaction transaction) {

    }

    @Override
    public void onItemClick(StandingOrder standingOrder) {

    }

    @Override
    public void onItemClick(Account account) {

    }

    @Override
    public void onItemClick(PaymentCode paymentCode) {

    }

    @Override
    public void onItemClick(CustomerDailyReport customerDailyReport) {

    }

    @Override
    public void onItemClick(SkyLightPackage lightPackage) {

    }
}