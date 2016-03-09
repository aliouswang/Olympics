package com.aliouswang.olympics.view.fragment.feed;

import android.view.View;

import com.aliouswang.entities.EntityWrap;
import com.aliouswang.olympics.presenter.interfaces.OnFetchListener;
import com.aliouswang.olympics.view.fragment.BaseListFragment;

/**
 * Created by aliouswang on 16/3/8.
 */
public class BaseTimeLineListFragment extends BaseListFragment
                implements OnFetchListener {

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onFetchSuccess(EntityWrap entityWrap) {
        if (entityWrap != null) {

        }
    }

    @Override
    public void onFetchError(String error) {

    }

    @Override
    public void onFetchEmpty() {

    }


}
