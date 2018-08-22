package com.smart.novel.manager;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.bean.ResultBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.novel.MyApplication;
import com.smart.tvpos.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by JoJo on 2016/12/19.
 * 统一封装的网络请求入口类，提供网络的GET、POST请求（内部采用OKHTTP实现）
 */

public class RequestManager {

    private static final String TAG = RequestManager.class.getSimpleName();

    /**
     * mdiatype 这个需要和服务端保持一致（请求数据类型）
     */
//    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * mdiatype 这个需要和服务端保持一致（请求数据类型）
     */
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    /**
     * mdiatype 这个需要和服务端保持一致（请求数据类型）
     */
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");

    /**
     * 请求接口根地址
     */
    private static final String BASE_URL = "http://xxx.com/openapi";
    /**
     * 请求接口实例对象
     */
    private static volatile RequestManager mInstance;

    /**
     * okHttpClient 实例
     */
    private OkHttpClient mOkHttpClient;
    /**
     * 全局处理子线程和M主线程通信
     */
    private Handler okHttpHandler;

    /**
     * 普通连接的超时时间
     */
    private final int CONNECT_TIMEOIUT = 10;

    /**
     * 服务器返回code值
     */
    private final int RESPONCE_CODE_200 = 200;
    private final int RESPONCE_NO_ERROR_CODE = 0;
    /**
     * 服务器返回code值
     */
    private final int RESPONCE_CODE_400 = 400;
    /**
     * 服务器返回code值
     */
    private final int RESPONCE_CODE_500 = 500;

    /**
     * 初始化RequestManager
     *
     * @param context
     */
    public RequestManager(Context context) {
        //初始化Handler
        if (null == context) {
            context = MyApplication.getInstance();
        }

        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOIUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(CONNECT_TIMEOIUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(CONNECT_TIMEOIUT, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new CommonQueryParamsInterceptor())
                .cookieJar(new CookieJar() {//OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
        okHttpHandler = new Handler(context.getMainLooper());
    }

    /**
     * 设置公共查询参数
     */
    public class CommonQueryParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("m", "api")
                    .addQueryParameter("c", "AdminTv")
                    .build();
            return chain.proceed(request.newBuilder().url(url).build());
        }
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static RequestManager getInstance(Context context) {
        RequestManager inst = mInstance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new RequestManager(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static RequestManager getInstance() {
        return getInstance(null);
    }


    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @param <T>       数据泛型
     * @return
     */
    public <T> Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {

            int pos = 0;
            if (null != paramsMap) {
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
//                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    tempParams.append(String.format("%s=%s", key, paramsMap.get(key)));
                    pos++;
                }
            }
            final String requestUrl;
            if (null != tempParams && !TextUtils.isEmpty(tempParams)) {
                requestUrl = String.format("%s?%s", actionUrl, tempParams.toString());
            } else {
                requestUrl = actionUrl;
            }
            Elog.e(TAG, "Method:get");
            Elog.e(TAG, requestUrl);
            final Request request = addHeaders().url(requestUrl).build();

            Call call = mOkHttpClient.newCall(request);
            call = onRequest(callBack, call);
            return call;
        } catch (Exception e) {
            Elog.e(TAG, e.toString());
            requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
        }
        return null;
    }

    /**
     * 请求数据
     *
     * @param callBack 请求返回数据回调
     * @param call     请求体
     * @param <T>
     * @return
     */
    private <T> Call onRequest(final ReqCallBack<T> callBack, Call call) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //服务器IP不对的时候、没有网络时返回
                failedCallBack(MyApplication.getInstance().getString(R.string.common_onfail), callBack);
                Elog.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Elog.e(TAG, response.code());
                    if (response.isSuccessful() || response.code() == RESPONCE_CODE_400 || response.code() == RESPONCE_CODE_500) {
                        String string = response.body().string();
                        Elog.e(TAG, "response ----->" + string);
//                        ResultBean result = new Gson().fromJson(string, ResultBean.class);
                        ResultBean result = JSON.parseObject(string, ResultBean.class);

                        if (null != result) {
                            switch (result.errorCode) {
                                case RESPONCE_NO_ERROR_CODE:
                                    if (callBack.type != null) {
                                        String data = result.data;
                                        if (null == data || data.equals("")) {
                                            successCallBack(null, callBack);
                                        } else {
                                            if (callBack.type == String.class || callBack.type == Integer.class || callBack.type == Double.class || callBack.type == Float.class) {
                                                successCallBack((T) data, callBack);
                                            } else {
                                                successCallBack((T) JSON.parseObject(data, callBack.type), callBack);
                                            }
                                        }
                                    } else {
                                        requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
                                    }
                                    break;
                                default:
                                    requestOnFailure(result.msg, result.errorCode, callBack);
                                    break;
                            }
                        } else {
                            failedCallBack(MyApplication.getInstance().getString(R.string.common_request_fail), callBack);
                        }
                    } else {
                        failedCallBack(MyApplication.getInstance().getString(R.string.common_request_fail), callBack);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    requestOnFailure(MyApplication.getInstance().getString(R.string.common_error_parse), callBack);
                }
            }
        });
        return call;
    }

    /**
     * 统一处理成功信息
     *
     * @param result
     * @param callBack
     * @param <T>
     */
    private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
        if (null != okHttpHandler) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onReqSuccess(result);
                    }
                }
            });
        } else {
            if (callBack != null) {
                callBack.onReqSuccess(result);
            }
        }

    }

    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
        if (null != okHttpHandler) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onFailure(errorMsg);
                    }
                }
            });
        } else {
            if (callBack != null) {
                callBack.onFailure(errorMsg);
            }
        }
    }

    private <T> void requestOnFailure(final String errorMsg, final ReqCallBack<T> callBack) {
        if (!TextUtils.isEmpty(errorMsg)) {
            this.requestOnFailure(errorMsg, RESPONCE_CODE_500, callBack);
        }
    }

    /**
     * 统一处理调用失败信息（接口返回的失败信息）
     *
     * @param errorMsg
     * @param code     错误码
     * @param callBack
     * @param <T>
     */
    private <T> void requestOnFailure(final String errorMsg, final int code, final ReqCallBack<T> callBack) {
        if (null != okHttpHandler) {
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        ErrorBean errorBean = new ErrorBean();
                        errorBean.setMsg(errorMsg);
                        errorBean.setCode(code + "");
                        callBack.onReqFailed(errorBean);
                    }
                }
            });
        } else {
            if (callBack != null) {
                ErrorBean errorBean = new ErrorBean();
                errorBean.setMsg(errorMsg);
                errorBean.setCode(code + "");
                callBack.onReqFailed(errorBean);
            }
        }

    }


    /**
     * 请求头信息
     */
    private final String HEADER_CONNECTION = "keep-alive";
    /**
     * 请求头信息
     */
    private final String HEADER_PLATFORM = "2";
    /**
     * 请求头信息
     */
    private final String HEADER_APPVERSION = "3.2.0";

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", HEADER_CONNECTION);
        return builder;
    }
}
