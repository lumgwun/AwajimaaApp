package com.skylightapp.Interfaces;

import android.widget.Toast;

import com.skylightapp.Classes.SavedCard;

import java.util.List;

public interface CardSavedListener {
    default void onSavedCardsLookupSuccessful(List<SavedCard> cards, String phoneNumber) {
        //if (cards.size() != 0) cardPayManager.chargeSavedCard(cards.get(0));

    }
    void onSavedCardsLookupFailed(String message);
    void onDeleteSavedCardRequestSuccessful();
    public void onCardSaveSuccessful(String phoneNumber);
    public void onCardSaveFailed(String message);
    void onDeleteSavedCardRequestFailed(String message);
    public default void collectOtpForSaveCardCharge() {
        //collectOtp("Otp for saved card");
    }


}
