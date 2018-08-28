package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.databinding.FraBookshelfBinding
import com.smart.novel.mvp.contract.TestContract
import com.smart.novel.mvp.model.TestModel
import com.smart.novel.mvp.presenter.TestPresenter
import com.smart.novel.net.WeatherEntity
import kotlinx.android.synthetic.main.fra_bookshelf.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_BookShelf : BaseMVPFragment<TestPresenter, TestModel>(), TestContract.View {

    /**
     * companion object {}内：静态方法
     */
    companion object {
        fun getInstance(): FRA_BookShelf {
            val fragment = FRA_BookShelf()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun startEvents() {
        mMvpPresenter.getTestData(multipleStatusView)

    }

    @OnClick(R.id.tv_read_history)
    fun onClick(view: View) {
        readyGo(ACT_Login::class.java)
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_bookshelf
    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
    }

    override fun onUserInvisible() {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showBusinessError(error: ErrorBean?) {
        multipleStatusView.showError()
    }

    override fun getTestData(weatherEntity: WeatherEntity) {
//        multipleStatusView.showEmpty(R.drawable.ic_reading_no_data, MyApplication.context.getString(R.string.string_empty_bookshelf))
        var viewBinder = viewDataBinding as FraBookshelfBinding
        viewBinder.weather = weatherEntity
    }
}
