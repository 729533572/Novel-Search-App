package com.smart.novel.test;

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
import com.smart.framework.library.net.ReqCallBack;
import com.smart.framework.library.net.RequestManager;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.novel.MyApplication;
import com.smart.novel.R;
import com.smart.novel.bean.UserEntity;
import com.smart.novel.db.bean.TestBean;
import com.smart.novel.db.gen.TestBeanDao;
import com.smart.novel.db.manager.DbManager;
import com.smart.novel.global.API;
import com.smart.novel.util.SharePreConstants;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
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
    private TestBean testBean;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_login;
    }

    @Override
    protected void handleStatusBar() {

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
    }


    @OnClick({R.id.rb_get_code, R.id.rb_login})
    public void onClick(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.rb_get_code:
                if (getLoginUser() != null) {
                    CommonUtils.makeEventToast(mContext, getLoginUser().getMsg(), false);
                }
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
            CommonUtils.makeEventToast(MyApplication.getInstance(), "账号不能为空", false);
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            CommonUtils.makeEventToast(MyApplication.getInstance(), "密码不能为空", false);
            return false;
        }
        return true;
    }

    private void requestLogin() {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", "login");
        params.put("name", etPhone.getText().toString());
        params.put("password", etPassword.getText().toString());
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<UserEntity>() {
            @Override
            public void onReqSuccess(UserEntity userEntity) {
                dismissDialogLoad();
                CommonUtils.makeEventToast(MyApplication.getInstance(), "登录成功", false);
                //存储用户信息
                sharePre.putString(SharePreConstants.USER_SIGN, userEntity.getSign() == null ? "" : userEntity.getSign());
                sharePre.putString(SharePreConstants.USER_ID, userEntity.getId() + "");
                sharePre.putString(SharePreConstants.USER_NAME, userEntity.getName());
                sharePre.putString(SharePreConstants.TYPE, userEntity.getType());
                sharePre.putString(SharePreConstants.BRANCH_NAME, userEntity.getBranchName());
                sharePre.putBoolean(SharePreConstants.LOGOUT, false);
//                Constants.USER_ID = sharePre.getString(SharePreConstants.USER_ID);
//                Constants.USER_SIGN = sharePre.getString(SharePreConstants.USER_SIGN);
//                Constants.TYPE = sharePre.getString(SharePreConstants.TYPE);
//                Constants.BRANCH_NAME = sharePre.getString(SharePreConstants.BRANCH_NAME);
                finish();
            }

            @Override
            public void onFailure(String result) {
                dismissDialogLoad();
                CommonUtils.makeEventToast(MyApplication.getInstance(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                dismissDialogLoad();
                testBean = new TestBean();
                testBean.setMsg(error.getMsg());
                DbManager.getInstance().insertOrReplace(TestBean.class, testBean);
                CommonUtils.makeEventToast(MyApplication.getInstance(), error.getMsg(), false);
            }
        });
    }

    /**
     * 测试GreenDao
     *
     * @return
     */
    public TestBean getLoginUser() {
        ArrayList<Property> properties = new ArrayList<Property>();
        properties.add(TestBeanDao.Properties.Msg);
        // 匹配条件
        ArrayList<Object> objects = new ArrayList<Object>();
        objects.add(testBean.getMsg());
        TestBean userInfo = null;
        try {
            userInfo = (TestBean) DbManager.getInstance().query(TestBean.class, properties, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
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
