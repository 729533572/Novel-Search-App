package com.yonyou.hhtpos.ui.dinner.dishes.gq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yonyou.framework.library.base.BaseAppCompatActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.ui.dinner.wm.FRA_TakeOutDetail;

/**
 * 菜品估清列表
 * 作者：liushuofei on 2017/8/7 11:00
 */
public class ACT_SoldOut extends ACT_BaseMultiple {

    @Override
    protected void initView() {

    }

    @Override
    protected float getLeftWeight() {
        return 0.33f;
    }

    @Override
    protected Fragment getLeftContent() {
        return new FRA_SoldOutList();
    }

    @Override
    protected Fragment getRightContent() {
        return new FRA_TakeOutDetail();
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}
