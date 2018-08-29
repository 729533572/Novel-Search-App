package com.smart.novel.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import butterknife.OnClick
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.interfaces.OnRefreshListener
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.adapter.ADA_SearchList
import com.smart.novel.adapter.ADA_TestMultiple
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.UserEntity
import com.smart.novel.mvp.contract.TestContract
import com.smart.novel.mvp.model.TestModel
import com.smart.novel.mvp.presenter.TestPresenter
import com.smart.novel.net.WeatherEntity
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.act_login.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 登录页面
 */
class ACT_Login : BaseMVPActivity<TestPresenter, TestModel>(), TestContract.View, OnRefreshListener, OnLoadMoreListener {
    var mAdapter: ADA_SearchList? = null
    var mMutiAdapter: ADA_TestMultiple? = null
    var mLrecyclViewAdapter: LRecyclerViewAdapter? = null

    var data = ArrayList<UserEntity>()

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
    }

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun startEvents() {
//        mAdapter = ADA_SearchList(this)
        mMutiAdapter = ADA_TestMultiple(this)
        RecyclerViewHelper.initMutiTypeRecyclerView(recyclerview, mMutiAdapter!!, this)
        recyclerview.setOnRefreshListener(this)
        recyclerview.setOnLoadMoreListener(this)

        for (i in 0..50) {
            var user = UserEntity()
            user.id = i
            user.name = "item=" + i
            data.add(user)
        }
    }

    override fun onRefresh() {
        Handler().postDelayed(Runnable { recyclerview.refreshComplete(1) }, 2000)
    }

    override fun onLoadMore() {
        Handler().postDelayed(Runnable { recyclerview.setNoMore(true) }, 2000)
    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    @OnClick(R.id.tv_do_search, R.id.et_search_keywords)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_do_search -> {
                mMvpPresenter.getTestData(multipleStatusView)
            }
            R.id.et_search_keywords -> mMutiAdapter!!.update(data.subList(0, 10), true)
        }
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun getTestData(weather: WeatherEntity) {
//        mAdapter!!.update(data, true)
//        multipleStatusView.showEmpty(R.drawable.ic_reading_no_data, MyApplication.context.getString(R.string.string_empty_bookshelf))
        mMutiAdapter!!.update(data, true)
    }
}