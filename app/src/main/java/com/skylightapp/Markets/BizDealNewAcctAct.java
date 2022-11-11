package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.skylightapp.Adapters.CusSpinnerAdapter;
import com.skylightapp.Classes.AppConstants;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.DailyAccount;
import com.skylightapp.Classes.JourneyAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.AcctBookDAO;
import com.skylightapp.Database.BizDealDAO;
import com.skylightapp.Database.JourneyAcctDAO;
import com.skylightapp.MarketClasses.AccountBookApp;
import com.skylightapp.MarketClasses.BizDealPartner;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.TimeUtils;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class BizDealNewAcctAct extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = BizDealNewAcctAct.class.getSimpleName();
    public static final String CONTENT = "content";
    public static final String AMOUNT = "amount";
    public static final String CURRENCY_TYPE = "currency_type";
    public static final String PERSON = "person";
    public static final String TIME = "time";
    public static final String IS_INCOME = "is_income";


    private TextInputEditText etNumber;
    private TextInputEditText etContent;
    private Spinner spPerson;
    private Spinner spCurrency;


    private boolean isDailyAccount;
    private boolean isIncome;
    SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1;
    String json, json1, bDTittle,selectedDealType;
    SecureRandom random;
    int profileID,  businessAcctNo;
    private MarketBusiness marketBusiness;
    private long bdFromID;
    private BizDealDAO bizDealDAO;
    private Calendar calendar;
    private Profile adminProfile;
    //private List<String> peopleList = new LinkedList<>();
    private List<String> currencyList = new ArrayList<>(3);
    private ArrayList<Customer> mYBizCustomers;
    private CusSpinnerAdapter cusSpinnerAdapter;
    private Customer customer;
    private BizDealPartner bizDealPartner;
    private ArrayList<BizDealPartner> bizDealPartners;
    private int selectedCusIndex;
    private boolean match = false;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_daily_biz_record);
        setTitle("Business Deal Acct. Creation");
        gson1 = new Gson();
        gson = new Gson();
        bizDealPartner= new BizDealPartner();
        marketBusiness= new MarketBusiness();
        adminProfile = new Profile();
        customer= new Customer();
        Intent intent = getIntent();
        mYBizCustomers= new ArrayList<>();
        bizDealPartners= new ArrayList<>();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        adminProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        isDailyAccount = intent.getByteExtra(AppConstants.ACCOUNT_TYPE, AppConstants.NEW_DAILY_ACCOUNT) == AppConstants.NEW_DAILY_ACCOUNT;
        if (adminProfile != null) {
            profileID = adminProfile.getPID();

        }

        if(marketBusiness !=null){
            bdFromID=marketBusiness.getBusinessID();
            mYBizCustomers=marketBusiness.getMBCustomers();
            bizDealPartners=marketBusiness.getBizDealPartners();

        }
        etNumber = (TextInputEditText)findViewById(R.id.et_new_account_number);
        etContent = (TextInputEditText)findViewById(R.id.et_new_account_content);
        spPerson = (Spinner)findViewById(R.id.sp_person);
        spCurrency = (Spinner)findViewById(R.id.sp_currency);
        RadioGroup rgInOut = (RadioGroup)findViewById(R.id.rg_account_is_income);

        rgInOut.setOnCheckedChangeListener(((radioGroup, checkedId) -> {
            if (checkedId == R.id.rb_account_in){
                isIncome = true;
            }else if (checkedId == R.id.rb_account_out){
                isIncome = false;
            }
        }));

        TextView tvPerson = (TextView)findViewById(R.id.tv_person);
        TextView tvTime = (TextView) findViewById(R.id.tv_create_time);
        MaterialButton btCancel = (MaterialButton) findViewById(R.id.bt_cancel);
        MaterialButton btSubmit = (MaterialButton) findViewById(R.id.bt_submit);

        btCancel.setOnClickListener(this);
        btSubmit.setOnClickListener(this);

        DrawableCompat.setTintList(DrawableCompat.wrap(btCancel.getBackground()), ColorStateList.valueOf(getColor(R.color.colorPrimary)));
//        btCancel.getBackground().setColorFilter(getColor(R.color.colorPrimary));

        tvTime.setText(String.format("时间: %s", TimeUtils.now()));

        if (isDailyAccount){
            spPerson.setVisibility(View.GONE);
            tvPerson.setVisibility(View.GONE);
        }else {
            spPerson.setVisibility(View.VISIBLE);
            tvPerson.setVisibility(View.VISIBLE);
            cusSpinnerAdapter = new CusSpinnerAdapter(BizDealNewAcctAct.this, R.layout.lst_accounts, mYBizCustomers);
            spPerson.setAdapter(cusSpinnerAdapter);
            selectedCusIndex = spPerson.getSelectedItemPosition();
            customer = mYBizCustomers.get(selectedCusIndex);
        }

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencyList);
        spCurrency.setAdapter(currencyAdapter);

        int pos = 0;
        switch (AccountBookApp.getPrimaryCurrency()){
            case "NGN":
                pos = 0;
                break;
            case "USD":
                pos = 1;
                break;
            case "GBP":
                pos = 2;
                break;

            case "CAD":
                pos = 3;
                break;
            case "AUD":
                pos = 4;
                break;
            default:
                break;
        }
        spCurrency.setSelection(pos);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_new_account);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(isDailyAccount ? "Income" : "Expenditure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isIncome = false;

        loadData();

        initUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_submit:
                String number = "", content = "";
                String person = "";
                if (!isDailyAccount){
                    person = spPerson.getSelectedItem().toString();
                }
                String currency_type = spCurrency.getSelectedItem().toString();
                if (etNumber.getText() != null){
                    number = etNumber.getText().toString().trim();
                }
                if (etContent.getText() != null){
                    content = etContent.getText().toString().trim();
                }

                Intent intent = new Intent();

                if (isDailyAccount){
                    DailyAccount dailyAccount = new DailyAccount();
                    dailyAccount.setdAcctAmount(Double.valueOf(number));
                    dailyAccount.setdAcctContent(content);
                    dailyAccount.setdAcctCurrType(currency_type);
                    dailyAccount.setdAcctIsIncome(isIncome);

                    intent.putExtra(CONTENT, dailyAccount.getdAcctContent());
                    intent.putExtra(AMOUNT, dailyAccount.getdAcctAmount());
                    intent.putExtra(CURRENCY_TYPE, dailyAccount.getdAcctCurrType());
                    intent.putExtra(TIME, dailyAccount.getdAcctCreateTime());
                    intent.putExtra(IS_INCOME, dailyAccount.isdAcctIsIncome());

                    AcctBookDAO db = new AcctBookDAO(BizDealNewAcctAct.this);
                    db.addDailyAccount(dailyAccount);
                }else {
                    JourneyAccount journeyAccount = new JourneyAccount();
                    journeyAccount.setjAAmount(Double.valueOf(number));
                    journeyAccount.setjAContent(content);
                    journeyAccount.setjACurrency(currency_type);
                    journeyAccount.setJourneyPerson(person);
                    journeyAccount.setJACome(isIncome);

                    intent.putExtra(CONTENT, journeyAccount.getjAContent());
                    intent.putExtra(AMOUNT, journeyAccount.getjAAmount());
                    intent.putExtra(CURRENCY_TYPE, journeyAccount.getjACurrency());
                    intent.putExtra(TIME, journeyAccount.getjACreatedTime());
                    intent.putExtra(IS_INCOME, journeyAccount.isJACome());

                    JourneyAcctDAO db = new JourneyAcctDAO(BizDealNewAcctAct.this);
                    db.addJourneyAccount(journeyAccount);
                }

                setResult(1, intent);
                finish();
                break;
            case R.id.bt_cancel:
                finish();
                break;
            default:
                break;
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadData() {
        SharedPreferences preferences = getSharedPreferences("journey_data", MODE_PRIVATE);
        cusSpinnerAdapter = new CusSpinnerAdapter(BizDealNewAcctAct.this, R.layout.lst_accounts, mYBizCustomers);
        spPerson.setAdapter(cusSpinnerAdapter);
        selectedCusIndex = spPerson.getSelectedItemPosition();
        Log.e(TAG, "loadData: " + mYBizCustomers.size());
        customer = mYBizCustomers.get(selectedCusIndex);

        for (int i = 0; i < selectedCusIndex; i++){
            Log.e(TAG, "Customer:"+selectedCusIndex);
        }
        currencyList.add("NGN");
        currencyList.add("DZD");
        currencyList.add("GBP");
        currencyList.add("USD");
        currencyList.add("AOA");
        currencyList.add("SHP");
        currencyList.add("XOF");
        currencyList.add("BWP");
        currencyList.add("KMF");
        currencyList.add("BIF");
        currencyList.add("CVE");
        currencyList.add("XAF");
        currencyList.add("CDF");
        currencyList.add("DJF");
        currencyList.add("EGP");
        currencyList.add("ERN");
        currencyList.add("SZL");
        currencyList.add("ETB");
        currencyList.add("GMD");
        currencyList.add("GHS");
        currencyList.add("KMF");
        currencyList.add("GNF");
        currencyList.add("KES");
        currencyList.add("LSL");
        currencyList.add("LRD");
        currencyList.add("CAD");
        currencyList.add("AUD");
        currencyList.add("INR");

    }

    public void initUI(){
        etNumber = (TextInputEditText)findViewById(R.id.et_new_account_number);
        etContent = (TextInputEditText)findViewById(R.id.et_new_account_content);
        spPerson = (Spinner)findViewById(R.id.sp_person);
        spCurrency = (Spinner)findViewById(R.id.sp_currency);
        RadioGroup rgInOut = (RadioGroup)findViewById(R.id.rg_account_is_income);

        rgInOut.setOnCheckedChangeListener(((radioGroup, checkedId) -> {
            if (checkedId == R.id.rb_account_in){
                isIncome = true;
            }else if (checkedId == R.id.rb_account_out){
                isIncome = false;
            }
        }));

        TextView tvPerson = (TextView)findViewById(R.id.tv_person);
        TextView tvTime = (TextView) findViewById(R.id.tv_create_time);
        MaterialButton btCancel = (MaterialButton) findViewById(R.id.bt_cancel);
        MaterialButton btSubmit = (MaterialButton) findViewById(R.id.bt_submit);

        btCancel.setOnClickListener(this);
        btSubmit.setOnClickListener(this);

        DrawableCompat.setTintList(DrawableCompat.wrap(btCancel.getBackground()), ColorStateList.valueOf(getColor(R.color.colorPrimary)));
//        btCancel.getBackground().setColorFilter(getColor(R.color.colorPrimary));

        tvTime.setText(String.format("时间: %s", TimeUtils.now()));

        if (isDailyAccount){
            spPerson.setVisibility(View.GONE);
            tvPerson.setVisibility(View.GONE);
        }else {
            spPerson.setVisibility(View.VISIBLE);
            tvPerson.setVisibility(View.VISIBLE);
            cusSpinnerAdapter = new CusSpinnerAdapter(BizDealNewAcctAct.this, R.layout.lst_accounts, mYBizCustomers);
            spPerson.setAdapter(cusSpinnerAdapter);
            selectedCusIndex = spPerson.getSelectedItemPosition();
            customer = mYBizCustomers.get(selectedCusIndex);
        }

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencyList);
        spCurrency.setAdapter(currencyAdapter);

        int pos = 0;
        switch (AccountBookApp.getPrimaryCurrency()){
            case "NGN":
                pos = 0;
                break;
            case "USD":
                pos = 1;
                break;
            case "GBP":
                pos = 2;
                break;

            case "CAD":
                pos = 3;
                break;
            case "AUD":
                pos = 4;
                break;
            default:
                break;
        }
        spCurrency.setSelection(pos);
    }


}