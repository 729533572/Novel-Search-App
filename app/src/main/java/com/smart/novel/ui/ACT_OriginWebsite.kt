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
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.WebsiteBean
import com.smart.novel.mvp.contract.WebsiteContract
import com.smart.novel.mvp.model.WebsiteModel
import com.smart.novel.mvp.presenter.WebsitePresenter
import com.smart.novel.util.PageDataConstants
import com.smart.novel.util.RecyclerViewHelper
import kotlinx.android.synthetic.main.act_origin_website.*
import kotlinx.android.synthetic.main.layout_common_progress.*

/**
 * Created by JoJo on 2018/9/5.
 * wechat:18510829974
 * description: 原网页页面
 */
class ACT_OriginWebsite : BaseMVPActivity<WebsitePresenter, WebsiteModel>(), WebsiteContract.View {
    var mAdapter: ADA_OriginWebsite? = null
    var chapterBean: ChapterBean? = null
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    override fun getBundleExtras(extras: Bundle?) {
        chapterBean = extras!!.getSerializable(PageDataConstants.CHAPTER_BEAN) as ChapterBean?
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_origin_website
    }

    override fun startEvents() {
        ivLeft.visibility = View.VISIBLE
        setHeaderTitle(chapterBean!!.chapter_name)
        sb_progress.progress = ((chapterBean!!.chapter_number * 1.0f / chapterBean!!.totol_size) * 100).toInt()

        mAdapter = ADA_OriginWebsite(this)
        RecyclerViewHelper.initNormalRecyclerView(this, recyclerviewWebsite, mAdapter!!, LinearLayoutManager(this))

        mMvpPresenter.getOtherWebsiteList(null, "啤酒鱼", "张苏静的幸福日常")

        initListener()
    }

    private fun initListener() {
        mAdapter!!.setOnItemClickListener(object : MultiTypeAdapterNormal.OnItemClickListener, MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                val bean = mAdapter!!.dataList.get(position)
                mAdapter!!.setSelectItem(position)
                mAdapter!!.notifyDataSetChanged()

                mMvpPresenter.switchWebsite(bean.id.toString(), chapterBean!!.chapter_number.toString())
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

    override fun switchWebsite(dataList: List<ChapterBean>) {
        if (dataList != null && dataList.size > 0){
            var mSwitchChapterBean = dataList.get(0)
        }
    }

}