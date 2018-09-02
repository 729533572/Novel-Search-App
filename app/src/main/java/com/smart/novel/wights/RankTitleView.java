package com.smart.novel.wights;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.smart.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.smart.novel.R;
import com.smart.novel.adapter.ADA_RankingTitle;
import com.smart.novel.bean.RankTitleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/9/1.
 * wechat：18510829974
 * description：排行榜title view
 */
public class RankTitleView extends LinearLayout {
    private Context mContext;
    private ADA_RankingTitle mAdapter;
    private List<RankTitleBean> datas = new ArrayList<>();
    private String[] titles = {"搜索榜", "阅读榜", "新书榜", "女生榜", "男生榜"};

    public RankTitleView(Context context) {
        this(context, null);
    }

    public RankTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RankTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        for (int i = 0; i < titles.length; i++) {
            RankTitleBean rankTitleBean = new RankTitleBean();
            rankTitleBean.name = titles[i];
            rankTitleBean.isCheck = i == 0 ? true : false;
            datas.add(rankTitleBean);
        }
        mAdapter = new ADA_RankingTitle(mContext);
        //root参数必须为this,否则该自定义的布局无法显示出来
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_ranking_title_list, this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewRankTitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);
        mAdapter.update(datas, true);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //设置选中效果
//                for (i in 0..mTitleAdapter!!.dataList.size - 1) {
//                    if (i == position) mTitleAdapter!!.dataList.get(i).isCheck = true else mTitleAdapter!!.dataList.get(i).isCheck = false
//                }
                if (mListener != null) {
                    mListener.onTitleCheck(position);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public interface onTitleCheckListener {
        void onTitleCheck(int position);
    }

    onTitleCheckListener mListener;

    public void setonTitleCheckListener(onTitleCheckListener listener) {
        this.mListener = listener;
    }

    public ADA_RankingTitle getAdapter() {
        return mAdapter;
    }
}
