package com.skylightapp.MapAndLoc;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RemoteService {
     @POST("routeService/driving")
    Observable<DirectionResponse> getRoute(@Body DirectionRequest directionRequest);
}
