package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.skylightapp.Classes.ImageUtil.TAG;

public class AcctStatmOptions extends AppCompatActivity {
    private Bundle typeBundle;
    private String statementType;

    private Bundle customerBundle;
    private TellerReport tellerReport;
    private DBHelper dbHelper;
    private int customerID;
    private  int adminID,tellerReportID;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1,dateOfStatement, selectedDay;
    private Customer customer,customer1;
    private Bitmap bitmap;
    private  int selectedDayInt;
    private Button btn;
    private  double walletBalance,soBalance;
    private LinearLayout llScroll;
    TextView txtName,txtWalletBalance;
    private AppCompatSpinner spnDay;
    private AppCompatButton btnGetIt;
    private DatePicker picker;
    private  Account account;
    private StandingOrderAcct standingOrderAcct;
    private  int acctID,soAcctID,profileID;
    private static final String PREF_NAME = "skylight";
    private int day, month, year, monthSOCount, monthPackCount, monthSavingsCount, monthTranxCount;
    private double doubleTotalSO, monthTotalSavings, monthTotalTranx;
    private  int totalTellerNewCusForTheMonth;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private AppCompatTextView dosText;
    Date date;
    private  String todayDate,stringDate;
    LinearLayoutCompat layoutByDate,layoutDayMonth,layoutDayBy;
    AppCompatButton layoutBtnMonth,layoutBtnDay,btnGetByDate, btnGetByDaysBack,btnGetByMonth,layoutBtnDayPast;

    String SharedPrefUserPassword, stringNoOfSavings,yrMth, officeBranch,dateOfReport, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_acct_statm_options);
        setTitle("Account Statement Selections");
        typeBundle= new Bundle();
        stringDate=null;
        dbHelper=new DBHelper(this);
        typeBundle.putString("StatementType",statementType);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        customer = new Customer();
        customer1 = new Customer();
        standingOrderAcct = new StandingOrderAcct();
        customerBundle = new Bundle();
        typeBundle= new Bundle();
        account= new Account();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = calendar.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        todayDate = mdformat.format(calendar.getTime());
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] items133 = todayDate.split("-");
        String year1=items133[0];
        String month1=items133[1];
        String d1=items133[2];
        yrMth =year1+"-"+month1;


        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerProfileUsed", "");
        customer1 = gson1.fromJson(json, Customer.class);
        typeBundle=getIntent().getBundleExtra("StatementType");
        customerBundle =getIntent().getExtras();
        layoutByDate=findViewById(R.id.layoutByDate);
        layoutDayBy=findViewById(R.id.layoutByDaysBack);
        layoutDayMonth=findViewById(R.id.layoutByMonth);

        layoutBtnMonth=findViewById(R.id.specificMonth);
        layoutBtnDay=findViewById(R.id.specificDate);
        layoutBtnDayPast=findViewById(R.id.byDaysAgo);
        dosText=findViewById(R.id.dateSelectText);

        layoutBtnMonth.setOnClickListener(this::byMonthDate);
        layoutBtnDay.setOnClickListener(this::BySpecificDay);
        layoutBtnDayPast.setOnClickListener(this::byDayAgo);
        layoutBtnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutDayMonth.setVisibility(View.VISIBLE);
                layoutByDate.setVisibility(View.GONE);
                layoutDayBy.setVisibility(View.GONE);


            }
        });
        layoutBtnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutByDate.setVisibility(View.VISIBLE);
                layoutDayBy.setVisibility(View.GONE);
                layoutDayMonth.setVisibility(View.GONE);

            }
        });
        layoutBtnDayPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutByDate.setVisibility(View.GONE);
                layoutDayBy.setVisibility(View.VISIBLE);
                layoutDayMonth.setVisibility(View.GONE);

            }
        });

        btnGetByDate=findViewById(R.id.getByDate);
        btnGetByDaysBack=findViewById(R.id.getByDays);
        btnGetByMonth=findViewById(R.id.getByForMonth);
        spnDay=findViewById(R.id.calenderSpn);
        btnGetByDate.setOnClickListener(this::generateByDate);
        btnGetByDaysBack.setOnClickListener(this::getByDays);
        btnGetByMonth.setOnClickListener(this::generateStatementByMonth);

        picker=(DatePicker)findViewById(R.id._date_Search);


        spnDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = spnDay.getSelectedItem().toString();
                selectedDay = (String) parent.getSelectedItem();
                selectedDayInt=Integer.parseInt(selectedDay);
                Toast.makeText(AcctStatmOptions.this, "Date: "+ selectedDay,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(userProfile !=null){
            account=userProfile.getProfileAccount();
            customer=userProfile.getProfileCus();
            profileID=userProfile.getPID();
        }
        if(account!=null){
            acctID=account.getSkyLightAcctNo();
            walletBalance=account.getAccountBalance();



        }
        if(standingOrderAcct !=null){
            soAcctID= standingOrderAcct.getSoAcctNo();
            soBalance=standingOrderAcct.getSoAcctBalance();

        }
        if(customer1==customer){
            account=customer.getCusAccount();
            cmFirstName = customer.getCusFirstName();
            cmLastName = customer.getCusSurname();
            cmName=cmLastName+""+cmFirstName;



        }


        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfStatement = picker.getDayOfMonth()+"-"+ (picker.getMonth() + 1)+"-"+picker.getYear();
        if(customerBundle !=null){
            customer= customerBundle.getParcelable("Customer");

        }
        if(customer !=null){
            cmFirstName = customer.getCusFirstName();
            cmLastName = customer.getCusSurname();
            cmName=cmLastName+","+cmFirstName;
            customerID= customer.getCusUID();
        }
        typeBundle.putString("Name",cmName);
        dosText.setOnClickListener(this::dPicker);

        dosText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH+1);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog( AcctStatmOptions.this, R.style.DatePickerDialogStyle,mDateSetListener,day,month,year);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
                dialog.show();

                stringDate = day+"-"+ month+"-"+year;
                dosText.setText("Date:"+stringDate);
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Log.d(TAG, "onDateSet: date for Month: "+ i + "-" +i1 + "-" +i2);
            }
        };
        stringDate = year+"-"+ month+"-"+day;

        String[] items1 = stringDate.split("-");
        String date1=items1[0];
        String month133=items1[1];
        String year121=items1[2];
        String yearMonth =year121+"-"+month133;

        dosText.setText("Date with Month"+stringDate);

        if(stringDate.isEmpty()){
            stringDate=todayDate;
            yearMonth=yrMth;
        }



        monthPackCount =dbHelper.getCusMonthPackagesCount1(customerID,yearMonth);
        monthSOCount =dbHelper.getCusMonthSOCount1(customerID,yearMonth);
        monthSavingsCount=dbHelper.getCusMonthSavingsCount1(customerID,yearMonth);
        monthTranxCount=dbHelper.getCusMonthTransactionCount1(customerID,stringDate);
        monthTotalSavings =dbHelper.getMonthTotalSavingsForCustomer(customerID,yearMonth);
        monthTotalTranx =dbHelper.getMonthTotalTransactionForCus1(customerID,yearMonth);

        String finalYearMonth = yearMonth;
        btnGetByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPackCount =dbHelper.getCusMonthPackagesCount1(customerID, finalYearMonth);
                monthSavingsCount=dbHelper.getCusMonthSavingsCount1(customerID, finalYearMonth);

                monthSOCount =dbHelper.getCusMonthSOCount1(customerID,finalYearMonth);
                monthTranxCount=dbHelper.getCusMonthTransactionCount1(customerID,finalYearMonth);
                monthTotalSavings =dbHelper.getMonthTotalSavingsForCustomer(customerID,dateOfStatement);
                monthTotalTranx =dbHelper.getMonthTotalTransactionForCus1(customerID,finalYearMonth);
                statementType="byDate";
                typeBundle.putString("PackageType",statementType);
                typeBundle.putString("customerName",cmName);
                typeBundle.putParcelable("Customer",customer);
                typeBundle.putLong("customerID",customerID);
                typeBundle.putLong("profileID",profileID);
                typeBundle.putInt("monthPackCount",monthPackCount);
                typeBundle.putInt("monthSOCount",monthSOCount);
                typeBundle.putInt("monthSavingsCount",monthSavingsCount);
                typeBundle.putInt("monthTranxCount",monthTranxCount);
                typeBundle.putDouble("monthTotalSavings",monthTotalSavings);
                typeBundle.putDouble("monthTotalTranx",monthTotalTranx);

                Intent typeIntent = new Intent(AcctStatmOptions.this, CusStatementAct.class);
                typeIntent.putExtras(typeBundle);
                startActivity(typeIntent);

            }
        });


        String finalYearMonth1 = yearMonth;
        btnGetByDaysBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                monthPackCount =dbHelper.getCusMonthPackagesCount1(customerID, finalYearMonth1);
                monthSOCount =dbHelper.getCusMonthSOCount1(customerID, finalYearMonth1);
                monthSavingsCount=dbHelper.getCusMonthSavingsCount1(customerID, finalYearMonth1);
                monthTranxCount=dbHelper.getCusMonthTransactionCount1(customerID, finalYearMonth1);
                monthTotalSavings =dbHelper.getMonthTotalSavingsForCustomer(customerID, finalYearMonth1);
                monthTotalTranx =dbHelper.getMonthTotalTransactionForCus1(customerID, finalYearMonth1);

                statementType="daysAgo";
                typeBundle.putString("PackageType",statementType);
                typeBundle.putString("customerName",cmName);
                typeBundle.putLong("customerID",customerID);
                typeBundle.putLong("profileID",profileID);
                typeBundle.putInt("monthPackCount",monthPackCount);
                typeBundle.putParcelable("Customer",customer);
                typeBundle.putInt("monthSOCount",monthSOCount);
                typeBundle.putInt("monthSavingsCount",monthSavingsCount);
                typeBundle.putInt("monthTranxCount",monthTranxCount);
                typeBundle.putDouble("monthTotalSavings",monthTotalSavings);
                typeBundle.putDouble("monthTotalTranx",monthTotalTranx);
                Intent typeIntent = new Intent(AcctStatmOptions.this, CusStatementAct.class);
                typeIntent.putExtras(typeBundle);
                startActivity(typeIntent);

            }
        });
        btnGetByMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statementType="fromBegOfthisMonth";
                typeBundle.putString("PackageType",statementType);
                typeBundle.putLong("profileID",profileID);
                typeBundle.putInt("monthPackCount",monthPackCount);
                typeBundle.putString("customerName",cmName);
                typeBundle.putParcelable("Customer",customer);
                typeBundle.putLong("customerID",customerID);
                typeBundle.putInt("monthSOCount",monthSOCount);
                typeBundle.putInt("monthSavingsCount",monthSavingsCount);
                typeBundle.putInt("monthTranxCount",monthTranxCount);
                typeBundle.putDouble("monthTotalSavings",monthTotalSavings);
                typeBundle.putDouble("monthTotalTranx",monthTotalTranx);
                Intent typeIntent = new Intent(AcctStatmOptions.this, CusStatementAct.class);
                typeIntent.putExtras(typeBundle);
                startActivity(typeIntent);

            }
        });
    }
    private void chooseDate() {
        dateOfStatement = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


    }

    public void generateAcctStatement(View view) {
    }

    public void generateStatementByMonth(View view) {
    }

    public void BySpecificDay(View view) {
    }

    public void byMonthDate(View view) {
    }

    public void byDayAgo(View view) {
    }

    public void generateByDate(View view) {
    }

    public void getByDays(View view) {
    }

    public void dPicker(View view) {
    }
}