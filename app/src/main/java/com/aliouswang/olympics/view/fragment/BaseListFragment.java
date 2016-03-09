package com.aliouswang.olympics.view.fragment;

import com.aliouswang.olympics.R;
import com.aliouswang.olympics.interactor.BasicViewInteractor;

/**
 * Created by aliouswang on 16/3/8.
 */
public abstract class BaseListFragment extends BaseFragment implements
        BasicViewInteractor{

    @Override
    protected int getInflaterLayout() {
        return R.layout.timeline_list_fragment_layout;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage() {

    }
}
