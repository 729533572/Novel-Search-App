package com.smart.framework.library.base;

import android.view.View;

import com.smart.framework.library.base.mvp.BaseModel;
import com.smart.framework.library.base.mvp.BasePresenter;
import com.smart.framework.library.base.mvp.IBaseView;
import com.smart.framework.library.common.utils.ClassReflectHelper;
import com.smart.framework.library.loading.MultipleStatusView;

/**
 * Created by JoJo on 2018/8/27.
   wechat：18510829974
  description：mvp的BaseFramen,需联网获取数据的Fragment的父类
 */
public abstract class BaseMVPFragment<P extends BasePresenter,M extends BaseModel> extends BaseLazyFragment implements IBaseView {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMvpPresenter != null) {
            mMvpPresenter.onDestroy();
        }
    }

    protected abstract void startEvents();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showDialogLoading(String msg) {
        toggleShowDialogLoading(msg);
    }

    @Override
    public void dismissDialogLoading() {
        dismissDialogLoad();
    }

    @Override
    public void showLoading(MultipleStatusView multipleStatusView, String msg) {

    }

    @Override
    public void hideLoading() {

    }
    //使用MultipleStatusView，暂时无用
    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}

