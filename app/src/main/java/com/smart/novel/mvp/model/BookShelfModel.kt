package com.smart.novel.mvp.model

import com.smart.novel.bean.BaseHttpResponse
import com.smart.novel.db.bean.ReadHistoryBean
import com.smart.novel.mvp.contract.BookShelfContract
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:书架模块
 */

class BookShelfModel : BookShelfContract.Model{
    override fun getBookShelfData(): Observable<BaseHttpResponse<List<ReadHistoryBean>>> {
        return RetrofitRxManager.getRequestService().getReadHistory()
    }
}
