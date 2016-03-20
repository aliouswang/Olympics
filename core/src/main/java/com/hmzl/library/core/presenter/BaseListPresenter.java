package com.hmzl.library.core.presenter;

import android.content.Context;

import com.aliouswang.entities.EntityWrap;
import com.aliouswang.utils.ACache;
import com.hmzl.library.core.models.TimeLineListModel;
import com.hmzl.library.core.presenter.interfaces.IFetchAction;
import com.hmzl.library.core.presenter.interfaces.OnFetchListener;
import com.hmzl.library.core.view.fragment.BaseRecyclerViewFragment;

import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;


/**
 * Created by aliouswang on 16/3/9.
 */
public abstract class BaseListPresenter<E extends EntityWrap> extends BasePresenter
        implements IFetchAction, OnFetchListener {

    protected int page = 1;
    protected int curLoadPage = 1;
    protected int count = 20;
    protected AtomicBoolean isLoading = new AtomicBoolean(false);

    private BaseRecyclerViewFragment timeLineListFragment;

    protected TimeLineListModel timeLineListModel;

    public BaseListPresenter(Context context) {
        super(context);
    }

    public BaseListPresenter(Context context,
                                     BaseRecyclerViewFragment timeLineListFragment) {
        super(context);
        this.timeLineListFragment = timeLineListFragment;
        this.timeLineListModel = getTimeLineListModel();
    }


    protected boolean needCache() {
        return false;
    }

    protected int cacheTime() {
        return ACache.TIME_HOUR;
    }

    protected abstract Observable<E> getLoadTask();

    public boolean isLoadFirstPage() {
        return curLoadPage == 1;
    }

    @Override
    public void restartFetch() {
        if (isLoading.get()) return;
        isLoading.set(true);
        curLoadPage = 1;
        Observable loadTask = getLoadTask();
        if (loadTask != null) {
            getTimeLineListModel().getDataList(loadTask,
                    this, needCache(), cacheTime());
        }
    }

    @Override
    public void fetchNext() {
        if (isLoading.get()) return;
        isLoading.set(true);
        curLoadPage = page + 1;
        Observable loadTask = getLoadTask();
        if (loadTask != null) {
            getTimeLineListModel().getDataList(loadTask,
                    this, needCache(), cacheTime());
        }
    }

    @Override
    public void onFetchSuccess(EntityWrap entityWrap) {
        page = curLoadPage;
        isLoading.set(false);
        timeLineListFragment.onFetchSuccess(entityWrap);
    }

    @Override
    public void onFetchError(String error) {
        isLoading.set(false);
        timeLineListFragment.onFetchError(error);
    }

    @Override
    public void onFetchEmpty() {
        isLoading.set(false);
        timeLineListFragment.onFetchEmpty();
    }

    protected abstract TimeLineListModel getTimeLineListModel();
}
