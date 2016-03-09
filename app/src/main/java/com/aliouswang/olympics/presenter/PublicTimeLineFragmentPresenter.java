package com.aliouswang.olympics.presenter;

import android.content.Context;

import com.aliouswang.network_lib.ConfigConstants;
import com.aliouswang.olympics.AppAplication;
import com.aliouswang.olympics.models.TimeLineListModel;
import com.aliouswang.olympics.models.PublicTimeLineListModelImpl;
import com.aliouswang.olympics.view.fragment.feed.BaseTimeLineListFragment;
import com.aliouswang.utils.SharePreferenceUtil;

/**
 * Created by aliouswang on 16/3/8.
 */
public class PublicTimeLineFragmentPresenter extends BaseTimeLineListPresenter{

    public PublicTimeLineFragmentPresenter(Context context, BaseTimeLineListFragment timeLineListFragment) {
        super(context, timeLineListFragment);
    }

    @Override
    protected TimeLineListModel getTimeLineListModel() {
        if (timeLineListModel == null) {
            return new PublicTimeLineListModelImpl();
        }return timeLineListModel;
    }

    @Override
    public void restartFetch() {
        super.restartFetch();
        String token = SharePreferenceUtil.getPrefString(context,
                ConfigConstants.TOKEN, "");
        timeLineListModel.getTimeLineList(AppAplication.get(context)
                .getAppComponent().getApiService()
                .getPublicTimeLine(token, count, page, 0),
                this, needCache(), cacheTime());
    }

    @Override
    public void fetchNext() {
        super.fetchNext();

    }
}
