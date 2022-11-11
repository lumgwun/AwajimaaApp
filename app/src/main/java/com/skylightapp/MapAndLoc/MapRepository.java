package com.skylightapp.MapAndLoc;

import io.reactivex.rxjava3.core.Observable;

public interface MapRepository {
    Observable<DirectionResponse> getRoute(DirectionRequest directionRequest);
}
