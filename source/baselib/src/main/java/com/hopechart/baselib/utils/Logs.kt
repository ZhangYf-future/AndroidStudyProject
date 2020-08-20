package com.hopechart.baselib.utils

import android.util.Log
import com.hopechart.baselib.BaseApplication

class Logs {
    companion object {
        @JvmStatic
        fun e(tag: String, content: String) {
            Log.e(tag, content)
        }

        @JvmStatic
        fun e(content: String) {
            e(BaseApplication.getInstance().packageName, content)
        }
    }
}