package com.skylightapp.MapAndLoc;

import static com.skylightapp.BuildConfig.MAP_API;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

public class ApiUtil {
    private static final String TAG = ApiUtil.class.getSimpleName();

    private static volatile ApiUtil apiUtil;
    private final IHttpRequester requester;

    public static ApiUtil init() {
        if (apiUtil == null) {
            if (apiUtil == null) {
                apiUtil = new ApiUtil();
            }
        }
        return apiUtil;
    }

    public ApiUtil() {
        requester = new OkhttpUtils();
        //requester = new HttpUtils();
    }
    public String getGooglePostion(com.google.android.gms.maps.model.LatLng latLng) throws Exception {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latLng.latitude + "," + latLng.longitude + "&key=" + MAP_API;
        String formatted_address = "";
        String json = requester.get(url);
        //LogUtils.debug(TAG,"json="+json+"，url="+url);
        GoogleAddress googleAddress = JsonUtil.json2Bean(json, GoogleAddress.class);
        List<GoogleAddress.ResultsBean> results = googleAddress.getResults();
        formatted_address = results.get(0).getFormatted_address();
        return formatted_address;
    }

    public void getGooglePostion(com.google.android.gms.maps.model.LatLng latLng, MyMapCallBack myCallBack) {
        ThreadManager.getBackgroundPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String googlePostion = getGooglePostion(latLng);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallBack.onSuccess(googlePostion);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    myCallBack.onFailure(e);
                }
            }
        });
    }
    private class NonoTask implements Runnable {
        private MyMapCallBack myCallBack;
        private int taskType;

        public NonoTask(int taskType, MyMapCallBack myCallBack) {
            this.myCallBack = myCallBack;
        }

        @Override
        public void run() {
            String result = "";
        }
    }

}
