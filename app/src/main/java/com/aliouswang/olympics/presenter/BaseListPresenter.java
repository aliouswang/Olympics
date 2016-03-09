package com.aliouswang.olympics.presenter;

import android.content.Context;

import com.aliouswang.entities.EntityWrap;
import com.aliouswang.olympics.presenter.interfaces.IFetchAction;
import com.aliouswang.olympics.presenter.interfaces.OnFetchListener;
import com.aliouswang.utils.ACache;

/**
 * Created by aliouswang on 16/3/9.
 */
public abstract class BaseListPresenter extends BasePresenter
        implements IFetchAction, OnFetchListener {

    protected int page = 1;
    protected int count = 30;
    protected boolean isLoading = false;

    public BaseListPresenter(Context context) {
        super(context);
    }

    protected boolean needCache() {
        return false;
    }

    protected int cacheTime() {
        return ACache.TIME_HOUR;
    }

    @Override
    public void restartFetch() {

    }

    @Override
    public void fetchNext() {

    }

    @Override
    public void onFetchSuccess(EntityWrap entityWrap) {

    }

    @Override
    public void onFetchError(String error) {

    }

    @Override
    public void onFetchEmpty() {

    }
}
