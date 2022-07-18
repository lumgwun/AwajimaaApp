package com.skylightapp.RealEstate;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class GoogleMapData extends Properties{
    private static Properties currentListing = null;
    private static HashMap<LatLng, Properties> savedListings = new HashMap<>();

    private static float cameraZoom = 15;
    private static LatLng cameraLoc = new LatLng(4.8359, 7.0139);

    private static Integer mapType = GoogleMap.MAP_TYPE_HYBRID;
    private static Integer cameraTilt = 0;

    private static Double targetMaxPrice = null;
    private static Integer targetNbrBedrooms = null;

    public GoogleMapData(long propertyID, String tittleOfProperty, String description, Double price, String currentAddress, LatLng currentLocation) {
        super();
        //super(propertyID, tittleOfProperty, description, price, currentAddress, currentLocation);
    }

    public static Integer getCameraTilt() {
        return cameraTilt;
    }

    public static void setCameraTilt(Integer cameraTilt) {
        if (cameraTilt < 0 || cameraTilt > 90)  {
            throw new IllegalArgumentException("oops,something went wrong");
        } else {
            GoogleMapData.cameraTilt = cameraTilt;
        }
    }

    public static String getMapName() {
        String mapName;
        switch (mapType) {
            case GoogleMap.MAP_TYPE_NORMAL :
                mapName = "Normal";
                break;
            case GoogleMap.MAP_TYPE_HYBRID :
                mapName = "Hybrid";
                break;
            case GoogleMap.MAP_TYPE_SATELLITE :
                mapName = "Satellite";
                break;
            case GoogleMap.MAP_TYPE_TERRAIN :
                mapName = "Terrain";
                break;
            default:
                throw new IllegalArgumentException();
        }
        return mapName;
    }

    public static Integer getMapType() {
        return mapType;
    }

    public static void setMapType(String mapName) {
        switch (mapName) {
            case "Normal":
                mapType = GoogleMap.MAP_TYPE_NORMAL;
                break;
            case "Hybrid":
                mapType = GoogleMap.MAP_TYPE_HYBRID;
                break;
            case "Satellite":
                mapType = GoogleMap.MAP_TYPE_SATELLITE;
                break;
            case "Terrain":
                mapType = GoogleMap.MAP_TYPE_TERRAIN;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Double getTargetMaxPrice() {
        return targetMaxPrice;
    }

    public static void setTargetMaxPrice(Double targetMaxPrice) {
        GoogleMapData.targetMaxPrice = targetMaxPrice;
    }

    public static Integer getTargetNbrBedrooms() {
        return targetNbrBedrooms;
    }

    public static void setTargetNbrBedrooms(Integer targetNbrBedrooms) {
        GoogleMapData.targetNbrBedrooms = targetNbrBedrooms;
    }

    public static float getCameraZoom() {
        return cameraZoom;
    }

    public static void setCameraZoom(float cameraZoom) {
        GoogleMapData.cameraZoom = cameraZoom;
    }

    public static LatLng getCameraLoc() {
        return new LatLng(cameraLoc.latitude, cameraLoc.longitude);
    }

    public static void setCameraLoc(LatLng cameraLoc) {
        GoogleMapData.cameraLoc = cameraLoc;
    }

    public static boolean isCurrentLocInSavedListings(LatLng currentLocation)  {
        return savedListings.containsKey(currentLocation);
    }

    public static Properties getCurrentListing() {
        return currentListing;
    }

    public static void eraseCurrentListing() {
        currentListing = null;
    }

    public static void setCurrentListing(long propertyID,String tittleOfProperty,String description, Double price,String currentAddress, LatLng currentLocation) {
        if  (isCurrentLocInSavedListings(currentLocation)) {
            currentListing = savedListings.get(currentLocation);
        }  else {
            currentListing = new Properties(propertyID,tittleOfProperty,description, price,currentAddress, currentLocation);
        }
    }

    public static void addSavedListing(Properties newListing) {
        savedListings.put(newListing.getPropertyLocation(), newListing);
    }

    public static HashMap<LatLng, Properties> getSavedListings() {
        return new HashMap<>(savedListings);
    }

    public static void eraseSavedListings() {
        savedListings.clear();
    }

    /*public static void setCurrentListing(String stringLocation, LatLng listingLatLng) {

    }*/
}
