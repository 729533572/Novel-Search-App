package com.smart.novel.mvp.model

import com.smart.novel.bean.WebsiteBean
import com.smart.novel.mvp.contract.WebsiteContract
import com.smart.novel.net.BaseHttpResponse
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/9/6.
 * wechat:18510829974
 * description:小说其他网站的小说信息
 */

class WebsiteModel : WebsiteContract.Model {
    override fun getOtherWebsiteList( author: String,book_name: String): Observable<BaseHttpResponse<List<WebsiteBean>>> {
        return RetrofitRxManager.getRequestService().getWebsiteList(author,book_name)
    }
}