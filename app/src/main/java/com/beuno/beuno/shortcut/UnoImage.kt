package com.beuno.beuno.shortcut

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.Gravity

/** 将图片剪裁为正方形 */
fun Bitmap.toSquare(): Bitmap {
    //边框宽度 pixel
    val borderWidthHalf = 20

    //转换为正方形后的宽高
    val bitmapSquareWidth = Math.min(width, height)

    //最终图像的宽高
    val newBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf

    val roundedBitmap = Bitmap.createBitmap(
            newBitmapSquareWidth, newBitmapSquareWidth, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(roundedBitmap)
    val x = borderWidthHalf + bitmapSquareWidth - width
    val y = borderWidthHalf + bitmapSquareWidth - height

    //裁剪后图像,注意X,Y要除以2 来进行一个中心裁剪
    canvas.drawBitmap(this, x.toFloat() / 2, y.toFloat() / 2, null)
    val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = borderWidthHalf.toFloat()
        color = Color.WHITE
    }

    //添加边框
    canvas.drawCircle(canvas.width.toFloat() / 2, canvas.height.toFloat() / 2,
            newBitmapSquareWidth.toFloat() / 2, borderPaint)

    return roundedBitmap
}

/** 将图片剪裁为圆形 */
fun Bitmap.toCircleDrawable(res: Resources): Drawable {
    return RoundedBitmapDrawableFactory.create(res, this)
            .apply {
                isCircular = true
                gravity = Gravity.CENTER
            }
}