package com.skylightapp.MapAndLoc;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class OkhttpUtils implements IHttpRequester {

    private static final OkHttpClient client = new OkHttpClient();

    private String runGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String runPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public String get(String url) throws Exception {
        return runGet(url);
    }

    @Override
    public String post(String url, String json) throws Exception{
        return runPost(url,json);
    }
}


