package com.yonyou.hhtpos.ui.store;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.common.utils.AppSharedPreferences;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.StoreEntity;
import com.yonyou.hhtpos.dialog.DIA_ChooseStore;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.presenter.IGetAllShopsPresenter;
import com.yonyou.hhtpos.presenter.Impl.GetAllShopsPresenterImpl;
import com.yonyou.hhtpos.ui.login.ACT_Login;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.util.SpUtil;
import com.yonyou.hhtpos.view.IGetAllShopsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zj on 2017/6/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：绑定门店页面
 */
public class FRA_BindStore extends BaseFragment implements IGetAllShopsView {
    @Bind(R.id.rb_finish)
    RadioButton rbFinish;
    @Bind(R.id.choose_store)
    TextView tvStoreName;
    @Bind(R.id.ll_content)
    LinearLayout content;

    private DIA_ChooseStore diaChooseStore;
    private AppSharedPreferences sharePre;

    /**
     * 获取所有门店
     */
    private IGetAllShopsPresenter mGetAllShopsPresenter;
    private ArrayList<StoreEntity> shopList = new ArrayList<>();

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return content;
    }

    @Override
    protected void initViewsAndEvents() {
        sharePre = new AppSharedPreferences(mContext);
        mGetAllShopsPresenter = new GetAllShopsPresenterImpl(mContext, this);
        mGetAllShopsPresenter.getAllShops("");

        rbFinish.setChecked(false);
        tvStoreName.addTextChangedListener(new InputWatcher());

        diaChooseStore = new DIA_ChooseStore(mContext, new DIA_ChooseStore.OnChooseStoreListener() {
            @Override
            public void onChooseStore(StoreEntity shop, int poition) {
                tvStoreName.setText(shop.shopName);
                //保存shopId
                sharePre.putString(SpUtil.SHOP_ID, StringUtil.getString(shop.id));
                //保存shopName
                sharePre.putString(SpUtil.SHOP_NAME, StringUtil.getString(shop.shopName));
                Constants.SHOPID = shop.id;
                Elog.e("SHOP_ID",sharePre.getString(SpUtil.SHOP_ID));
                Elog.e("SHOP_NAME",sharePre.getString(SpUtil.SHOP_NAME));

                //绑定成功后,发送一个广播
                sendBroadcast(ReceiveConstants.BIND_SUCCESS);
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_bind_store;
    }

    @OnClick({R.id.rb_finish, R.id.choose_store})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_finish:
                readyGoThenKill(ACT_Login.class);
                break;
            case R.id.choose_store:
                if (shopList != null && shopList.size() > 0) {
                    diaChooseStore.setData(shopList);
                    diaChooseStore.show();
                }
                break;
        }
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void getShops(List<StoreEntity> result) {
        this.shopList = (ArrayList<StoreEntity>) result;
    }

    /**
     * 输入框监视器
     */
    class InputWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //只有店名有内容时，登录按钮才可以点击
            if (TextUtils.isEmpty(tvStoreName.getText().toString())) {
                rbFinish.setChecked(false);
            } else {
                rbFinish.setChecked(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
