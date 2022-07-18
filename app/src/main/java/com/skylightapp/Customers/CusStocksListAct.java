package com.skylightapp.Customers;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.StockTransferAdapter;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.ADepositList;
import com.skylightapp.SuperAdmin.UpDateDeposit;
import com.skylightapp.SuperAdmin.UpdateSTCode;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CusStocksListAct extends AppCompatActivity implements StockTransferAdapter.OnItemsClickListener{
    Button btnUtility, btnOurPrivacyPolicy,btnInvestment;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private LinearLayout linearLayout;

    private AppCompatImageView imgTime;

    private Profile userProfile;
    private int profileID;
    Button btnMore;
    //drawLinechart(yValues,xValues);
    float yValues[]={10,20,30,0,40,60};
    CardView cardViewPackges,cardViewGrpSavings,cardViewHistory, cardViewStandingOrders, cardViewOrders, cardViewSupport;

    AppCompatTextView extName, textID,textAcctNo,textBalance, textInvestments,textSavings;
    FrameLayout frameLayout1,frameLayout2;
    CircleImageView profileImage;
    private Profile profile;

    private AppCompatTextView txtMessage, balance, sPackages, accountNo,txtSO, grpSavings, txtUserName, reports, standingOrders;
    private Intent data;
    private FloatingActionButton floatingActionButton;
    private Customer customer;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Account account;
    private ActionBarDrawerToggle toggle;
    private String surname,firstName,names,cusUserName;
    ImageButton hideLayouts;
    private int accountN, savings,skPackages;
    private double accountBalance;
    CircleImageView imgProfilePic;
    private  int SOCount,customerID,loanCount,txCount;
    private Button btnUtils,btnLoans,btnSupport;
    private StandingOrderAcct standingOrderAcct;
    private static final String PREF_NAME = "skylight";
    RecyclerView recyclerView, recyclerViewStocks;
    private SearchView searchView;
    private DBHelper dbHelper;
    private ArrayList<StockTransfer> stockTransferArrayList;
    private StockTransferAdapter stockAdap;
    private  Bundle bundle;
    String SharedPrefUserPassword,SharedPrefCusID,name,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_stocks);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        bundle= new Bundle();
        gson1 = new Gson();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        dbHelper= new DBHelper(this);
        standingOrderAcct= new StandingOrderAcct();
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        stockTransferArrayList=new ArrayList<>();
        if(customer !=null){
            customerID=customer.getCusUID();
            name=customer.getCusSurname()+","+customer.getCusFirstName();
        }
        recyclerViewStocks = findViewById(R.id.recyclerCusStocks);
        if(dbHelper !=null){
            stockTransferArrayList=dbHelper.getStocksToCustomer(name);
        }
        stockAdap = new StockTransferAdapter(this,stockTransferArrayList);
        LinearLayoutManager linearLayoutManagerT = new LinearLayoutManager(this);
        recyclerViewStocks.setLayoutManager(linearLayoutManagerT);
        recyclerViewStocks.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStocks.setAdapter(stockAdap);
        recyclerViewStocks.setNestedScrollingEnabled(false);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewStocks);
        recyclerViewStocks.setClickable(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cus_stocks_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchMyStocks)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                stockAdap.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                stockAdap.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchMyStocks) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClick(StockTransfer stockTransfer) {

        bundle= new Bundle();
        bundle.putParcelable("StockTransfer",stockTransfer);
        Intent intent = new Intent(CusStocksListAct.this, UpdateSTCode.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onItemClick(int adapterPosition) {

    }
}