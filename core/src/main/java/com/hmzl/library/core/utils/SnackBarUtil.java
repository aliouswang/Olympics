package com.hmzl.library.core.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;

/**
 * Created by aliouswang on 16/3/23.
 */
public class SnackBarUtil {

    public static void showSnackBar(Activity activity, String message) {
        showSnackBar(activity, message, Snackbar.LENGTH_SHORT);
    }

    public static void showSnackBar(Activity activity, String message, int duration) {
        Snackbar.make(activity.getWindow().getDecorView(),
                message,
                duration)
                .show();
    }

}
