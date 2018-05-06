package com.dongyk.wallpaper.base

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */
data class Response<T>(
        var code: String = Constans.RESPONSE_FAILURE_CODE,
        var msg: String = Constans.RESPONSE_DEFAULT_MSG,
        var data: T?
)