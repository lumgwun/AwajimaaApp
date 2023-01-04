package com.skylightapp.Classes;

import static com.skylightapp.Bookings.BookingConstant.IS_REQEUST_CREATED;
import static com.skylightapp.Bookings.BookingConstant.MANUAL;
import static com.skylightapp.Bookings.BookingConstant.NO_REQUEST;
import static com.skylightapp.Classes.AwajimaLog.TAG;

import android.app.Activity;
import android.net.Uri;

import com.blongho.country_data.Country;
import com.blongho.country_data.World;
import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.AppPages;
import com.skylightapp.Bookings.BookingConstant;
import com.skylightapp.Bookings.Driver;
import com.skylightapp.Bookings.DriverLocation;
import com.skylightapp.Bookings.History;
import com.skylightapp.Bookings.PTour;
import com.skylightapp.Bookings.Route;
import com.skylightapp.Bookings.Step;
import com.skylightapp.Bookings.Tour;
import com.skylightapp.Bookings.VehicalType;
import com.skylightapp.Database.ProfDAO;

import com.skylightapp.MapAndLoc.PolyLineUtils;
import com.skylightapp.R;
import com.skylightapp.Transactions.PayStackCard;

import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ParseContent {
    private Activity activity;
    private PrefManager prefManager;
    private final String KEY_SUCCESS = "success";
    private final String KEY_ERROR = "error";
    private final String NAME = "name";
    private final String AGE = "age";
    private final String TYPE = "type";
    private final String MIN_FARE = "min_fare";
    private final String MAX_SIZE = "max_size";
    private final String NOTES = "notes";
    private final String IMAGE_URL = "image_url";
    private final String THINGS_ID = "thing_id";
    private final String KEY_ERROR_CODE = "error_code";
    private final String KEY_WALKER = "walker";
    private final String BILL = "bill";
    private final String KEY_BILL = "bill";

    private final String IS_WALKER_STARTED = "is_walker_started";
    private final String IS_WALKER_ARRIVED = "is_walker_arrived";
    private final String IS_WALK_STARTED = "is_walk_started";
    private final String IS_WALKER_RATED = "is_walker_rated";
    private final String IS_COMPLETED = "is_completed";
    private final String STATUS = "status";
    private final String CONFIRMED_WALKER = "confirmed_walker";

    private final String TIME = "time";
    private final String BASE_PRICE = "base_price";
    private final String BASE_DISTANCE = "base_distance";
    private final String DISTANCE_COST = "distance_cost";
    private final String DISTANCE = "distance";
    private final String UNIT = "unit";
    private final String TIME_COST = "time_cost";
    private final String TOTAL = "total";
    private final String IS_PAID = "is_paid";
    private final String START_TIME = "start_time";

    public static final String DATE = "date";

    private final String TYPES = "types";

    private final String ID = "id";

    private final String ICON = "icon";
    private final String IS_DEFAULT = "is_default";
    private final String PRICE_PER_UNIT_TIME = "price_per_unit_time";
    private final String PRICE_PER_UNIT_DISTANCE = "price_per_unit_distance";

    private final String STRIPE_TOKEN = "stripe_token";
    private final String LAST_FOUR = "last_four";
    private final String CREATED_AT = "created_at";
    private final String UPDATED_AT = "updated_at";
    private final String OWNER_ID = "owner_id";
    private final String CARD_TYPE = "card_type";

    private final String PAYMENTS = "payments";

    private final String REQUESTS = "requests";
    private final String WALKER = "walker";
    private final String CUSTOMER_ID = "customer_id";

    private final String REFERRAL_CODE = "referral_code";
    private final String TOTAL_REFERRALS = "total_referrals";
    private final String AMOUNT_EARNED = "total_referrals";
    private final String AMOUNT_SPENT = "total_referrals";
    private final String BALANCE_AMOUNT = "balance_amount";
    private final String WALKERS = "walker_list";
    private final String PROMO_CODE = "promo_code";
    private final String PROMO_BONUS = "promo_bonus";
    private final String REFERRAL_BONUS = "referral_bonus";
    PrefManager preferenceHelper;

    public ParseContent(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        prefManager = new PrefManager(activity);
    }

    public Route parseRoute(String response, Route routeBean) {

        try {
            Step stepBean;
            JSONObject jObject = new JSONObject(response);
            JSONArray jArray = jObject.getJSONArray("routes");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject innerjObject = jArray.getJSONObject(i);
                if (innerjObject != null) {
                    JSONArray innerJarry = innerjObject.getJSONArray("legs");
                    for (int j = 0; j < innerJarry.length(); j++) {

                        JSONObject jObjectLegs = innerJarry.getJSONObject(j);
                        routeBean.setDistanceText(jObjectLegs.getJSONObject(
                                "distance").getString("text"));
                        routeBean.setDistanceValue(jObjectLegs.getJSONObject(
                                "distance").getInt("value"));

                        routeBean.setDurationText(jObjectLegs.getJSONObject(
                                "duration").getString("text"));
                        routeBean.setDurationValue(jObjectLegs.getJSONObject(
                                "duration").getInt("value"));

                        routeBean.setStartAddress(jObjectLegs
                                .getString("start_address"));
                        routeBean.setEndAddress(jObjectLegs
                                .getString("end_address"));

                        routeBean.setStartLat(jObjectLegs.getJSONObject(
                                "start_location").getDouble("lat"));
                        routeBean.setStartLon(jObjectLegs.getJSONObject(
                                "start_location").getDouble("lng"));

                        routeBean.setEndLat(jObjectLegs.getJSONObject(
                                "end_location").getDouble("lat"));
                        routeBean.setEndLon(jObjectLegs.getJSONObject(
                                "end_location").getDouble("lng"));

                        JSONArray jstepArray = jObjectLegs
                                .getJSONArray("steps");
                        if (jstepArray != null) {
                            for (int k = 0; k < jstepArray.length(); k++) {
                                stepBean = new Step();
                                JSONObject jStepObject = jstepArray
                                        .getJSONObject(k);
                                if (jStepObject != null) {

                                    stepBean.setHtml_instructions(jStepObject
                                            .getString("html_instructions"));
                                    stepBean.setStrPoint(jStepObject
                                            .getJSONObject("polyline")
                                            .getString("points"));
                                    stepBean.setStartLat(jStepObject
                                            .getJSONObject("start_location")
                                            .getDouble("lat"));
                                    stepBean.setStartLon(jStepObject
                                            .getJSONObject("start_location")
                                            .getDouble("lng"));
                                    stepBean.setEndLat(jStepObject
                                            .getJSONObject("end_location")
                                            .getDouble("lat"));
                                    stepBean.setEndLong(jStepObject
                                            .getJSONObject("end_location")
                                            .getDouble("lng"));

                                    stepBean.setListPoints(new PolyLineUtils()
                                            .decodePoly(stepBean.getStrPoint()));
                                    routeBean.getListStep().add(stepBean);
                                }

                            }
                        }
                    }

                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return routeBean;
    }

    public boolean isSuccessWithStoreId(String response) {
        AppLog.Log(TAG, response);
        if (TextUtils.isEmpty(response))
            return false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                prefManager.putUserId(jsonObject
                        .getInt(BookingConstant.Params.ID));
                prefManager.putSessionToken(jsonObject
                        .getString(BookingConstant.Params.TOKEN));
                prefManager.putEmail(jsonObject
                        .optString(BookingConstant.Params.EMAIL));
                prefManager.putLoginBy(jsonObject
                        .getString(BookingConstant.Params.LOGIN_BY));
                prefManager.putReferee(jsonObject
                        .getInt(BookingConstant.Params.IS_REFEREE));
                if (!prefManager.getLoginBy().equalsIgnoreCase(
                        MANUAL)) {
                    /*prefManager.putSocialId(jsonObject
                            .getString(Const.Params.SOCIAL_UNIQUE_ID))*/;
                }

                return true;
            } else {
                UtilsExtra.showToast(jsonObject.getString(KEY_ERROR), activity);
                // AndyUtils.showErrorToast(jsonObject.getInt(KEY_ERROR_CODE),
                // activity);
                return false;
                // AndyUtils.showToast(jsonObject.getString(KEY_ERROR),
                // activity);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public Profile parseUserAndStoreToDb(String response) {
        Profile profile = null;
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                profile = new Profile();
                ProfDAO profDAO = new ProfDAO(activity);
                profile.setPID(jsonObject.getInt(BookingConstant.Params.ID));
                profile.setProfileEmail(jsonObject.optString(BookingConstant.Params.EMAIL));
                profile.setProfileFirstName(jsonObject.getString(BookingConstant.Params.FIRSTNAME));
                profile.setProfileLastName(jsonObject.getString(BookingConstant.Params.LAST_NAME));
                profile.setProfileAddress(jsonObject.getString(BookingConstant.Params.ADDRESS));
                profile.setProfileDob(jsonObject.getString(BookingConstant.Params.GENDER));
                profile.setProfileDateJoined(jsonObject.getString(BookingConstant.Params.DATE_JOINED));
                profile.setProfilePicture(Uri.parse(jsonObject.getString(BookingConstant.Params.PICTURE)));
                profile.setProfilePhoneNumber(jsonObject.getString(BookingConstant.Params.PHONE));
                profDAO.insertProfile(profile);

            } else {
                // AndyUtils.showToast(jsonObject.getString(KEY_ERROR),
                // activity);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return profile;
    }

    public boolean isSuccess(String response) {
        if (TextUtils.isEmpty(response))
            return false;
        try {
            // AppLog.Log(Const.TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                return true;
            } else {
                UtilsExtra.showToast(jsonObject.getString(KEY_ERROR), activity);
                // AndyUtils.showErrorToast(jsonObject.getInt(KEY_ERROR_CODE),
                // activity);
                return false;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public DriverLocation getDriverLocation(String response) {
        DriverLocation driverLocation = null;
        LatLng latLng = null;
        if (TextUtils.isEmpty(response))
            return null;
        AppLog.Log(TAG, response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            driverLocation = new DriverLocation();
            latLng = new LatLng(jsonObject.getDouble(BookingConstant.Params.LATITUDE),
                    jsonObject.getDouble(BookingConstant.Params.LONGITUDE));
            driverLocation.setLatLng(latLng);
            driverLocation.setDistance(new DecimalFormat("0.00").format(Double
                    .parseDouble(jsonObject.getString(DISTANCE))));
            driverLocation.setBearing(jsonObject
                    .getDouble(BookingConstant.Params.BEARING));
            driverLocation.setUnit(jsonObject.getString(UNIT));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return driverLocation;
    }

    public int getErrorCode(String response) {
        if (TextUtils.isEmpty(response))
            return 0;
        try {
            AppLog.Log(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getInt(KEY_ERROR_CODE);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public String getMessage(String response) {
        if (TextUtils.isEmpty(response))
            return "";
        try {
            AppLog.Log(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(KEY_ERROR);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public int checkRequestStatus(String response) {
        int status = NO_REQUEST;
        try {
             AppLog.Log(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            try {
                if (jsonObject.getInt(CONFIRMED_WALKER) == 0
                        && jsonObject.getInt(STATUS) == 0) {
                    return IS_REQEUST_CREATED;
                } else if (jsonObject.getInt(CONFIRMED_WALKER) == 0
                        && jsonObject.getInt(STATUS) == 1) {
                    return NO_REQUEST;
                } else if (jsonObject.getInt(CONFIRMED_WALKER) != 0
                        && jsonObject.getInt(STATUS) == 1) {

                    if (jsonObject.getInt(IS_WALKER_STARTED) == 0) {
                        status = Integer.parseInt(IS_WALKER_STARTED);
                    } else if (jsonObject.getInt(IS_WALKER_ARRIVED) == 0) {
                        status = Integer.parseInt(IS_WALKER_ARRIVED);
                    } else if (jsonObject.getInt(IS_WALK_STARTED) == 0) {
                        status = Integer.parseInt(IS_WALK_STARTED);
                    } else if (jsonObject.getInt(IS_COMPLETED) == 0) {
                        status = Integer.parseInt(IS_COMPLETED);
                    } else if (jsonObject.getInt(IS_WALKER_RATED) == 0) {
                        status = Integer.parseInt(IS_WALKER_RATED);
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            prefManager.putPromoCode(jsonObject.optString(PROMO_CODE));
            String time = jsonObject.optString(START_TIME);
            if (!TextUtils.isEmpty(time)) {
                try {
                    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                            Locale.ENGLISH).parse(time);
                    AppLog.Log("TAG", "START DATE---->" + date.toString()
                            + " month:" + date.getMonth());
                    prefManager.putRequestTime(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (jsonObject.getString("dest_latitude").length() != 0) {
                prefManager.putClientDestination(new LatLng(jsonObject
                        .getDouble("dest_latitude"), jsonObject
                        .getDouble("dest_longitude")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public Bill parseBllingInfo(String response) {
        Bill bill = null;
        try {

            JSONObject jsonObject = new JSONObject(response)
                    .getJSONObject(KEY_BILL);
            bill = new Bill();
            bill.setBasePrice(jsonObject.getString(BASE_PRICE));
            double distance = Double
                    .parseDouble(jsonObject.getString(DISTANCE));
            // bill.setDistance(jsonObject.getString(DISTANCE));
            bill.setUnit(jsonObject.getString(UNIT));
            if (bill.getUnit().equalsIgnoreCase("kms")) {
                distance = distance * 0.62137;

            }
            bill.setDistance(new DecimalFormat("0.00").format(distance));
            bill.setDistanceCost(jsonObject.getString(DISTANCE_COST));
            bill.setTime(jsonObject.getString(TIME));
            bill.setTimeCost(jsonObject.getString(TIME_COST));
            bill.setIsPaid(jsonObject.getString(IS_PAID));
            bill.setTotal(jsonObject.getString(TOTAL));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            bill = null;
            e.printStackTrace();
        }
        return bill;
    }

    public Referral parseReffrelCode(String response) {
        Referral reffral = null;
        try {

            JSONObject jsonObject = new JSONObject(response);

            reffral = new Referral();

            reffral.setReferralCode(jsonObject.getString(REFERRAL_CODE));
            reffral.setAmountSpent(Double.parseDouble(jsonObject.getString(AMOUNT_SPENT)));
            reffral.setBalanceAmount(Double.parseDouble(jsonObject.getString(BALANCE_AMOUNT)));
            reffral.setTotalReferrals(jsonObject.getString(TOTAL_REFERRALS));
            reffral.setAmountEarned(Double.parseDouble(jsonObject.getString(AMOUNT_EARNED)));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            reffral = null;
            e.printStackTrace();
        }
        return reffral;
    }

    public Driver getDriverInfo(String response) {
        Driver driver = null;
        try {
            driver = new Driver();
            AppLog.Log(TAG, response);
            JSONObject jsonObject = new JSONObject(response)
                    .getJSONObject(KEY_WALKER);
            driver.setDriverGender(jsonObject.getString(BookingConstant.Params.GENDER));
            driver.setDriverFirstName(jsonObject.getString(BookingConstant.Params.FIRSTNAME));
            driver.setDriverLastName(jsonObject.getString(BookingConstant.Params.LAST_NAME));
            driver.setDriverPhone(jsonObject.getString(BookingConstant.Params.PHONE));
            driver.setDriverPicture(Uri.parse(jsonObject.getString(BookingConstant.Params.PICTURE)));
            driver.setLatitude(jsonObject.getDouble(BookingConstant.Params.LATITUDE));
            driver.setLongitude(jsonObject.getDouble(BookingConstant.Params.LONGITUDE));
            driver.setRating(jsonObject.getDouble(BookingConstant.Params.RATING));
            driver.setDVehicleModel(jsonObject.getString(BookingConstant.Params.TAXI_MODEL));
            driver.setDriverVehicle(jsonObject.getString(BookingConstant.Params.TAXI_NUMBER));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return driver;
    }

    public Driver getDriverDetail(String response) {
        Driver driver = null;
        try {
            driver = new Driver();
            AppLog.Log(TAG, response);
            JSONObject object = new JSONObject(response);
            JSONObject jsonObject = new JSONObject(response)
                    .getJSONObject(KEY_WALKER);
            driver.setDriverGender(jsonObject.getString(BookingConstant.Params.GENDER));
            driver.setDriverFirstName(jsonObject.getString(BookingConstant.Params.FIRSTNAME));
            driver.setDriverLastName(jsonObject.getString(BookingConstant.Params.LAST_NAME));
            driver.setDriverPhone(jsonObject.getString(BookingConstant.Params.PHONE));
            driver.setDriverPicture(Uri.parse(jsonObject.getString(BookingConstant.Params.PICTURE)));
            driver.setLatitude(jsonObject.getDouble(BookingConstant.Params.LATITUDE));
            driver.setLongitude(jsonObject.getDouble(BookingConstant.Params.LONGITUDE));
            driver.setRating(jsonObject.getDouble(BookingConstant.Params.RATING));
            driver.setDVehicleModel(jsonObject.getString(BookingConstant.Params.TAXI_MODEL));
            driver.setDriverVehicle(jsonObject.getString(BookingConstant.Params.TAXI_NUMBER));
            JSONObject jsonObjectBill = new JSONObject(response)
                    .optJSONObject(BILL);
            if (jsonObjectBill != null) {
                Bill bill = new Bill();
                bill.setUnit(jsonObjectBill.getString(UNIT));
                double distance = Double.parseDouble(jsonObjectBill
                        .getString(DISTANCE));
                if (bill.getUnit().equalsIgnoreCase("kms")) {
                    distance = distance * 0.62137;

                }
                bill.setDistance(new DecimalFormat("0.00").format(distance));
                bill.setTime(jsonObjectBill.getString(TIME));
                bill.setBasePrice(jsonObjectBill.getString(BASE_PRICE));
                bill.setTimeCost(jsonObjectBill.getString(TIME_COST));
                bill.setDistanceCost(jsonObjectBill.getString(DISTANCE_COST));
                bill.setTotal(jsonObjectBill.getString(TOTAL));
                bill.setIsPaid(jsonObjectBill.getString(IS_PAID));
                bill.setPromoBouns(jsonObjectBill.getString(PROMO_BONUS));
                bill.setReferralBouns(jsonObjectBill.getString(REFERRAL_BONUS));
                driver.setBill(bill);
            }
            driver.setBearing(jsonObject.optDouble(BookingConstant.Params.BEARING));
            // driver.getBill().setUnit(object.getString(UNIT));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return driver;

    }

    public ArrayList<LatLng> parsePathRequest(String response,
                                              ArrayList<LatLng> points) {
        // TODO Auto-generated method stub
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                JSONArray jsonArray = jsonObject
                        .getJSONArray(BookingConstant.Params.LOCATION_DATA);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    points.add(new LatLng(Double.parseDouble(json
                            .getString(BookingConstant.Params.LATITUDE)),
                            Double.parseDouble(json
                                    .getString(BookingConstant.Params.LONGITUDE))));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return points;
    }

    public int getRequestInProgress(String response) {
        if (TextUtils.isEmpty(response))
            return NO_REQUEST;
        try {
            AppLog.Log(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                int requestId = jsonObject.getInt(BookingConstant.Params.REQUEST_ID);
                new PrefManager(activity).putRequestId(requestId);
                return requestId;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return NO_REQUEST;
    }

    public int getRequestId(String response) {
        if (TextUtils.isEmpty(response))
            return NO_REQUEST;
        try {
            AppLog.Log(TAG, response);
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                int requestId = jsonObject.getInt(BookingConstant.Params.REQUEST_ID);
                new PrefManager(activity).putRequestId(requestId);
                return requestId;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return NO_REQUEST;
    }

    public ArrayList<VehicalType> parseTypes(String response,
                                             ArrayList<VehicalType> list) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TYPES);
                for (int i = 0; i < jsonArray.length(); i++) {
                    VehicalType type = new VehicalType();
                    JSONObject typeJson = jsonArray.getJSONObject(i);
                    type.setBasePrice(typeJson.getDouble(BASE_PRICE));
                    type.setBaseDistance(typeJson.getInt(BASE_DISTANCE));
                    type.setUnit(typeJson.getString(UNIT));
                    type.setIcon(typeJson.getString(ICON));
                    type.setId(typeJson.getInt(ID));
                    type.setName(typeJson.getString(NAME));
                    type.setPricePerUnitDistance(typeJson
                            .getDouble(PRICE_PER_UNIT_DISTANCE));
                    type.setPricePerUnitTime(typeJson
                            .getDouble(PRICE_PER_UNIT_TIME));
                    type.setMinFare(typeJson.optString(MIN_FARE));
                    type.setMaxSize(typeJson.optString(MAX_SIZE));
                    list.add(type);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }



    public ArrayList<Card> parseCards(String response, ArrayList<Card> listPayStackCards) {

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(PAYMENTS);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Card card = new Card();
                    JSONObject cardJson = jsonArray.getJSONObject(i);
                    // payStackCard.setStripeToken(cardJson.getString(STRIPE_TOKEN));
                    card.setLastFour(cardJson.getString(LAST_FOUR));
                    card.setToken(cardJson.getString(CUSTOMER_ID));
                    card.setCardID(cardJson.getInt(ID));
                    card.setCreatedAt(cardJson.getString(CREATED_AT));
                    card.setUpdatedAt(cardJson.getString(UPDATED_AT));
                    card.setOwnerID(cardJson.getInt(OWNER_ID));
                    card.setCardType(cardJson.getString(CARD_TYPE));
                    if (cardJson.getInt(IS_DEFAULT) == 1)
                        card.setDefault(true);
                    else
                        card.setDefault(false);
                    listPayStackCards.add(card);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listPayStackCards;
    }





    public ArrayList<String> parseNearByPlaces(String response,
                                               ArrayList<String> resultList) {
        try {
            JSONObject resultObject;
            JSONObject job = new JSONObject(response);
            JSONArray resultArr = job.getJSONArray("results");
            for (int i = 0; i < resultArr.length(); i++) {
                resultObject = resultArr.getJSONObject(i);
                // String fullVicinity = resultObject.getString("vicinity");
                // String[] vicinityArr = resultObject.getString("vicinity")
                // .split(", ");
                // String vicinity = null;
                // if (vicinityArr.length > 2) {
                // vicinity = vicinityArr[vicinityArr.length - 2];
                // vicinity += ", " + vicinityArr[vicinityArr.length - 1];
                // } else {
                // vicinity = fullVicinity;
                // }
                resultList.add(resultObject.getString("name"));
            }
        } catch (JSONException e) {
            AppLog.Log("BookingDrawerAct", "" + e);
        }
        return resultList;
    }
    public ArrayList<AppPages> parsePages(
            ArrayList<AppPages> list, String response) {
        list.clear();

        AppPages applicationPages = new AppPages();
        applicationPages.setId(-1);
        applicationPages.setTitle("Profile");
        applicationPages.setData("");
        list.add(applicationPages);

        applicationPages = new AppPages();
        applicationPages.setId(-2);
        applicationPages.setTitle("Payment");
        applicationPages.setData("");
        list.add(applicationPages);

        applicationPages = new AppPages();
        applicationPages.setId(-3);
        applicationPages.setTitle("History");
        applicationPages.setData("");
        list.add(applicationPages);

        applicationPages = new AppPages();
        applicationPages.setId(-4);
        applicationPages.setTitle("Referral");
        applicationPages.setData("");
        list.add(applicationPages);
        if (TextUtils.isEmpty(response)) {
            return list;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                JSONArray jsonArray = jsonObject
                        .getJSONArray(BookingConstant.Params.INFORMATIONS);
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        applicationPages = new AppPages();
                        JSONObject object = jsonArray.getJSONObject(i);
                        applicationPages.setId(object.getInt(BookingConstant.Params.ID));
                        applicationPages.setTitle(object
                                .getString(BookingConstant.Params.TITLE));
                        applicationPages.setData(object
                                .getString(BookingConstant.Params.CONTENT));
                        applicationPages.setIcon(object
                                .getString(BookingConstant.Params.ICON));
                        list.add(applicationPages);
                    }
                }
            }
            // else {
            // AndyUtils.showToast(jsonObject.getString(KEY_ERROR), activity);
            // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public ArrayList<History> parseHistory(String response,
                                           ArrayList<History> list) {
        list.clear();

        if (TextUtils.isEmpty(response)) {
            return list;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(REQUESTS);
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        History history = new History();
                        history.setId(object.getInt(ID));
                        history.setDate(object.getString(DATE));

                        double distance = Double.parseDouble(object
                                .getString(DISTANCE));
                        // bill.setDistance(jsonObject.getString(DISTANCE));
                        history.setUnit(object.getString(UNIT));
                        if (history.getUnit().equalsIgnoreCase("kms")) {
                            distance = distance * 0.62137;
                        }
                        history.setDistance(new DecimalFormat("0.00")
                                .format(distance));
                        history.setUnit(object.getString(UNIT));
                        history.setTime(object.getString(TIME));
                        history.setDistanceCost(object.getString(DISTANCE_COST));
                        history.setTimecost(object.getString(TIME_COST));
                        history.setBasePrice(object.getString(BASE_PRICE));
                        history.setTotal(new DecimalFormat("0.00")
                                .format(Double.parseDouble(object
                                        .getString(TOTAL))));
                        history.setType(object.getString(TYPE));
                        history.setPromoBonus(object.getString(PROMO_BONUS));
                        history.setReferralBonus(object
                                .getString(REFERRAL_BONUS));
                        JSONObject userObject = object.getJSONObject(WALKER);
                        history.setFirstName(userObject
                                .getString(BookingConstant.Params.FIRSTNAME));
                        history.setLastName(userObject
                                .getString(BookingConstant.Params.LAST_NAME));
                        history.setPhone(userObject
                                .getString(BookingConstant.Params.PHONE));
                        history.setPicture(userObject
                                .getString(BookingConstant.Params.PICTURE));
                        history.setEmail(userObject
                                .getString(BookingConstant.Params.EMAIL));
                        history.setGender(userObject.getString(BookingConstant.Params.GENDER));

                        list.add(history);
                    }
                }

            }
            // else {
            // AndyUtils.showToast(jsonObject.getString(KEY_ERROR), activity);
            // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Driver> parseNearestDrivers(String response) {
        ArrayList<Driver> listDriver = new ArrayList<Driver>();
        if (TextUtils.isEmpty(response))
            return listDriver;
        try {

            JSONArray jsonArray = new JSONObject(response)
                    .getJSONArray(WALKERS);
            for (int i = 0; i < jsonArray.length(); i++) {
                Driver driver = new Driver();
                driver.setDriverID(String.valueOf(jsonArray.getJSONObject(i).getInt(
                        BookingConstant.Params.ID)));
                driver.setLatitude(jsonArray.getJSONObject(i).getDouble(
                        BookingConstant.Params.LATITUDE));
                driver.setLongitude(jsonArray.getJSONObject(i).getDouble(
                        BookingConstant.Params.LONGITUDE));
                driver.setBearing(jsonArray.getJSONObject(i).getDouble(
                        BookingConstant.Params.BEARING));
                driver.setVehicleTypeId(jsonArray.getJSONObject(i).getInt(
                        "type"));
                listDriver.add(driver);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listDriver;
    }

    public int parseNearestDriverDuration(String response) {
        if (TextUtils.isEmpty(response))
            return 0;
        try {
            JSONArray jsonArray = new JSONObject(response)
                    .getJSONArray("routes");
            JSONArray jArrSub = jsonArray.getJSONObject(0).getJSONArray("legs");
            long totalSeconds = jArrSub.getJSONObject(0)
                    .getJSONObject("duration").getLong("value");
            int totalMinutes = Math.round(totalSeconds / 60);
            return totalMinutes;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String parseNearestDriverDurationString(String response) {
        if (TextUtils.isEmpty(response))
            return "1 min";
        try {
            JSONArray jsonArray = new JSONObject(response)
                    .getJSONArray("routes");
            JSONArray jArrSub = jsonArray.getJSONObject(0).getJSONArray("legs");

            return jArrSub.getJSONObject(0).getJSONObject("duration")
                    .getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
            return "1 min";
        }
    }

    public void parseCardAndPriceDetails(String response) {
        try {
            preferenceHelper=new PrefManager(activity.getApplicationContext());
            AppLog.Log("ParseContent", "parseCardAndPriceDetails");
            JSONObject job = new JSONObject(response);
            JSONObject cardObject = job.getJSONObject("card_details");
            preferenceHelper.putDefaultCard(cardObject.getInt("card_id"));
            preferenceHelper
                    .putDefaultCardNo(cardObject.getString("last_four"));
            preferenceHelper.putDefaultCardType(cardObject
                    .getString("card_type"));

            JSONObject chargeObject = job.getJSONObject("charge_details");
            preferenceHelper.putBasePrice(Float.parseFloat(chargeObject
                    .getString("base_price")));
            preferenceHelper.putDistancePrice(Float.parseFloat(chargeObject
                    .getString("distance_price")));
            preferenceHelper.putTimePrice(Float.parseFloat(chargeObject
                    .getString("price_per_unit_time")));
            if (job.has("owner")) {

                JSONObject walkerObject = job.getJSONObject("owner");
                AppLog.Log("ParseContent",
                        "Payment type : " + walkerObject.getInt("payment_type"));
                preferenceHelper.putPaymentMode(walkerObject
                        .getInt("payment_type"));
            }
        } catch (JSONException e) {
            AppLog.Log("MainDrawerActivity", "" + e);
        }
    }

    public ArrayList<Tour> parseTours(ArrayList<Tour> list, String response) {
        list.clear();
        if (TextUtils.isEmpty(response)) {
            return list;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean(KEY_SUCCESS)) {
                JSONArray jsonArray = jsonObject
                        .getJSONArray(BookingConstant.Params.TOUR);
                if (jsonArray.length() > 0) {
                    Tour tour;
                    PTour pTour;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        tour = new Tour();
                        JSONObject object = jsonArray.getJSONObject(i);
                        tour.setTourId(object.getInt(BookingConstant.Params.ID));
                        tour.setTourName(object.getString("tour_name"));
                        tour.setTourImage(object.getString("tour_image"));
                        tour.setTourDesc(object.getString("description"));
                        tour.setFullDayTour(object.getString("full_day_tour"));
                        tour.setHalfDayTour(object.getString("half_day_tour"));
                        tour.setFullDayPrice(object
                                .getDouble("f_day_sheduled_price"));
                        tour.setHalfDayPrice(object
                                .getDouble("h_day_sheduled_price"));
                        tour.setMorningDTime(object.getString("m_depart_time"));
                        tour.setMorningRTime(object.getString("m_return_time"));
                        tour.setAfterDTime(object.getString("a_depart_time"));
                        tour.setAfterRTime(object.getString("a_return_time"));
                        tour.setTourNote(object.getString("note"));
                        tour.setfDayDTime(object
                                .getString("full_day_depart_time"));
                        tour.setfDayRTime(object
                                .getString("full_day_return_time"));

                        // Parse gallery images
                        ArrayList<String> imageList = new ArrayList<String>();
                        String[] imgArr = object.getString("image_galllery")
                                .split(",");
                        for (String img : imgArr) {
                            imageList.add(img);
                        }
                        tour.setImgLst(imageList);

                        // Private tours parse
                        ArrayList<PTour> pTourList = new ArrayList<PTour>();
                        JSONArray pTourArr = object
                                .getJSONArray("private_tour");
                        for (int j = 0; j < pTourArr.length(); j++) {
                            JSONObject pTourJObject = pTourArr.getJSONObject(j);

                            pTour = new PTour();
                            pTour.setpTourPerson(pTourJObject.getInt("persons"));
                            pTour.setpTourPrice(pTourJObject
                                    .getDouble("tour_price"));
                            pTour.setpTourType(pTourJObject.getInt("tour_type"));
                            pTourList.add(pTour);
                        }
                        tour.setpTour(pTourList);
                        list.add(tour);
                    }
                }
            }
            // else {
            // AndyUtils.showToast(jsonObject.getString(KEY_ERROR), activity);
            // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
