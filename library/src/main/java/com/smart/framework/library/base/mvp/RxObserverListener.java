package com.smart.framework.library.base.mvp;

import com.smart.framework.library.BaseApplication;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.common.utils.StringUtil;
import com.smart.framework.library.net.retrofit.BaseObserverListener;


/**
 * Created by JoJo on 2018/4/1.
 * wechat：18510829974
 * description：网络数据监听
 */
public abstract class RxObserverListener<T> implements BaseObserverListener<T> {
    private IBaseView mView;

    protected RxObserverListener(IBaseView view) {
        this.mView = view;
    }

    @Override
    public void onComplete() {

    }

    /**
     * 统一处理异常情况：包括没网、数据返回错误等
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        RetrofitException.ResponseThrowable responseThrowable = RetrofitException.getResponseThrowable(e);
        Elog.e("TAG", "e.code=" + responseThrowable.code + responseThrowable.message);
        ErrorBean errorBean = new ErrorBean();
        errorBean.setMsg(responseThrowable.message);
        errorBean.setCode(StringUtil.getString(responseThrowable.code));
        if (mView != null) {
            mView.showException(errorBean);
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(BaseApplication.getInstance(), responseThrowable.message, false);
        }
    }

    @Override
    public void onBusinessError(ErrorBean errorBean) {
        if (mView != null) {
            mView.showBusinessError(errorBean);
            mView.dismissDialogLoading();
            CommonUtils.makeEventToast(BaseApplication.getInstance(), errorBean.getMsg(), false);
            Elog.e("TAG", "onBusinessError msg=" + errorBean.getMsg());
        }
    }
}
