package com.smart.novel.net

/**
 * Created by JoJo on 2018/8/29.
 * wechat：18510829974
 * description：数据格式统一封装
 */
class BaseHttpResponse<T> {
    var status: Int = 0
    var msg: String? = null
    var data: T? = null
}
