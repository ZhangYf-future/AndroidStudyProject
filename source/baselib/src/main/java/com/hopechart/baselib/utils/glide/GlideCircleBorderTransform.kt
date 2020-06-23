package com.hopechart.baselib.utils.glide

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 *@time 2020/4/26
 *@user 张一凡
 *@description
 *@introduction
 */
class GlideCircleBorderTransform(context: Context, borderWidth: Int, borderColor: Int) :
    BitmapTransformation() {

    private val mBorderPaint: Paint
    private val mBorderWidth: Float = borderWidth.toFloat()

    init {
        this.mBorderPaint = Paint().also {
            it.isDither = true
            it.isAntiAlias = true
            it.color = borderColor
            it.style = Paint.Style.STROKE
            it.strokeWidth = mBorderWidth
        }
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val size: Int = toTransform.width.coerceAtMost(toTransform.height)
        val x: Int = (toTransform.width - size) / 2
        val y: Int = (toTransform.height - size) / 2

        val squared: Bitmap = Bitmap.createBitmap(toTransform, x, y, size, size)
        val result: Bitmap = pool.get(size, size, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(result)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val r: Float = size / 2f;
        canvas.drawCircle(r, r, r, paint)
        val borderRadius: Float = r - mBorderWidth / 2;
        canvas.drawCircle(r, r, borderRadius, mBorderPaint)
        return result

    }
}