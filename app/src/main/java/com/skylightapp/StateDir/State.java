package com.skylightapp.StateDir;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.model.QBEntity;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Bookings.TaxiDriver;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.Housing;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.MapAndLoc.EmergResponse;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketBizRegulator;
import com.skylightapp.MarketClasses.MarketBizSub;
import com.skylightapp.MarketClasses.MarketBusiness;

import java.io.Serializable;
import java.util.ArrayList;

public class State implements Parcelable, Serializable, BaseColumns {
    public static final String STATE_SUB_ID = "state_Sub_ID";
    public static final String STATE_CUS_ID = "state_Cus_ID";
    public static final String STATE_PROF_ID = "state_Prof_ID";
    public static final String STATE_STATUS = "state_Status";
    public static final String STATE_MIN_PASSCODE = "state_min_Password";
    public static final String STATE_MIN_ROLE = "state_min_Role";
    public static final String STATE_TYPE = "state_type";
    public static final String STATE_COUNTRY = "state_country";
    public static final String STATE_NAME = "state_name";
    public static final String STATE_ID = "state_id";
    public static final String STATE_DB_ID = "state_DB_Id";
    public static final String STATE_TRANX_ID = "state_Tranx_ID";
    public static final String STATE_TABLE = "state_Table";
    public static final String STATE_PICTURE = "state_Picture";

    public static final String CREATE_STATE_TABLE = "CREATE TABLE " + STATE_TABLE + " (" + STATE_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STATE_ID + " INTEGER, " + STATE_PROF_ID + " INTEGER , " +
            STATE_PICTURE + " BLOB ,"+ STATE_CUS_ID + " INTEGER, "+ STATE_SUB_ID + " INTEGER, " + STATE_NAME + " TEXT, " + STATE_COUNTRY + " TEXT, " + STATE_MIN_PASSCODE + " TEXT, " + STATE_MIN_ROLE + " TEXT, " + STATE_STATUS + " TEXT, " + STATE_TYPE + " TEXT, "+"FOREIGN KEY(" + STATE_TRANX_ID  + ") REFERENCES " + TRANSACTIONS_TABLE + "(" + TRANSACTION_ID + "),"+"FOREIGN KEY(" + STATE_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + STATE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


    private int stateID;
    private int stateCusID;
    private int stateProfID;
    private int stateTranxID;
    private String stateName;
    private String stateCountry;
    private String stateStatus;
    private String stateMinType;
    private String stateMinRole;
    private String stateMinPassword;
    private ArrayList<MarketBizSub> stateScriptions;
    private ArrayList<String> stateRoles;
    private ArrayList<Profile> stateProfiles;
    private ArrayList<Customer> stateCustomers;
    private ArrayList<Transaction> stateTranxS;
    private ArrayList<EmergencyReport> stateEmergReports;
    private ArrayList<EmergResponse> stateEmergResponses;
    private ArrayList<TaxiDriver> stateTaxiDrivers;
    private ArrayList<MarketBizRegulator> stateRegulators;
    private ArrayList<Message> stateMessages;
    private ArrayList<QBChatDialog> stateQBChatDialogs;
    private ArrayList<Market> stateMarkets;
    private ArrayList<MarketBusiness> stateMarketBizS;
    private transient boolean state_Authed = false;
    private QBUser stateQbUser;
    private QBEntity stateQbEntity;
    private ArrayList<QBUser> stateQBUsers;
    private ArrayList<GroupSavings> stateGrpSavings;
    private ArrayList<GroupAccount> stateGrpAccounts;
    private ArrayList<StateMinistry> stateMinistries;
    private ArrayList<Housing> stateHousing;
    public State() {
        super();
    }

    protected State(Parcel in) {
        stateID = in.readInt();
        stateCusID = in.readInt();
        stateProfID = in.readInt();
        stateTranxID = in.readInt();
        stateName = in.readString();
        stateCountry = in.readString();
        stateStatus = in.readString();
        stateMinType = in.readString();
        stateMinRole = in.readString();
        stateMinPassword = in.readString();
        stateRoles = in.createStringArrayList();
        stateProfiles = in.createTypedArrayList(Profile.CREATOR);
        stateCustomers = in.createTypedArrayList(Customer.CREATOR);
        stateTranxS = in.createTypedArrayList(Transaction.CREATOR);
        stateEmergReports = in.createTypedArrayList(EmergencyReport.CREATOR);
        stateEmergResponses = in.createTypedArrayList(EmergResponse.CREATOR);
        stateTaxiDrivers = in.createTypedArrayList(TaxiDriver.CREATOR);
        stateMessages = in.createTypedArrayList(Message.CREATOR);
        stateMarkets = in.createTypedArrayList(Market.CREATOR);
        stateMarketBizS = in.createTypedArrayList(MarketBusiness.CREATOR);
        stateGrpSavings = in.createTypedArrayList(GroupSavings.CREATOR);
        stateGrpAccounts = in.createTypedArrayList(GroupAccount.CREATOR);
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

    public ArrayList<MarketBizSub> getStateScriptions() {
        return stateScriptions;
    }

    public void setStateScriptions(ArrayList<MarketBizSub> stateScriptions) {
        this.stateScriptions = stateScriptions;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCountry() {
        return stateCountry;
    }

    public void setStateCountry(String stateCountry) {
        this.stateCountry = stateCountry;
    }

    public String getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(String stateStatus) {
        this.stateStatus = stateStatus;
    }

    public String getStateMinType() {
        return stateMinType;
    }

    public void setStateMinType(String stateMinType) {
        this.stateMinType = stateMinType;
    }

    public String getStateMinRole() {
        return stateMinRole;
    }

    public void setStateMinRole(String stateMinRole) {
        this.stateMinRole = stateMinRole;
    }

    public String getStateMinPassword() {
        return stateMinPassword;
    }

    public void setStateMinPassword(String stateMinPassword) {
        this.stateMinPassword = stateMinPassword;
    }

    public int getStateCusID() {
        return stateCusID;
    }

    public void setStateCusID(int stateCusID) {
        this.stateCusID = stateCusID;
    }

    public int getStateProfID() {
        return stateProfID;
    }

    public void setStateProfID(int stateProfID) {
        this.stateProfID = stateProfID;
    }

    public int getStateTranxID() {
        return stateTranxID;
    }

    public void setStateTranxID(int stateTranxID) {
        this.stateTranxID = stateTranxID;
    }

    public ArrayList<String> getStateRoles() {
        return stateRoles;
    }

    public void setStateRoles(ArrayList<String> stateRoles) {
        this.stateRoles = stateRoles;
    }

    public ArrayList<Profile> getStateProfiles() {
        return stateProfiles;
    }

    public void setStateProfiles(ArrayList<Profile> stateProfiles) {
        this.stateProfiles = stateProfiles;
    }

    public ArrayList<Customer> getStateCustomers() {
        return stateCustomers;
    }

    public void setStateCustomers(ArrayList<Customer> stateCustomers) {
        this.stateCustomers = stateCustomers;
    }

    public ArrayList<Transaction> getStateTranxS() {
        return stateTranxS;
    }

    public void setStateTranxS(ArrayList<Transaction> stateTranxS) {
        this.stateTranxS = stateTranxS;
    }

    public ArrayList<EmergencyReport> getStateEmergReports() {
        return stateEmergReports;
    }

    public void setStateEmergReports(ArrayList<EmergencyReport> stateEmergReports) {
        this.stateEmergReports = stateEmergReports;
    }

    public ArrayList<EmergResponse> getStateEmergResponses() {
        return stateEmergResponses;
    }

    public void setStateEmergResponses(ArrayList<EmergResponse> stateEmergResponses) {
        this.stateEmergResponses = stateEmergResponses;
    }

    public ArrayList<TaxiDriver> getStateTaxiDrivers() {
        return stateTaxiDrivers;
    }

    public void setStateTaxiDrivers(ArrayList<TaxiDriver> stateTaxiDrivers) {
        this.stateTaxiDrivers = stateTaxiDrivers;
    }

    public ArrayList<MarketBizRegulator> getStateRegulators() {
        return stateRegulators;
    }

    public void setStateRegulators(ArrayList<MarketBizRegulator> stateRegulators) {
        this.stateRegulators = stateRegulators;
    }

    public ArrayList<Message> getStateMessages() {
        return stateMessages;
    }

    public void setStateMessages(ArrayList<Message> stateMessages) {
        this.stateMessages = stateMessages;
    }

    public ArrayList<QBChatDialog> getStateQBChatDialogs() {
        return stateQBChatDialogs;
    }

    public void setStateQBChatDialogs(ArrayList<QBChatDialog> stateQBChatDialogs) {
        this.stateQBChatDialogs = stateQBChatDialogs;
    }

    public boolean isState_Authed() {
        return state_Authed;
    }

    public void setState_Authed(boolean state_Authed) {
        this.state_Authed = state_Authed;
    }

    public ArrayList<Market> getStateMarkets() {
        return stateMarkets;
    }

    public void setStateMarkets(ArrayList<Market> stateMarkets) {
        this.stateMarkets = stateMarkets;
    }

    public ArrayList<MarketBusiness> getStateMarketBizS() {
        return stateMarketBizS;
    }

    public void setStateMarketBizS(ArrayList<MarketBusiness> stateMarketBizS) {
        this.stateMarketBizS = stateMarketBizS;
    }

    public QBUser getStateQbUser() {
        return stateQbUser;
    }

    public void setStateQbUser(QBUser stateQbUser) {
        this.stateQbUser = stateQbUser;
    }

    public QBEntity getStateQbEntity() {
        return stateQbEntity;
    }

    public void setStateQbEntity(QBEntity stateQbEntity) {
        this.stateQbEntity = stateQbEntity;
    }

    public ArrayList<QBUser> getStateQBUsers() {
        return stateQBUsers;
    }

    public void setStateQBUsers(ArrayList<QBUser> stateQBUsers) {
        this.stateQBUsers = stateQBUsers;
    }

    public ArrayList<GroupSavings> getStateGrpSavings() {
        return stateGrpSavings;
    }

    public void setStateGrpSavings(ArrayList<GroupSavings> stateGrpSavings) {
        this.stateGrpSavings = stateGrpSavings;
    }

    public ArrayList<GroupAccount> getStateGrpAccounts() {
        return stateGrpAccounts;
    }

    public void setStateGrpAccounts(ArrayList<GroupAccount> stateGrpAccounts) {
        this.stateGrpAccounts = stateGrpAccounts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(stateID);
        parcel.writeInt(stateCusID);
        parcel.writeInt(stateProfID);
        parcel.writeInt(stateTranxID);
        parcel.writeString(stateName);
        parcel.writeString(stateCountry);
        parcel.writeString(stateStatus);
        parcel.writeString(stateMinType);
        parcel.writeString(stateMinRole);
        parcel.writeString(stateMinPassword);
        parcel.writeStringList(stateRoles);
        parcel.writeTypedList(stateProfiles);
        parcel.writeTypedList(stateCustomers);
        parcel.writeTypedList(stateTranxS);
        parcel.writeTypedList(stateEmergReports);
        parcel.writeTypedList(stateEmergResponses);
        parcel.writeTypedList(stateTaxiDrivers);
        parcel.writeTypedList(stateMessages);
        parcel.writeTypedList(stateMarkets);
        parcel.writeTypedList(stateMarketBizS);
        parcel.writeTypedList(stateGrpSavings);
        parcel.writeTypedList(stateGrpAccounts);
    }

    public ArrayList<StateMinistry> getStateMinistries() {
        return stateMinistries;
    }

    public void setStateMinistries(ArrayList<StateMinistry> stateMinistries) {
        this.stateMinistries = stateMinistries;
    }

    public ArrayList<Housing> getStateHousing() {
        return stateHousing;
    }

    public void setStateHousing(ArrayList<Housing> stateHousing) {
        this.stateHousing = stateHousing;
    }
}
