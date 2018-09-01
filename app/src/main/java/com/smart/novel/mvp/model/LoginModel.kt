package com.smart.novel.mvp.model

import com.smart.novel.bean.UserBean
import com.smart.novel.mvp.contract.LoginContract
import com.smart.novel.net.BaseHttpResponse
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:登录
 */

class LoginModel : LoginContract.Model {
    override fun login(phone: String, code: String): Observable<BaseHttpResponse<UserBean>> {
        return RetrofitRxManager.getRequestService().login(phone, code)
    }

    override fun sendCode(phone: String): Observable<BaseHttpResponse<Any>> {
        return RetrofitRxManager.getRequestService().sendCode(phone)
    }
}