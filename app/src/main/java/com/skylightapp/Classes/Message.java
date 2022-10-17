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
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

@Entity(tableName = Message.MESSAGE_TABLE)
public class Message implements Serializable, Parcelable {
    public static final String MESSAGE_TABLE = "message_table";
    public static final String MESSAGE_ID = "_message_id";
    public static final String MESSAGE_DETAILS = "_message_Details";
    public static final String VIEWED = "message_viewed";
    public static final String MESSAGE_TIME = "message_time";
    public static final String MESSAGE_SENDER = "message_sender";
    public static final String MESSAGE_SENDEE = "message_sendee";
    public static final String MESSAGE_PURPOSE = "message_purpose";
    public static final String MESSAGE_ADMIN_NAME = "message_admin_name";
    public static final String MESSAGE_ADMIN_ID = "message_admin_id";
    public static final String MESSAGE_CUS_ID = "message_Cus_id";
    public static final String MESSAGE_PROF_ID = "message__Prof_id";
    public static final String MESSAGE_BRANCH_OFFICE = "message_office";
    public static final String MESSAGE_PHONENO = "message_Phone";
    public static final String MESSAGE_OTP = "message_OTP";
    public static final String MESSAGE_BIZ_ID = "message_BIZ_ID";
    public static final String MESSAGE_MARKET_ID = "message_Market_ID";
    public static final String MESSAGE_DB_ID = "message_DB_ID";


    public static final String CREATE_MESSAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + MESSAGE_TABLE + " (" + MESSAGE_PROF_ID + " INTEGER, " + MESSAGE_ID + " INTEGER , " +
            MESSAGE_CUS_ID + " INTEGER , "+ MESSAGE_ADMIN_ID + " TEXT , "+ MESSAGE_ADMIN_NAME + " TEXT , " + MESSAGE_PURPOSE + " TEXT , "+ MESSAGE_DETAILS + " TEXT , " + MESSAGE_SENDER + " TEXT , "+ MESSAGE_SENDEE + " TEXT , " + MESSAGE_TIME + " TEXT , " +
            VIEWED + " INTEGER, " + MESSAGE_BRANCH_OFFICE + " TEXT , " + MESSAGE_PHONENO + " TEXT , "+ MESSAGE_OTP + " INTEGER , "+ MESSAGE_BIZ_ID + " INTEGER , "+ MESSAGE_MARKET_ID + " INTEGER , "+ MESSAGE_DB_ID + " INTEGER , "+ "PRIMARY KEY("  + MESSAGE_DB_ID + "), " +"FOREIGN KEY(" + MESSAGE_BIZ_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+"FOREIGN KEY(" + MESSAGE_MARKET_ID + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "),"+"FOREIGN KEY(" + MESSAGE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+ "FOREIGN KEY(" + MESSAGE_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    public static final String MESSAGE_REPLY_TABLE = "Message_Reply_table";
    public static final String MESSAGE_REPLY_ID = "Reply_message_ID";
    public static final String MESSAGE_REPLY_DETAILS = "message_Reply_Details";
    public static final String MESSAGE_REPLY_TIME = "message_Reply_Time";
    public static final String MESSAGE_REPLY_TIMEA = "message_Reply_TimeA";
    public static final String MESSAGE_REPLY_MESSAGE_ID = "message_Reply_MID";
    public static final String MESSAGE_REPLY_DETAILS_ADMIN = "message_Reply_DetailsA";


    public static final String CREATE_MESSAGE_REPLY_TABLE = "CREATE TABLE IF NOT EXISTS " + MESSAGE_REPLY_TABLE + " (" + MESSAGE_REPLY_ID + " INTEGER NOT NULL, " + MESSAGE_REPLY_MESSAGE_ID + " INTEGER , "+ MESSAGE_REPLY_DETAILS + " TEXT , " + MESSAGE_REPLY_DETAILS_ADMIN + " TEXT , "+ MESSAGE_REPLY_TIME + " TEXT , " + MESSAGE_REPLY_TIMEA + " TEXT , "+ "PRIMARY KEY("  + MESSAGE_REPLY_ID + "), " + "FOREIGN KEY(" + MESSAGE_REPLY_MESSAGE_ID + ") REFERENCES " + MESSAGE_TABLE + "(" + MESSAGE_ID + "))";

    public Message() {
        super();

    }
    public Message(int messageID, String phoneNumber,String messageTime) {
        this.messageTime = messageTime;
        this.messageID = messageID;


    }

    public Message(int i, String s, String s1, String s2) {

    }

    protected Message(Parcel in) {
        messageDetails = in.readString();
        messageID = in.readInt();
        messageSender = in.readString();
        messageTime = in.readString();
        messageType = in.readString();
        messageNo = in.readInt();
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

    public Message(String uPhoneNumber, int otpDigit) {
        this.messagePhone = uPhoneNumber;
        this.messageOTP = otpDigit;

    }


    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    private String messageDetails;
    private String messagePhone;
    private int messageOTP;
    private int messageID;
    private String messageSender;
    private String messageSenderName;
    private int messageRecipientID;
    private int messageCusID;
    private String messageRecipient;
    private String messageOffice;
    private String messageTime;
    private String messageType;
    @PrimaryKey(autoGenerate = true)
    private int messageNo =150;
    private int messageBizID;
    private int messageMarketID;

    public Message (String messageType, String messageDetails, String messageSender, String messageTime, int messageNo) {
        this.messageDetails = messageDetails;
        this.messageSender = messageSender;
        this.messageTime = messageTime;
        this.messageType = messageType;
        this.messageNo = messageNo;

    }
    public Message(int profileID, int messageCusID, int messageID, String purpose, String messageDetails, String messageSender, String messageSendee, String messageTime) {
        this.messageDetails = messageDetails;
        this.messageSender = messageSender;
        this.messageTime = messageTime;
        this.messageRecipient = messageSendee;
        this.messageType = purpose;
        this.messageNo = messageID;
    }
    public Message(int keyExtraMessageId, String selectedPurpose, String message, String messageSender, String time) {
        this.messageDetails = message;
        this.messageSender = messageSender;
        this.messageTime = time;
        this.messageType = selectedPurpose;
        this.messageNo = keyExtraMessageId;

    }
    public Message (int messageNo, String messageSenderName, int messageRecipientID, int messageCusID, String purpose, String details, String messageTime) {
        this.messageSenderName = messageSenderName;
        this.messageRecipientID = messageRecipientID;
        this.messageCusID = messageCusID;
        this.messageDetails = details;
        this.messageTime = messageTime;
        this.messageType = purpose;
        this.messageNo = messageNo;

    }


    public void setMessageOffice(String messageOffice) {
        this.messageOffice = messageOffice;
    }
    public String getMessageOffice() { return messageOffice; }



    public int getMessageID() { return messageID; }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getMessageTime() {
        return messageTime;
    }
    public String getMessageType() { return messageType; }
    public String getMessageSender() {
        return messageSender;
    }
    public String getMessageDetails() { return messageDetails; }
    public String getMessageSenderName() { return messageSenderName; }
    public long getMessageRecipientID() { return messageRecipientID; }
    public long getMessageCusID() { return messageCusID; }

    public void setMessageSenderName(String messageSenderName) {
        this.messageSenderName = messageSenderName;
    }
    public void setMessageRecipientID(int messageRecipientID) {
        this.messageRecipientID = messageRecipientID;
    }
    public void setMessageCusID(int messageCusID) {
        this.messageCusID = messageCusID;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }
    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails;
    }

    public void setMessageNo(int messageNo) {
        this.messageNo = messageNo;
    }

    @NonNull
    public String toString() { return (messageSender + " (" + messageDetails + ")"); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(messageDetails);
        parcel.writeInt(messageID);
        parcel.writeString(messageSender);
        parcel.writeString(messageTime);
        parcel.writeString(messageType);
        parcel.writeLong(messageNo);
    }

    public String getMessagePhone() {
        return messagePhone;
    }

    public void setMessagePhone(String messagePhone) {
        this.messagePhone = messagePhone;
    }

    public int getMessageOTP() {
        return messageOTP;
    }

    public void setMessageOTP(int messageOTP) {
        this.messageOTP = messageOTP;
    }

    public int getMessageBizID() {
        return messageBizID;
    }

    public void setMessageBizID(int messageBizID) {
        this.messageBizID = messageBizID;
    }

    public int getMessageMarketID() {
        return messageMarketID;
    }

    public void setMessageMarketID(int messageMarketID) {
        this.messageMarketID = messageMarketID;
    }
}