package com.smart.novel.mvp.model

import com.smart.novel.bean.BaseHttpResponse
import com.smart.novel.db.bean.ReadHistoryEntity
import com.smart.novel.mvp.contract.TestContract
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:
 */

class TestModel:TestContract.Model{
    override fun getTestData(): Observable<BaseHttpResponse<List<ReadHistoryEntity>>> {
//        return RetrofitRxManager.getRequestService().getWeather("北京")
        return RetrofitRxManager.getRequestService().searchNovel()
    }
//    override fun getTestData() {
//        RetrofitRxManager.getRequestService().getWeather("北京")
//                .compose(RxSchedulers.io_main())
//                .subscribeWith(object : DisposableObserver<WeatherEntity>() {
//                    override fun onNext(bean: WeatherEntity) {
//
//                    }
//
//                    override fun onError(e: Throwable) {
//                    }
//
//                    override fun onComplete() {
//                    }
//                })
//    }

}
