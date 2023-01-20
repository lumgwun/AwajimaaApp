package com.skylightapp.Bookings;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.skylightapp.Classes.Profile;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;

@SuppressWarnings("UnstableApiUsage")
public class LocalProviderService {
    private static final String TAG = "LocalProviderService";

    public static final int GET_TRIP_RETRY_INTERVAL_MILLIS = 5000;
    private final RestProvider provider;

    private final Executor executor;
    TripResponse trip ;
    private final ScheduledExecutorService scheduledExecutor;

    public LocalProviderService(
            RestProvider provider, Executor executor, ScheduledExecutorService scheduledExecutor) {
        this.provider = provider;
        this.executor = executor;
        this.scheduledExecutor = scheduledExecutor;
    }

    public ListenableFuture<TripResponse> createTrip(TaxiTrip createTripRequest) {
        return provider.createTrip(createTripRequest);
    }

    public ListenableFuture<TokenResponse> fetchAuthToken(String tripId) {
        return provider.getProfileToken(tripId);
    }

    public ListenableFuture<Profile> fetchMatchedTrip(String tripName) {
        String tripId = null;
        if (TripName.create(tripName) != null) {
            if (TripName.create(tripName) != null) {
                tripId = TripName.create(tripName).getTripId();
            }
        }
        ListenableFuture<GetTripResponse> getTripResponseFuture = fetchMatchedTripWithRetries(tripId);
        String finalTripId = tripId;
        return Futures.transform(
                getTripResponseFuture, getTripResponse -> {

                    if (getTripResponse != null) {
                        trip = getTripResponse.getTrip();
                    }
                    if (trip != null) {
                        if (TripData.newBuilder() != null) {
                            return TripData.newBuilder()
                                    .setTripId(finalTripId)
                                    .setTripName(trip.getTripName())
                                    .setVehicleId(trip.getVehicleId())
                                    .setTripStatus(TripStatus.parse(trip.getTripStatus()))
                                    .setWaypoints(Lists.newArrayList(trip.getWaypoints()))
                                    .build();
                        }
                    }
                    return null;
                },
                executor);
    }

    private ListenableFuture<GetTripResponse> fetchMatchedTripWithRetries(String tripId) {
        return new RetryingFuture(scheduledExecutor)
                .runWithRetries(
                        () -> provider.getTrip(tripId),
                        RetryingFuture.RUN_FOREVER,
                        GET_TRIP_RETRY_INTERVAL_MILLIS,
                        LocalProviderService::isTripValid);
    }

    private static boolean isTripValid(GetTripResponse response) {
        return response != null
                && response.getTrip() != null
                && response.getTrip().getVehicleId() != null
                && !response.getTrip().getVehicleId().isEmpty();
    }

    public static RestProvider createRestProvider(String baseUrl) {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        return retrofit.create(RestProvider.class);
    }
}
