package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.AppSharedPreferences
import com.smart.framework.library.common.utils.TimeCount
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.UserBean
import com.smart.novel.db.manager.DbManager
import com.smart.novel.mvp.contract.LoginContract
import com.smart.novel.mvp.model.LoginModel
import com.smart.novel.mvp.presenter.LoginPresenter
import com.smart.novel.util.SharePreConstants
import kotlinx.android.synthetic.main.act_login.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 登录页面
 */
class ACT_Login : BaseMVPActivity<LoginPresenter, LoginModel>(), LoginContract.View {

    var countDownTmer: TimeCount? = null
    //验证码倒计时时间：60s
    val msgTime = 60 * 1000
    //倒计时间隔时间
    val intervalTime = 1000

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun startEvents() {
        //验证码倒计时
        countDownTmer = TimeCount(btn_get_code, this)
    }

    @OnClick(R.id.rb_login, R.id.btn_get_code)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_get_code -> {
                countDownTmer!!.start()
                mMvpPresenter.sendCode(et_phone_number.text.toString())
            }
            R.id.rb_login -> {
                mMvpPresenter.login(et_phone_number.text.toString(), et_code.text.toString())
            }
        }
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun showException(error: ErrorBean?) {
    }

    override fun login(userInfo: UserBean) {
        MyApplication.isLogin = true
        DbManager.getInstance().insert(UserBean::class.java, userInfo)
        AppSharedPreferences(MyApplication.context).putString(SharePreConstants.USER_ID, userInfo.id.toString())
        finish()
    }

    override fun sendCode(data: Any) {
    }

}