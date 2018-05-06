package com.dongyk.wallpaper.bean

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */


data class CategoryResponse(
        var error: String,
        var count: String?,
        var star: String?,
        var data: List<WallPagerCategory>?
){
    data class WallPagerCategory(
            var cat: String,
            var cid: Int
    )
}


