package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLngBounds;
import com.webgeoservices.woosmapgeofencingcore.database.Region;
import com.webgeoservices.woosmapgeofencingcore.database.RegionLog;

import java.io.Serializable;

public class PlaceData implements Parcelable, Serializable {

    public static final String COLUMN_PLACE_ID = "pLace_eNtry_cOLUMN";
    public static final String PLACE_ENTRY_ID = "pLace_eNtry_ID";
    public static final String PLACE_ENTRY_TABLE = "pLace_eNtry_table";
    public static final String PLACE_ENTRY_PROF_ID = "pLace_eNtry_ProfID";
    public static final String PLACE_ENTRY_NAME = "pLace_eNtry_Name";
    public static final String PLACE_ENTRY_ADDRESS = "pLace_eNtry_Adress";
    public static final String PLACE_ENTRY_PHONE = "pLace_eNtry_Phone";
    public static final String PLACE_ENTRY_LATLNG = "pLace_eNtry_LatLng";
    public static final String PLACE_ENTRY_LATLNGBOUNDS = "pLace_eNtry_Bounds";
    public static final String PLACE_ENTRY_REPORTID = "pLace_eNtry_ReportID";
    public static final String PLACE_ENTRY_TIMESTAMP = "pLace_eNtry_Datetime";

    public static final String CREATE_PLACES_TABLE = "CREATE TABLE " + PLACE_ENTRY_TABLE + " (" + PLACE_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PLACE_ID + " TEXT NOT NULL, " +"FOREIGN KEY(" + PLACE_ENTRY_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +PLACE_ENTRY_PROF_ID + " INTEGER, "+  PLACE_ENTRY_NAME + " TEXT, "+PLACE_ENTRY_REPORTID + " INTEGER, "+  PLACE_ENTRY_LATLNGBOUNDS + " FLOAT, "+PLACE_ENTRY_LATLNG + " FLOAT, "+  PLACE_ENTRY_ADDRESS + " TEXT, " + PLACE_ENTRY_PHONE + " TEXT, "+  PLACE_ENTRY_TIMESTAMP + " TEXT, "+
            "UNIQUE (" + COLUMN_PLACE_ID + ") ON CONFLICT REPLACE" + "); ";

    protected PlaceData(Parcel in) {
        date = in.readLong();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            POILatitude = null;
        } else {
            POILatitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            POILongitude = null;
        } else {
            POILongitude = in.readDouble();
        }
        city = in.readString();
        travelingDistance = in.readString();
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readDouble();
        }
        zipCode = in.readString();
        if (in.readByte() == 0) {
            accuracy = null;
        } else {
            accuracy = in.readDouble();
        }
        arrivalDate = in.readLong();
        departureDate = in.readLong();
        duration = in.readLong();
        movingDuration = in.readString();
        locationId = in.readInt();
        regionIdentifier = in.readString();
        didEnter = in.readByte() != 0;
        isCurrentPositionInside = in.readByte() != 0;
        idStore = in.readString();
        radius = in.readDouble();
        placeAddress = in.readString();
        placePhoneNo = in.readString();
        placeReportID = in.readInt();
        placeProfileID = in.readString();
        placeLatLngBounds = in.readParcelable(LatLngBounds.class.getClassLoader());
        TypeRegion = in.readString();
    }

    public static final Creator<PlaceData> CREATOR = new Creator<PlaceData>() {
        @Override
        public PlaceData createFromParcel(Parcel in) {
            return new PlaceData(in);
        }

        @Override
        public PlaceData[] newArray(int size) {
            return new PlaceData[size];
        }
    };

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlacePhoneNo() {
        return placePhoneNo;
    }

    public void setPlacePhoneNo(String placePhoneNo) {
        this.placePhoneNo = placePhoneNo;
    }

    public int getPlaceReportID() {
        return placeReportID;
    }

    public void setPlaceReportID(int placeReportID) {
        this.placeReportID = placeReportID;
    }

    public String getPlaceProfileID() {
        return placeProfileID;
    }

    public void setPlaceProfileID(String placeProfileID) {
        this.placeProfileID = placeProfileID;
    }

    public LatLngBounds getPlaceLatLngBounds() {
        return placeLatLngBounds;
    }

    public void setPlaceLatLngBounds(LatLngBounds placeLatLngBounds) {
        this.placeLatLngBounds = placeLatLngBounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(date);
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitude);
        }
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitude);
        }
        if (POILatitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(POILatitude);
        }
        if (POILongitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(POILongitude);
        }
        parcel.writeString(city);
        parcel.writeString(travelingDistance);
        if (distance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(distance);
        }
        parcel.writeString(zipCode);
        if (accuracy == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(accuracy);
        }
        parcel.writeLong(arrivalDate);
        parcel.writeLong(departureDate);
        parcel.writeLong(duration);
        parcel.writeString(movingDuration);
        parcel.writeInt(locationId);
        parcel.writeString(regionIdentifier);
        parcel.writeByte((byte) (didEnter ? 1 : 0));
        parcel.writeByte((byte) (isCurrentPositionInside ? 1 : 0));
        parcel.writeString(idStore);
        parcel.writeDouble(radius);
        parcel.writeString(placeAddress);
        parcel.writeString(placePhoneNo);
        parcel.writeInt(placeReportID);
        parcel.writeString(placeProfileID);
        parcel.writeParcelable(placeLatLngBounds, i);
        parcel.writeString(TypeRegion);
    }

    public String getPlaceDateTime() {
        return placeDateTime;
    }

    public void setPlaceDateTime(String placeDateTime) {
        this.placeDateTime = placeDateTime;
    }

    public EmergencyReport getPlaceDataEmergReport() {
        return placeDataEmergReport;
    }

    public void setPlaceDataEmergReport(EmergencyReport placeDataEmergReport) {
        this.placeDataEmergReport = placeDataEmergReport;
    }

    public enum dataType {
        POI,
        location,
        visit,
        region,
        regionLog,
        ZOI
    }

    private long date;
    private Double latitude;
    private Double longitude;
    private Double POILatitude;
    private Double POILongitude;
    private String city;
    private String travelingDistance;
    private Double distance;
    private String zipCode;
    private dataType type;
    private Double accuracy;
    private long arrivalDate;
    private long departureDate;
    private long duration;
    private String movingDuration;
    private int locationId;
    private String regionIdentifier;
    private boolean didEnter;
    private boolean isCurrentPositionInside;
    private String idStore;
    private double radius;
    private String placeAddress;
    private String placePhoneNo;
    private int placeReportID;
    private String placeProfileID;
    private String placeDateTime;
    private LatLngBounds placeLatLngBounds;
    private EmergencyReport placeDataEmergReport;


    public String getTypeRegion() {
        return TypeRegion;
    }

    public void setTypeRegion(String typeRegion) {
        TypeRegion = typeRegion;
    }

    private String TypeRegion;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPOILatitude() {
        return POILatitude;
    }

    public void setPOILatitude(Double POILatitude) {
        this.POILatitude = POILatitude;
    }

    public Double getPOILongitude() {
        return POILongitude;
    }

    public void setPOILongitude(Double POILongitude) {
        this.POILongitude = POILongitude;
    }

    public String getTravelingDistance() {
        return travelingDistance;
    }

    public void setTravelingDistance(String travelingDistance) {
        this.travelingDistance = travelingDistance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public dataType getType() {
        return type;
    }

    public void setType(dataType type) {
        this.type = type;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(long departureDate) {
        this.departureDate = departureDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getMovingDuration() {
        return movingDuration;
    }

    public void setMovingDuration(String movingDuration) {
        this.movingDuration = movingDuration;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isDidEnter() {
        return didEnter;
    }

    public boolean isCurrentPositionInside() {
        return isCurrentPositionInside;
    }

    public void setDidEnter(boolean didEnter) {
        this.didEnter = didEnter;
    }

    public void setCurrentPositionInside(boolean isCurrentPositionInside) {
        this.isCurrentPositionInside = isCurrentPositionInside;
    }

    public String getRegionIdentifier() {
        return regionIdentifier;
    }

    public void setRegionIdentifier(String regionIdentifier) {
        this.regionIdentifier = regionIdentifier;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }


    public PlaceData(long date, Double latitude, Double longitude, String city, Double distance, String zipCode, dataType type, Double accuracy, long arrivalDate, long departureDate, long duration, String movingDuration, int locationId) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.distance = distance;
        this.zipCode = zipCode;
        this.type = type;
        this.accuracy = accuracy;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.duration = duration;
        this.movingDuration = movingDuration;
        this.locationId = locationId;
    }

    public PlaceData() {
        this.date = 0;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.POILatitude = 0.0;
        this.POILongitude = 0.0;
        this.city = "";
        this.distance = 0.0;
        this.zipCode = "";
        this.type = dataType.location;
        this.accuracy = 0.0;
        this.arrivalDate = 0;
        this.departureDate = 0;
        this.duration = 0;
        this.travelingDistance = "";
        this.movingDuration = "";
        this.locationId = 0;
        this.radius = 0.0;
        this.regionIdentifier = "";
        this.didEnter = false;
        this.isCurrentPositionInside = false;
        this.idStore = "";
    }

    public PlaceData(Region region) {
        this.setType( PlaceData.dataType.region );
        this.setLatitude( region.lat );
        this.setLongitude( region.lng );
        this.setDidEnter( region.didEnter );
        this.setCurrentPositionInside( region.isCurrentPositionInside );
        this.setIdStore(region.idStore);
        this.setRegionIdentifier( region.identifier );
        this.setDate( region.dateTime );
        this.setRadius( region.radius );
    }

    public PlaceData(RegionLog regionLog) {
        this.setType( PlaceData.dataType.regionLog );
        this.setLatitude( regionLog.lat );
        this.setLongitude( regionLog.lng );
        this.setDidEnter( regionLog.didEnter );
        this.setCurrentPositionInside( regionLog.isCurrentPositionInside );
        this.setIdStore( regionLog.idStore );
        this.setRegionIdentifier( regionLog.identifier );
        this.setDate( regionLog.dateTime );
        this.setRadius( regionLog.radius );
        this.setDuration( regionLog.duration );
        this.setTravelingDistance( regionLog.distanceText );
        this.setTypeRegion( regionLog.type );
    }
}
