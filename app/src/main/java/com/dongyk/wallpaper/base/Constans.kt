package com.dongyk.wallpaper.base

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 常量
 */
object Constans {

    val BASE_URL: String = "https://api.lylares.com/images/"
    var NET_CONNECT_TIMEOUT: Long = 10L // 单位s
    var NET_READ_TIMEOUT: Long = 10L
    var NET_WRITE_TIMEOUT: Long = 10L
    var RESPONSE_NULL: String = "请检查网络状况是否良好"
    var RESPONSE_SUCCESS_CODE: String = "0"
    var RESPONSE_FAILURE_CODE: String = "-1"
    var RESPONSE_DEFAULT_MSG: String = "网络请求失败，请检查网络状况"

}