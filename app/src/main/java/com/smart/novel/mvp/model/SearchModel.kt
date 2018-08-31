package com.smart.novel.mvp.model

import com.smart.novel.bean.HotSearchBean
import com.smart.novel.bean.SearchResultBean
import com.smart.novel.mvp.contract.SearchContract
import com.smart.novel.net.BaseHttpResponse
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:搜小说模块
 */

class SearchModel : SearchContract.Model {
    override fun getHotSearchList(): Observable<BaseHttpResponse<List<HotSearchBean>>> {
        return RetrofitRxManager.getRequestService().searchHotList()
    }

    override fun getSearchResultList(keywords: String): Observable<BaseHttpResponse<List<SearchResultBean>>> {
        return RetrofitRxManager.getRequestService().searchNovel(keywords)
    }
}