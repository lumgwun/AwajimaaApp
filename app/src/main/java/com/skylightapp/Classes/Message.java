package com.skylightapp.Classes;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

;import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = Message.MESSAGE_TABLE)
public class Message implements Serializable, Parcelable {
    public static final String MESSAGE_TABLE = "message_table";
    public static final String MESSAGE_ID = "_message_id";
    public static final String MESSAGE_DETAILS = "_message_Details";
    public static final String VIEWED = "viewed";
    public static final String MESSAGE_TIME = "message_time";
    public static final String MESSAGE_SENDER = "message_sender";
    public static final String MESSAGE_SENDEE = "message_sendee";
    public static final String MESSAGE_PURPOSE = "message_purpose";
    public static final String MESSAGE_ADMIN_NAME = "message_admin_name";
    public static final String MESSAGE_ADMIN_ID = "message_admin_id";
    public static final String MESSAGE_CUS_ID = "message_Cus_id";
    public static final String MESSAGE_PROF_ID = "message__Prof_id";
    public static final String MESSAGE_BRANCH_OFFICE = "message_office";




    public static final String CODE_OWNER = "Code_owner";
    /*public static final String CODE_TABLE = "Code_table";
    public static final String CODE_OWNER_PHONE = "Code_owner_phone";
    public static final String CODE_PIN = "Code";
    public static final String CODE_DATE = "Code_Date";
    public static final String CODE_STATUS = "Code_status";
    public static final String CODE_MANAGER = "Code_manager";*/

    public static final String CREATE_MESSAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + MESSAGE_TABLE + " (" + MESSAGE_PROF_ID + " INTEGER, " + MESSAGE_ID + " INTEGER , " +
            MESSAGE_CUS_ID + " INTEGER , "+ MESSAGE_ADMIN_ID + " TEXT , "+ MESSAGE_ADMIN_NAME + " TEXT , " + MESSAGE_PURPOSE + " TEXT , "+ MESSAGE_DETAILS + " TEXT , " + MESSAGE_SENDER + " TEXT , "+ MESSAGE_SENDEE + " TEXT , " + MESSAGE_TIME + " TEXT , " +
            VIEWED + " INTEGER, " + MESSAGE_BRANCH_OFFICE + " TEXT , " + "PRIMARY KEY("  + MESSAGE_ID + "), " +"FOREIGN KEY(" + MESSAGE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+ "FOREIGN KEY(" + MESSAGE_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    public static final String MESSAGE_REPLY_TABLE = "Message_Reply_table";
    public static final String MESSAGE_REPLY_ID = "Reply_message_ID";
    public static final String MESSAGE_REPLY_DETAILS = "message_Reply_Details";
    public static final String MESSAGE_REPLY_TIME = "message_Reply_Time";
    public static final String MESSAGE_REPLY_TIMEA = "message_Reply_TimeA";
    public static final String MESSAGE_REPLY_MESSAGE_ID = "message_Reply_MID";
    public static final String MESSAGE_REPLY_DETAILS_ADMIN = "message_Reply_DetailsA";


    public static final String CREATE_MESSAGE_REPLY_TABLE = "CREATE TABLE IF NOT EXISTS " + MESSAGE_REPLY_TABLE + " (" + MESSAGE_REPLY_ID + " INTEGER NOT NULL, " + MESSAGE_REPLY_MESSAGE_ID + " INTEGER , "+ MESSAGE_REPLY_DETAILS + " TEXT , " + MESSAGE_REPLY_DETAILS_ADMIN + " TEXT , "+ MESSAGE_REPLY_TIME + " TEXT , " + MESSAGE_REPLY_TIMEA + " TEXT , "+ "PRIMARY KEY("  + MESSAGE_REPLY_ID + "), " + "FOREIGN KEY(" + MESSAGE_REPLY_MESSAGE_ID + ") REFERENCES " + MESSAGE_TABLE + "(" + MESSAGE_ID + "))";

    /*public static final String CREATE_CODE_TABLE = "CREATE TABLE " + CODE_TABLE + " (" + PROFILE_ID + " LONG NOT NULL, " + CODE_OWNER_PHONE + " TEXT , " +
            CODE_PIN + " TEXT , " + CODE_DATE + " TEXT , " + CODE_STATUS + " TEXT , " + CODE_MANAGER + " TEXT , " + "PRIMARY KEY("  + CODE_OWNER_PHONE + "), " + "FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";*/

    public Message() {
        super();

    }
    public Message(int messageID, String phoneNumber,String timestamp) {
        this.timestamp = timestamp;
        this.dbId = messageID;


    }

    public Message(int i, String s, String s1, String s2) {

    }

    protected Message(Parcel in) {
        text = in.readString();
        messageID = in.readString();
        sender = in.readString();
        timestamp = in.readString();
        supportType = in.readString();
        dbId = in.readInt();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };




    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    private String text;
    private String messageID;
    private String sender;
    private String senderName;
    private long sendeeProfileID;
    private long customerID;
    private String sendee;
    private String messageOffice;
    private String timestamp;
    private String supportType;
    @PrimaryKey(autoGenerate = true)
    private int dbId=150;

    public Message (String supportType,String text, String sender,String timestamp,int dbId) {
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
        this.supportType = supportType;
        this.dbId = dbId;

    }
    public Message(int profileID, int customerID, int messageID, String purpose, String messageDetails, String messageSender, String messageSendee,String messageTime) {
        this.text = messageDetails;
        this.sender = messageSender;
        this.timestamp = messageTime;
        this.sendee = messageSendee;
        this.supportType = purpose;
        this.dbId = messageID;
    }
    public Message(int keyExtraMessageId, String selectedPurpose, String message, String sender, String time) {
        this.text = message;
        this.sender = sender;
        this.timestamp = time;
        this.supportType = selectedPurpose;
        this.dbId = keyExtraMessageId;

    }
    public Message (int dbId,String senderName,long sendeeProfileID,long customerID,String purpose,String details, String timestamp) {
        this.senderName = senderName;
        this.sendeeProfileID = sendeeProfileID;
        this.customerID = customerID;
        this.sendee = sendee;
        this.text = details;
        this.sender = sender;
        this.timestamp = timestamp;
        this.supportType = purpose;
        this.dbId = dbId;

    }


    public void setMessageOffice(String messageOffice) {
        this.messageOffice = messageOffice;
    }
    public String getMessageOffice() { return messageOffice; }



    public String getMessageID() { return messageID; }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public String getSupportType() { return supportType; }
    public String getSender() {
        return sender;
    }
    public String getText() { return text; }
    public String getSenderName() { return senderName; }
    public long getSendeeProfileID() { return sendeeProfileID; }
    public long getCustomerID() { return customerID; }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public void setSendeeProfileID(long sendeeProfileID) {
        this.sendeeProfileID = sendeeProfileID;
    }
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public void setSupportType(String supportType) {
        this.supportType = supportType;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setText(String text) {
        this.text = text;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    @NonNull
    public String toString() { return (sender + " (" + text + ")"); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(messageID);
        parcel.writeString(sender);
        parcel.writeString(timestamp);
        parcel.writeString(supportType);
        parcel.writeLong(dbId);
    }
}