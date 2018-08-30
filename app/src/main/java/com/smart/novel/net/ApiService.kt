package com.zongxueguan.naochanle_android.retrofitrx

import com.smart.novel.bean.BaseHttpResponse
import com.smart.novel.db.bean.HotSearchBean
import com.smart.novel.db.bean.ReadHistoryBean
import com.smart.novel.db.bean.SearchResultBean
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
     * Get请求情形一: https://api-t.zmbai.com/v1/user/111111111
     */
    @GET("user/{user_id}")
    fun getUser(@Path("user_id") user_id: String): Observable<String>

    /**
     * Get请求情形一: https://api-t.zmbai.com/v1/sysmsg?user_id=1645
     */
    @GET("sysmsg")
    fun getMessage(@Query("user_id") user_id: String): Observable<String>

    /**
     * Get请求情形二: https://api-t.zmbai.com/v1/selfdesbyid/2
     */
    @GET("selfdesbyid/{teacher_id}")
    fun getTeacherSelfDes(@Path("teacher_id") teacher_id: String): Observable<String>

    /**
     * Get请求情形三：多个参数
     */
    @GET("selfdesbyid/{teacher_id}")
    fun getTeacherSelfDesOther(@Path("teacher_id") teacher_id: String, @QueryMap queryParams: Map<String, String>): Observable<String>


    /**
     * post请求情形二：map形式
     */
    @FormUrlEncoded
    @POST("attentions")
    fun doAttentionByMap(@FieldMap paramsMap: Map<String, String>)


    /**
     * post请求情形思：传递list类型参数(暂时没有用到)
     */
    @POST("url_path")
    fun doRequestByList(@Body requestList: List<Any>): Observable<Any>

    /**
     * 文件上传
     */
    @POST("upload/")
    fun uploadFile(@Body requestBody: RequestBody): Observable<String>


    /**
     * 上传Android设备信息: https://api-t.zmbai.com/v1/versions/9812
     * 加body:{"app_version":"1.0.2","imei":"355905074879520","phone_model":"samsung—SM-G9300","system_version":"Android:7.0"}
     */
    @POST("versions/{user_id}")
    fun uploadDeviceInfo(@Path("user_id") user_id: String, @Body deviceBean: String): Observable<Any>


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
    //搜索历史


}