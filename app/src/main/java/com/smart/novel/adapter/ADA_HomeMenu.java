//package com.smart.tvpos.adapter;
//
//import android.content.Context;
//
//import com.smart.framework.library.adapter.lv.CommonAdapterListView;
//import com.smart.framework.library.adapter.lv.ViewHolderListView;
//import com.smart.tvpos.R;
//import HomeMenuEntity;
//
///**
// * Created by JoJo on 2018/6/21.
// * wechat：18510829974
// * description：homepage menulist adapter
// */
//public class ADA_HomeMenu extends CommonAdapterListView<HomeMenuEntity> {
//    public ADA_HomeMenu(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected int itemLayoutId() {
//        return R.layout.item_menu;
//    }
//
//    @Override
//    protected void convert(ViewHolderListView holder, HomeMenuEntity itemMenu, int position) {
//        holder.setText(R.id.tv_menu_txt, itemMenu.menuTxt);
//        holder.setImageResource(R.id.iv_menu, itemMenu.menuIcon);
//    }
//}
