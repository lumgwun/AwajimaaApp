package com.skylightapp.Bookings;

import android.app.Application;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.ImmutableList;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CusViewModel {
    private static final int IDLE_STATE_RESET_DELAY_SECONDS = 3;

    interface JourneySharingListener {

        TripModel startJourneySharing(TripData tripData);

        void stopJourneySharing();

        void updateCurrentLocation(LatLng latLang);
    }

    private static final String TAG = "ConsumerViewModel";

    private final MutableLiveData<Integer> appState = new MutableLiveData<>();

    private final MutableLiveData<LatLng> pickupLocation = new MutableLiveData<>();

    private final MutableLiveData<LatLng> dropoffLocation = new MutableLiveData<>();

    private final MutableLiveData<ImmutableList<LatLng>> intermediateDestinations =
            new MutableLiveData<>();

    private final MutableLiveData<Integer> tripStatus = new MutableLiveData<>();

    private final MutableLiveData<String> tripId = new MutableLiveData<>();

    private final MutableLiveData<Integer> remainingDistanceMeters = new MutableLiveData<>();

    private final MutableLiveData<Long> nextWaypointEta = new MutableLiveData<>();

    private final MutableLiveData<TripInfo> tripInfo = new MutableLiveData<>();

    private final MutableLiveData<TripModel> trip = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isSharedTripType = new MutableLiveData<>();

    private final MutableLiveData<ImmutableList<TripWaypoint>> otherTripWaypoints = new MutableLiveData<>(ImmutableList.of());

    private final SingleLiveEvent<Integer> errorMessage = new SingleLiveEvent<>();
    private LocalProviderService providerService ;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private Executor mainExecutor ;
    private final ScheduledExecutorService scheduledExecutor =
            Executors.newSingleThreadScheduledExecutor();

    private WeakReference<JourneySharingListener> journeySharingListener = new WeakReference<>(null);

    public CusViewModel(Application application) {
        super();
        providerService = new LocalProviderService(LocalProviderService.createRestProvider(ProviderUtils.getProviderBaseUrl(application)), executor, scheduledExecutor);
        appState.setValue(AppStates.UNINITIALIZED);
        mainExecutor = ContextCompat.getMainExecutor(application);
    }

}
