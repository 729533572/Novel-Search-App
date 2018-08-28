package com.smart.novel.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.adapter.ADA_SearchList
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
class ACT_Login : BaseMVPActivity<TestPresenter, TestModel>(), TestContract.View {
    var mAdapter: ADA_SearchList? = null
    var data = ArrayList<UserEntity>()

    override fun getBundleExtras(extras: Bundle?) {

    }

    override fun startEvents() {
        mAdapter = ADA_SearchList(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = mAdapter

        for (i in 0..50) {
            var user = UserEntity()
            user.name = "item=" + i
            data.add(user)
        }
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
            R.id.et_search_keywords -> mAdapter!!.update(data.subList(0, 10), true)
        }
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun getTestData(weather: WeatherEntity) {
        mAdapter!!.update(data, true)
    }
}