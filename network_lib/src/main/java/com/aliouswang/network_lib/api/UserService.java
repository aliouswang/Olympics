package com.aliouswang.network_lib.api;

import com.aliouswang.entities.user.User;
import com.aliouswang.network_lib.ApiConstants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by aliouswang on 16/3/26.
 */
public interface UserService {

    @GET(ApiConstants.User.GET_USER_INFO)
    Observable<User> getUserInfoById(@Query("access_token") String access_token,
                                     @Query("uid") long uid);

}
