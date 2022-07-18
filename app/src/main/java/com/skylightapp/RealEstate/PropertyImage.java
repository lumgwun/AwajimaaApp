package com.skylightapp.RealEstate;

import android.net.Uri;

import com.skylightapp.Classes.Profile;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class PropertyImage extends  Properties{
    public static final String PROPERTY_PICTURE = "property_picture";
    public static final String PROPERTY_PICTURE_ID = "property_picture_ID";
    public static final String PROPERTY_PICTURE_TITTLE = "property_pix_Tittle";
    public static final String PROPERTY_PICTURE_STATUS = "property_pix_Status";
    public static final String PROPERTY_PICTURE_TABLE = "PropertyTable";
    public static final String PROPERTY_PICTURE_PROF_ID = "Property_PROF_ID";
    public static final String PROPERTY_PICTURE_PROP_ID = "Prop_Prop_ID";

    public static final String CREATE_PROPERTY_PICTURE_TABLE = "CREATE TABLE IF NOT EXISTS " + PROPERTY_PICTURE_TABLE + " (" + PROPERTY_PICTURE_ID + " INTEGER, " + PROPERTY_ID + " LONG , " +PROPERTY_PICTURE_PROF_ID + " INTEGER , " +
            PROPERTY_PICTURE   +  " BLOB, " + PROPERTY_PICTURE_TITTLE + " TEXT, " + PROPERTY_PICTURE_STATUS + " TEXT, " + "PRIMARY KEY(" +PROPERTY_PICTURE_ID+"), " +"FOREIGN KEY(" + PROPERTY_PICTURE_PROP_ID  + ") REFERENCES " + PROPERTY_TABLE + "(" + PROPERTY_ID + ")," +"FOREIGN KEY(" + PROPERTY_PICTURE_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private Profile profile;
    private String tittleOfPropImage;
    private String imageDescrip;
    private Uri imageUri;
    long propertyImageID;

    public PropertyImage(long propertyImageID,long propertyID,String tittleOfPropImage) {
        this.tittleOfPropImage=tittleOfPropImage;
        this.propertyImageID=propertyImageID;
        this.propertyID=propertyID;
    }

    public PropertyImage() {

    }

    public String getTittleOfPropImage() {
        return tittleOfPropImage;
    }
    public void setTittleOfPropImage(String tittleOfPropImage) { this.tittleOfPropImage = tittleOfPropImage; }

    public String getImageDescrip() {
        return imageDescrip;
    }
    public void setImageDescrip(String imageDescrip) { this.imageDescrip = imageDescrip; }
    public long getPropertyImageID() {
        return propertyImageID;
    }
    public void setPropertyImageID(long propertyImageID) { this.propertyImageID = propertyImageID; }

    public Uri getImageUri() {
        return imageUri;
    }
    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }
}
