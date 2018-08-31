package com.smart.novel.mvp.model

import com.smart.novel.bean.RankListBean
import com.smart.novel.mvp.contract.LoginContract
import com.smart.novel.mvp.contract.RankingContract
import com.smart.novel.net.BaseHttpResponse
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:排行榜
 */

class RankingModel : RankingContract.Model {
    override fun getRankList(type: String): Observable<BaseHttpResponse<List<RankListBean>>> {
        return RetrofitRxManager.getRequestService().getRankList(type)
    }
}