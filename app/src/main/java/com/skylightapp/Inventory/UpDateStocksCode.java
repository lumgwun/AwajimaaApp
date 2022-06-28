package com.skylightapp.Inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class UpDateStocksCode extends AppCompatActivity {
    Bundle bundle;
    private Stocks stocks;
    private long stocksID;
    DBHelper dbHelper;
    AppCompatEditText edtCode;
    private AppCompatButton btnRunUpdate;
    TextView txtStocksID;
    String dateOfApproval;
    DatePicker picker;
    private  int profileID;
    long code,stocksCode;
    String selectedStatus, profileName,officeBranch;
    Gson gson,gson1;
    String json,json1,nIN;
    Profile userProfile;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;
    private static final String PREF_NAME = "skylight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_up_date_stocks_code);
        setTitle("Stocks Code Updating");
        stocks= new Stocks();
        gson1 = new Gson();
        gson = new Gson();
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json = userPreferences.getString("LastTellerProfileUsed", "");
        dbHelper=new DBHelper(this);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            stocks=bundle.getParcelable("Stocks");


        }
        if(stocks!=null){
            officeBranch=stocks.getStockLocation();
            stocksID=stocks.getStockID();
            profileID=stocks.getStockProfileID();
            stocksCode=stocks.getStockCode();
        }
        edtCode = findViewById(R.id.adminEdtAmount);
        txtStocksID = findViewById(R.id.txtStockID);
        btnRunUpdate = findViewById(R.id.confirmStocksUpdate);
        btnRunUpdate.setOnClickListener(this::updateStocksCode);
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar1.getTime());


        txtStocksID.setText("Stock ID:"+stocksID);
        btnRunUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    code = Long.parseLong(Objects.requireNonNull(edtCode.getText()).toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(stocksCode==code){
                    dbHelper.updateStocksWithCode(officeBranch,profileID, code,todayDate);

                }


            }
        });
    }
    private void chooseDate(String dateOfApproval) {
        dateOfApproval = picker.getYear()+"/"+ (picker.getMonth() + 1)+"/"+picker.getDayOfMonth();



    }

    public void updateStocksCode(View view) {
    }
}