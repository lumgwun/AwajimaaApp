package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.DEVICE_TYPE_ANDROID;
import static com.skylightapp.Bookings.BookingConstant.MANUAL;
import static com.skylightapp.Bookings.BookingConstant.Params.DEVICE_TYPE;
import static com.skylightapp.Bookings.BookingConstant.URL;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.androidquery.callback.ImageOptions;
import com.skylightapp.Classes.AsyncListener;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.R;

import java.util.HashMap;

abstract public class BaseFragment extends Fragment implements
        View.OnClickListener, AsyncListener, Response.ErrorListener {
    BookingDrawerAct activity;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BookingDrawerAct) getActivity();
        requestQueue = Volley.newRequestQueue(activity);
    }

    protected abstract boolean isValidate();

    @Override
    public void onTaskCompleted(final String response, int serviceCode) {

    }

    protected ImageOptions getAqueryOption() {
        ImageOptions options = new ImageOptions();
        options.targetWidth = 200;
        options.memCache = true;
        options.fallback = R.drawable.ic_admin_user;
        options.fileCache = true;
        return options;
    }

    protected void login() {
        if (!UtilsExtra.isNetworkAvailable(activity)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    activity);
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.LOGIN);
        map.put(BookingConstant.Params.EMAIL, activity.pHelper.getEmail());
        map.put(BookingConstant.Params.PASSWORD, activity.pHelper.getPassword());
        map.put(DEVICE_TYPE, DEVICE_TYPE_ANDROID);
        map.put(BookingConstant.Params.DEVICE_TOKEN, activity.pHelper.getDeviceToken());
        map.put(BookingConstant.Params.LOGIN_BY, MANUAL);
        // new HttpRequester(activity, map, Const.ServiceCode.LOGIN, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.LOGIN, this, this));

    }
}
