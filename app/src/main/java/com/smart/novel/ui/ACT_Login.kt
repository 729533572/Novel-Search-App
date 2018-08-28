package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.databinding.ActLoginBinding
import com.smart.novel.mvp.contract.TestContract
import com.smart.novel.mvp.model.TestModel
import com.smart.novel.mvp.presenter.TestPresenter
import com.smart.novel.net.WeatherEntity
import kotlinx.android.synthetic.main.act_login.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 登录页面
 */
class ACT_Login : BaseMVPActivity<TestPresenter,TestModel>(),TestContract.View{

    override fun startEvents() {

    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }
    @OnClick(R.id.tv_do_search)
    fun onClick(view: View){
        when(view.id){
            R.id.tv_do_search-> {
                CommonUtils.makeEventToast(MyApplication.context,"搜索",false)
                mMvpPresenter.getTestData(multipleStatusView)
            }
        }
    }
    override fun getLoadingTargetView(): View? {
        return null
    }
    override fun toggleOverridePendingTransition(): Boolean {
        return false
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
    override fun getTestData(weather: WeatherEntity) {
        var viewBinder = viewDataBinding as ActLoginBinding
        viewBinder.weather = weather
    }
}