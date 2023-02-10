package com.brickendon.hdplus.webservice;

import static com.brickendon.hdplus.utils.AppConstants.BASE_URL;
import static com.brickendon.hdplus.utils.AppConstants.BASE_URL_DYNAMIC;

import androidx.annotation.NonNull;

import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.brickendon.hdplus.utils.Utils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit retrofit = null;
    public static String apiBaseUrl = "https://api.hybridhero.com/";
    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.BODY);
        Builder httpClient = new Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request;
                if (Utils.getToken().isEmpty() || Utils.getToken()==null || Utils.getToken().equals("")) {
                    request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .build();
                } else {
                    request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", "Bearer " + Utils.getToken())
                            .build();
                }
                return chain.proceed(request);
            }
        });
        httpClient.addInterceptor(logging);
        return buildRetrofit(httpClient);
    }

    @NonNull
    private static Retrofit buildRetrofit(Builder httpClient) {
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(5, TimeUnit.MINUTES);
        httpClient.writeTimeout(5, TimeUnit.MINUTES);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_DYNAMIC)
                    .client(httpClient.build()).addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit;
    }

}
