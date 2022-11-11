package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BizDealMileStone implements Serializable, Parcelable {
    private int mileStoneID;
    private int mileStoneBizDealID;
    private double mileStoneAmt;
    private int mileStoneCode;
    private String mileStoneDate;

    private int[] numbersOfMileStones;
    private int[] answerPoints;
    private int[] mileStoneCodes;
    private int numberOfCodesUsed;
    private double score;

    public BizDealMileStone () {
        super();

    }
    public BizDealMileStone(int mileStoneBizDealID1, int [] codes, int[] numbOfMileStones1)
    {
        mileStoneBizDealID = mileStoneBizDealID1;
        mileStoneCodes = codes;
        numberOfCodesUsed = 0;
        numbersOfMileStones = numbOfMileStones1;
        answerPoints = new int[codes.length];
    }

    public BizDealMileStone(int codeID, int bizDealID, int codeDigit, double calAmount) {
        mileStoneID = codeID;
        mileStoneBizDealID = bizDealID;
        mileStoneCode = codeDigit;
        mileStoneAmt = calAmount;
    }

    public boolean containsNumber(int number)
    {
        boolean result = false;
        for(int i = 0; i < numbersOfMileStones.length; i++) {
            if(numbersOfMileStones[i] == number)
                result = true;
        }
        return result;
    }

    public void saveScore(int num, int number) {
        for(int i = 0; i < numbersOfMileStones.length; i++) {
            if(numbersOfMileStones[i] == num) {
                answerPoints[i] = number;
            }
        }
    }

    public int[] getNumbersOfMileStones() {
        return numbersOfMileStones;
    }

    public void setNumbersOfMileStones(int[] numbersOfMileStones) {
        this.numbersOfMileStones = numbersOfMileStones;
    }

    public int[] getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(int[] answerPoints) {
        this.answerPoints = answerPoints;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }



    protected BizDealMileStone(Parcel in) {
        mileStoneID = in.readInt();
        mileStoneBizDealID = in.readInt();
        mileStoneAmt = in.readDouble();
        mileStoneCode = in.readInt();
        mileStoneDate = in.readString();
    }

    public static final Creator<BizDealMileStone> CREATOR = new Creator<BizDealMileStone>() {
        @Override
        public BizDealMileStone createFromParcel(Parcel in) {
            return new BizDealMileStone(in);
        }

        @Override
        public BizDealMileStone[] newArray(int size) {
            return new BizDealMileStone[size];
        }
    };

    public int getMileStoneID() {
        return mileStoneID;
    }

    public void setMileStoneID(int mileStoneID) {
        this.mileStoneID = mileStoneID;
    }

    public double getMileStoneAmt() {
        return mileStoneAmt;
    }

    public void setMileStoneAmt(double mileStoneAmt) {
        this.mileStoneAmt = mileStoneAmt;
    }

    public int getMileStoneCode() {
        return mileStoneCode;
    }

    public void setMileStoneCode(int mileStoneCode) {
        this.mileStoneCode = mileStoneCode;
    }

    public String getMileStoneDate() {
        return mileStoneDate;
    }

    public void setMileStoneDate(String mileStoneDate) {
        this.mileStoneDate = mileStoneDate;
    }

    public int getMileStoneBizDealID() {
        return mileStoneBizDealID;
    }

    public void setMileStoneBizDealID(int mileStoneBizDealID) {
        this.mileStoneBizDealID = mileStoneBizDealID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mileStoneID);
        parcel.writeInt(mileStoneBizDealID);
        parcel.writeDouble(mileStoneAmt);
        parcel.writeInt(mileStoneCode);
        parcel.writeString(mileStoneDate);
    }

    public int[] getMileStoneCodes() {
        return mileStoneCodes;
    }

    public void setMileStoneCodes(int[] mileStoneCodes) {
        this.mileStoneCodes = mileStoneCodes;
    }

    public int getNumberOfCodesUsed() {
        return numberOfCodesUsed;
    }

    public void setNumberOfCodesUsed(int numberOfCodesUsed) {
        this.numberOfCodesUsed = numberOfCodesUsed;
    }
}
