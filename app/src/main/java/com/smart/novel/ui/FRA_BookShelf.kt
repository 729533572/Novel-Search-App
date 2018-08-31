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
import com.smart.novel.bean.ReadHistoryBean
import com.smart.novel.mvp.contract.BookShelfContract
import com.smart.novel.mvp.model.BookShelfModel
import com.smart.novel.mvp.presenter.BookShelfPresenter
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.fra_bookshelf.*
import kotlinx.android.synthetic.main.layout_common_recyclview.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 书架
 */
class FRA_BookShelf : BaseMVPFragment<BookShelfPresenter, BookShelfModel>(), BookShelfContract.View, OnLoadMoreListener, OnRefreshListener {

    var mAdapter: ADA_ReadHistory? = null
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
//        var actHome = activity as ACT_Home
//        actHome.lightModeStatusBar()

        initRecyclerView()

        initListener()

        mMvpPresenter.getBookShelfData(multipleStatusView)

    }

    /**
     * 处理列表
     */
    private fun initRecyclerView() {
        mAdapter = ADA_ReadHistory(activity)
        RecyclerViewHelper.initRecyclerView(activity, recyclerview, mAdapter!!, GridLayoutManager(activity, 3))
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
                //设置item距离左边的距离
                outRect.left = 60
                if (itemPosition == mAdapter!!.dataList.size + 1 || itemPosition == 0) {
                    outRect.left = 0
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
                CommonUtils.makeEventToast(MyApplication.context, MyApplication.context.getString(R.string.string_read_history), false)
                ll_read_history.getChildAt(0).visibility = View.VISIBLE
                ll_read_history.getChildAt(1).visibility = View.GONE
                ll_my_collected.getChildAt(0).visibility = View.GONE
                ll_my_collected.getChildAt(1).visibility = View.VISIBLE
            }
            R.id.ll_my_collected -> {
                CommonUtils.makeEventToast(MyApplication.context, MyApplication.context.getString(R.string.string_mine_collected), false)
                ll_my_collected.getChildAt(0).visibility = View.VISIBLE
                ll_my_collected.getChildAt(1).visibility = View.GONE
                ll_read_history.getChildAt(0).visibility = View.GONE
                ll_read_history.getChildAt(1).visibility = View.VISIBLE
                readyGo(ACT_Login::class.java)
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

    override fun showException(error: ErrorBean?) {

    }

    override fun getBookShelfData(dataList: List<ReadHistoryBean>) {
        tv_total.text = "共" + dataList.size + "本"
        mAdapter!!.update(dataList!!, true)
    }
}
