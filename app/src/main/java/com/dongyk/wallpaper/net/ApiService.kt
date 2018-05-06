package com.dongyk.wallpaper.net

import com.dongyk.wallpaper.base.Constans
import com.dongyk.wallpaper.bean.CategoryResponse
import com.dongyk.wallpaper.bean.SpecifyCategoryResponse
import com.dongyk.wallpaper.utils.LogUtil
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 网络服务
 */
object ApiService{

    private var mRetrofit: Retrofit
    private var mOkHttpClient: OkHttpClient
    private var mIApiService: IApiService

    init {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            try {
                // 尝试将String转换成json todo 不优雅
                JSONObject(message)
                LogUtil.json(message)
            }catch (e: JSONException) {
                LogUtil.d(message)
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

        mOkHttpClient = with(OkHttpClient.Builder()){
            connectTimeout(Constans.NET_CONNECT_TIMEOUT,TimeUnit.SECONDS)
            readTimeout(Constans.NET_READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constans.NET_WRITE_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
            build()
        }

        mRetrofit = Retrofit.Builder().apply {
            baseUrl(Constans.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            client(mOkHttpClient)
        }.build()

        mIApiService = mRetrofit.create(IApiService::class.java)
    }

    fun queryWallPagerAllCategory(): Deferred<CategoryResponse>{
        return mIApiService.queryWallPagerAllCategory()
    }

    fun queryWallPagerSpecifyCategory(cid: Int, start: Int, count: Int): Deferred<SpecifyCategoryResponse>{
        return mIApiService.queryWallPagerSpecifyCategory(cid, start, count)
    }

}