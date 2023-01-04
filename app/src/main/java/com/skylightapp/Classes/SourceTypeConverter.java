package com.skylightapp.Classes;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SourceTypeConverter {
    public String fromValuesToProfileList(ArrayList<Profile> value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Profile>>() {}.getType();
        return gson.toJson(value, type);
    }

    public ArrayList<Profile> toProfileList(String value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Customer>>() {
        }.getType();
        return gson.fromJson(value, type);
    }

    public String fromValuesToList(ArrayList<Customer> value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Customer>>() {}.getType();
        return gson.toJson(value, type);
    }

    //@TypeConverter
    public ArrayList<Customer> toOptionValuesList(String value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Customer>>() {
        }.getType();
        return gson.fromJson(value, type);
    }

    //@TypeConverter
    public static Uri fromUriToString(String value) {
        //Uri myUri = Uri.fromFile(new File(value));
        //return value == null ? null : Uri.parse(Uri.decode(value));
        return value == null ? null : Uri.parse(value);
    }
    //@TypeConverter
    public static String stringToUri(Uri uri) {
        String stringUri;
        stringUri = uri.toString();
        return uri == null ? null : stringUri;
    }
    //@TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    //@TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
