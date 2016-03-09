package com.aliouswang.olympics.presenter.helper;

import android.content.Context;

import com.aliouswang.entities.EntityWrap;
import com.aliouswang.olympics.presenter.interfaces.IFetchAction;
import com.aliouswang.olympics.presenter.interfaces.OnFetchListener;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aliouswang on 16/3/8.
 */
public abstract class FetchHelper<T extends EntityWrap> implements IFetchAction{

    protected Context context;
    protected OnFetchListener onFetchListener;
    protected int count = 30;
    protected int page = 1;

    public FetchHelper(Context context) {
        this.context = context;
    }

    public FetchHelper(Context context, OnFetchListener onFetchListener) {
        this.context = context;
        this.onFetchListener = onFetchListener;
    }

    protected abstract Observable<T> getFetchObservable();

    @Override
    public void restartFetch() {
        getFetchObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onFetchListener.onFetchError(e.toString());
                    }

                    @Override
                    public void onNext(T t) {
                        onFetchListener.onFetchSuccess(t);
                    }
                });
    }

    @Override
    public void fetchNext() {

    }
}
