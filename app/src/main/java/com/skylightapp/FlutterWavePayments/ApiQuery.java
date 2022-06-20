package com.skylightapp.FlutterWavePayments;

import java.util.HashMap;

public class ApiQuery {
    final private HashMap<String, Object> queryMap;


    public ApiQuery() {
        this.queryMap = new HashMap<>();
    }


    public void putParams(String key, Object value) {
        this.queryMap.put(key, value);
    }


    public HashMap<String, Object> getParams() {
        return this.queryMap;
    }

}
