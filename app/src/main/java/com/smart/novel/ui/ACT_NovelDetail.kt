package com.smart.novel.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.qq.e.ads.banner.ADSize
import com.qq.e.ads.banner.AbstractBannerADListener
import com.qq.e.ads.banner.BannerView
import com.qq.e.comm.util.AdError
import com.smart.framework.library.adapter.rv.normal.databinding.MultiItemTypeAdapter
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.log.Elog
import com.smart.framework.library.common.utils.AppDateUtil
import com.smart.framework.library.common.utils.CommonUtils
import com.smart.novel.MyApplication
import com.smart.novel.R
import com.smart.novel.adapter.ADA_ChapterList
import com.smart.novel.base.BaseMVPActivity
import com.smart.novel.bean.ChapterBean
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ActNovelDetailBinding
import com.smart.novel.db.manager.DbManager
import com.smart.novel.dialog.DIA_Share
import com.smart.novel.mvp.contract.NovelDetailContract
import com.smart.novel.mvp.model.NovelDetailModel
import com.smart.novel.mvp.presenter.NovelDetailPresenter
import com.smart.novel.util.Constants
import com.smart.novel.util.IntentUtil
import com.smart.novel.util.PageDataConstants
import com.smart.novel.util.share.ShareEntity
import com.smart.novel.util.share.UMShareUtils.WEIXIN_PAKAGENAME
import com.smart.novel.util.share.UMShareUtils.isPlatformInstalled
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.act_novel_detail.*
import kotlinx.android.synthetic.main.layout_novel_detail_footer.*
import java.util.*

/**
 * Created by JoJo on 2018/9/2.
wechat：18510829974
description：小说详情页
 */
class ACT_NovelDetail : BaseMVPActivity<NovelDetailPresenter, NovelDetailModel>(), NovelDetailContract.View {
    companion object {
        val FROM = "ACT_NovelDetail"
    }

    var novelBean: NovelBean? = null
    var mAdapter: ADA_ChapterList? = null
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    @BindView(R.id.tv_title) lateinit var tvTile: TextView
    var mShareDialog: DIA_Share? = null
    var total_size = 100
    var novelDetailBean: NovelBean? = null
    var dataRealShow = ArrayList<ChapterBean>()

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_novel_detail
    }

    override fun getBundleExtras(extras: Bundle?) {
        novelBean = extras!!.getSerializable(PageDataConstants.NOVEL_BEAN) as NovelBean
    }

    override fun startEvents() {
        ivLeft.visibility = View.VISIBLE
        tvTile.setText(novelBean!!.name_cn)

        //展示小说详情信息 要求从接口获取
//        (viewDataBinding as ActNovelDetailBinding).novelBean = novelBean

        //分享弹窗
        mShareDialog = DIA_Share(this)

        mAdapter = ADA_ChapterList(this, true)

        val layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                // 直接禁止垂直滑动
                return false
            }
        }
        recyclerviewChapters.layoutManager = layoutManager
        recyclerviewChapters.adapter = mAdapter

        //章节列表数据
        mMvpPresenter.getChapterList(multipleStatusView, novelBean!!.book_id, "", "")
        //小说详情-(刷新小说是否收藏的状态)
        requestNovelDetail()

        initListener()

        //加载banner广告
        getBanner().loadAD()
    }

    var bv: BannerView? = null
    var posId = ""
    private fun getBanner(): BannerView {
        val posId = Constants.BannerPosID
        if (this.bv != null && this.posId == posId) {
            return this.bv!!
        }
        if (this.bv != null) {
            bannerContainer.removeView(bv)
            bv!!.destroy()
        }
        this.posId = posId
        this.bv = BannerView(this, ADSize.BANNER, Constants.APPID, posId)
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        bv!!.setRefresh(30)
        bv!!.setADListener(object : AbstractBannerADListener() {

            override fun onNoAD(error: AdError) {
                Log.i(
                        "AD_DEMO",
                        String.format("Banner onNoAD，eCode = %d, eMsg = %s", error.errorCode,
                                error.errorMsg))
            }

            override fun onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive")
            }
        })
        bannerContainer.addView(bv)
        return this.bv!!
    }


    private fun initListener() {
        mAdapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                val chapterBean = mAdapter!!.dataList.get(position)
//                mMvpPresenter.addReadRecord(chapterBean.book_id.toString(), chapterBean.chapter_name, chapterBean.chapter_number.toString())

                //跳转到阅读页面
                chapterBean.totol_size = novelDetailBean!!.total_size
                IntentUtil.intentToReadNovel(this@ACT_NovelDetail, chapterBean)
            }

            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean {
                return false
            }

        })
        mShareDialog!!.setOnShareBoardClickListener(object : DIA_Share.OnShareBoardClickListener {
            override fun onShareBoardClick(position: Int) {
                when (position) {
                //微信
                    0 -> {
                        var shareBean = ShareEntity()
                        shareBean.shareUrl = "https://www.baidu.com/"
                        isPlatformInstalled(this@ACT_NovelDetail, SHARE_MEDIA.WEIXIN, WEIXIN_PAKAGENAME, shareBean)
                    }
                //QQ
                    1 -> {
                        var shareBean = ShareEntity()
                        shareBean.shareUrl = "https://www.baidu.com/"
                        isPlatformInstalled(this@ACT_NovelDetail, SHARE_MEDIA.QQ, WEIXIN_PAKAGENAME, shareBean)
                    }
                //微博
                    2 -> {
                        var shareBean = ShareEntity()
                        shareBean.shareUrl = "https://www.baidu.com/"
                        isPlatformInstalled(this@ACT_NovelDetail, SHARE_MEDIA.SINA, WEIXIN_PAKAGENAME, shareBean)
                    }
                }
            }

        })

    }

    @OnClick(R.id.btn_collect, R.id.btn_share, R.id.btn_all_chapters, R.id.btn_read, R.id.iv_arrow_down)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_collect -> {
                if (!MyApplication.isLogin) {
                    readyGo(ACT_Login::class.java)
                    return
                }
                if (btn_collect.isSelected) {
                    mMvpPresenter.deleteCollect(novelBean!!.book_id)
                } else {
                    mMvpPresenter.doCollect(novelBean!!.book_id)
                }
            }
            R.id.btn_share -> {
                CommonUtils.makeShortToast("功能正在开发中，请耐心等待~")
//                mShareDialog!!.dialog.show()
            }
        //跳转到所有章节页面
            R.id.btn_all_chapters -> {
                IntentUtil.intentToAllChapters(this, FROM, novelBean!!.book_id, total_size)
            }
        //优化阅读:有阅读过，取最近阅读的章节，没有阅读过，取第一章节
            R.id.btn_read -> {
                val localChapterList = DbManager.getInstance().queryAll(ChapterBean::class.java) as List<ChapterBean>

                Collections.reverse(localChapterList)
                var localReadBean: ChapterBean? = null
                var firstChapter: ChapterBean? = null
                //从本地匹配当前书籍是否有最近阅读的章节。
                localChapterList?.let {
                    for (i in 0..localChapterList.size - 1) {
                        novelBean?.let {
                            if (novelBean!!.book_id.equals(localChapterList.get(i).book_id)) {
                                localReadBean = localChapterList.get(i)
                                return@let
                            }
                        }
                    }
                }
                if (dataRealShow != null && dataRealShow.size > 0) {
                    //如果集合中有最新章节，则取第一条为第一章，反之，0条为第一章
                    if (dataRealShow.get(0).latest) {
                        if (dataRealShow.size > 1) firstChapter = dataRealShow.get(1)
                    } else {
                        firstChapter = dataRealShow.get(0)
                    }
                }

                if (localReadBean == null && firstChapter == null) {
                    CommonUtils.makeShortToast("没有可读的章节~")
                    return
                }

                if (localReadBean != null) IntentUtil.intentToReadNovel(this@ACT_NovelDetail, localReadBean!!) else IntentUtil.intentToReadNovel(this@ACT_NovelDetail, firstChapter!!)
            }
            R.id.iv_arrow_down -> {
                iv_arrow_down.visibility = View.GONE
                tv_comment.setText(novelDetailBean!!.comment)
                tv_comment.setMaxLines(Integer.MAX_VALUE)
                tv_comment.requestLayout()
            }
        }
    }

    /**
     * 在分享所在的Activity里复写onActivityResult方法,如果不实现onActivityResult方法，会导致分享或回调无法正常进行
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(MyApplication.context).onActivityResult(requestCode, resultCode, data)
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showBusinessError(error: ErrorBean?) {
    }

    override fun showException(error: ErrorBean?) {
    }

    override fun getLastChapter(dataList: List<ChapterBean>) {
    }

    override fun getNextChapter(dataList: List<ChapterBean>) {
    }

    override fun getChapterList(dataList: List<ChapterBean>) {
        if (dataList == null || dataList.size == 0) return
        //第一页的最后一个为最新章节
        val newestChapterBean = dataList.get(dataList.size - 1)
        dataRealShow.clear()

        if (newestChapterBean.latest) dataRealShow.add(newestChapterBean)
        dataRealShow.addAll(dataList)
        if (dataList.size >= 5) mAdapter!!.update(dataRealShow.subList(0, 5), true) else mAdapter!!.update(dataRealShow, true)
    }

    override fun doCollect(result: Any) {
        requestNovelDetail()
        CommonUtils.makeShortToast("添加收藏成功")
    }

    override fun deleteCollect(result: Any) {
        CommonUtils.makeShortToast("删除收藏成功")
        requestNovelDetail()
    }


    override fun addReadRecord(result: Any) {
        CommonUtils.makeShortToast("添加阅读记录成功")
    }

    /**
     * 请求小说详情接口
     */
    private fun requestNovelDetail() {
        mMvpPresenter.getNovelDetail(novelBean!!.book_id)
    }

    /**
     * 请求小说详情回调
     */
    override fun getNovelDetail(dataList: List<NovelBean>) {
        val bean = dataList!!.get(0)
        novelDetailBean = bean
        if (bean == null) return

        //展示小说详情信息
        (viewDataBinding as ActNovelDetailBinding).novelBean = bean

        //设置是否收藏的状态
        btn_collect.isSelected = !TextUtils.isEmpty(bean.like)//like-是否已收藏
        //章节总数
        total_size = bean.total_size

        if (!TextUtils.isEmpty(novelDetailBean!!.content_update_time)) {
            var updateTime = Math.abs(System.currentTimeMillis() - novelDetailBean!!.content_update_time.toLong())
            val hms = AppDateUtil.getHMS(updateTime)
            var splitTime = hms.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var hour = splitTime[0].toInt()
            var minute = splitTime[1]
            if (hour < 24) {
                tv_date.setText(hour.toString() + "小时" + minute + "分 前更新")
            } else {
                tv_date.setText(AppDateUtil.getTimeStamp(novelDetailBean!!.content_update_time.toLong(), AppDateUtil.YYYY_MM_DD_HH_MM1) + " 更新")
            }
        }

        if (!TextUtils.isEmpty(bean.comment)) tv_comment.setText(bean.comment) else tv_comment.setText("暂无简介")
//        //超过固定行数，展示....
        tv_comment.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                tv_comment.replaceTips()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tv_comment.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                } else {
                    tv_comment.getViewTreeObserver()
                            .removeGlobalOnLayoutListener(this);
                }
            }
        })
        tv_comment.getViewTreeObserver().addOnPreDrawListener((object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                //这个回调会调用多次，获取完行数记得注销监听
                tv_comment.getViewTreeObserver().removeOnPreDrawListener(this);

                Elog.e("TAG", "TextView 行数：" + tv_comment.getLineCount())
                if (tv_comment.getLineCount() != 0) iv_arrow_down.visibility = View.GONE
                return false
            }

        }))

    }
}