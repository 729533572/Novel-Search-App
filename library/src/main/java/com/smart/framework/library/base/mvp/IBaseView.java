package com.smart.framework.library.base.mvp;

import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.loading.MultipleStatusView;

/**
 * Created by JoJo on 2018/3/30.
 * wechat：18510829974
 * description：
 */
public interface IBaseView {
    /**
     * show loading message
     *
     * @param msg
     */
    void showLoading(MultipleStatusView multipleStatusView, String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * dialog loading
     */
    void showDialogLoading(String msg);

    /**
     * dismiss  dialog loading
     */
    void dismissDialogLoading();

    /**
     * show business error
     */
    void showBusinessError(ErrorBean error);

    /**
     * 网络连接失败，超时等
     *
     * @param msg
     */
    void showException(String msg);
}
