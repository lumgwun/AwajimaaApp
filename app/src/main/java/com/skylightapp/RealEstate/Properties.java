package com.skylightapp.RealEstate;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.Profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class Properties implements Parcelable , Serializable {
    public static final String PROPERTY_TABLE = "PropertyTable";
    public static final String PROPERTY_ID = "P_id";
    public static final String PROPERTY_LINK = "propertyLink";
    public static final String PROPERTY_DESCRIPTION = "property_description";
    public static final String PROPERTY_WARD = "propertyWard";
    public static final String PROPERTY_TOWN = "propertyTown";
    public static final String PROPERTY_LGA = "propertyLGA";
    public static final String PROPERTY_PRICE = "property_Price";
    public static final String PROPERTY_STATUS = "propertyStatus";
    public static final String PROPERTY_TYPE = "propertyType";
    public static final String PROPERTY_AMOUNT = "property_amount";
    public static final String PROPERTY_DATE = "property_date";
    public static final String PROPERTY_LONG = "property_Long";
    public static final String PROPERTY_LAT = "property_Lat";
    public static final String PROPERTY_TYPE_OF_LETTING = "property_Type_Of_Letting";
    public static final String PROPERTY_CAPACITY = "property_capacity";
    public static final String PROPERTY_PRICE_DURATION = "property_Price_Duration";
    public static final String PROPERTY_AGENT_FEE = "property_Agent_Fee";
    public static final String PROPERTY_TITTLE = "property_tittle";
    public static final String PROPERTY_LOCATION = "property_Location";
    public static final String PROPERTY_STATE = "property_State";
    public static final String PROPERTY_COUNTRY = "property_Country";
    public static final String PROPERTY_CUS_ID = "property_Cus_ID";
    public static final String PROPERTY_PROFILE_ID = "property_Prof_Id";




    public static final String CREATE_PROPERTY_TABLE = "CREATE TABLE IF NOT EXISTS " + PROPERTY_TABLE + " (" + PROPERTY_ID + " LONG, " + PROPERTY_PROFILE_ID + " INTEGER , " +
            PROPERTY_CUS_ID + " INTEGER , " + PROPERTY_TITTLE + " TEXT, " +PROPERTY_TYPE_OF_LETTING + " TEXT, " + PROPERTY_DATE + " TEXT, " + PROPERTY_WARD + " TEXT, " +
            PROPERTY_TOWN +  " TEXT, "+ PROPERTY_STATE + " TEXT, " + PROPERTY_CAPACITY + " TEXT, " + PROPERTY_LONG + " TEXT, " + PROPERTY_LAT + " TEXT, " + PROPERTY_LOCATION + " TEXT, " + PROPERTY_LGA + " TEXT, " + PROPERTY_AMOUNT + " INTEGER, " + PROPERTY_PRICE_DURATION + " TEXT, " + PROPERTY_AGENT_FEE + " INTEGER, " +
            PROPERTY_TYPE + " TEXT, " + PROPERTY_LINK + " TEXT, " + PROPERTY_STATUS + " TEXT, "+ PROPERTY_DESCRIPTION + " TEXT, "+ PROPERTY_COUNTRY + " TEXT, " + PROPERTY_PRICE + " INTEGER, " + "PRIMARY KEY(" + PROPERTY_ID + "), " +"FOREIGN KEY(" + PROPERTY_PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + PROPERTY_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


    private Profile profile;
    public String tittleOfProperty;
    public String description;
    public String ward;
    public String town;
    public String lga;
    public Double price;
    public String currency;
    public String rules;
    public String state;

    private String profileId;
    public String typeOfLetting;


    public String status;
    public  int descriptionId;
    public  long propertyID;
    public String propertyType;
    public String propertyCapacity;
    public Double agentFee;
    public String propertyCountry;
    public String priceDuration;
    public Date propertyDate;
    public Uri propertylink;
    public double propertyLongitude;
    public double propertyLat;
    LatLng currentLocation;
    PropertyManager propertyManager;
    private ArrayList<Profile> profiles;
    private ArrayList<PropertyManager> propertyManagers;

    public Properties(long propertyID, String tittleOfProperty, String propertyType, String description, String town, Double price, LatLng currentLocation, String status) {
        this.tittleOfProperty=tittleOfProperty;
        this.propertyID=propertyID;
        this.town=town;
        this.price=price;
        this.status=status;
        this.propertyType=propertyType;
        this.description=description;
        this.currentLocation=currentLocation;
    }

    public Properties(long propertyID, String tittleOfProperty, String propertyType, String description, String town, Double price, Uri propertyLink, LatLng currentLocation, String status) {
        this.tittleOfProperty=tittleOfProperty;
        this.propertyID=propertyID;
        this.town=town;
        this.price=price;
        this.propertylink=propertyLink;
        this.status=status;
        this.propertyType=propertyType;
        this.description=description;
        this.currentLocation=currentLocation;
    }

    public Properties() {

    }

    public Properties(String title, String typeOfLetting, String town, String type, String description,String state, double amount, String priceDuration, Uri propertyImage, String status) {
        this.tittleOfProperty=title;
        this.typeOfLetting=typeOfLetting;
        this.town=town;
        this.priceDuration=priceDuration;
        this.price=amount;
        this.propertyType=type;
        this.state=state;
        this.propertylink=propertyImage;
        this.status=status;
        this.description=description;
    }

    protected Properties(Parcel in) {
        profile = in.readParcelable(Profile.class.getClassLoader());
        tittleOfProperty = in.readString();
        description = in.readString();
        ward = in.readString();
        town = in.readString();
        lga = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        currency = in.readString();
        rules = in.readString();
        state = in.readString();
        profileId = in.readString();
        typeOfLetting = in.readString();
        status = in.readString();
        descriptionId = in.readInt();
        propertyID = in.readLong();
        propertyType = in.readString();
        propertyCapacity = in.readString();
        if (in.readByte() == 0) {
            agentFee = null;
        } else {
            agentFee = in.readDouble();
        }
        propertyCountry = in.readString();
        priceDuration = in.readString();
        propertylink = in.readParcelable(Uri.class.getClassLoader());
        propertyLongitude = in.readDouble();
        propertyLat = in.readDouble();
        currentLocation = in.readParcelable(LatLng.class.getClassLoader());
        propertyManager = in.readParcelable(PropertyManager.class.getClassLoader());
        //profiles = in.createTypedArrayList(Profile.CREATOR);
        //propertyManagers = in.createTypedArrayList(PropertyManager.CREATOR);
    }

    public static final Creator<Properties> CREATOR = new Creator<Properties>() {
        @Override
        public Properties createFromParcel(Parcel in) {
            return new Properties(in);
        }

        @Override
        public Properties[] newArray(int size) {
            return new Properties[size];
        }
    };

    public Properties(int eventCenter, int eventCenter_description, AppCompatActivity aClass) {

    }

    public ArrayList<Profile> getProfiles() { return profiles; }
    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;

    }

    public void setPropertyLongitude(double propertyLongitude) { this.propertyLongitude = propertyLongitude; }
    public double getPropertyLongitude() {
        return propertyLongitude;
    }

    public void setPropertyLat(double propertyLat) { this.propertyLat = propertyLat; }
    public double getPropertyLat() {
        return propertyLat;
    }

    public void addPropertyManager(long profileID, String firstName, String lastName,String phoneNumber, String email, String gender,String state,String username,String identity, String company) {
        String PMNo = "PM" + (propertyManagers.size() + 1);
        PropertyManager propertyManager = new PropertyManager(profileID,firstName,lastName, phoneNumber, email,gender,state,username,identity,company);
        propertyManagers.add(propertyManager);
    }
    public Properties(long propertyID,String tittleOfProperty,String description, Double price,String currentAddress, LatLng currentLocation) {
        this.tittleOfProperty=tittleOfProperty;
        this.propertyID=propertyID;
        this.town=currentAddress;
        this.price=price;
        this.description=description;
        this.currentLocation=currentLocation;

        //activityClass = null;
    }

    public void addPropertyManager(String sharedPrefProfileID) {
        this.profileId = sharedPrefProfileID;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(profile, i);
        parcel.writeString(tittleOfProperty);
        parcel.writeString(description);
        parcel.writeString(ward);
        parcel.writeString(town);
        parcel.writeString(lga);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeString(currency);
        parcel.writeString(rules);
        parcel.writeString(state);
        parcel.writeString(profileId);
        parcel.writeString(typeOfLetting);
        parcel.writeString(status);
        parcel.writeInt(descriptionId);
        parcel.writeLong(propertyID);
        parcel.writeString(propertyType);
        parcel.writeString(propertyCapacity);
        if (agentFee == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(agentFee);
        }
        parcel.writeString(propertyCountry);
        parcel.writeString(priceDuration);
        parcel.writeParcelable(propertylink, i);
        parcel.writeDouble(propertyLongitude);
        parcel.writeDouble(propertyLat);
        parcel.writeParcelable(currentLocation, i);
        parcel.writeParcelable(propertyManager, i);
        parcel.writeTypedList(profiles);
        parcel.writeTypedList(propertyManagers);
    }

    public enum PROPERTY_TYPE {
        RESIDENTIAL, OFFICE, INDUSTRIAL,
        ESTATE,
        HALL,BORROWING,
        RETURNS;
    }
    public Properties(long propertyID, long profileId, String tittleOfProperty, String description, String propertyType, String town, String lga, Double price, String priceDuration, String propertyCapacity, String typeOfLetting,Date propertyDate,Uri propertylink,String status) {
        this.propertyID = propertyID;
        this.tittleOfProperty = tittleOfProperty;
        this.description = description;
        this.propertyType = propertyType;
        this.town = town;
        this.lga = lga;
        this.price = price;
        this.priceDuration = priceDuration;
        this.propertyCapacity = propertyCapacity;
        this.propertyDate = propertyDate;
        this.typeOfLetting = typeOfLetting;
        this.status = status;
        this.propertylink = propertylink;


        //activityClass = null;
    }

    public String getTittleOfProperty() {
        return tittleOfProperty;
    }
    public void setTittleOfProperty(String tittleOfProperty) { this.tittleOfProperty = tittleOfProperty; }

    public String getPropertyState() {
        return state;
    }
    public void setPropertyState(String state) { this.state = state; }

    public String getPropertyCountry() {
        return propertyCountry;
    }
    public void setPropertyCountry(String propertyCountry) { this.propertyCountry = propertyCountry; }


    public Date getPropertyDate() {
        return propertyDate;
    }
    public void setPropertyDate(Date propertyDate) { this.propertyDate = propertyDate; }

    public Uri getPropertylink() {
        return propertylink;
    }
    public void setPropertylink(Uri propertylink) { this.propertylink = propertylink; }



    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }
    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyId(long propertyID) { this.propertyID = propertyID; }
    public long getPropertyID() {
        return propertyID;
    }




    public void setPropertyLocation(LatLng currentLocation) { this.currentLocation = currentLocation; }
    public LatLng getPropertyLocation() {
        return currentLocation;
    }

    public void setPropertyManager(PropertyManager propertyManager) { this.propertyManager = propertyManager; }
    public PropertyManager getPropertyManager() {
        return propertyManager;
    }



    public void setPropertyCapacity(String propertyCapacity) { this.propertyCapacity = propertyCapacity; }
    public String getPropertyCapacity() {
        return propertyCapacity;
    }

    public void setTypeOfLetting(String typeOfLetting) { this.typeOfLetting = typeOfLetting; }
    public String getTypeOfLetting() {
        return typeOfLetting;
    }



    public void setAgentFee(Double agentFee) { this.agentFee = agentFee; }
    public Double getAgentFee() {
        return agentFee;
    }

    public void setPriceDuration(String priceDuration) { this.priceDuration = priceDuration; }
    public String getPriceDuration() {
        return priceDuration;
    }

    public void setPrice(Double price) { this.price = price; }
    public Double getPrice() {
        return price;
    }


    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) { this.currency = currency; }


    public void setLga(String lga) { this.lga = lga; }
    public String getLga() {
        return lga;
    }

    public void setProfile(Profile profile) { this.profile = profile; }
    public Profile getProfile() {
        return profile;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) { this.status = status; }

    public void setWard(String ward) { this.ward = ward; }
    public String getWard() {
        return ward;
    }
    public void setTown(String town) { this.town = town; }
    public String getTown() {
        return town;
    }
    public String getRules() {
        return rules;
    }
    public void setRules(String rules) { this.rules = rules; }
    //public final Class<? extends AppCompatActivity> activityClass;

    public Properties(
            int titleId, int descriptionId, java.lang.Class activityClass) {
        this.propertyID = titleId;
        this.descriptionId = descriptionId;
        //this.activityClass = activityClass;
    }
}
