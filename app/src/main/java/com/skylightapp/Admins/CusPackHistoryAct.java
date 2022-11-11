package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.AccountRecylerAdap;
import com.skylightapp.Adapters.MessageAdapter;
import com.skylightapp.Adapters.MySavingsCodeAdapter;
import com.skylightapp.Adapters.SkyLightPackageAdapter;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.SuperSavingsAdapter;
import com.skylightapp.Adapters.TranxAdminA;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CusPackHistoryAct extends AppCompatActivity implements SkyLightPackageAdapter.OnItemsClickListener, SuperSavingsAdapter.OnItemsClickListener, MySavingsCodeAdapter.OnItemsClickListener, TranxAdminA.OnItemsClickListener, StandingOrderAdapter.OnItemsClickListener, AccountRecylerAdap.OnAcctClickListener {
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;

    private Profile userProfile;
    private long profileID;
    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");
    private List<CustomerDailyReport> customerDailyReports;
    private StandingOrderAdapter standingOrderAdapter;
    private TranxAdminA tranxAdminA;
    private AccountRecylerAdap accountAdapter;
    private MySavingsCodeAdapter codeAdapter;
    private SuperSavingsAdapter savingsAdapter;
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messages;

    SkyLightPackageAdapter packageAdapter;
    DBHelper dbHelper;
    private ArrayList<CustomerDailyReport> customerDailyReports3;
    private ArrayList<SkyLightPackage> skyLightPackages;
    private ArrayList<Account> accounts4;
    private ArrayList<Transaction> transactions2;
    private ArrayList<StandingOrder> standingOrders;
    private ArrayList<PaymentCode> paymentCodeArrayList;
    Bundle userBundle;
    private Customer customer;
    private int customerID;
    private TextView txtCusName;
    SQLiteDatabase sqLiteDatabase;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private static final String PREF_NAME = "skylight";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_pack_history);
        setTitle("Customer Package History");
        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        messageDAO= new MessageDAO(this);
        codeDAO= new CodeDAO(this);
        loanDAO= new LoanDAO(this);
        acctDAO= new AcctDAO(this);
        userBundle= new Bundle();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        final Calendar c = Calendar.getInstance();
        userProfile= new Profile();
        gson = new Gson();
        gson1 = new Gson();
        customer= new Customer();

        userBundle=getIntent().getExtras();
        RecyclerView recyclerPackages = findViewById(R.id.recyclerViewPackages2);
        RecyclerView recyclerSavings = findViewById(R.id.recyclerViewSavings42);
        RecyclerView recyclerCodes = findViewById(R.id.recyclerViewCodes2);
        RecyclerView rcyclerTransactions = findViewById(R.id.recyclerViewTx2);
        RecyclerView recyclerAccounts = findViewById(R.id.recyclerViewAccount2);
        RecyclerView recyclerStandingOrder = findViewById(R.id.recyclerViewSO2);
        RecyclerView recyclerMessages = findViewById(R.id.recyclerViewMessage);
        txtCusName = findViewById(R.id.adminCus);

        if(userBundle !=null){
            userProfile=userBundle.getParcelable("Profile");
            customer=userBundle.getParcelable("Customer");

        }else {
            json = sharedpreferences.getString("LastProfileUsed", "");
            userProfile = gson.fromJson(json, Profile.class);
            json1 = sharedpreferences.getString("LastCustomerUsed", "");
            customer = gson1.fromJson(json1, Customer.class);
        }

        dbHelper= new DBHelper(this);
        if(customer !=null){
            customerID=customer.getCusUID();
        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = dbHelper.getWritableDatabase();
            standingOrders = sodao.getSOFromCurrentCustomer(customerID);
            skyLightPackages = dbHelper.getPacksFromCurrentCustomer(customerID);
            paymentCodeArrayList = codeDAO.getSavingsCodesCustomer(customerID);
            messages = messageDAO.getMessagesForCurrentCustomer(customerID);
            transactions2 = tranXDAO.getAllTransactionCustomer(customerID);
            accounts4 = acctDAO.getAccountsForCustomer(customerID);


        }
        if(customer !=null){
            customerID=customer.getCusUID();
            txtCusName.setText(MessageFormat.format("Cus:{0},{1}", customer.getCusSurname(), customer.getCusFirstName()));


            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerStandingOrder.setLayoutManager(layoutManager);
            standingOrderAdapter = new StandingOrderAdapter(CusPackHistoryAct.this,standingOrders);
            //recyclerStandingOrder.setHasFixedSize(true);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerStandingOrder.getContext(),
                    layoutManager.getOrientation());
            recyclerStandingOrder.addItemDecoration(dividerItemDecoration);
            recyclerStandingOrder.setItemAnimator(new DefaultItemAnimator());
            recyclerStandingOrder.setAdapter(standingOrderAdapter);


            LinearLayoutManager layoutManager1
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            recyclerPackages.setLayoutManager(layoutManager1);
            packageAdapter = new SkyLightPackageAdapter(CusPackHistoryAct.this,skyLightPackages);
            recyclerPackages.setAdapter(packageAdapter);
            DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerPackages.getContext(),
                    layoutManager.getOrientation());
            recyclerPackages.addItemDecoration(dividerItemDecoration1);
            recyclerPackages.setItemAnimator(new DefaultItemAnimator());



            LinearLayoutManager layoutManager2
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerCodes.setLayoutManager(layoutManager2);
            codeAdapter = new MySavingsCodeAdapter(CusPackHistoryAct.this,paymentCodeArrayList);
            //recyclerCodes.setHasFixedSize(true);
            DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerCodes.getContext(),
                    layoutManager.getOrientation());
            recyclerCodes.addItemDecoration(dividerItemDecoration2);
            recyclerCodes.setItemAnimator(new DefaultItemAnimator());
            recyclerCodes.setAdapter(codeAdapter);




            LinearLayoutManager layoutManager3
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            rcyclerTransactions.setLayoutManager(layoutManager3);
            tranxAdminA = new TranxAdminA(CusPackHistoryAct.this,transactions2);
            //rcyclerTransactions.setHasFixedSize(true);
            rcyclerTransactions.setAdapter(tranxAdminA);
            rcyclerTransactions.setItemAnimator(new DefaultItemAnimator());


            LinearLayoutManager layoutManager4
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            recyclerMessages.setLayoutManager(layoutManager4);
            messageAdapter = new MessageAdapter(CusPackHistoryAct.this,messages);
            recyclerMessages.setAdapter(messageAdapter);
            DividerItemDecoration dividerItemDecorationM = new DividerItemDecoration(recyclerAccounts.getContext(),
                    layoutManager.getOrientation());
            recyclerMessages.addItemDecoration(dividerItemDecorationM);
            recyclerMessages.setItemAnimator(new DefaultItemAnimator());


            LinearLayoutManager layoutManager5
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerSavings.setLayoutManager(layoutManager5);

            recyclerSavings.setItemAnimator(new DefaultItemAnimator());
            customerDailyReports3 = dbHelper.getSavingsFromCurrentCustomer(customerID);
            savingsAdapter = new SuperSavingsAdapter(CusPackHistoryAct.this,customerDailyReports3);
            recyclerSavings.setAdapter(savingsAdapter);

            LinearLayoutManager layoutManager6
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            recyclerAccounts.setLayoutManager(layoutManager6);
            recyclerAccounts.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerAccounts.getContext(),
                    layoutManager.getOrientation());
            recyclerAccounts.addItemDecoration(dividerItemDecorationL);
            accountAdapter = new AccountRecylerAdap(CusPackHistoryAct.this,accounts4);
            //recyclerAccounts.setHasFixedSize(true);
            recyclerAccounts.setAdapter(accountAdapter);
        }


    }

    @Override
    public void onItemClick(Transaction transaction) {

    }

    @Override
    public void onItemClick(StandingOrder standingOrder) {

    }

    @Override
    public void onAcctClicked(Account account) {

    }

    @Override
    public void onListItemClick(int index) {

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