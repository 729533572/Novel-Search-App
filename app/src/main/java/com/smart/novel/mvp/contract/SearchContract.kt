package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.HotSearchBean
import com.smart.novel.bean.SearchResultBean
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description: 搜小说模块
 */
class SearchContract {
    interface View : IBaseView {
        fun getSearchResultList(dataList: List<SearchResultBean>)
        fun getHotSearchList(hotList: List<HotSearchBean>)
    }

    interface Model : BaseModel {
        fun getSearchResultList(keywords: String): Observable<BaseHttpResponse<List<SearchResultBean>>>
        fun getHotSearchList(): Observable<BaseHttpResponse<List<HotSearchBean>>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getSearchResultList(multipleStatusView: MultipleStatusView, keywords: String)
        abstract fun getHotSearchList()
    }
}