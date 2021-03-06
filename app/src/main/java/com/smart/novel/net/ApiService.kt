package com.zongxueguan.naochanle_android.retrofitrx

import com.smart.novel.bean.*
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
    fun login(@Field("phone") phone: String, @Field("code") code: String): Observable<BaseHttpResponse<UserBean>>

    /**
     * 书架
     */
    @GET("users/auth/get/my/fictions")
    fun getReadHistory(@Query("type") type: String, @Query("page") page: String): Observable<BaseHttpResponse<List<NovelBean>>>

    /**
     *  搜小说
     */
    //搜小说结果
    @GET("search/fictions")
    fun searchNovel(@Query("keyword") keyword: String): Observable<BaseHttpResponse<List<NovelBean>>>

    //热门搜索
    @GET("search/top/keywords")
    fun searchHotList(): Observable<BaseHttpResponse<List<HotSearchBean>>>

    /**
     * 排行榜
     */
    @GET("popular/fictions")
    fun getRankList(@Query("type") type: String): Observable<BaseHttpResponse<List<NovelBean>>>

    /**
     * 小说详情
     */
    //通过小说id获取小说章节
    @GET("fictions/{id}/chapters")
    fun getChapterList(@Path("id") id: String, @Query("latest") type: String, @Query("page") page: String): Observable<BaseHttpResponse<List<ChapterBean>>>

    //小说详情信息
    @GET("fictions/get/info")
    fun getNovelDetail(@Query("id") id: String): Observable<BaseHttpResponse<List<NovelBean>>>

    //收藏小说
    @POST("users//auth/like/fiction/{id}")
    fun doCollect(@Path("id") id: String): Observable<BaseHttpResponse<Any>>

    //删除收藏
    @POST("users//auth/unlike/fiction/{id}")
    fun deleteCollect(@Path("id") id: String): Observable<BaseHttpResponse<Any>>

    //增加小说阅读记录
    @FormUrlEncoded //使用@Field时记得添加@FormUrlEncoded
    @POST("users/auth/read/fiction/{id}")
    fun addReadRecord(@Path("id") id: String, @Field("chapter_name") chapter_name: String, @Field("chapter_number") chapter_number: String): Observable<BaseHttpResponse<Any>>

    //删除小说阅读记录
    @POST("users/auth/unread/fiction/{id}")
    fun deleteReadRecord(@Path("id") id: String): Observable<BaseHttpResponse<Any>>

    //通过小说名称、小说作者获取该小说其他网站的小说信息
    @GET("fictions/get/info/backup/website/list")
    fun getWebsiteList(@Query("author") author: String, @Query("book_name") book_name: String): Observable<BaseHttpResponse<List<WebsiteBean>>>

    //通过小说id,当前章节获取上章节信息
    @GET("fictions/{book_id}/last/chapter")
    fun getLastChapter(@Path("book_id") book_id: String, @Query("chapter_number") chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>>

    //通过小说id,当前章节获取下章节信息
    @GET("fictions/{book_id}/next/chapter")
    fun getNextChapter(@Path("book_id") book_id: String, @Query("chapter_number") chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>>

    //获取QQ阅读群
    @GET("customer/qq/group/info")
    fun getGroupQQ(): Observable<BaseHttpResponse<GroupInfo>>

    //根据当前小说来源，切换网址解析
    @GET("fictions/{website_id}/this/chapter")
    fun switchWebsite(@Path("website_id") website_id: String, @Query("chapter_number") chapter_number: String): Observable<BaseHttpResponse<List<ChapterBean>>>
}