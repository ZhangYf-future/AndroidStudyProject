package com.hopechart.baselib.utils

import com.github.promeg.pinyinhelper.Pinyin
import com.github.promeg.pinyinhelper.PinyinMapDict

/**
 *@time 2020/5/27
 *@user 张一凡
 *@description 拼音相关的工具类
 *@introduction
 */
class PinYinUtils {

    companion object{
        @JvmStatic
        fun isChinese(c: Char): Boolean = Pinyin.isChinese(c)

        @JvmStatic
        fun toPinYin(c: Char): String = Pinyin.toPinyin(c)

        @JvmStatic
        fun toPinYin(hanzi: String,subStr: String): String = Pinyin.toPinyin(hanzi,subStr)

        //设置拼音字典，进行自定义转化
        @JvmStatic
        fun setPinyinDict(hanzi: String,pinyin: String){
            Pinyin.init(Pinyin.newConfig()
                .with(object : PinyinMapDict() {
                    override fun mapping(): MutableMap<String, Array<String>> {
                        val map = mutableMapOf<String,Array<String>>()
                        map[hanzi] = arrayOf(pinyin)
                        return map
                    }

                })
            )
        }
    }
}