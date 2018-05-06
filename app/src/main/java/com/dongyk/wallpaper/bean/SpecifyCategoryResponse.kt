package com.dongyk.wallpaper.bean

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */

data class SpecifyCategoryResponse(
        var errno: String,
        var errmsg: String?,
        var consume: String?,
        var total: String?,
        var data: List<SpecifyCategoryPic>?
){

    data class SpecifyCategoryPic (
            var id: String?,
            var class_id: String?,
            var resolution: String?,
            var url_mobile: String?,
            var url: String?,
            var url_thumb: String?,
            var url_mid: String?,
            var download_times: String?,
            var imgcut: String?,
            var tag: String?,
            var create_time: String?,
            var update_time: String?,
            var utag: String?,
            var tempdata: String?,
            var img_1600_900: String?,
            var img_1440_900: String?,
            var img_1366_768: String?,
            var img_1280_800: String?,
            var img_1280_1024: String?,
            var img_1024_768: String?
    )
}

