package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skylightapp.Adapters.ADepositAdapter;
import com.skylightapp.Adapters.ProfileSpinnerAdapter;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.AlarmReceiver;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ADepositList extends AppCompatActivity implements ADepositAdapter.OnItemsClickListener, AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerViewCustomDate;
    private RecyclerView recyclerViewOffice;
    private RecyclerView recyclerViewAdmin,recyclerViewAll;
    public static final String IMPORTANT_LIST_ID = "IMP_Id";
    private RecyclerView recyclerViewToday;
    private ArrayList<AdminBankDeposit> adminBankDepositToday;
    private ArrayList<String> adminNames;
    private ArrayList<AdminBankDeposit> adminBankDepositCustomDate;
    private ArrayList<AdminBankDeposit> adminBankDepositOffice;
    private ArrayList<AdminBankDeposit> adminBankDepositAdmin;
    private ArrayList<AdminBankDeposit> adminBankDepositAll;
    ArrayAdapter<String> adapterAdmin;
    private ProfileSpinnerAdapter profileSpinnerAdapter;

    ArrayAdapter<String> stringArrayAdapter;
    private ADepositAdapter adapterToday;
    private ADepositAdapter adapterCustomDate;
    private ADepositAdapter adapterOffice;
    private ADepositAdapter adapterFullAdmin;
    private ADepositAdapter adapterAll;
    private Toolbar mToolbar;
    Context context;

    private AlarmReceiver mAlarmReceiver;

    DBHelper dbHelper;
    private String stringOfficeBranch,dateOfCustomDays,selectedOffice,selectedAdmin,dateOfDeposit;
    private String[] mDateSplit;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private Date mBirthDate;
    private TextView txtTodayCount;
    int todayDepositCount,depositCountAdmin,depositCountOffice,depositCountAll;
    private  Date customDayDate;
    int selectedAdminIndex,selectedOfficeIndex;
    AppCompatSpinner spnOffice,spnAdminName;
    private AppCompatButton btnByBranch,btnByAdmin,btnByDate,btnLayoutOffice,btnLayoutAdmin,btnLayoutDate;
    private  Bundle bundle;
    DatePicker picker;
    private SearchView searchView;
    LinearLayout dateLayout,adminLayout, allDepositLayout,officeLayoutTittle;
    CardView dateCard,adminCard,allDepositCard,officeSpnCard,officeBtnCard,dateCard2,adminCardDepositor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_deposit_list);
        dbHelper= new DBHelper(this);
        adminBankDepositOffice=new ArrayList<>();
        adminBankDepositToday=new ArrayList<>();
        adminBankDepositAdmin=new ArrayList<>();
        adminBankDepositCustomDate=new ArrayList<>();
        adminBankDepositAll=new ArrayList<>();
        adminNames=new ArrayList<String>();

        recyclerViewCustomDate = findViewById(R.id.recyclerViewCD);
        recyclerViewOffice = findViewById(R.id.recyclerViewOffices);
        recyclerViewAdmin = findViewById(R.id.recyclerViewAd);
        recyclerViewToday = findViewById(R.id.recyclerViewDToday);
        recyclerViewAll = findViewById(R.id.recyclerViewAll);
        txtTodayCount = findViewById(R.id.txtTodayCount);
        //btnAllDB = findViewById(R.id.buttonAllDeposit);
        spnAdminName = findViewById(R.id.spnAdminNames);
        btnByBranch = findViewById(R.id.buttonSBranch);
        btnByAdmin = findViewById(R.id.buttonSAdmin);
        btnByDate = findViewById(R.id.buttonSDate);
        spnOffice = findViewById(R.id.officeBranchSpn);
        spnAdminName.setOnItemSelectedListener(this);
        spnOffice.setOnItemSelectedListener(this);
        bundle= new Bundle();
        btnLayoutAdmin = findViewById(R.id.buttonByAdmin);
        btnLayoutDate = findViewById(R.id.buttonByDate);
        btnLayoutOffice = findViewById(R.id.buttonByO);
        picker = findViewById(R.id.admin_date_deposit);
        dateCard = findViewById(R.id.cardDateDeposit);
        dateCard2 = findViewById(R.id.cardSDate);
        dateLayout = findViewById(R.id.layoutSDate);
        adminLayout = findViewById(R.id.layoutPayer);
        allDepositLayout = findViewById(R.id.layoutAllDeposit);
        adminCard = findViewById(R.id.cardSAdmin);
        adminCardDepositor = findViewById(R.id.cardDepositorSpn);
        allDepositCard = findViewById(R.id.cardAllDeposit);
        officeSpnCard = findViewById(R.id.cardBranchOffice);
        officeBtnCard = findViewById(R.id.cardBranchBtn);
        officeLayoutTittle = findViewById(R.id.layoutOfficeTittle);
        btnLayoutAdmin.setOnClickListener(this::revealAdminLayout);
        btnLayoutOffice.setOnClickListener(this::revealOfficeLayout);
        btnLayoutDate.setOnClickListener(this::revealDateLayout);
        btnLayoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminCard.setVisibility(View.VISIBLE);
                adminLayout.setVisibility(View.VISIBLE);
                recyclerViewAdmin.setVisibility(View.VISIBLE);
                adminCardDepositor.setVisibility(View.VISIBLE);

            }
        });
        btnLayoutAdmin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                adminCard.setVisibility(View.GONE);
                adminLayout.setVisibility(View.GONE);
                recyclerViewAdmin.setVisibility(View.GONE);
                adminCardDepositor.setVisibility(View.GONE);
                return false;
            }
        });
        btnLayoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCustomDate.setVisibility(View.VISIBLE);
                dateCard.setVisibility(View.VISIBLE);
                dateCard2.setVisibility(View.VISIBLE);
                dateLayout.setVisibility(View.VISIBLE);

            }
        });
        btnLayoutDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewCustomDate.setVisibility(View.GONE);
                dateCard.setVisibility(View.GONE);
                dateCard2.setVisibility(View.GONE);
                dateLayout.setVisibility(View.GONE);
                return false;
            }
        });
        btnLayoutOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                officeSpnCard.setVisibility(View.VISIBLE);
                officeBtnCard.setVisibility(View.VISIBLE);
                officeLayoutTittle.setVisibility(View.VISIBLE);
                recyclerViewOffice.setVisibility(View.VISIBLE);

            }
        });
        btnLayoutOffice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                officeSpnCard.setVisibility(View.GONE);
                officeBtnCard.setVisibility(View.GONE);
                officeLayoutTittle.setVisibility(View.GONE);
                recyclerViewOffice.setVisibility(View.GONE);
                return false;
            }
        });
        btnByAdmin.setOnClickListener(this::getAdminDepositByAdmin);
        btnByBranch.setOnClickListener(this::getAdminDepositByOffice);
        btnByDate.setOnClickListener(this::getAdminDepositByDate);


        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        spnOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringOfficeBranch = (String) parent.getSelectedItem();
                Toast.makeText(ADepositList.this, "Office Branch: "+ stringOfficeBranch,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(dbHelper !=null){
            adminNames=dbHelper.getAllAdminNamesForDeposit();
        }

        try {

            profileSpinnerAdapter = new ProfileSpinnerAdapter(this, android.R.layout.simple_spinner_item, adminNames);
            spnAdminName.setAdapter(profileSpinnerAdapter);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        spnAdminName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAdmin = parent.getItemAtPosition(position).toString();
                Toast.makeText(ADepositList.this, "Depositor: "+ selectedAdmin,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //selectedAdmin = String.valueOf(spnAdminName.getSelectedItem());
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfDeposit);
            }
        });

        dateOfDeposit = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(calendar1.getTime());
        todayDepositCount=dbHelper.getAllAdminDepositCountDate(todayDate);
        adminBankDepositOffice=dbHelper.getAdminDepositsForBranch(stringOfficeBranch);
        adminBankDepositCustomDate=dbHelper.getAdminDepositsAtDate(dateOfDeposit);
        adminBankDepositAdmin=dbHelper.getAdminDepositsByDepositor(selectedAdmin);

        depositCountAdmin=dbHelper.getAllAdminDepositCountForAdmin(selectedAdmin);
        depositCountAll=dbHelper.getAllAdminDepositCount();
        depositCountOffice=dbHelper.getAllAdminDepositCountForOffice(stringOfficeBranch);
        adminBankDepositAll=dbHelper.getAllAdminBankDeposit();


        recyclerViewOffice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterOffice = new ADepositAdapter(ADepositList.this, adminBankDepositOffice);


        if(todayDepositCount >0){
            txtTodayCount.setText("Deposits Today:"+ todayDepositCount);

        }else if(todayDepositCount ==0){
            txtTodayCount.setText("Sorry! no deposit,for today, yet");

        }

        adapterCustomDate = new ADepositAdapter(this, R.layout.deposit_row, adminBankDepositCustomDate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);



        adapterAll = new ADepositAdapter(this, R.layout.deposit_row, adminBankDepositAll);
        LinearLayoutManager linearLayoutManagerAll = new LinearLayoutManager(this);



        adapterFullAdmin = new ADepositAdapter(this,adminBankDepositAdmin);
        LinearLayoutManager linearLayoutManagerF = new LinearLayoutManager(this);



        adapterToday = new ADepositAdapter(this,adminBankDepositToday);
        LinearLayoutManager linearLayoutManagerT = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManagerT);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(adapterToday);
        recyclerViewToday.setNestedScrollingEnabled(false);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewToday);
        recyclerViewToday.setClickable(true);
        btnByBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewOffice.setAdapter(adapterOffice);
                recyclerViewOffice.setItemAnimator(new DefaultItemAnimator());
                recyclerViewOffice.addItemDecoration(new DividerItemDecoration(ADepositList.this,DividerItemDecoration.VERTICAL));
                //SnapHelper snapHelperOffice = new PagerSnapHelper();
                //snapHelperOffice.attachToRecyclerView(recyclerViewOffice);

            }
        });
        btnByAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdmin.setLayoutManager(linearLayoutManagerF);
                recyclerViewAdmin.setItemAnimator(new DefaultItemAnimator());
                recyclerViewAdmin.addItemDecoration(new DividerItemDecoration(ADepositList.this,DividerItemDecoration.VERTICAL));
                recyclerViewAdmin.setAdapter(adapterFullAdmin);
                recyclerViewAdmin.setNestedScrollingEnabled(false);
                recyclerViewAdmin.setClickable(true);

            }
        });
        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCustomDate.setLayoutManager(linearLayoutManager);
                recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
                recyclerViewCustomDate.setAdapter(adapterCustomDate);
                recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(ADepositList.this,DividerItemDecoration.VERTICAL));
                recyclerViewCustomDate.setNestedScrollingEnabled(false);
                recyclerViewCustomDate.setClickable(true);

            }
        });
        recyclerViewAll.setLayoutManager(linearLayoutManagerAll);
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.addItemDecoration(new DividerItemDecoration(ADepositList.this,DividerItemDecoration.VERTICAL));
        recyclerViewAll.setNestedScrollingEnabled(false);
        recyclerViewAll.setClickable(true);

    }
    private void chooseDate(String dateOfDeposit) {
        dateOfDeposit = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();


    }


    @Override
    public void onItemClick(AdminBankDeposit adminBankDeposit) {
        bundle= new Bundle();
        bundle.putParcelable("AdminBankDeposit",adminBankDeposit);
        Intent intent = new Intent(ADepositList.this, UpDateDeposit.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void getAdminDepositByDate(View view) {
    }

    public void revealOfficeLayout(View view) {
    }

    public void revealAdminLayout(View view) {
    }

    public void revealDateLayout(View view) {
    }

    public void getAdminDepositByOffice(View view) {
    }

    public void getAllDeposits(View view) {
    }

    public void getAdminDepositByAdmin(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == spnOffice.getId()) {
            selectedOfficeIndex = i;
            try {
                selectedOffice = (String) adapterView.getSelectedItem();

                try {
                    if(selectedOfficeIndex==0){
                        spnOffice.setFocusable(true);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            selectedOffice = (String) adapterView.getSelectedItem();

        }
        else if (adapterView.getId() == spnAdminName.getId()) {
            selectedAdminIndex = i;
            try {
                selectedAdmin = (String) adapterView.getSelectedItem();

                try {
                    if(selectedAdminIndex==0){
                        spnAdminName.setFocusable(true);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            selectedAdmin = (String) adapterView.getSelectedItem();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_deposit_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search23)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterAll.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapterAll.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search23) {
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
}