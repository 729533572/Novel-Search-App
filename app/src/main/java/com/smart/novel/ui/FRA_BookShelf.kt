package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import com.smart.framework.library.base.BaseFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.databinding.FraBookshelfBinding
import com.smart.novel.net.RxSchedulers
import com.smart.novel.net.WeatherEntity
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.observers.DisposableObserver

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_BookShelf : BaseFragment() {
    companion object {
        fun getInstance(): FRA_BookShelf {
            val fragment = FRA_BookShelf()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_bookshelf
    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
    }

    override fun onUserInvisible() {
    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun initViewsAndEvents() {
        RetrofitRxManager.getRequestService(mContext).getWeather("北京")
                .compose(RxSchedulers.io_main())
                .subscribeWith(object : DisposableObserver<WeatherEntity>() {
                    override fun onNext(bean: WeatherEntity) {
                        var viewBinder =viewDataBinding as FraBookshelfBinding
                        viewBinder.weather = bean
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }
                })
    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }
}