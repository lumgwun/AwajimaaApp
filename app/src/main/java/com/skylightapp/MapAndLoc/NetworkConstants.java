package com.skylightapp.MapAndLoc;

import org.jetbrains.annotations.NotNull;

public class NetworkConstants {
    @NotNull
    public static final String MAP_BASE_URL = "https://mapapi.cloud.huawei.com/mapApi/v1/";
    @NotNull
    public static final String GOOGLE_MAP_BASE_URL = "https://mapapi.cloud.huawei.com/mapApi/v1/";
    @NotNull
    public static final NetworkConstants INSTANCE;

    private NetworkConstants() {
    }

    static {
        NetworkConstants networkConstants = new NetworkConstants();
        INSTANCE = networkConstants;
    }
}
