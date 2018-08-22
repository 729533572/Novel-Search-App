package com.smart.novel.global;


/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：接口地址配置
 */
public class API {

    /**
     * 服务器ip
     */
    public static final String API_SERVER_IP = "http://resthome.wotaomi.com";
    //正式环境
    public static final String BASE_SERVER_IP = "http://www.hafuyun.com";
    //测试环境
//    public static final String BASE_SERVER_IP = "http://resthome.php-liyong.com";

    public static final String SERVER_IP = BASE_SERVER_IP + "/";
    public static final String IMG_SERVER_IP = BASE_SERVER_IP;

    //H5地理围栏
    public static final String URL_H5 = SERVER_IP + "index.php?m=&c=home&a=warningSet&id=";
}
