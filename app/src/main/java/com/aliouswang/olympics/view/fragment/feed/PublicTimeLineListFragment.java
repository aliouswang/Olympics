package com.aliouswang.olympics.view.fragment.feed;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aliouswang.entities.EntityWrap;
import com.aliouswang.olympics.presenter.PublicTimeLineFragmentPresenter;
import com.aliouswang.olympics.view.adapter.TimeLineRecyclerViewAdapter;
import com.hmzl.library.core.presenter.BaseListPresenter;
import com.hmzl.library.core.view.adapter.BaseRecyclerViewAdapter;
import com.hmzl.library.core.view.fragment.BaseRecyclerViewFragment;

/**
 * Created by aliouswang on 16/3/8.
 */
public class PublicTimeLineListFragment extends BaseRecyclerViewFragment
{
    PublicTimeLineFragmentPresenter timeLineFragmentPresenter;

    TimeLineRecyclerViewAdapter adapter;

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        if (adapter == null) {
            adapter = new TimeLineRecyclerViewAdapter(mContext);
        }
        return adapter;
    }

    @Override
    protected void onLoadNextPage() {
        timeLineFragmentPresenter.fetchNext();

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
//        return new WrapContentLinearLayoutManager(mContext);
        return new LinearLayoutManager(mContext);
    }

    @Override
    protected BaseListPresenter getListPresenter() {
        if (timeLineFragmentPresenter == null) {
            timeLineFragmentPresenter = new PublicTimeLineFragmentPresenter(mContext, this);
        }
        return timeLineFragmentPresenter;
    }

    @Override
    public void onFetchSuccess(EntityWrap entityWrap) {
        super.onFetchSuccess(entityWrap);
//        if (entityWrap != null && entityWrap instanceof TimeLineWrap) {
//            TimeLineWrap timeLineWrap = (TimeLineWrap) entityWrap;
//            if (timeLineFragmentPresenter.isLoadFirstPage()) {
//                adapter.setDataList(timeLineWrap.statuses);
//            }else {
//                adapter.addDataList(timeLineWrap.statuses);
//            }
//            endlessRecyclerViewAdapter.restartAppending();
//        }
    }

    @Override
    protected void onLazyLoad() {

    }
}

