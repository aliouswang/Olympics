package com.hmzl.library.core.models;

import com.aliouswang.entities.EntityWrap;
import com.hmzl.library.core.presenter.interfaces.OnFetchListener;

import rx.Observable;
import rx.Subscription;

/**
 * Created by aliouswang on 16/3/9.
 */
public interface TimeLineListModel {

//    Subscription getTimeLineList(Observable<TimeLineWrap> observable,
//                                 OnFetchListener onFetchListener,
//                                 boolean enableCache,
//                                 long cacheTime);

    Subscription getDataList(Observable<EntityWrap> observable,
                                 OnFetchListener onFetchListener,
                                 boolean enableCache,
                                 long cacheTime);

}
