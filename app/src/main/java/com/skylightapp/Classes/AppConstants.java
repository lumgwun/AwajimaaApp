package com.skylightapp.Classes;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AppConstants {
    public static final String FLUTTERWAVE_PUBLIC_KEY = "FLWPUBK_TEST-ddff7f1daad11fb95203bec5a21b4d6e-X";
    public static final String FLUTTERWAVE_SECRET_KEY = "FLWSECK_TEST-b5f18fe0c0f22172e340a1827668a0be-X";
    public static final String FLUTTERWAVE_ENCRYPTION_KEY = "FLWSECK_TEST010c1328202c";
    public static final String AWAJIMA_SO_WEBHOOK = "https://eod04os6ldlez5q.m.pipedream.net";
    public static final String AWAJIMA_SUB_WEBHOOK = "https://eob03piz55v4647.m.pipedream.net";

    //curl -d '{
    //  "test": "event"
    //}'   -H "Content-Type: application/json"   https://eob03piz55v4647.m.pipedream.net

    public static final String FIRST_TIME = "FirstTime";
    public static final String WHATS_NEW_LAST_SHOWN = "whats_new_last_shown";
    // Audio prefernces
    public static final String VIEW_PAGER_ANIME = "PagerAnime";
    // Help Preference
    public static final String SUBMIT_LOGS = "CrashLogs";
    public static final String DD_MM_YY = "DD/MONTH/YEAR";

    public static int CURRENT_CATEGORY = 0;
    public final static String SINGLE_MONTH_NR = "https://business.quickteller.com/link/pay/LumgwunqebGd";
    public final static String THREE_MONTHS_R = "https://business.quickteller.com/link/pay/LumgwunXwipB";
    public final static String SIX_MONTHS_R = "https://business.quickteller.com/link/pay/LumgwunQsB8s";
    public final static String RECURRENT_SUB = "https://business.quickteller.com/link/pay/LumgwunMa0s9";
    public final static String SIX_PREMIUM = "https://business.quickteller.com/link/pay/Lumgwunk9VxY";
    public final static String SIX_GOLD = "https://business.quickteller.com/link/pay/LumgwunjhyjH";
    public final static String PREMIUM_LIFE = "https://business.quickteller.com/link/pay/LumgwunvvGKi";
    public final static String GOLD_LIFE = "https://business.quickteller.com/link/pay/LumgwunQxPrd";

    public static final String FILENAME = "birthdays.json";
    public static final int NO_REQUEST = -1;
    public static final int NO_TIME = -1;
    public static final int CASH = 1;
    public static final int CREDIT = 0;
    public static final String[] PREFIXES_AMERICAN_EXPRESS = { "34", "37" };
    public static final String[] PREFIXES_DISCOVER = { "60", "62", "64", "65" };
    public static final String[] PREFIXES_JCB = { "35" };
    public static final String[] PREFIXES_DINERS_CLUB = { "300", "301", "302",
            "303", "304", "305", "309", "36", "38", "37", "39" };
    public static final String[] PREFIXES_VISA = { "4" };
    public static final String[] PREFIXES_MASTERCARD = { "50", "51", "52",
            "53", "54", "55" };
    public static final String AMERICAN_EXPRESS = "American Express";
    public static final String DISCOVER = "Discover";
    public static final String JCB = "JCB";
    public static final String DINERS_CLUB = "Diners Club";
    public static final String VISA = "Visa";
    public static final String VERVE = "Verve";
    public static final String MASTERCARD = "MasterCard";
    public static final String UNKNOWN = "Unknown";
    public static final String DEVICE_TOKEN = "device_token";

    // Tours type
    public static final int FULL_DAY_TOUR = 1;
    public static final int HALF_DAY_TOUR = 0;

    // Placesurls
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String TYPE_NEAR_BY = "/nearbysearch";

    public static final String AWAJIMA_PRIVACY_POLICIES = "https://docs.google.com/document/d/1G8L2qti36yVtxGFJOQR0NV1RA0eQiK2DZFpZQj_Metw/edit?usp=sharing";

    //public static final String TWILLO_ACCOUNT_SID = "AC5e05dc0a793a29dc1da2eabdebd6c28d";
    //public static final String TWILLO_AUTH_TOKEN = "@39410e8b813c131da386f3d7bb7f94f7";
    //public static final String TWILLO_PHONE_NO = "+15703251701";
    public final static String ACCOUNT_TYPE = "account_type";
    public final static byte NEW_DAILY_ACCOUNT = 0;
    public final static byte NEW_JOURNEY_ACCOUNT = 1;


    public final static String JOURNEY_ID = "journey_id";
    public final static byte NONE_JOURNEY = -99;

    public final static String CACHE_DATA = "user_preference";
    public final static String PRIMARY_CURRENCY = "primary_currency";
    public final static String SECONDARY_CURRENCY = "secondary_currency";

    public static int DEFAULT_YEAR_OF_BIRTH = 1990;
    public static long DAY_IN_MILLIS = 86400000L; // / 86,400,000 milliseconds in a day
    public static long HOUR_IN_MILLIS = 3600000L; // Amount of milliseconds in an hour

    public static int INTENT_FROM_NOTIFICATION = 30;
    public static int CONTACT_PERMISSION_CODE = 3;
    public static String INTENT_FROM_KEY = "intent_from_key";
    public static String GOOGLE_SIGN_IN_KEY = "1072707269724-3v8qbbu86kmfs252eu44amna8cpqqj9c.apps.googleusercontent.com";

    /**
     * Firebase constants
     */
    public static String TABLE_REPORTS = "birthdays";
    private static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";


    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;


    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km


    static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();

    static {

        BAY_AREA_LANDMARKS.put("SFO", new LatLng(37.621313, -122.378955));

        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(37.422611,-122.0840577));
    }
    public static final BigDecimal CENTS_IN_A_UNIT = new BigDecimal(100);


    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0);
    }

    public static PaymentsClient createPaymentsClient(Context context) {
        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder().setEnvironment(PAYMENTS_ENVIRONMENT).build();
        return Wallet.getPaymentsClient(context, walletOptions);
    }
    private static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        return new JSONObject() {{
            put("type", "PAYMENT_GATEWAY");
            put("parameters", new JSONObject() {{
                put("gateway", "example");
                put("gatewayMerchantId", "exampleGatewayMerchantId");
            }});
        }};
    }
    public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_PRODUCTION;


    public static final List<String> SUPPORTED_NETWORKS = Arrays.asList(
            "AMEX",
            "DISCOVER",
            "JCB",
            "MASTERCARD",
            "VISA");


    public static final List<String> SUPPORTED_METHODS = Arrays.asList(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS");


    public static final String COUNTRY_CODE = "US";


    public static final String CURRENCY_CODE = "USD";


    public static final List<String> SHIPPING_SUPPORTED_COUNTRIES = Arrays.asList("US", "GB","NG");


    public static final String PAYMENT_GATEWAY_TOKENIZATION_NAME = "example";


    public static final HashMap<String, String> PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {{
                put("gateway", PAYMENT_GATEWAY_TOKENIZATION_NAME);
                put("gatewayMerchantId", "exampleGatewayMerchantId");
                // Your processor may require additional parameters.
            }};


    public static final String DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME";


    public static final HashMap<String, String> DIRECT_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {{
                put("protocolVersion", "ECv2");
                put("publicKey", DIRECT_TOKENIZATION_PUBLIC_KEY);
            }};
}
