package com.beuno.beuno.shortcut

import java.util.*

/** 获取随机数, 区间[min, max] */
fun getRandomInt(min: Int = 0, max: Int = 100): Int {
    // Random函数是不包括最大值的, 当前方法是包括最大值的
    val differ = max - min + 1
    return Random().nextInt(differ) + min
}

/** 当前系统时间 */
fun getCurrentMillis() = System.currentTimeMillis()

/** 几天前的系统时间 */
fun getDaysAgoMillis(days: Int) = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * days