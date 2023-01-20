package com.skylightapp.Markets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.BizDealDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.BizDealAdapter;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class MyBizDealListAct extends AppCompatActivity implements BizDealAdapter.OnBizDealClickListener, MarketBizRecyAdapter.OnBizClickListener{

    private LinearLayout layoutDate;
    private CardView cardDate,cardDatesButton,cardBtnSubmitHost;
    private DatePicker datePicker;
    private AppCompatSpinner spnHosts;
    private Profile marketBizProfile,senderProfile;
    private static final String PREF_NAME = "awajima";
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
    private ArrayList<BusinessDeal> bizDealsFromUsToday;
    private ArrayList<BusinessDeal> businessDealsAllFromUs;
    private ArrayList<BusinessDeal> businessDealsAllToUs;
    private ArrayList<BusinessDeal> businessDealsAccepted;
    private ArrayList<BusinessDeal> businessDealsUnAccepted;
    //private ArrayList<BusinessDeal> businessDealsNew;
    private ArrayList<BusinessDeal> bizDealsToUsByToday;
    private ArrayList<BusinessDeal> businessDealArrayList;
    private ArrayList<BusinessDeal> bizDealsFromUsAtDate;
    private ArrayList<BusinessDeal> bizDealsToUsAtDate;
    private MarketBizRecyAdapter.OnBizClickListener mbListener;
    List<BusinessDeal> businessDealList;
    private MarketAdapter marketAdapter;
    private AppCompatButton btnCreatedDate, btnDealsFromUs,btnSubmitHost, btnDealsToUs,btnSearchByDate,btnPartner, btnByStatusLayout;

    private MarketBizRecyAdapter marketBizAdapter;
    private BizDealAdapter bizDealAdapterAll;
    private BizDealAdapter bizDealAdapterByDate;
    private BizDealAdapter bizDealAdapterNew;
    private BizDealAdapter bizDealAdapterToday;

    private DBHelper selector;
    private int marketBizID,qbID;
    private Profile bizProfile;
    private QBUser qbUserOfBiz;
    private Bundle bundle;
    private Customer customer;
    private MarketBusiness marketBusiness;
    private MarketBizArrayAdapter marketBizArrayAdapter;
    private BizDealDAO bizDealDAO;
    private DBHelper dbHelper;
    private long bizID;
    public BizDealAdapter.OnBizDealClickListener listener;
    private MaterialCardView cardAmt;
    private Calendar calendar;
    private LinearLayoutCompat layoutByStatus,LayoutByToUs,LayoutByFromUs;
    private AppCompatSpinner spnStatus;
    private String SharedPrefUserName,SharedPrefUserPassword,dateOfDeal,selectedStatus,SharedPrefUserMachine,todayDate;

    private RecyclerView recyclerViewDeals,recylerToUs,recyViewByDate,recyViewHostDeal, recyclerViewDealsStatus;
    private int year;
    private int index =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_biz_deal_list);
        businessDealList = new ArrayList<>();
        bizDealsFromUsAtDate = new ArrayList<>();
        bizDealsToUsByToday = new ArrayList<>();
        bizDealsToUsAtDate = new ArrayList<>();
        businessDealArrayList = new ArrayList<>();
        businessDealsAccepted = new ArrayList<>();
        businessDealsAllFromUs = new ArrayList<>();
        bizDealsFromUsToday = new ArrayList<>();
        businessDealsAllToUs = new ArrayList<>();

        businessDealsUnAccepted = new ArrayList<>();
        bizDealDAO= new BizDealDAO(this);
        dbHelper= new DBHelper(this);


        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        todayDate = mdformat.format(calendar.getTime());
        layoutByStatus = findViewById(R.id.DealLayoutByStatus);
        spnStatus = findViewById(R.id.spnDeal_Status);
        btnCreatedDate = findViewById(R.id.buttonByCreatedDate);
        btnByStatusLayout = findViewById(R.id.buttonByStatus);
        recyViewByDate = findViewById(R.id.recyclerBizDealDate);
        layoutDate = findViewById(R.id.DealLayoutByDate);
        datePicker = findViewById(R.id.deal_date_picker);
        btnSearchByDate = findViewById(R.id.buttonGetDealsByDate);
        LayoutByToUs = findViewById(R.id.DealLayoutByToUs);
        recylerToUs = findViewById(R.id.recyDealToUsBiz);


        btnCreatedDate = findViewById(R.id.buttonByCreatedDate);
        btnDealsFromUs = findViewById(R.id.buttonFromUs);
        btnDealsToUs = findViewById(R.id.buttonDealToUs);

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(index==position){
                    return;
                }else {
                    selectedStatus = spnStatus.getSelectedItem().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnCreatedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutDate.setVisibility(View.VISIBLE);

            }
        });
        btnDealsToUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutByToUs.setVisibility(View.VISIBLE);

            }
        });
        btnSearchByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSearchByDate.setOnClickListener(this::getBizDealsByDate);


        btnByStatusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutByStatus.setVisibility(View.VISIBLE);
                recyViewByDate.setVisibility(View.VISIBLE);

            }
        });
        btnByStatusLayout.setOnClickListener(this::revealStatusLayout);

        cardDatesButton = findViewById(R.id.cardDatesBtn);

        btnDealsToUs.setOnClickListener(this::revealDealToUsLayout);
        btnCreatedDate.setOnClickListener(this::revealDateLayout);
        btnDealsFromUs.setOnClickListener(this::revealFromUsLayout);
        bundle= new Bundle();
        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        qbUserOfBiz= new QBUser();
        customer= new Customer();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfDeal = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();


        if(marketBusiness !=null){
            bizID =marketBusiness.getBusinessID();
            businessDealsAllToUs= bizDealDAO.getBizDealToBizID(bizID);
            businessDealArrayList=marketBusiness.getmBusBizDeals();
        }
        if(dateOfDeal ==null){
            dateOfDeal =todayDate;
        }
        if(bizDealDAO !=null){
            bizDealsToUsByToday = bizDealDAO.getBizDealToBizAtCreatedToday(bizID,todayDate);
            bizDealsFromUsToday=bizDealDAO.getBizDealFromUsToday(bizID,todayDate);
            bizDealsToUsAtDate= bizDealDAO.getBizDealToBizAtCreatedToday(bizID,todayDate);
            bizDealsFromUsAtDate=bizDealDAO.getBizDealFromBizAtCreatedDate(bizID,dateOfDeal);
            businessDealsAllToUs= bizDealDAO.getBizDealToBizID(bizID);
            businessDealsAllFromUs=bizDealDAO.getBizDealFromBizID(bizID);
        }

        if(businessDealsAllToUs ==null){
            businessDealsAllToUs =businessDealArrayList;

        }


        initiateRecycler();
    }
    private void chooseDate() {
        dateOfDeal = datePicker.getYear()+"-"+ (datePicker.getMonth() + 1)+"-"+datePicker.getDayOfMonth();

    }

    private void initiateRecycler() {
        bizDealAdapterAll = new BizDealAdapter(MyBizDealListAct.this,businessDealsAllToUs,listener);
        LinearLayoutManager layout = new LinearLayoutManager (this);
        recyclerViewDealsStatus.setLayoutManager (layout);
        recyclerViewDealsStatus.setAdapter (bizDealAdapterAll);
        recyclerViewDealsStatus.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewDealsStatus);
        recyclerViewDealsStatus.setNestedScrollingEnabled(false);
        recyclerViewDealsStatus.invalidate ();
        bizDealAdapterAll.setSelectedItemsCountsChangedListener(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onItemClick(int position) {

    }

    public void revealDateLayout(View view) {
    }

    public void revealFromUsLayout(View view) {
    }

    public void revealDealToUsLayout(View view) {
    }

    public void revealStatusLayout(View view) {
    }

    public void revealCompletedLayout(View view) {
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

    @Override
    public void DealSelected(BusinessDeal businessDeal, int position) {

    }

    @Override
    public void DealSelected(BusinessDeal businessDeal) {

    }

    @Override
    public void onCountSelectedItemsChanged(Integer count) {

    }
}