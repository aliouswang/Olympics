package com.hmzl.library.core.utils;

import android.content.Intent;

/**
 * Created by aliouswang on 16/3/23.
 */
public class IntentUtil {

    public static final String POJO_NAME = "default_pojo_name";

    public static <T> T parseObject(Intent intent, String name) {
        return (T)intent.getSerializableExtra(name);
    }
}
