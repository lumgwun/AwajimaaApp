package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.AMERICAN_EXPRESS;
import static com.skylightapp.Bookings.BookingConstant.CASH;
import static com.skylightapp.Bookings.BookingConstant.CREDIT;
import static com.skylightapp.Bookings.BookingConstant.DELAY;
import static com.skylightapp.Bookings.BookingConstant.DINERS_CLUB;
import static com.skylightapp.Bookings.BookingConstant.DISCOVER;
import static com.skylightapp.Bookings.BookingConstant.DRIVER;
import static com.skylightapp.Bookings.BookingConstant.EXTRA_WALKER_STATUS;
import static com.skylightapp.Bookings.BookingConstant.INTENT_WALKER_STATUS;
import static com.skylightapp.Bookings.BookingConstant.IS_COMPLETED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALKER_ARRIVED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALKER_RATED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALKER_STARTED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALK_STARTED;
import static com.skylightapp.Bookings.BookingConstant.MAP_ZOOM;
import static com.skylightapp.Bookings.BookingConstant.MASTERCARD;
import static com.skylightapp.Bookings.BookingConstant.NO_REQUEST;
import static com.skylightapp.Bookings.BookingConstant.NO_TIME;
import static com.skylightapp.Bookings.BookingConstant.TAG;
import static com.skylightapp.Bookings.BookingConstant.TIME_SCHEDULE;
import static com.skylightapp.Bookings.BookingConstant.URL;
import static com.skylightapp.Bookings.BookingConstant.VISA;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.stats.WakeLock;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.HttpRequester;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.R;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TripFragment extends BaseFragment implements LocationHelper.OnLocationReceived {
    private GoogleMap map;
    private PolylineOptions lineOptions;
    private Route route;
    ArrayList<LatLng> points;

    private AppCompatTextView tvTime, tvDist, tvDriverName, tvRate, tvStatus,tvTaxiModel,tvTaxiNo,tvRateStar,tvPopupMsg, tvJobAccepted, tvDriverStarted,
            tvDriverArrvied, tvTripStarted, tvTripCompleted,
            tvEstimatedTime,tvDurationUnit;
    private Driver driver;
    private Marker myMarker, markerDriver;
    private AppCompatImageView ivDriverPhoto;
    private LocationHelper locHelper;
    private boolean isContinueStatusRequest;
    private boolean isContinueDriverRequest;
    private Timer timer, timerDriverLocation;

    private final int LOCATION_SCHEDULE = 5 * 1000;
    private String strDistance;
    private Polyline polyLine;
    private LatLng myLatLng;
    private Location myLocation;
    private boolean isTripStarted = false;

    private final int DRAW_TIME = 5 * 1000;
    private String lastTime;
    private String lastDistance;
    private WalkerStatusReceiver walkerReceiver;
    private boolean isAllLocationReceived = false;
    PowerManager.WakeLock wakeLock;
    private PopupWindow notificationWindow, driverStatusWindow;

    private AppCompatImageView ivJobAccepted, ivDriverStarted, ivDriverArrvied,
            ivTripStarted, ivTripCompleted;
    private boolean isNotificationArrievd = false;

    private View markerLayout;
    private ProgressBar pBar;
    private LinearLayout layoutCash;
    private LinearLayout layoutCard;
    private AppCompatTextView tvCash,tvCardNo;
    private PrefManager preference;
    private AppCompatImageView ivCard;
    private BroadcastReceiver mReceiver;
    private MapView mMapView;
    private Bundle mBundle;
    private View view;
    private AppCompatImageButton btnCancelTrip,ibApplyPromo;
    private LinearLayout layoutDestination;
    private AutoCompleteTextView etDestination;
    private PlacesAutoCAd adapterDestination;
    private Marker markerDestination;
    private Route routeDest;
    private ArrayList<LatLng> pointsDest;
    private PolylineOptions lineOptionsDest;
    private Polyline polyLineDest;
    private RelativeLayout layout;

    private Dialog dialog;
    private AppCompatEditText etPromoCode;
    private AppCompatImageView ivPromoResult;
    private AppCompatTextView tvPromoResult;
    private LinearLayout llErrorMsg;
    private AppCompatButton btnPromoSubmit, btnPromoSkip;
    private RequestQueue requestQueue;

    // private LatLng destLatlng;

    // private ImageView imgSelectedCash;
    // private ImageView imgSelectedCard;

    // private RatingBar ratingBarTrip;
    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
        PowerManager powerManager = (PowerManager) activity
                .getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        wakeLock.acquire();
        if (getArguments() != null) {
            driver = (Driver) getArguments().getParcelable(DRIVER);
        }
        points = new ArrayList<LatLng>();
        route = new Route();
        IntentFilter filter = new IntentFilter(INTENT_WALKER_STATUS);
        walkerReceiver = new WalkerStatusReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                walkerReceiver, filter);
        isAllLocationReceived = false;
        requestQueue = Volley.newRequestQueue(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle(getString(R.string.app_name));
        activity.tvTitle.setVisibility(View.GONE);
        activity.layoutDestination.setVisibility(View.VISIBLE);
        view = inflater.inflate(R.layout.frag_trip_new, container, false);
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
        }
        view.findViewById(R.id.btnCall).setOnClickListener(this);
        view.findViewById(R.id.btnAddDestination).setOnClickListener(this);
        view.findViewById(R.id.imgClearDst).setOnClickListener(this);
        etDestination = (AutoCompleteTextView) view.findViewById(R.id.etEnterSouce);
        layoutDestination = (LinearLayout) view.findViewById(R.id.layoutDestination);
        etDestination = activity.etSource;
        layoutDestination = activity.layoutDestination;
        preference = new PrefManager(activity);
        tvTime = view.findViewById(R.id.tvJobTime);
        tvDist =  view.findViewById(R.id.tvJobDistance);
        tvDriverName =  view.findViewById(R.id.tvDriverName);
        tvTaxiModel = view.findViewById(R.id.tvTaxiModel);
        tvTaxiNo =  view.findViewById(R.id.tvTaxiNo);
        tvRateStar =  view.findViewById(R.id.tvRateStar);
        layoutCash = (LinearLayout) view.findViewById(R.id.layoutCash);
        layoutCard = (LinearLayout) view.findViewById(R.id.layoutCard);
        tvCash = view.findViewById(R.id.tvCash);
        tvCardNo = view.findViewById(R.id.tvCardNo);
        ivCard =  view.findViewById(R.id.ivCard);
        ibApplyPromo = view.findViewById(R.id.ibApplyPromo);
        // imgSelectedCash = (ImageView)
        // view.findViewById(R.id.imgSelectedCash);
        // imgSelectedCard = (ImageView)
        // view.findViewById(R.id.imgSelectedCard);
        btnCancelTrip =  view.findViewById(R.id.btnCancelTrip);
        btnCancelTrip.setOnClickListener(this);
        layoutCash.setOnClickListener(this);
        layoutCard.setOnClickListener(this);
        ibApplyPromo.setOnClickListener(this);
        // tvDriverPhone = (MyFontTextView)
        // view.findViewById(R.id.tvDriverPhone);
        ivDriverPhoto =  view.findViewById(R.id.ivDriverPhoto);
        // tvRate = (TextView) view.findViewById(R.id.tvRate);
        // tvRate.setText(new DecimalFormat("0.0").format(driver.getRating()));
        // ratingBarTrip = (RatingBar) view.findViewById(R.id.ratingBarTrip);
        // ratingBarTrip.setRating((float) driver.getRating());

        // tvDriverPhone.setText(driver.getPhone());
        tvDriverName
                .setText(driver.getDriverFirstName() + " " + driver.getDriverLastName());
        tvTaxiModel.setText(driver.getDVehicleModel());
        tvTaxiNo.setText(driver.getDriverVehicle());
        tvRateStar.setText(driver.getRating() + "");

        tvStatus =  view.findViewById(R.id.tvStatus);
        mMapView = (MapView) view.findViewById(R.id.maptrip);
        mMapView.onCreate(mBundle);
        setUpMap();
        setDefaultCardDetails();
        if (preference.getPaymentMode() == CASH) {
            layoutCash.setSelected(true);
            layoutCard.setSelected(false);
            tvCash.setTextColor(getResources().getColor(R.color.white));
            // imgSelectedCash.setVisibility(View.VISIBLE);
        } else {
            layoutCash.setSelected(false);
            layoutCard.setSelected(true);
            tvCardNo.setTextColor(getResources().getColor(R.color.white));
            // imgSelectedCard.setVisibility(View.VISIBLE);
        }
        if (preference.getIsTripStarted()) {
            btnCancelTrip.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.Log("TripFragment", "Driver Photo : " + driver.getDriverPicture());
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.fileCache = true;
        imageOptions.memCache = true;
        imageOptions.fallback = R.drawable.ic_admin_user;
        new AQuery(activity).id(ivDriverPhoto).progress(R.id.pBar)
                .image(String.valueOf(driver.getDriverPicture()), imageOptions);
        locHelper = new LocationHelper(activity);
        locHelper.setLocationReceivedLister(getContext());
        adapterDestination = new PlacesAutoCAd(activity,
                R.layout.autocomplete_text);
        etDestination.setAdapter(adapterDestination);
        etDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                final String selectedDestPlace = adapterDestination.getItem(arg2);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        preference
                                .putClientDestination(getLocationFromAddress(selectedDestPlace));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // setMarkerOnRoad(destLatlng, destLatlng);
                                setDestination(preference
                                        .getClientDestination());
                                // setDestinationMarker(destLatlng);
                                // if (myMarker != null
                                // && markerDestination != null) {
                                // drawPath(myMarker.getPosition(), destLatlng);
                                // }
                            }
                        });
                    }
                }).start();
            }
        });

        locHelper.onStart();
        // PopUp Window
        LayoutInflater inflate = LayoutInflater.from(activity);
        layout = (RelativeLayout) inflate.inflate(
                R.layout.popup_not, null);
        tvPopupMsg =  layout.findViewById(R.id.tvPopupMsg);
        notificationWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setOnClickListener(this);
        activity.btnNotification.setOnClickListener(this);

        // Big PopUp Window
        RelativeLayout bigPopupLayout = (RelativeLayout) inflate.inflate(
                R.layout.popup_status_w, null);
        tvJobAccepted =  bigPopupLayout
                .findViewById(R.id.tvJobAccepted);
        tvDriverStarted =  bigPopupLayout
                .findViewById(R.id.tvDriverStarted);
        tvDriverArrvied =  bigPopupLayout
                .findViewById(R.id.tvDriverArrvied);
        tvTripStarted =  bigPopupLayout
                .findViewById(R.id.tvTripStarted);
        tvTripCompleted =  bigPopupLayout
                .findViewById(R.id.tvTripCompleted);
        ivJobAccepted =  bigPopupLayout
                .findViewById(R.id.ivJobAccepted);
        ivDriverStarted =  bigPopupLayout
                .findViewById(R.id.ivDriverStarted);
        ivDriverArrvied =  bigPopupLayout
                .findViewById(R.id.ivDriverArrvied);
        ivTripStarted =  bigPopupLayout
                .findViewById(R.id.ivTripStarted);
        ivTripCompleted =  bigPopupLayout
                .findViewById(R.id.ivTripCompleted);
        driverStatusWindow = new PopupWindow(bigPopupLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        driverStatusWindow.setBackgroundDrawable(new BitmapDrawable());
        // driverStatusWindow.setFocusable(false);
        // driverStatusWindow.setTouchable(true);
        driverStatusWindow.setOutsideTouchable(true);
        showNotificationPopUp(getString(R.string.job_accepted));
    }

    private LatLng getLocationFromAddress(final String place) {
        AppLog.Log("Address", "" + place);
        LatLng loc = null;
        Geocoder gCoder = new Geocoder(getActivity());
        try {
            final List<Address> list = gCoder.getFromLocationName(place, 1);
            if (list != null && list.size() > 0) {
                loc = new LatLng(list.get(0).getLatitude(), list.get(0)
                        .getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loc;
    }

    private void setMarkerOnRoad(LatLng source, LatLng destination) {
        String msg = null;
        if (source == null) {
            msg = "Unable to get source location, please try again";
        } else if (destination == null) {
            msg = "Unable to get destination location, please try again";
        }
        if (msg != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL,
                "http://maps.googleapis.com/maps/api/directions/json?origin="
                        + source.latitude + "," + source.longitude
                        + "&destination=" + destination.latitude + ","
                        + destination.longitude + "&sensor=false");

        new HttpRequester(activity, map, BookingConstant.ServiceCode.DRAW_PATH_ROAD,
                true, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCall:
                if (driver != null) {
                    String number = driver.getDriverPhone();
                    if (!TextUtils.isEmpty(number)) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + number));
                        startActivity(callIntent);
                    }
                }
                break;
            case R.id.rlPopupWindow:
                notificationWindow.dismiss();
                activity.setIcon(R.drawable.ic_notification_icon);
                break;
            case R.id.btnActionNotification:
                showDriverStatusNotification();
                break;
            case R.id.layoutCash:
                layoutCash.setSelected(true);
                layoutCard.setSelected(false);
                tvCash.setTextColor(getResources().getColor(R.color.white));
                tvCardNo.setTextColor(getResources().getColor(R.color.gray));
                preference.putPaymentMode(CASH);
                // imgSelectedCash.setVisibility(View.VISIBLE);
                // imgSelectedCard.setVisibility(View.GONE);
                setPaymentMode(CASH);
                break;
            case R.id.layoutCard:
                if (layoutCard.isSelected()) {
                    startActivity(new Intent(getActivity(),
                            ViewPaymentAct.class));
                } else {
                    layoutCash.setSelected(false);
                    layoutCard.setSelected(true);
                    tvCardNo.setTextColor(getResources().getColor(R.color.white));
                    tvCash.setTextColor(getResources().getColor(R.color.gray));
                    preference.putPaymentMode(CREDIT);
                    // imgSelectedCard.setVisibility(View.VISIBLE);
                    // imgSelectedCash.setVisibility(View.GONE);
                    setPaymentMode(CREDIT);
                }
                break;
            case R.id.btnCancelTrip:
                cancleRequest();
                break;
            case R.id.btnAddDestination:
                layoutDestination.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
                break;
            case R.id.imgClearDst:
                etDestination.setText("");
                break;
            case R.id.ibApplyPromo:
                showPromoDialog();
                break;
            case R.id.btnPromoSkip:
                if (dialog != null)
                    dialog.dismiss();
                break;
            case R.id.btnPromoSubmit:
                applyPromoCode();
                break;
            default:
                // if(driverStatusWindow.isShowing())
                // driverStatusWindow.dismiss();
                break;
        }
    }

    private void applyPromoCode() {
        if (!UtilsExtra.isNetworkAvailable(activity)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    activity);
            return;
        }
        UtilsExtra.showCustomProgressRequestDialog(activity,
                getString(R.string.text_apply_promo), true, null);
        HashMap<String, String> map = new HashMap<String, String>();

        map.put(URL, BookingConstant.ServiceType.APPLY_PROMO);
        map.put(BookingConstant.Params.TOKEN, activity.pHelper.getSessionToken());
        map.put(BookingConstant.Params.ID, String.valueOf(activity.pHelper.getUserId()));
        map.put(BookingConstant.Params.PROMO_CODE, etPromoCode.getText().toString()
                .trim());
        // new HttpRequester(activity, map, Const.ServiceCode.APPLY_PROMO,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map, BookingConstant.ServiceCode.APPLY_PROMO, this, this));
    }

    public void showDriverStatusNotification() {
        activity.setIcon(R.drawable.ic_notification_icon);
        if (driverStatusWindow.isShowing())
            driverStatusWindow.dismiss();
        else {
            if (notificationWindow.isShowing())
                notificationWindow.dismiss();
            else
                driverStatusWindow.showAsDropDown(activity.btnNotification);
        }
    }

    public void showNotificationPopUp(String text) {
        tvPopupMsg.setText(text);
        if (!driverStatusWindow.isShowing()) {
            if (!notificationWindow.isShowing()) {
                activity.setIcon(R.drawable.ic_notification_icon);
                notificationWindow.showAsDropDown(activity.btnNotification);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // if (activity.pHelper.getRequestTime() == Const.NO_TIME)
        // setRequestTime(SystemClock.e);
        mMapView.onResume();
        activity.btnNotification.setVisibility(View.VISIBLE);
        startUpdateDriverLocation();
        startCheckingStatusUpdate();
        registerCardReceiver();
    }

    @Override
    public void onPause() {
        stopUpdateDriverLoaction();
        stopCheckingStatusUpdate();

        super.onPause();
        mMapView.onPause();
    }

    private void setUpMap() {

        if (map == null) {

            // map.setOnMyLocationChangeListener(new
            // OnMyLocationChangeListener() {
            //
            // @Override
            // public void onMyLocationChange(Location arg0) {
            // // TODO Auto-generated method stub
            // drawTrip(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
            // }
            // });
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @SuppressLint("PotentialBehaviorOverride")
                @Override
                public View getInfoWindow(Marker marker) {
                    View v = activity.getLayoutInflater().inflate(
                            R.layout.info_layout, null);
                    ((AppCompatTextView) v).setText(marker.getTitle());
                    return v;
                }

                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker marker) {
                    return null;
                }
            });

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    return true;
                }
            });
        }
        initPreviousDrawPath();

    }

    private void setMarkers(LatLng latLang) {
        LatLng latLngDriver = new LatLng(driver.getLatitude(),
                driver.getLongitude());
        setMarker(latLngDriver);
        setDriverMarker(latLngDriver, driver.getBearing());
//		animateCameraToMarkerWithZoom(latLngDriver);
        boundLatLang();

        // showDirection(latLang, latLngDriver);
        // Location locDriver = new Location("");
        // locDriver.setLatitude(driver.getLatitude());
        // locDriver.setLongitude(driver.getLongitude());
        // strDistance = convertMilesFromMeters(loc
        // .distanceTo(locDriver));
        // animateCameraToMarker(latLang);
    }

    private void showPromoDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_promo);
        ivPromoResult =  dialog.findViewById(R.id.ivPromoResult);
        tvPromoResult =  dialog.findViewById(R.id.tvPromoResult);
        etPromoCode =  dialog.findViewById(R.id.etPromoCode);
        llErrorMsg = (LinearLayout) dialog.findViewById(R.id.llErrorMsg);
        btnPromoSubmit =  dialog.findViewById(R.id.btnPromoSubmit);
        btnPromoSubmit.setOnClickListener(this);
        btnPromoSkip =  dialog.findViewById(R.id.btnPromoSkip);
        btnPromoSkip.setOnClickListener(this);
        if (!TextUtils.isEmpty(activity.pHelper.getPromoCode())) {
            etPromoCode.setText(activity.pHelper.getPromoCode());
            btnPromoSkip.setText(getString(R.string.done));
            btnPromoSubmit.setEnabled(false);
            etPromoCode.setEnabled(false);
        }
        dialog.show();
    }

    private void showDirection(LatLng source, LatLng destination) {

        Map<String, String> hashMap = new HashMap<String, String>();

        final String url = "http://maps.googleapis.com/maps/api/directions/json?origin="
                + source.latitude
                + ","
                + source.longitude
                + "&destination="
                + destination.latitude
                + ","
                + destination.longitude
                + "&sensor=false";
        new HttpRequester(activity, hashMap, BookingConstant.ServiceCode.GET_ROUTE, true,
                this);
        UtilsExtra.showCustomProgressDialog(activity, "getting direction", false, null);
    }

    private void drawPath(LatLng source, LatLng destination) {
        if (source == null || destination == null) {
            return;
        }
        if (destination.latitude != 0) {
            setDestinationMarker(destination);
            boundLatLang();

            HashMap<String, String> map = new HashMap<String, String>();
            map.put(URL,
                    "http://maps.googleapis.com/maps/api/directions/json?origin="
                            + source.latitude + "," + source.longitude
                            + "&destination=" + destination.latitude + ","
                            + destination.longitude + "&sensor=false");
            // new HttpRequester(activity, map, Const.ServiceCode.DRAW_PATH,
            // true,
            // this);
            requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                    BookingConstant.ServiceCode.DRAW_PATH, this, this));
        }

    }

    private void boundLatLang() {

        try {
            if (myMarker != null && markerDriver != null
                    && markerDestination != null) {
                LatLngBounds.Builder bld = new LatLngBounds.Builder();
                bld.include(new LatLng(myMarker.getPosition().latitude,
                        myMarker.getPosition().longitude));
                bld.include(new LatLng(markerDriver.getPosition().latitude,
                        markerDriver.getPosition().longitude));
                bld.include(new LatLng(
                        markerDestination.getPosition().latitude,
                        markerDestination.getPosition().longitude));
                LatLngBounds latLngBounds = bld.build();

                map.animateCamera(CameraUpdateFactory.newLatLngBounds(
                        latLngBounds, 50));
            } else if (myMarker != null && markerDriver != null) {
                LatLngBounds.Builder bld = new LatLngBounds.Builder();
                bld.include(new LatLng(myMarker.getPosition().latitude,
                        myMarker.getPosition().longitude));
                bld.include(new LatLng(markerDriver.getPosition().latitude,
                        markerDriver.getPosition().longitude));
                LatLngBounds latLngBounds = bld.build();

                map.animateCamera(CameraUpdateFactory.newLatLngBounds(
                        latLngBounds, 100));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void onDestroyView() {
        wakeLock.release();
        SupportMapFragment f = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.maptrip);
        if (f != null) {
            try {
                getFragmentManager().beginTransaction().remove(f).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        map = null;
        activity.layoutDestination.setVisibility(View.GONE);
        super.onDestroyView();
    }

    @SuppressLint("NewApi")
    @Override
    public void onTaskCompleted(final String response, int serviceCode) {
        if (!this.isVisible())
            return;
        switch (serviceCode) {
            case BookingConstant.ServiceCode.GET_ROUTE:
                UtilsExtra.removeCustomProgressDialog();
                if (!TextUtils.isEmpty(response)) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            new ParseContent(activity).parseRoute(response, route);

                            final ArrayList<Step> step = route.getListStep();
                            points = new ArrayList<LatLng>();
                            lineOptions = new PolylineOptions();
                            lineOptions.geodesic(true);

                            for (int i = 0; i < step.size(); i++) {

                                List<LatLng> path = step.get(i).getListPoints();
                                // System.out.println("step =====> " + i + " and "
                                // + path.size());
                                points.addAll(path);
                            }
                            activity.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    if (polyLine != null)
                                        polyLine.remove();
                                    lineOptions.addAll(points);
                                    lineOptions.width(15);
                                    lineOptions.geodesic(true);
                                    lineOptions.color(getResources().getColor(
                                            R.color.green));
                                    polyLine = map.addPolyline(lineOptions);
                                    LatLngBounds.Builder bld = new LatLngBounds.Builder();
                                    bld.include(myMarker.getPosition());
                                    bld.include(markerDriver.getPosition());
                                    LatLngBounds latLngBounds = bld.build();
                                    map.moveCamera(CameraUpdateFactory
                                            .newLatLngBounds(latLngBounds, 50));
                                    // tvDist.setText(route.getDistanceText());
                                    // tvTime.setText(route.getDurationText());
                                    // tvDist.setText(0 + " KM");
                                    // tvTime.setText(0 + " MINS");
                                }
                            });
                        }
                    }).start();
                }
            case BookingConstant.ServiceCode.DRAW_PATH_ROAD:
                if (!TextUtils.isEmpty(response)) {
                    routeDest = new Route();
                    activity.pContent.parseRoute(response, routeDest);

                    final ArrayList<Step> step = routeDest.getListStep();
                    System.out.println("step size=====> " + step.size());
                    pointsDest = new ArrayList<LatLng>();
                    lineOptionsDest = new PolylineOptions();
                    lineOptionsDest.geodesic(true);

                    for (int i = 0; i < step.size(); i++) {
                        List<LatLng> path = step.get(i).getListPoints();
                        System.out.println("step =====> " + i + " and "
                                + path.size());
                        pointsDest.addAll(path);
                    }
                    if (pointsDest != null && pointsDest.size() > 0) {
                        drawPath(myMarker.getPosition(),
                                preference.getClientDestination());
                    }
                }
                break;
            case BookingConstant.ServiceCode.DRAW_PATH:
                if (!TextUtils.isEmpty(response)) {
                    routeDest = new Route();
                    activity.pContent.parseRoute(response, routeDest);

                    final ArrayList<Step> step = routeDest.getListStep();
                    System.out.println("step size=====> " + step.size());
                    pointsDest = new ArrayList<LatLng>();
                    lineOptionsDest = new PolylineOptions();
                    lineOptionsDest.geodesic(true);

                    for (int i = 0; i < step.size(); i++) {
                        List<LatLng> path = step.get(i).getListPoints();
                        System.out.println("step =====> " + i + " and "
                                + path.size());
                        pointsDest.addAll(path);
                    }
                    if (polyLineDest != null)
                        polyLineDest.remove();
                    lineOptionsDest.addAll(pointsDest);
                    lineOptionsDest.width(15);
                    lineOptionsDest.color(getResources().getColor(
                            R.color.red_brown_dark)); // #00008B rgb(0,0,139)
                    try {
                        if (lineOptionsDest != null && map != null) {
                            polyLineDest = map.addPolyline(lineOptionsDest);
                            boundLatLang();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case BookingConstant.ServiceCode.GET_REQUEST_LOCATION:
                if (activity.pContent.isSuccess(response)) {
                    DriverLocation driverLocation = activity.pContent
                            .getDriverLocation(response);
                    if (driverLocation == null || !this.isVisible()
                            || driverLocation.getLatLng() == null)
                        return;
                    setDriverMarker(driverLocation.getLatLng(),
                            driverLocation.getBearing());

                    drawTrip(driverLocation.getLatLng());
                    if (isTripStarted) {
                        long startTime = NO_TIME;
                        if (activity.pHelper.getRequestTime() == NO_TIME) {
                            startTime = System.currentTimeMillis();
                            activity.pHelper.putRequestTime(startTime);
                        } else {
                            startTime = activity.pHelper.getRequestTime();
                        }

                        double distance = Double.parseDouble(driverLocation
                                .getDistance());
                        // distance = distance / 1625;
                        // tvDist.setText(new DecimalFormat("0.00").format(distance)
                        // + " " + driverLocation.getUnit());
                        tvDist.setText(new DecimalFormat("0.00").format(distance)
                                + " " + driverLocation.getUnit());
                        long elapsedTime = System.currentTimeMillis() - startTime;
                        lastTime = elapsedTime / (1000 * 60) + " "
                                + getResources().getString(R.string.mins);
                        tvTime.setText(lastTime);
                        // tvTime.setText("0" + " MINS");
                        // tvDist.setText("0" + " KM");
                    } else
                        tvDist.setText(0 + " " + driverLocation.getUnit());
                }
                isContinueDriverRequest = true;
                // setMarker(latLng);
                break;
            case BookingConstant.ServiceCode.GET_REQUEST_STATUS:
                if (activity.pContent.isSuccess(response)) {
                    switch (activity.pContent.checkRequestStatus(response)) {
                        case IS_WALK_STARTED:
                            tvStatus.setText(Html
                                    .fromHtml(getString(R.string.driver_arrived)));
                            // showNotificationPopUp(getString(R.string.text_driver_arrvied));
                            changeNotificationPopUpUI(3);
                            isContinueStatusRequest = true;
                            isTripStarted = false;
                            break;
                        case IS_COMPLETED:
                            btnCancelTrip.setVisibility(View.GONE);
                            tvStatus.setText(Html
                                    .fromHtml(getString(R.string.trip_started)));
                            // showNotificationPopUp(getString(R.string.text_trip_started));
                            changeNotificationPopUpUI(4);
                            if (!isAllLocationReceived) {
                                isAllLocationReceived = true;
                                getPath(String.valueOf(activity.pHelper.getRequestId()));
                            }
                            isContinueStatusRequest = true;
                            isTripStarted = true;
                            preference.putIsTripStarted(true);
                            break;
                        case IS_WALKER_ARRIVED:
                            tvStatus.setText(Html
                                    .fromHtml(getString(R.string.driver_started)));
                            // showNotificationPopUp(getString(R.string.text_driver_started));
                            changeNotificationPopUpUI(2);
                            isContinueStatusRequest = true;
                            break;
                        case IS_WALKER_STARTED:
                            tvStatus.setText(Html
                                    .fromHtml(getString(R.string.job_accepted)));
                            // showNotificationPopUp(getString(R.string.text_job_accepted));
                            changeNotificationPopUpUI(1);
                            isContinueStatusRequest = true;
                            break;
                        case IS_WALKER_RATED:
                            stopCheckingStatusUpdate();
                            isTripStarted = false;
                            if (notificationWindow.isShowing())
                                notificationWindow.dismiss();
                            if (driverStatusWindow.isShowing())
                                driverStatusWindow.dismiss();
                            driver = activity.pContent.getDriverDetail(response);
                            driver.setLastDistance(lastDistance);
                            driver.setLastTime(lastTime);
                            activity.gotoRateFragment(driver);
                            break;

                        default:

                            break;
                    }

                } else {
                    isContinueStatusRequest = true;
                }
                break;
            case BookingConstant.ServiceCode.GET_PATH:
                UtilsExtra.removeCustomProgressDialog();
                activity.pContent.parsePathRequest(response, points);
                initPreviousDrawPath();
                AppLog.Log(TAG, "Path====>" + response + "");
                break;
            case BookingConstant.ServiceCode.GET_DURATION:
                pBar.setVisibility(View.GONE);
                AppLog.Log("UberTripFragment", "Duration Response : " + response);

                String[] durationArr = activity.pContent
                        .parseNearestDriverDurationString(response).split(" ");
                tvEstimatedTime.setText(durationArr[0]);
                tvDurationUnit.setText(durationArr[1]);
                myMarker.setIcon(BitmapDescriptorFactory.fromBitmap(UtilsExtra
                        .createDrawableFromView(getActivity(), markerLayout)));

                break;
            case BookingConstant.ServiceCode.PAYMENT_TYPE:
                UtilsExtra.removeCustomProgressDialog();
                AppLog.Log("UberTripFragment", "Payment type reponse : " + response);
                if (!activity.pContent.isSuccess(response)) {
                    UtilsExtra.showToast("Sorry, Cannot change payment mode",
                            getActivity());
                }
                break;
            case BookingConstant.ServiceCode.CANCEL_REQUEST:
                if (activity.pContent.isSuccess(response)) {

                }
                activity.pHelper.clearRequestData();
                stopCheckingStatusUpdate();
                stopUpdateDriverLoaction();
                UtilsExtra.removeCustomProgressDialog();
                activity.gotoMapFragment();
                break;
            case BookingConstant.ServiceCode.SET_DESTINATION:
                UtilsExtra.removeCustomProgressDialog();
                AppLog.Log("Trip", "Destination Response : " + response);
                if (activity.pContent.isSuccess(response)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // setMarkerOnRoad(destLatlng, destLatlng);
                            drawPath(myMarker.getPosition(),
                                    preference.getClientDestination());
                        }
                    });
                }
                break;
            case BookingConstant.ServiceCode.APPLY_PROMO:
                llErrorMsg.setVisibility(View.VISIBLE);
                UtilsExtra.removeCustomProgressDialog();
                if (activity.pContent.isSuccess(response)) {
                    activity.pHelper.putPromoCode(etPromoCode.getText().toString());
                    tvPromoResult.setText(activity.pContent.getMessage(response));
                    ivPromoResult.setSelected(true);
                    tvPromoResult.setSelected(true);
                    btnPromoSkip.setText(getString(R.string.done));
                    btnPromoSubmit.setEnabled(false);
                    etPromoCode.setEnabled(false);

                } else {
                    tvPromoResult.setText(activity.pContent.getMessage(response));
                    ivPromoResult.setSelected(false);
                    tvPromoResult.setSelected(false);
                }
                break;
        }
    }

    private void changeNotificationPopUpUI(int i) {
        switch (i) {
            case 1:
                ivJobAccepted.setImageResource(R.drawable.ic_check_circle_black_24dp);
                tvJobAccepted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                break;
            case 2:
                ivJobAccepted.setImageResource(R.drawable.ic_check_circle_black_24dp);
                tvJobAccepted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                ivDriverStarted.setImageResource(R.drawable.ic_check_circle_black_24dp);
                tvDriverStarted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                break;
            case 3:
                ivJobAccepted.setImageResource(R.drawable.check_fg);
                tvJobAccepted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                ivDriverStarted.setImageResource(R.drawable.check_fg);
                tvDriverStarted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                ivDriverArrvied.setImageResource(R.drawable.check_fg);
                tvDriverArrvied.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                break;
            case 4:
                ivJobAccepted.setImageResource(R.drawable.check_fg);
                tvJobAccepted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                ivDriverStarted.setImageResource(R.drawable.check_fg);
                tvDriverStarted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                ivDriverArrvied.setImageResource(R.drawable.check_fg);
                tvDriverArrvied.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                ivTripStarted.setImageResource(R.drawable.check_fg);
                tvTripStarted.setTextColor(getResources().getColor(
                        R.color.red_brown_dark));
                break;

            default:
                break;
        }
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    class TrackLocation extends TimerTask {
        public void run() {
            if (isContinueDriverRequest) {
                isContinueDriverRequest = false;
                getDriverLocation();
            }
        }
    }

    private void getDriverLocation() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL,
                BookingConstant.ServiceType.GET_REQUEST_LOCATION + BookingConstant.Params.ID + "="
                        + new PrefManager(activity).getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "="
                        + new PrefManager(activity).getSessionToken()
                        + "&" + BookingConstant.Params.REQUEST_ID + "="
                        + new PrefManager(activity).getRequestId());
        AppLog.Log("TAG",
                BookingConstant.ServiceType.GET_REQUEST_LOCATION + BookingConstant.Params.ID + "="
                        + new PrefManager(activity).getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "="
                        + new PrefManager(activity).getSessionToken()
                        + "&" + BookingConstant.Params.REQUEST_ID + "="
                        + new PrefManager(activity).getRequestId());
        // new HttpRequester(activity, map,
        // Const.ServiceCode.GET_REQUEST_LOCATION, true, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_REQUEST_LOCATION, this, this));

    }

    private void setMarker(LatLng latLng) {
        if (latLng != null) {
            if (map != null && this.isVisible()) {
                if (myMarker == null) {
                    markerLayout = ((LayoutInflater) getActivity()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.custom_marker, null);
                    tvEstimatedTime = markerLayout
                            .findViewById(R.id.num_txt);
                    tvEstimatedTime = markerLayout
                            .findViewById(R.id.num_txt);
                    tvDurationUnit =  markerLayout
                            .findViewById(R.id.tvDurationUnit);
                    pBar = (ProgressBar) markerLayout.findViewById(R.id.pBar);

                    MarkerOptions opt = new MarkerOptions();
                    opt.position(latLng);
                    opt.icon(BitmapDescriptorFactory.fromBitmap(UtilsExtra
                            .createDrawableFromView(getActivity(), markerLayout)));
                    opt.title(getString(R.string.my_location));
                    myMarker = map.addMarker(opt);
                    // animateCameraToMarkerWithZoom(latLng);
                } else {
                    myMarker.setPosition(latLng);
                }
                drawPath(myMarker.getPosition(),
                        preference.getClientDestination());
            }
        }
    }

    private void setDriverMarker(LatLng latLng, double bearing) {
        if (latLng != null) {
            if (map != null && this.isVisible()) {
                if (markerDriver == null) {
                    MarkerOptions opt = new MarkerOptions();
                    opt.flat(true);
                    opt.anchor(0.5f, 0.5f);
                    opt.position(latLng);
                    opt.icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.location__start));
                    opt.title(getString(R.string.drive_loc));
                    markerDriver = map.addMarker(opt);
                } else {
                    Location driverLocation = new Location("");
                    driverLocation.setLatitude(latLng.latitude);
                    driverLocation.setLongitude(latLng.longitude);
                    driverLocation.setBearing((float) bearing);
                    animateMarker(markerDriver, latLng, driverLocation, false);
                    // if (isCameraZoom) {
                    // animateCameraToMarker(latLng);
                    // }
                }
                if (myMarker != null && myMarker.getPosition() != null)
                    getDirectionsUrl(latLng, myMarker.getPosition());
            }
        }

    }

    private void setDestinationMarker(LatLng latLng) {
        if (latLng != null) {
            if (map != null && this.isVisible()) {
                if (markerDestination == null) {
                    MarkerOptions opt = new MarkerOptions();
                    opt.position(latLng);
                    opt.icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.location__end));
                    opt.title(getString(R.string.dest));
                    markerDestination = map.addMarker(opt);
                } else {
                    markerDestination.setPosition(latLng);
                }
            }
        }
    }

    private void startUpdateDriverLocation() {
        isContinueDriverRequest = true;
        timerDriverLocation = new Timer();
        timerDriverLocation.scheduleAtFixedRate(new TrackLocation(), 0, LOCATION_SCHEDULE);
    }

    private void stopUpdateDriverLoaction() {
        isContinueDriverRequest = false;
        if (timerDriverLocation != null) {
            timerDriverLocation.cancel();
            timerDriverLocation = null;
        }
    }

    private void animateCameraToMarkerWithZoom(LatLng latLng) {
        CameraUpdate cameraUpdate = null;
        cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(latLng, MAP_ZOOM);
        map.animateCamera(cameraUpdate);
    }

    private void animateCameraToMarker(LatLng latLng) {
        CameraUpdate cameraUpdate = null;
        cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        map.animateCamera(cameraUpdate);
    }

    private String convertKmFromMeters(float disatanceInMeters) {
        return new DecimalFormat("0.0").format(0.001f * disatanceInMeters);
    }

    private void startCheckingStatusUpdate() {
        stopCheckingStatusUpdate();
        if (activity.pHelper.getRequestId() != NO_REQUEST) {
            isContinueStatusRequest = true;
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerRequestStatus(), DELAY,
                    TIME_SCHEDULE);
        }
    }

    private void stopCheckingStatusUpdate() {
        isContinueStatusRequest = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class TimerRequestStatus extends TimerTask {
        @Override
        public void run() {
            if (isContinueStatusRequest) {
                isContinueStatusRequest = false;
                getRequestStatus(String
                        .valueOf(activity.pHelper.getRequestId()));
            }
        }
    }

    private void getRequestStatus(String requestId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL,
                BookingConstant.ServiceType.GET_REQUEST_STATUS + BookingConstant.Params.ID + "="
                        + new PrefManager(activity).getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "="
                        + new PrefManager(activity).getSessionToken()
                        + "&" + BookingConstant.Params.REQUEST_ID + "=" + requestId);

        // new HttpRequester(activity, map,
        // Const.ServiceCode.GET_REQUEST_STATUS,
        // true, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_REQUEST_STATUS, this, this));
    }

    private void getPath(String requestId) {
        UtilsExtra.showCustomProgressDialog(activity,
                getString(R.string.loading1), false, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL,
                BookingConstant.ServiceType.GET_PATH + BookingConstant.Params.ID + "="
                        + new PrefManager(activity).getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "="
                        + new PrefManager(activity).getSessionToken()
                        + "&" + BookingConstant.Params.REQUEST_ID + "=" + requestId);
        // new HttpRequester(activity, map, Const.ServiceCode.GET_PATH, true,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_PATH, this, this));
    }

    private void setRequestTime(long time) {
        activity.pHelper.putRequestTime(time);
    }

    private void drawTrip(LatLng latlng) {
        if (map != null && this.isVisible()) {
            points.add(latlng);
            lineOptions = new PolylineOptions();
            lineOptions.addAll(points);
            lineOptions.width(15);
            lineOptions.geodesic(true);
            lineOptions.color(getResources().getColor(R.color.green));
            map.addPolyline(lineOptions);
        }
    }

    class WalkerStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String response = intent.getStringExtra(EXTRA_WALKER_STATUS);
            AppLog.Log("Response ---- Trip", response);
            if (TextUtils.isEmpty(response))
                return;
            stopCheckingStatusUpdate();

            if (activity.pContent.isSuccess(response)) {
                switch (activity.pContent.checkRequestStatus(response)) {
                    case IS_WALK_STARTED:

                        tvStatus.setText(Html
                                .fromHtml(getString(R.string.driver_arrived)));
                        showNotificationPopUp(getString(R.string.driver_arrived));
                        changeNotificationPopUpUI(3);
                        isContinueStatusRequest = true;
                        isTripStarted = false;
                        break;
                    case IS_COMPLETED:
                        tvStatus.setText(Html
                                .fromHtml(getString(R.string.trip_started)));
                        showNotificationPopUp(getString(R.string.trip_started));
                        changeNotificationPopUpUI(4);
                        if (!isAllLocationReceived) {
                            isAllLocationReceived = true;
                            getPath(String.valueOf(activity.pHelper.getRequestId()));
                        }
                        isContinueStatusRequest = true;
                        isTripStarted = true;
                        break;
                    case IS_WALKER_ARRIVED:
                        tvStatus.setText(Html
                                .fromHtml(getString(R.string.driver_started)));
                        showNotificationPopUp(getString(R.string.driver_started));
                        changeNotificationPopUpUI(2);
                        isContinueStatusRequest = true;
                        break;
                    case IS_WALKER_STARTED:
                        tvStatus.setText(Html
                                .fromHtml(getString(R.string.job_accepted)));
                        showNotificationPopUp(getString(R.string.job_accepted));
                        changeNotificationPopUpUI(1);
                        isContinueStatusRequest = true;
                        break;
                    case IS_WALKER_RATED:
                        stopCheckingStatusUpdate();
                        isTripStarted = false;
                        if (notificationWindow.isShowing())
                            notificationWindow.dismiss();
                        if (driverStatusWindow.isShowing())
                            driverStatusWindow.dismiss();
                        driver = activity.pContent.getDriverDetail(response);
                        driver.setLastDistance(lastDistance);
                        driver.setLastTime(lastTime);
                        activity.gotoRateFragment(driver);
                        break;
                    default:
                        break;
                }
            } else {
                isContinueStatusRequest = true;
            }
            startCheckingStatusUpdate();
        }
    }

    private void initPreviousDrawPath() {
        lineOptions = new PolylineOptions();
        lineOptions.addAll(points);
        lineOptions.width(15);
        lineOptions.geodesic(true);
        lineOptions.color(getResources().getColor(R.color.green));
        if (map != null && this.isVisible())
            map.addPolyline(lineOptions);
        points.clear();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                walkerReceiver);
        if (notificationWindow.isShowing())
            notificationWindow.dismiss();
        if (driverStatusWindow.isShowing())
            driverStatusWindow.dismiss();
        ubregisterCardReceiver();
    }

    // perfect function...
    private void animateMarker(final Marker marker, final LatLng toPosition,
                               final Location toLocation, final boolean hideMarker) {
        if (map == null || !this.isVisible()) {
            return;
        }


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final double startRotation = marker.getRotation();
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                float rotation = (float) (t * toLocation.getBearing() + (1 - t)
                        * startRotation);
                if (rotation != 0) {
                    marker.setRotation(rotation);
                }
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    private void getDirectionsUrl(LatLng origin, LatLng destination) {
        if (!UtilsExtra.isNetworkAvailable(activity)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    activity);
            return;
        } else if (origin == null) {
            return;
        }
        String str_origin = "origin=" + origin.latitude + ","
                + origin.longitude;
        String str_dest = "destination=" + destination.latitude + ","
                + destination.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + parameters;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, url);
        // new HttpRequester(activity, map, Const.ServiceCode.GET_DURATION,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.GET_DURATION, this, this));
    }

    private void setPaymentMode(int type) {
        if (!UtilsExtra.isNetworkAvailable(activity)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    activity);
            return;
        }
        UtilsExtra.showCustomProgressDialog(activity,
                getString(R.string.changing_payment_mode), true, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.PAYMENT_TYPE);
        map.put(BookingConstant.Params.ID, String.valueOf(activity.pHelper.getUserId()));
        map.put(BookingConstant.Params.TOKEN,
                String.valueOf(activity.pHelper.getSessionToken()));
        map.put(BookingConstant.Params.REQUEST_ID,
                String.valueOf(activity.pHelper.getRequestId()));
        map.put(BookingConstant.Params.CASH_OR_CARD, String.valueOf(type));

        // new HttpRequester(activity, map, Const.ServiceCode.PAYMENT_TYPE,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.PAYMENT_TYPE, this, this));
    }

    private void setDefaultCardDetails() {
        if (preference.getDefaultCard() == 0) {
            layoutCard.setVisibility(View.INVISIBLE);
        } else {
            layoutCard.setVisibility(View.VISIBLE);
            tvCardNo.setText("*****" + preference.getDefaultCardNo());
            String type = preference.getDefaultCardType();
            if (type.equalsIgnoreCase(VISA)) {
                ivCard.setImageResource(R.drawable.visa_logo_new);
            } else if (type.equalsIgnoreCase(MASTERCARD)) {
                ivCard.setImageResource(R.drawable.master_card_logo_svg);
            } else if (type.equalsIgnoreCase(AMERICAN_EXPRESS)) {
                //ivCard.setImageResource(R.drawable.ub__creditcard_amex);
            } else if (type.equalsIgnoreCase(DISCOVER)) {
                //ivCard.setImageResource(R.drawable.ub__creditcard_discover);
            } else if (type.equalsIgnoreCase(DINERS_CLUB)) {
                //ivCard.setImageResource(R.drawable.ub__creditcard_discover);
            } else {
                //ivCard.setImageResource(R.drawable.ub__nav_payment);
            }
        }
    }

    private void registerCardReceiver() {
        IntentFilter intentFilter = new IntentFilter("card_change_receiver");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setDefaultCardDetails();
                AppLog.Log("TripFragment", "Card change Receiver");
            }
        };
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    private void ubregisterCardReceiver() {
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    private void cancleRequest() {
        if (!UtilsExtra.isNetworkAvailable(activity)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    activity);
            return;
        }
        AppLog.Log("UberTripFragment",
                "Request ID : " + activity.pHelper.getRequestId());
        UtilsExtra.showCustomProgressDialog(activity,
                getString(R.string.canceling_request), true, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.CANCEL_REQUEST);
        map.put(BookingConstant.Params.ID, String.valueOf(activity.pHelper.getUserId()));
        map.put(BookingConstant.Params.TOKEN,
                String.valueOf(activity.pHelper.getSessionToken()));
        map.put(BookingConstant.Params.REQUEST_ID,
                String.valueOf(activity.pHelper.getRequestId()));
        // new HttpRequester(activity, map, Const.ServiceCode.CANCEL_REQUEST,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.CANCEL_REQUEST, this, this));
    }

    private void setDestination(LatLng destination) {
        if (!UtilsExtra.isNetworkAvailable(activity)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    activity);
            return;
        }
        if (destination != null) {
            UtilsExtra.showCustomProgressDialog(activity,
                    getString(R.string.adding_dest), true, null);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(URL, BookingConstant.ServiceType.SET_DESTINATION);
            map.put(BookingConstant.Params.ID,
                    String.valueOf(activity.pHelper.getUserId()));
            map.put(BookingConstant.Params.TOKEN,
                    String.valueOf(activity.pHelper.getSessionToken()));
            map.put(BookingConstant.Params.REQUEST_ID,
                    String.valueOf(activity.pHelper.getRequestId()));
            map.put(BookingConstant.Params.DEST_LAT, String.valueOf(destination.latitude));
            map.put(BookingConstant.Params.DEST_LNG,
                    String.valueOf(destination.longitude));
            // new HttpRequester(activity, map,
            // Const.ServiceCode.SET_DESTINATION,
            // this);
            requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                    BookingConstant.ServiceCode.SET_DESTINATION, this, this));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub
        AppLog.Log(TAG, error.getMessage());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.automated.taxinow.utils.LocationHelper.OnLocationReceived#
     * onLocationReceived(com.google.android.gms.maps.model.LatLng)
     */
    @Override
    public void onLocationReceived(LatLng latlong) {
        // TODO Auto-generated method stub
        if (isTripStarted && isAllLocationReceived) {
            // drawTrip(latlong);
            myLocation.setLatitude(latlong.latitude);
            myLocation.setLongitude(latlong.longitude);
            if (!isTripStarted)
                setMarker(latlong);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.automated.taxinow.utils.LocationHelper.OnLocationReceived#
     * onLocationReceived(android.location.Location)
     */
    @Override
    public void onLocationReceived(Location location) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.automated.taxinow.utils.LocationHelper.OnLocationReceived#onConntected
     * (android.os.Bundle)
     */
    @Override
    public void onConntected(Bundle bundle) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.automated.taxinow.utils.LocationHelper.OnLocationReceived#onConntected
     * (android.location.Location)
     */
    @Override
    public void onConntected(Location location) {
        // TODO Auto-generated method stub
        if (location != null) {

            myLocation = location;
            myLatLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if (TripFragment.this.isVisible())
                setMarkers(myLatLng);
        }
    }

}
