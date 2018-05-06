package com.dongyk.wallpaper.widget

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dongyk.wallpaper.base.ContextInstance
import java.util.*

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription 图片加载包装类
 */
class ImageLoader<ModelType: Any> private constructor(builder: Builder){

    private var sContext: Context? = ContextInstance.getContext()


    companion object {
        fun loadImage(imageView: ImageView, url: String) {
            loadImage(imageView, url, 0)
        }


        @SafeVarargs
        fun loadImage(imageView: ImageView, url: String, defaultImg: Int,
                      vararg bitmapTransformations: Transformation<Bitmap>) {
            loadImage(imageView, url, defaultImg, defaultImg, *bitmapTransformations)
        }

        @SafeVarargs
        fun loadImage(imageView: ImageView, url: String,
                      placeHolderDrawableId: Int,
                      errorDrawableId: Int,
                      vararg bitmapTransformations: Transformation<Bitmap>) {
            val builder = Builder().setUrl(url)
            setSimpleBuilder(builder, imageView, placeHolderDrawableId, errorDrawableId, *bitmapTransformations)
            builder.build()
        }

        fun loadImage(imageView: ImageView, resourceId: Int) {
            loadImage(imageView, resourceId, 0, 0)
        }

        @SafeVarargs
        fun loadImage(imageView: ImageView, resourceId: Int,
                      placeHolderDrawableId: Int,
                      errorDrawableId: Int,
                      vararg bitmapTransformations: Transformation<Bitmap>) {
            val builder = Builder().setResourceId(resourceId)
            setSimpleBuilder(builder, imageView, placeHolderDrawableId, errorDrawableId, *bitmapTransformations)

            builder.build()
        }

        private fun setSimpleBuilder(builder: Builder, imageView: ImageView,
                                     placeHolderDrawableId: Int,
                                     errorDrawableId: Int,
                                     vararg bitmapTransformations: Transformation<Bitmap>): Builder {
            builder.setImageView(imageView)

            if (placeHolderDrawableId != 0) {
                builder.setPlaceHolder(placeHolderDrawableId)
            }
            if (errorDrawableId != 0) {
                builder.setError(errorDrawableId)
            }

            if (placeHolderDrawableId != 0 && errorDrawableId != 0) {
                builder.setDontAnimate(true)
            }

            if (bitmapTransformations != null && bitmapTransformations.size != 0) {
                builder.setBitmapTransform(*bitmapTransformations)
            }
            return builder
        }


    }


    init {
        var glideBuilder: DrawableRequestBuilder<ModelType>? = null
        var dataSource: ModelType
        if (builder.url != null) {
            dataSource = builder.url as ModelType
        } else if (builder.resourceId != null && builder.resourceId != 0) {
            dataSource = builder.resourceId as ModelType
        } else {
            dataSource = "" as ModelType;
            throw UnsupportedOperationException("暂时只支持url及drawableId")
        }
        // 获取glideBuilder对象
        glideBuilder = create(sContext, dataSource, builder)
        // 设置各种属性
        setAllAttribute(glideBuilder, builder)
        // 加载图片
        build(glideBuilder, builder.imageView)

    }

    private fun setAllAttribute(glideBuilder: DrawableRequestBuilder<ModelType>?, builder: Builder) {

        if (builder.placeHolderDrawableId != 0) {
            glideBuilder!!.placeholder(builder.placeHolderDrawableId)

        }

        if (builder.dontAnimate) {
            glideBuilder!!.dontAnimate()
        }

        if (builder.fitCenter) {
            glideBuilder!!.fitCenter()
        }


        if (builder.errorDrawableId != 0) {
            glideBuilder!!.error(builder.errorDrawableId)
        }

        if (builder.bitmapTransformations != null && builder.bitmapTransformations!!.size > 0) {
            glideBuilder!!.bitmapTransform(*builder.bitmapTransformations!!.toTypedArray())
        }
    }


    class Builder {

        var url: String? = null
        var resourceId: Int? = null
        var imageView: ImageView? = null
        // 占位图
        var placeHolderDrawableId: Int = 0
        // 加载失败图
        var errorDrawableId: Int = 0
        var bitmapTransformations: List<Transformation<Bitmap>>? = null
        var dontAnimate: Boolean = false
        var fitCenter: Boolean = false

        fun setUrl(url: String): Builder {
            var url = url
            if (TextUtils.isEmpty(url)) {
                url = ""
            }
            this.url = url
            return this
        }

        fun setResourceId(resourceId: Int): Builder {
            this.resourceId = resourceId
            return this
        }

        fun setImageView(imageView: ImageView): Builder {
            this.imageView = imageView
            return this
        }

        fun setPlaceHolder(placeHolderDrawableId: Int): Builder {
            this.placeHolderDrawableId = placeHolderDrawableId
            return this
        }

        fun setDontAnimate(dontAnimate: Boolean): Builder {
            this.dontAnimate = dontAnimate
            return this
        }

        fun setFitCenter(fitCenter: Boolean): Builder {
            this.fitCenter = fitCenter
            return this
        }

        fun setError(errorDrawableId: Int): Builder {
            this.errorDrawableId = errorDrawableId
            return this
        }

        fun setBitmapTransform(vararg bitmapTransformations: Transformation<Bitmap>): Builder {
            if (this.bitmapTransformations == null) {
                this.bitmapTransformations = ArrayList()
            }
            this.bitmapTransformations = Arrays.asList(*bitmapTransformations)
            return this
        }

        fun build(): ImageLoader<*> {
            return if (url != null) {
                ImageLoader<String>(this)
            } else if (resourceId != null && resourceId != 0) {
                ImageLoader<Int>(this)
            } else {
                throw UnsupportedOperationException("暂时只支持url及drawableId")
            }
        }


    }

    private fun build(builder: DrawableRequestBuilder<ModelType>?, imageView: ImageView?) {
        builder!!.into(imageView!!)
    }


    /**
     * 个性化定制glide
     * @param context
     * @param dataSource 图片url或者resId
     * @return
     */
    fun create(context: Context?, dataSource: ModelType, builder: Builder): DrawableRequestBuilder<ModelType> {
        return Glide.with(context)
                .load(dataSource)
                .listener(object : RequestListener<ModelType, GlideDrawable> {
                    override fun onException(e: Exception, model: ModelType, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {

                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: ModelType, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
    }


}