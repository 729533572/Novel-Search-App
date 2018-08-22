package com.smart.tvpos.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.utils.AppSharedPreferences;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.common.utils.TimeCount;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.UserEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;
import com.smart.tvpos.util.SharePreConstants;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：
 */
public class ACT_Login extends BaseActivity {
    @Bind(R.id.rb_get_code)
    RadioButton rbGetCode;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    //验证码倒计时
    private TimeCount timer;
    //倒计时间隔时间
    private final int countDownInterval = 1000;
    //验证码倒计时总时间
    private final int msgTime = 60 * 1000;
    private AppSharedPreferences sharePre;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_login;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        timer = new TimeCount(msgTime, countDownInterval, rbGetCode, mContext);
        sharePre = new AppSharedPreferences(this);

        String token = sharePre.getString(SharePreConstants.USER_SIGN);
        if (!TextUtils.isEmpty(token)) {
            finish();
        } else {
            etPhone.setText(sharePre.getString(SharePreConstants.USER_NAME));
            etPhone.setSelection(etPhone.getText().length());
        }

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (ScreenUtil.getScreenWidth(mContext) * 2.0f  / 5));
//        layoutParams.addRule();
//        llLogin.setLayoutParams(layoutParams);
    }


    @OnClick({R.id.rb_get_code, R.id.rb_login})
    public void onClick(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.rb_get_code:
                timer.start();
                break;
            //登录
            case R.id.rb_login:
                if (doVerifyEmpty()) {
                    requestLogin();
                    showDialogLoading("正在登入...");
                }
                break;
        }
    }

    /**
     * 校验用户输入
     *
     * @return
     */
    private boolean doVerifyEmpty() {
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            CommonUtils.makeEventToast(MyApplication.getContext(), "账号不能为空", false);
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            CommonUtils.makeEventToast(MyApplication.getContext(), "密码不能为空", false);
            return false;
        }
        return true;
    }

    private void requestLogin() {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", "login");
//        params.put("name", "hafuadmin");
//        params.put("password", "666666");
        params.put("name", etPhone.getText().toString());
        params.put("password", etPassword.getText().toString());
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<UserEntity>() {
            @Override
            public void onReqSuccess(UserEntity userEntity) {
                dismissDialogLoad();
                CommonUtils.makeEventToast(MyApplication.getContext(), "登录成功", false);
                //存储用户信息
                sharePre.putString(SharePreConstants.USER_SIGN, userEntity.getSign() == null ? "" : userEntity.getSign());
                sharePre.putString(SharePreConstants.USER_ID, userEntity.getId() + "");
                sharePre.putString(SharePreConstants.USER_NAME, userEntity.getName());
                sharePre.putString(SharePreConstants.TYPE, userEntity.getType());
                sharePre.putString(SharePreConstants.BRANCH_NAME, userEntity.getBranchName());
                sharePre.putBoolean(SharePreConstants.LOGOUT, false);
                Constants.USER_ID = sharePre.getString(SharePreConstants.USER_ID);
                Constants.USER_SIGN = sharePre.getString(SharePreConstants.USER_SIGN);
                Constants.TYPE = sharePre.getString(SharePreConstants.TYPE);
                Constants.BRANCH_NAME = sharePre.getString(SharePreConstants.BRANCH_NAME);
                MyApplication.isLogin = true;
                finish();
            }

            @Override
            public void onFailure(String result) {
                dismissDialogLoad();
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                dismissDialogLoad();
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    protected long getRefreshTime() {
        return 0;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
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
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
