package com.smart.novel.mvp.model

import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.RankingContract
import com.smart.novel.net.BaseHttpResponse
import com.smart.novel.net.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:排行榜
 */

class RankingModel : RankingContract.Model {
    override fun getRankList(type: String): Observable<BaseHttpResponse<List<NovelBean>>> {
        return RetrofitRxManager.getRequestService().getRankList(type)
    }
}