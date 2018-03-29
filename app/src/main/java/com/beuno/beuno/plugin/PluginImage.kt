package com.beuno.beuno.plugin

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.DrawableRes

/**
 * 图像相关
 */
object PluginImage {

    /** 获取图片资源 */
    fun getBitmap(res: Resources, @DrawableRes resId: Int): Bitmap {
        return BitmapFactory.decodeResource(res, resId)
    }
}