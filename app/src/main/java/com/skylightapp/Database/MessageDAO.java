package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Message;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Message.MESSAGE_ADMIN_ID;
import static com.skylightapp.Classes.Message.MESSAGE_ADMIN_NAME;
import static com.skylightapp.Classes.Message.MESSAGE_BIZ_ID;
import static com.skylightapp.Classes.Message.MESSAGE_BRANCH_OFFICE;
import static com.skylightapp.Classes.Message.MESSAGE_CUS_ID;
import static com.skylightapp.Classes.Message.MESSAGE_DETAILS;
import static com.skylightapp.Classes.Message.MESSAGE_ID;
import static com.skylightapp.Classes.Message.MESSAGE_MARKET_ID;
import static com.skylightapp.Classes.Message.MESSAGE_OTP;
import static com.skylightapp.Classes.Message.MESSAGE_PHONENO;
import static com.skylightapp.Classes.Message.MESSAGE_PROF_ID;
import static com.skylightapp.Classes.Message.MESSAGE_PURPOSE;
import static com.skylightapp.Classes.Message.MESSAGE_REPLY_MESSAGE_ID;
import static com.skylightapp.Classes.Message.MESSAGE_REPLY_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_SENDEE;
import static com.skylightapp.Classes.Message.MESSAGE_SENDER;
import static com.skylightapp.Classes.Message.MESSAGE_TABLE;
import static com.skylightapp.Classes.Message.MESSAGE_TIME;
import static java.lang.String.valueOf;

public class MessageDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = CUSTOMER_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public MessageDAO(Context context) {
        super(context);
    }
    public ArrayList<Message> getMessagesForBranchToday(String officeBranch, String todayDate) {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {MESSAGE_CUS_ID,MESSAGE_PURPOSE, MESSAGE_DETAILS,MESSAGE_SENDER,MESSAGE_SENDEE,MESSAGE_TIME};
        String selection = MESSAGE_BRANCH_OFFICE + "=? AND " + MESSAGE_TIME + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(todayDate)};

        Cursor cursor = db.query(MESSAGE_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getMessagesFromCursor(messageArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        //db.close();

        return messageArrayList;

    }
    public ArrayList<Message> getAllMessagesForBranch(String officeBranch) {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CUSTOMER_ID,MESSAGE_PURPOSE, MESSAGE_DETAILS,MESSAGE_SENDER,MESSAGE_SENDEE,MESSAGE_TIME};
        String selection = MESSAGE_BRANCH_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(officeBranch)};

        Cursor cursor = db.query(MESSAGE_TABLE, columns, selection, selectionArgs, null, null, null);

        getMessagesFromCursor(messageArrayList, cursor);

        cursor.close();
        //db.close();

        return messageArrayList;
    }


    public ArrayList<Message> getMessagesFromCurrentProfile(int profileID) {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MESSAGE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(MESSAGE_TABLE, null,  selection, selectionArgs, null,
                null, null);

        getMessagesFromCursor(messageArrayList, cursor);

        cursor.close();
        //db.close();

        return messageArrayList;
    }
    public long saveNewMessage(int messageId, int profileID, Message message, LocalTime today) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String messageDetails = message.getMessageDetails();
        String messageTimeStamp = message.getMessageTime();
        String sender = message.getMessageSender();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGE_PROF_ID, profileID);
        contentValues.put(MESSAGE_SENDER, sender);
        contentValues.put(MESSAGE_DETAILS, messageDetails);
        contentValues.put(MESSAGE_TIME, messageTimeStamp);
        return sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);
    }

    public ArrayList<Message> getAllMessages2() {
        ArrayList<Message> messages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(MESSAGE_TABLE, null, null, null, MESSAGE_TIME,
                null, null);
        getMessagesFromCursor(messages, cursor);
        cursor.close();
        db.close();

        return messages;

    }
    public long insertNewMessage(int messageID, int profileID, int customerID, int adminID, String adminName, String purposeOfMessage, String message, String sender, String sendee, String time) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGE_PROF_ID, profileID);
        contentValues.put(MESSAGE_ID, messageID);
        contentValues.put(MESSAGE_CUS_ID, customerID);
        contentValues.put(MESSAGE_ADMIN_ID, adminID);
        contentValues.put(MESSAGE_ADMIN_NAME, adminName);
        contentValues.put(MESSAGE_PURPOSE, purposeOfMessage);
        contentValues.put(MESSAGE_DETAILS, message);
        contentValues.put(MESSAGE_SENDER, sender);
        contentValues.put(MESSAGE_SENDEE, sendee);
        contentValues.put(MESSAGE_TIME, time);
        return sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);

    }
    public void updateMessage(int messageID,String messageUpdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        savingsUpdateValues.put(MESSAGE_DETAILS, messageUpdate);
        String selection = MESSAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(messageID)};
        String selection3 = MESSAGE_REPLY_MESSAGE_ID + "=?";
        String[] selectionArgs3 = new String[]{valueOf(messageID)};
        db.update(MESSAGE_REPLY_TABLE, savingsUpdateValues, selection3, selectionArgs3);
        db.update(MESSAGE_TABLE, savingsUpdateValues, selection, selectionArgs);
        db.close();


    }

    protected void getMessagesFromCursor(ArrayList<Message> messageArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int id = (cursor.getInt(1));
            String messageDetails = cursor.getString(6);
            String views = cursor.getString(10);
            String sender = cursor.getString(7);
            String time = cursor.getString(9);

            messageArrayList.add(new Message());
        }

    }
    public ArrayList<Message> getMessagesToday(String todayDate) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = sdf.format(calendar.getTime().getDate());
        ArrayList<Message> messages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {MESSAGE_SENDER, MESSAGE_PURPOSE,MESSAGE_DETAILS,MESSAGE_TIME};
        String selection = MESSAGE_TIME + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};
        Cursor cursor = db.query(MESSAGE_TABLE, columns, selection, selectionArgs, null, null, null);
        getMessagesFromCursor(messages, cursor);
        cursor.close();
        //db.close();

        return messages;
    }
    public ArrayList<Message> getMessagesFromCurrentCustomer(int customerID) {
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MESSAGE_CUS_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(customerID)};

            Cursor cursor = db.query(MESSAGE_TABLE, null,  selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getMessagesFromCursor(messageArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return messageArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public long insertMessage(int profileID, int customerID, int messageID, long bizID, String message, String sender, String sendee, String officeBranch, String messageTime) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MESSAGE_PROF_ID, profileID);
            contentValues.put(MESSAGE_CUS_ID, customerID);
            contentValues.put(MESSAGE_DETAILS, message);
            contentValues.put(MESSAGE_ID, messageID);
            contentValues.put(MESSAGE_SENDER, sender);
            contentValues.put(MESSAGE_BIZ_ID, bizID);
            contentValues.put(MESSAGE_SENDEE, sendee);
            contentValues.put(MESSAGE_BRANCH_OFFICE, officeBranch);
            contentValues.put(MESSAGE_TIME, messageTime);
            sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messageID;
    }
    public long insertMessage(int profileID, int customerID, int bizID,int marketID, String purposeTittle,String message, String sender, String sendee, String officeBranch, String messageTime) {

        int messageID=0;
        try {

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MESSAGE_PROF_ID, profileID);
            contentValues.put(MESSAGE_CUS_ID, customerID);
            contentValues.put(MESSAGE_DETAILS, message);
            contentValues.put(MESSAGE_SENDER, sender);
            contentValues.put(MESSAGE_BIZ_ID, bizID);
            contentValues.put(MESSAGE_MARKET_ID, marketID);
            contentValues.put(MESSAGE_SENDEE, sendee);
            contentValues.put(MESSAGE_PURPOSE, purposeTittle);
            contentValues.put(MESSAGE_BRANCH_OFFICE, officeBranch);
            contentValues.put(MESSAGE_TIME, messageTime);
            sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messageID;
    }
    public long insertMessageCustomer(int profID, int cusID, int messageID, String purpose,String message, String sender,String sendee,String office, String meassageDate) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Message message1 = new Message();
            contentValues.put(MESSAGE_PROF_ID, profID);
            contentValues.put(MESSAGE_CUS_ID, cusID);
            contentValues.put(MESSAGE_ID, messageID);
            contentValues.put(MESSAGE_PURPOSE, purpose);
            contentValues.put(MESSAGE_DETAILS, message);
            contentValues.put(MESSAGE_SENDER, sender);
            contentValues.put(MESSAGE_SENDEE, sendee);
            contentValues.put(MESSAGE_BRANCH_OFFICE, office);
            contentValues.put(MESSAGE_TIME, meassageDate);
            sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messageID;
    }

    public Cursor getAllMessagesUser(int profileID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            String selection = MESSAGE_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profileID)};
            return sqLiteDatabase.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                    null, null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getAllCustomerMessages(int profileID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = MESSAGE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        return sqLiteDatabase.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
    }

    public String getSpecificMessage(int messageId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String result=null;
        String selection = MESSAGE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(messageId)};

        Cursor cursor = sqLiteDatabase.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(1);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        sqLiteDatabase.close();

        return result;
    }
    public String getMessage() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {MESSAGE_ID,MESSAGE_DETAILS};
        Cursor cursor =sqLiteDatabase.query(MESSAGE_TABLE,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(1);
            String name =cursor.getString(6);
            buffer.append(cid+ "   " + name );
        }
        return buffer.toString();
    }


    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(MESSAGE_TABLE, null, null, null, MESSAGE_SENDER,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getMessagesFromCursorAdmin(messageArrayList, cursor);
                cursor.close();
            }

        db.close();

        return messageArrayList;
    }

    private void getMessagesFromCursorAdmin(ArrayList<Message> messages, Cursor cursor) {
        while (cursor.moveToNext()) {
            try {
                int profileID = cursor.getInt(0);
                int customerID = cursor.getInt(2);
                int messageID = cursor.getInt(1);
                String purpose = cursor.getString(5);
                String messageDetails = cursor.getString(6);
                String messageSender = cursor.getString(7);
                String messageSendee = cursor.getString(8);
                String messageTime = cursor.getString(9);
                messages.add(new Message(profileID,customerID,messageID,purpose,messageDetails,messageSender,messageSendee,messageTime));

            }catch (SQLException e)
            {
                e.printStackTrace();
            }


        }
    }



    public ArrayList<Message> getMessagesForCurrentProfile(int profileID) {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MESSAGE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getMessagesFromCursorAdmin(messageArrayList, cursor);
                cursor.close();
            }

        db.close();

        return messageArrayList;
    }
    public ArrayList<Message> getMessagesForCurrentCustomer(int customerID) {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MESSAGE_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(MESSAGE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getMessagesFromCursorAdmin(messageArrayList, cursor);
                cursor.close();
            }

        db.close();

        return messageArrayList;
    }
    public int getMessageCountToday(String dateOfMessage) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;


        String selection = MESSAGE_TIME + "=?";
        String[] selectionArgs = new String[]{valueOf(dateOfMessage)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + MESSAGE_TABLE + " WHERE " + selection, selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }
    public int getCustomerMessagesCount(int customerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MESSAGE_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + MESSAGE_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(MESSAGE_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    protected boolean updateUserMessageState(int id) {
        return true;
    }

    public int getProfileMessagesCount(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MESSAGE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + MESSAGE_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(MESSAGE_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public long saveNewMessage(Message supportMessage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String phoneNo=null;
        int otp=0;
        if(supportMessage !=null){
            phoneNo=supportMessage.getMessagePhone();
            otp=supportMessage.getMessageOTP();

        }
        contentValues.put(MESSAGE_PHONENO, phoneNo);
        contentValues.put(MESSAGE_OTP, otp);
        return sqLiteDatabase.insert(MESSAGE_TABLE, null, contentValues);

    }

}
