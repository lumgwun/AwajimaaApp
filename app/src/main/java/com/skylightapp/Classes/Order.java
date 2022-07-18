package com.skylightapp.Classes;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

public class Order  {
    public int orderNo;
    public  String orderStatus;

    public Order() {
        super();
    }

    public void setId(int orderNo) {
        this.orderNo=orderNo;

    }
    public void setDocStatus(String orderStatus) {
        this.orderStatus= orderStatus;

    }

}
