package com.skylightapp.Bookings;

import com.google.common.util.concurrent.ListenableFuture;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestProvider {
    @POST("trip/new")
    ListenableFuture<TripResponse> createTrip(@Body TaxiTrip createTripRequest);

    @GET("trip/{tripId}")
    ListenableFuture<GetTripResponse> getTrip(@Path("tripId") String tripId);

    @GET("token/profile/{tripId}")
    ListenableFuture<TokenResponse> getProfileToken(@Path("tripId") String tripId);
}
