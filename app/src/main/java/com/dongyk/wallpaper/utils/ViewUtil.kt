package com.dongyk.wallpaper.utils

import android.graphics.Rect
import android.view.View
import com.dongyk.wallpaper.base.ContextInstance



/**
 * @author dongyk
 * @date 2018/5/4
 * @discription
 */
object ViewUtil {

    /**
     * 获取View在屏幕上的坐标-注意调用时机
     */
    fun getViewLocationRect(view: View): Rect{
        val position = IntArray(2)
        view.getLocationOnScreen(position)
        val viewHeight = view.height
        val viewWidth = view.width
        return Rect(position[0], position[1], position[0]+viewWidth, position[1] + viewHeight)
    }

    fun getScreenWidth(): Int{
        return ContextInstance.getContext().resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int{
        return ContextInstance.getContext().resources.displayMetrics.heightPixels
    }
}