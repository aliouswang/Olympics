package com.aliouswang.olympics;

import com.aliouswang.network_lib.api.ApiService;
import com.aliouswang.network_lib.api.UserService;
import com.aliouswang.olympics.api.ApiServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aliouswang on 16/3/8.
 */
@Singleton
@Component(modules = {ApiServiceModule.class})
public interface AppComponent {

    ApiService getApiService();

    UserService getUserService();

}
