package com.smart.novel.ui

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.smart.framework.library.bean.ErrorBean
import com.smart.novel.R
import com.smart.novel.base.ACT_Base
import kotlinx.android.synthetic.main.act_home.*

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
class ACT_Home : ACT_Base(), View.OnClickListener {
    private var mFraBookShelf: FRA_BookShelf? = null
    private var mFraSearch: FRA_Search? = null
    private var mFraRankingList: FRA_RankingList? = null
    private var mFraMine: FRA_Mine? = null
    private var mIndex = 0
    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.act_home
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (savedInstanceState != null) {
//            mIndex = savedInstanceState.getInt("currTabIndex")
//        }
//        Elog.e("TAG","onCreate222")
//    先执行initViewAndEvents(),再执行onCreate(),因为先走super.onCreate()父类的逻辑。
//    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun initViewsAndEvents() {
        switchFragment(mIndex)
        initListener()
    }

    private fun initListener() {
        rb_bookshelf.setOnClickListener(this)
        rb_search.setOnClickListener(this)
        rb_ranklist.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rb_bookshelf -> switchFragment(0)
            R.id.rb_search -> switchFragment(1)
            R.id.rb_ranklist -> switchFragment(2)
            R.id.rb_mine -> switchFragment(3)
        }
    }

    /**
     * 根据下标切换Fragment
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideAllFragments(transaction)
        when (position) {
            0
            -> {
                if (mFraBookShelf == null) {
                    mFraBookShelf = FRA_BookShelf.getInstance()
                    transaction!!.add(R.id.fl_container, mFraBookShelf)
                } else {
                    transaction!!.show(mFraBookShelf)
                }
                rb_bookshelf.isChecked = true
                rb_search.isChecked = false
                rb_ranklist.isChecked = false
                rb_mine.isChecked = false
            }

            1
            -> {
                if (mFraSearch == null) {
                    mFraSearch = FRA_Search.getInstance()
                    transaction!!.add(R.id.fl_container, mFraSearch)
                } else {
                    transaction!!.show(mFraSearch)
                }
                rb_bookshelf.isChecked = false
                rb_search.isChecked = true
                rb_ranklist.isChecked = false
                rb_mine.isChecked = false
            }
            2
            -> {
                if (mFraRankingList == null) {
                    mFraRankingList = FRA_RankingList.getInstance()
                    transaction!!.add(R.id.fl_container, mFraRankingList)
                } else {
                    transaction!!.show(mFraRankingList)
                }
                rb_bookshelf.isChecked = false
                rb_search.isChecked = false
                rb_ranklist.isChecked = true
                rb_mine.isChecked = false
            }
            3
            -> {
                if (mFraMine == null) {
                    mFraMine = FRA_Mine.getInstance()
                    transaction!!.add(R.id.fl_container, mFraMine)
                } else {
                    transaction!!.show(mFraMine)
                }
                rb_bookshelf.isChecked = false
                rb_search.isChecked = false
                rb_ranklist.isChecked = false
                rb_mine.isChecked = true
            }
        }
        transaction.commitNowAllowingStateLoss()
        tabCheck(position)
    }

    /**
     * fragment tabcheck
     */
    private fun tabCheck(position: Int) {
        when(position){
            0-> {
                mFraBookShelf!!.tabCheck()
            }
            1->{
                mFraSearch!!.tabCheck()
            }
            2->{
                mFraRankingList!!.tabCheck()
            }
            3->{
                mFraMine!!.tabCheck()
            }
        }

    }

    private fun hideAllFragments(transaction: FragmentTransaction) {
        if (null != mFraBookShelf) {
            transaction.hide(mFraBookShelf)
        }
        if (null != mFraSearch) {
            transaction.hide(mFraSearch)
        }
        if (null != mFraRankingList) {
            transaction.hide(mFraRankingList)
        }
        if (null != mFraMine) {
            transaction.hide(mFraMine)
        }
    }

    override fun toggleOverridePendingTransition(): Boolean {
        return false
    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    override fun showException(error: ErrorBean?) {

    }

    override fun showBusinessError(error: ErrorBean?) {

    }
//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment重叠错乱
//        outState!!.putInt("currTabIndex", mIndex)
//    }
}