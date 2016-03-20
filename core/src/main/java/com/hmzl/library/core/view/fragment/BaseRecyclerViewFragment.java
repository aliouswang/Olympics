package com.hmzl.library.core.view.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aliouswang.entities.BaseEntity;
import com.aliouswang.entities.EntityWrap;
import com.aliouswang.entities.TimeLineWrap;
import com.hmzl.library.core.R;
import com.hmzl.library.core.presenter.BaseListPresenter;
import com.hmzl.library.core.presenter.interfaces.OnFetchListener;
import com.hmzl.library.core.view.adapter.BaseRecyclerViewAdapter;
import com.hmzl.library.core.view.animator.ListLoadMoreItemAnimator;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
//import com.marshalchen.ultimaterecyclerview.animators.SlideInUpAnimator;

/**
 * Created by aliouswang on 16/3/14.
 */
public abstract class BaseRecyclerViewFragment<T extends BaseEntity,
        E extends EntityWrap> extends BaseFragment implements OnFetchListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected BaseRecyclerViewAdapter baseRecyclerViewAdapter;
    protected BaseListPresenter<E> baseListPresenter;
    protected EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;

    @Override
    protected void initView(View view) {
        setupSwipeRefreshLayout(view);
        setupRecyclerView(view);
        setupListPresenter();
    }

    protected void setupSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.font_orange,
                R.color.font_orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseListPresenter.restartFetch();
            }
        });
    }

    protected void setupRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_content);
        mRecyclerView.setLayoutManager(getLayoutManager());
        RecyclerView.ItemAnimator itemAnimator = new ListLoadMoreItemAnimator(mContext);
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        mRecyclerView.setItemAnimator(itemAnimator);
        baseRecyclerViewAdapter = getRecyclerViewAdapter();
        if (baseRecyclerViewAdapter != null) {
            endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(mContext,
                    baseRecyclerViewAdapter,
                    new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            onLoadNextPage();
                        }
                    });
            endlessRecyclerViewAdapter.onDataReady(true);
        }
        AlphaAnimatorAdapter animatorAdapter =
                new AlphaAnimatorAdapter(endlessRecyclerViewAdapter, mRecyclerView);
        mRecyclerView.setAdapter(animatorAdapter);

    }

    protected void setupListPresenter() {
        if (baseListPresenter == null) {
            baseListPresenter = getListPresenter();
        }
//        baseListPresenter.restartFetch();
    }

    @Override
    protected int getInflaterLayout() {
        return R.layout.base_recycler_fragment_layout;
    }

    protected abstract BaseRecyclerViewAdapter getRecyclerViewAdapter();

    protected abstract BaseListPresenter<E> getListPresenter();

    protected abstract void onLoadNextPage();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void onFetchSuccess(EntityWrap entityWrap) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (entityWrap != null && entityWrap instanceof TimeLineWrap) {
            TimeLineWrap timeLineWrap = (TimeLineWrap) entityWrap;
            if (baseListPresenter.isLoadFirstPage()) {
                baseRecyclerViewAdapter.setDataList(timeLineWrap.statuses);
            }else {
                baseRecyclerViewAdapter.addDataList(timeLineWrap.statuses);
            }
            endlessRecyclerViewAdapter.restartAppending();
        }
    }

    @Override
    public void onFetchError(String error) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchEmpty() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
