package com.skylightapp.MarketClasses;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_NAME;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;

import android.net.Uri;

import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;

import java.util.ArrayList;

public class BizDealPartner {

    public static final String BDEAL_PARTNER_TABLE = "biz_Deal_Partner_Table";
    public static final String BDEAL_PARTNER_DBID = "biz_Deal_Partner_DB_id";
    public static final String BDEAL_PARTNER_NAME = "biz_Deal_PartnerName";
    public static final String BDEAL_PARTNER_REG_NO = "biz_Deal_Partner_RegNo";
    public static final String BDEAL_PARTNER_BRAND_NAME = "biz_Deal_P__BrandName";
    public static final String BDEAL_PARTNER_MARKET_NAME = "biz_Deal_Partner_marketName";
    public static final String BDEAL_PARTNER_GENDER = "biz_Deal_Partner_Gender";
    public static final String BDEAL_PARTNER_STATUS = "biz_Deal_Partner_Status";
    public static final String BDEAL_PARTNER_COUNTRY = "biz_Deal_Partner_Country";
    public static final String BDEAL_PARTNER_PIX = "biz_Deal_Partner_Pix";
    public static final String BDEAL_PARTNER_PROF_ID = "biz_Deal_Partner_Prof_ID";
    public static final String BDEAL_PARTNER_QUSER_ID = "biz_Deal_Partner_Quser_ID";
    public static final String BDEAL_PARTNER_RATING = "biz_Deal_Partner_Rating";
    public static final String BDEAL_PARTNER_ID = "biz_Deal_Partner_ID";

    public static final String CREATE_BDEAL_PARTNER_TABLE = "CREATE TABLE IF NOT EXISTS " + BDEAL_PARTNER_TABLE + " (" + BDEAL_PARTNER_DBID + " INTEGER , " +
            BDEAL_PARTNER_PROF_ID + " INTEGER , " + BDEAL_PARTNER_QUSER_ID + " INTEGER , " + BDEAL_PARTNER_NAME + " TEXT , " + BDEAL_PARTNER_BRAND_NAME + " TEXT , " + BDEAL_PARTNER_REG_NO + " TEXT , " + BDEAL_PARTNER_MARKET_NAME + " TEXT , " + BDEAL_PARTNER_GENDER + " TEXT , " + BDEAL_PARTNER_COUNTRY + " TEXT , " + BDEAL_PARTNER_PIX + " BLOB , " + BDEAL_PARTNER_STATUS + " TEXT , " + BDEAL_PARTNER_RATING + " FLOAT , " + BDEAL_PARTNER_ID + " INTEGER , "+  "PRIMARY KEY(" + BDEAL_PARTNER_DBID + "), " +"FOREIGN KEY(" + BDEAL_PARTNER_MARKET_NAME  + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_NAME + ")," +
            "FOREIGN KEY(" + BDEAL_PARTNER_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";



    private QBUser user;
    private boolean isSelected = false;
    private int partnerDBID;
    private int partnerID;
    private int partnerQUserID;
    private Uri partnerPix;
    private String partnerName;
    private String partnerBrandName;
    private  String partnerCACNo;
    private String partnerMarketName;
    private String partnerGender;
    private String partnerStatus;
    private Profile partnerProfile;
    private Customer partnerCusProf;
    private MarketBusiness partnerBiz;
    private Market partnerMarket;
    private int partnerRating;
    private int partnerProfID;
    private String partnerCountry;
    private ArrayList<BusinessDeal> businessDeals;


    public BizDealPartner(QBUser user, boolean isSelected) {
        this.user = user;
        this.isSelected = isSelected;
    }

    public BizDealPartner() {
        super();
    }

    public BizDealPartner(int partnerID, String partnerName, String brandName, Uri picture) {
        this.partnerID = partnerID;
        this.partnerName = partnerName;
        this.partnerBrandName = brandName;
        this.partnerPix = picture;

    }

    public BizDealPartner(int partnerID, int profID, int qUserID, int rating, String partnerName, String gender, String brandName, String regNo, String marketName, String country, Uri picture, String status) {
        this.partnerID = partnerID;
        this.partnerBrandName = brandName;
        this.partnerProfID = profID;
        this.partnerQUserID = qUserID;
        this.partnerRating = rating;
        this.partnerCountry = country;
        this.partnerName = partnerName;
        this.partnerGender = gender;
        this.partnerCACNo = regNo;
        this.partnerMarketName = marketName;
        this.partnerStatus = status;
        this.partnerPix = picture;
    }
    public void addBizDeal(BusinessDeal businessDeal) {
        businessDeals = new ArrayList<>();
        businessDeals.add(businessDeal);
    }

    public QBUser getUser() {
        return user;
    }

    public void setUser(QBUser user) {
        this.user = user;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPartnerDBID() {
        return partnerDBID;
    }

    public void setPartnerDBID(int partnerDBID) {
        this.partnerDBID = partnerDBID;
    }

    public int getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(int partnerID) {
        this.partnerID = partnerID;
    }

    public Uri getPartnerPix() {
        return partnerPix;
    }

    public void setPartnerPix(Uri partnerPix) {
        this.partnerPix = partnerPix;
    }

    public String getPartnerBrandName() {
        return partnerBrandName;
    }

    public void setPartnerBrandName(String partnerBrandName) {
        this.partnerBrandName = partnerBrandName;
    }

    public String getPartnerCACNo() {
        return partnerCACNo;
    }

    public void setPartnerCACNo(String partnerCACNo) {
        this.partnerCACNo = partnerCACNo;
    }

    public String getPartnerMarketName() {
        return partnerMarketName;
    }

    public void setPartnerMarketName(String partnerMarketName) {
        this.partnerMarketName = partnerMarketName;
    }

    public String getPartnerGender() {
        return partnerGender;
    }

    public void setPartnerGender(String partnerGender) {
        this.partnerGender = partnerGender;
    }

    public String getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public Profile getPartnerProfile() {
        return partnerProfile;
    }

    public void setPartnerProfile(Profile partnerProfile) {
        this.partnerProfile = partnerProfile;
    }

    public Customer getPartnerCusProf() {
        return partnerCusProf;
    }

    public void setPartnerCusProf(Customer partnerCusProf) {
        this.partnerCusProf = partnerCusProf;
    }

    public Market getPartnerMarket() {
        return partnerMarket;
    }

    public void setPartnerMarket(Market partnerMarket) {
        this.partnerMarket = partnerMarket;
    }

    public MarketBusiness getPartnerBiz() {
        return partnerBiz;
    }

    public void setPartnerBiz(MarketBusiness partnerBiz) {
        this.partnerBiz = partnerBiz;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public int getPartnerQUserID() {
        return partnerQUserID;
    }

    public void setPartnerQUserID(int partnerQUserID) {
        this.partnerQUserID = partnerQUserID;
    }

    public int getPartnerRating() {
        return partnerRating;
    }

    public void setPartnerRating(int partnerRating) {
        this.partnerRating = partnerRating;
    }

    public int getPartnerProfID() {
        return partnerProfID;
    }

    public void setPartnerProfID(int partnerProfID) {
        this.partnerProfID = partnerProfID;
    }

    public String getPartnerCountry() {
        return partnerCountry;
    }

    public void setPartnerCountry(String partnerCountry) {
        this.partnerCountry = partnerCountry;
    }

    public ArrayList<BusinessDeal> getBusinessDeals() {
        return businessDeals;
    }

    public void setBusinessDeals(ArrayList<BusinessDeal> businessDeals) {
        this.businessDeals = businessDeals;
    }
}
