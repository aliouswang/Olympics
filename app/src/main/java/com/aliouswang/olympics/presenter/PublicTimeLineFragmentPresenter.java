package com.aliouswang.olympics.presenter;

import android.content.Context;

import com.aliouswang.entities.TimeLineWrap;
import com.aliouswang.entities.feeds.TimeLine;
import com.aliouswang.entities.images.TimeLineImage;
import com.aliouswang.network_lib.ConfigConstants;
import com.aliouswang.olympics.AppAplication;
import com.aliouswang.olympics.MainActivity;
import com.aliouswang.olympics.models.PublicTimeLineListModelImpl;
import com.aliouswang.utils.SharePreferenceUtil;
import com.hmzl.library.core.models.TimeLineListModel;
import com.hmzl.library.core.presenter.BaseListPresenter;
import com.hmzl.library.core.presenter.interfaces.OnFetchListener;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by aliouswang on 16/3/8.
 */
public class PublicTimeLineFragmentPresenter extends BaseListPresenter {

    private MainActivity.Section mHomeDrawerSection = MainActivity.Section.NAV_ALL;

    public PublicTimeLineFragmentPresenter(Context context,
                                           OnFetchListener timeLineListFragment) {
        super(context, timeLineListFragment);
    }

    public void setSection(MainActivity.Section section) {
        this.mHomeDrawerSection = section;
        restartFetch();
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
        Observable<TimeLineWrap> task;
        if (mHomeDrawerSection == MainActivity.Section.NAV_ALL) {
            task = AppAplication.get(context)
                    .getAppComponent().getApiService()
                    .getHomeTimeLine(token, count, curLoadPage);
        }else if (mHomeDrawerSection == MainActivity.Section.NAV_BIBLATERAL){
            task = AppAplication.get(context)
                    .getAppComponent().getApiService()
                    .getBilateralTimeLine(token, count, curLoadPage);
        }else {
            task = null;
        }
        return task != null ? task.map(new Func1<TimeLineWrap, TimeLineWrap>() {

                    @Override
                    public TimeLineWrap call(TimeLineWrap o) {
                        for (TimeLine timeLine : o.statuses) {
                            ArrayList<TimeLineImage>
                                    timeLineImages = timeLine.pic_urls;
                            if (timeLineImages != null) {
                                int size = timeLineImages.size();
                                for (int i =0;i < size; i ++) {
                                    TimeLineImage image = timeLineImages.get(i);
                                    image.bmiddle_pic =
                                            image.thumbnail_pic.replace("thumbnail",
                                            "bmiddle");
                                    image.original_pic =
                                            image.thumbnail_pic.replace("thumbnail",
                                                    "original");
                                }
                            }
                        }
                        return o;
                    }
                }) : null;
    }
}
