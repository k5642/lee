package com.beuno.beuno.shortcut

import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView

/** 设为粗体 */
fun TextView.bold(): TextView {
    paint.isFakeBoldText = true
    return this
}

/**
 * 获取View的宽高
 */
fun View.getSize(): Pair<Int, Int> {
    val width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    val height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(width, height)
    return Pair(measuredWidth, measuredHeight)
}

/**
 * 异步获取View的宽高
 */
fun View.setSizeListener(onMeasure: (width: Int, height: Int) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    onMeasure(width, height)
                }
            }
    )
}