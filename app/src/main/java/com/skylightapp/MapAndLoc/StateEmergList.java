package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Adapters.StringArAdapter;
import com.skylightapp.Admins.TrackWorkersAct;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class StateEmergList extends AppCompatActivity implements EmergencyReportAdapter.OnEmergClickListener, OnMapReadyCallback {
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private AppCompatButton transactions, savings, supportMessage;

    private RecyclerView recyclerViewToday,recyclerViewDate;

    private ArrayList<EmergencyReport> allSemergReports;
    private ArrayList<EmergencyReport> dateEmergency;
    private ArrayList<String > stateListFromDB;
    private EmergencyReportAdapter mAdapter;
    private EmergencyReportAdapter AllAdapter;

    DBHelper dbHelper;
    int emergencyCount;
    String json;
    private TextView txtEmergDateCount,txtAllEmergCount;
    String LastLocation;
    SQLiteDatabase sqLiteDatabase;
    private EmergReportDAO emergReportDAO;
    private static final String PREF_NAME = "skylight";
    private MarketBusiness marketBiz;
    private OfficeBranch office;
    private long bizID;
    Gson gson3,gson2,gson4,gson5;
    String json3,json4,json5,currentDate,selectedDate,json2;
    private OfficeBranchDAO officeBranchDAO;
    private String userRole,selectedState;
    protected DatePickerDialog datePickerDialog;
    DatePicker picker;
    private AppCompatSpinner spnState ;
    private Button btnSearch;
    private Market market;
    private StringArAdapter stringArAdapter;
    private FloatingActionButton homeFAB;
    private MaterialCardView cardView;
    private Awajima awajima;
    private Bundle bundle;
    private int dateCount;
    FloatingActionButton newFAB;
    private FragmentContainerView mapFrag;
    LatLng latLng;
    LatLng lastLatLng;
    String reportLatlng;
    private GoogleMap mMap;
    LatLng mostRecentLatLng;
    private ArrayList<LatLng > latLngs;
    private CircleImageView btnCloseSheet;
    private TextView txtDetals,txtReportSheetDate;
    private RecyclerView recylSheet;
    private EmergResponse emergencyResponse;
    private ArrayList<EmergResponse> emergResponseList;
    private EmergResAdapter responseAdapter;

    private LinearLayoutCompat sheetLayout;
    private Button btnMore;
    private double lat;
    private double lng;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_emerg_list);
        recyclerViewToday = findViewById(R.id.recyVEmergToday);
        recyclerViewDate = findViewById(R.id._EmergList_state);
        txtEmergDateCount = findViewById(R.id.tx34Emerg_C13);
        txtAllEmergCount = findViewById(R.id.txEmerg_C1);
        btnCloseSheet = findViewById(R.id.img_close_sheet56);
        txtDetals = findViewById(R.id.rE_Details);
        recylSheet = findViewById(R.id.recyV_Responses);
        sheetLayout = findViewById(R.id.bottom_response_sheet);
        btnMore = findViewById(R.id.btn_do_more);

        txtReportSheetDate = findViewById(R.id.rEport_Date);
        allSemergReports = new ArrayList<EmergencyReport>();
        emergResponseList= new ArrayList<>();
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();
        dateEmergency= new ArrayList<>();
        latLngs= new ArrayList<>();
        market= new Market();
        bundle= new Bundle();
        awajima= new Awajima();
        emergencyResponse= new EmergResponse();
        picker = findViewById(R.id.date_of_report);
        spnState = findViewById(R.id.state_spn);
        btnSearch = findViewById(R.id.btn_getReport);
        homeFAB = findViewById(R.id.emergFAB);
        newFAB = findViewById(R.id.createNewEmerg);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapStateR);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        latLng = new LatLng(4.8156, 7.0498);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Me!!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(19));


        cardView = findViewById(R.id.card_emerg);
        userProfile=new Profile();
        setTitle("State Emergencies");
        gson = new Gson();
        gson2 = new Gson();
        gson3= new Gson();
        gson4= new Gson();
        gson5= new Gson();
        dbHelper = new DBHelper(this);
        emergReportDAO= new EmergReportDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userRole = userPreferences.getString("machine", "");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd ", Locale.getDefault());
        currentDate = dateFormat.format(calendar.getTime());

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson2.fromJson(json2, MarketBusiness.class);

        json5 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson5.fromJson(json5, Profile.class);

        json3 = userPreferences.getString("LastMarketUsed", "");

        market = gson3.fromJson(json3, Market.class);

        json4 = userPreferences.getString("LastAwajimaUsed", "");

        awajima = gson4.fromJson(json4, Awajima.class);

        stateListFromDB= new ArrayList<>();

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        selectedDate = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        bundle.putParcelable("MarketBusiness",marketBiz);
        bundle.putParcelable("Market",market);
        bundle.putParcelable("Awajima",awajima);
        bundle.putParcelable("Profile",userProfile);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            stateListFromDB =emergReportDAO.getEmergencyStates();
        }
        newFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StateEmergList.this, AwajimaReportAct.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        homeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StateEmergList.this, LoginDirAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        stringArAdapter = new StringArAdapter(this, R.layout.item_string, stateListFromDB);
        spnState.setAdapter(stringArAdapter);

        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedState = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(awajima !=null){
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                allSemergReports = emergReportDAO.getStateEmergForToday(selectedState,currentDate);
            }


        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            dateEmergency =emergReportDAO.getStateEmergForDate(selectedState,selectedDate);
        }

        for (int i = 0; i < dateEmergency.size(); i++) {

            for (EmergencyReport placeIt : dateEmergency) {
                if (placeIt.getEmergReportID()>0)
                    continue;
                Objects.requireNonNull(mMap.addMarker(new MarkerOptions().position(new LatLng(placeIt.getEmergRLat(), placeIt.getEmergRLng()))
                        .title(placeIt.getEmergReport()).snippet(placeIt.getEmergRGroup()))).setIcon(
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_));
            }



        }

        //txtEmergencyCount.setText("Emergency:0");

        try {
            if(allSemergReports.size()>0){
                emergencyCount=allSemergReports.size();
                txtAllEmergCount.setText("Today Emergency Count:"+ emergencyCount);
            }else {
                txtAllEmergCount.setText("No Emergency Today");

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        AllAdapter = new EmergencyReportAdapter(this, R.layout.emergency_row, allSemergReports);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManager);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(AllAdapter);
        recyclerViewToday.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewToday);
        recyclerViewToday.setClickable(true);
        if(dateEmergency !=null){
            dateCount=dateEmergency.size();

        }


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {


                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(StateEmergList.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewDate.setLayoutManager(layoutManagerC);
                //recyclerView.setHasFixedSize(true);
                mAdapter = new EmergencyReportAdapter(StateEmergList.this,R.layout.emergency_row,dateEmergency);
                recyclerViewDate.setAdapter(mAdapter);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewDate.getContext(),
                        layoutManagerC.getOrientation());
                SnapHelper snapHelper23 = new PagerSnapHelper();
                recyclerViewDate.addItemDecoration(dividerItemDecoration7);
                snapHelper23.attachToRecyclerView(recyclerViewDate);



                if(dateCount >0){
                    txtEmergDateCount.setText(MessageFormat.format("Emergencies for Date:{0}", dateCount));

                }else if(dateCount ==0){
                    txtEmergDateCount.setText("No Emergencies  for Date");

                }
                for (int i = 0; i < dateEmergency.size(); i++) {

                    for (EmergencyReport placeIt : dateEmergency) {
                        if (placeIt.getEmergReportID()>0)
                            continue;
                        Objects.requireNonNull(mMap.addMarker(new MarkerOptions().position(new LatLng(placeIt.getEmergRLat(), placeIt.getEmergRLng()))
                                .title(placeIt.getEmergReport()).snippet(placeIt.getEmergRGroup()))).setIcon(
                                BitmapDescriptorFactory
                                        .fromResource(R.drawable.marker_));
                    }



                }


            }
        });
        btnSearch.setOnClickListener(this::getEmergReports);


    }

    private void chooseDate() {
        selectedDate = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

    }

    @Override
    public void onItemClick(EmergencyReport emergencyReport) {
        emergResponseList= new ArrayList<>();
        sheetLayout = findViewById(R.id.bottom_response_sheet);
        btnCloseSheet = findViewById(R.id.img_close_sheet56);
        txtDetals = findViewById(R.id.rE_Details);
        recylSheet = findViewById(R.id.recyV_Responses);
        btnMore = findViewById(R.id.btn_do_more);
        recylSheet.setNestedScrollingEnabled(true);
        if(emergencyReport !=null){
            emergResponseList=emergencyReport.getEmergResponses();
            lat=emergencyReport.getEmergRLat();
            lng=emergencyReport.getEmergRLng();
            lastLatLng=new LatLng(lat,lng);

        }

        BottomSheetBehavior behavior = BottomSheetBehavior.from(sheetLayout);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        btnCloseSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                //behavior.dismiss();

            }
        });
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        LinearLayoutManager layoutMan
                = new LinearLayoutManager(StateEmergList.this, LinearLayoutManager.HORIZONTAL, false);
        recylSheet.setLayoutManager(layoutMan);

        responseAdapter = new EmergResAdapter(StateEmergList.this,emergResponseList);
        recylSheet.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDeco = new DividerItemDecoration(recylSheet.getContext(),
                layoutMan.getOrientation());
        SnapHelper snapHelp = new PagerSnapHelper();
        recylSheet.addItemDecoration(dividerItemDeco);
        snapHelp.attachToRecyclerView(recylSheet);
        Bundle bundle=new Bundle();
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LastLocation=emergencyReport.getEmergRLatLng();
                Intent intent = new Intent(StateEmergList.this, TrackWorkersAct.class);
                bundle.putParcelable("EmergencyReport",emergencyReport);
                bundle.putString("LastLocation",LastLocation);
                bundle.putParcelable("PreviousLocation",lastLatLng);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }
    public void getEmergReports(View view) {
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new EmergencyReportAdapter(StateEmergList.this,R.layout.emergency_row,dateEmergency);
        recyclerViewDate.setAdapter(mAdapter);
    }
    public void createNotification(String title, String body) {
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setLightColor( Color.GREEN);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, StateEmergList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
            }
            pendingIntent = PendingIntent.getActivity(this, 0, intent, flags);

            builder.setContentTitle(title)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)
                    .setDefaults( Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);


            intent = new Intent(this, StateEmergList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(title)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}