package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.BaseHttpResponse
import com.smart.novel.db.bean.ReadHistoryBean
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description: 书架模块
 */
class BookShelfContract {
    interface View : IBaseView {
        fun getBookShelfData(dataList: List<ReadHistoryBean>)
    }

    interface Model : BaseModel {
        fun getBookShelfData(): Observable<BaseHttpResponse<List<ReadHistoryBean>>>
    }

    abstract class Presenter : BasePresenter<BookShelfContract.View, BookShelfContract.Model>() {
        abstract fun getBookShelfData(multipleStatusView: MultipleStatusView)
    }
}