package com.aliouswang.olympics.presenter;

import android.content.Context;

import com.aliouswang.network_lib.ConfigConstants;
import com.aliouswang.olympics.AppAplication;
import com.aliouswang.olympics.models.PublicTimeLineListModelImpl;
import com.aliouswang.utils.SharePreferenceUtil;
import com.hmzl.library.core.models.TimeLineListModel;
import com.hmzl.library.core.presenter.BaseListPresenter;
import com.hmzl.library.core.presenter.interfaces.OnFetchListener;

import rx.Observable;

/**
 * Created by aliouswang on 16/3/8.
 */
public class PublicTimeLineFragmentPresenter extends BaseListPresenter {

    public PublicTimeLineFragmentPresenter(Context context,
                                           OnFetchListener timeLineListFragment) {
        super(context, timeLineListFragment);
    }

    @Override
    protected TimeLineListModel getTimeLineListModel() {
        if (timeLineListModel == null) {
            return new PublicTimeLineListModelImpl();
        }
        return timeLineListModel;
    }

    @Override
    protected Observable getLoadTask() {
        String token = SharePreferenceUtil.getPrefString(context,
                ConfigConstants.TOKEN, "");
        return AppAplication.get(context)
                .getAppComponent().getApiService()
                .getHomeTimeLine(token, count, curLoadPage);
    }
}
