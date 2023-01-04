package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.TAG;
import static com.skylightapp.Bookings.BookingConstant.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class HistoryAct extends ActionBarBaseAct implements AdapterView.OnItemClickListener {
    private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();
    private PinnedSectionListView lvHistory;
    private HistoryAd historyAdapter;
    private ArrayList<History> historyList;
    private ArrayList<History> historyListOrg;
    private PrefManager preferenceHelper;
    private ParseContent parseContent;
    private AppCompatImageView tvNoHistory;
    private ArrayList<Date> dateList = new ArrayList<Date>();
    private AppCompatTextView toDateBtn,fromDateBtn;

    Calendar cal = Calendar.getInstance();
    private int day;
    private int month;
    private int year;
    private DatePickerDialog fromPiker;
    private DatePickerDialog.OnDateSetListener dateset;
    private DatePickerDialog toPiker;
    private RequestQueue requestQueue;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json;
    private Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_history);
        setIconMenu(R.drawable.timeline1);
        setTitle("Taxi Booking History");
        gson = new Gson();
        profile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
        setIcon(R.drawable.ic_arrow_back_black_24dp);
        requestQueue = Volley.newRequestQueue(this);
        lvHistory = (PinnedSectionListView) findViewById(R.id.lvHistory);
        lvHistory.setOnItemClickListener(this);
        fromDateBtn = findViewById(R.id.fromDateBtn);
        toDateBtn =  findViewById(R.id.toDateBtn);
        fromDateBtn.setOnClickListener(this);
        toDateBtn.setOnClickListener(this);
        historyList = new ArrayList<History>();

        tvNoHistory = findViewById(R.id.ivEmptyView);
        findViewById(R.id.btnSearch).setOnClickListener(this);
        preferenceHelper = new PrefManager(this);
        parseContent = new ParseContent(this);
        dateList = new ArrayList<Date>();
        historyListOrg = new ArrayList<History>();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        getHistory();

        dateset = new DatePickerDialog.OnDateSetListener() {
            private String userDate;

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                userDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                if (view == fromPiker.getDatePicker())
                    fromDateBtn.setText(userDate);
                else
                    toDateBtn.setText(userDate);
            }
        };
    }

    /**
     *
     */
    private void getHistory() {
        if (!UtilsExtra.isNetworkAvailable(this)) {
            UtilsExtra.showToast(
                    getResources().getString(R.string.no_internet_connection),
                    this);
            return;
        }
        UtilsExtra.showCustomProgressDialog(this,
                getResources().getString(R.string.progress_getting_history),
                false, null);
        HashMap<String, String> map = new HashMap<String, String>();
        if (fromDateBtn.getText().toString()
                .equals(getString(R.string.from_date))
                && toDateBtn.getText().toString()
                .equals(getString(R.string.to_date))) {
            map.put(URL,
                    BookingConstant.ServiceType.HISTORY + BookingConstant.Params.ID + "="
                            + preferenceHelper.getUserId() + "&"
                            + BookingConstant.Params.TOKEN + "="
                            + preferenceHelper.getSessionToken());
        } else {
            AppLog.Log(
                    "History",
                    BookingConstant.ServiceType.HISTORY + BookingConstant.Params.ID + "="
                            + preferenceHelper.getUserId() + "&"
                            + BookingConstant.Params.TOKEN + "="
                            + preferenceHelper.getSessionToken() + "&"
                            + BookingConstant.Params.FROM_DATE + "="
                            + fromDateBtn.getText().toString() + "&"
                            + BookingConstant.Params.TO_DATE + "="
                            + toDateBtn.getText().toString());
            map.put(URL,
                    BookingConstant.ServiceType.HISTORY + BookingConstant.Params.ID + "="
                            + preferenceHelper.getUserId() + "&"
                            + BookingConstant.Params.TOKEN + "="
                            + preferenceHelper.getSessionToken() + "&"
                            + BookingConstant.Params.FROM_DATE + "="
                            + fromDateBtn.getText().toString() + "&"
                            + BookingConstant.Params.TO_DATE + "="
                            + toDateBtn.getText().toString());
        }
        // new HttpRequester(this, map, Const.ServiceCode.HISTORY, true, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.HISTORY, this, this));
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {

        if (mSeparatorsSet.contains(position))
            return;
        History history = historyListOrg.get(position);

        showBillDialog(history.getTimecost(), history.getTotal(),
                history.getDistanceCost(), history.getBasePrice(),
                history.getTime(), history.getDistance(),
                history.getPromoBonus(), history.getReferralBonus(), null);
    }


    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        UtilsExtra.removeCustomProgressDialog();
        switch (serviceCode) {
            case BookingConstant.ServiceCode.HISTORY:
                AppLog.Log("TAG", "History Response :" + response);
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    final Calendar cal = Calendar.getInstance();
                    historyListOrg.clear();
                    historyList.clear();
                    dateList.clear();
                    parseContent.parseHistory(response, historyList);

                    Collections.sort(historyList, new Comparator<History>() {
                        @Override
                        public int compare(History o1, History o2) {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                                    "yyyy-MM-dd hh:mm:ss");
                            try {
                                String firstStrDate = o1.getDate();
                                String secondStrDate = o2.getDate();
                                Date date2 = dateFormat.parse(secondStrDate);
                                Date date1 = dateFormat.parse(firstStrDate);
                                return date2.compareTo(date1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });

                    HashSet<Date> listToSet = new HashSet<Date>();
                    for (int i = 0; i < historyList.size(); i++) {
                        AppLog.Log("date", historyList.get(i).getDate() + "");
                        if (listToSet.add(sdf.parse(historyList.get(i).getDate()))) {
                            dateList.add(sdf.parse(historyList.get(i).getDate()));
                        }

                    }

                    for (int i = 0; i < dateList.size(); i++) {

                        cal.setTime(dateList.get(i));
                        History item = new History();
                        item.setDate(sdf.format(dateList.get(i)));
                        historyListOrg.add(item);

                        mSeparatorsSet.add(historyListOrg.size() - 1);
                        for (int j = 0; j < historyList.size(); j++) {
                            Calendar messageTime = Calendar.getInstance();
                            messageTime.setTime(Objects.requireNonNull(sdf.parse(historyList.get(j).getDate())));
                            if (cal.getTime().compareTo(messageTime.getTime()) == 0) {
                                historyListOrg.add(historyList.get(j));
                            }
                        }
                    }

                    if (historyList.size() > 0) {
                        lvHistory.setVisibility(View.VISIBLE);
                        tvNoHistory.setVisibility(View.GONE);
                    } else {
                        lvHistory.setVisibility(View.GONE);
                        tvNoHistory.setVisibility(View.VISIBLE);
                    }
                    Log.i("historyListOrg size  ", "" + historyListOrg.size());

                    historyAdapter = new HistoryAd(this, historyListOrg,
                            mSeparatorsSet);
                    lvHistory.setAdapter(historyAdapter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActionNotification:
                onBackPressed();
                break;
            case R.id.fromDateBtn:
                fromPiker = new DatePickerDialog(this, dateset, year, month, day);
                fromPiker.show();
                break;
            case R.id.toDateBtn:
                toPiker = new DatePickerDialog(this, dateset, year, month, day);
                toPiker.show();
                break;
            case R.id.btnSearch:
                getHistory();
                break;
            default:
                break;
        }
    }


    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub
        AppLog.Log(TAG, error.toString());

    
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}