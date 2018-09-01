package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.novel.bean.UserBean
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description: 注册登录，发送验证码
 */
class LoginContract {
    interface View : IBaseView {
        fun sendCode(data: Any)
        fun login(userInfo: UserBean)
    }

    interface Model : BaseModel {
        fun sendCode(phone: String): Observable<BaseHttpResponse<Any>>
        fun login(phone: String, code: String): Observable<BaseHttpResponse<UserBean>>
    }

    abstract class Presenter : BasePresenter<LoginContract.View, LoginContract.Model>() {
        abstract fun sendCode(phone: String)
        abstract fun login(phone: String, code: String)
    }
}