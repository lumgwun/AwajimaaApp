package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.skylightapp.Classes.Journey;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Classes.Journey.DESTINATION_JOURNEY;
import static com.skylightapp.Classes.Journey.END_DATE_JOURNEY;
import static com.skylightapp.Classes.Journey.JOURNEY_CUS_ID;
import static com.skylightapp.Classes.Journey.JOURNEY_ID;
import static com.skylightapp.Classes.Journey.JOURNEY_JADCCT_ID;
import static com.skylightapp.Classes.Journey.JOURNEY_PROF_ID;
import static com.skylightapp.Classes.Journey.JOURNEY_TABLE;
import static com.skylightapp.Classes.Journey.PERSON_JOURNEY;
import static com.skylightapp.Classes.Journey.START_DATE_JOURNEY;
import static com.skylightapp.Classes.Journey.STATUS_JOURNEY;
import static com.skylightapp.Classes.Journey.TOTAL_AMOUNT_JOURNEY;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_ID;

public class JourneyDAO extends DBHelperDAO {

    private static final String WHERE_ID_EQUALS = DOCUMENT_ID
            + " =?";
    public JourneyDAO(Context context) {
        super(context);
    }
    public long addJourney(Journey journey){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JOURNEY_ID, journey.getJournetID());
        values.put(START_DATE_JOURNEY, journey.getJourneyStartDate());
        values.put(END_DATE_JOURNEY, journey.getJourneyEndDate());
        values.put(TOTAL_AMOUNT_JOURNEY, journey.getJourneyTotalAmount());
        values.put(PERSON_JOURNEY, journey.getMember());
        values.put(JOURNEY_PROF_ID, journey.getJourneyProfID());
        values.put(JOURNEY_JADCCT_ID, journey.getJourneyAcctID());
        values.put(JOURNEY_CUS_ID, journey.getJourneyCusID());
        values.put(STATUS_JOURNEY, journey.getJourneyStatus());
         db.insert(JOURNEY_TABLE, null, values);
        Log.e(TAG, "addJourney: " + journey.getJournetID());
        db.close();
        return journey.getJournetID();
    }

    public List<Journey> queryJourney(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(JOURNEY_TABLE, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            List<Journey> journeys = new ArrayList<Journey>(cursor.getCount());
            while (cursor.moveToNext()) {
                Journey journey = parseJourney(cursor);
                journeys.add(journey);
            }
            return journeys;
        }

        return null;
    }
    private Journey parseJourney(Cursor cursor){
        Journey journey = new Journey();
        journey.setJournetID(cursor.getInt(0));
        journey.setJourneyDest(cursor.getString(3));
        journey.setMember(cursor.getString(4));
        journey.setJourneyTotalAmount(cursor.getDouble(5));
        journey.setJourneyStartDate(cursor.getString(2));
        journey.setJourneyEndDate(cursor.getString(3));
        journey.setJourneyStatus(cursor.getString(6));
        return journey;
    }
}
