package com.zongxueguan.naochanle_android.retrofitrx

import com.smart.novel.bean.HotSearchBean
import com.smart.novel.bean.RankListBean
import com.smart.novel.bean.ReadHistoryBean
import com.smart.novel.bean.SearchResultBean
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*


/**
 * Created by JoJo on 2018/1/15.
 * wechat:18510829974
 * description: api接口配置
 */
interface ApiService {

    /**
     * 文件上传
     */
    @POST("upload/")
    fun uploadFile(@Body requestBody: RequestBody): Observable<String>


    /**
     * 发送验证码
     */
    @GET("users/get/login/code")
    fun sendCode(@Query("phone") phone: String): Observable<BaseHttpResponse<Any>>

    /**
     * 登录校验:参数(body)
    phone 手机号
    code 验证码
    user 用户信息，JSON格式
    {
    "code":772278,
    "phone": 18516987934,
    "user": {
    "province": "广东",
    "city": "广州" }
    }
     */
    @FormUrlEncoded //使用@Field时记得添加@FormUrlEncoded
    @POST("users/check/login/code")
    fun login(@Field("phone") phone: String, @Field("code") code: String): Observable<BaseHttpResponse<Any>>

    /**
     * 书架
     */
    @GET("search/fictions?keyword=帝国&&page=1")
    fun getReadHistory(): Observable<BaseHttpResponse<List<ReadHistoryBean>>>

    /**
     *  搜小说
     */
    //搜小说结果
    @GET("search/fictions")
    fun searchNovel(@Query("keyword") keyword: String): Observable<BaseHttpResponse<List<SearchResultBean>>>

    //热门搜索
    @GET("search/top/keywords")
    fun searchHotList(): Observable<BaseHttpResponse<List<HotSearchBean>>>

    /**
     * 排行榜
     */
    @GET("popular/fictions")
    fun getRankList(@Query("type") type: String): Observable<BaseHttpResponse<List<RankListBean>>>

}