package com.skylightapp.MarketInterfaces;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PhotoUploadService {
    @Multipart
    @POST(".")
    Call<ResponseBody> upload(
            @Part MultipartBody.Part image,
            @Part("user_id") RequestBody userId,
            @Part("image_position") RequestBody position,
            @Part("qb_session_token") RequestBody qbSessionToken
    );
}
