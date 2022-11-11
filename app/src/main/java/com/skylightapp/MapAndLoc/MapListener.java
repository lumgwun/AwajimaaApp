package com.skylightapp.MapAndLoc;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapListener {
    void showNearbyResponseTeams(List<LatLng> latLngList);

    void informReporterRescued();

    void showPath(List<LatLng> latLngList);

    void updateResponseTeamLocation(LatLng latLng);

    void informResponseTeamIsArriving();

    void informResponseTeamArrived();

    void informTripStart();

    void informTripEnd();

    void showRoutesNotAvailableError();

    void showDirectionApiFailedError(String error);
}
