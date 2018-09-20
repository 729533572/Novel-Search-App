package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.mvp.contract.TestContract
import com.smart.novel.net.RetrofitRxManager
import com.smart.novel.net.WeatherEntity

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:
 */
class TestPresenter : TestContract.Presenter() {
    override fun getTestData(multipleStatusView: MultipleStatusView) {
        multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getTestData(), object : RxObserverListener<WeatherEntity.DataBean>(mView) {
            override fun onSuccess(result: WeatherEntity.DataBean) {
                multipleStatusView.showContent()
                mView.getTestData(result)
            }
        }))
    }
}