package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.novel.bean.UserBean
import com.smart.novel.mvp.contract.LoginContract
import com.smart.novel.net.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:登录
 */
class LoginPresenter : LoginContract.Presenter() {
    override fun login(phone: String, code: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.login(phone, code), object : RxObserverListener<UserBean>(mView) {
            override fun onSuccess(result: UserBean?) {
                mView.login(result!!)
            }
        }))
    }

    override fun sendCode(phone: String) {
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.sendCode(phone), object : RxObserverListener<Any>(mView) {
            override fun onSuccess(result: Any?) {
                mView.sendCode(result!!)
            }
        }))
    }

}