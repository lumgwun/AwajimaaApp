package com.skylightapp.MapAndLoc;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GoogleMapData extends Customer {
    private static Customer currentListing = null;
    private static HashMap<LatLng, Customer> savedListings = new HashMap<>();

    // current camera data
    private static float cameraZoom = 15;
    private static LatLng cameraLoc = new LatLng(40.734457, -73.993886);

    // map settings
    private static Integer mapType = GoogleMap.MAP_TYPE_NORMAL;
    private static Integer cameraTilt = 0;

    //filter for displaying real estate listings
    private static Double targetMaxPrice = null;
    private static Integer targetNbrBedrooms = null;

    public GoogleMapData(int customerID, String customerName, String Phone, String currentAddress, LatLng currentLocation) {
        super();
    }

    public GoogleMapData() {
        super();

    }

    public static Integer getCameraTilt() {
        return cameraTilt;
    }

    public static void setCameraTilt(Integer cameraTilt) {
        if (cameraTilt < 0 || cameraTilt > 90)  {
            throw new IllegalArgumentException("Camera tilt must be a number between 0 and 90 degrees");
        } else {
            GoogleMapData.cameraTilt = cameraTilt;
        }
    }
    public List<HashMap<String, String>> parse(JSONObject jsonObject) {
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
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

    public static Customer getCurrentListing() {
        return currentListing;
    }

    public static void eraseCurrentListing() {
        currentListing = null;
    }


    public static void setCurrentListing(int customerID, String customerName,  LatLng currentLocation) {
        if  (isCurrentLocInSavedListings(currentLocation)) {
            currentListing = savedListings.get(currentLocation);
        }  else {
            currentListing = new Customer( customerID, customerName,  currentLocation);
        }
    }

    public static void addSavedListing(Customer newListing) {
        savedListings.put(newListing.getCusLocation(), newListing);
    }

    public static HashMap<LatLng, Customer> getSavedListings() {
        return new HashMap<>(savedListings);
    }

    public static void eraseSavedListings() {
        savedListings.clear();
    }

    public static void setCurrentListing(String stringLocation, LatLng listingLatLng) {

    }
}
