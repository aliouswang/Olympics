package com.hmzl.library.core.utils;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.View;

/**
 * Created by aliouswang on 16/3/22.
 */
public class PopupMenuUtil {

    public static void showPopupMenu(Context context,
                                     View view,
                                     int menuLayout,
                                     PopupMenu.OnMenuItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater()
                .inflate(menuLayout, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(listener);
        popupMenu.show();
    }

}
