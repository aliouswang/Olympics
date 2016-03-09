package com.aliouswang.olympics.models;

import com.aliouswang.entities.TimeLineWrap;
import com.aliouswang.olympics.presenter.interfaces.OnFetchListener;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aliouswang on 16/3/9.
 */
public class PublicTimeLineListModelImpl implements TimeLineListModel {

    @Override
    public Subscription getTimeLineList(Observable<TimeLineWrap> observable,
                                        final OnFetchListener onFetchListener,
                                        boolean enableCache,
                                        long cacheTime) {
        if (observable != null) {
            return observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TimeLineWrap>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (onFetchListener != null) {
                                onFetchListener.onFetchError(e.toString());
                            }
                        }

                        @Override
                        public void onNext(TimeLineWrap timeLineWrap) {
                            if (onFetchListener != null) {
                                if (timeLineWrap != null && !timeLineWrap.isEmpty()) {
                                    onFetchListener.onFetchSuccess(timeLineWrap);
                                }else {
                                    onFetchListener.onFetchEmpty();
                                }
                            }
                        }
                    });
        } else {
            return null;
        }
    }
}
