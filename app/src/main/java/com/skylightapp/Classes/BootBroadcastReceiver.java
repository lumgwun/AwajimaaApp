package com.skylightapp.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.skylightapp.Database.DBHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BootBroadcastReceiver extends BroadcastReceiver {
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;
    private String mRepeat,mDateStarted,mDateEnded,mBirthdayCelebrant,mAge,mPhoneNumber;
    private String[] mDateSplit;
    private String[] mTimeSplit,mVirthDate;
    private int mYear, mMonth, mHour, mMinute, mDay, mReceivedID;
    private long mRepeatTime,mPackReceivedID,mBirthdayID;
    private  Double mAmountColected,mPackageTotal;

    private Calendar mCalendar;
    private Customer mCustomer;
    private AlarmReceiver mAlarmReceiver;
    private Date mBirthDate;
    //private Date mBirthDate;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;


    @Override
    public void onReceive(Context context, Intent intent) {

        //throw new UnsupportedOperationException("Not yet implemented");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            DBHelper rb = new DBHelper(context);
            mCalendar = Calendar.getInstance();
            mAlarmReceiver = new AlarmReceiver();

            List<ImportantDates> reminders = rb.getAllReminders();
            List<SkyLightPackage> skyLightPackages = rb.getAllPackagesAdmin();
            List<Birthday> birthdays = rb.getAllBirthDays();

            for (Birthday birthday : birthdays) {
                mBirthdayID = birthday.getBirthdayID();
                mBirthDate = birthday.getbDate();
                mBirthdayCelebrant = birthday.getbFirstName();
                mAge = birthday.getAge();
                mPhoneNumber = birthday.getbPhoneNumber();

                mVirthDate = mDate.split("/");

                //mDay = Integer.parseInt(mBirthDate[0]);
                //mMonth = Integer.parseInt(mBirthDate[1]);
                //mYear = Integer.parseInt(mBirthDate[2]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);


                // Cancel existing notification of the reminder by using its ID
                // mAlarmReceiver.cancelAlarm(context, mReceivedID);

                // Check repeat type
                if (mRepeatType.equals("Minute")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                } else if (mRepeatType.equals("Hour")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                } else if (mRepeatType.equals("Day")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
                } else if (mRepeatType.equals("Week")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
                } else if (mRepeatType.equals("Month")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
                }

                // Create a new notification
                if (mActive.equals("true")) {
                    if (mRepeat.equals("true")) {
                        mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);
                    } else if (mRepeat.equals("false")) {
                        mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                    }
                }
            }




            for (SkyLightPackage skyLightPackage : skyLightPackages) {
                mPackReceivedID = skyLightPackage.getPackID();
                mAmountColected = skyLightPackage.getPackageAmount_collected();
                mPackageTotal = skyLightPackage.getPackageTotalAmount();
                mDateStarted = skyLightPackage.getPackageDateStarted();
                mDateEnded = skyLightPackage.getPackageDateEnded();
                mCustomer = skyLightPackage.getPackageCustomer();

                mDateSplit = mDate.split("/");
                mTimeSplit = mTime.split(":");

                mDay = Integer.parseInt(mDateSplit[0]);
                mMonth = Integer.parseInt(mDateSplit[1]);
                mYear = Integer.parseInt(mDateSplit[2]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);


                // Cancel existing notification of the reminder by using its ID
                // mAlarmReceiver.cancelAlarm(context, mReceivedID);

                // Check repeat type
                if (mRepeatType.equals("Minute")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                } else if (mRepeatType.equals("Hour")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                } else if (mRepeatType.equals("Day")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
                } else if (mRepeatType.equals("Week")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
                } else if (mRepeatType.equals("Month")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
                }

                // Create a new notification
                if (mActive.equals("true")) {
                    if (mRepeat.equals("true")) {
                        mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);
                    } else if (mRepeat.equals("false")) {
                        mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                    }
                }
            }

            for (ImportantDates rm : reminders) {
                mReceivedID = rm.getID();
                mRepeat = rm.getRepeat();
                mRepeatNo = rm.getRepeatNo();
                mRepeatType = rm.getRepeatType();
                mActive = rm.getActive();
                mDate = String.valueOf(rm.getDate());
                mTime = rm.getTime();

                mDateSplit = mDate.split("/");
                mTimeSplit = mTime.split(":");

                mDay = Integer.parseInt(mDateSplit[0]);
                mMonth = Integer.parseInt(mDateSplit[1]);
                mYear = Integer.parseInt(mDateSplit[2]);
                mHour = Integer.parseInt(mTimeSplit[0]);
                mMinute = Integer.parseInt(mTimeSplit[1]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);

                // Cancel existing notification of the reminder by using its ID
                // mAlarmReceiver.cancelAlarm(context, mReceivedID);

                // Check repeat type
                if (mRepeatType.equals("Minute")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
                } else if (mRepeatType.equals("Hour")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
                } else if (mRepeatType.equals("Day")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
                } else if (mRepeatType.equals("Week")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
                } else if (mRepeatType.equals("Month")) {
                    mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
                }

                // Create a new notification
                if (mActive.equals("true")) {
                    if (mRepeat.equals("true")) {
                        mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);
                    } else if (mRepeat.equals("false")) {
                        mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                    }
                }
            }
        }
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            AlarmsHelper.setAllNotificationAlarms(context);
        }

    }
}