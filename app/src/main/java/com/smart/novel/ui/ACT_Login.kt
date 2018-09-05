package com.smart.novel.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.log.Elog
import com.smart.framework.library.common.utils.AppSharedPreferences
import com.smart.framework.library.common.utils.TimeCount
import com.smart.framework.library.common.utils.login.VerifyInputUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.UserBean
import com.smart.novel.db.manager.DbManager
import com.smart.novel.mvp.contract.LoginContract
import com.smart.novel.mvp.model.LoginModel
import com.smart.novel.mvp.presenter.LoginPresenter
import com.smart.novel.util.BroadCastConstant
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
    var sharePre: AppSharedPreferences? = null
    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun startEvents() {
        sharePre = AppSharedPreferences(MyApplication.context)
        //验证码倒计时
        countDownTmer = TimeCount(rb_get_code, this)

        // addTextChangedListener ???不好使
//        RxTextView.textChanges(et_phone_number).subscribe { text ->
//            Elog.e("","textChanges")
//        }

//        val etPhoneObservable = RxTextView.textChanges(et_phone_number)
//        val etCodeObservable = RxTextView.textChanges(et_code)

//        // 相当于合并
//        Observable.combineLatest(etPhoneObservable, etCodeObservable,
//                BiFunction<CharSequence, CharSequence, Any> { phone, code ->
//                    // 设置按钮是否可用(或者改变背景颜色)
//                    val b = !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code)
//                    rb_login.isChecked = b
//                    return@BiFunction this
//                })
        initListener()
    }

    private fun initListener() {
        et_phone_number.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Elog.e("txr", s.toString())
                if (!TextUtils.isEmpty(s.toString())) {
                    rb_get_code.setTextColor(ContextCompat.getColor(MyApplication.context, R.color.color_3AC270))
                } else {
                    rb_get_code.setTextColor(ContextCompat.getColor(MyApplication.context, R.color.color_999999))
                }
            }
        })
    }

    @OnClick(R.id.rb_login, R.id.rb_get_code)
    fun onClick(view: View) {
        when (view.id) {
            R.id.rb_get_code -> {
                if (VerifyInputUtils.doValidatePhone(mContext, et_phone_number)) mMvpPresenter.sendCode(et_phone_number.text.toString())
            }
            R.id.rb_login -> {
                if (VerifyInputUtils.doValidatePhoneAndCode(mContext, et_phone_number, et_code)) mMvpPresenter.login(et_phone_number.text.toString(), et_code.text.toString())

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

    override fun sendCode(data: Any) {
        countDownTmer!!.start()
    }

    override fun login(userInfo: UserBean) {
        //保存登录状态及用户信息
        MyApplication.isLogin = true
        sharePre!!.putString(SharePreConstants.USER_ID, userInfo.id.toString())
        sharePre!!.putString(SharePreConstants.USER_PHONE, userInfo.phone)
        sharePre!!.putString(SharePreConstants.USER_NAME, userInfo.user_name)
        sharePre!!.putString(SharePreConstants.USER_HEAD_AVATAR, userInfo.chat_head_uri)
        sharePre!!.putBoolean(SharePreConstants.LOGOUT, false)
        DbManager.getInstance().insertOrReplace(UserBean::class.java, userInfo)
        sendBroadcast(BroadCastConstant.LOGIN_SUCCESS)
        finish()
    }

}