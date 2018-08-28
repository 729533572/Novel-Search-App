package com.smart.novel.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.OnClick
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.interfaces.OnRefreshListener
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.recyclerview.ProgressStyle
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_SearchList
import com.smart.novel.adapter.ADA_TestMultiple
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.UserEntity
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
class ACT_Login : BaseMVPActivity<TestPresenter, TestModel>(), TestContract.View, OnRefreshListener, OnLoadMoreListener {
    var mAdapter: ADA_SearchList? = null
    var mMutiAdapter: ADA_TestMultiple? = null
    var mLrecyclViewAdapter: LRecyclerViewAdapter? = null

    var data = ArrayList<UserEntity>()

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun startEvents() {
//        mAdapter = ADA_SearchList(this)
        mMutiAdapter = ADA_TestMultiple(this)
        mLrecyclViewAdapter = LRecyclerViewAdapter(mMutiAdapter)
        recyclerview.adapter = mLrecyclViewAdapter
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setRefreshProgressStyle(ProgressStyle.TriangleSkewSpin)
//        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        recyclerview.setOnRefreshListener(this)
        recyclerview.setOnLoadMoreListener(this)
        //设置头部文字颜色
        recyclerview.setHeaderViewColor(R.color.color_3AC270, R.color.color_73787d, R.color.color_f9f9f9)
        //设置底部加载颜色-loading动画颜色,文字颜色,footer的背景颜色
        recyclerview.setFooterViewColor(R.color.color_3AC270, R.color.color_73787d, R.color.color_f9f9f9)
        //设置底部加载文字提示
        recyclerview.setFooterViewHint(MyApplication.context.getString(R.string.list_footer_loading), MyApplication.context.getString(R.string.list_footer_end), MyApplication.context.getString(R.string.list_footer_network_error))

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

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_login
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
        mMutiAdapter!!.update(data, true)
    }
}