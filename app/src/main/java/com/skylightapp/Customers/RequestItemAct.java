package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.AwardDAO;
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
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class RequestItemAct extends AppCompatActivity {
    SharedPreferences userPreferences;
    private Gson gson;
    private String json,packageName;
    private Profile userProfile;
    Bundle packBundle,bundle;
    private Customer customer;
    private int selectedPackageIndex;
    MarketBizPackage selectedPackage;
    private ArrayList<MarketBizPackage> marketBizPackageAll;
    private ArrayAdapter<MarketBizPackage> skyLightPackageAllAdapter;
    AppCompatSpinner spn_select_package;
    SecureRandom random;
    Random ran ;
    private DBHelper dbHelper;
    int customerID;
    int profileID;
    int messageID;
    long packageID;
    AppCompatTextView txtCusName;
    AppCompatButton btnRequest;
    String customerName, message,dateOfRequest, customerBranch;
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
    private static final String PREF_NAME = "skylight";
    private static boolean isPersistenceEnabled = false;
    int selectedCustomerIndex;
    SharedPreferences sharedpreferences;
    Gson gson0,gson1,gson2;
    String json0,json1,json2;
    private Calendar calendar;

    String machine,userName,officeBranch,sender,sendee,selectedOffice,collectionStatus;

    AdminUser adminUser;
    UserSuperAdmin superAdmin;
    CustomerManager customerManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_request_item);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson0 = new Gson();
        gson1 = new Gson();
        dbHelper= new DBHelper(this);
        customer = new Customer();
        superAdmin= new UserSuperAdmin();
        userProfile= new Profile();
        adminUser=new AdminUser();
        userProfile=new Profile();
        machine = sharedpreferences.getString("Machine", "");
        gson0 = new Gson();
        json0 = sharedpreferences.getString("LastAdminUserProfileUsed", "");
        superAdmin = gson0.fromJson(json0, UserSuperAdmin.class);
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        gson1 = new Gson();
        json1 = sharedpreferences.getString("LastAdminUserProfileUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);
        packBundle=new Bundle();
        bundle=new Bundle();

        workersDAO= new WorkersDAO(this);
        awardDAO= new AwardDAO(this);
        grantingDAO= new TransactionGrantingDAO(this);
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

        random= new SecureRandom();
        txtCusName = findViewById(R.id.cusForRequest);
        spn_select_package = findViewById(R.id.packageForRequestCus);
        btnRequest = findViewById(R.id.submitRequest);
        userProfile = gson.fromJson(json, Profile.class);
        packBundle = getIntent().getExtras();
        if(userProfile !=null){
            customer=userProfile.getProfileCus();
            profileID=userProfile.getPID();

        }
        if(customer !=null){
            customerID=customer.getCusUID();
            customerName=customer.getCusSurname()+","+customer.getCusFirstName();
            customerBranch=customer.getCusOfficeBranch();
        }
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        dateOfRequest = mdformat.format(calendar.getTime());
        txtCusName.setText(MessageFormat.format("Customer:{0}", customerName));
        collectionStatus="unCollected";
        marketBizPackageAll =dbHelper.getCustomerCompleteUnCollectedPack(customerID,"completed",collectionStatus);

        skyLightPackageAllAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, marketBizPackageAll);
        skyLightPackageAllAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_select_package.setAdapter(skyLightPackageAllAdapter);
        spn_select_package.setSelection(0);
        selectedPackageIndex = spn_select_package.getSelectedItemPosition();
        try {
            selectedPackage = marketBizPackageAll.get(selectedPackageIndex);
            packageName=selectedPackage.getPackageName();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(selectedPackage !=null){
            packageID=selectedPackage.getPackID();
        }
        message="I am requesting for my "+packageName+"/"+packageID;
        messageDAO= new MessageDAO(this);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //messageDAO.insertMessage(profileID,customerID,messageID, bizID, "Package Request",message,customerName,customerBranch,dateOfRequest, bizID);
                Toast.makeText(RequestItemAct.this, "Request has been sent", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cusHome(View view) {
        Intent amountIntent = new Intent(RequestItemAct.this, NewCustomerDrawer.class);
        amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(amountIntent);
    }

    public void addNew(View view) {
        Intent amountIntent = new Intent(RequestItemAct.this, NewManualPackCusAct.class);
        amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(amountIntent);
    }
}