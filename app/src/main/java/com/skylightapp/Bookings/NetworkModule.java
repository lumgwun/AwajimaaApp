package com.skylightapp.Bookings;

import com.google.android.datatransport.runtime.dagger.Provides;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skylightapp.BuildConfig;
import com.skylightapp.MapAndLoc.NetworkConstants;
import com.skylightapp.MapAndLoc.RemoteService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(){

        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor( chain -> {
                    HttpUrl url = chain.request().url().newBuilder()
                            .addQueryParameter("key", BuildConfig.MAP_API)
                            .build();
                    Request request = chain.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .url(url)
                            .build();
                    return chain.proceed(request);
                })
                .retryOnConnectionFailure(true)
                .build();

    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient okHttpClient){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(NetworkConstants.GOOGLE_MAP_BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    public RemoteService getRemoteService(Retrofit retrofit){
        return retrofit.create(RemoteService.class);
    }
}
