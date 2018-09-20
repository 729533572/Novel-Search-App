package com.smart.novel.util

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.recyclerview.ProgressStyle
import com.smart.framework.library.adapter.rv.normal.MultiTypeAdapterNormal
import com.smart.framework.library.adapter.rv.normal.databinding.MultiItemTypeAdapter
import com.smart.novel.MyApplication
import com.smart.novel.R

/**
 * Created by JoJo on 2018/8/29.
 * wechat:18510829974
 * description: LRecyclerView初始化帮助类
 */
class RecyclerViewHelper {
    companion object {
        //初始化多类型LRecyclerView
        fun initMutiTypeRecyclerView(mRecyclerView: LRecyclerView, mMutiAdapter: MultiTypeAdapterNormal<*>, context: Activity) {
            var mLrecyclViewAdapter = LRecyclerViewAdapter(mMutiAdapter)
            mRecyclerView.adapter = mLrecyclViewAdapter
            mRecyclerView.setHasFixedSize(true)
            mRecyclerView.layoutManager = LinearLayoutManager(context)
            mRecyclerView.setRefreshProgressStyle(ProgressStyle.TriangleSkewSpin)
            mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
            //设置头部文字颜色
            mRecyclerView.setHeaderViewColor(R.color.color_3AC270, R.color.color_73787d, R.color.color_f9f9f9)
            //设置底部加载颜色-loading动画颜色,文字颜色,footer的背景颜色
            mRecyclerView.setFooterViewColor(R.color.color_3AC270, R.color.color_73787d, R.color.color_f9f9f9)
            //设置底部加载文字提示
            mRecyclerView.setFooterViewHint(MyApplication.context.getString(R.string.list_footer_loading), MyApplication.context.getString(R.string.list_footer_end), MyApplication.context.getString(R.string.list_footer_network_error))
        }

        //初始化普通的LRecyclerView
        fun initRecyclerView(mRecyclerView: LRecyclerView, mAdapter: MultiItemTypeAdapter<*, *>, context: Activity) {
            var mLrecyclViewAdapter = LRecyclerViewAdapter(mAdapter)
            mRecyclerView.adapter = mLrecyclViewAdapter
            mRecyclerView.setHasFixedSize(true)
            mRecyclerView.layoutManager = LinearLayoutManager(context)
            mRecyclerView.setRefreshProgressStyle(ProgressStyle.TriangleSkewSpin)
            mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
            //设置头部文字颜色
            mRecyclerView.setHeaderViewColor(R.color.color_3AC270, R.color.color_73787d, R.color.color_f9f9f9)
            //设置底部加载颜色-loading动画颜色,文字颜色,footer的背景颜色
            mRecyclerView.setFooterViewColor(R.color.color_3AC270, R.color.color_73787d, R.color.color_f9f9f9)
            //设置底部加载文字提示
            mRecyclerView.setFooterViewHint(MyApplication.context.getString(R.string.list_footer_loading), MyApplication.context.getString(R.string.list_footer_end), MyApplication.context.getString(R.string.list_footer_network_error))
        }
    }
}