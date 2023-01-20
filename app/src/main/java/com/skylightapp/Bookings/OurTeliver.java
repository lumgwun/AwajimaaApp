package com.skylightapp.Bookings;

import com.skylightapp.Classes.Profile;

import java.util.ArrayList;
import java.util.Collection;


public class OurTeliver {
    private  Trip trip;
    private static Profile userProfile;
    private static TripOptions tripOptions;
    private static TripListener listener;
    //private static Collection<? extends Trip> currentTrips;
    private static ArrayList<Trip> currentTrips;


    public static void identifyUser(Profile profile) {
        userProfile = profile;

    }


    public static void startTrip(TripOptions build) {
        tripOptions = build;

    }

    /*public static Collection<? extends Trip> getCurrentTrips() {
        return currentTrips;
    }

    public static void setCurrentTrips(Collection<? extends Trip> currentTrips) {
        OurTeliver.currentTrips = currentTrips;
    }*/

    public static void stopTrip(String toString) {

    }

    public static void setTripListener(TripListener tripListener) {
        listener = tripListener;
    }

    public static TripListener getTripListener() {
        return listener;
    }



    public Profile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Profile userProfile) {
        this.userProfile = userProfile;
    }

    /*public static ArrayList<Trip> getCurrentTrips() {
        return currentTrips;
    }

    public void setCurrentTrips(ArrayList<Trip> currentTrips) {
        this.currentTrips = currentTrips;
    }*/
}

