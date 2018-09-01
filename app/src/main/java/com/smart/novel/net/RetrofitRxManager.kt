package com.zongxueguan.naochanle_android.retrofitrx

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.smart.framework.library.base.mvp.RxSchedulers
import com.smart.framework.library.bean.ErrorBean
import com.smart.framework.library.common.log.Elog
import com.smart.framework.library.common.utils.StringUtil
import com.smart.framework.library.net.retrofit.BaseObserverListener
import com.smart.framework.library.netstatus.NetUtils
import com.smart.novel.MyApplication
import com.smart.novel.global.API
import com.smart.novel.net.BaseHttpResponse
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by JoJo on 2018/1/15.
 * wechat:18510829974
 * description:
 */
object RetrofitRxManager {
    private val DEFAULT_TIMEOUT = 60L
    private var retrofit: Retrofit? = null
    //请求头信息
    private val HEADER_CONNECTION = "keep-alive"
//    private val BASE_URL = "https://www.sojson.com/open/api/"

    fun getRetrofit(context: Context): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitRxManager::class.java) {
                if (retrofit == null) {
                    ///getExternalFilesDir:Android/data/包名/files/okhttp… (该路径通常挂载在/mnt/sdcard/下)
                    val cache = Cache(File(context.getExternalFilesDir("ok"), ""), 14 * 1024 * 100)
                    var mClient = OkHttpClient.Builder()
                            //添加公告查询参数
//                            .addInterceptor(CommonQueryParamsInterceptor ())
                            //处理多个Baseurl的拦截器
//                            .addInterceptor(MutiBaseUrlInterceptor())
                            .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context)))
                            .cache(cache)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(getHeaderInterceptor())
                            .addInterceptor(LoggingInterceptor())//添加请求拦截(可以在此处打印请求信息和响应信息)
                            .addInterceptor(CacheInterceptor())
                            .addNetworkInterceptor(CacheInterceptor())//必须要有，否则会返回504
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build()
                    retrofit = Retrofit.Builder()
                            .baseUrl(API.BASE_SERVER_IP)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mClient)
                            .build()
                }
            }
        }
        return retrofit
    }

    /**
     * 不添加Retrofit cache
     */
    fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitRxManager::class.java) {
                if (retrofit == null) {
                    ///getExternalFilesDir:Android/data/包名/files/okhttp… (该路径通常挂载在/mnt/sdcard/下)
                    var mClient = OkHttpClient.Builder()
                            //添加公告查询参数
//                            .addInterceptor(CommonQueryParamsInterceptor ())
                            //处理多个Baseurl的拦截器
//                            .addInterceptor(MutiBaseUrlInterceptor())
                            .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.context)))
                            .retryOnConnectionFailure(true)
                            .addInterceptor(getHeaderInterceptor())
                            .addInterceptor(LoggingInterceptor())//添加请求拦截(可以在此处打印请求信息和响应信息)
                            .addInterceptor(CacheInterceptor())
                            .addNetworkInterceptor(CacheInterceptor())//必须要有，否则会返回504
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build()
                    retrofit = Retrofit.Builder()
                            .baseUrl(API.BASE_SERVER_IP)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mClient)
                            .build()
                }
            }
        }
        return retrofit
    }

    /**
     * 获取Api接口,带cache
     */
    fun getRequestService(context: Context): ApiService {
        return getRetrofit(context)!!.create(ApiService::class.java)
    }

    /**
     * 获取Api接口
     */
    fun getRequestService(): ApiService {
        return getRetrofit()!!.create(ApiService::class.java)
    }

    /**
     * 设置公共查询参数
     */
    class CommonQueryParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain!!.request()
            val url = request.url().newBuilder()
                    .addQueryParameter("zxg", "mingbai")
                    .addQueryParameter("zxg", "hehe")
                    .build()
            return chain!!.proceed(request.newBuilder().url(url).build())
        }
    }

    /**
     * header
     */
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain!!.request()
            val requestBuilder = request.newBuilder()
                    .addHeader("api_key", "mingjiazongxueguan")
//                    .addHeader("authorization", UserConstants.AUTHORIZATION)
                    .method(request.method(), request.body())
                    .build()
            return chain!!.proceed(requestBuilder)
        }
    }

    fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain!!.request()
            val requestBuilder = request.newBuilder()
                    .addHeader("Connection", HEADER_CONNECTION)
//                    .addHeader("authorization", UserConstants.AUTHORIZATION)// TOKEN
                    .method(request.method(), request.body())
                    .build()
            chain!!.proceed(requestBuilder)
        }
    }

    //短缓存有效期为10分钟
    val CACHE_STALE_SHORT = 60 * 10
    //长缓存有效期为7天
    val CACHE_STALE_LONG = "60 * 60 * 24 * 7"
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    val CACHE_CONTROL_NETWORK = "max-age=0"

    /**
     * 设置缓存
     */
    class CacheInterceptor : Interceptor {
        //云端响应头拦截器，用来适配缓存策略
        override fun intercept(chain: Interceptor.Chain?): Response {
            var request = chain!!.request()
            //无网络时，去cache中请求
            if (!NetUtils.isNetworkConnected(MyApplication.context)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            var response = chain.proceed(request)
            if (NetUtils.isNetworkConnected(MyApplication.context)) {
                var cacheControl: String = request.cacheControl().toString()
//                Log.e("Tag", "有网")
                return response.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build() // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
            } else {
//                Log.e("Tag", "无网")
                //无网络时，设置超时为CACHE_STALE_LONG  只对get有用, post没有缓冲
                return response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build()
            }
            return response
        }
    }

    /**
     * 自定义log日志打印:http://blog.csdn.net/csdn_lqr/article/details/61420753
     */
    class LoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            val request = chain!!.request()
            val t1 = System.nanoTime()//请求发起的时间
            val method = request.method()
            val jsonObject = JSONObject()
            if ("POST" == method || "PUT" == method) {
                if (request.body() is FormBody) {
                    val body = request.body() as FormBody?//as?安全转型，当转型不成功的时候，它会返回 null
                    if (body != null) {
                        for (i in 0 until body!!.size()) {
                            try {
                                jsonObject.put(body!!.name(i), body!!.encodedValue(i))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        }
                    }
                    Log.e("request", String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                            request.url(), chain!!.connection(), jsonObject.toString(), request.method()))
                } else {
                    val buffer = Buffer()
                    val requestBody = request.body()
                    if (requestBody != null) {
                        request.body()!!.writeTo(buffer)
                        val body = buffer.readUtf8()
                        Log.e("request", String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                                request.url(), chain!!.connection(), body, request.method()))
                    }
                }
            } else {
                Log.e("request", String.format("发送请求 %s on %s%nMethod:%s",
                        request.url(), chain!!.connection(), request.method()))
            }
            val response = chain!!.proceed(request)
            val t2 = System.nanoTime()//收到响应的时间
            val responseBody = response.peekBody((1024 * 1024).toLong())
            Log.e("request",
                    String.format("Retrofit接收响应： %s %n返回json:%s %n耗时：%.1fms %nCode:%s",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6, response.code()
                    ))
            Elog.e("request", "cookie=" + SharedPrefsCookiePersistor(MyApplication.context))
            return response
        }
    }

    /**
     * 该拦截器用于记录应用中的网络请求的信息
     */
    fun getHttpLogingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //包含所有的请求信息
            //如果收到响应是json才打印
            if ("{" == message || "[" == message) {
                Log.d("TAG", "收到响应: " + message)
            }
            Log.d("TAG", "message=" + message)
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private val BASE_URL_OTHER = "http://wthrcdn.etouch.cn/"

    /**
     *  添加可以处理多个Baseurl的拦截器：http://blog.csdn.net/qq_36707431/article/details/77680252
     * Retrofit(OKHttp)多BaseUrl情况下url实时自动替换完美解决方法:https://www.2cto.com/kf/201708/663977.html
     */
    class MutiBaseUrlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            //获取request
            val request = chain!!.request()
            //从request中获取原有的HttpUrl实例oldHttpUrl
            val oldHttpUrl = request.url()
            //获取request的创建者builder
            val builder = request.newBuilder()
            //从request中获取headers，通过给定的键url_name
            val headerValues = request.headers("url_name")
            if (headerValues != null && headerValues.size > 0) {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                builder.removeHeader("url_name")
                //匹配获得新的BaseUrl
                val headerValue = headerValues[0]
                var newBaseUrl: HttpUrl? = null
                if ("other" == headerValue) {
                    newBaseUrl = HttpUrl.parse(BASE_URL_OTHER)
                    //                } else if ("other".equals(headerValue)) {
                    //                    newBaseUrl = HttpUrl.parse(BASE_URL_PAY);
                } else {
                    newBaseUrl = oldHttpUrl
                }
                //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
                val newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme("http")//更换网络协议,根据实际情况更换成https或者http
                        .host(newBaseUrl!!.host())//更换主机名
                        .port(newBaseUrl!!.port())//更换端口
                        .removePathSegment(0)//移除第一个参数v1
                        .build()
                //重建这个request，通过builder.url(newFullUrl).build()；
                // 然后返回一个response至此结束修改
                Log.e("Url", "intercept: " + newFullUrl.toString())
                return chain!!.proceed(builder.url(newFullUrl).build())
            }
            return chain!!.proceed(request)
        }
    }

    /**
     * 上传文件
     *
     * @param mImagePath
     * @return
     */
    fun getUploadFileRequestBody(imagePath: String): RequestBody {
        val file = File(imagePath)
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build()
        return requestBody

    }

    /**
     * 建立请求
     */
    fun <T> doRequest(observable: Observable<BaseHttpResponse<T>>, observerListener: BaseObserverListener<T>): DisposableObserver<BaseHttpResponse<T>> {
        return observable
                .compose(RxSchedulers.io_main())
                .subscribeWith(object : DisposableObserver<BaseHttpResponse<T>>() {
                    override fun onNext(result: BaseHttpResponse<T>) {
                        if (result.status === 0) {
                            observerListener.onSuccess(result.data)
                        } else {
                            var errorBean = ErrorBean()
                            errorBean.code = StringUtil.getString(result.status)
                            errorBean.msg = result.msg
                            observerListener.onBusinessError(errorBean)
                        }
                    }

                    override fun onError(e: Throwable) {
                        observerListener.onError(e)
                    }

                    override fun onComplete() {
                        observerListener.onComplete()
                    }
                    //未统一封装数据格式
//                    override fun onNext(result: T) {
//                        observerListener.onNext(result)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        observerListener.onError(e)
//                    }
//
//                    override fun onComplete() {
//                        observerListener.onComplete()
//                    }
                })
    }

    /**
     * 直接使用RetrofitRxManager请求
     */
//    fun doNormalRequest() {
////        multipleStatusView.showLoading()
//        Handler().postDelayed({
//            RetrofitRxManager.getRequestService().getWeather("北京")
//                    .compose(RxSchedulers.io_main())
//                    .subscribeWith(object : DisposableObserver<WeatherEntity>() {
//                        override fun onNext(bean: WeatherEntity) {
////                            var viewBinder = viewDataBinding as FraBookshelfBinding
////                            viewBinder.weather = bean
//////                            multipleStatusView.showContent()
////                            multipleStatusView.showEmpty(R.drawable.ic_reading_no_data, MyApplication.context.getString(R.string.string_empty_bookshelf))
//                        }
//
//                        override fun onError(e: Throwable) {
//                        }
//
//                        override fun onComplete() {
//                        }
//                    })
//        }, 1500)
//    }
}