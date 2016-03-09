package com.aliouswang.olympics.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aliouswang on 16/3/8.
 */
public abstract class BaseFragment extends Fragment{

    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(getInflaterLayout(), container, false);
        initView(view);
        return view;
    }

    protected abstract int getInflaterLayout();
    protected abstract void initView(View view);
}
