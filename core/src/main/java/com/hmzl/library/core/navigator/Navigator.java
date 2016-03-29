package com.hmzl.library.core.navigator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by aliouswang on 16/3/28.
 */
public class Navigator {

    public static void navigate(Context context, Bundle bundle, Class clazz) {
        Intent intent = new Intent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}
