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
import com.skylightapp.Classes.SkylightCash;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.Tellers.UpdateTellerCashAct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SkylightCashList extends AppCompatActivity implements SkylightCashAdapter.OnItemsClickListener, AdapterView.OnItemSelectedListener{
    private RecyclerView recyclerViewCustomDate,recyclerCTo,recyclerViewFrom;
    private RecyclerView recyclerViewPayee;
    private RecyclerView recyclerViewPayer,recyclerViewAll;
    public static final String IMPORTANT_LIST_ID = "IMP_Id";
    private RecyclerView recyclerViewToday;
    private ArrayList<SkylightCash> skylightCashToday;
    private ArrayList<String> workersNames;
    private ArrayList<SkylightCash> skylightCashCustomDate;
    private ArrayList<SkylightCash> payeeOfSkylightCash;
    private ArrayList<SkylightCash> payersOfSkylightCash;
    private ArrayList<SkylightCash> skylightCashAll;
    private ArrayList<SkylightCash> fromCategoryOfSkylightUsers;
    private ArrayList<SkylightCash> toCategoryOfSkylightUsers;
    private ArrayList<SkylightCash> toCategoryOfSkylightUsersWithDate;
    private ArrayList<SkylightCash> fromCategoryOfSkylightUsersWithDate;


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
    double tCTotalPayer,tCTotalBranch,tCTotalPayee;
    LinearLayout layoutCustomDate, layoutPayer, allTCLayout, layoutPayeeTittle,layoutTo,layoutFrom;
    CardView dateCard, cardPayer2, allTCCard, cardLayoutPayeeSpn,cardLayoutTo, cardATo,cardPayeeBtnLayout,dateCard2,cardLayoutAF, cardLayoutFrom,cardPayerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_tcash);
        dbHelper= new DBHelper(this);
        payeeOfSkylightCash =new ArrayList<>();
        skylightCashToday =new ArrayList<>();
        payersOfSkylightCash =new ArrayList<>();
        skylightCashCustomDate =new ArrayList<>();
        fromCategoryOfSkylightUsers =new ArrayList<>();
        toCategoryOfSkylightUsers =new ArrayList<>();
        skylightCashAll =new ArrayList<>();
        workersNames =new ArrayList<>();
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

        btnLayoutFrom.setOnClickListener(this::revealFromLayout);
        btnLayoutPayer.setOnClickListener(this::revealPayerLayout);
        btnLayoutDate.setOnClickListener(this::revealDateLayout);
        btnLayoutTo.setOnClickListener(this::revealToLayout);
        btnLayoutPayee.setOnClickListener(this::revealPayeeLayout);
        buttonSCFrom.setOnClickListener(this::getSCashByFrom);
        btnLayoutPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        btnByAPayer.setOnClickListener(this::getSCashByPayer);
        btnByPayee.setOnClickListener(this::getSkylightCashByPayee);
        btnByDate.setOnClickListener(this::getSkylightCashByDate);


        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        if(dbHelper !=null){
            workersNames =dbHelper.getAllWorkers();
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
                selectedPayee = parent.getItemAtPosition(position).toString();
                Toast.makeText(SkylightCashList.this, "Payee: "+ selectedPayee,Toast.LENGTH_SHORT).show();
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
                selectedPayer = parent.getItemAtPosition(position).toString();
                Toast.makeText(SkylightCashList.this, "Payer: "+ selectedPayer,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnCashFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFrom = parent.getItemAtPosition(position).toString();
                Toast.makeText(SkylightCashList.this, "From: "+ selectedFrom,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnCashTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTo = parent.getItemAtPosition(position).toString();
                Toast.makeText(SkylightCashList.this, "To: "+ selectedTo,Toast.LENGTH_SHORT).show();
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(calendar1.getTime());
        if(dateOfSC !=null){
            dateOfSC =todayDate;
        }
        todaySCAmount =dbHelper.getAllSkylightCashCountForDate(todayDate);
        payeeOfSkylightCash =dbHelper.getAllSkylightCashForPayee(selectedPayee);
        skylightCashCustomDate =dbHelper.getAllSCashAtDate(dateOfSC);
        payersOfSkylightCash =dbHelper.getAllSkylightCashForPayer(selectedPayer);

        fromCategoryOfSkylightUsers =dbHelper.getAllSkylightCashForFromCategory(selectedFrom);
        toCategoryOfSkylightUsers =dbHelper.getAllSkylightCashForToCategory(selectedTo);
        fromCategoryOfSkylightUsersWithDate =dbHelper.getAllSkylightCashForFromCategoryAndDate(selectedTo,dateOfSC);
        toCategoryOfSkylightUsersWithDate =dbHelper.getAllSkylightCashForToCategoryAndDate(selectedTo,dateOfSC);

        tCCountPayer =dbHelper.getTellerCashCountForPayerWithDate(selectedPayer, dateOfSC);
        tCTotalPayer =dbHelper.getSCashTotalForPayerAndDate(selectedPayer, dateOfSC);
        tCTotalPayee =dbHelper.getSCashTotalForPayeeAndDate(selectedPayer, dateOfSC);

        tCCountAll =dbHelper.getAllTellerCashCountWithDate(dateOfSC);
        tCCountPayee =dbHelper.getTellerCashCountForPayeeWithDate(selectedPayee, dateOfSC);
        skylightCashAll =dbHelper.getAllSkylightCash();

        if(todaySCAmount >0){
            txtTodayTotal.setText("Skylight Cash Today:"+ todaySCAmount);

        }else if(todaySCAmount ==0){
            txtTodayTotal.setText("Sorry! no Skylight Cash,for today, yet");

        }


        adapterToday = new SkylightCashAdapter(this, skylightCashToday);
        LinearLayoutManager linearLayoutManagerToday = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManagerToday);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(adapterToday);
        recyclerViewToday.setNestedScrollingEnabled(false);
        recyclerViewToday.setClickable(true);



        buttonSCFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewFrom.setLayoutManager(new LinearLayoutManager(SkylightCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterFroms = new SkylightCashAdapter(SkylightCashList.this, fromCategoryOfSkylightUsersWithDate);
                recyclerViewFrom.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFrom.setAdapter(adapterFroms);
                recyclerViewFrom.setNestedScrollingEnabled(false);
                recyclerViewFrom.setClickable(true);

            }
        });
        buttonSCTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerCTo.setLayoutManager(new LinearLayoutManager(SkylightCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterTos = new SkylightCashAdapter(SkylightCashList.this, toCategoryOfSkylightUsersWithDate);
                recyclerCTo.setItemAnimator(new DefaultItemAnimator());
                recyclerCTo.setAdapter(adapterTos);
                recyclerCTo.setNestedScrollingEnabled(false);
                recyclerCTo.setClickable(true);

            }
        });
        btnByPayee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewPayee.setLayoutManager(new LinearLayoutManager(SkylightCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterOffice = new SkylightCashAdapter(SkylightCashList.this, payeeOfSkylightCash);
                recyclerViewPayee.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPayee.setAdapter(adapterPayees);
                recyclerViewPayee.setNestedScrollingEnabled(false);
                recyclerViewPayee.setClickable(true);

            }
        });
        btnByAPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewPayer.setLayoutManager(new LinearLayoutManager(SkylightCashList.this, LinearLayoutManager.HORIZONTAL, false));
                adapterPayers = new SkylightCashAdapter(SkylightCashList.this, payersOfSkylightCash);
                recyclerViewPayer.setItemAnimator(new DefaultItemAnimator());
                recyclerViewPayer.setAdapter(adapterPayers);
                recyclerViewPayer.setNestedScrollingEnabled(false);
                recyclerViewPayer.setClickable(true);

            }
        });
        adapterCustomDate = new SkylightCashAdapter(this, R.layout.skylight_cash_row, skylightCashCustomDate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCustomDate.setLayoutManager(linearLayoutManager);
                recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
                recyclerViewCustomDate.setAdapter(adapterCustomDate);
                recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(SkylightCashList.this,DividerItemDecoration.VERTICAL));
                recyclerViewCustomDate.setNestedScrollingEnabled(false);
                //SnapHelper snapHelperCDate = new PagerSnapHelper();
                //snapHelperCDate.attachToRecyclerView(recyclerViewCustomDate);
                recyclerViewCustomDate.setClickable(true);

            }
        });
        adapterAll = new SkylightCashAdapter(this, R.layout.skylight_cash_row, skylightCashAll);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(SkylightCashList.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.addItemDecoration(new DividerItemDecoration(SkylightCashList.this,DividerItemDecoration.VERTICAL));
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

    public void getSkylightCashByDate(View view) {
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
    }

    @Override
    public void onItemClick(SkylightCash skylightCash) {
        bundle= new Bundle();
        bundle.putParcelable("TellerCash", skylightCash);
        Intent intent = new Intent(SkylightCashList.this, UpdateTellerCashAct.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onItemClick(int adapterPosition) {


    }

    public void revealFromLayout(View view) {
    }

    public void revealToLayout(View view) {
    }

    public void revealPayeeLayout(View view) {
    }

    public void getSCashByFrom(View view) {
    }
}