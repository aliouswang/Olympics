package com.aliouswang.olympics.view.activity;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

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

//    @Bind(R.id.et_emojicon)
    EmojiconEditText mEmojiconEditText;

    ImageView img_emoticon;

    EmojiconsFragment emojicons;

    @Override
    protected int getInflateLayout() {
        return R.layout.activity_write_timeline;
    }

    @Override
    protected void initView() {
        super.initView();
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
//        hideEmojiFragment();
    }

    private void hideEmojiFragment() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction().hide(emojicons);
        transaction.commit();
    }

    private void showEmojiFragment() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction().show(emojicons);
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
