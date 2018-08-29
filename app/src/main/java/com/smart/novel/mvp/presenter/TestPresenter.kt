package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.db.bean.ReadHistoryEntity
import com.smart.novel.mvp.contract.TestContract
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:
 */
class TestPresenter : TestContract.Presenter() {
    override fun getTestData(multipleStatusView: MultipleStatusView) {
        multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getTestData(), object : RxObserverListener<List<ReadHistoryEntity>>(mView) {
            override fun onSuccess(result: List<ReadHistoryEntity>) {
                mView.getTestData(result)
                multipleStatusView.showContent()
            }
        }))
    }
}