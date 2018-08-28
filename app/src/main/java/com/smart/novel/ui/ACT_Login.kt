package com.smart.novel.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.base.ACT_Base

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 登录页面
 */
class ACT_Login : ACT_Base() {
    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }
    @OnClick(R.id.tv_read_history,R.id.tv_total)
    fun onClick(view: View){
        when(view.id){
            R.id.tv_read_history-> CommonUtils.makeEventToast(MyApplication.context,"登录",false)
            R.id.tv_total-> CommonUtils.makeEventToast(MyApplication.context,"请输入账号",false)
        }
    }
    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun initViewsAndEvents() {
//        super.initViewsAndEvents()
        Log.e("TAG","initViewsAndEvents")
        setHeaderTitle("")
    }

    override fun toggleOverridePendingTransition(): Boolean {
        return false
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
}