package com.smart.novel.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.smart.novel.R
import com.smart.novel.base.ACT_Base

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:
 */
class ACT_Announcement : ACT_Base() {
    @BindView(R.id.tv_title) lateinit var tvTile: TextView
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_announcement
    }

    override fun initViewsAndEvents() {
        tvTile.setText("声明条款")

        ivLeft.visibility = View.VISIBLE
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
}