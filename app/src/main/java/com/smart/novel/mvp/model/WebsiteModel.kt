package com.smart.novel.mvp.model

import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.WebsiteBean
import com.smart.novel.mvp.contract.WebsiteContract
import com.smart.novel.net.BaseHttpResponse
import com.smart.novel.net.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/9/6.
 * wechat:18510829974
 * description:小说其他网站的小说信息
 */

class WebsiteModel : WebsiteContract.Model {
    override fun switchWebsite(website_id: String, chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>> {
        return RetrofitRxManager.getRequestService().switchWebsite(website_id,chapter_number)
    }

    override fun getOtherWebsiteList( author: String,book_name: String): Observable<BaseHttpResponse<List<WebsiteBean>>> {
        return RetrofitRxManager.getRequestService().getWebsiteList(author,book_name)
    }
}