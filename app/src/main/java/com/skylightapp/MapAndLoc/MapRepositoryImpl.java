package com.skylightapp.MapAndLoc;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MapRepositoryImpl implements MapRepository {

    RemoteService mRemoteService;

    @Inject
    public MapRepositoryImpl(@NotNull RemoteService remoteService) {
        this.mRemoteService = remoteService;
    }

    //@Override
    public Observable<DirectionResponse> getRoute(DirectionRequest directionRequest) {
        return mRemoteService.getRoute(directionRequest);
    }
}
