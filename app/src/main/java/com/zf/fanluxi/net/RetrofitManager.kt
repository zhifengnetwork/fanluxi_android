package com.zf.fanluxi.net

import android.content.Intent
import com.zf.fanluxi.MyApplication
import com.zf.fanluxi.api.ApiService
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.ui.activity.LoginActivity
import com.zf.fanluxi.utils.NetworkUtil
import com.zf.fanluxi.utils.Preference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    val service: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java) }

    private var token: String by Preference(UriConstant.TOKEN, "")

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    //添加一个log拦截器,打印所有的log
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    //可以设置请求过滤的水平,body,basic,headers
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    //设置 请求的缓存的大小跟位置
                    val cacheFile = File(MyApplication.context.cacheDir, "cache")
                    val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

                    client = OkHttpClient.Builder()
                        .addInterceptor(addQueryParameterInterceptor())  //参数添加
                        .addInterceptor(TokenFreshInterceptor()) // token过滤
//                        .addInterceptor(addCacheInterceptor()) //缓存
//                        .addNetworkInterceptor(addCacheInterceptor()) //缓存
                        .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                        .cache(cache)  //添加缓存
                        .connectTimeout(10L, TimeUnit.SECONDS)
                        .readTimeout(10L, TimeUnit.SECONDS)
                        .writeTimeout(10L, TimeUnit.SECONDS)
                        .build()

                    // 获取retrofit的实例
                    retrofit = Retrofit.Builder()
                        .baseUrl(UriConstant.BASE_URL)  //自己配置
                        .client(client)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        //默认的gSon解析
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }
        return retrofit
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                //添加自定义参数
                .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 处理头部信息
     */
    class TokenFreshInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val originalRequest = chain.request()
            val request = chain.request().newBuilder()
                .header("Token", token)
                .method(originalRequest.method(), originalRequest.body())
            val response = chain.proceed(request.build())

            if (response.code() == 401) {
                //过期，去登陆
                Preference.clearPreference(UriConstant.TOKEN)
                val intent = Intent(MyApplication.context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                MyApplication.context.startActivity(intent)
            }
            return response

        }
    }


    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
            } else {
                // 无网络时，设置超时为1周(7天)  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 7
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("nyn")
                    .build()
            }
            response
        }
    }

}