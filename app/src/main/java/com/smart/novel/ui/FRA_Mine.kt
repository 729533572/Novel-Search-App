package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import butterknife.OnClick
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_MineList
import com.smart.novel.bean.MineBean
import com.smart.novel.databinding.FraMineBinding
import com.smart.novel.db.AppDBHelper
import com.smart.novel.mvp.model.BookShelfModel
import com.smart.novel.mvp.presenter.BookShelfPresenter
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
        mAdapter = ADA_MineList(activity)
        listviewMine.adapter = mAdapter

        var data = ArrayList<MineBean>()
        for (i in 0..iconArray.size - 1) {
            data.add(MineBean(iconArray.get(i), nameArray.get(i)))
        }
        mAdapter!!.update(data, true)


        initListener()
    }

    private fun initListener() {
        listviewMine.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    2 -> readyGo(ACT_AboutUs::class.java)
                }
            }
        })
    }

    @OnClick(R.id.tv_un_login)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_un_login -> {
                readyGo(ACT_Login::class.java)
            }
        }
    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun showException(error: ErrorBean?) {

    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
        if (MyApplication.isLogin) {
            (viewDataBinding as FraMineBinding).user = AppDBHelper.getLoginUser()
            tv_un_login.visibility = View.GONE
            ll_logined.visibility = View.VISIBLE
        } else {
            tv_un_login.visibility = View.VISIBLE
            ll_logined.visibility = View.GONE
        }
    }

    override fun onUserInvisible() {
    }

    override fun getLoadingTargetView(): View? {
        return null
    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }
}