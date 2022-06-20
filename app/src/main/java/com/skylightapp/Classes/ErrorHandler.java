package com.skylightapp.Classes;

import android.content.Context;
import android.widget.Toast;

public class ErrorHandler {
    public static void displayError(Context context, int stringID) {
        String errorText = context.getResources().getString(stringID);
        Toast toast = Toast.makeText(context, errorText, Toast.LENGTH_LONG);
        toast.show();
    }


    public static void displayError(Context context, String errorText) {
        Toast toast = Toast.makeText(context, errorText, Toast.LENGTH_LONG);
        toast.show();
    }


    public static void displayException(Context context, Exception e) {
        String[] exceptionText = e.toString().split(":");
        String errorText = exceptionText[1];
        Toast toast = Toast.makeText(context, errorText, Toast.LENGTH_LONG);
        toast.show();
    }
}
