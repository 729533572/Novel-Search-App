package com.smart.novel.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.smart.framework.library.base.BaseFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.databinding.FraBookshelfBinding
import com.smart.novel.net.RxSchedulers
import com.smart.novel.net.WeatherEntity
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.fra_bookshelf.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_BookShelf : BaseFragment() {
    /**
     * companion object {}内：静态方法
     */
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
        //状态栏透明和间距处理(不作如下处理，则为android:theme="@style/style_darkactionbar_activity"该主题的黑色背景的状态栏)
//        StatusBarUtil.darkMode(activity)//状态栏字体颜色及icon变黑
//        StatusBarUtil.setPaddingSmart(activity, titlebar)


//        StatusBarUtil.darkMode(this)//状态栏文字颜色：深色
//        var fakeStatusBar = View(this)
//        var lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StatusBarUtil.getStatusBarHeight(this))
//        fakeStatusBar.setBackgroundColor(Color.BLUE)
//        fakeStatusBar.layoutParams = lp
//        StatusBarUtil.setPaddingSmart(this, fakeStatusBar)

        multipleStatusView.showLoading()
        Handler().postDelayed({
            RetrofitRxManager.getRequestService(mContext).getWeather("北京")
                    .compose(RxSchedulers.io_main())
                    .subscribeWith(object : DisposableObserver<WeatherEntity>() {
                        override fun onNext(bean: WeatherEntity) {
                            var viewBinder = viewDataBinding as FraBookshelfBinding
                            viewBinder.weather = bean
                            multipleStatusView.showContent()
                        }

                        override fun onError(e: Throwable) {
                        }

                        override fun onComplete() {
                        }
                    })
        }, 1500)
        tv_total.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                readyGo(ACT_Login::class.java)
            }
        })
    }


    override fun isBindEventBusHere(): Boolean {
        return false
    }
}
