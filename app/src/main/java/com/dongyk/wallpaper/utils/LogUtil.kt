package com.dongyk.wallpaper.utils

import com.dongyk.wallpaper.BuildConfig
import com.dongyk.wallpaper.R
import com.dongyk.wallpaper.base.ContextInstance
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 日志打印类
 */
object LogUtil {

    val loggable = BuildConfig.DEBUG

    init{
        val logger = PrettyFormatStrategy.newBuilder().let {
            it.tag(ContextInstance.getContext()?.resources?.getString(R.string.app_name))
            it.build()
        }

        Logger.addLogAdapter(object : AndroidLogAdapter(logger){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return loggable
            }
        })
    }

    fun d(content: Any?){
        Logger.d(content)
    }

    fun d(content: String?){
        Logger.d(content)
    }

    fun json(json: String?){
        Logger.json(json)
    }



}