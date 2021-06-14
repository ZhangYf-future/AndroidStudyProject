package com.zyf.opencvapplication.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

/**
 * 从Uri中获取路径
 */
fun Uri.getPath(context: Context): String {
    val array = arrayOf<String>(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(this, array, null, null, null)
    cursor?.let {
        val index = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        it.moveToFirst()
        return cursor.getString(index)
    }
    return this.path ?: ""
}