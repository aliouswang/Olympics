package com.aliouswang.olympics.api;

import com.aliouswang.network_lib.ApiConstants;
import com.aliouswang.network_lib.api.ApiService;
import com.aliouswang.network_lib.api.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by aliouswang on 16/3/8.
 */
@Module
public class ApiServiceModule {

    @Provides
    @Singleton
    ApiService provideApiService() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create(mapper);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.interceptors().add(new BasicRequestInterceptor());
//        okHttpClient.networkInterceptors().add(new StethoInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(jacksonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    UserService provideUserService() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create(mapper);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.interceptors().add(new BasicRequestInterceptor());
//        okHttpClient.networkInterceptors().add(new StethoInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(jacksonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(UserService.class);
    }

}
