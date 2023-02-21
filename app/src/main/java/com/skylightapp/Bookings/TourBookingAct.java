package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.Params.FULL_DAY;
import static com.skylightapp.Bookings.BookingConstant.Params.FULL_DAY_COMMON_PRICE;
import static com.skylightapp.Bookings.BookingConstant.Params.FULL_DAY_DTIME;
import static com.skylightapp.Bookings.BookingConstant.Params.FULL_DAY_PRIVATE_PRICE;
import static com.skylightapp.Bookings.BookingConstant.Params.FULL_DAY_RTIME;
import static com.skylightapp.Bookings.BookingConstant.Params.HALF_DAY;
import static com.skylightapp.Bookings.BookingConstant.Params.HALF_DAY_AFTER_DTIME;
import static com.skylightapp.Bookings.BookingConstant.Params.HALF_DAY_AFTER_RTIME;
import static com.skylightapp.Bookings.BookingConstant.Params.HALF_DAY_COMMON_PRICE;
import static com.skylightapp.Bookings.BookingConstant.Params.HALF_DAY_MORNING_DTIME;
import static com.skylightapp.Bookings.BookingConstant.Params.HALF_DAY_MORNING_RTIME;
import static com.skylightapp.Bookings.BookingConstant.Params.HALF_DAY_PRIVATE_PRICE;
import static com.skylightapp.Bookings.BookingConstant.URL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AsyncListener;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.HttpRequester;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.R;
import com.skylightapp.ViewPayAct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class TourBookingAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, AsyncListener {
    private PrefManager preferenceHelper;
    private Spinner spnTourType;
    ArrayList<String> tourType = new ArrayList<String>();
    private ArrayList<String> scheduleList = new ArrayList<String>();
    private ArrayList<String> timeList = new ArrayList<String>();
    ArrayList<String> tourPackages = new ArrayList<String>();
    ArrayList<String> personList = new ArrayList<String>();
    private ArrayAdapter<String> typeAdapter;
    private Spinner spnTourPackage;
    private ArrayAdapter<String> packageAdapter;
    private Bundle bundle;
    private Bundle fullDayBundle;
    private Bundle halfDayBundle;
    private ArrayList<PTour> fullDayPriceList;
    private ArrayList<PTour> halfDayPriceList;
    private double fullDayCommonPrice;
    private double halfDayCommonPrice;
    private Spinner spnTourSchedule;
    private ScheduleAdapter scheduleAdapter;
    private Spinner spnTourPersons;
    private ArrayAdapter<String> personAdapter;
    private double totalTarrif;
    private PTour pTour;
    private AppCompatTextView tvTotalTarrif;
    private AppCompatTextView txtTourDate;
    private int day;
    private int month;
    private int year;
    Calendar cal = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateset;
    private DatePickerDialog tourDatePiker;
    private static final String PREF_NAME = "awajima";
    SharedPreferences userPreferences;
    ContentLoadingProgressBar progressBar;
    private ProgressDialog progressDialog;
    String SharedPrefUserPassword;
    int SharedPrefCusID;
    String SharedPrefUserRole;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    Gson gson;
    String json,dateOfTranx,SharedPrefRole,SharedPrefUser,SharedPrefPassword;
    int profileUID,marketProfID;
    private Gson gson1,gson2,gson3;
    private Profile userProfile;
    private int profileID;
    private Account account;
    private String json1,json2,json3,nBankN,marketName;
    private Customer customer;
    private String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tour_booking);
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        gson= new Gson();
        gson1 = new Gson();
        gson3= new Gson();
        gson2= new Gson();
        fullDayBundle= new Bundle();
        customer= new Customer();
        userProfile= new Profile();
        spnTourPackage = (Spinner) findViewById(R.id.spnTourPackage);
        spnTourType = (Spinner) findViewById(R.id.spnTourType);
        spnTourSchedule = (Spinner) findViewById(R.id.spnTourSchedule);
        spnTourPersons = (Spinner) findViewById(R.id.spnTourPersons);
        tvTotalTarrif = findViewById(R.id.tvTotalTarrif);
        txtTourDate = findViewById(R.id.txtTourDate);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        customer = gson1.fromJson(json1, Customer.class);
        try {
            fullDayBundle = bundle.getBundle(FULL_DAY);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            halfDayBundle = bundle.getBundle(HALF_DAY);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            fullDayPriceList = (ArrayList<PTour>) fullDayBundle.getSerializable(FULL_DAY_PRIVATE_PRICE);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            halfDayPriceList = (ArrayList<PTour>) halfDayBundle.getSerializable(HALF_DAY_PRIVATE_PRICE);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            fullDayCommonPrice = fullDayBundle.getDouble(FULL_DAY_COMMON_PRICE);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            halfDayCommonPrice = halfDayBundle.getDouble(HALF_DAY_COMMON_PRICE);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        tourPackages.add(getResources().getString(R.string.text_common_tour));
        tourPackages.add(getResources().getString(R.string.private_luxury_tour));
        try {
            if (fullDayPriceList.size() > 0 && halfDayPriceList.size() > 0) {
                tourType.add(getResources().getString(R.string.text_full_day_tour));
                tourType.add(getResources().getString(R.string.text_half_day_tour));
            } else if (fullDayPriceList.size() == 0) {
                tourType.add(getResources().getString(R.string.text_half_day_tour));
            } else if (halfDayPriceList.size() == 0) {
                tourType.add(getResources().getString(R.string.text_full_day_tour));
            }



        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        packageAdapter = new ArrayAdapter<String>(this,
                R.layout.tour_spinner_item, R.id.tvSpnItem, tourPackages);
        spnTourPackage.setAdapter(packageAdapter);
        typeAdapter = new ArrayAdapter<String>(this,
                R.layout.tour_spinner_item, R.id.tvSpnItem, tourType);
        spnTourType.setAdapter(typeAdapter);
        scheduleAdapter = new ScheduleAdapter(this, scheduleList, timeList);
        spnTourSchedule.setAdapter(scheduleAdapter);
        personAdapter = new ArrayAdapter<String>(this,
                R.layout.tour_spinner_item, R.id.tvSpnItem, personList);
        spnTourPersons.setAdapter(personAdapter);

        spnTourPackage.setOnItemSelectedListener(this);
        spnTourType.setOnItemSelectedListener(this);
        spnTourSchedule.setOnItemSelectedListener(this);
        spnTourPersons.setOnItemSelectedListener(this);
        findViewById(R.id.btnBooking).setOnClickListener(this);
        txtTourDate.setOnClickListener(this);

        dateset = new DatePickerDialog.OnDateSetListener() {
            private String userDate;

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                userDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                txtTourDate.setText(userDate);
            }
        };
    }
    @Override
    public void onClick(View v) {
        preferenceHelper= new PrefManager();
        switch (v.getId()) {
            case R.id.btnBooking:

                try {
                    if (preferenceHelper.getDefaultCard() == 0) {
                        UtilsExtra.showToast(getResources().getString(R.string.no_card),
                                this);
                        startActivity(new Intent(this, ViewPayAct.class));
                    } else {
                        if (txtTourDate.getText().toString()
                                .equals(getString(R.string.text_booking_date))) {
                            UtilsExtra.showToast(
                                    getString(R.string.error), this);
                        } else {
                            bookTour();
                        }
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.home:
                onBackPressed();
                break;
            case R.id.txtTourDate:
                tourDatePiker = new DatePickerDialog(this, dateset, year, month,
                        day);
                tourDatePiker.getDatePicker().setMinDate(
                        System.currentTimeMillis() - 1000);
                tourDatePiker.show();
            default:
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }

    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
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
    protected void onStart() {
        super.onStart();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }

    private void bookTour() {
        checkInternetConnection();
        showProgressDialog();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.BOOK_TOUR);
        map.put(BookingConstant.Params.ID, String.valueOf(preferenceHelper.getUserId()));
        map.put(BookingConstant.Params.TOKEN,
                String.valueOf(preferenceHelper.getSessionToken()));
        map.put(BookingConstant.Params.TOUR_ID,
                String.valueOf(bundle.getInt(BookingConstant.Params.TOUR_ID)));
        map.put(BookingConstant.Params.PACKAGE, spnTourPackage.getSelectedItem()
                .toString());
        map.put(BookingConstant.Params.SCHEDULE, spnTourSchedule.getSelectedItem()
                .toString());
        map.put(BookingConstant.Params.PERSON, spnTourPersons.getSelectedItem()
                .toString());
        map.put(BookingConstant.Params.PRICE, String.valueOf(totalTarrif));
        map.put(BookingConstant.Params.TOUR_TYPE, spnTourType.getSelectedItem()
                .toString());
        map.put(BookingConstant.Params.BOOKING_DATE, txtTourDate.getText().toString());
        new HttpRequester(this, map, BookingConstant.ServiceCode.BOOK_TOUR, this);
    }

    @Override
    public void onItemSelected(AdapterView<?> view, View arg1, int arg2,
                               long arg3) {
        if (view == spnTourPackage) {
            personList.clear();
            spnTourPersons.setSelection(0);
            if (spnTourPackage.getSelectedItemPosition() == 0) {
                int commonTourMaxPerson = 8;
                for (int i = 1; i <= commonTourMaxPerson; i++) {
                    personList.add(i + " Person");
                }
            } else {
                if (spnTourType.getSelectedItem().toString()
                        .equals(getString(R.string.text_full_day_tour))) {
                    for (int i = 0; i < fullDayPriceList.size(); i++) {
                        pTour = fullDayPriceList.get(i);
                        personList.add("Max. " + pTour.getpTourPerson()
                                + " Person");
                    }
                } else {
                    for (int i = 0; i < halfDayPriceList.size(); i++) {
                        pTour = halfDayPriceList.get(i);
                        personList.add("Max. " + pTour.getpTourPerson()
                                + " Person");
                    }
                }
            }
            personAdapter.notifyDataSetChanged();
        }
        if (view == spnTourType) {
            scheduleList.clear();
            timeList.clear();
            if (spnTourType.getSelectedItem().toString()
                    .equals(getString(R.string.text_full_day_tour))) {
                scheduleList.add(getString(R.string.text_morning));
                timeList.add(getString(R.string.text_departure) + ":"
                        + fullDayBundle.getString(FULL_DAY_DTIME)
                        + " " + getString(R.string.text_return) + ":"
                        + fullDayBundle.getString(FULL_DAY_RTIME));
                if (spnTourPackage.getSelectedItemPosition() == 1) {
                    personList.clear();
                    for (int i = 0; i < fullDayPriceList.size(); i++) {
                        pTour = fullDayPriceList.get(i);
                        personList.add("Max. " + pTour.getpTourPerson()
                                + " Person");
                    }
                    spnTourPersons.setSelection(0);
                }
            } else {
                scheduleList.add(getString(R.string.text_morning));
                scheduleList.add(getString(R.string.text_afternoon));
                timeList.add(getString(R.string.text_departure)
                        + ":"
                        + halfDayBundle
                        .getString(HALF_DAY_MORNING_DTIME)
                        + " "
                        + getString(R.string.text_return)
                        + ":"
                        + halfDayBundle
                        .getString(HALF_DAY_MORNING_RTIME));
                timeList.add(getString(R.string.text_departure)
                        + ":"
                        + halfDayBundle
                        .getString(HALF_DAY_AFTER_DTIME)
                        + " "
                        + getString(R.string.text_return)
                        + ":"
                        + halfDayBundle
                        .getString(HALF_DAY_AFTER_RTIME));
                if (spnTourPackage.getSelectedItemPosition() == 1) {
                    personList.clear();
                    for (int i = 0; i < halfDayPriceList.size(); i++) {
                        pTour = halfDayPriceList.get(i);
                        personList.add("Max. " + pTour.getpTourPerson()
                                + " Person");
                        spnTourPersons.setSelection(0);
                    }
                }
            }
            scheduleAdapter.notifyDataSetChanged();
            personAdapter.notifyDataSetChanged();
        }
        try {
            selectedType = spnTourType.getSelectedItem().toString();



        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Common Tour Calculation
        if (spnTourPackage.getSelectedItemPosition() == 0) {
            try {
                int totalPersons = spnTourPersons.getSelectedItemPosition() + 1;
                if(selectedType !=null){
                    if (selectedType.equals(getString(R.string.text_full_day_tour))) {
                        totalTarrif = fullDayCommonPrice * totalPersons;
                    } else {
                        totalTarrif = halfDayCommonPrice * totalPersons;
                    }

                }



            } catch (NullPointerException e) {
                e.printStackTrace();
            }



        }

        else {
            try {
                if(selectedType !=null){
                    if (selectedType.equals(getString(R.string.text_full_day_tour))) {
                        totalTarrif = fullDayPriceList.get(
                                spnTourPersons.getSelectedItemPosition())
                                .getpTourPrice();
                    } else {
                        totalTarrif = halfDayPriceList.get(
                                spnTourPersons.getSelectedItemPosition())
                                .getpTourPrice();
                    }

                }



            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        tvTotalTarrif.setText("NGN" + totalTarrif);
    }
    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//you can cancel it by pressing back button
        progressDialog.setMessage("Processing wait ...");
        progressBar.show();//displays the progress bar
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}