package com.zyf.opencvapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.hopechart.baselib.ui.BaseActivity
import com.zyf.opencvapplication.databinding.ActivityCompareImageBinding
import com.zyf.opencvapplication.utils.getPath
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import kotlin.math.absoluteValue

/**
 * 比较两张图片相似度的页面
 */
class CompareImageActivity : BaseActivity<ActivityCompareImageBinding>() {

    //常量
    companion object {
        //打开相册获取第一张图片的requestCode
        private const val REQUEST_FIRST_IMAGE_CODE = 2000

        //打开相册获取第二张图片的requestCode
        private const val REQUEST_SECOND_IMAGE_CODE = 2001
    }

    //第一张图片的路径
    private var mFirstImagePath: String? = null

    //第二章图片的路径
    private var mSecondImagePath: String? = null

    //创建图片的options
    private val mImageOptions by lazy {
        BitmapFactory.Options().apply {
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_compare_image

    override fun initUI() {
        super.initUI()
        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "opencv loader success")
        }
    }


    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.iv_first_image ->
                //点击第一张图片
                openAlbum(REQUEST_FIRST_IMAGE_CODE)

            R.id.iv_second_image ->
                //点击第二张图片
                openAlbum(REQUEST_SECOND_IMAGE_CODE)

            R.id.btn_compare_image ->
                //点击比较按钮
                compareImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_FIRST_IMAGE_CODE -> {
                    if (data != null && data.data != null) {
                        val uri = data.data
                        mFirstImagePath = uri!!.getPath(this)
                        val bitmap = BitmapFactory.decodeFile(mFirstImagePath)
                        mBinding.ivFirstImage.setImageBitmap(bitmap)
                    }
                }

                REQUEST_SECOND_IMAGE_CODE -> {
                    if (data != null && data.data != null) {
                        val uri = data.data
                        mSecondImagePath = uri!!.getPath(this)
                        val bitmap = BitmapFactory.decodeFile(mSecondImagePath)
                        mBinding.ivSecondImage.setImageBitmap(bitmap)
                    }
                }
            }
            mBinding.btnCompareImage.isEnabled =
                !TextUtils.isEmpty(mFirstImagePath) && !TextUtils.isEmpty(mSecondImagePath)
        }
    }

    //打开相册获取图片
    private fun openAlbum(code: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, code)
    }

    //比较两张图片
    private fun compareImage() {
        //通过图片获取mat
        val firstMat = Imgcodecs.imread(mFirstImagePath)
        val secondMat = Imgcodecs.imread(mSecondImagePath)
        //根据第一张图片创建尺寸信息
        val size = Size(firstMat.width().toDouble(), firstMat.height().toDouble())
        //设置尺寸一致
        Imgproc.resize(secondMat, secondMat, size)

        firstMat.convertTo(firstMat, CvType.CV_32F)
        secondMat.convertTo(secondMat, CvType.CV_32F)
        val result = Imgproc.compareHist(firstMat, secondMat, Imgproc.CV_COMP_BHATTACHARYYA).absoluteValue
        Log.i(TAG, "图片比较结果:$result")
        mBinding.tvCompareResult.text = "两张图片的识别结果:$result"
    }
}