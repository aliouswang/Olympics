package com.aliouswang.olympics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aliouswang.entities.feeds.TimeLine;
import com.aliouswang.network_lib.ApiConstants;
import com.aliouswang.network_lib.ConfigConstants;
import com.aliouswang.olympics.Sina.Constants;
import com.aliouswang.olympics.interfaces.OnTimeLineItemClickListener;
import com.aliouswang.olympics.presenter.PublicTimeLineFragmentPresenter;
import com.aliouswang.olympics.view.activity.ImageBrowseActivity;
import com.aliouswang.olympics.view.activity.TimeLineDetailActivity;
import com.aliouswang.olympics.view.adapter.TimeLineRecyclerViewAdapter;
import com.aliouswang.olympics.view.fragment.feed.PublicTimeLineListFragment;
import com.aliouswang.utils.SharePreferenceUtil;
import com.hmzl.library.core.presenter.BaseListPresenter;
import com.hmzl.library.core.utils.IntentUtil;
import com.hmzl.library.core.view.activity.BaseRecyclerViewActivity;
import com.hmzl.library.core.view.adapter.BaseRecyclerViewAdapter;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseRecyclerViewActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    private View content;

    private Handler mHandler = new Handler();

    @Override
    protected int getInflateLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(MainActivity.this, mAuthInfo);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSsoHandler.authorizeClientSso(new AuthListener());
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction =
                            manager.beginTransaction()
                                    .replace(R.id.content, new PublicTimeLineListFragment());
                    transaction.commit();
                }
            }, 200);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    PublicTimeLineFragmentPresenter timeLineFragmentPresenter;

    TimeLineRecyclerViewAdapter adapter;

    @Override
    protected BaseRecyclerViewAdapter getRecyclerViewAdapter() {
        if (adapter == null) {
            adapter = new TimeLineRecyclerViewAdapter(mThis);
            adapter.setOnTimeLineItemClickListener(new OnTimeLineItemClickListener() {
                @Override
                public void onClick(View v, TimeLine timeLine) {
                    Intent intent = new Intent(MainActivity.this, ImageBrowseActivity.class);
                    intent.putExtra(IntentUtil.POJO_NAME, timeLine.pic_urls);
                    ActivityTransitionLauncher
                            .with(MainActivity.this)
                            .from(v)
                            .launch(intent);
                }
            });
        }
        return adapter;
    }

    @Override
    protected BaseListPresenter getListPresenter() {
        if (timeLineFragmentPresenter == null) {
            timeLineFragmentPresenter =
                    new PublicTimeLineFragmentPresenter(mThis, this);
        }
        return timeLineFragmentPresenter;
    }

    @Override
    protected void onLoadNextPage() {
        timeLineFragmentPresenter.fetchNext();
        //just for debug
//        String token = SharePreferenceUtil.getPrefString(MainActivity.this,
//                ConfigConstants.TOKEN, "");
//        String url = ApiConstants.HOME_TIME_LINE + "?access_token=" + token
//                + "&count=50&page=1";
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers,
//                                  byte[] responseBody) {
//                String response = new String(responseBody);
//                if (response == null) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  byte[] responseBody, Throwable error) {
//
//            }
//        });
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
//                updateTokenView(false);

                SharePreferenceUtil.
                        setPrefString(MainActivity.this ,ConfigConstants.TOKEN,
                                mAccessToken.getToken());

//                AppAplication.get(MainActivity.this)
//                        .getAppComponent().getApiService()
//                        .getPublicTimeLine(mAccessToken.getToken(),
//                                20, 1, 0).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TimeLineWrap>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(TimeLineWrap timeLineWrap) {
//                        if (timeLineWrap != null) {
//
//                        }
//                    }
//                });

//                String getUrl = "https://api.weibo.com/2/statuses/public_timeline.json";
//                getUrl += "?access_token=" + mAccessToken.getToken();
//                getUrl += "&count=50";
//
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.get(getUrl, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        try {
//                            String body = new String(responseBody, "UTF-8");
//                            Log.e("body", body);
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                    }
//                });

//
//                // 保存 Token 到 SharedPreferences
//                AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);
                Toast.makeText(MainActivity.this,
                        "weibosdk_demo_toast_auth_success", Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "weibosdk_demo_toast_auth_failed";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this,
                    "weibosdk_demo_toast_auth_canceled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(MainActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
