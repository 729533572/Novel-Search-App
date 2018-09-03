package com.smart.novel.adapter

import android.content.Context
import com.smart.framework.library.adapter.rv.CommonAdapter
import com.smart.framework.library.adapter.rv.ViewHolder
import com.smart.novel.R
import com.smart.novel.bean.NovelBean
import com.smart.novel.databinding.ItemRankingListBinding

/**
 * Created by JoJo on 2018/8/31.
 * wechat:18510829974
 * description: 排行榜列表Adapter
 */
class ADA_RankingList constructor(context: Context) : CommonAdapter<NovelBean, ItemRankingListBinding>(context) {
    override fun convert(viewBinding: ItemRankingListBinding?, holder: ViewHolder.BindingHolder, bean: NovelBean?, position: Int) {
        viewBinding!!.rankBean = bean
        when (position) {
            1 -> {
                holder.setImageResource(R.id.iv_rank, R.drawable.ic_ranking_list_first)
                holder.setVisible(R.id.iv_rank, true)
                holder.setVisible(R.id.tv_rank_num, false)
            }
            2 -> {
                holder.setImageResource(R.id.iv_rank, R.drawable.ic_ranking_list_second)
                holder.setVisible(R.id.iv_rank, true)
                holder.setVisible(R.id.tv_rank_num, false)
            }
            3 -> {
                holder.setImageResource(R.id.iv_rank, R.drawable.ic_ranking_list_third)
                holder.setVisible(R.id.iv_rank, true)
                holder.setVisible(R.id.tv_rank_num, false)
            }
            else -> {
                holder.setVisible(R.id.iv_rank, false)
                holder!!.setVisible(R.id.tv_rank_num, true)
                holder.setText(R.id.tv_rank_num, position.toString())
            }
        }
    }

    override fun itemLayoutId(): Int {
        return R.layout.item_ranking_list
    }

}