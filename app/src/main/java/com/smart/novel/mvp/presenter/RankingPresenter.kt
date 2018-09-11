package com.smart.novel.mvp.presenter

import com.smart.framework.library.base.mvp.RxObserverListener
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.RankingContract
import com.smart.novel.net.RetrofitRxManager

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:排行榜
 */

class RankingPresenter : RankingContract.Presenter() {
    override fun getRankList(multipleStatusView: MultipleStatusView, type: String) {
        multipleStatusView.showLoading()
        rxManager.addObserver(RetrofitRxManager.doRequest(mModel.getRankList(type), object : RxObserverListener<List<NovelBean>>(mView) {
            override fun onSuccess(result: List<NovelBean>) {
                multipleStatusView.showContent()
                mView.getRankList(result)
            }
        }))
    }
}