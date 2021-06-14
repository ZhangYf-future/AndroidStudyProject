package com.zyf.opencvapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.BitmapCompat
import com.hopechart.baselib.ui.BaseActivity
import com.zyf.opencvapplication.databinding.ActivityMainBinding
import com.zyf.opencvapplication.utils.getPath
import org.opencv.android.InstallCallbackInterface
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.FileDescriptor
import java.sql.Struct

class MainActivity : BaseActivity<ActivityMainBinding>() {
    //跳转到相册页面的requestCode
    private val TO_ALBUM_CODE = 2000

    //请求读取文件的requestCode
    private val REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE = 3000
    //val mat = Mat()

    //从相册返回的图片信息
    private var mLocalImageBitmap: Bitmap? = null

    //从相册中返回的图片路径
    private var mLocalImagePath: String? = null

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initUI() {
        super.initUI()
        //初始化openCVSDK
//        OpenCVLoader.initAsync("4.5.2", this, object : LoaderCallbackInterface {
//            override fun onManagerConnected(status: Int) {
//                Log.i(TAG, "onManagerConnected status is $status")
//            }
//
//            override fun onPackageInstall(operation: Int, callback: InstallCallbackInterface?) {
//                Log.i(TAG, "onPackageInstall $operation,callback is $callback")
//            }
//
//        })
        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "openCV loader success")
        }
    }

    //点击事件
    override fun doClick(view: View) {
        super.doClick(view)
        when (view.id) {
            R.id.btn_open_album ->
                //点击打开相册按钮
                checkPermission()

            R.id.btn_to_gray ->
                //打开转换为灰度图片按钮
                toGrayImage()

            R.id.btn_to_image_compare -> {
                //点击图片比较按钮，跳转到图片比较页面
                val intent = Intent(this, CompareImageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE -> checkPermission()
                TO_ALBUM_CODE -> {
                    if (data != null) {
                        val uri = data.data
                        Log.i(TAG, "请求到的图片的uri为:$uri")
                        //mLocalImageBitmap = BitmapFactory
                        mLocalImagePath = uri!!.getPath(this)
                        Log.i(TAG, "image path is $mLocalImagePath")
                        mLocalImageBitmap = BitmapFactory.decodeFile(mLocalImagePath)
                        mBinding.ivImage.setImageBitmap(mLocalImageBitmap)
                        mBinding.btnToGray.isEnabled = mLocalImageBitmap != null
                    }
                }
            }
        }
    }

    //判断是否拥有权限
    private fun checkPermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本大于等于6.0，动态申请权限
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //授予权限
                openAlbum()
            } else {
                val permissionArray = arrayOf(permission)
                //请求权限
                requestPermissions(permissionArray, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_CODE)
            }
        } else {
            //版本小于6.0，不需要动态申请权限，直接打开相册
            openAlbum()
        }

    }

    //打开相册
    private fun openAlbum() {
        Log.i(TAG, "打开相册")
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, TO_ALBUM_CODE)
    }

    //将获取到的图片转化为灰度图片
    private fun toGrayImage() {
        val rgbMat = Imgcodecs.imread(mLocalImagePath, Imgcodecs.IMREAD_GRAYSCALE)
        val grayMat = Mat()
        Utils.bitmapToMat(mLocalImageBitmap, rgbMat)
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2BGR)
        val grayBitmap = Bitmap.createBitmap(
            mLocalImageBitmap!!.width,
            mLocalImageBitmap!!.height,
            Bitmap.Config.RGB_565
        )
        Utils.matToBitmap(rgbMat, grayBitmap)
        mBinding.ivImage.setImageBitmap(grayBitmap)

        val result = Imgproc.compareHist(rgbMat, grayMat, Imgproc.CV_COMP_BHATTACHARYYA)
        Log.i(TAG, "图片对比结果:$result")
    }
}