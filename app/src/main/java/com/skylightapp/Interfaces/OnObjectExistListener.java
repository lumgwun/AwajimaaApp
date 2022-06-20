package com.skylightapp.Interfaces;


import com.skylightapp.Classes.User;

public interface OnObjectExistListener<P extends User> {
    public void onDataChanged(boolean exist);

}
