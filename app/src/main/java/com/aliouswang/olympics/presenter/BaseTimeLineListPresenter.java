package com.aliouswang.olympics.presenter;

import android.content.Context;

import com.aliouswang.entities.EntityWrap;
import com.aliouswang.olympics.models.TimeLineListModel;
import com.aliouswang.olympics.view.fragment.feed.BaseTimeLineListFragment;

/**
 * Created by aliouswang on 16/3/9.
 */
public abstract class BaseTimeLineListPresenter extends BaseListPresenter{

    protected TimeLineListModel timeLineListModel;

    private BaseTimeLineListFragment timeLineListFragment;

    public BaseTimeLineListPresenter(Context context,
                                           BaseTimeLineListFragment timeLineListFragment) {
        super(context);
        this.timeLineListFragment = timeLineListFragment;
        this.timeLineListModel = getTimeLineListModel();
    }

    protected abstract TimeLineListModel getTimeLineListModel();

    @Override
    public void onFetchSuccess(EntityWrap entityWrap) {
        timeLineListFragment.onFetchSuccess(entityWrap);
    }

    @Override
    public void onFetchError(String error) {
        timeLineListFragment.onFetchError(error);
    }

    @Override
    public void onFetchEmpty() {
        timeLineListFragment.onFetchEmpty();
    }

}
