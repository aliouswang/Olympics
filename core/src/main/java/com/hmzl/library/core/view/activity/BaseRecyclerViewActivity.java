package com.hmzl.library.core.view.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

/**
 * Created by aliouswang on 16/3/21.
 */
public abstract class BaseRecyclerViewActivity<T extends BaseEntity,
        E extends EntityWrap> extends BaseActivity implements OnFetchListener{

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected BaseRecyclerViewAdapter baseRecyclerViewAdapter;
    protected BaseListPresenter<E> baseListPresenter;
    protected EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;

    @Override
    protected int getInflateLayout() {
        return R.layout.base_recycler_activity_layout;
    }

    @Override
    protected void parseIntent(Intent intent) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        setupSwipeRefreshLayout();
        setupRecyclerView();
        setupListPresenter();
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


    protected void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.font_orange,
                R.color.font_orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseListPresenter.restartFetch();
            }
        });
    }

    protected void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_content);
        mRecyclerView.setLayoutManager(getLayoutManager());
        RecyclerView.ItemAnimator itemAnimator = new ListLoadMoreItemAnimator(mThis);
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        mRecyclerView.setItemAnimator(itemAnimator);
        baseRecyclerViewAdapter = getRecyclerViewAdapter();
        if (baseRecyclerViewAdapter != null) {
            endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(mThis,
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

    protected abstract BaseRecyclerViewAdapter getRecyclerViewAdapter();

    protected abstract BaseListPresenter<E> getListPresenter();

    protected abstract void onLoadNextPage();

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mThis);
    }
}
