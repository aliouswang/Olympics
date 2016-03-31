package com.aliouswang.olympics.view.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aliouswang.olympics.R;
import com.hmzl.library.core.view.activity.BaseAppActivity;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import butterknife.ButterKnife;

/**
 * Created by aliouswang on 16/3/28.
 */
public class WriteTimeLineActivity extends BaseAppActivity
        implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{

    View root_view;

//    @Bind(R.id.et_emojicon)
    EmojiconEditText mEmojiconEditText;

    ImageView img_emoticon;

    EmojiconsFragment emojicons;

    RelativeLayout rl_bar;

    private int mKeyBoardHeight;

    private int mRootViewHeight;
    private int previousHeightDiffrence;
    private boolean isKeyBoardVisible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.activity_write_timeline;
    }

    @Override
    protected void initView() {
        super.initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.normal_toolbar_menu);
//        setSupportActionBar(toolbar);
        toolbar.setTitle("发表");

        root_view = ButterKnife.findById(this, R.id.root_view);
        mEmojiconEditText = ButterKnife.findById(this, R.id.et_emojicon);

        img_emoticon = ButterKnife.findById(this, R.id.img_emoticon);
        img_emoticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftwareKeyBoard();
                showEmojiFragment();
            }
        });
        emojicons = (EmojiconsFragment)
                getSupportFragmentManager().findFragmentById(R.id.emojicons);
        hideEmojiFragment();

        rl_bar = ButterKnife.findById(this, R.id.rl_bar);

        getKeyboardHeight();
    }

    private void getKeyboardHeight() {
        root_view.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();

                        root_view.getWindowVisibleDisplayFrame(r);

                        int screenHeight = root_view.getRootView().getHeight();
                        int keyboardHeight = screenHeight - (r.bottom);

                        if (previousHeightDiffrence - keyboardHeight > 50)
                        {
                            // Do some stuff here
                        }

                        previousHeightDiffrence = keyboardHeight;
                        if (keyboardHeight> 500)
                        {
                            isKeyBoardVisible = true;
                            hideEmojiFragment();
                        }
                        else
                        {
                            isKeyBoardVisible = false;
                            mRootViewHeight = r.height();
                        }
                    }
                });
    }

    private void hideEmojiFragment() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction().hide(emojicons);
        transaction.commit();
    }

    private void showEmojiFragment() {
        RelativeLayout.LayoutParams rlp =
                (RelativeLayout.LayoutParams) emojicons.getView().getLayoutParams();
        rlp.height = previousHeightDiffrence;
        emojicons.getView().setLayoutParams(rlp);

        rlp = (RelativeLayout.LayoutParams) rl_bar.getLayoutParams();
        rlp.topMargin = previousHeightDiffrence;
        rl_bar.setLayoutParams(rlp);

        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_bottom, R.anim.slide_out_up,
                                R.anim.slide_in_bottom, R.anim.slide_out_up)
                        .addToBackStack("123")
                        .show(emojicons);
        transaction.commit();
    }

    private void hideSoftwareKeyBoard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {

    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
//        EmojiconsFragment.input(mEmojiconEditText, emojicon);
        mEmojiconEditText.setText(mEmojiconEditText.getText() + emojicon.getEmoji());
    }

}
