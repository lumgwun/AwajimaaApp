package com.skylightapp.Classes;

public class Card {
    private int cardID;
    private String stripeToken;
    private String lastFour;
    private String createdAt;
    private String updatedAt;
    private String ownerId;
    private String cardType;
    private boolean isDefault;
    private String token;
    private int ownerID;
    private String cardNumber;
    private int cardMonth;
    private int cardYear;
    private int cardCvv;

    public Card(String toString, int cardMonth, int cardYear, int cardCvv) {
        this.cardNumber = toString;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
        this.cardCvv = cardCvv;
    }

    public Card() {
        super();
    }


    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getOwnerID() {
        return ownerID;
    }
    public boolean validateCard() {
        return false;
    }

    public boolean validateNumber() {
        return false;
    }

    public boolean validateExpiryDate() {
        return false;
    }

    public boolean validateCVC() {
        return false;
    }
}
