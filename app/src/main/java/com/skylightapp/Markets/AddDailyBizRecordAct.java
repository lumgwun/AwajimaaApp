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
import com.skylightapp.Classes.AppConstants;
import com.skylightapp.Classes.DailyAccount;
import com.skylightapp.Classes.JourneyAccount;
import com.skylightapp.Database.AcctBookDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.JourneyAcctDAO;
import com.skylightapp.MarketClasses.AccountBookApp;
import com.skylightapp.MarketClasses.TimeUtils;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AddDailyBizRecordAct extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = AddDailyBizRecordAct.class.getSimpleName();
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
    private List<String> peopleList = new LinkedList<>();
    private List<String> currencyList = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_daily_biz_record);
        Intent intent = getIntent();
        isDailyAccount = intent.getByteExtra(AppConstants.ACCOUNT_TYPE, AppConstants.NEW_DAILY_ACCOUNT) == AppConstants.NEW_DAILY_ACCOUNT;

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

                    AcctBookDAO db = new AcctBookDAO(AddDailyBizRecordAct.this);
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

                    JourneyAcctDAO db = new JourneyAcctDAO(AddDailyBizRecordAct.this);
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
        int cnt = preferences.getInt("count", 0);
        Log.e(TAG, "loadData: " + cnt);
        for (int i = 0; i < cnt; i++){
            peopleList.add(preferences.getString(String.format("person%s", i), ""));
            Log.e(TAG, peopleList.get(i));
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
        currencyList.add("NGN");
        currencyList.add("GBP");

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
            ArrayAdapter<String> peopleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, peopleList);
            spPerson.setAdapter(peopleAdapter);
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
            default:
                break;
        }
        spCurrency.setSelection(pos);
    }


}