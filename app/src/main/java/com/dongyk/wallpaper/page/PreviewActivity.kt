package com.dongyk.wallpaper.page

import android.animation.Animator
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.dongyk.wallpaper.R
import com.dongyk.wallpaper.base.BaseActivity
import com.dongyk.wallpaper.bus.manager.BusServiceFactory
import com.dongyk.wallpaper.bus.viewservice.IBusPreViewService
import com.dongyk.wallpaper.utils.ViewUtil
import com.dongyk.wallpaper.widget.AnimatorEndListener
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 预览页
 */
class PreviewActivity : BaseActivity() {


    companion object {
        private val IMG_RECR: String = "img_rect"
        private val IMG_URL: String = "img_url"

        fun start(context: Context, imgUrl: String, imgRect: Rect){
            val intent = Intent(context, PreviewActivity::class.java)
            intent.putExtra(IMG_RECR, imgRect)
            intent.putExtra(IMG_URL, imgUrl)
            context.startActivity(intent)
        }
    }

    private lateinit var mSourceImgRect: Rect
    private lateinit var mSourceImgUrl: String
    private lateinit var mDestImgRect: Rect

    private val busPreViewService = BusServiceFactory.getInstance().getBusService(IBusPreViewService::class.java)
    private lateinit var mWallPaperBitmap: Bitmap


    override fun getLayoutResId(): Int {
        return R.layout.activity_preview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preImg.isEnabled = false
        showSettingBtn.isEnabled = false
        dealImgRectInfo()
        preImg.post {
            Glide.with(this).load(mSourceImgUrl).dontAnimate().into(object : GlideDrawableImageViewTarget(preImg){
                override fun onResourceReady(resource: GlideDrawable?, animation: GlideAnimation<in GlideDrawable>?) {
                    super.onResourceReady(resource, animation)
                    dealImgRectInfo()
                    busPreViewService?.doEnterAnim(preImg, mSourceImgRect, mDestImgRect,  object: AnimatorEndListener() {
                        override fun onAnimationEnd(animation: Animator?) {
                            preImg.isEnabled = true
                            showSettingBtn.isEnabled = true
                            showSettingBtn.visibility = View.VISIBLE
                        }
                    })

                    preImg.postDelayed({
                        preImg.visibility = View.VISIBLE
                    },200)
                }

                override fun onLoadFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                    super.onLoadFailed(e, errorDrawable)
                    Toast.makeText(this@PreviewActivity, "加载大图失败", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
        }

        preImg.setOnClickListener {
            preImg.isEnabled = false
            showSettingBtn.visibility = View.GONE
            dealImgRectInfo()
            busPreViewService?.doExitrAnim(preImg, mSourceImgRect, mDestImgRect,object: AnimatorEndListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    preImg.visibility = View.GONE


                    finish()
                }
            })
        }

        showSettingBtn.setOnClickListener {
            try {
                val wallpaperManager = WallpaperManager.getInstance(this@PreviewActivity)
                launch(CommonPool){
                    val bitmap = getBitMap()
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(this@PreviewActivity, "设置壁纸成功", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@PreviewActivity, "设置壁纸失败", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private suspend fun getBitMap(): Bitmap{
        val bitmap =  Glide.with(this@PreviewActivity)
                .load(mSourceImgUrl)
                .asBitmap()
                .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .get()

        return bitmap
    }

    private fun dealImgRectInfo(){
        mSourceImgRect = intent.getParcelableExtra(IMG_RECR)
        mSourceImgUrl = intent.getStringExtra(IMG_URL)
        mDestImgRect = ViewUtil.getViewLocationRect(preImg)
    }
}