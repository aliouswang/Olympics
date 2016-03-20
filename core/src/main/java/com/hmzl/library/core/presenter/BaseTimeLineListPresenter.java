//package com.hmzl.library.core.presenter;
//
//import android.content.Context;
//
//import com.aliouswang.entities.EntityWrap;
//import com.hmzl.library.core.models.TimeLineListModel;
//import com.hmzl.library.core.view.fragment.BaseRecyclerViewFragment;
//
///**
// * Created by aliouswang on 16/3/9.
// */
//public abstract class BaseTimeLineListPresenter extends BaseListPresenter{
//
//
//
//
//
//    public BaseTimeLineListPresenter(Context context,
//                                     BaseRecyclerViewFragment timeLineListFragment) {
//        super(context);
//        this.timeLineListFragment = timeLineListFragment;
//        this.timeLineListModel = getTimeLineListModel();
//    }
//
//
//
//    @Override
//    public void onFetchSuccess(EntityWrap entityWrap) {
//        timeLineListFragment.onFetchSuccess(entityWrap);
//    }
//
//    @Override
//    public void onFetchError(String error) {
//        timeLineListFragment.onFetchError(error);
//    }
//
//    @Override
//    public void onFetchEmpty() {
//        timeLineListFragment.onFetchEmpty();
//    }
//
//}
