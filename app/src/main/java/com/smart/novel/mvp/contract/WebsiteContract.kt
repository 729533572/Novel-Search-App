package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.WebsiteBean
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/9/6.
 * wechat:18510829974
 * description: 小说其他网站的小说信息
 */
class WebsiteContract {
    interface View : IBaseView {
        //通过小说名称、小说作者获取该小说其他网站的小说信息
        fun getOtherWebsiteList(dataList: List<WebsiteBean>)
    }

    interface Model : BaseModel {
        fun getOtherWebsiteList( author: String,book_name: String): Observable<BaseHttpResponse<List<WebsiteBean>>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getOtherWebsiteList(multipleStatusView: MultipleStatusView?,book_name: String, author: String)
    }
}