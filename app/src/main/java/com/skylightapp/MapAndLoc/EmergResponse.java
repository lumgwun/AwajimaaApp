package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_LOCID;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_LOCID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class EmergResponse implements Parcelable, Serializable {

    public static final String RESPONSE_TABLE = "response_Table";
    public static final String RESPONSE_DB_ID = "response_DB_ID";
    public static final String RESPONDER_PROF_ID = "response_Prof_ID";
    public static final String RESPONDER_EMERG_ID = "response_Emerg_ID";
    public static final String RESPONDER_ENEXT_ID = "response_ENext_ID";
    public static final String RESPONDER_DEVICE_ID = "responder_Device_ID";
    public static final String RESPONSE_START_TIME = "response_Start_Time";
    public static final String RESPONSE_END_TIME = "response_End_Time";
    public static final String RESPONDER_ORIGIN = "responder_origin";
    public static final String RESPONDER_A_COUNT = "responder_appear_Count";
    public static final String RESPONSE_STATUS_A = "response_Status";
    public static final String RESPONSE_ID = "responseV_ID";
    public static final String RESPONSE_DATE = "response_Date";
    public static final String RESPONSE_STATE = "response_State";
    public static final String RESPONSE_CATEGORY = "response_Category";

    public static final String CREATE_RESPONSE_TABLE = "CREATE TABLE " + RESPONSE_TABLE + " (" + RESPONSE_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RESPONDER_EMERG_ID + " INTEGER, " + RESPONDER_PROF_ID + " INTEGER, " + RESPONDER_DEVICE_ID + " INTEGER, " + RESPONSE_START_TIME + " TEXT, " + RESPONSE_END_TIME + " TEXT, " + RESPONDER_ORIGIN + " TEXT, " +
            RESPONDER_A_COUNT + " INTEGER, " + RESPONSE_STATUS_A + " TEXT, " + RESPONSE_ID + " INTEGER, "+ RESPONSE_DATE + " TEXT, "+ RESPONSE_STATE + " TEXT, "+ RESPONSE_CATEGORY + " TEXT, "+ "FOREIGN KEY(" + RESPONDER_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + RESPONDER_EMERG_ID + ") REFERENCES " + EMERGENCY_REPORT_TABLE + "(" + EMERGENCY_LOCID + "),"+ "FOREIGN KEY(" + RESPONDER_ENEXT_ID + ") REFERENCES " + EMERGENCY_NEXT_REPORT_TABLE + "(" + EMERGENCY_NEXT_LOCID + "))";


    private String responseStartTime;
    private String responseEndTime;
    private int responseDeviceID;
    private int responseEmergID;
    private int respPersonProfID;
    private String respOriginLatLng;
    private int responseAppearanceCount;
    private boolean itIsSameReport;
    private String responseStatus;
    private int responseID;
    private String responseDate;
    private String responseState;
    private String responseCategory;

    public EmergResponse() {

        super();
    }

    protected EmergResponse(Parcel in) {
        responseStartTime = in.readString();
        responseEndTime = in.readString();
        responseDeviceID = in.readInt();
        responseEmergID = in.readInt();
        respPersonProfID = in.readInt();
        respOriginLatLng = in.readParcelable(LatLng.class.getClassLoader());
        responseAppearanceCount = in.readInt();
        itIsSameReport = in.readByte() != 0;
        responseStatus = in.readString();
    }

    public static final Creator<EmergResponse> CREATOR = new Creator<EmergResponse>() {
        @Override
        public EmergResponse createFromParcel(Parcel in) {
            return new EmergResponse(in);
        }

        @Override
        public EmergResponse[] newArray(int size) {
            return new EmergResponse[size];
        }
    };

    public EmergResponse(int responseID, int emergID, int deviceID, int profileID, String origin, String date,int count, String responseTime,String responseState,String responseCategory, String endTime,String status) {
        this.responseID = responseID;
        this.responseEmergID = emergID;
        this.responseDeviceID = deviceID;
        this.respPersonProfID = profileID;
        this.respOriginLatLng = origin;
        this.responseAppearanceCount = count;
        this.responseStartTime = responseTime;
        this.responseStatus = status;
        this.responseEndTime = endTime;
        this.responseDate = date;
        this.responseState = responseState;
        this.responseCategory = responseCategory;
    }

    public String getResponseStartTime() {
        return responseStartTime;
    }

    public void setResponseStartTime(String responseStartTime) {
        this.responseStartTime = responseStartTime;
    }

    public String getResponseEndTime() {
        return responseEndTime;
    }

    public void setResponseEndTime(String responseEndTime) {
        this.responseEndTime = responseEndTime;
    }

    public int getResponseDeviceID() {
        return responseDeviceID;
    }

    public void setResponseDeviceID(int responseDeviceID) {
        this.responseDeviceID = responseDeviceID;
    }

    public int getResponseEmergID() {
        return responseEmergID;
    }

    public void setResponseEmergID(int responseEmergID) {
        this.responseEmergID = responseEmergID;
    }

    public String getRespOriginLatLng() {
        return respOriginLatLng;
    }

    public void setRespOriginLatLng(String respOriginLatLng) {
        this.respOriginLatLng = respOriginLatLng;
    }

    public int getResponseAppearanceCount() {
        return responseAppearanceCount;
    }

    public void setResponseAppearanceCount(int responseAppearanceCount) {
        this.responseAppearanceCount = responseAppearanceCount;
    }

    public boolean isItIsSameReport() {
        return itIsSameReport;
    }

    public void setItIsSameReport(boolean itIsSameReport) {
        this.itIsSameReport = itIsSameReport;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public int getRespPersonProfID() {
        return respPersonProfID;
    }

    public void setRespPersonProfID(int respPersonProfID) {
        this.respPersonProfID = respPersonProfID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(responseStartTime);
        parcel.writeString(responseEndTime);
        parcel.writeInt(responseDeviceID);
        parcel.writeInt(responseEmergID);
        parcel.writeInt(respPersonProfID);
        parcel.writeString(respOriginLatLng);
        parcel.writeInt(responseAppearanceCount);
        parcel.writeByte((byte) (itIsSameReport ? 1 : 0));
        parcel.writeString(responseStatus);
    }

    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public String getResponseState() {
        return responseState;
    }

    public void setResponseState(String responseState) {
        this.responseState = responseState;
    }

    public String getResponseCategory() {
        return responseCategory;
    }

    public void setResponseCategory(String responseCategory) {
        this.responseCategory = responseCategory;
    }
}
