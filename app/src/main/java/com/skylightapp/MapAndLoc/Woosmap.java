package com.skylightapp.MapAndLoc;

import static android.content.Context.MODE_PRIVATE;
import static com.skylightapp.MapAndLoc.OtherPref.Tags.WoosmapSdkTag;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;

import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.PrefManager;
import com.webgeoservices.woosmapgeofencing.LocationUpdatesService;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executors;

public class Woosmap {
    private static volatile Woosmap woosmapInstance;
    private Context context;
    private String fcmToken = "";

    private LocationManager locationManager;
    private Boolean vistingEnable = false;
    private Boolean isForegroundEnabled = false;
    private String dateTime;
    private String asyncTrackNotifOpened = null;
    private static final String PREF_NAME = "awajima";
    SharedPreferences userPreferences;

    AirshipSearchAPIReadyListener airshipSearchAPIReadyListener = null;
    AirshipVisitReadyListener airshipVisitReadyListener = null;
    AirshipRegionLogReadyListener airshipRegionLogReadyListener = null;

    MarketingCloudSearchAPIReadyListener marketingCloudSearchAPIReadyListener = null;
    MarketingCloudVisitReadyListener marketingCloudVisitReadyListener = null;
    MarketingCloudRegionLogReadyListener marketingCloudRegionLogReadyListener = null;

    LocationReadyListener locationReadyListener = null;
    SearchAPIReadyListener searchAPIReadyListener = null;
    VisitReadyListener visitReadyListener = null;
    DistanceReadyListener distanceReadyListener = null;
    RegionReadyListener regionReadyListener = null;
    RegionLogReadyListener regionLogReadyListener = null;

    private LocationUpdatesService mLocationUpdateService = null;

    public interface AirshipSearchAPIReadyListener {

        void AirshipSearchAPIReadyCallback(HashMap<String, Object> dataEvent);
    }


    public interface AirshipVisitReadyListener {

        void AirshipVisitReadyCallback(HashMap<String, Object> dataEvent);
    }


    public interface AirshipRegionLogReadyListener {

        void AirshipRegionLogReadyCallback(HashMap<String, Object> dataEvent);
    }

    public interface MarketingCloudSearchAPIReadyListener {


        void MarketingCloudSearchAPIReadyCallback(HashMap<String, Object> dataEvent);
    }


    public interface MarketingCloudVisitReadyListener {

        void MarketingCloudVisitReadyCallback(HashMap<String, Object> dataEvent);
    }


    public interface MarketingCloudRegionLogReadyListener {

        void MarketingCloudRegionLogReadyCallback(HashMap<String, Object> dataEvent);
    }

    public final class ConfigurationProfile {

        public static final String liveTracking = "liveTracking";
        public static final String passiveTracking = "passiveTracking";
        public static final String stopsTracking = "visitsTracking";

        private ConfigurationProfile() { }
    }


    public interface LocationReadyListener {

        void LocationReadyCallback(Location location);
    }


    public interface SearchAPIReadyListener {

        void SearchAPIReadyCallback(POI poi);

    }



    public interface DistanceReadyListener {

        void DistanceReadyCallback(Distance[] distances);
    }


    public interface VisitReadyListener {

        void VisitReadyCallback(Visit visit);
    }


    public interface RegionReadyListener {

        void RegionReadyCallback(Region region);
    }


    public interface RegionLogReadyListener {

        void RegionLogReadyCallback(RegionLog regionLog);
    }

    private Woosmap() {
        if (woosmapInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    public Woosmap initializeWoosmap(final Context context) {
        Woosmap woosmapInstance = initializeWoosmap(context, null);
        return woosmapInstance;
    }


    private Woosmap initializeWoosmap(Context context, String messageToken) {
        this.setupWoosmap(context);
        if (messageToken != null) {
            this.fcmToken = messageToken;
        }
        this.initWoosmap();

        return woosmapInstance;
    }


    Woosmap initializeWoosmapInBackground(Context context) {
        this.setupWoosmap(context);
        return woosmapInstance;
    }


    public void setAirshipSearchAPIReadyListener(AirshipSearchAPIReadyListener airshipSearchAPIReadyListener) {
        this.airshipSearchAPIReadyListener = airshipSearchAPIReadyListener;
    }


    public void setAirshipVisitReadyListener(AirshipVisitReadyListener airshipVisitReadyListener) {
        this.airshipVisitReadyListener = airshipVisitReadyListener;
    }


    public void setAirhshipRegionLogReadyListener(AirshipRegionLogReadyListener airshipRegionLogReadyListener) {
        this.airshipRegionLogReadyListener = airshipRegionLogReadyListener;
    }


    public void setMarketingCloudSearchAPIReadyListener(MarketingCloudSearchAPIReadyListener marketingCloudSearchAPIReadyListener) {
        this.marketingCloudSearchAPIReadyListener = marketingCloudSearchAPIReadyListener;
    }

    /**
     * Add a listener to get callback on new Visit for MarketingCloud
     *
     * @param marketingCloudVisitReadyListener
     * @see MarketingCloudVisitReadyListener
     */
    public void setMarketingCloudVisitReadyListener(MarketingCloudVisitReadyListener marketingCloudVisitReadyListener) {
        this.marketingCloudVisitReadyListener = marketingCloudVisitReadyListener;
    }

    /**
     * Add a listener to get callback on event region for MarketingCloud
     *
     * @param marketingCloudRegionLogReadyListener
     * @see MarketingCloudRegionLogReadyListener
     */
    public void setMarketingCloudRegionLogReadyListener(MarketingCloudRegionLogReadyListener marketingCloudRegionLogReadyListener) {
        this.marketingCloudRegionLogReadyListener = marketingCloudRegionLogReadyListener;
    }



    /**
     * Add a listener to get callback on new locations
     *
     * @param locationReadyListener
     * @see LocationReadyListener
     */
    public void setLocationReadyListener(LocationReadyListener locationReadyListener) {
        this.locationReadyListener = locationReadyListener;
    }

    /**
     * Add a listener to get callback on new POI
     *
     * @param searchAPIReadyListener
     * @see SearchAPIReadyListener
     */
    public void setSearchAPIReadyListener(SearchAPIReadyListener searchAPIReadyListener) {
        this.searchAPIReadyListener = searchAPIReadyListener;
    }

    /**
     * Add a listener to get callback on new Distance
     *
     * @param distanceReadyListener
     * @see DistanceReadyListener
     */
    public void setDistanceReadyListener(DistanceReadyListener distanceReadyListener) {
        this.distanceReadyListener = distanceReadyListener;
    }

    /**
     * Add a listener to get callback on new Visit
     *
     * @param visitReadyListener
     * @see VisitReadyListener
     */
    public void setVisitReadyListener(VisitReadyListener visitReadyListener) {
        this.visitReadyListener = visitReadyListener;
    }

    /**
     * Add a listener to get callback on create region
     *
     * @param regionReadyListener
     * @see RegionReadyListener
     */
    public void setRegionReadyListener(RegionReadyListener regionReadyListener) {
        this.regionReadyListener = regionReadyListener;
    }

    /**
     * Add a listener to get callback on event region
     *
     * @param regionLogReadyListener
     * @see RegionLogReadyListener
     */
    public void setRegionLogReadyListener(RegionLogReadyListener regionLogReadyListener) {
        setRegionLogReadyListener(regionLogReadyListener,false);
    }

    /**
     * Add a listener to get callback on event region
     *
     * @param regionLogReadyListener
     * @see RegionLogReadyListener
     */
    public void setRegionLogReadyListener(RegionLogReadyListener regionLogReadyListener, Boolean sendCurrentState) {
        this.regionLogReadyListener = regionLogReadyListener;
        if(sendCurrentState) {
            getLastRegionState();
        }
    }

    private void setupWoosmap(Context context) {
        this.context = context;
        this.locationManager = new LocationManager(context, this);
    }


    public static Woosmap getInstance() {
        if  (woosmapInstance == null) {
            synchronized (Woosmap.class) {
                if (woosmapInstance == null) {
                    woosmapInstance = new Woosmap();
                }
            }
        }
        return woosmapInstance;
    }

    /**
     * Should be call on your mainActivity onResume method
     */
    public void onResume() {
        if(!OtherPref.trackingEnable) {
            return;
        }
        this.isForegroundEnabled = true;
        if (this.shouldTrackUser()) {
            this.locationManager.updateLocationForeground();
        } else {
            Log.d(WoosmapSdkTag, "Get Location permissions error");
        }

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //WoosmapDb.getInstance(context).cleanOldGeographicData(context);
            }
        });

        if(mLocationUpdateService != null && OtherPref.foregroundLocationServiceEnable) {
            mLocationUpdateService.removeLocationUpdates();
        }

    }

    public void onPause() {
        if(!OtherPref.trackingEnable) {
            return;
        }

        OtherPref.saveSettings(context);

        if(OtherPref.foregroundLocationServiceEnable){
            if(mLocationUpdateService != null ) {
                mLocationUpdateService.enableLocationBackground( true );
            }else {
                // Bind to the service. If the service is in foreground mode, this signals to the service
                // that since this activity is in the foreground, the service can exit foreground mode.
                getInstance().context.getApplicationContext().bindService(new Intent(context.getApplicationContext(), LocationUpdatesService.class), mServiceConnection,Context.BIND_AUTO_CREATE);
            }
        }


        try {
            if (this.shouldTrackUser()) {
                this.isForegroundEnabled = false;
                this.locationManager.updateLocationBackground();
            } else {
                Log.d(WoosmapSdkTag, "Get Location permissions error");
            }
        } catch (NullPointerException e) {
            Log.d("WoosmapGeofencing", "Foreground inactive");
        }
    }


    void onReboot() {
        this.isForegroundEnabled = false;
        if(!OtherPref.trackingEnable) {
            return;
        }
        PrefManager prefManager= new PrefManager(context);
        //dateTime=prefManager.putWorkAddress("");
        if (this.shouldTrackUser()) {
            this.locationManager.setmLocationRequest();
            this.locationManager.updateLocationBackground();
            //this.locationManager.setMonitoringRegions();
        } else {
            Log.d(WoosmapSdkTag, "Get Location permissions error");
        }
    }

    public void onDestroy() {

        if(mLocationUpdateService != null && OtherPref.foregroundLocationServiceEnable) {
            mLocationUpdateService.removeLocationUpdates();
        }
        mLocationUpdateService = null;
        if(OtherPref.trackingEnable) {
            getInstance().context.getApplicationContext().unbindService( mServiceConnection );
        }
    }


    public Boolean shouldTrackUser() {
        return this.locationManager.checkPermissions();
    }

    public Boolean enableTracking(boolean trackingEnable) {
        OtherPref.trackingEnable = trackingEnable;
        if(OtherPref.trackingEnable) {
            onResume();
            return true;
        } else {
            this.locationManager.removeLocationUpdates();
            return false;
        }
    }

    public void enableModeHighFrequencyLocation(boolean modeHighFrequencyLocationEnable) {
        OtherPref.modeHighFrequencyLocation = modeHighFrequencyLocationEnable;
        if(OtherPref.modeHighFrequencyLocation) {
            OtherPref.searchAPIEnable = false;
            OtherPref.visitEnable = false;
            OtherPref.classificationEnable = false;
        }

        onResume();
    }


    private void initWoosmap() {
        if (fcmToken == null) {
            Log.i(WoosmapSdkTag, "Message Token is null");
        }

        /* Send notifications is opened async if the app was killed */
        if (asyncTrackNotifOpened != null) {
            asyncTrackNotifOpened = null;
        }
        if (isForegroundEnabled) {
            onResume();
        }

    }


    @RequiresApi(26)
    public void createWoosmapNotifChannel() {
        NotificationManager mNotificationManager =
                (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        String id = OtherPref.WoosmapNotificationChannelID;
        CharSequence name = "AwajimaGeofencing";
        String description = "AwajimaGeofencing Notifs";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        Objects.requireNonNull(mNotificationManager).createNotificationChannel(mChannel);
    }


    public static void setMessageToken(String messageToken) {
        Woosmap.getInstance().fcmToken = messageToken;
    }

    public void addGeofence(String id, LatLng latLng, float radius, String type) {
        addGeofence( id,latLng,radius, "", type);
    }

    public void addGeofence(String id, LatLng latLng, float radius) {
        addGeofence( id,latLng,radius, "", "circle" );
    }

    public void addGeofence(String id, LatLng latLng, float radius, String idStore, String type) {
        locationManager.addGeofence( id,latLng,radius, idStore, type );
    }

    public void removeGeofence(String id) {
        locationManager.removeGeofences(id);
    }
    public void removeGeofence() {
        //locationManager.removeGeofences();
    }

    public void replaceGeofence(String oldId, String newId, LatLng latLng, float radius){
        locationManager.replaceGeofence(oldId, newId, latLng, radius, "circle");
    }

    public void replaceGeofence(String oldId, String newId, LatLng latLng, float radius, String type){
        locationManager.replaceGeofence(oldId, newId, latLng, radius, type);
    }

    // Monitors the state of the connection to the service.
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("LocationUpdatesService", "onServiceConnected");
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            //mLocationUpdateService = binder.getService();
            mLocationUpdateService.enableLocationBackground( true );
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("LocationUpdatesService", "onServiceDisconnected");
            mLocationUpdateService = null;
        }
    };

    public void getLastRegionState() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //RegionLog rLog = new RegionLog();
                /*RegionLog rLog = WoosmapDb.getInstance(context).getRegionLogsDAO().getLastRegionLog();
                if (Woosmap.getInstance().regionLogReadyListener != null && rLog != null) {
                    Woosmap.getInstance().regionLogReadyListener.RegionLogReadyCallback(rLog);
                }*/
            }
        });
    }

    public void stopTracking() {
        OtherPref.trackingEnable = false;
        this.locationManager.removeLocationUpdates();
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = this.context.getAssets().open(fileName +".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void startTracking(String profile) {

        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset(profile));
            OtherPref.trackingEnable = obj.getBoolean( "trackingEnable" );
            OtherPref.foregroundLocationServiceEnable = obj.getBoolean( "foregroundLocationServiceEnable" );
            OtherPref.modeHighFrequencyLocation = obj.getBoolean( "modeHighFrequencyLocation" );

            OtherPref.visitEnable = obj.getBoolean( "visitEnable" );
            OtherPref.classificationEnable = obj.getBoolean( "classificationEnable" );
            if(!obj.isNull( "minDurationVisitDisplay" )) {
                OtherPref.minDurationVisitDisplay = obj.getLong( "minDurationVisitDisplay" );
            }
            if(!obj.isNull( "radiusDetectionClassifiedZOI" )) {
                OtherPref.radiusDetectionClassifiedZOI = obj.getInt( "radiusDetectionClassifiedZOI" );
            }
            if(!obj.isNull( "distanceDetectionThresholdVisits" )) {
                OtherPref.distanceDetectionThresholdVisits = obj.getDouble( "distanceDetectionThresholdVisits" );
            }
            OtherPref.creationOfZOIEnable = obj.getBoolean( "creationOfZOIEnable" );

            OtherPref.currentLocationTimeFilter = obj.getInt( "currentLocationTimeFilter" );
            OtherPref.currentLocationDistanceFilter = obj.getInt( "currentLocationTimeFilter" );
            OtherPref.accuracyFilter = obj.getInt( "accuracyFilter" );

            OtherPref.searchAPIEnable = obj.getBoolean( "searchAPIEnable" );
            OtherPref.searchAPICreationRegionEnable = obj.getBoolean( "searchAPICreationRegionEnable" );
            OtherPref.searchAPITimeFilter = obj.getInt( "searchAPITimeFilter" );
            OtherPref.searchAPIDistanceFilter = obj.getInt( "searchAPIDistanceFilter" );
            OtherPref.searchAPIRefreshDelayDay = obj.getInt( "searchAPIRefreshDelayDay" );


            OtherPref.distanceAPIEnable = obj.getBoolean( "distanceAPIEnable" );
            OtherPref.outOfTimeDelay = obj.getInt( "outOfTimeDelay" );
            OtherPref.numberOfDayDataDuration = obj.getLong( "dataDurationDelay" );

            OtherPref.setDistanceProvider( obj.getJSONObject( "distance" ).getString( "distanceProvider" ) );
            OtherPref.setModeDistance( obj.getJSONObject( "distance" ).getString( "distanceMode" ) );
            OtherPref.setDistanceUnits( obj.getJSONObject( "distance" ).getString( "distanceUnits" ) );
            OtherPref.setTrafficDistanceRouting( obj.getJSONObject( "distance" ).getString( "distanceRouting" ) );
            OtherPref.setDistanceLanguage( obj.getJSONObject( "distance" ).getString( "distanceLanguage" ) );
            OtherPref.setDistanceMaxAirDistanceFilter( obj.getJSONObject( "distance" ).getInt( "distanceMaxAirDistanceFilter" ) );
            OtherPref.setDistanceTimeFilter( obj.getJSONObject( "distance" ).getInt( "distanceTimeFilter" ) );

            enableTracking(OtherPref.trackingEnable);

            OtherPref.saveSettings(context);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
