package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skylightapp.Adapters.SkylightCashAdapter;
import com.skylightapp.Adapters.ProfileSpinnerAdapter;
import com.skylightapp.Classes.AlarmReceiver;
import com.skylightapp.Classes.AppCash;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.R;
import com.skylightapp.Tellers.UpdateTellerCashAct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AppCashList extends AppCompatActivity implements SkylightCashAdapter.OnItemsClickListener, AdapterView.OnItemSelectedListener{
    private RecyclerView recyclerViewCustomDate,recyclerCTo,recyclerViewFrom;
    private RecyclerView recyclerViewPayee;
    private RecyclerView recyclerViewPayer,recyclerViewAll;
    public static final String IMPORTANT_LIST_ID = "IMP_Id";
    private RecyclerView recyclerViewToday;
    private ArrayList<AppCash> appCashToday;
    private ArrayList<String> workersNames;
    private ArrayList<AppCash> appCashCustomDate;
    private ArrayList<AppCash> payeeOfAppCashes;
    private ArrayList<AppCash> payersOfAppCashes;
    private ArrayList<AppCash> appCashAll;
    private ArrayList<AppCash> fromCategoryOfSkylightUsers;
    private ArrayList<AppCash> toCategoryOfSkylightUsers;
    private ArrayList<AppCash> toCategoryOfSkylightUsersWithDate;
    private ArrayList<AppCash> fromCategoryOfSkylightUsersWithDate;


    ArrayAdapter<String> adapterAdmin;
    private ProfileSpinnerAdapter profileSpinnerAdapter;

    ArrayAdapter<String> stringArrayAdapter;
    private SkylightCashAdapter adapterToday;
    private SkylightCashAdapter adapterCustomDate;
    private SkylightCashAdapter adapterOffice;
    private SkylightCashAdapter adapterPayers;
    private SkylightCashAdapter adapterAll;
    private SkylightCashAdapter adapterFroms;
    private SkylightCashAdapter adapterTos;
    private SkylightCashAdapter adapterPayees;
    SQLiteDatabase sqLiteDatabase;


    private Toolbar mToolbar;
    Context context;

    private AlarmReceiver mAlarmReceiver;

    DBHelper dbHelper;
    private String dateOfCustomDays, selectedPayee,selectedFrom,selectedTo, selectedPayer, dateOfSC;
    private String[] mDateSplit;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private Date mBirthDate;
    private TextView txtTodayTotal;
    int todaySCAmount, tCCountPayer, tCCountPayee, tCCountAll;
    private  Date customDayDate;
    int selectedPayerIndex, selectedPayeeIndex,selectedFromIndex,selectedToIndex;
    AppCompatSpinner spnPayee, spnCashPayer,spnCashFrom,spnCashTo;
    private AppCompatButton btnByPayee, btnByAPayer,btnByDate,btnLayoutPayee,buttonSCTo,buttonSCFrom, btnLayoutPayer,btnLayoutTo, btnLayoutFrom,btnLayoutDate,btnAllDB;
    private  Bundle bundle;
    DatePicker picker;
    private  WorkersDAO workersDAO;
    private int spnIndex=0;
    private boolean takeAction=false;
    double tCTotalPayer,tCTotalBranch,tCTotalPayee;
    private static final String PREF_NAME = "awajima";
    LinearLayout layoutCustomDate, layoutPayer, allTCLayout, layoutPayeeTittle,layoutTo,layoutFrom;
    CardView dateCard, cardPayer2, allTCCard, cardLayoutPayeeSpn,cardLayoutTo, cardATo,cardPayeeBtnLayout,dateCard2,cardLayoutAF, cardLayoutFrom,cardPayerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_app_cash_list);
        dbHelper= new DBHelper(this);
        payeeOfAppCashes =new ArrayList<>();
        appCashToday =new ArrayList<>();
        payersOfAppCashes =new ArrayList<>();
        appCashCustomDate =new ArrayList<>();
        fromCategoryOfSkylightUsers =new ArrayList<>();
        toCategoryOfSkylightUsers =new ArrayList<>();
        appCashAll =new ArrayList<>();
        workersNames =new ArrayList<>();
        workersDAO = new WorkersDAO(this);
        txtTodayTotal = findViewById(R.id.tcTodayTotal);

        btnLayoutFrom = findViewById(R.id.buttonByFrom);
        btnLayoutTo = findViewById(R.id.buttonByTo);
        btnLayoutDate = findViewById(R.id.buttonByScDate);
        btnLayoutPayer = findViewById(R.id.buttonByScPayer);
        btnLayoutPayee = findViewById(R.id.buttonByPayee);
        recyclerViewFrom = findViewById(R.id.recyclerFrom);
        recyclerCTo = findViewById(R.id.recyclerCTo);
        recyclerViewToday = findViewById(R.id.recyclerSCashToday);
        recyclerViewPayer = findViewById(R.id.recyclerCashPayer);
        recyclerViewAll = findViewById(R.id.recyclerAllSCash);
        recyclerViewPayee = findViewById(R.id.recyclerSCPayee);
        recyclerViewCustomDate = findViewById(R.id.recyclerSCashCDate);

        layoutFrom = findViewById(R.id.layoutFrom);

        cardLayoutFrom = findViewById(R.id.cardLayoutFrom);
        spnCashFrom = findViewById(R.id.spnCashFrom);
        cardLayoutAF = findViewById(R.id.cardAF);
        buttonSCFrom = findViewById(R.id.buttonSCFrom);

        layoutTo = findViewById(R.id.layoutTo);

        cardLayoutTo = findViewById(R.id.cardLayoutTo);
        spnCashTo = findViewById(R.id.spnCashTo);
        cardATo = findViewById(R.id.cardATo);
        buttonSCTo = findViewById(R.id.buttonSCTo);

        layoutCustomDate = findViewById(R.id.layoutTcDate);

        dateCard = findViewById(R.id.cardDatePicker);
        picker = findViewById(R.id.sc_date_picker);
        dateCard2 = findViewById(R.id.cardTCDates);
        btnByDate = findViewById(R.id.buttonTcDate);
        layoutPayer = findViewById(R.id.layoutPayer);

        cardPayerLayout = findViewById(R.id.cardPayerLayout);
        spnCashPayer = findViewById(R.id.spnCashPayer);
        cardPayer2 = findViewById(R.id.cardAPayer);
        btnByAPayer = findViewById(R.id.buttonSCPayer);
        allTCLayout = findViewById(R.id.layoutAllSkylightCash);

        allTCCard = findViewById(R.id.cardAllTC);
        btnAllDB = findViewById(R.id.buttonAllTC);
        layoutPayeeTittle = findViewById(R.id.layoutPayee33);
        cardLayoutPayeeSpn = findViewById(R.id.cardLayoutPayee);
        spnPayee = findViewById(R.id.payeeSkylightCash);
        cardPayeeBtnLayout = findViewById(R.id.cardPayeeLayout);
        btnByPayee = findViewById(R.id.buttonSCPayee);

        spnCashPayer.setOnItemSelectedListener(this);
        spnPayee.setOnItemSelectedListener(this);
        bundle= new Bundle();

        //btnLayoutFrom.setOnClickListener(this::revealFromLayout);
        //btnLayoutPayer.setOnClickListener(this::revealPayerLayout);
        btnLayoutDate.setOnClickListener(this::revealDateLayout);
        //btnLayoutTo.setOnClickListener(this::revealToLayout);
        //btnLayoutPayee.setOnClickListener(this::revealPayeeLayout);
        //buttonSCFrom.setOnClickListener(this::getSCashByFrom);
        btnLayoutPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                layoutPayer.setVisibility(View.VISIBLE);
                recyclerViewPayer.setVisibility(View.VISIBLE);
                spnCashPayer.setVisibility(View.VISIBLE);
                btnByAPayer.setVisibility(View.VISIBLE);
                cardPayerLayout.setVisibility(View.VISIBLE);
                cardPayer2.setVisibility(View.VISIBLE);

            }
        });
        btnLayoutPayer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutPayer.setVisibility(View.GONE);
                recyclerViewPayer.setVisibility(View.GONE);
                spnCashPayer.setVisibility(View.GONE);
                btnByAPayer.setVisibility(View.GONE);
                cardPayerLayout.setVisibility(View.GONE);
                cardPayer2.setVisibility(View.GONE);
                return false;
            }
        });

        btnLayoutPayee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                cardLayoutPayeeSpn.setVisibility(View.VISIBLE);
                cardPayeeBtnLayout.setVisibility(View.VISIBLE);
                layoutPayeeTittle.setVisibility(View.VISIBLE);
                recyclerViewPayee.setVisibility(View.VISIBLE);
                btnByPayee.setVisibility(View.VISIBLE);


            }
        });
        btnLayoutPayee.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                cardLayoutPayeeSpn.setVisibility(View.GONE);
                cardPayeeBtnLayout.setVisibility(View.GONE);
                layoutPayeeTittle.setVisibility(View.GONE);
                recyclerViewPayee.setVisibility(View.GONE);
                return false;
            }
        });
        btnLayoutFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                layoutFrom.setVisibility(View.VISIBLE);
                recyclerViewFrom.setVisibility(View.VISIBLE);
                cardLayoutFrom.setVisibility(View.VISIBLE);
                spnCashFrom.setVisibility(View.VISIBLE);
                cardLayoutAF.setVisibility(View.VISIBLE);
                buttonSCFrom.setVisibility(View.VISIBLE);

            }
        });
        btnLayoutFrom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutFrom.setVisibility(View.GONE);
                recyclerViewFrom.setVisibility(View.GONE);
                cardLayoutFrom.setVisibility(View.GONE);
                spnCashFrom.setVisibility(View.GONE);
                cardLayoutAF.setVisibility(View.GONE);
                buttonSCFrom.setVisibility(View.GONE);
                return false;
            }
        });
        btnLayoutTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                layoutTo.setVisibility(View.VISIBLE);
                recyclerCTo.setVisibility(View.VISIBLE);
                cardLayoutTo.setVisibility(View.VISIBLE);
                spnCashTo.setVisibility(View.VISIBLE);
                cardATo.setVisibility(View.VISIBLE);
                buttonSCTo.setVisibility(View.VISIBLE);


            }
        });
        btnLayoutTo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layoutTo.setVisibility(View.GONE);
                recyclerCTo.setVisibility(View.GONE);
                cardLayoutTo.setVisibility(View.GONE);
                spnCashTo.setVisibility(View.GONE);
                cardATo.setVisibility(View.GONE);
                buttonSCTo.setVisibility(View.GONE);
                return false;
            }
        });

        btnLayoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                recyclerViewCustomDate.setVisibility(View.VISIBLE);
                dateCard.setVisibility(View.VISIBLE);
                dateCard2.setVisibility(View.VISIBLE);
                layoutCustomDate.setVisibility(View.VISIBLE);

            }
        });
        btnLayoutDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewCustomDate.setVisibility(View.GONE);
                dateCard.setVisibility(View.GONE);
                dateCard2.setVisibility(View.GONE);
                layoutCustomDate.setVisibility(View.GONE);
                return false;
            }
        });

        //btnByAPayer.setOnClickListener(this::getSCashByPayer);
        //btnByPayee.setOnClickListener(this::getSkylightCashByPayee);
        //btnByDate.setOnClickListener(this::getSkylightCashByDate);


        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        if(workersDAO !=null){
            try {
                workersNames =workersDAO.getAllWorkers();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }

        try {

            profileSpinnerAdapter = new ProfileSpinnerAdapter(this, android.R.layout.simple_spinner_item, workersNames);
            spnPayee.setAdapter(profileSpinnerAdapter);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        spnPayee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;
                }else {
                    selectedPayee = parent.getItemAtPosition(position).toString();

                }

                Toast.makeText(AppCashList.this, "Payee: "+ selectedPayee,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        try {

            profileSpinnerAdapter = new ProfileSpinnerAdapter(this, android.R.layout.simple_spinner_item, workersNames);
            spnCashPayer.setAdapter(profileSpinnerAdapter);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        spnCashPayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;
                }else {
                    selectedPayer = parent.getItemAtPosition(position).toString();
                    Toast.makeText(AppCashList.this, "Payer: "+ selectedPayer,Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnCashFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;
                }else {
                    selectedFrom = parent.getItemAtPosition(position).toString();
                    Toast.makeText(AppCashList.this, "From: "+ selectedFrom,Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnCashTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    return;
                }else {
                    selectedTo = parent.getItemAtPosition(position).toString();
                    Toast.makeText(AppCashList.this, "To: "+ selectedTo,Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //selectedAdmin = String.valueOf(spnAdminName.getSelectedItem());
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfSC);
            }
        });

        dateOfSC = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar1.getTime());
        if(dateOfSC !=null){
            dateOfSC =todayDate;
        }
        if(dbHelper !=null){
            try {
                todaySCAmount =dbHelper.getAllSkylightCashCountForDate(todayDate);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                payeeOfAppCashes =dbHelper.getAllSkylightCashForPayee(selectedPayee);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                appCashCustomDate =dbHelper.getAllSCashAtDate(dateOfSC);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                payersOfAppCashes =dbHelper.getAllSkylightCashForPayer(selectedPayer);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                fromCategoryOfSkylightUsers =dbHelper.getAllSkylightCashForFromCategory(selectedFrom);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                toCategoryOfSkylightUsers =dbHelper.getAllSkylightCashForToCategory(selectedTo);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                tCCountPayer =dbHelper.getTellerCashCountForPayerWithDate(selectedPayer, dateOfSC);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                toCategoryOfSkylightUsersWithDate =dbHelper.getAllSkylightCashForToCategoryAndDate(selectedTo,dateOfSC);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                fromCategoryOfSkylightUsersWithDate =dbHelper.getAllSkylightCashForFromCategoryAndDate(selectedTo,dateOfSC);


            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                tCCountPayee =dbHelper.getTellerCashCountForPayeeWithDate(selectedPayee, dateOfSC);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                appCashAll =dbHelper.getAllSkylightCash();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                tCTotalPayer =dbHelper.getSCashTotalForPayerAndDate(selectedPayer, dateOfSC);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                tCTotalPayee =dbHelper.getSCashTotalForPayeeAndDate(selectedPayer, dateOfSC);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                tCCountAll =dbHelper.getAllTellerCashCountWithDate(dateOfSC);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }


        if(todaySCAmount >0){
            txtTodayTotal.setText(" Cash Today:"+ todaySCAmount);

        }else if(todaySCAmount ==0){
            txtTodayTotal.setText("Sorry! no  Cash,for today, yet");

        }


        adapterToday = new SkylightCashAdapter(this, appCashToday);
        LinearLayoutManager linearLayoutManagerToday = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManagerToday);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(adapterToday);
        recyclerViewToday.setNestedScrollingEnabled(false);
        recyclerViewToday.setClickable(true);



        buttonSCFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                recyclerViewFrom.setLayoutManager(new LinearLayoutManager(AppCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterFroms = new SkylightCashAdapter(AppCashList.this, fromCategoryOfSkylightUsersWithDate);
                recyclerViewFrom.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFrom.setAdapter(adapterFroms);
                recyclerViewFrom.setNestedScrollingEnabled(false);
                recyclerViewFrom.setClickable(true);

            }
        });
        buttonSCTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                recyclerCTo.setLayoutManager(new LinearLayoutManager(AppCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterTos = new SkylightCashAdapter(AppCashList.this, toCategoryOfSkylightUsersWithDate);
                recyclerCTo.setItemAnimator(new DefaultItemAnimator());
                recyclerCTo.setAdapter(adapterTos);
                recyclerCTo.setNestedScrollingEnabled(false);
                recyclerCTo.setClickable(true);

            }
        });
        btnByPayee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                recyclerViewPayee.setLayoutManager(new LinearLayoutManager(AppCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterOffice = new SkylightCashAdapter(AppCashList.this, payeeOfAppCashes);
                recyclerViewPayee.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPayee.setAdapter(adapterPayees);
                recyclerViewPayee.setNestedScrollingEnabled(false);
                recyclerViewPayee.setClickable(true);

            }
        });
        btnByAPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                recyclerViewPayer.setLayoutManager(new LinearLayoutManager(AppCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterPayers = new SkylightCashAdapter(AppCashList.this, payersOfAppCashes);
                recyclerViewPayer.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPayer.setAdapter(adapterPayers);
                recyclerViewPayer.setNestedScrollingEnabled(false);
                recyclerViewPayer.setClickable(true);

            }
        });
        adapterCustomDate = new SkylightCashAdapter(this, R.layout.skylight_cash_row, appCashCustomDate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeAction=true;
                recyclerViewCustomDate.setLayoutManager(linearLayoutManager);
                recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
                recyclerViewCustomDate.setAdapter(adapterCustomDate);
                recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(AppCashList.this,DividerItemDecoration.VERTICAL));
                recyclerViewCustomDate.setNestedScrollingEnabled(false);
                //SnapHelper snapHelperCDate = new PagerSnapHelper();
                //snapHelperCDate.attachToRecyclerView(recyclerViewCustomDate);
                recyclerViewCustomDate.setClickable(true);

            }
        });
        adapterAll = new SkylightCashAdapter(this, R.layout.skylight_cash_row, appCashAll);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(AppCashList.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.addItemDecoration(new DividerItemDecoration(AppCashList.this,DividerItemDecoration.VERTICAL));
        recyclerViewAll.setNestedScrollingEnabled(false);
        recyclerViewAll.setClickable(true);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewAll);

    }
    private void chooseDate(String dateOfTC) {
        dateOfTC = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();


    }





    public void revealDateLayout(View view) {
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == spnPayee.getId()) {
            selectedPayeeIndex = i;
            try {
                selectedPayee = (String) adapterView.getSelectedItem();

                try {
                    if(selectedPayeeIndex ==0){
                        spnPayee.setFocusable(true);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            selectedPayee = (String) adapterView.getSelectedItem();

        }
        if (adapterView.getId() == spnCashFrom.getId()) {
            selectedFromIndex = i;
            try {
                selectedFrom = (String) adapterView.getSelectedItem();

                try {
                    if(selectedFromIndex ==0){
                        spnCashFrom.setFocusable(true);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            selectedFrom = (String) adapterView.getSelectedItem();

        }
        if (adapterView.getId() == spnCashTo.getId()) {
            selectedToIndex = i;
            try {
                selectedTo = (String) adapterView.getSelectedItem();

                try {
                    if(selectedToIndex ==0){
                        spnCashTo.setFocusable(true);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            selectedTo = (String) adapterView.getSelectedItem();

        }
        else if (adapterView.getId() == spnCashPayer.getId()) {
            selectedPayerIndex = i;
            try {
                selectedPayer = (String) adapterView.getSelectedItem();

                try {
                    if(selectedPayerIndex ==0){
                        spnCashPayer.setFocusable(true);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            selectedPayer = (String) adapterView.getSelectedItem();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    /*public void getSkylightCashByDate(View view) {
    }

    public void getSCashByPayer(View view) {
    }

    public void getAllSkylightCash(View view) {
    }

    public void getSkylightCashByPayee(View view) {
    }

    public void revealPayerLayout(View view) {
    }

    public void revealLayout(View view) {
    }

    public void revealCashDateLayout(View view) {
    }

    public void revealCashTellerLayout(View view) {
    }*/

    @Override
    public void onItemClick(AppCash appCash) {
        bundle= new Bundle();
        bundle.putParcelable("TellerCash", appCash);
        Intent intent = new Intent(AppCashList.this, UpdateTellerCashAct.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onItemClick(int adapterPosition) {


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
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    /*public void revealFromLayout(View view) {
    }

    public void revealToLayout(View view) {
    }

    public void revealPayeeLayout(View view) {
    }

    public void getSCashByFrom(View view) {
    }*/
}