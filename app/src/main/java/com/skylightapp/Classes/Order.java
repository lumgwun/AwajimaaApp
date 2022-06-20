package com.skylightapp.Classes;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

public class Order extends SkyLightPackage {
    public long recordNo;
    public  String status;

    public Order() {
        super();
    }

    public void setId(long recordNo) {
        this.recordNo=recordNo;

    }
    public void setStatus(String status) {
        this.status=status;

    }
    @Override
    public void setNotificationUris(@NonNull ContentResolver cr, @NonNull List<Uri> uris) {

    }
}
