package com.smart.framework.library.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.smart.framework.library.R;
import com.smart.framework.library.base.mvp.IBaseView;
import com.smart.framework.library.common.ReceiveConstants;
import com.smart.framework.library.loading.MultipleStatusView;
import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：addison on 15/12/15 14:15
 * 邮箱：lsf@yonyou.com
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements IBaseView {

    protected Toolbar mToolbar;

    protected Activity mContext;
    private Unbinder unBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        mContext = this;
        unBinder = ButterKnife.bind(this);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * 刷新频率——打开刷新频率：只需getRefreshTime()返回不为0,且接收一下ReceiveConstants.REFRESH_CURRENT_PAGE的广播即可
     */
    private Timer mTimer; // 计时器，每1秒执行一次任务
    private MyTimerTask mTimerTask; // 计时任务，判断是否未操作时间到达5s
    private long mLastActionTime; // 最近一次的操作时间

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (getRefreshTime() == 0)
                return;

            if (System.currentTimeMillis() - mLastActionTime > getRefreshTime()) {
                // 停止计时任务
                stop();
                // 开始计时
                start();
                // 刷新频率
                sendBroadcast(ReceiveConstants.REFRESH_CURRENT_PAGE);
            }
        }
    }

    /**
     * 开始计时
     */
    protected void start() {
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        // 初始化上次操作时间为登录成功的时间
        mLastActionTime = System.currentTimeMillis();
        // 每过1s检查一次
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * 停止计时任务
     */
    protected void stop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = null;
        mTimerTask = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        if (getRefreshTime() != 0) {
            // 停止计时任务
            stop();
            // 开始计时
            start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);

        if (getRefreshTime() != 0) {
            // 停止计时任务
            stop();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //用户每次操作，重新记录操作时间
        if (getRefreshTime() != 0) {
            mLastActionTime = System.currentTimeMillis();
            if (mTimer != null) {
                // 停止计时任务
                stop();
                // 开始计时
                start();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected abstract long getRefreshTime();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }

    @Override
    public void showLoading(MultipleStatusView multipleStatusView, String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDialogLoading(String msg) {
        toggleShowDialogLoading(msg);
    }

    @Override
    public void dismissDialogLoading() {
        dismissDialogLoad();
    }

    protected abstract boolean isApplyKitKatTranslucency();

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu(){
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

}

