package com.aliouswang.olympics.view.activity;

import android.content.Intent;

import com.aliouswang.entities.feeds.TimeLine;
import com.aliouswang.olympics.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hmzl.library.core.manager.ImageManager;
import com.hmzl.library.core.view.activity.BaseActivity;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

/**
 * Created by aliouswang on 16/3/22.
 */
public class TimeLineDetailActivity extends BaseActivity {

    private ExitActivityTransition exitTransition;

    private SimpleDraweeView img_avatar_head;

    private TimeLine timeLine;

    @Override
    protected int getInflateLayout() {
        return R.layout.timeline_item_detail_layout;
    }

    @Override
    protected void parseIntent() {
        Intent intent = getIntent();
        timeLine = (TimeLine) intent.getSerializableExtra("pojo");
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView() {
        img_avatar_head = (SimpleDraweeView) findViewById(R.id.img_avatar_head);
        ImageManager.loadImageWithFresco(img_avatar_head, timeLine.user.avatar_large);
        exitTransition = ActivityTransition.with(getIntent()).
                to(findViewById(R.id.img_avatar_head)).start(mSaveInstanceState);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
        super.onBackPressed();
    }
}
