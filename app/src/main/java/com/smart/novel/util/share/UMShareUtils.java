package com.smart.novel.util.share;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.smart.framework.library.BaseApplication;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.common.utils.PermissionUtils;
import com.smart.novel.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;
import java.lang.ref.WeakReference;

import static com.smart.framework.library.common.utils.PermissionUtils.REQUEST_QQ_SHARE_PERMISSION;
import static com.smart.novel.util.share.UMShareUtils.CustomShareListener.getPlatformNote;

/**
 * Created by JoJo on 2017/12/9.
 * wechat：18510829974
 * description：友盟统一管理分享工具类(使用友盟的分享面板)
 */
public class UMShareUtils {
    //QQ的包名
    public static final String QQ_PAKAGENAME = "com.tencent.mobileqq";
    //微信的包名
    public static final String WEIXIN_PAKAGENAME = "com.tencent.mm";
    //新浪客户端包名=
    public static final String SINA_PAKAGENAME = "com.sina.weibo";
    private static CustomShareListener mShareListener;
    private static ShareAction mShareAction;

    public static void initShareBoard(final Activity context, final ShareEntity shareBean) {
        //第三方分享
        mShareListener = new CustomShareListener(context);
             /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(context).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                        doshare(share_media, shareBean, context);
                        if (share_media == SHARE_MEDIA.WEIXIN || share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.WEIXIN_FAVORITE) {
                            isPlatformInstalled(context, share_media, WEIXIN_PAKAGENAME, shareBean);
                        } else if (share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.QZONE) {
                            isPlatformInstalled(context, share_media, QQ_PAKAGENAME, shareBean);
                        } else if (share_media == SHARE_MEDIA.SINA) {
                            isPlatformInstalled(context, share_media, SINA_PAKAGENAME, shareBean);
                        }

                    }
                });
        mShareAction.open();
    }

    /**
     * 判断是否安装了应用，再确定是否进行分享
     *
     * @param mContext
     * @param platform
     * @param mediaPakageName
     * @param shareBean
     */
    public static void isPlatformInstalled(Activity mContext, SHARE_MEDIA platform, String mediaPakageName, ShareEntity shareBean) {
        //第三方分享
        mShareListener = new CustomShareListener(mContext);

        boolean packageInstalled = isPackageInstalled(mediaPakageName);
        if (packageInstalled) {
//            //如果使用 targetSdkVersion 23或23以上，需要做6.0适配，手动获取WRITE_EXTERNAL_STORAGE权限，否则QQ不能进行图片分享
            if (platform == SHARE_MEDIA.QQ) {
                String[] needPermissons = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                boolean hasPermisson = PermissionUtils.getInstance(mContext).checkPermission(needPermissons, REQUEST_QQ_SHARE_PERMISSION);
                if (hasPermisson) {
                    doshare(platform, shareBean, mContext);
                }
            } else {
                doshare(platform, shareBean, mContext);
            }
//            doshare(platform, shareBean, mContext);
        } else {
            //没有安装新浪客户端，调起网页版
            if (platform == SHARE_MEDIA.SINA) {
                UMShareConfig config = new UMShareConfig();
                config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
                UMShareAPI.get(mContext).setShareConfig(config);
                doshare(platform, shareBean, mContext);
                return;
            }
            CommonUtils.makeEventToast(mContext, mContext.getResources().getString(R.string.no_install_platform) + getPlatformNote(platform, mContext), false);
        }
    }

    /**
     * 判断手机上是否安装了某个应用
     *
     * @param packageName 应用包名
     * @return
     */
    public static boolean isPackageInstalled(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 开始分享
     *
     * @param share_media
     * @param shareBean
     * @param context
     */
    private static void doshare(SHARE_MEDIA share_media, ShareEntity shareBean, Activity context) {
        UMWeb web = new UMWeb(shareBean.shareUrl == null ? "http://www.zmbai.com" : shareBean.shareUrl);
        web.setTitle(shareBean.title == null ? "小说搜索神器" : shareBean.title);
        web.setDescription(shareBean.content == null ? "应用下载链接" : shareBean.content);
        web.setThumb(new UMImage(context, R.drawable.app_logo));
        new ShareAction(context).withMedia(web)
                .setPlatform(share_media)
                .setCallback(mShareListener)
                .share();
    }

    static class CustomShareListener implements UMShareListener {

        private WeakReference<Activity> mActivity;

        private CustomShareListener(Activity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
                CommonUtils.makeEventToast(mActivity.get(), BaseApplication.getInstance().getString(R.string.string_collect_success), false);
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
//                    Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    CommonUtils.makeEventToast(mActivity.get(), BaseApplication.getInstance().getString(R.string.string_share_successful), false);
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
//                Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                CommonUtils.makeEventToast(mActivity.get(), BaseApplication.getInstance().getString(R.string.string_share_failed), false);
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            CommonUtils.makeEventToast(mActivity.get(), BaseApplication.getInstance().getString(R.string.string_share_canceled), false);

        }

        /**
         * 根据平台获取对应第三方应用的包名
         *
         * @param platform
         * @return
         */
        public static String getPackageNameByPlatform(SHARE_MEDIA platform) {
            if (platform == SHARE_MEDIA.WEIXIN) {
                return WEIXIN_PAKAGENAME;
            } else if (platform == SHARE_MEDIA.QQ) {
                return QQ_PAKAGENAME;
            } else if (platform == SHARE_MEDIA.SINA) {
                return SINA_PAKAGENAME;
            }
            return "";
        }

        /**
         * 根据平台获取提示信息
         *
         * @param platform
         * @param mContext
         * @return
         */
        public static String getPlatformNote(SHARE_MEDIA platform, Context mContext) {
            String platformNote = "";
            if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE || platform == SHARE_MEDIA.WEIXIN_FAVORITE) {
                platformNote = mContext.getResources().getString(R.string.note_weixin);
            } else if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE) {
                platformNote = mContext.getResources().getString(R.string.note_qq);
            } else if (platform == SHARE_MEDIA.SINA) {
                platformNote = mContext.getResources().getString(R.string.note_weibo);
            }
            return platformNote;
        }

        /**
         * 释放资源
         */
        public static void recycleUMShare() {
            UMShareUtils.mShareAction = null;
            UMShareUtils.mShareListener = null;
        }

        //                /**
//                 * 6.0以上动态申请权限的回调
//                 * @param requestCode
//                 * @param permissions
//                 * @param grantResults
//                 */
//                @Override
//                public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//                    if (requestCode == REQUEST_QQ_SHARE_PERMISSION) {
//                        if (permissions.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//读写权限
//                            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//同意
//                                mDiaShare.share(SHARE_MEDIA.QQ);
//                            } else {//不同意
//                                PermissionUtils.getInstance(mContext).showPermissionDeniedDIA(mContext);
//                            }
//                        }
//                    }
//                }
        //使用该类注意，在对应的分享页面的Activity中添加：

//    /**
//     * 在分享所在的Activity里复写onActivityResult方法,如果不实现onActivityResult方法，会导致分享或回调无法正常进行
//     *
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//    }
    }
}
