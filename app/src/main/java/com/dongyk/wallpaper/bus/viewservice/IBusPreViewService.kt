package com.dongyk.wallpaper.bus.viewservice

import android.animation.Animator
import android.content.Context
import android.graphics.Rect
import android.view.View
import com.dongyk.wallpaper.bus.base.IBusService

/**
 * @author dongyk
 * @date 2018/5/4
 * @discription 预览模块-UI相关
 */
interface IBusPreViewService: IBusService{

    /**
     * 打开预览页
     */
    fun goPreviewActivity(context: Context, imgUrl: String, sourceImgRect: Rect)

    /**
     * 进入动画
     */
    fun doEnterAnim(view: View, sourceViewRect: Rect, destVieweRect: Rect, animationListener: Animator.AnimatorListener)

    /**
     * 结束动画
     */
    fun doExitrAnim(view: View, sourceViewRect: Rect, destVieweRect: Rect, animationListener: Animator.AnimatorListener)
}