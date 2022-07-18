package com.skylightapp.Classes;

import android.content.Context;
import android.content.Intent;

import com.skylightapp.MapAndLoc.GoogleMapActivity;
import com.skylightapp.MapAndLoc.ListingFiltersActivity;
import com.skylightapp.MapAndLoc.MapSettingsActivity;
import com.skylightapp.R;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;



@SuppressWarnings("ALL")
public class ContextMenu {
    private static Context context;


    public static List<MenuObject> getMenuObjects(Context newContext, String menuTypes) {
        context = newContext;

        List<MenuObject> menuObjects = new ArrayList<>();

        String[] parts = menuTypes.split("-");
        for (String type : parts) {
            try {
                menuObjects.add(parseMenuType(type));
            } catch (InvalidParameterException e) {
            }
        }

        return menuObjects;
    }

    private static MenuObject parseMenuType (String type) {
        MenuObject menu;

        switch (type)
        {
            case "CloseMenu":

                menu = new MenuObject(context.getResources().getString(R.string.close_menu));

                menu.setResource(R.drawable.ic__category);
                break;
            case "SaveListing":
                menu = new MenuObject(context.getResources().getString(R.string.save_listings));
                menu.setResource(R.drawable.add_loc_);
                break;
            case "GoogleMap" :

                menu = new MenuObject(context.getResources().getString(R.string.google_map));
                menu.setResource(R.drawable.map_foreground);
                break;
            case "MapSettings" :
                menu = new MenuObject(context.getResources().getString(R.string.map_settings));
                menu.setResource(R.drawable.search_foreground);
                break;
            case "ListingSettings" :
                menu = new MenuObject(context.getResources().getString(R.string.listing_filters));
                menu.setResource(R.drawable.map_foreground);
                break;
            default :
                throw new InvalidParameterException("Trying to display an unknown menu type");
        }

        return menu;
    }


    public static Intent getMenuActivityIntent(Context newContext, String menuTypes, int position) {
        context = newContext;

        String[] parts = menuTypes.split("-");
        String type = parts[position];
        return new Intent(context, parseMenuClass(type));
    }

    private static Class parseMenuClass (String type) {
        switch (type)
        {
            case "CloseMenu":
                throw new InvalidParameterException("Cannot open a screen for CloseMenu");

            case "SaveListing":
                throw new InvalidParameterException("Cannot open a screen for SaveListing");

            case "GoogleMap" :

                return GoogleMapActivity.class;

            case "MapSettings" :

                return MapSettingsActivity.class;

            case "ListingSettings" :


                return ListingFiltersActivity.class;

            default :
                throw new InvalidParameterException("Trying to open an unknown menu type");
        }
    }
}
