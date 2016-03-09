package com.aliouswang.entities.user;

import com.aliouswang.entities.BaseEntity;

/**
 * Created by aliouswang on 16/3/8.
 */
public class User extends BaseEntity{

    public long id;
    public String idStr;
    public String screen_name;
    public String name;
    public String location;
    public String profile_image_url;
    public String cover_image;
    public String cover_image_phone;
    public String profile_url;

    public int followers_count;
    public int friends_count;
    public int pagefriends_count;
    public int favourites_count;

    public String created_at;
    public boolean following;
    public boolean verified;

    public String avatar_large;
    public String avatar_hd;
    public String description;


}
