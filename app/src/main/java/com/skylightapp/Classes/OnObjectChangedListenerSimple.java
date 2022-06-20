package com.skylightapp.Classes;


import com.skylightapp.Interfaces.OnObjectExistListener;

public abstract class OnObjectChangedListenerSimple<T extends User> implements OnObjectExistListener<T> {

    //@Override
    public void onError(String errorText) {
    }
}
