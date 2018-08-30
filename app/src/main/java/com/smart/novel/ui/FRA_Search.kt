package com.smart.novel.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_HotSearchTag
import com.smart.novel.adapter.ADA_SearchHistory
import com.smart.novel.adapter.ADA_SearchList
import com.smart.novel.db.bean.HotSearchBean
import com.smart.novel.db.bean.SearchHistoryBean
import com.smart.novel.db.bean.SearchResultBean
import com.smart.novel.mvp.contract.SearchContract
import com.smart.novel.mvp.model.SearchModel
import com.smart.novel.mvp.presenter.SearchPresenter
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.fra_search.*
import kotlinx.android.synthetic.main.layout_search_no_result.*
import kotlinx.android.synthetic.main.layout_search_no_result_header.*
import kotlinx.android.synthetic.main.layout_search_title.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 搜索小说
 */
class FRA_Search : BaseMVPFragment<SearchPresenter, SearchModel>(), SearchContract.View {

    var mAdapter: ADA_SearchList? = null
    var mSearchHistoryAdapyer: ADA_SearchHistory? = null
    var mHotSearchAdapter: ADA_HotSearchTag? = null
    var data = ArrayList<SearchResultBean>()

    companion object {
        fun getInstance(): FRA_Search {
            val fragment = FRA_Search()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_search
    }

    @OnClick(R.id.tv_do_search, R.id.iv_clear_txt, R.id.btn_clear_history, R.id.btn_change)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_do_search -> mMvpPresenter.getSearchResultList(multipleStatusView, et_search_keywords.text.toString())
            R.id.iv_clear_txt -> {
                et_search_keywords.setText("")
                showResultView(false, false)
            }
        //清空历史记录
            R.id.btn_clear_history -> CommonUtils.makeEventToast(MyApplication.context, "清空历史记录", false)
        //换一批
            R.id.btn_change -> mMvpPresenter.getHotSearchList()
        }
    }

    override fun startEvents() {
        //搜索结果列表
        mAdapter = ADA_SearchList(activity)
        recyclerview.setPullRefreshEnabled(false)
        RecyclerViewHelper.initRecyclerView(activity, recyclerview, mAdapter!!, LinearLayoutManager(activity))

        //搜索历史
        mSearchHistoryAdapyer = ADA_SearchHistory(activity)
        listviewSearchHistory.adapter = mSearchHistoryAdapyer

        //获取热门搜索列表
        mMvpPresenter.getHotSearchList()

        var searchList = ArrayList<SearchHistoryBean>()
        for (i in 0..2) {
            var bean = SearchHistoryBean()
            bean.searchKeyWords = "搜索历史记录..."
            searchList.add(bean)
        }
        mSearchHistoryAdapyer!!.update(searchList, true)

    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun showException(error: ErrorBean?) {
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

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    /**
     * 热门搜索
     */
    override fun getHotSearchList(hotList: List<HotSearchBean>) {
        mHotSearchAdapter = ADA_HotSearchTag(hotList, mContext)
        flowTagview.setAdapter(mHotSearchAdapter)
    }

    /**
     * 搜索结果
     */
    override fun getSearchResultList(dataList: List<SearchResultBean>) {
        if (dataList!!.size > 0) {
            showResultView(true, false)
            mAdapter!!.update(dataList, true)
        } else {
            showResultView(false, true)
        }

    }

    /**
     * 展示无结果或者展示搜索结果列表
     */
    private fun showResultView(isShowResult: Boolean, isShowNoResultHeader: Boolean) {
        if (isShowResult) {
            multipleStatusView.visibility = View.VISIBLE
            layout_no_search_result.visibility = View.GONE
        } else {
            layout_no_search_result.visibility = View.VISIBLE
            multipleStatusView.visibility = View.GONE
        }
        if (isShowNoResultHeader) ll_no_result_header.visibility = View.VISIBLE else ll_no_result_header.visibility = View.GONE
    }
}