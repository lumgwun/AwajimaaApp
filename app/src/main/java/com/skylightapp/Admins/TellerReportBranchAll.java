package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.TellerReportAdapter;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TellerReportBranchAll extends AppCompatActivity implements TellerReportAdapter.OnItemsClickListener {
    private static final String TAG = TellerReportBranchAll.class.getSimpleName();

    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private AdminUser adminUser;
    private CustomerManager customerManager;

    private RecyclerView recyclerView;
    private SearchView iSearchView;

    private SearchManager manager;

    private ArrayList<TellerReport> tellerReportArrayList;
    private TellerReportAdapter mAdapter;

    DBHelper dbHelper;
    String json;
    String currentDate;
    Gson gson1,gson2;
    String json1,json2,officeBranch;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_report_branch);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        userProfile = new Profile();
        adminUser = new AdminUser();
        tellerReportArrayList=new ArrayList<TellerReport>();
        manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        json2 = userPreferences.getString("LastAdminUserUsed", "");
        json = userPreferences.getString("LastProfileUsed", "");
        adminUser = gson2.fromJson(json, AdminUser.class);
        userProfile = gson.fromJson(json, Profile.class);
        workersDAO= new WorkersDAO(this);
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        officeBranch=userPreferences.getString("USER_OFFICE","");
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.TellerB_fab);

        if(userProfile !=null){
            long profileID =userProfile.getPID();

        }


        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = mdformat.format(calendar.getTime());
        recyclerView = findViewById(R.id.recycler_view_tellerB);
        mAdapter = new TellerReportAdapter(this, R.layout.teller_report_row, tellerReportArrayList);
        dbHelper = new DBHelper(this);
        tellerReportArrayList = tReportDAO.getTellerReportForABranch(officeBranch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminHome(adminUser,userProfile);
            }
        });
    }

    private void adminHome(AdminUser adminUser, Profile userProfile) {
        Bundle extras = new Bundle();
        extras.putParcelable("Profile",userProfile);
        extras.putParcelable("AdminUser",adminUser);
        extras.putString("USER_OFFICE",officeBranch);
        Intent intent = new Intent(this, AdminDrawerActivity.class);
        intent.putExtras(extras);
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_branch, menu);
        final MenuItem item = menu.findItem(R.id.searchMenub);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)) {

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<String> itemList = new ArrayList<>();

                for (String items : itemList) {
                    if (items.toLowerCase().contains(s.toLowerCase())) ;
                    itemList.add(items);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (TellerReportBranchAll.this,
                                android.R.layout.simple_list_item_1, itemList);

                return true;
            }
        });
        return true;
    }

    @Override
    public void onItemClick(TellerReport tellerReport, String officeBranch) {
        Bundle extras = new Bundle();
        extras.putParcelable("TellerReport",tellerReport);
        extras.putParcelable("CustomerManager",customerManager);
        extras.putParcelable("AdminUser",adminUser);
        extras.putString("USER_OFFICE",officeBranch);
        Intent intent = new Intent(this, TellerReportUpdateAct.class);
        intent.putExtras(extras);
        startActivity(intent);


    }
}