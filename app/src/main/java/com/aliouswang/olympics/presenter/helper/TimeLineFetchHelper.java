package com.aliouswang.olympics.presenter.helper;

import android.content.Context;

import com.aliouswang.network_lib.ConfigConstants;
import com.aliouswang.olympics.AppAplication;
import com.aliouswang.olympics.presenter.interfaces.OnFetchListener;
import com.aliouswang.utils.SharePreferenceUtil;

import rx.Observable;

/**
 * Created by aliouswang on 16/3/8.
 */
public class TimeLineFetchHelper extends FetchHelper{

    public TimeLineFetchHelper(Context context) {
        super(context);
    }

    public TimeLineFetchHelper(Context context, OnFetchListener onFetchListener) {
        super(context, onFetchListener);
    }

    @Override
    protected Observable getFetchObservable() {
        String token = SharePreferenceUtil.getPrefString(context,
                ConfigConstants.TOKEN, "");
        return AppAplication.get(context)
                .getAppComponent().getApiService()
                .getPublicTimeLine(token, count, page, 0);
    }
}
