package com.hopechart.baselib.net

/**
 *@time 2020/4/28
 *@user 张一凡
 *@description
 *@introduction
 */
abstract class BaseRepository(
    val onNetComplete: () -> Unit,
    val onNetError: (String) -> Unit
) {
}