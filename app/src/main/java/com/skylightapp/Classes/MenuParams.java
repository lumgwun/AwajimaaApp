package com.skylightapp.Classes;

import android.content.Context;
import android.content.Intent;

import java.util.List;



public class MenuParams {
    private static List<MenuObject> activity;
    int actionBarSize;
    boolean setClosableOutside=false;
    private boolean closableOutside;
    private List<MenuObject> menuObjects;
    Context context;

    public static List<MenuObject> getMenuObjects(Context context, String string) {
        return activity;
    }

    public static Intent getMenuActivityIntent(Context context, String string, int position) {
        return null;
    }


    public void setActionBarSize(int actionBarSize) { this.actionBarSize = actionBarSize; }
    public int getActionBarSize() {
        return actionBarSize;
    }

    public void setClosableOutside(boolean closableOutside) {
        this.closableOutside = closableOutside;
    }

    public boolean getClosableOutside() {
        return closableOutside;
    }

    public void setMenuObjects(List<MenuObject> menuObjects) {
        this.menuObjects = menuObjects;
    }

    public List<MenuObject> getMenuObjects() {
        return menuObjects;
    }
}
