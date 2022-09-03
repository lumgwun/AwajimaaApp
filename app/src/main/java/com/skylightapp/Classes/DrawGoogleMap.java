package com.skylightapp.Classes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.skylightapp.Classes.Customer.CUSTOMER_ADDRESS;
import static com.skylightapp.Classes.Customer.CUSTOMER_FIRST_NAME;


public class DrawGoogleMap extends GoogleApiActivity {


    Context context;
    GoogleMap map;
    String propertyListings;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private LatLng tmpLocation;
    private CusDAO cusDAO;

    public void drawMap(Context context, final GoogleMap tmpMap, LatLng cameraLoc, float cameraZoom) {
        this.context = context;
        this.map = tmpMap;

        // erase old markers on map
        map.clear();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        float tilt = GoogleMapData.getCameraTilt();
        CameraPosition x = new CameraPosition(cameraLoc, cameraZoom, tilt, 0);
        map.moveCamera(CameraUpdateFactory.newCameraPosition(x));
        // set map type
        int mapType = GoogleMapData.getMapType();
        map.setMapType(mapType);

        map.getUiSettings().setZoomControlsEnabled(true);

        if (GoogleMapData.getCurrentListing() != null) {
            //double Lat = currentListing.getLocation().latitude;
            //double Lng = currentListing.getLocation().longitude;
            map.addMarker(new MarkerOptions()
                    .position(GoogleMapData.getCurrentListing().getCusLocation()));
        }

        loadSavedListings();

        loadInfoWindow();
    }

    private void loadSavedListings() {

       /* String query = ParseQuery.getQuery("PropertyListings");
        Double price = GoogleMapData.getTargetMaxPrice();
        Properties targetNbrBedrooms = GoogleMapData.getCurrentListing();
        if (price != null) {
            query.whereLessThanOrEqualTo("Price", price);
        }
        if (targetNbrBedrooms != null) {
            query.whereEqualTo("NbrBedrooms", targetNbrBedrooms);
        }

        query.findInBackground(new FindCallback<Properties>() {

            @Override
            public void done(List<Properties> results, ParseException e) {
                if (e == null) {
                    addSavedListingMarkers(results);
                } else {
                    // something went wrong
                }
            }
        });*/
    }

    private void addSavedListingMarkers(List<Customer> parseData) {
        GoogleMapData.eraseSavedListings();

        for (Customer listing : parseData) {


            int customerID = listing.getCusUID();
            String cusName = listing.getCusFirstName();
            Double cusLatitiude = listing.getCusLocation().latitude;
            Double cusLongitude = listing.getCusLocation().longitude;
            LatLng currentLocation = new LatLng(cusLatitiude, cusLongitude);

            Customer tmpListing = new Customer(customerID,cusName,currentLocation);
            GoogleMapData.addSavedListing(tmpListing);

            final HashMap<LatLng, Customer> allListings = GoogleMapData.getSavedListings();
            if (GoogleMapData.getCurrentListing() != null) {
                allListings.put(GoogleMapData.getCurrentListing().getCusLocation()
                        , GoogleMapData.getCurrentListing());
            }
            if (GoogleMapData.getCurrentListing() == null || !(tmpLocation.equals(GoogleMapData.getCurrentListing().getCusLocation()))) {
                map.addMarker(new MarkerOptions()
                        .position(tmpLocation)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private void loadInfoWindow() {
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @SuppressLint("PotentialBehaviorOverride")
            @Override
            public View getInfoWindow(Marker marker) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.cus_map_listing, null);

                Customer listingData = null;
                int customerID = listingData.getCusUID();
                LatLng markerLocation = marker.getPosition();
                if (GoogleMapData.getCurrentListing() != null && markerLocation.equals(GoogleMapData.getCurrentListing().getCusLocation())) {
                    listingData = GoogleMapData.getCurrentListing();
                } else {
                    listingData = GoogleMapData.getSavedListings().get(markerLocation);
                }

                TextView cusName = (TextView) view.findViewById(R.id.cusName5);
                TextView address = (TextView) view.findViewById(R.id.CusAddress5);
                ImageView cusPicView = view.findViewById(R.id.cusPix);

                if (listingData != null) {
                    cusName.setText(MessageFormat.format("Name:{0}", listingData.getCusFirstName()));
                    address.setText(MessageFormat.format("Address: {0}", listingData.getCusAddress()));

                    dbHelper = new DBHelper(context);
                    cusDAO = new CusDAO(context);


                    Cursor cursor = cusDAO.getCustomerCursor(customerID);
                    cursor.moveToFirst();

                    try {
                        if (cursor.moveToNext()) {


                            int customerName = cursor.getColumnIndex(CUSTOMER_FIRST_NAME);
                            int cusAddress = cursor.getColumnIndex(CUSTOMER_ADDRESS);

                            long ID = cursor.getLong(customerName);
                            String tittle5 = cursor.getString(cusAddress);
                        }
                    } finally {
                        cursor.close();
                    }

                    return view;

                }

                return view;
            }


            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }
    public  void doImageListing(long propertyID){

        /*String stringQuery = "Select Image from PROPERTY_PICTURE_TABLE where PROPERTY_ID=\"MyImage\"";
        Cursor cursor = sqLiteDatabase.rawQuery(stringQuery, null);

        ImageView propPicView = findViewById(R.id.propPix);
        Bitmap bitmap = BitmapFactory.decodeFile(propPix);
        try {
            cursor.moveToFirst();
            byte[] bytesImage = cursor.getBlob(0);
            cursor.close();
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            propPicView.setImageBitmap(bitmapImage);
        }
        catch (Exception e){
            //propPix.setText("ERROR");
        }*/
    }

    public float getCameraZoom() {
        return map.getCameraPosition().zoom;
    }

    public LatLng getCameraLocation() {
        return new LatLng(map.getCameraPosition().target.latitude, map.getCameraPosition().target.longitude);
    }

    private static class ParseQuery<T> {
        public static ArrayList<Customer> getQuery(ArrayList<Customer> propertyListings) {
            return propertyListings;
        }

        public static String getQuery(String propertyListings) {
            return propertyListings;
        }

        public void whereLessThanOrEqualTo(String price, Double maxPrice) {

        }
    }
}
