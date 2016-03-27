package com.aliouswang.network_lib;

/**
 * Created by aliouswang on 16/3/8.
 */
public class ApiConstants {

    public static final String BASE_SING_WEIBO_URL = "https://api.weibo.com/2/";

    public static final String BASE_URL = BASE_SING_WEIBO_URL;

    public static final String PUBLIC_TIME_LINE =
            BASE_URL + "statuses/public_timeline.json";

    public static final String HOME_TIME_LINE =
            BASE_URL + "statuses/home_timeline.json";

    public static final String BILATERAL_TIME_LINE =
            BASE_URL + "statuses/bilateral_timeline.json";

    public static class User{
        public static final String USERS = BASE_URL + "users";
        public static final String GET_USER_INFO = USERS + "/show.json";
    }


}
