package com.dongyk.wallpaper.widget

import android.animation.Animator
import com.dongyk.wallpaper.utils.LogUtil

/**
 * @author dongyk
 * @date 2018/5/4
 * @discription
 */
 open class AnimatorEndListener : Animator.AnimatorListener {
    override fun onAnimationEnd(animation: Animator?) {
        LogUtil.d("onAnimationStart")
    }

    override fun onAnimationRepeat(animation: Animator?) {
        LogUtil.d("onAnimationStart")
    }

    override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
        super.onAnimationEnd(animation, isReverse)
    }

    override fun onAnimationCancel(animation: Animator?) {
        LogUtil.d("onAnimationStart")
    }

    override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
        super.onAnimationStart(animation, isReverse)
    }

    override fun onAnimationStart(animation: Animator?) {
        LogUtil.d("onAnimationStart")
    }
}