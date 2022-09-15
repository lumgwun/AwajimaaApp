package com.skylightapp.Classes;

import com.skylightapp.MarketClasses.TimeUtils;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.DailyAccount.DAILY_ACCOUNTING_TABLE;
import static com.skylightapp.Classes.DailyAccount.ID_DA;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class Journey implements Serializable, Comparable<Journey>{

    private int journetID;
    private int journeyProfID;
    private int journeyCusID;
    private int journeyAcctID;
    private String journeyStartDate;
    private String journeyEndDate;
    private String journeyDest;
    private double journeyTotalAmount;
    private String journeyMembers;
    private String journeyStatus;
    private boolean isJourneyIncome;

    public static final String DESTINATION_JOURNEY = "destinationJ";
    public static final String JOURNEY_TABLE = "journey_table";
    public static final String START_DATE_JOURNEY = "start_dateJourney";
    public static final String END_DATE_JOURNEY = "end_dateJourney";
    public static final String TOTAL_AMOUNT_JOURNEY = "total_amountJourney";
    public static final String PERSON_JOURNEY = "personJourney";
    public static final String JOURNEY_ID = "journey_id_Journey";
    public static final String JOURNEY_PROF_ID = "journey_id_Prof";
    public static final String JOURNEY_JADCCT_ID = "journey_id_JAcct";
    public static final String JOURNEY_CUS_ID = "journey_id_Cus";
    public static final String STATUS_JOURNEY = "status_Journey";

    public static final String CREATE_JOURNEY_TABLE = "CREATE TABLE " + JOURNEY_TABLE + "("
            + JOURNEY_ID + " INTEGER ,"
            + START_DATE_JOURNEY + "TEXT NOT NULL,"
            + END_DATE_JOURNEY + "TEXT NOT NULL,"
            + DESTINATION_JOURNEY + "TEXT NOT NULL,"
            + PERSON_JOURNEY + "TEXT NOT NULL,"
            + TOTAL_AMOUNT_JOURNEY + "REAL NOT NULL,"+ STATUS_JOURNEY + "TEXT NOT NULL, " +
            "PRIMARY KEY AUTOINCREMENT UNIQUE(" + JOURNEY_ID + "), "+ "FOREIGN KEY(" + JOURNEY_JADCCT_ID + ") REFERENCES " + DAILY_ACCOUNTING_TABLE + "(" + ID_DA + ")," + "FOREIGN KEY(" + JOURNEY_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"FOREIGN KEY(" + JOURNEY_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";



    public Journey(){
        this.journeyDest = "";
        this.journeyTotalAmount = 0.0;
        this.journeyMembers = "";
        this.journeyStartDate = TimeUtils.today();
        this.journeyEndDate = TimeUtils.today();
    }

    public int getJournetID() {
        return journetID;
    }

    public void setJournetID(int journetID) {
        this.journetID = journetID;
    }

    public String getJourneyDest() {
        return journeyDest;
    }

    public void setJourneyDest(String journeyDest) {
        this.journeyDest = journeyDest;
    }

    public double getJourneyTotalAmount() {
        return journeyTotalAmount;
    }

    public void setJourneyTotalAmount(double journeyTotalAmount) {
        this.journeyTotalAmount = journeyTotalAmount;
    }

    public String getMember() {
        return journeyMembers;
    }

    public void setMember(String members) {
        this.journeyMembers = members;
    }

    public String getJourneyStartDate() {
        return journeyStartDate;
    }

    public void setJourneyStartDate(String journeyStartDate) {
        this.journeyStartDate = journeyStartDate;
    }

    public String getJourneyEndDate() {
        return journeyEndDate;
    }

    public void setJourneyEndDate(String journeyEndDate) {
        this.journeyEndDate = journeyEndDate;
    }

    @Override
    public String toString() {
        return "Destination: " + this.journeyDest + "\n" +
                "Start Date: " + this.journeyStartDate + "\n" +
                "Member: " + this.getMember() + "\n" +
                "Total Amount: " + this.getJourneyTotalAmount();
    }

    public String getSimpleString() {
        return this.journeyDest + this.journeyStartDate + this.journeyMembers + this.journeyTotalAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Journey){
            Journey journey = (Journey)obj;
            return this.journeyStartDate.equals(journey.getJourneyStartDate()) && this.journeyDest.equals(journey.getJourneyDest());
        }
        return false;
    }

    @Override
    public int compareTo(Journey journey) {
        return this.journeyStartDate.compareTo(journey.getJourneyStartDate());
    }

    public String getJourneyStatus() {
        return journeyStatus;
    }

    public void setJourneyStatus(String journeyStatus) {
        this.journeyStatus = journeyStatus;
    }

    public int getJourneyProfID() {
        return journeyProfID;
    }

    public void setJourneyProfID(int journeyProfID) {
        this.journeyProfID = journeyProfID;
    }

    public int getJourneyCusID() {
        return journeyCusID;
    }

    public void setJourneyCusID(int journeyCusID) {
        this.journeyCusID = journeyCusID;
    }

    public int getJourneyAcctID() {
        return journeyAcctID;
    }

    public void setJourneyAcctID(int journeyAcctID) {
        this.journeyAcctID = journeyAcctID;
    }

    public boolean isJourneyIncome() {
        return isJourneyIncome;
    }

    public void setJourneyIncome(boolean journeyIncome) {
        isJourneyIncome = journeyIncome;
    }
}
