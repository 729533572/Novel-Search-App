package com.smart.novel.base;

import android.view.View;
import android.view.ViewGroup;

import com.hazz.kotlinmvp.utils.StatusBarUtil;
import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.base.mvp.BaseModel;
import com.smart.framework.library.base.mvp.BasePresenter;
import com.smart.framework.library.base.mvp.IBaseView;
import com.smart.framework.library.common.utils.ClassReflectHelper;
import com.smart.framework.library.netstatus.NetUtils;

/**
 * Created by JoJo on 2018/8/27.
 * wechat：18510829974
 * description：联网的Activity的抽象类
 */
public abstract class BaseMVPActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity implements IBaseView {

    protected P mMvpPresenter;
    protected M mModel;

    @Override
    protected void initViewsAndEvents() {
        //MVP
        mMvpPresenter = ClassReflectHelper.getT(this, 0);
        mModel = ClassReflectHelper.getT(this, 1);
        if (this instanceof IBaseView) {
            if (mMvpPresenter != null && mModel != null) {
                mMvpPresenter.setVM(this, mModel);
            }
        }
        startEvents();
    }

    protected abstract void startEvents();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMvpPresenter != null) {
            mMvpPresenter.onDestroy();
        }
    }

    @Override
    protected long getRefreshTime() {
        return 0;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void handleStatusBar(StatusBarMode mode) {
        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        ViewGroup contentView = (ViewGroup) rootView.getChildAt(0);
        switch (mode) {
            case DARK_FULLSCREEN:
                StatusBarUtil.Companion.darkMode(this);
                contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(),
                        contentView.getPaddingRight(), contentView.getPaddingBottom());
                break;
            case DARK_NO_FULLSCREEN:
                StatusBarUtil.Companion.darkMode(this);
                contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + StatusBarUtil.Companion.getStatusBarHeight(this),
                        contentView.getPaddingRight(), contentView.getPaddingBottom());
                break;
            case LIGHT_FULLSCREEN:
                StatusBarUtil.Companion.immersive(this);
                contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(),
                        contentView.getPaddingRight(), contentView.getPaddingBottom());
                break;
            case LIGHT_NO_FULLSCREEN:
                StatusBarUtil.Companion.immersive(this);
                contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + StatusBarUtil.Companion.getStatusBarHeight(this),
                        contentView.getPaddingRight(), contentView.getPaddingBottom());
                break;
        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    //使用MultipleStatusView，暂时无用
    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }
}
