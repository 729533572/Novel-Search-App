package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import butterknife.OnClick
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.log.Elog
import com.smart.framework.library.common.utils.AppSharedPreferences
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_MineList
import com.smart.novel.bean.MineBean
import com.smart.novel.db.AppDBHelper
import com.smart.novel.dialog.DialogUtils
import com.smart.novel.mvp.model.BookShelfModel
import com.smart.novel.mvp.presenter.BookShelfPresenter
import com.smart.novel.util.BroadCastConstant
import com.smart.novel.util.SharePreConstants
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import kotlinx.android.synthetic.main.fra_mine.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 我的
 */
class FRA_Mine : BaseMVPFragment<BookShelfPresenter, BookShelfModel>() {
    var mAdapter: ADA_MineList? = null
    var iconArray = listOf<Int>(R.drawable.ic_btn_mine_qq_read_group, R.drawable.ic_btn_mine_customer_service, R.drawable.ic_btn_mine_about_us, R.drawable.ic_btn_mine_statement_clause, R.drawable.ic_btn_mine_update)
    val nameArray = listOf<String>("QQ阅读群", "联系客服", "关于我们", "声明条款", "检查更新")
    var sharePre: AppSharedPreferences? = null

    companion object {
        fun getInstance(): FRA_Mine {
            val fragment = FRA_Mine()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_mine
    }

    override fun startEvents() {
        sharePre = AppSharedPreferences(activity)
        mAdapter = ADA_MineList(activity)
        listviewMine.adapter = mAdapter

        var data = ArrayList<MineBean>()
        for (i in 0..iconArray.size - 1) {
            data.add(MineBean(iconArray.get(i), nameArray.get(i)))
        }
        mAdapter!!.update(data, true)

        //展示用户信息
        showUserInfo(MyApplication.isLogin)

        initListener()
    }

    private fun showUserInfo(isLogin: Boolean) {
        if (isLogin) {
            val user = AppDBHelper.getLoginUser()
            tv_un_login.visibility = View.GONE
            ll_logined.visibility = View.VISIBLE
            btn_logot.visibility = View.VISIBLE
            tv_user_phone.setText(user!!.phone)
            tv_user_name.setText(user!!.user_name)
        } else {
            tv_un_login.visibility = View.VISIBLE
            ll_logined.visibility = View.GONE
            btn_logot.visibility = View.GONE
        }
    }

    private fun initListener() {
        listviewMine.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when (position) {
                2 -> readyGo(ACT_AboutUs::class.java)
            }
        }
    }

    @OnClick(R.id.tv_un_login, R.id.btn_logot)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_un_login -> {
                readyGo(ACT_Login::class.java)
            }
        //退出登录
            R.id.btn_logot -> {
                DialogUtils.showConfirmDialog(activity, "登录", "确定要退出登录吗?", object : DialogUtils.OnConfirmListener {
                    override fun onDialogConfirm() {
                        //清除cookie
                        RetrofitRxManager.persistentCookieJar!!.clear()
                        RetrofitRxManager.persistentCookieJar!!.clearSession()
                        showUserInfo(false)
                        MyApplication.isLogin = false
                        //清除用户信息
                        sharePre!!.putBoolean(SharePreConstants.LOGOUT, true)
                        sharePre!!.putString(SharePreConstants.USER_ID, "")
                        sharePre!!.putString(SharePreConstants.USER_NAME, "")
                        sharePre!!.putString(SharePreConstants.USER_PHONE, "")
                        sharePre!!.putString(SharePreConstants.USER_HEAD_AVATAR, "")
                        sendBroadcast(BroadCastConstant.LOGOUT)
                    }
                })
            }
        }
    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun showException(error: ErrorBean?) {

    }

    override fun onFirstUserVisible() {
        Elog.e("TAG", " onFirstUserVisible() ")
    }

    override fun onUserVisible() {
        showUserInfo(MyApplication.isLogin)
    }

    override fun onUserInvisible() {
    }

    override fun getLoadingTargetView(): View? {
        return null
    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }

    fun tabCheck() {
//        CommonUtils.makeEventToast(MyApplication.context, "我的", false)
    }
}