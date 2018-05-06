package com.dongyk.wallpaper.base

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */
object ContextInstance {



    private lateinit var sInstance: Context


    fun getContext(): Context{
        return sInstance
    }

    fun setContext(context: Context){
        sInstance = context
    }


}