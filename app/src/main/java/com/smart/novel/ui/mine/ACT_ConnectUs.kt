package com.smart.novel.ui.mine

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.smart.novel.R
import com.smart.novel.base.ACT_Base
import kotlinx.android.synthetic.main.act_announcement.*

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description:
 */
class ACT_ConnectUs : ACT_Base() {
    @BindView(R.id.tv_title) lateinit var tvTile: TextView
    @BindView(R.id.iv_left) lateinit var ivLeft: ImageView
    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_connect_us
    }

    override fun initViewsAndEvents() {
        tvTile.setText("联系客服")

        ivLeft.visibility = View.VISIBLE
        tv_txt.setText(Html.fromHtml(content))
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }
    val content = "<html>\n" +
            "<body>\n" +
            "\n" +
            "<p>\n" +
            "邮箱：yulekeji@qingqingshuwu.club<br>\n" +
            "电话：18516987934<br>\n" +
            "&nbsp;QQ：872995601<br>\n" +
            "</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>"
}