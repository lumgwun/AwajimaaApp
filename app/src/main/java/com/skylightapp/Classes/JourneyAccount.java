package com.skylightapp.Classes;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class JourneyAccount extends DailyAccount implements Serializable {

    private int journeyId;
    private String journeyPerson;
    private String jAStatus;
    private String jACreatedTime;
    private String jAContent;
    private double jAAmount;
    private String jACurrency;
    private int jAProfID;
    private int jAAcctID;
    private int jACusID;
    private boolean isJACome;

    public static final String JOURNEY_ACCOUNT_TABLE = "journey_account_table";
    public static final String JOURNEY_ACCT_PERSON = "personJAcct";
    public static final String JOURNEY_ACCT_ID = "journey_Acct_id";
    public static final String JOURNEY_ACCT_CONTENT = "journey_Acct_id";
    public static final String JOURNEY_ACCT_AMOUNT = "journey_Acct_id";
    public static final String JOURNEY_ACCT_CURRENCY = "journey_Acct_id";
    public static final String JOURNEY_ACCT_CREATED_TIME = "journey_Acct_Time";
    public static final String JOURNEY_ACCT_DACCT_ID = "journey_Acct_Acct_ID";
    public static final String JOURNEY_ACCT_PROF_ID = "journey_Acct_Prof_ID";
    public static final String JOURNEY_ACCT_CUS_ID = "journey_Acct_Cus_ID";
    public static final String JOURNEY_ACCT_STATUS = "journey_Acct_Status";

    public static final String CREATE_JOURNEY_ACCOUNT_TABLE = "CREATE TABLE " + JOURNEY_ACCOUNT_TABLE + "("
            + JOURNEY_ACCT_ID + " INTEGER ,"
            + JOURNEY_ACCT_CREATED_TIME + " TEXT NOT NULL,"
            + JOURNEY_ACCT_CURRENCY + " TEXT NOT NULL,"
            + JOURNEY_ACCT_AMOUNT + " REAL NOT NULL,"
            + JOURNEY_ACCT_CONTENT + " TEXT,"
            + JOURNEY_ACCT_PERSON + "TEXT NOT NULL,"
            + JOURNEY_ACCT_DACCT_ID + " INTEGER , "+ JOURNEY_ACCT_PROF_ID + " INTEGER , " + JOURNEY_ACCT_CUS_ID + " INTEGER ,"+ JOURNEY_ACCT_STATUS + " INTEGER ,"+"FOREIGN KEY(" + JOURNEY_ACCT_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + JOURNEY_ACCT_DACCT_ID + ") REFERENCES " + DAILY_ACCOUNTING_TABLE + "(" + ID_DA + ")," + "FOREIGN KEY(" + JOURNEY_ACCT_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," +
            "PRIMARY KEY AUTOINCREMENT UNIQUE(" + JOURNEY_ACCT_ID + "))";



    public JourneyAccount(){
        super();
        journeyPerson = "";
    }

    public JourneyAccount(int journeyId){
        super();
        this.journeyId = journeyId;
    }

    public String getJourneyPerson(){
        return journeyPerson;
    }

    public void setJourneyPerson(String journeyPerson){
        this.journeyPerson = journeyPerson;
    }

    public int getJourneyId(){
        return journeyId;
    }

    public void setJourneyId(int journeyId){
        this.journeyId = journeyId;
    }

    public String getjAStatus() {
        return jAStatus;
    }

    public void setjAStatus(String jAStatus) {
        this.jAStatus = jAStatus;
    }

    public String getjACreatedTime() {
        return jACreatedTime;
    }

    public void setjACreatedTime(String jACreatedTime) {
        this.jACreatedTime = jACreatedTime;
    }

    public String getjAContent() {
        return jAContent;
    }

    public void setjAContent(String jAContent) {
        this.jAContent = jAContent;
    }

    public double getjAAmount() {
        return jAAmount;
    }

    public void setjAAmount(double jAAmount) {
        this.jAAmount = jAAmount;
    }

    public String getjACurrency() {
        return jACurrency;
    }

    public void setjACurrency(String jACurrency) {
        this.jACurrency = jACurrency;
    }

    public int getjAProfID() {
        return jAProfID;
    }

    public void setjAProfID(int jAProfID) {
        this.jAProfID = jAProfID;
    }

    public int getjAAcctID() {
        return jAAcctID;
    }

    public void setjAAcctID(int jAAcctID) {
        this.jAAcctID = jAAcctID;
    }

    public int getjACusID() {
        return jACusID;
    }

    public void setjACusID(int jACusID) {
        this.jACusID = jACusID;
    }


    public boolean isJACome() {
        return isJACome;
    }

    public void setJACome(boolean JACome) {
        isJACome = JACome;
    }
}
