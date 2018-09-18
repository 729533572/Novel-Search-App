package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.ChapterBean
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

        //根据当前小说来源，切换网址解析
        fun switchWebsite(dataList: List<ChapterBean>)
    }

    interface Model : BaseModel {
        fun getOtherWebsiteList(author: String, book_name: String): Observable<BaseHttpResponse<List<WebsiteBean>>>
        fun switchWebsite(website_id: String, chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getOtherWebsiteList(multipleStatusView: MultipleStatusView?, book_name: String, author: String)
        abstract fun switchWebsite(website_id: String, chapter_number: String)
    }
}