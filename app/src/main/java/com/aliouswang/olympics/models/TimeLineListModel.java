package com.aliouswang.olympics.models;

import com.aliouswang.entities.TimeLineWrap;
import com.aliouswang.olympics.presenter.interfaces.OnFetchListener;

import rx.Observable;
import rx.Subscription;

/**
 * Created by aliouswang on 16/3/9.
 */
public interface TimeLineListModel {

    Subscription getTimeLineList(Observable<TimeLineWrap> observable,
                                 OnFetchListener onFetchListener,
                                 boolean enableCache,
                                 long cacheTime);

}
