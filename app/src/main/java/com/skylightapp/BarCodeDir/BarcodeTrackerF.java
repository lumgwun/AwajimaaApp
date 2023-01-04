package com.skylightapp.BarCodeDir;

import android.content.Context;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeTrackerF extends Tracker<Barcode> implements MultiProcessor.Factory<Barcode> {

    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
    private Context mContext;

    public BarcodeTrackerF(GraphicOverlay<BarcodeGraphic> mGraphicOverlay,
                           Context mContext) {
        this.mGraphicOverlay = mGraphicOverlay;
        this.mContext = mContext;
    }

    public BarcodeTrackerF(GraphicOverlay<BarcodeGraphic> mGraphicOverlay, BarcodeGraphic graphic, Context mContext) {

    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeGraphic graphic = new BarcodeGraphic(mGraphicOverlay);
        return new BarcodeTrackerF(mGraphicOverlay, graphic, mContext);
    }

}