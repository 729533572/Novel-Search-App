package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.RankListBean
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description: 排行榜
 */
class RankingContract {
    interface View : IBaseView {
        fun getRankList(dataList: List<RankListBean>)
    }

    interface Model : BaseModel {
        fun getRankList(type: String): Observable<BaseHttpResponse<List<RankListBean>>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getRankList(multipleStatusView: MultipleStatusView, type: String)
    }
}