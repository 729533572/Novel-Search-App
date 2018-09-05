package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.bean.NovelBean
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description: 书架模块
 */
class BookShelfContract {
    interface View : IBaseView {
        fun getBookShelfData(dataList: List<NovelBean>)
        fun deleteReadRecord(result: Any)
        fun deleteCollect(result: Any)
    }

    interface Model : BaseModel {
        //返回的只有BaseHttpResponse的data部分
        fun getBookShelfData(type: String, page: String): Observable<BaseHttpResponse<List<NovelBean>>>

        fun deleteReadRecord(id: String): Observable<BaseHttpResponse<Any>>

        fun deleteCollect(id: String): Observable<BaseHttpResponse<Any>>
    }

    abstract class Presenter : BasePresenter<BookShelfContract.View, BookShelfContract.Model>() {
        abstract fun getBookShelfData(type: String, page: String, multipleStatusView: MultipleStatusView?)
        abstract fun deleteReadRecord(id: String)
        abstract fun deleteCollect(id: String)

    }
}