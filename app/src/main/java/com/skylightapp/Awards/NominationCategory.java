package com.skylightapp.Awards;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class NominationCategory extends Nomination implements Serializable, Parcelable {
    public static final String NOM_CATEGORY_TABLE = "NCTable";
    public static final String NOMCAT_ID = "NCid";
    public static final String NOM_CATEGORY = "NCategory";
    public static final String NOMINATION_BANNER = "NBanner";

    public static final String CREATE_NOMCAT_TABLE = "CREATE TABLE " + NOM_CATEGORY_TABLE + " (" + NOMCAT_ID + " LONG PRIMARY KEY, " + NOMINATION_ID + " LONG , " +  NOM_CATEGORY +" TEXT,"+  NOMINATION_BANNER +" BLOB,"+  "FOREIGN KEY(" + NOMINATION_ID + ") REFERENCES " + NOMINATION_TABLE + "(" + NOMINATION_ID + "))";



    private long nomCatID;
    private String nominationCategory;
    private Uri nominationBanner;


    public NominationCategory() {

    }

    public NominationCategory(long id, String nomCategory) {
        this.nomCatID = id;
        this.nominationCategory = nomCategory;

    }

    protected NominationCategory(Parcel in) {
        nomCatID = in.readLong();
        nominationCategory = in.readString();
        nominationBanner = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<NominationCategory> CREATOR = new Creator<NominationCategory>() {
        @Override
        public NominationCategory createFromParcel(Parcel in) {
            return new NominationCategory(in);
        }

        @Override
        public NominationCategory[] newArray(int size) {
            return new NominationCategory[size];
        }
    };

    public String getNominatedCategory() { return nominationCategory; }
    public void setNominatedCategory(String nominationCategory) {
        this.nominationCategory = nominationCategory;
    }
    public long getNomCatID() { return nomCatID; }
    public void setNominationStatus(long nomCatID) {
        this.nomCatID = nomCatID;
    }

    public Uri getNominationBanner() { return nominationBanner; }
    public void setNominationBanner(Uri nominationBanner) { this.nominationBanner = nominationBanner; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(nomCatID);
        parcel.writeString(nominationCategory);
        parcel.writeParcelable(nominationBanner, i);
    }
}
