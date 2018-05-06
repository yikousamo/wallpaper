package com.dongyk.wallpaper.bus.viewserviceimpl

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.view.View
import com.dongyk.wallpaper.bus.viewservice.IBusPreViewService
import com.dongyk.wallpaper.page.PreviewActivity
import com.dongyk.wallpaper.widget.AnimatorEndListener
import java.math.BigDecimal


/**
 * @author dongyk
 * @date 2018/5/4
 * @discription 预览模块-UI具体实现
 */
class BusPreViewServiceImpl : IBusPreViewService {


    private val INIT_ANIM_TIME = 200L
    private val ENTER_ANIM_TIME = 500L
    private val EXIT_ANIM_TIME = 500L
    private val mInitAnim = initAnim()
    private val mEnterAnim = enterAnim()

    enum class AnimMode {
        ENTER, EXIT
    }


    override fun goPreviewActivity(context: Context, imgUrl: String, sourceImgRect: Rect) {
        PreviewActivity.start(context, imgUrl, sourceImgRect)
    }

    override fun doEnterAnim(view: View, sourceViewRect: Rect, destVieweRect: Rect, animationListener: Animator.AnimatorListener) {
        val xInitTransDistance = sourceViewRect.left - destVieweRect.left - view.translationX
        val yInitTransDistance = sourceViewRect.top - destVieweRect.top - view.translationY

        val sourceWidth = BigDecimal(sourceViewRect.right - sourceViewRect.left)
        val destWidth = BigDecimal(destVieweRect.right - destVieweRect.left)
        val sourceHeight = BigDecimal(sourceViewRect.bottom - sourceViewRect.top)
        val destHeight = BigDecimal(destVieweRect.bottom - destVieweRect.top)

        val xInitScale = sourceWidth.divide(destWidth, 6, BigDecimal.ROUND_HALF_DOWN).toFloat()
        val yInitScale = sourceHeight.divide(destHeight, 6, BigDecimal.ROUND_HALF_DOWN).toFloat()

        startInitAnim(view, xInitTransDistance, yInitTransDistance, xInitScale, yInitScale, INIT_ANIM_TIME, AnimMode.ENTER, animationListener)


    }


    private fun startEntertAnim(view: View, translationX: Float, translationY: Float, scaleX: Float, scaleY: Float, animatorListener: Animator.AnimatorListener) {
        // 平移
        mEnterAnim.xEnterTransAnim = ObjectAnimator.ofFloat(view, "translationX", view.translationX, translationX)
        mEnterAnim.yEnterTransAnim = ObjectAnimator.ofFloat(view, "translationY", view.translationY, translationY)
        // 缩放
        mEnterAnim.xEnterScaleAnim = ObjectAnimator.ofFloat(view, "scaleX", view.scaleX, scaleX)
        mEnterAnim.yEnterScaleAnim = ObjectAnimator.ofFloat(view, "scaleY", view.scaleY, scaleY)

        view.pivotX = 0F
        view.pivotY = 0F

        mEnterAnim.xEnterTransAnim?.duration = ENTER_ANIM_TIME
        mEnterAnim.yEnterTransAnim?.duration = ENTER_ANIM_TIME
        mEnterAnim.xEnterScaleAnim?.duration = ENTER_ANIM_TIME
        mEnterAnim.yEnterScaleAnim?.duration = ENTER_ANIM_TIME

        mEnterAnim.xEnterTransAnim?.start()
        mEnterAnim.yEnterTransAnim?.start()
        mEnterAnim.xEnterScaleAnim?.start()
        mEnterAnim.yEnterScaleAnim?.start()
        mEnterAnim.yEnterScaleAnim?.addListener(animatorListener)
    }

    override fun doExitrAnim(view: View, sourceViewRect: Rect, destVieweRect: Rect, animatorListener: Animator.AnimatorListener) {
        val xInitTransDistance = sourceViewRect.left - destVieweRect.left - view.translationX
        val yInitTransDistance = sourceViewRect.top - destVieweRect.top - view.translationY

        val sourceWidth = BigDecimal(sourceViewRect.right - sourceViewRect.left)
        val destWidth = BigDecimal(destVieweRect.right - destVieweRect.left)
        val sourceHeight = BigDecimal(sourceViewRect.bottom - sourceViewRect.top)
        val destHeight = BigDecimal(destVieweRect.bottom - destVieweRect.top)

        val xInitScale = sourceWidth.divide(destWidth, 6, BigDecimal.ROUND_HALF_DOWN).toFloat()
        val yInitScale = sourceHeight.divide(destHeight, 6, BigDecimal.ROUND_HALF_DOWN).toFloat()

        startInitAnim(view, xInitTransDistance, yInitTransDistance, xInitScale, yInitScale, EXIT_ANIM_TIME, AnimMode.EXIT, animatorListener)
    }

    private fun startInitAnim(view: View, translationX: Float, translationY: Float, scaleX: Float, scaleY: Float, duration: Long, animMode: AnimMode, animatorListener: Animator.AnimatorListener) {
        // 平移
        mInitAnim.xInitTransAnim = ObjectAnimator.ofFloat(view, "translationX", view.translationX, translationX)
        mInitAnim.yInitTransAnim = ObjectAnimator.ofFloat(view, "translationY", view.translationY, translationY)
        // 缩放
        mInitAnim.xInitScaleAnim = ObjectAnimator.ofFloat(view, "scaleX", view.scaleX, scaleX)
        mInitAnim.yInitScaleAnim = ObjectAnimator.ofFloat(view, "scaleY", view.scaleY, scaleY)

        view.pivotX = 0F
        view.pivotY = 0F

        mInitAnim.xInitTransAnim?.duration = duration
        mInitAnim.yInitTransAnim?.duration = duration
        mInitAnim.xInitScaleAnim?.duration = duration
        mInitAnim.yInitScaleAnim?.duration = duration

        mInitAnim.yInitScaleAnim?.addListener(object : AnimatorEndListener(){
            override fun onAnimationEnd(animation: Animator?) {
                if (animMode == AnimMode.ENTER) {
                    startEntertAnim(view, 0f, 0f, 1f, 1f, animatorListener)
                }else if (animMode == AnimMode.EXIT){
                    animatorListener.onAnimationEnd(animation)
                }
            }
        })

        mInitAnim.xInitTransAnim?.start()
        mInitAnim.yInitTransAnim?.start()
        mInitAnim.xInitScaleAnim?.start()
        mInitAnim.yInitScaleAnim?.start()
    }

    private fun initAnim() = object {
        // 将大图移动并缩放到小图位置
        var xInitTransAnim: ObjectAnimator? = null // x水平动画
        var yInitTransAnim: ObjectAnimator? = null // y水平动画
        var xInitScaleAnim: ObjectAnimator? = null // x缩小动画
        var yInitScaleAnim: ObjectAnimator? = null // Y缩小动画
    }

    private fun enterAnim() = object {
        // 将缩放后的图移动到正常位置
        var xEnterTransAnim: ObjectAnimator? = null // x水平动画
        var yEnterTransAnim: ObjectAnimator? = null // y水平动画
        var xEnterScaleAnim: ObjectAnimator? = null // x缩小动画
        var yEnterScaleAnim: ObjectAnimator? = null // Y缩小动画
    }
}