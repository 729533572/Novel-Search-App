package com.smart.novel.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.github.jdsjlzx.interfaces.OnLoadMoreListener
import com.github.jdsjlzx.interfaces.OnRefreshListener
import com.smart.framework.library.adapter.rv.MultiItemTypeAdapter
import com.smart.framework.library.base.BaseMVPFragment
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_RankingList
import com.smart.novel.bean.NovelBean
import com.smart.novel.mvp.contract.RankingContract
import com.smart.novel.mvp.model.RankingModel
import com.smart.novel.mvp.presenter.RankingPresenter
import com.smart.novel.util.PageDataConstants
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.fra_ranklist.*
import kotlinx.android.synthetic.main.layout_common_recyclview.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description: 排行榜
 */
class FRA_RankingList : BaseMVPFragment<RankingPresenter, RankingModel>(), RankingContract.View, OnRefreshListener, OnLoadMoreListener {
    var mAdapter: ADA_RankingList? = null
    @BindView(R.id.tv_right) lateinit var tvRight: TextView
    @BindView(R.id.tv_title) lateinit var tvTile: TextView

    companion object {
        fun getInstance(): FRA_RankingList {
            val fragment = FRA_RankingList()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fra_ranklist
    }

    override fun startEvents() {
        tvRight!!.visibility = View.GONE
        tvTile.setText(MyApplication.context.getString(R.string.string_title_ranking))

        mAdapter = ADA_RankingList(activity)
        RecyclerViewHelper.initRecyclerView(activity, recyclerview, mAdapter!!, LinearLayoutManager(activity))
        recyclerview.setOnRefreshListener(this)
        recyclerview.setOnLoadMoreListener(this)

        mMvpPresenter.getRankList(multipleStatusView, "read")

        initListener()
    }

    private fun initListener() {
        rankTitleView.setonTitleCheckListener { position ->
            //设置选中效果
            rankTitleView.adapter!!.setSelectItem(position)
            rankTitleView.adapter!!.notifyDataSetChanged()
            //刷新数据
            when (position) {
                0 -> mMvpPresenter.getRankList(multipleStatusView, "search")
                1 -> mMvpPresenter.getRankList(multipleStatusView, "read")
                2 -> mMvpPresenter.getRankList(multipleStatusView, "new")
                3 -> mMvpPresenter.getRankList(multipleStatusView, "female")
                4 -> mMvpPresenter.getRankList(multipleStatusView, "man")
            }
        }
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                var realPos = position - 1
                var novelBean = mAdapter!!.dataList.get(realPos)
                var bundle = Bundle()
                bundle.putSerializable(PageDataConstants.NOVEL_BEAN, novelBean)
                readyGo(ACT_NovelDetail::class.java, bundle)
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })
    }

    override fun onRefresh() {
        Handler().postDelayed({ recyclerview.refreshComplete(1) }, 2000)
    }

    override fun onLoadMore() {
        Handler().postDelayed({ recyclerview.setNoMore(true) }, 2000)
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

    override fun getRankList(dataList: List<NovelBean>) {
        mAdapter!!.update(dataList!!, true)
    }

    fun tabCheck() {

    }

}