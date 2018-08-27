package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.mvp.contract.TestContract
import com.smart.novel.net.WeatherEntity
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:
 */
class TestPresenter : TestContract.Presenter() {
    override fun getTestData(multipleStatusView: MultipleStatusView) {
        multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getTestData(), object : RxObserverListener<Any>(mView) {
            override fun onNext(result: Any?) {
                var weather=result as WeatherEntity
                mView.getTestData(weather)
                multipleStatusView.showContent()
            }
        }))
    }
}