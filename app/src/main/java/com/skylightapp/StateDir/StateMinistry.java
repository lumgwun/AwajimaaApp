package com.skylightapp.StateDir;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.StateDir.State.STATE_ID;
import static com.skylightapp.StateDir.State.STATE_TABLE;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.model.QBEntity;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.Housing;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.MapAndLoc.EmergResponse;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MarketClasses.MarketBizRegulator;
import com.skylightapp.MarketClasses.MarketBizSub;

import java.io.Serializable;
import java.util.ArrayList;

public class StateMinistry implements Parcelable, Serializable, BaseColumns {
    public static final String SM_SUB_ID = "sm_Sub_ID";
    public static final String SM_CUS_ID = "sm_Cus_ID";
    public static final String SM_PROF_ID = "sm_Prof_ID";
    public static final String SM_STATUS = "sm_Status";
    public static final String SM_PASSCODE = "sm_Password";
    public static final String SM_ROLE = "sm_Role";
    public static final String SM_TYPE = "sm_type";
    public static final String SM_COUNTRY = "sm_country";
    public static final String SM_NAME = "sm_name";
    public static final String SM_ID = "sm_id";
    public static final String SM_DB_ID = "sm_DB_Id";
    public static final String STATE_MINISTRY_TABLE = "state_ministry_Table";
    public static final String SM_PICTURE = "sm_Picture";
    public static final String SM_STATE_ID = "sm_state_ID";

    public static final String CREATE_STATE_MIN_TABLE = "CREATE TABLE " + STATE_MINISTRY_TABLE + " (" + SM_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SM_ID + " INTEGER, " + SM_STATE_ID + " INTEGER , " +
            SM_PICTURE + " BLOB ,"+ SM_PROF_ID + " INTEGER, "+ SM_CUS_ID + " INTEGER, "+ SM_SUB_ID + " INTEGER, " + SM_NAME + " TEXT, " + SM_COUNTRY + " TEXT, " + SM_PASSCODE + " TEXT, " + SM_TYPE + " TEXT, " + SM_STATUS + " TEXT, " + SM_ROLE + " TEXT, "+"FOREIGN KEY(" + SM_STATE_ID  + ") REFERENCES " + STATE_TABLE + "(" + STATE_ID + "),"+"FOREIGN KEY(" + SM_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + SM_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    private int sMStateID;
    private int sMID;
    private String sMType;
    private String sMRole;
    private String sMStatus;
    private String sMPassCode;
    private Uri sMPicture;
    private State sMState;
    private Profile sMProfile;
    private Customer sMCustomer;
    private ArrayList<MarketBizSub> sMScriptions;
    private ArrayList<Profile> sMProfiles;
    private ArrayList<Customer> sMCustomers;
    private ArrayList<Transaction> sMTranxS;
    private ArrayList<EmergencyReport> sMEmergReports;
    private ArrayList<EmergResponse> sMEmergResponses;
    private ArrayList<MarketBizRegulator> sMRegulators;
    private ArrayList<Message> sMMessages;
    private ArrayList<QBChatDialog> sMQBChatDialogs;
    private QBUser sMQbUser;
    private QBEntity sMQbEntity;
    private ArrayList<QBUser> sMQBUsers;
    private ArrayList<GroupSavings> sMGrpSavings;
    private ArrayList<StateMinistry> sMMinistries;
    private ArrayList<Housing> sMHousing;

    protected StateMinistry(Parcel in) {
        sMStateID = in.readInt();
        sMID = in.readInt();
        sMType = in.readString();
        sMRole = in.readString();
        sMStatus = in.readString();
        sMPassCode = in.readString();
        sMPicture = in.readParcelable(Uri.class.getClassLoader());
        sMState = in.readParcelable(State.class.getClassLoader());
        sMProfile = in.readParcelable(Profile.class.getClassLoader());
        sMCustomer = in.readParcelable(Customer.class.getClassLoader());
        sMProfiles = in.createTypedArrayList(Profile.CREATOR);
        sMCustomers = in.createTypedArrayList(Customer.CREATOR);
        sMTranxS = in.createTypedArrayList(Transaction.CREATOR);
        sMEmergReports = in.createTypedArrayList(EmergencyReport.CREATOR);
        sMEmergResponses = in.createTypedArrayList(EmergResponse.CREATOR);
        sMMessages = in.createTypedArrayList(Message.CREATOR);
        sMGrpSavings = in.createTypedArrayList(GroupSavings.CREATOR);
        sMMinistries = in.createTypedArrayList(StateMinistry.CREATOR);
    }

    public static final Creator<StateMinistry> CREATOR = new Creator<StateMinistry>() {
        @Override
        public StateMinistry createFromParcel(Parcel in) {
            return new StateMinistry(in);
        }

        @Override
        public StateMinistry[] newArray(int size) {
            return new StateMinistry[size];
        }
    };

    public int getsMStateID() {
        return sMStateID;
    }

    public void setsMStateID(int sMStateID) {
        this.sMStateID = sMStateID;
    }

    public int getsMID() {
        return sMID;
    }

    public void setsMID(int sMID) {
        this.sMID = sMID;
    }

    public String getsMType() {
        return sMType;
    }

    public void setsMType(String sMType) {
        this.sMType = sMType;
    }

    public String getsMRole() {
        return sMRole;
    }

    public void setsMRole(String sMRole) {
        this.sMRole = sMRole;
    }

    public String getsMStatus() {
        return sMStatus;
    }

    public void setsMStatus(String sMStatus) {
        this.sMStatus = sMStatus;
    }

    public String getsMPassCode() {
        return sMPassCode;
    }

    public void setsMPassCode(String sMPassCode) {
        this.sMPassCode = sMPassCode;
    }

    public Uri getsMPicture() {
        return sMPicture;
    }

    public void setsMPicture(Uri sMPicture) {
        this.sMPicture = sMPicture;
    }

    public State getsMState() {
        return sMState;
    }

    public void setsMState(State sMState) {
        this.sMState = sMState;
    }

    public Profile getsMProfile() {
        return sMProfile;
    }

    public void setsMProfile(Profile sMProfile) {
        this.sMProfile = sMProfile;
    }

    public Customer getsMCustomer() {
        return sMCustomer;
    }

    public void setsMCustomer(Customer sMCustomer) {
        this.sMCustomer = sMCustomer;
    }

    public ArrayList<MarketBizSub> getsMScriptions() {
        return sMScriptions;
    }

    public void setsMScriptions(ArrayList<MarketBizSub> sMScriptions) {
        this.sMScriptions = sMScriptions;
    }

    public ArrayList<Profile> getsMProfiles() {
        return sMProfiles;
    }

    public void setsMProfiles(ArrayList<Profile> sMProfiles) {
        this.sMProfiles = sMProfiles;
    }

    public ArrayList<Customer> getsMCustomers() {
        return sMCustomers;
    }

    public void setsMCustomers(ArrayList<Customer> sMCustomers) {
        this.sMCustomers = sMCustomers;
    }

    public ArrayList<Transaction> getsMTranxS() {
        return sMTranxS;
    }

    public void setsMTranxS(ArrayList<Transaction> sMTranxS) {
        this.sMTranxS = sMTranxS;
    }

    public ArrayList<EmergencyReport> getsMEmergReports() {
        return sMEmergReports;
    }

    public void setsMEmergReports(ArrayList<EmergencyReport> sMEmergReports) {
        this.sMEmergReports = sMEmergReports;
    }

    public ArrayList<EmergResponse> getsMEmergResponses() {
        return sMEmergResponses;
    }

    public void setsMEmergResponses(ArrayList<EmergResponse> sMEmergResponses) {
        this.sMEmergResponses = sMEmergResponses;
    }

    public ArrayList<MarketBizRegulator> getsMRegulators() {
        return sMRegulators;
    }

    public void setsMRegulators(ArrayList<MarketBizRegulator> sMRegulators) {
        this.sMRegulators = sMRegulators;
    }

    public ArrayList<Message> getsMMessages() {
        return sMMessages;
    }

    public void setsMMessages(ArrayList<Message> sMMessages) {
        this.sMMessages = sMMessages;
    }

    public ArrayList<QBChatDialog> getsMQBChatDialogs() {
        return sMQBChatDialogs;
    }

    public void setsMQBChatDialogs(ArrayList<QBChatDialog> sMQBChatDialogs) {
        this.sMQBChatDialogs = sMQBChatDialogs;
    }

    public QBUser getsMQbUser() {
        return sMQbUser;
    }

    public void setsMQbUser(QBUser sMQbUser) {
        this.sMQbUser = sMQbUser;
    }

    public QBEntity getsMQbEntity() {
        return sMQbEntity;
    }

    public void setsMQbEntity(QBEntity sMQbEntity) {
        this.sMQbEntity = sMQbEntity;
    }

    public ArrayList<QBUser> getsMQBUsers() {
        return sMQBUsers;
    }

    public void setsMQBUsers(ArrayList<QBUser> sMQBUsers) {
        this.sMQBUsers = sMQBUsers;
    }

    public ArrayList<GroupSavings> getsMGrpSavings() {
        return sMGrpSavings;
    }

    public void setsMGrpSavings(ArrayList<GroupSavings> sMGrpSavings) {
        this.sMGrpSavings = sMGrpSavings;
    }

    public ArrayList<StateMinistry> getsMMinistries() {
        return sMMinistries;
    }

    public void setsMMinistries(ArrayList<StateMinistry> sMMinistries) {
        this.sMMinistries = sMMinistries;
    }

    public ArrayList<Housing> getsMHousing() {
        return sMHousing;
    }

    public void setsMHousing(ArrayList<Housing> sMHousing) {
        this.sMHousing = sMHousing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(sMStateID);
        parcel.writeInt(sMID);
        parcel.writeString(sMType);
        parcel.writeString(sMRole);
        parcel.writeString(sMStatus);
        parcel.writeString(sMPassCode);
        parcel.writeParcelable(sMPicture, i);
        parcel.writeParcelable(sMState, i);
        parcel.writeParcelable(sMProfile, i);
        parcel.writeParcelable(sMCustomer, i);
        parcel.writeTypedList(sMProfiles);
        parcel.writeTypedList(sMCustomers);
        parcel.writeTypedList(sMTranxS);
        parcel.writeTypedList(sMEmergReports);
        parcel.writeTypedList(sMEmergResponses);
        parcel.writeTypedList(sMMessages);
        parcel.writeTypedList(sMGrpSavings);
        parcel.writeTypedList(sMMinistries);
    }
}
