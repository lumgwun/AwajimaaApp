package com.skylightapp.Markets;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.BizDealAdapter;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class MyBizArrayDealListAct extends AppCompatActivity implements MarketBizRecyAdapter.OnItemsClickListener,MarketBizRecyAdapter.OnClickListener{
    private ArrayList<BusinessDeal> businessDealArrayList;
    private MarketBizRecyAdapter.OnClickListener mbListener;
    List<BusinessDeal> businessDealList;
    private MarketAdapter marketAdapter;
    private AppCompatButton btnAmt,btnMilestones,btnSubmitHost,btnByDate,btnSearchByDate,btnPartner,btnHostLayout;

    private MarketBizRecyAdapter marketBizAdapter;
    private LinearLayout layoutDate,layoutHost;
    private CardView cardDate,cardDatesButton,cardBtnSubmitHost;
    private DatePicker datePicker;
    private AppCompatSpinner spnHosts;
    private Profile marketBizProfile,senderProfile;
    private static final String PREF_NAME = "skylight";
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private  Bundle messageBundle;
    private int marketBizProfileID;
    private int customerID;
    private int SharedPrefAdminID;
    private  String purpose,skylightType;
    private ArrayList<BusinessDeal> businessDeals;
    private BizDealAdapter bizDealAdapter;

    private DBHelper selector;
    private int marketBizID,qbID;
    private Profile bizProfile;
    private QBUser qbUserOfBiz;
    private Bundle bundle;
    private Customer customer;
    private MarketBusiness marketBusiness;
    private MarketBizArrayAdapter marketBizArrayAdapter;
    private String SharedPrefUserName,SharedPrefUserPassword,SharedPrefUserMachine;

    private RecyclerView recyclerViewDeals,recyViewByDate,recyViewHostDeal,recyclerViewDealsToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_biz_deal_list);
        businessDealList = new ArrayList<>();
        btnAmt = findViewById(R.id.buttonByDealAmount);
        btnMilestones = findViewById(R.id.buttonByMilestones);
        btnByDate = findViewById(R.id.buttonByDealDate);
        btnHostLayout = findViewById(R.id.buttonByHost);
        btnPartner = findViewById(R.id.buttonByPartner);
        recyclerViewDealsToday = findViewById(R.id.recyclerDealToday);
        recyViewByDate = findViewById(R.id.recyclerBizDealDate);
        layoutDate = findViewById(R.id.layoutDate33);
        cardDate = findViewById(R.id.cardDatePicker);
        datePicker = findViewById(R.id.deal_date_picker);
        cardDatesButton = findViewById(R.id.cardDatesBtn);
        btnSearchByDate = findViewById(R.id.buttonGetDealsByDate);
        layoutHost = findViewById(R.id.layoutHost);
        recyViewHostDeal = findViewById(R.id.recyclerDealHost);
        spnHosts = findViewById(R.id.spnDealHoster);
        cardBtnSubmitHost = findViewById(R.id.cardHostPayer);


        btnSubmitHost = findViewById(R.id.btnHostPayer);
        layoutHost = findViewById(R.id.layoutHost);
        btnSubmitHost.setOnClickListener(this::getBizDealByPayerHost);

        btnByDate.setOnClickListener(this::revealDealDateLayout);
        btnAmt.setOnClickListener(this::revealAmtLayout);
        btnMilestones.setOnClickListener(this::revealMilestoneLayout);
        bundle= new Bundle();
        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        qbUserOfBiz= new QBUser();
        customer= new Customer();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
    }

    @Override
    public void onItemClick(int position) {

    }

    public void revealAmtLayout(View view) {
    }

    public void revealMilestoneLayout(View view) {
    }

    public void revealDealDateLayout(View view) {
    }

    public void revealHostLayout(View view) {
    }

    public void revealPartnerLayout(View view) {
    }

    public void getBizDealsByDate(View view) {
    }

    public void getBizDealByPayer(View view) {
    }

    public void getBizDealByPayerHost(View view) {
    }

    @Override
    public void onBizClick(MarketBusiness marketBusiness) {

    }
}