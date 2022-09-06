package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
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
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.AppCommission;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AdminCountAct extends AppCompatActivity {
    TextView txtTotalForTheDay,txtDurationInDays,txtTotalPaymentToday, countManualPaymentToday,txtNewCusToday,totalSavings2Today,txttotalSavingCs,totalSoCountToday,txtNewPackageCountToday, txtNewTXToday,txtTotalSavingsToday,txtAllProfileCount;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson;
    String json,branchName,branchName1,branchName2,tellerIDString,tellerIDString1,tellerIDString2,customerIDString;
    double totalSavings2Today33;
    int totalSavingsToday,soCount,txCountToday,countPackageToday,customerCountToday,customersForTeller;
    private Profile userProfile;
    private Date date;
    private int tellerID;
    private int tellerID1;
    private int customerID;
    private int tellerID2;
    private  AdminUser adminUser;
    //private Spinner spnPaymentBranchToday,spnPaymentBranchT,spnBranchNewCus;
    private static final String PREF_NAME = "skylight";
    private  TextView txtTellerPaymentT,txtCustomersforBranch,txtCustomerPaymentToday,txtBranchPaymentToday,txtTellerTotalPayment,txtBranchTotalPayment,txtTellerNewCus;
    private AppCompatButton btnTellerPayment,btnCusPayment,btnBranchPayment,btnTotalTellerPayment,buttonTotalPaymentBranch,buttonTellerNewCus, btnTellerNewCus;
    private AppCompatEditText edtTellerPaymentToday, edtCustomerPaymentToday,edtPaymentTellerTotal,edtTellerNewCus;
    private double paymentForTellerToday,paymentForCusToday,paymentTotalForTeller,paymentForBranchToday,paymentForBranchTotal;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_count);
        setTitle("Admin Counts");
        cusDAO= new CusDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);

        gson = new Gson();
        PaymentCode paymentCode=new PaymentCode();
        AppCommission appCommission = new AppCommission();
        adminUser= new AdminUser();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(adminUser !=null){
            branchName=adminUser.getAdminOffice();

        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        dbHelper = new DBHelper(this);


        txtTellerPaymentT = findViewById(R.id.tellerPaymentT);
        txtCustomerPaymentToday = findViewById(R.id.CustomerPaymentToday);
        txtBranchPaymentToday = findViewById(R.id.BranchPaymentToday);
        txtTellerTotalPayment = findViewById(R.id.TellerTotalPayment);
        txtBranchTotalPayment = findViewById(R.id.BranchTotalPayment);
        txtTellerNewCus = findViewById(R.id.TellerNewCus);
        txtCustomersforBranch = findViewById(R.id.CustomersforBranch);


        countManualPaymentToday =  findViewById(R.id.countManualPayments);
        txtTotalPaymentToday =  findViewById(R.id.TotalPayments);
        btnTellerPayment =  findViewById(R.id.butonPaymentTellerToday);
        edtTellerPaymentToday =  findViewById(R.id.edtPaymentTellerToday);
        edtCustomerPaymentToday =  findViewById(R.id.edtCustomerPaymentToday);
        //spnPaymentBranchToday =  findViewById(R.id.edtPaymentBranchToday);
        edtPaymentTellerTotal =  findViewById(R.id.edtTotalPaymentTeller);
        //spnPaymentBranchT =  findViewById(R.id.edtPaymentBranchT);
        edtTellerNewCus =  findViewById(R.id.edtTellerNewCusT);
        //spnBranchNewCus =  findViewById(R.id.edtBranchNewCus);



        btnCusPayment =  findViewById(R.id.buttonCustomerPayment);

        btnCusPayment.setOnClickListener(this::getCusPaymentToday);

        btnBranchPayment =  findViewById(R.id.buttonPaymentBranch);
        btnBranchPayment.setOnClickListener(this::getBranchPaymentToday);
        btnTotalTellerPayment =  findViewById(R.id.buttonPaymentTellerTotal);

        btnTotalTellerPayment.setOnClickListener(this::getTellerTotalPayment);
        buttonTotalPaymentBranch =  findViewById(R.id.buttonPaymentBranchT);
        buttonTotalPaymentBranch.setOnClickListener(this::getTotalPaymentBranch);
        btnTellerNewCus =  findViewById(R.id.buttonTellerNewCus);
        btnTellerNewCus.setOnClickListener(this::getTellerNewCus);

        totalSoCountToday =  findViewById(R.id.soCounts);
        soCount=sodao.getStandingOrderCountToday(todayDate);
        txtTotalSavingsToday =  findViewById(R.id.txtTotalSavingsToday);
        totalSavingsToday=dbHelper.getSavingsCountToday(todayDate);
        txtTotalSavingsToday.setText(MessageFormat.format("Savings Count{0}", totalSavingsToday));
        txtAllProfileCount=  findViewById(R.id.edtNoOfDaysMyCus);
        txtNewCusToday =  findViewById(R.id.txtNewCusToday);
        txttotalSavingCs =  findViewById(R.id.totalSavingCs);
        totalSavings2Today33=dbHelper.getTotalSavingsToday(todayDate);
        txttotalSavingCs.setText(MessageFormat.format("Total Savings N{0}", totalSavings2Today33));
        customerCountToday=cusDAO.getAllNewCusCountForToday(todayDate);
        txtNewCusToday.setText(MessageFormat.format("New Customers:{0}", customerCountToday));
        countPackageToday=tranXDAO.getAllTxCountForToday(todayDate);
        txtNewPackageCountToday =  findViewById(R.id.txtNewPackageCountToday);
        txtNewPackageCountToday.setText(MessageFormat.format("New Packs:{0}", countPackageToday));
        txCountToday=tranXDAO.getAllTxCountForToday(todayDate);
        txtNewTXToday = findViewById(R.id.txtNewTXToday);
        txtNewTXToday.setText(MessageFormat.format("Transactions today N{0}", txCountToday));



    }

    public void getCusForBranch(View view) {
        dbHelper = new DBHelper(this);
        txtTellerPaymentT = findViewById(R.id.tellerPaymentT);
        txtCustomerPaymentToday = findViewById(R.id.CustomerPaymentToday);
        txtBranchPaymentToday = findViewById(R.id.BranchPaymentToday);
        txtTellerTotalPayment = findViewById(R.id.TellerTotalPayment);
        txtBranchTotalPayment = findViewById(R.id.BranchTotalPayment);
        txtTellerNewCus = findViewById(R.id.TellerNewCus);
        txtCustomersforBranch = findViewById(R.id.CustomersforBranch);

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String joinedDate = mdformat.format(calendar.getTime());
        int cusCountForOffice= cusDAO.getNewCustomersCountForTodayOffice(branchName,joinedDate);
        txtCustomersforBranch.setText(MessageFormat.format("Branch Customers ,Today:{0}", cusCountForOffice));

    }

    public void getTellerNewCus(View view) {
        dbHelper = new DBHelper(this);
        txtTellerPaymentT = findViewById(R.id.tellerPaymentT);
        txtCustomerPaymentToday = findViewById(R.id.CustomerPaymentToday);
        txtBranchPaymentToday = findViewById(R.id.BranchPaymentToday);
        txtTellerTotalPayment = findViewById(R.id.TellerTotalPayment);
        txtBranchTotalPayment = findViewById(R.id.BranchTotalPayment);
        txtTellerNewCus = findViewById(R.id.TellerNewCus);
        txtCustomersforBranch = findViewById(R.id.CustomersforBranch);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            tellerIDString2 = edtTellerNewCus.getText().toString().trim();
            tellerID2= Integer.parseInt((tellerIDString2));
        } catch (Exception e) {
            System.out.println("Oops!");
        }

        customersForTeller=cusDAO.getNewCustomersCountForTodayTeller(tellerID2,todayDate);
        txtTellerNewCus.setText(MessageFormat.format("Teller Cus:{0}", customersForTeller));
    }

    public void getTotalPaymentBranch(View view) {
        dbHelper = new DBHelper(this);
        txtBranchTotalPayment = findViewById(R.id.BranchTotalPayment);
        adminUser= new AdminUser();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(adminUser !=null){
            branchName=adminUser.getAdminOffice();

        }

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        paymentForBranchTotal=paymentDAO.getTotalPaymentForBranch(branchName);
        txtBranchTotalPayment.setText(MessageFormat.format("Branch''s Total Payment: N{0}", paymentForBranchTotal));

    }

    public void getTellerTotalPayment(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        txtTellerTotalPayment = findViewById(R.id.TellerTotalPayment);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            tellerIDString = edtPaymentTellerTotal.getText().toString().trim();
            tellerID= Integer.parseInt((tellerIDString));
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        paymentTotalForTeller=paymentDAO.getTotalPaymentForTeller(tellerID);
        txtTellerTotalPayment.setText(MessageFormat.format("Teller''s Total Payment: N{0}", paymentTotalForTeller));

    }

    public void getBranchPaymentToday(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        adminUser= new AdminUser();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(adminUser !=null){
            branchName=adminUser.getAdminOffice();

        }

        txtBranchPaymentToday = findViewById(R.id.BranchPaymentToday);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentForBranchToday=paymentDAO.getTotalPaymentTodayForBranch1(branchName,todayDate);
        txtBranchPaymentToday.setText(MessageFormat.format("Branch''s Payment ,today : N{0}", paymentForBranchToday));


    }

    public void getCusPaymentToday(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        txtCustomerPaymentToday = findViewById(R.id.CustomerPaymentToday);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            customerIDString = edtCustomerPaymentToday.getText().toString().trim();
            customerID= Integer.parseInt((customerIDString));
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentForCusToday=paymentDAO.getTotalPaymentTodayForCustomer(customerID,todayDate);
        txtCustomerPaymentToday.setText(MessageFormat.format("Customer''s Payment ,today : N{0}", paymentForCusToday));

    }

    public void tellerPaymentToday(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        txtTellerPaymentT = findViewById(R.id.tellerPaymentT);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            tellerIDString1 = edtTellerPaymentToday.getText().toString().trim();
            tellerID1= Integer.parseInt((tellerIDString1));
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentForTellerToday=paymentDAO.getTotalPaymentTodayForTeller1(tellerID1,todayDate);
        txtTellerPaymentT.setText(MessageFormat.format("Teller''s Payment ,today : N{0}", paymentForTellerToday));


    }
}