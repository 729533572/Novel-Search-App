package com.smart.novel.ui

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.OnClick
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.interfaces.OnRefreshListener
import com.smart.framework.library.adapter.rv.MultiItemTypeAdapter
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ReadHistory
import com.smart.novel.db.bean.ReadHistoryEntity
import com.smart.novel.mvp.contract.TestContract
import com.smart.novel.mvp.model.TestModel
import com.smart.novel.mvp.presenter.TestPresenter
import com.smart.novel.net.WeatherEntity
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.fra_bookshelf.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_BookShelf : BaseMVPFragment<TestPresenter, TestModel>(), TestContract.View, OnLoadMoreListener, OnRefreshListener {
    var mAdapter: ADA_ReadHistory? = null
    var data = ArrayList<ReadHistoryEntity>()
    val mColumnNum = 3//列表每行展示的个数

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

    override fun startEvents() {
        for (i in 0..16) {
            var history = ReadHistoryEntity()
            data.add(history)
        }

        initRecyclerView()

        initListener()

        mMvpPresenter.getTestData(multipleStatusView)

    }

    /**
     * 处理列表
     */
    private fun initRecyclerView() {
        mAdapter = ADA_ReadHistory(activity)
        RecyclerViewHelper.initRecyclerView(recyclerview, mAdapter!!, activity, GridLayoutManager(activity, 3))
        recyclerview.setOnRefreshListener(this)
        recyclerview.setOnLoadMoreListener(this)
        recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView?) {
                //设计图item之间的间距为40 (header占了一个位置，故从位置1开始显示实际的item)
                if (itemPosition != 0 && itemPosition <= mColumnNum) {
                    outRect.top = 40
                }
                if (itemPosition > mColumnNum) {
                    outRect.top = 50
                }
            }
        })
    }

    private fun initListener() {
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                CommonUtils.makeEventToast(MyApplication.context, "position=" + position, false)
            }
        })
    }

    override fun onRefresh() {
        Handler().postDelayed({ recyclerview.refreshComplete(1) }, 2000)
    }

    override fun onLoadMore() {
        Handler().postDelayed({ recyclerview.setNoMore(true) }, 2000)
    }

    @OnClick(R.id.ll_read_history, R.id.ll_my_collected)
    fun onClick(view: View) {
        when (view.id) {
            R.id.ll_read_history -> {
                CommonUtils.makeEventToast(MyApplication.context, "阅读历史", false)
                ll_read_history.getChildAt(0).visibility = View.VISIBLE
                ll_read_history.getChildAt(1).visibility = View.GONE
                ll_my_collected.getChildAt(0).visibility = View.GONE
                ll_my_collected.getChildAt(1).visibility = View.VISIBLE
//                readyGo(ACT_Login::class.java)
            }
            R.id.ll_my_collected -> {
                CommonUtils.makeEventToast(MyApplication.context, "我的收藏", false)
                ll_my_collected.getChildAt(0).visibility = View.VISIBLE
                ll_my_collected.getChildAt(1).visibility = View.GONE
                ll_read_history.getChildAt(0).visibility = View.GONE
                ll_read_history.getChildAt(1).visibility = View.VISIBLE
            }
        }
    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
    }

    override fun onUserInvisible() {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showBusinessError(error: ErrorBean?) {
        multipleStatusView.showError()
    }

    override fun getTestData(weatherEntity: WeatherEntity) {
        tv_total.text = "共" + "9" + "本"
        mAdapter!!.update(data, true)
    }
}
