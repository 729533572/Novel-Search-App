package com.smart.novel.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import butterknife.OnClick
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.framework.library.widgets.tag.FlowLayout
import com.smart.framework.library.widgets.tag.TagFlowLayout
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_HotSearchTag
import com.smart.novel.adapter.ADA_SearchHistory
import com.smart.novel.adapter.ADA_SearchList
import com.smart.novel.bean.HotSearchBean
import com.smart.novel.bean.SearchResultBean
import com.smart.novel.db.bean.SearchHistoryBean
import com.smart.novel.db.manager.DbManager
import com.smart.novel.mvp.contract.SearchContract
import com.smart.novel.mvp.model.SearchModel
import com.smart.novel.mvp.presenter.SearchPresenter
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.layout_common_recyclview.*
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
    var mSearchHistoryList: ArrayList<SearchHistoryBean>? = null

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

    override fun startEvents() {
        //获取热门搜索列表
        mMvpPresenter.getHotSearchList()

        //搜索结果列表
        mAdapter = ADA_SearchList(activity)
        recyclerview.setPullRefreshEnabled(false)
        RecyclerViewHelper.initRecyclerView(activity, recyclerview, mAdapter!!, LinearLayoutManager(activity))

        //搜索历史
        mSearchHistoryAdapyer = ADA_SearchHistory(activity)
        listviewSearchHistory.adapter = mSearchHistoryAdapyer

        //搜索历史记录
        mSearchHistoryAdapyer = ADA_SearchHistory(activity)
        listviewSearchHistory.adapter = mSearchHistoryAdapyer

        //刷新搜索历史记录
        refreshSearchHistory()

        initListener()
    }

    private fun initListener() {
        mSearchHistoryAdapyer!!.setOnItemDeleteListener(object : ADA_SearchHistory.OnItemDeleteListener {
            override fun onItemDelete(bean: SearchHistoryBean) {
//                CommonUtils.makeEventToast(MyApplication.context, bean.searchKeyWords + "id=" + bean.id, false)
                //本地存储移出
                DbManager.getInstance().delete(SearchHistoryBean::class.java, bean)
                val arrayList = DbManager.getInstance().queryAll(SearchHistoryBean::class.java) as ArrayList<SearchHistoryBean>
                mSearchHistoryAdapyer!!.update(arrayList, true)
            }
        })
        flowTagview.setOnTagClickListener(object : TagFlowLayout.OnTagClickListener {
            override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
                et_search_keywords.setText(mHotSearchAdapter!!.getItem(position).search_keyword)
                et_search_keywords.setSelection(et_search_keywords.text.toString().length)
                mMvpPresenter.getSearchResultList(multipleStatusView, et_search_keywords.text.toString())
                return true
            }
        })
    }

    @OnClick(R.id.tv_do_search, R.id.iv_clear_txt, R.id.btn_clear_history, R.id.btn_change)
    fun onClick(view: View) {
        when (view.id) {
        //搜索
            R.id.tv_do_search -> {
                mMvpPresenter.getSearchResultList(multipleStatusView, et_search_keywords.text.toString())
            }
        //清空搜索框
            R.id.iv_clear_txt -> {
                et_search_keywords.setText("")
                showResultView(false, false)
                refreshSearchHistory()
            }
        //清空历史记录
            R.id.btn_clear_history -> {
                DbManager.getInstance().deleteAll(SearchHistoryBean::class.java)
                refreshSearchHistory()
                CommonUtils.makeEventToast(MyApplication.context, MyApplication.context.getString(R.string.string_clear_history_success), false)
            }
        //换一批
            R.id.btn_change -> mMvpPresenter.getHotSearchList()
        }
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
        mHotSearchAdapter = ADA_HotSearchTag(hotList)
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
        //搜索成功了，才保存搜索记录
        saveSearchHistory()
    }

    /**
     * 展示无结果或者展示搜索结果列表
     */
    private fun showResultView(isShowResult: Boolean, isShowNoResultHeader: Boolean) {
        if (isShowResult) {
            layout_no_search_result.visibility = View.GONE
            multipleStatusView.visibility = View.VISIBLE
        } else {
            layout_no_search_result.visibility = View.VISIBLE
            multipleStatusView.visibility = View.GONE
        }
        //无搜索结果的header
        if (isShowNoResultHeader) ll_no_result_header.visibility = View.VISIBLE else ll_no_result_header.visibility = View.GONE
    }

    /**
     * 保存历史记录
     */
    private fun saveSearchHistory() {
        var history = SearchHistoryBean()
        if (!TextUtils.isEmpty(et_search_keywords.text.toString())) {
            history.searchKeyWords = et_search_keywords.text.toString()
            DbManager.getInstance().insertOrReplace(SearchHistoryBean::class.java, history)
        }
    }

    /**
     * 刷新搜索历史记录
     */
    private fun refreshSearchHistory() {
        mSearchHistoryList = (DbManager.getInstance().queryAll(SearchHistoryBean::class.java) as ArrayList<SearchHistoryBean>)
        mSearchHistoryAdapyer!!.update(mSearchHistoryList!!, true)
    }

    fun tabCheck() {

    }

}