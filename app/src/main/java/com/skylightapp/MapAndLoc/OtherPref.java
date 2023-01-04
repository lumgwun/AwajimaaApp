package com.skylightapp.MapAndLoc;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class OtherPref {
    private static final String PREF_NAME = "awajima";
    public static void saveSettings(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.clear();
        prefsEditor.putBoolean("modeHighFrequencyLocationEnable", modeHighFrequencyLocation);
        prefsEditor.putBoolean("trackingEnable", trackingEnable);
        prefsEditor.putInt("currentLocationTimeFilter", currentLocationTimeFilter);
        prefsEditor.putInt("currentLocationDistanceFilter", currentLocationDistanceFilter);
        prefsEditor.putBoolean("searchAPIEnable", searchAPIEnable);
        prefsEditor.putBoolean("visitEnable", visitEnable);
        prefsEditor.putBoolean("creationOfZOIEnable", creationOfZOIEnable);
        prefsEditor.putBoolean("searchAPICreationRegionEnable", searchAPICreationRegionEnable);
        prefsEditor.putInt("poiRadius", poiRadius);
        prefsEditor.putString("poiRadiusNameFromResponse", poiRadiusNameFromResponse);
        prefsEditor.putInt("searchAPITimeFilter", searchAPITimeFilter);
        prefsEditor.putInt("distanceTimeFilter", distanceTimeFilter);
        prefsEditor.putInt("distanceMaxAirDistanceFilter", distanceMaxAirDistanceFilter);
        prefsEditor.putInt("searchAPIDistanceFilter", searchAPIDistanceFilter);
        prefsEditor.putInt("searchAPIRefreshDelayDay", searchAPIRefreshDelayDay);
        prefsEditor.putLong("searchAPILastRequestTimeStamp", searchAPILastRequestTimeStamp);
        prefsEditor.putBoolean("distanceAPIEnable", distanceAPIEnable);
        prefsEditor.putString("modeDistance", modeDistance);
        prefsEditor.putString("trafficDistanceRouting", trafficDistanceRouting);
        prefsEditor.putString("distanceProvider", distanceProvider);
        prefsEditor.putString("distanceUnits", distanceUnits);
        prefsEditor.putString("distanceLanguage", distanceLanguage);
        prefsEditor.putInt("accuracyFilter", accuracyFilter);
        prefsEditor.putInt("outOfTimeDelay", outOfTimeDelay);
        prefsEditor.putFloat("distanceDetectionThresholdVisits", (float) distanceDetectionThresholdVisits);
        prefsEditor.putLong("minDurationVisitDisplay", minDurationVisitDisplay);
        prefsEditor.putLong("numberOfDayDataDuration", numberOfDayDataDuration);
        prefsEditor.putBoolean("classificationEnable", classificationEnable);
        prefsEditor.putInt("radiusDetectionClassifiedZOI", radiusDetectionClassifiedZOI);
        prefsEditor.putString("privateKeyGMPStatic", privateKeyGMPStatic);
        prefsEditor.putString("privateKeyWoosmapAPI", privateKeyWoosmapAPI);
        prefsEditor.putString("WoosmapURL", Urls.WoosmapURL);
        prefsEditor.putString("SearchAPIUrl", Urls.SearchAPIUrl);
        prefsEditor.putString("DistanceAPIUrl", Urls.DistanceAPIUrl);
        prefsEditor.putString("TrafficDistanceAPIUrl", Urls.TrafficDistanceAPIUrl);
        prefsEditor.putString("GoogleMapStaticUrl", Urls.GoogleMapStaticUrl);
        prefsEditor.putString("GoogleMapStaticUrl1POI", Urls.GoogleMapStaticUrl1POI);
        prefsEditor.putBoolean("checkIfPositionIsInsideGeofencingRegionsEnable", checkIfPositionIsInsideGeofencingRegionsEnable);
        prefsEditor.putBoolean("foregroundLocationServiceEnable", foregroundLocationServiceEnable);
        prefsEditor.putString("updateServiceNotificationTitle", updateServiceNotificationTitle);
        prefsEditor.putString("updateServiceNotificationText", updateServiceNotificationText);
        prefsEditor.putString("WoosmapNotificationChannelID", WoosmapNotificationChannelID);
        prefsEditor.putString("WoosmapNotificationChannelName", WoosmapNotificationChannelName);
        prefsEditor.putString("WoosmapNotificationDescriptionChannel", WoosmapNotificationDescriptionChannel);
        prefsEditor.putBoolean("WoosmapNotificationActive", WoosmapNotificationActive);
        prefsEditor.putString("SFMCAccessToken", SFMCAccessToken);

        //convert to string using gson
        Gson gson = new Gson();
        String searchAPIHashMapString = gson.toJson(searchAPIParameters);
        prefsEditor.putString("searchAPIParameters", searchAPIHashMapString).apply();
        String userPropertiesHashMapString = gson.toJson(userPropertiesFilter);
        prefsEditor.putString("userPropertiesFilter", userPropertiesHashMapString).apply();
        String SFMCCredentialsHashMapString = gson.toJson(SFMCCredentials);
        prefsEditor.putString("SFMCCredentials", SFMCCredentialsHashMapString).apply();
        prefsEditor.putBoolean("optimizeDistanceRequest", optimizeDistanceRequest);


        prefsEditor.commit();

    }
    public static void loadSettings(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        OtherPref.modeHighFrequencyLocation = mPrefs.getBoolean( "modeHighFrequencyLocationEnable", OtherPref.modeHighFrequencyLocation );
        OtherPref.trackingEnable = mPrefs.getBoolean( "trackingEnable", OtherPref.trackingEnable );
        OtherPref.currentLocationTimeFilter  = mPrefs.getInt( "currentLocationTimeFilter",OtherPref.currentLocationTimeFilter );
        OtherPref.currentLocationDistanceFilter  = mPrefs.getInt( "currentLocationDistanceFilter",OtherPref.currentLocationDistanceFilter );
        OtherPref.searchAPIEnable  = mPrefs.getBoolean( "searchAPIEnable",OtherPref.searchAPIEnable );
        OtherPref.visitEnable  = mPrefs.getBoolean( "visitEnable",OtherPref.visitEnable );
        OtherPref.creationOfZOIEnable = mPrefs.getBoolean( "creationOfZOIEnable",OtherPref.creationOfZOIEnable );
        OtherPref.searchAPICreationRegionEnable  = mPrefs.getBoolean( "searchAPICreationRegionEnable",OtherPref.searchAPICreationRegionEnable );
        OtherPref.poiRadius =  mPrefs.getInt( "poiRadius",OtherPref.poiRadius );
        OtherPref.poiRadiusNameFromResponse  = mPrefs.getString( "poiRadiusNameFromResponse",OtherPref.poiRadiusNameFromResponse );
        OtherPref.searchAPITimeFilter  = mPrefs.getInt( "searchAPITimeFilter",OtherPref.searchAPITimeFilter );
        OtherPref.distanceMaxAirDistanceFilter = mPrefs.getInt( "distanceMaxAirDistanceFilter",OtherPref.distanceMaxAirDistanceFilter );
        OtherPref.searchAPIDistanceFilter  = mPrefs.getInt( "searchAPIDistanceFilter",OtherPref.searchAPIDistanceFilter );
        OtherPref.searchAPIRefreshDelayDay = mPrefs.getInt( "searchAPIRefreshDelayDay",OtherPref.searchAPIRefreshDelayDay );
        OtherPref.searchAPILastRequestTimeStamp = mPrefs.getLong( "searchAPILastRequestTimeStamp",OtherPref.searchAPILastRequestTimeStamp );
        OtherPref.distanceAPIEnable  = mPrefs.getBoolean( "distanceAPIEnable",OtherPref.distanceAPIEnable );
        OtherPref.trafficDistanceRouting  = mPrefs.getString( "trafficDistanceRouting",OtherPref.trafficDistanceRouting );
        OtherPref.distanceProvider  = mPrefs.getString( "distanceProvider",OtherPref.distanceProvider );
        OtherPref.distanceUnits  = mPrefs.getString( "distanceUnits",OtherPref.distanceUnits );
        OtherPref.distanceLanguage  = mPrefs.getString( "distanceLanguage",OtherPref.distanceLanguage );
        OtherPref.modeDistance  = mPrefs.getString( "modeDistance",OtherPref.modeDistance );
        OtherPref.accuracyFilter  = mPrefs.getInt( "accuracyFilter",OtherPref.accuracyFilter );
        OtherPref.outOfTimeDelay  = mPrefs.getInt( "outOfTimeDelay",OtherPref.outOfTimeDelay );
        OtherPref.distanceDetectionThresholdVisits  = mPrefs.getFloat( "distanceDetectionThresholdVisits", (float) OtherPref.distanceDetectionThresholdVisits );
        OtherPref.minDurationVisitDisplay  = mPrefs.getLong("minDurationVisitDisplay",OtherPref.minDurationVisitDisplay);
        OtherPref.numberOfDayDataDuration  = mPrefs.getLong("numberOfDayDataDuration",OtherPref.minDurationVisitDisplay);
        OtherPref.classificationEnable  = mPrefs.getBoolean("classificationEnable",OtherPref.classificationEnable );
        OtherPref.radiusDetectionClassifiedZOI  = mPrefs.getInt( "radiusDetectionClassifiedZOI",OtherPref.radiusDetectionClassifiedZOI );
        OtherPref.privateKeyGMPStatic  = mPrefs.getString( "privateKeyGMPStatic",OtherPref.privateKeyGMPStatic );
        OtherPref.privateKeyWoosmapAPI  = mPrefs.getString( "privateKeyWoosmapAPI",OtherPref.privateKeyWoosmapAPI );
        OtherPref.Urls.WoosmapURL  = mPrefs.getString( "WoosmapURL",OtherPref.Urls.WoosmapURL);
        OtherPref.Urls.SearchAPIUrl = mPrefs.getString( "SearchAPIUrl",OtherPref.Urls.SearchAPIUrl);
        OtherPref.Urls.DistanceAPIUrl  = mPrefs.getString( "DistanceAPIUrl",OtherPref.Urls.DistanceAPIUrl);
        OtherPref.Urls.TrafficDistanceAPIUrl  = mPrefs.getString( "TrafficDistanceAPIUrl",OtherPref.Urls.TrafficDistanceAPIUrl);
        OtherPref.Urls.GoogleMapStaticUrl  = mPrefs.getString( "GoogleMapStaticUrl",OtherPref.Urls.GoogleMapStaticUrl);
        OtherPref.Urls.GoogleMapStaticUrl1POI  = mPrefs.getString( "GoogleMapStaticUrl1POI",OtherPref.Urls.GoogleMapStaticUrl1POI);
        OtherPref.checkIfPositionIsInsideGeofencingRegionsEnable = mPrefs.getBoolean( "checkIfPositionIsInsideGeofencingRegionsEnable", OtherPref.checkIfPositionIsInsideGeofencingRegionsEnable );
        OtherPref.foregroundLocationServiceEnable = mPrefs.getBoolean( "foregroundLocationServiceEnable", OtherPref.foregroundLocationServiceEnable );
        OtherPref.updateServiceNotificationTitle = mPrefs.getString( "updateServiceNotificationTitle", OtherPref.updateServiceNotificationTitle );
        OtherPref.updateServiceNotificationText = mPrefs.getString( "updateServiceNotificationText", OtherPref.updateServiceNotificationText );
        OtherPref.WoosmapNotificationChannelID  = mPrefs.getString( "WoosmapNotificationChannelID",OtherPref.WoosmapNotificationChannelID );
        OtherPref.WoosmapNotificationChannelName  = mPrefs.getString( "WoosmapNotificationChannelName",OtherPref.WoosmapNotificationChannelID );
        OtherPref.WoosmapNotificationDescriptionChannel  = mPrefs.getString( "WoosmapNotificationDescriptionChannel",OtherPref.WoosmapNotificationDescriptionChannel );
        OtherPref.WoosmapNotificationActive = mPrefs.getBoolean( "WoosmapNotificationActive", OtherPref.WoosmapNotificationActive );
        OtherPref.SFMCAccessToken  = mPrefs.getString( "SFMCAccessToken",OtherPref.SFMCAccessToken );
        OtherPref.optimizeDistanceRequest = mPrefs.getBoolean( "optimizeDistanceRequest", OtherPref.optimizeDistanceRequest );

        //convert to string using gson
        Gson gson = new Gson();
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        String searchAPIHashMapString = gson.toJson(searchAPIParameters);
        String userPropertiesHashMapString = gson.toJson(userPropertiesFilter);
        String SFMCCredentialsHashMapString = gson.toJson(OtherPref.SFMCCredentials);

        String searchAPIParametersString = mPrefs.getString("searchAPIParameters", searchAPIHashMapString);
        OtherPref.searchAPIParameters = gson.fromJson(searchAPIParametersString, searchAPIParameters.getClass());

        String SFMCCredentialsString = mPrefs.getString("SFMCCredentials", SFMCCredentialsHashMapString);
        OtherPref.SFMCCredentials = gson.fromJson(SFMCCredentialsString, SFMCCredentials.getClass());

        String userPropertiesFilterString = mPrefs.getString("userPropertiesFilter", userPropertiesHashMapString);
        OtherPref.userPropertiesFilter = gson.fromJson(userPropertiesFilterString, userPropertiesFilter.getClass());

    }
    static String AndroidDeviceModel = "android";
    static String PositionDateFormat = "yyyy-MM-dd'T'HH:mm:ss Z";
    static final String WoosmapNotification = "woosmapNotification";
    static public String WoosmapNotificationChannelID = "Location Channel ID";
    static public String WoosmapNotificationChannelName = "Location Channel Name";
    static public String WoosmapNotificationDescriptionChannel = "Location Channel";
    static public boolean WoosmapNotificationActive = false;

    //Enable/disable Location
    static public boolean modeHighFrequencyLocation = false;

    //Enable/disable Location
    static public boolean trackingEnable = false;

    //filter time to refresh user location
    static public int currentLocationTimeFilter = 0;

    //filter distance to refresh user location
    static public int currentLocationDistanceFilter = 0;

    //Enable/disable VisitEnable
    static public boolean visitEnable = false;

    //Enable/disable Creation of ZOI
    static public boolean creationOfZOIEnable = false;

    //Enable/disable SearchAPI
    static public boolean searchAPIEnable = true;

    //Enable/disable Creation region on SearchAPI
    static public boolean searchAPICreationRegionEnable = true;

    //POI radius
    static public int poiRadius = 100;

    //POI radius parameters name
    static public String poiRadiusNameFromResponse = "";

    public static int getDistanceMaxAirDistanceFilter() {
        return distanceMaxAirDistanceFilter;
    }

    public static void setDistanceMaxAirDistanceFilter(int distanceMaxAirDistanceFilter) {
        OtherPref.distanceMaxAirDistanceFilter = distanceMaxAirDistanceFilter;
    }

    //filter distance to request Distance API
    static public int distanceMaxAirDistanceFilter = 1000000;

    public static int getDistanceTimeFilter() {
        return distanceTimeFilter;
    }

    public static void setDistanceTimeFilter(int distanceTimeFilter) {
        OtherPref.distanceTimeFilter = distanceTimeFilter;
    }

    //the minimum time to wait between 2 requests to the distance provider.
    static public int distanceTimeFilter = 0;

    //filter time to request Search API
    static public int searchAPITimeFilter = 0;

    //filter distance to request Search API
    static public int searchAPIDistanceFilter = 0;

    //filter refresh delay searhAPI
    public static int getSearchAPIRefreshDelayDay() {
        return searchAPIRefreshDelayDay;
    }

    public static void setSearchAPIRefreshDelayDay(int searchAPIRefreshDelayDay) {
        OtherPref.searchAPIRefreshDelayDay = searchAPIRefreshDelayDay;
    }

    static public int searchAPIRefreshDelayDay = 1;

    public static long getSearchAPILastRequestTimeStamp() {
        return searchAPILastRequestTimeStamp;
    }

    public static void setSearchAPILastRequestTimeStamp(long searchAPILastRequestTimeStamp) {
        OtherPref.searchAPILastRequestTimeStamp = searchAPILastRequestTimeStamp;
    }

    static public long searchAPILastRequestTimeStamp = 0;

    //Enable/disable DistanceAPI
    static public boolean distanceAPIEnable = true;
    static public boolean optimizeDistanceRequest=true;

    //Mode transportation DistanceAPI
    private static final String drivingMode  = "driving";
    private static final String walkingMode  = "walking";
    private static final String cyclingMode  = "cycling";
    private static final String truckMode  = "truck";
    static public String modeDistance = drivingMode;

    //Distance Provider
    public static final String woosmapDistance = "AwajimaDistance";
    public static final String woosmapTraffic = "AwajimaTraffic";
    static public String distanceProvider = woosmapDistance;

    //Distance Routing
    private static final String fastest = "fastest";
    private static final String balanced = "balanced";
    static public String trafficDistanceRouting = fastest;

    //Distance Language
    static public String distanceLanguage = "en";

    //Distance Units
    private static final String metric = "metric";
    private static final String imperial = "imperial";
    static public String distanceUnits = metric;

    public static void setModeDistance(String modeDistance) {
        if(modeDistance.equals(drivingMode) || modeDistance.equals(walkingMode) || modeDistance.equals(cyclingMode) || modeDistance.equals(truckMode)) {
            OtherPref.modeDistance = modeDistance;
        } else {
            OtherPref.modeDistance = drivingMode;
        }

    }

    public static void setDistanceProvider(String distanceProvider) {
        if(distanceProvider.equals(woosmapDistance) || distanceProvider.equals(woosmapTraffic)) {
            OtherPref.distanceProvider = distanceProvider;
        } else {
            OtherPref.distanceProvider = woosmapDistance;
        }
    }

    public static void setTrafficDistanceRouting(String trafficDistanceRouting) {
        if(trafficDistanceRouting.equals(fastest) || trafficDistanceRouting.equals(balanced) ) {
            OtherPref.trafficDistanceRouting = trafficDistanceRouting;
        } else {
            OtherPref.trafficDistanceRouting = fastest;
        }
    }

    public static void setDistanceLanguage(String distanceLanguage) {
        OtherPref.distanceLanguage = distanceLanguage;
    }

    public static void setDistanceUnits(String distanceUnits) {
        if(distanceUnits.equals(imperial) || distanceUnits.equals(metric) ) {
            OtherPref.distanceUnits = distanceUnits;
        } else {
            OtherPref.distanceUnits = metric;
        }

    }

    public static String getModeDistance() {
        return modeDistance;
    }

    public static String getDistanceProvider() {
        return distanceProvider;
    }

    public static String getTrafficDistanceRouting() {
        return trafficDistanceRouting;
    }

    public static String getDistanceLanguage() {
        return distanceLanguage;
    }

    public static String getDistanceUnits() {
        return distanceUnits;
    }

    //Filter Accuracy of the location
    static public int accuracyFilter = 100;

    // delay for outdated notification
    static public int outOfTimeDelay = 300;

    // Distance detection threshold for visits
    static public double distanceDetectionThresholdVisits = 25.0;

    // Distance detection threshold for visits
    static public long minDurationVisitDisplay = 60 * 5;
    static public long durationVisitFilter = 1000 * minDurationVisitDisplay;

    //Delay of Duration data
    static public long numberOfDayDataDuration = 30;// number of day
    static public long dataDurationDelay = numberOfDayDataDuration * 1000 * 86400;

    //Active Classification
    static public boolean classificationEnable = false;

    // Distance detection threshold for a ZOI classified
    static public int radiusDetectionClassifiedZOI = 50;

    // Key for APIs
    static public String privateKeyGMPStatic = "";
    static public String privateKeyWoosmapAPI = "";

    //Checking Position is inside a region
    static public boolean checkIfPositionIsInsideGeofencingRegionsEnable = true;

    //Notification ForegroundService
    static public boolean foregroundLocationServiceEnable = false;
    static public String updateServiceNotificationTitle = "Location updated";
    static public String updateServiceNotificationText = "This app use your location";


    static public HashMap<String, String> searchAPIParameters = new HashMap<String, String>();
    static public ArrayList<String> userPropertiesFilter = new ArrayList<String>();

    //SFMCCredentials
    static public HashMap<String, String> SFMCCredentials = new HashMap<String, String>();

    //Token SFMC
    static public String SFMCAccessToken = "";





    public static class Tags {
        public static final String WoosmapSdkTag = "AwajimaMapSdk";
        static final String WoosmapBackgroundTag = "AwajimaMapBackground";
        static String WoosmapVisitsTag = "AwajimaMapVisit";
        static String NotificationError = "NotificationError";
        static String WoosmapBroadcastTag = "AwajimaMapBroadcast";
        static String WoosmapGeofenceTag = "AwajimaMapGeofence";
    }

    static class Urls {
        static String WoosmapURL = "https://api.woosmap.com";
        static String SearchAPIUrl = "%s/stores/search/?private_key=%s&lat=%s&lng=%s&stores_by_page=5";
        static String DistanceAPIUrl ="%s/distance/distancematrix/json?mode=%s&units=%s&language=%s&origins=%s,%s&destinations=%s&private_key=%s&elements=duration_distance";
        static String TrafficDistanceAPIUrl ="%s/traffic/distancematrix/json?mode=%s&units=%s&language=%s&routing=%s&origins=%s,%s&destinations=%s&private_key=%s";
        static String GoogleMapStaticUrl = "https://maps.google.com/maps/api/staticmap?markers=color:red%%7C%s,%s&markers=color:blue%%7C%s,%s&zoom=14&size=400x400&sensor=true&key=%s";
        static String GoogleMapStaticUrl1POI = "https://maps.google.com/maps/api/staticmap?markers=color:red%%7C%s,%s&zoom=14&size=400x400&sensor=true&key=%s";
    }


    public static String getNotificationDefaultUri(Context context) {
        String notificationUri = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            notificationUri = bundle.getString("woosmap_notification_defautl_uri");
            Log.d(Tags.WoosmapSdkTag, "notification defautl uri : " + notificationUri);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(Tags.WoosmapSdkTag, "Failed to load project key, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(Tags.WoosmapSdkTag, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
        return notificationUri;
    }
}
