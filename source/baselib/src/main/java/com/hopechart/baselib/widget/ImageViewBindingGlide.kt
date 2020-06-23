package com.hopechart.baselib.widget

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.hopechart.baselib.R
import com.hopechart.baselib.utils.DensityUtils
import com.hopechart.baselib.utils.glide.GlideUtils

/**
 *@time 2020/4/26
 *@user 张一凡
 *@description DataBinding中使用加载图片
 *@introduction
 */
class ImageViewBindingGlide {

    companion object {

        //加载网络图片,圆角为4
        @JvmStatic
        @BindingAdapter("ivPath")
        fun imageLoad(imageView: ImageView, path: String?) {
            if (path == null) {
                imageView.setImageResource(R.drawable.baselib_default_pic)
                imageView.setTag(R.id.tag_data, null)
                return
            } else {
                if (imageView.getTag(R.id.tag_data) == null || imageView.getTag(R.id.tag_data) != path) {
                    GlideUtils.displayRoundImageWithNet(
                        imageView.context,
                        path,
                        imageView,
                        4,
                        R.drawable.baselib_default_pic
                        )

                }
            }
        }

        //加载网络圆形图片
        @JvmStatic
        @BindingAdapter("civPath","defaultImage",requireAll = false)
        fun imageCircleLoad(imageView: ImageView, path: String?,defaultImage: Int?) {
            if (path == null) {
                imageView.setImageResource(defaultImage?:R.drawable.baselib_default_pic)
                imageView.setTag(R.id.tag_data, null)
                return
            } else {
                if (imageView.getTag(R.id.tag_data) == null || imageView.getTag(R.id.tag_data) != path) {
                    GlideUtils.displayCircleImageWithNet(
                        imageView.context,
                        path,
                        imageView,
                        defaultImage?:R.drawable.baselib_default_pic
                    )

                }
            }
        }


        //加载网络图片，不做任何处理
        @JvmStatic
        @BindingAdapter("ivPthN")
        fun imageLoadNormal(imageView: ImageView, path: String?){
            if (path == null) {
                imageView.setImageResource(R.drawable.baselib_default_pic)
                imageView.setTag(R.id.tag_data, null)
                return
            } else {
                if (imageView.getTag(R.id.tag_data) == null || imageView.getTag(R.id.tag_data) != path) {
                    GlideUtils.displayImageWithNet(
                        imageView.context,
                        path,
                        imageView,
                        R.drawable.baselib_default_pic
                    )
                }
            }
        }


        //加载网络gif图片
        @JvmStatic
        @BindingAdapter("gifPathN")
        fun imageGifLoadNormal(imageView: ImageView, path: String?){
            if (path == null) {
                imageView.setImageResource(R.drawable.baselib_default_pic)
                imageView.setTag(R.id.tag_data, null)
                return
            } else {
                if (imageView.getTag(R.id.tag_data) == null || imageView.getTag(R.id.tag_data) != path) {
                    GlideUtils.displayGif(
                        imageView.context,
                        path,
                        imageView
                    )
                }
            }
        }

        //加载网络圆形带边框的图片
        @JvmStatic
        @BindingAdapter("civBorderPath","colorInt","borderWidth",requireAll = true)
        fun imageCircleBorderLoad(imageView: ImageView,path: String?,colorInt: Int,borderWidth: Int){
            if (path == null) {
                imageView.setImageResource(R.drawable.baselib_default_pic)
                imageView.setTag(R.id.tag_data, null)
                return
            } else {
                if (imageView.getTag(R.id.tag_data) == null || imageView.getTag(R.id.tag_data) != path) {
                    GlideUtils.displayCircleBorderImageWithNet(
                        imageView.context,
                        path,
                        imageView,
                        DensityUtils.dp2px(borderWidth.toFloat()),
                        colorInt,
                        R.drawable.baselib_default_pic
                    )
                }
            }
        }

        //加载本地资源文件
        @JvmStatic
        @BindingAdapter("setImageSrc")
        fun setImageSrc(view: ImageView, imageRes: Int){
            view.setImageResource(imageRes)
        }
    }

}