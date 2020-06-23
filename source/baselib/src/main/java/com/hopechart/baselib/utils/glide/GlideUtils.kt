package com.hopechart.baselib.utils.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import java.io.File

/**
 *@time 2020/4/26
 *@user 张一凡
 *@description 使用Glide处理图片的类
 *@introduction
 */
class GlideUtils {

    companion object {
        //通过本地File加载图片
        fun displayImageWithFile(
            context: Context,
            filePath: String,
            imageView: ImageView,
            defaultImage: Int
        ) {
            Glide.with(context)
                .asBitmap()
                .load(File(filePath))
                .apply(
                    RequestOptions.noAnimation()
                        .placeholder(defaultImage)
                        .error(defaultImage)
                )
                .into(imageView)
        }


        //通过本地文件加载图片，可以不指定默认图片
        fun displayImageWithFile(
            context: Context,
            filePath: String,
            imageView: ImageView
        ) {
            Glide.with(context)
                .asBitmap()
                .load(File(filePath))
                .apply(RequestOptions.noAnimation())
                .into(imageView)

        }

        //通过本地文件加载包含圆角的图片
        fun displayRoundImageWithFile(
            context: Context,
            filePath: String,
            imageView: ImageView,
            radius: Int,
            defaultImage: Int
        ) {
            Glide.with(context)
                .load(File(filePath))
                .apply(
                    RequestOptions.bitmapTransform(RoundedCorners(radius))
                        .placeholder(defaultImage)
                        .error(defaultImage)
                        .dontAnimate()
                        .autoClone()
                )
                .into(imageView)
        }


        //通过本地文件加载圆形图片
        fun displayCircleImageWithFile(
            context: Context,
            filePath: String,
            imageView: ImageView,
            defaultImage: Int
        ) {
            Glide.with(context)
                .asBitmap()
                .load(File(filePath))
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(defaultImage)
                        .error(defaultImage)
                )
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView)
        }

        //显示网络图片
        fun displayImageWithNet(
            context: Context,
            url: String,
            imageView: ImageView
        ) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(RequestOptions.noAnimation())
                .into(imageView)
        }

        //显示网络图片，指定加载中和出错时的图片
        fun displayImageWithNet(
            context: Context,
            url: String,
            imageView: ImageView,
            defaultImage: Int
        ) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(
                    RequestOptions.noAnimation()
                        .placeholder(defaultImage)
                        .error(defaultImage)
                )
                .into(imageView)
        }

        //加载网络圆角图片
        fun displayCircleImageWithNet(
            context: Context,
            url: String,
            imageView: ImageView,
            defaultImage: Int
        ) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(defaultImage)
                        .error(defaultImage)
                        .dontAnimate()
                        .autoClone()
                )
                .into(imageView)
        }

        //显示网络带边框的图片
        fun displayCircleBorderImageWithNet(
            context: Context,
            url: String,
            imageView: ImageView,
            border: Int,
            borderColor: Int,
            defaultImage: Int
        ) {
            Glide.with(context)
                .load(url)
                .apply(
                    RequestOptions()
                        .transform(GlideCircleBorderTransform(context, border, borderColor))
                        .placeholder(defaultImage)
                        .error(defaultImage)
                        .dontAnimate()
                        .autoClone()
                )
                .into(imageView)
        }

        //显示网络带圆角的图片
        fun displayRoundImageWithNet(
            context: Context,
            url: String,
            imageView: ImageView,
            radius: Int,
            defaultImage: Int
        ) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(
                    RequestOptions.bitmapTransform(RoundedCorners(radius))
                        .placeholder(defaultImage)
                        .error(defaultImage)
                        .dontAnimate()
                        .autoClone()
                )
                .into(imageView)
        }

        //显示资源文件带圆角的图片
        fun displayRoundImageWithResource(
            context: Context,
            resourceId: Int,
            imageView: ImageView,
            radius: Int,
            defaultImage: Int
        ) {
            Glide.with(context)
                .load(resourceId)
                .apply(
                    RequestOptions
                        .bitmapTransform(RoundedCorners(radius))
                        .placeholder(defaultImage)
                        .error(defaultImage)
                        .dontAnimate()
                        .autoClone()
                )
                .into(imageView)
        }

        //加载gif图片
        fun displayGif(
            context: Context,
            url: String,
            imageView: ImageView
        ) {
            Glide.with(context)
                .asGif()
                .load(url)
                .into(imageView)
        }
    }


}