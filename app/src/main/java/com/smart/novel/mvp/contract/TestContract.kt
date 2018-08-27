package com.smart.novel.mvp.contract

import com.smart.framework.library.base.mvp.BaseModel
import com.smart.framework.library.base.mvp.BasePresenter
import com.smart.framework.library.base.mvp.IBaseView
import com.smart.framework.library.loading.MultipleStatusView
import com.smart.novel.net.WeatherEntity
import io.reactivex.Observable

/**
 * Created by JoJo on 2018/8/27.
 * wechat:18510829974
 * description:
 */
class TestContract {
    interface View : IBaseView {
        fun getTestData(weather: WeatherEntity)
    }

    interface Model : BaseModel {
        fun getTestData(): Observable<WeatherEntity>
    }

    abstract class Presenter : BasePresenter<TestContract.View, TestContract.Model>() {
        abstract fun getTestData(multipleStatusView: MultipleStatusView)
    }
}