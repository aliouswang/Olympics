package com.aliouswang.network_lib.api;

import com.aliouswang.entities.TimeLineWrap;
import com.aliouswang.network_lib.ApiConstants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by aliouswang on 16/3/8.
 */
public interface ApiService {

    @GET(ApiConstants.PUBLIC_TIME_LINE)
    Observable<TimeLineWrap> getPublicTimeLine(
            @Query("access_token") String access_token,
            @Query("count") int count,
            @Query("page") int page,
            @Query("base_app") int base_app
    );

}
