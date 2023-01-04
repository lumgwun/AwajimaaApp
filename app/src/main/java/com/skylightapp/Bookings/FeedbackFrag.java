package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.DRIVER;
import static com.skylightapp.Bookings.BookingConstant.URL;
import static com.skylightapp.Bookings.DriverApp.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.androidquery.AQuery;
import com.android.volley.toolbox.Volley;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.R;

import java.util.HashMap;

public class FeedbackFrag extends BaseFragment {
    private AppCompatEditText etComment;
    private AppCompatRatingBar rtBar;
    private AppCompatButton btnSubmit;
    private AppCompatImageView ivDriverImage;
    private Driver driver;
    private AppCompatTextView tvDistance, tvTime, tvClientName;
    private RequestQueue requestQueue;

    // private TextView tvFeedbackAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            driver = (Driver) getArguments().getParcelable(DRIVER);
        }
        requestQueue = Volley.newRequestQueue(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle(getString(R.string.feedback));
        activity.tvTitle.setVisibility(View.VISIBLE);
        View view = inflater.inflate(R.layout.feedback, container, false);
        tvClientName = view.findViewById(R.id.tvClientName);
        etComment =  view.findViewById(R.id.etComment);
        rtBar = view.findViewById(R.id.ratingBar);
        btnSubmit =  view.findViewById(R.id.btnSubmit);
        ivDriverImage =  view.findViewById(R.id.ivDriverImage);
        tvDistance =  view.findViewById(R.id.tvDistance);
        tvTime =  view.findViewById(R.id.tvTime);
        // tvFeedbackAmount = (TextView)
        // view.findViewById(R.id.tvFeedbackAmount);
        // tvDistance.setText(driver.getLastDistance());
        if(driver !=null){
            tvDistance.setText(driver.getBill().getDistance() + " "
                    + driver.getBill().getUnit());
            tvTime.setText((int) (Double.parseDouble(driver.getBill().getTime()))
                    + " " + getString(R.string.mins));

        }

        // tvFeedbackAmount.setText(getString(R.string.text_price_unit)
        // + Double.parseDouble(driver.getBill().getTotal()));
        // tvTime.setText(driver.getLastTime());
        activity.btnNotification.setVisibility(View.GONE);
        btnSubmit.setOnClickListener(this);

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if (driver != null) {
            /*new AQuery(activity).id(ivDriverImage).progress(R.id.pBar)
                    .image(driver.getDriverPicture());*/
            tvClientName.setText(driver.getDriverFirstName() + " "
                    + driver.getDriverLastName());
            activity.showBillDialog(driver.getBill().getTimeCost(), driver
                            .getBill().getTotal(), driver.getBill().getDistanceCost(),
                    driver.getBill().getBasePrice(),
                    driver.getBill().getTime(), driver.getBill().getDistance(),
                    driver.getBill().getPromoBouns(), driver.getBill()
                            .getReferralBouns(),
                    getString(R.string.confirm));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (isValidate()) {
                    rating();
                } else
                    UtilsExtra.showToast(
                            activity.getString(R.string.error),
                            activity);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean isValidate() {
        if (rtBar.getRating() <= 0)
            return false;
        return true;
    }

    private void rating() {
        UtilsExtra.showCustomProgressDialog(activity,
                getString(R.string.rating), false, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.RATING);
        map.put(BookingConstant.Params.TOKEN, activity.pHelper.getSessionToken());
        map.put(BookingConstant.Params.ID, String.valueOf(new PrefManager(activity).getUserId()));
        map.put(BookingConstant.Params.COMMENT, etComment.getText().toString());
        map.put(BookingConstant.Params.RATING, String.valueOf(((int) rtBar.getRating())));
        map.put(BookingConstant.Params.REQUEST_ID,
                String.valueOf(activity.pHelper.getRequestId()));
        // new HttpRequester(activity, map, Const.ServiceCode.RATING, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.RATING, this, this));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.uberorg.fragments.UberBaseFragment#onTaskCompleted(java.lang.String,
     * int)
     */
    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case BookingConstant.ServiceCode.RATING:
                UtilsExtra.removeCustomProgressDialog();
                if (activity.pContent.isSuccess(response)) {
                    activity.pHelper.clearRequestData();
                    UtilsExtra.showToast(
                            getString(R.string.feedback_completed), activity);
                    activity.gotoMapFragment();
                }
                break;
        }
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        AppLog.Log(TAG, error.getMessage());
    }
}
