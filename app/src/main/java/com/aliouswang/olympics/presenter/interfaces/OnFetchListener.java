package com.aliouswang.olympics.presenter.interfaces;

import com.aliouswang.entities.EntityWrap;

/**
 * Created by aliouswang on 16/3/8.
 */
public interface OnFetchListener {

    void onFetchSuccess(EntityWrap entityWrap);

    void onFetchError(String error);

    void onFetchEmpty();

}
