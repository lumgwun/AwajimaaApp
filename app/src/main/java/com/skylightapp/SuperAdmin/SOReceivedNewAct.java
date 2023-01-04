package com.skylightapp.SuperAdmin;

import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Adapters.SOReceivedAd;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SOReceived;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.SOReceivedDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.R;
import com.skylightapp.SignUpAct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class SOReceivedNewAct extends AppCompatActivity {
    SOReceivedAd soReceivedAd;
    private SOReceivedDAO soReceivedDAO;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json;
    private Profile profile;
    private DBHelper dbHelper;
    private StandingOrder standingOrder;
    private Bundle bundle,receiverBundle;
    private int soID;
    private Animation translater, translER;
    private Date date;
    private AppCompatButton proceed_Button;
    double soBalance=0.00;
    AppCompatSpinner gateWaySpn, spnOffice;
    String currentDate;
    private StandingOrderAcct standingOrderAcct;
    private TimeLineClassDAO timeLineClassDAO;
    private Customer customer;
    private double amountReceived, soAcctBalance,currentAmount;
    private ProgressDialog progressDialog;
    AppCompatEditText edtTxID,edtAmountReceived;
    AppCompatTextView dateText,txtManager, txtSOAcctNo,txtSOID, txtSOAcctBalance;
    protected DatePickerDialog datePickerDialog;
    String dateOfTx, appliedDate, txID, selectedGateway, managerName,selectedOffice, cusName;
    int profileID, customerID, soReceivedID, soAcctNo;
    private int gatewayIndex=0;
    private Calendar cal;
    int  day, month, year, newMonth;
    private long dbID;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ArrayList<SOReceived> soReceivedArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_soreceived);
        soReceivedDAO= new SOReceivedDAO(this);
        dbHelper = new DBHelper(this);
        standingOrderAcct= new StandingOrderAcct();
        bundle= new Bundle();
        soReceivedArrayList= new ArrayList<>();
        gson = new Gson();
        customer= new Customer();
        profile= new Profile();
        receiverBundle= new Bundle();
        standingOrder= new StandingOrder();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
        doSOUpdate(bundle);
        translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        timeLineClassDAO= new TimeLineClassDAO(this);
        soReceivedID = ThreadLocalRandom.current().nextInt(105, 10208);
        cal = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = mdformat.format(cal.getTime());
        dateText = findViewById(R.id.picker_text_);
        edtTxID = findViewById(R.id.tx_ID_);
        gateWaySpn = findViewById(R.id.gateway);
        edtAmountReceived = findViewById(R.id.amt_gateway);
        proceed_Button = findViewById(R.id.send_SOR);
        spnOffice = findViewById(R.id.sor_office);
        txtManager = findViewById(R.id.v_manager);
        txtSOID = findViewById(R.id.so_id_started);
        txtSOAcctNo = findViewById(R.id.account_no_so);
        txtSOAcctBalance = findViewById(R.id.so_acct_bal);

        dateText.setOnClickListener(this::txDatePicker);
        edtTxID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtAmountReceived.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                newMonth = month + 1;
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SOReceivedNewAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
                dialog.show();
                dateOfTx = year + "-" + newMonth + "-" + day;
                appliedDate = dateOfTx;
                dateText.setText("Your date of Birth:" + dateOfTx);

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                Log.d(TAG, "onDateSet: date of Birth: " + day + "-" + month + "-" + year);
                dateOfTx = year + "-" + newMonth + "-" + day;
                appliedDate = dateOfTx;
                dateText.setText("Tx Date:" + dateOfTx);


            }


        };
        appliedDate = dateOfTx;
    }
    public void txDatePicker(View view) {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        newMonth = month + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(SOReceivedNewAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
        dialog.show();
        dateOfTx = year + "-" + newMonth + "-" + day;
        appliedDate = dateOfTx;
        dateText.setText("Tx Date:" + dateOfTx);


    }

    private void doSOUpdate(Bundle bundle) {
        receiverBundle=bundle;
        receiverBundle=getIntent().getExtras();
        txtManager = findViewById(R.id.v_manager);
        txtSOID = findViewById(R.id.so_id_started);
        txtSOAcctNo = findViewById(R.id.account_no_so);
        txtSOAcctBalance = findViewById(R.id.so_acct_bal);
        if(profile !=null){
            managerName= profile.getProfileLastName()+""+profile.getProfileFirstName();
        }
        if(receiverBundle !=null){
            standingOrder=receiverBundle.getParcelable("StandingOrder");
        }
        if(standingOrder !=null){
            soID=standingOrder.getUID();
            standingOrderAcct = standingOrder.getStandingOrderAcct();
            customer=standingOrder.getSo_Customer();
        }
        if(customer !=null){
            cusName=customer.getCusSurname()+""+customer.getCusFirstName();
        }
        if(standingOrderAcct !=null){
            soAcctBalance=standingOrderAcct.getSoAcctBalance();
            soAcctNo= standingOrderAcct.getSoAcctNo();
        }
        txtSOID.setText("SO ID:"+soID);
        txtSOAcctNo.setText("SO Acct No:"+soAcctNo);
        txtSOAcctBalance.setText("SO Acct. Balance:"+soID);
        txtManager.setText("Manager:"+managerName);

    }

    public void submitSoR(View view) {
        translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        view.startAnimation(translater);
        txID = edtTxID.getText().toString().trim();
        soReceivedArrayList= new ArrayList<>();
        receiverBundle=getIntent().getExtras();
        soReceivedDAO= new SOReceivedDAO(this);
        soReceivedID = ThreadLocalRandom.current().nextInt(105, 10208);
        amountReceived = Double.parseDouble(edtAmountReceived.getText().toString().trim());
        if(profile !=null){
            managerName= profile.getProfileLastName()+""+profile.getProfileFirstName();
            profileID =profile.getPID();
        }
        if(receiverBundle !=null){
            standingOrder=receiverBundle.getParcelable("StandingOrder");
        }
        if(standingOrder !=null){
            soID=standingOrder.getUID();
            standingOrderAcct = standingOrder.getStandingOrderAcct();
        }
        if(standingOrderAcct !=null){
            soAcctBalance=standingOrderAcct.getSoAcctBalance();
            soAcctNo= standingOrderAcct.getSoAcctNo();
        }
        gateWaySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(gatewayIndex==position){
                    return;
                }else {
                    selectedGateway = gateWaySpn.getSelectedItem().toString();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(gatewayIndex==position){
                    return;
                }else {
                    selectedOffice = spnOffice.getSelectedItem().toString();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        try {

            if (TextUtils.isEmpty(txID)) {
                edtAmountReceived.setError("Please enter Amount Received");
            }  else {

                for (int i = 0; i < soReceivedArrayList.size(); i++) {
                    try {
                        if (soReceivedArrayList.get(i).getSorTranxRef().equalsIgnoreCase(txID) && soReceivedArrayList.get(i).getSorCusName().equalsIgnoreCase(cusName)) {
                            Toast.makeText(SOReceivedNewAct.this, "This SO Payment has been Entered before", Toast.LENGTH_LONG).show();
                            return;

                        } else {

                            if(soReceivedDAO !=null){
                                try {
                                    dbID=soReceivedDAO.insertSOReceived(profileID, customerID, soID, soAcctNo,soReceivedID,amountReceived,this.appliedDate,selectedOffice,profileID,txID,managerName,"","new",cusName,selectedGateway);

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }
                if(dbID>0){

                }

            }

        } catch (Exception e) {
            System.out.println("Oops!");
        }

    }
}