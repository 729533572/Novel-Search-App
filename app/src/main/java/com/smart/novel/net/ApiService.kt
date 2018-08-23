package com.zongxueguan.naochanle_android.retrofitrx

import com.smart.novel.net.WeatherEntity
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*


/**
 * Created by JoJo on 2018/1/15.
 * wechat:18510829974
 * description: api接口——.kt文件
 * (请求参数的配置)-->https://www.jianshu.com/p/7687365aa946
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
     * post请求情形一：Filed方式
     * https://api-t.zmbai.com/v1/attentions
     * Params:{"topic_id":"78","type":"2","user_id":"1694"}
     */
    @FormUrlEncoded //使用@Field时记得添加@FormUrlEncoded
    @POST("attentions")
    fun doAttentionOne(@Field("topic_id") topic_id: String, @Field("type") type: String, @Field("user_id") user_id: String)

    /**
     * post请求情形二：map形式
     */
    @FormUrlEncoded
    @POST("attentions")
    fun doAttentionByMap(@FieldMap paramsMap: Map<String, String>)

    /**
     * post请求情形三：传递Object类型参数
     * https://api-t.zmbai.com/v1/collections
     * Params:{"source_id":"11","type":"1","user_id":"1694"}
     */
//    @POST("collections")
//    fun doCollectObject(@Body requestEntity: RequestEntity): Observable<String>

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
     * put请求情形一:https://api-t.zmbai.com/v1/collections/139
     */
    @FormUrlEncoded
    @PUT("collections/{voiceId}")
    fun cancelCollect(@Path("voiceId") voiceId: String, @Field("end_time") end_time: String)

    /**
     * put请求情形二:http://102.10.10.132/api/Accounts/1?access_token=1234123
     * params:{"json"}
     */
    @PUT("Accounts/{accountId}")
    fun updateExtras(@Path("accountId") accountId: String, @Query("access_token") access_token: String, @Body requestBean: String)

    /**
     * delete请求:http://102.10.10.132/api/Comments/1?access_token=1234123
     */
    @DELETE("Comments/{commentId}")
    fun deleteRequest(@Path("commentId") commentId: String, @Query("access_token") access_token: String)

    /**
     * https://api.zmbai.com/v1/teams/309686812  解散群
     */
    @DELETE("teams/{tid}")
    fun dismissTeam(@Path("tid") tid: String): Observable<Any>

    /**
     * 删除群成员/主动退群
     * https://api.zmbai.com/v1/tmembers/309498190/member/7981?is_active=1
     *  //主动退群，才传is_active参数给后台
     */
    @DELETE("tmembers/{tid}/member/{user_id}")
    fun leaveTeam(@Path("tid") tid: String, @Path("user_id") user_id: String, @Query("is_active") is_active: String): Observable<Any>

    /**
     * 删除群成员
     * https://api.zmbai.com/v1/tmembers/309498190/member/7981
     */
    @DELETE("tmembers/{tid}/member/{user_id}")
    fun deleteTeamMember(@Path("tid") tid: String, @Path("user_id") user_id: String): Observable<Any>

    /**
     * 上传Android设备信息: https://api-t.zmbai.com/v1/versions/9812
     * 加body:{"app_version":"1.0.2","imei":"355905074879520","phone_model":"samsung—SM-G9300","system_version":"Android:7.0"}
     */
    @POST("versions/{user_id}")
    fun uploadDeviceInfo(@Path("user_id") user_id: String, @Body deviceBean: String): Observable<Any>

    /**
     * 请求微信支付
     */
    @POST("versions/{user_id}")
    fun requestWexinPay(@Path("user_id") user_id: String, @Body payReqEntity: String): Observable<String>

    /**
     * 购买记录https://api-t.zmbai.com/v1/userorders/1899
     */
    @GET("userorders/{user_id}")
    fun getBuyRecoreds(@Path("user_id") user_id: String): Observable<Any>

    //获取北京的天气信息
//    "https://www.sojson.com/open/api/weather/json.shtml?city=" + "北京"
//    如果Oberserver<String> 则会报错：onErrorExpected a string but was BEGIN_OBJECT at line 1 column 2 path $
    @GET("weather/json.shtml")
    fun getWeather(@Query("city") city: String): Observable<WeatherEntity>


    /**
    @Path：所有在网址中的参数（URL的问号前面），如：
    http://102.10.10.132/api/Accounts/{accountId}
    @Query：URL问号后面的参数，如：
    http://102.10.10.132/api/Comments?access_token={access_token}
    @QueryMap：相当于多个@Query
    @Field：用于POST请求，提交单个数据
    @Body：相当于多个@Field，以对象的形式提交
    ----------------Tips-------------------
    Tips1
    使用@Field时记得添加@FormUrlEncoded
    Tips2
    若需要重新定义接口地址，可以使用@Url，将地址以参数的形式传入即可。如
    @GET
    Call<List<Activity>> getActivityList(
    @Url String url,
    @QueryMap Map<String, String> map);
    Call<List<Activity>> call = service.getActivityList(
    "http://115.159.198.162:3001/api/ActivitySubjects", map);
     */
}