package com.smart.framework.library.base;

import com.smart.framework.library.base.mvp.BaseModel;
import com.smart.framework.library.base.mvp.BasePresenter;
import com.smart.framework.library.base.mvp.IBaseView;
import com.smart.framework.library.common.utils.ClassReflectHelper;
import com.smart.framework.library.loading.MultipleStatusView;

/**
 * 作者：addison on 15/12/15 14:29
 * 邮箱：lsf@yonyou.com
 */
public abstract class BaseFragment<P extends BasePresenter,M extends BaseModel> extends BaseLazyFragment implements IBaseView {

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

//    @Override
//    public void showError(String msg) {
//        toggleShowError(true, msg, null);
//    }
//
//    @Override
//    public void showException(String msg) {
//        toggleShowError(true, msg, null);
//    }
//
//    @Override
//    public void showNetError() {
//        toggleNetworkError(true, null);
//    }
//
//    @Override
//    public void showLoading(String msg) {
//        toggleShowLoading(true, null);
//    }
//
//    @Override
//    public void hideLoading() {
//        toggleShowLoading(false, null);
//    }
//
//    @Override
//    public void showDialogLoading(String msg) {
//        toggleShowDialogLoading(msg);
//    }
//
//    @Override
//    public void dismissDialogLoading() {
//        dismissDialogLoad();
//    }
//
//    @Override
//    public void showBusinessError(ErrorBean error) {
//        if (null != error.getCode() && error.getCode().equals("10000"))
//            toggleshowLogin();
//    }


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

    @Override
    public void showException(String msg) {

    }
}

