package com.aliouswang.olympics;

import android.app.Application;
import android.content.Context;

import com.aliouswang.olympics.api.ApiServiceModule;

/**
 * Created by aliouswang on 16/3/8.
 */
public class AppAplication extends Application{

    private AppComponent appComponent;

    public static AppAplication get(Context context) {
        return (AppAplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .apiServiceModule(new ApiServiceModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
