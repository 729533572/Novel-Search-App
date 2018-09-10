package com.smart.framework.library.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.smart.framework.library.BaseApplication;

import java.util.ArrayList;

/**
 * Created by zj on 2017/3/6.
 * 邮箱：zjuan@yonyou.com
 * 描述：权限管理工具栏
 */
public class PermissionUtils {
    private static final String URI_STRING = "package:";
    private static PermissionUtils instance = null;
    private static Activity mContext;
    private ArrayList<String> permissionList = new ArrayList<>();
    public int mTagCode;
    public StringBuffer stringNotes;

    //申请所有权限请求码
    private static final int REQUEST_ALL_NEED_PERMISSION = 200;
    //定位权限申请的请求码
    public static final int MY_PERMISSIONS_REQUEST_LOCATE = 100;
    //扫描二维码权限申请的请求码
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    //应用升级时请求写文件权限的请求码
    public static final int MY_PERMISSIONS_REQUEST_SDCARD = 102;
    //请求打开相机权限请求码
    public static final int REQUEST_CAMERA_PERMISSION = 103;
    //请求打开相册权限请求码
    public static final int REQUEST_PHOTO_PERMISSION = 104;
    //QQ分享权限请求码
    public static final int REQUEST_QQ_SHARE_PERMISSION = 105;
    //读取联系人权限请求码
    public static final int REQUEST_CONTACTS_PERMISSION = 106;
    //版本升级申请写文件的权限
    public static final int REQUEST_WRITE_PERMISSION = 107;
    //直接拨打电话权限请求码
    public static final int REQUEST_CALL_PHONE = 107;
    //获取手机设备信息权限
    public static final int REQUEST_READ_PHONE_STATUS = 108;

    /**
     * 提供单例
     *
     * @param context
     * @return
     */
    public static PermissionUtils getInstance(Activity context) {
        mContext = context;
        if (instance == null) {
            instance = new PermissionUtils();
        }
        return instance;
    }

    /**
     * 第一次进入安装应用启动应用时申请所有的权限
     * 暂时保留该方法不使用
     */
    public void init() {
        String[] allPermissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.REQUEST_INSTALL_PACKAGES ,
                Manifest.permission.READ_PHONE_STATE};
//        ActivityCompat.requestPermissions(mContext, allPermissions, REQUEST_ALL_NEED_PERMISSION);
        ActivityCompat.requestPermissions(mContext, getDeniedPermisson(allPermissions), REQUEST_ALL_NEED_PERMISSION);
        //释放资源，防止内存泄露
        mContext = null;
    }

    /**
     * 需要动态申请的权限
     *
     * @param permissions
     */
    public boolean checkPermission(String[] permissions, int tagCode) {
        stringNotes = new StringBuffer();
        this.mTagCode = tagCode;
        String[] deniedPermissons = getDeniedPermisson(permissions);
        //由于华为手机无法监听到权限拒绝后的回调,权限拉取是系统自己处理的，在此处对华为手机做特殊处理
//        if (permissions.length == 0 && android.os.Build.MANUFACTURER.contains(DEVICE_HUWEI)) {
//            showPermissionDeniedDIA(mContext);
//            return false;
//        }
        //其他手机，如果没有被拒绝的权限，返回true
        if (deniedPermissons.length == 0) {
            return true;
        }
        //动态申请被拒绝的权限
        if (permissions.length > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.requestPermissions(mContext, permissions, tagCode);
            return false;
        }
        //释放资源，防止内存泄露
        mContext = null;
        return false;
    }

    /**
     * 从传入的权限列表中删选出被拒绝的权限，然后动态申请这些权限
     *
     * @param permissions
     * @return
     */
    private String[] getDeniedPermisson(String[] permissions) {
        permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            int permisson = ActivityCompat.checkSelfPermission(mContext, permissions[i]);
            if (isRefuse(permisson)) {
                permissionList.add(permissions[i]);
            }
        }
        String[] deniedString = permissionList.toArray(new String[permissionList.size()]);
        return deniedString;
    }

    /**
     * 权限是否被拒
     *
     * @param permission
     * @return
     */
    public boolean isRefuse(int permission) {
        return permission != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断某一个权限是否被授权
     *
     * @param permission
     * @return
     */
    public boolean checkPermissionIsGranted(String permission) {

        boolean hasPermission = ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;

        return hasPermission;
    }

//    /**
//     * 显示缺少权限提示窗
//     */
//    public void showPermissionDeniedDIA() {
//        DIA_Alert.showDialog(mContext, mContext.getResources().getString(R.string.permission_denied), mContext.getResources().getString(R.string.go_setting), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //跳转到应用设置页面
//                dialogInterface.dismiss();
//                Uri packageURI = Uri.parse(URI_STRING + mContext.getPackageName());
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                mContext.startActivity(intent);
//                //释放资源，防止内存泄露
//                mContext = null;
//            }
//        }, true);
//    }

    /**
     * 权限被拒后的处理
     *
     * @param context 上下文
     * @param msg     提示信息
     * @param b       toast持续时间是否为long
     */
    public void doWithRefusePermission(Context context, String msg, boolean b) {
        CommonUtils.makeEventToast(BaseApplication.getInstance(), msg, b);
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(getAppDetailSettingIntent());
            e.printStackTrace();
        }
    }

    /**
     * 获取权限设置页面的Action详细信息
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        return localIntent;
    }
}
