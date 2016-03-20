package com.hmzl.library.core.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aliouswang on 16/3/17.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getInflateLayout());
        parseIntent();
        initVariables();
        initView();
        loadData();
    }

    protected abstract int getInflateLayout();

    protected abstract void parseIntent();

    protected abstract void initVariables();

    protected abstract void initView();

    protected abstract void loadData();

}