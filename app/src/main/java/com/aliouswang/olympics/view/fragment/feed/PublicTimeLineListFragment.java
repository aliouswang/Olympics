package com.aliouswang.olympics.view.fragment.feed;

import android.view.View;

import com.aliouswang.olympics.presenter.PublicTimeLineFragmentPresenter;

/**
 * Created by aliouswang on 16/3/8.
 */
public class PublicTimeLineListFragment extends BaseTimeLineListFragment
{
    PublicTimeLineFragmentPresenter timeLineFragmentPresenter;

    @Override
    protected void initView(View view) {
        super.initView(view);
        timeLineFragmentPresenter = new PublicTimeLineFragmentPresenter(mContext, this);
        timeLineFragmentPresenter.restartFetch();
    }


}

