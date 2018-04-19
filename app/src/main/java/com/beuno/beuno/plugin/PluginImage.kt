package com.beuno.beuno.plugin

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.annotation.DrawableRes
import com.beuno.beuno.shortcut.getRandomInt

/**
 * 图像相关
 */
object PluginImage {

    /** 获取图片资源 */
    fun getBitmap(res: Resources, @DrawableRes resId: Int): Bitmap {
        return BitmapFactory.decodeResource(res, resId)
    }

    /** 随机生成彩色背景, 颜色较淡 */
    fun genColorfulBg(): Int {
        fun rdm() = getRandomInt(200, 255)
        return Color.rgb(rdm(), rdm(), rdm())
    }

}