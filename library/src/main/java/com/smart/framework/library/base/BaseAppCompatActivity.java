package com.smart.framework.library.base;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.smart.framework.library.BaseApplication;
import com.smart.framework.library.R;
import com.smart.framework.library.common.Constants;
import com.smart.framework.library.loading.LoadingDialog;
import com.smart.framework.library.loading.VaryViewHelperController;
import com.smart.framework.library.manager.ActivityTaskManager;
import com.smart.framework.library.netstatus.NetChangeObserver;
import com.smart.framework.library.netstatus.NetStateReceiver;
import com.smart.framework.library.netstatus.NetUtils;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * 作者：addison on 11/12/15 14:01
 * 邮箱：lsf@yonyou.com
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    /**
     * 日志标签，值为子类的classname
     */
    protected static String TAG_LOG = null;
    /**
     * 屏幕宽度
     */
    protected int mScreenWidth = 0;
    /**
     * 屏幕高度
     */
    protected int mScreenHeight = 0;
    /**
     * 屏幕密度
     */
    protected float mScreenDensity = 0.0f;
    protected Context mContext = null;

    /**
     * 网络状态监听
     */
    protected NetChangeObserver mNetChangeObserver = null;

    /**
     * loading control
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    /**
     * OverridePendingTransition
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE, ZOOM
    }

    private LoadingDialog mLoadingDialog;

    private boolean mIsRegisterReceiver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
//                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    overridePendingTransition(R.anim.enter_trans, R.anim.exit_scale);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
//                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    overridePendingTransition(R.anim.bottom_in, 0);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case ZOOM:
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    break;
            }
        }

        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
//        SmartBarUtils.hide(getWindow().getDecorView());
//        setTranslucentStatus(isApplyStatusBarTranslucency());

        mContext = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseActManager.getInstance().addActivity(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };

        NetStateReceiver.registerObserver(mNetChangeObserver);

        initViewsAndEvents();

        addToTask();

        registerReceiver();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    /**
     * set loading target view
     *
     * @param view
     */
    public void setLoadingTargetView(View view) {
        if (null != view) {
            mVaryViewHelperController = new VaryViewHelperController(view);
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseActManager.getInstance().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
//                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    overridePendingTransition(R.anim.enter_scale, R.anim.exit_trans);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
//                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    overridePendingTransition(0, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case ZOOM:
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }

        if (mIsRegisterReceiver && broadcastReceiver != null) {
            try {
                mIsRegisterReceiver = false;
                this.unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
            } finally {
                broadcastReceiver = null;
            }
        }
    }

    protected abstract void getBundleExtras(Bundle extras);

    protected abstract int getContentViewLayoutID();


    /**
     * loading view
     */
    protected abstract View getLoadingTargetView();


    /**
     * 初始化通信event
     */
    protected abstract void initViewsAndEvents();

    /**
     * network connected
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

    /**
     * is applyStatusBarTranslucency
     *
     * @return
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * is bind eventBus
     *
     * @return
     */
    protected abstract boolean isBindEventBusHere();

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Intent intent) {
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    protected void readyGo(Intent intent) {
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show login
     */
    protected void toggleshowLogin() {
        BaseApplication.getInstance().showLoginDialog();
    }


    /**
     * toggle show loading
     *
     * @param
     */
    protected void toggleShowDialogLoading(String msg) {
        mLoadingDialog = new LoadingDialog.Builder(this).setMessage(msg).create();
        mLoadingDialog.show();
    }


    protected void dismissDialogLoad() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, int emptyImg, String emptyMsg, String refreshTxt, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(emptyImg, emptyMsg, refreshTxt, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * show no clickable msg
     *
     * @param img
     * @param msg
     */
    protected void showEmpty(int img, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        mVaryViewHelperController.showEmpty(img, msg);
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     */
    protected void showServerError() {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        mVaryViewHelperController.showServerError();

    }

//    public void onEventMainThread(EventCenter eventCenter) {
//        if (null != eventCenter) {
//            onEventComming(eventCenter);
//        }
//    }


    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }
    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setHeaderTitle(String title) {

        if (TextUtils.isEmpty(title)) {
            return;
        }
        TextView titleTV = (TextView) this.findViewById(R.id.tv_title);
        titleTV.setText(title);
    }

    /**
     * 返回键，关闭aty
     *
     * @param view
     */
    public void onActionFinish(View view) {
        finish();
    }

    /**
     * 把当前Activity添加到activityMap统一管理
     */
    protected void addToTask() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        ActivityTaskManager.getInstance().putActivity(runningActivity, this);
    }

    ;

    /**
     * 注册广播
     */
    protected void registerReceiver() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            // 注册拿到加载状态广播接收器
            registerReceiver(broadcastReceiver, new IntentFilter(info.packageName + Constants.BROADCASE_ADDRESS));

            mIsRegisterReceiver = true;
        } catch (Exception e) {
        }
    }

    // 注册广播 ,用于接收耗时任务的处理进度
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);

                if (intent.getAction().equals(info.packageName + Constants.BROADCASE_ADDRESS)) {
                    Bundle bundle = intent.getExtras();
                    Integer i = bundle.getInt(Constants.BROADCASE_INTENT);
                    if (i != null) {
                        BaseAppCompatActivity.this.onReceiveBroadcast(i, bundle);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
    };

    /**
     * 发送一个广播
     *
     * @param value
     */
    protected void sendBroadcast(int value) {
        sendBroadcast(this, value);
    }

    /**
     * 发送一个广播
     *
     * @param value
     */
    private void sendBroadcast(Context context, int value) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Intent intent = new Intent();
            intent.setAction(info.packageName + Constants.BROADCASE_ADDRESS);
            intent.putExtra(Constants.BROADCASE_INTENT, value);
            context.sendBroadcast(intent);
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    /**
     * 接收其他类型的广播
     *
     * @param intent
     */
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
    }

    /**
     * 解决了EditText弹出软键盘关闭不了的问题
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
