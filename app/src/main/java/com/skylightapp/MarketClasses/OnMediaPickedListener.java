package com.skylightapp.MarketClasses;

import java.io.File;

public interface OnMediaPickedListener {
    void onMediaPicked(int requestCode, File file);

    void onMediaPickError(int requestCode, Exception e);

    void onMediaPickClosed(int requestCode);
}
