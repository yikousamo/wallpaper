package com.dongyk.wallpaper.net

import com.dongyk.wallpaper.bean.CategoryResponse
import com.dongyk.wallpaper.bean.SpecifyCategoryResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 定义接口
 */
interface IApiService {

    @GET("cid")
    fun queryWallPagerAllCategory(): Deferred<CategoryResponse>

    @GET(".")
    fun queryWallPagerSpecifyCategory(
         @Query("cid") cid: Int,
         @Query("start") star: Int,
         @Query("count") count: Int
         ):Deferred<SpecifyCategoryResponse>
}