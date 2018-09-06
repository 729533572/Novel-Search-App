package com.smart.novel.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import com.smart.framework.library.adapter.rv.normal.MultiTypeAdapterNormal
import com.smart.framework.library.adapter.rv.normal.databinding.MultiItemTypeAdapter
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.adapter.ADA_OriginWebsite
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.WebsiteBean
import com.smart.novel.mvp.contract.WebsiteContract
import com.smart.novel.mvp.model.WebsiteModel
import com.smart.novel.mvp.presenter.WebsitePresenter
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.act_origin_website.*

/**
 * Created by JoJo on 2018/9/5.
 * wechat:18510829974
 * description: 原网页页面
 */
class ACT_OriginWebsite : BaseMVPActivity<WebsitePresenter, WebsiteModel>(), WebsiteContract.View {
    var mAdapter: ADA_OriginWebsite? = null
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_origin_website
    }

    override fun startEvents() {
        ivLeft.visibility = View.VISIBLE
        setHeaderTitle("张苏静的幸福日常")

        mAdapter = ADA_OriginWebsite(this)
        RecyclerViewHelper.initNormalRecyclerView(this, recyclerviewWebsite, mAdapter!!, LinearLayoutManager(this))

        mMvpPresenter.getOtherWebsiteList(null, "啤酒鱼", "张苏静的幸福日常")

        initListener()
    }

    private fun initListener() {
        mAdapter!!.setOnItemClickListener(object : MultiTypeAdapterNormal.OnItemClickListener, MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                mAdapter!!.setSelectItem(position)
                mAdapter!!.notifyDataSetChanged()
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }
        })
    }

    override fun showBusinessError(error: ErrorBean?) {

    }

    override fun showException(error: ErrorBean?) {
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun getOtherWebsiteList(dataList: List<WebsiteBean>) {
        if (dataList != null && dataList.size > 0) mAdapter!!.update(dataList, true)

    }

}