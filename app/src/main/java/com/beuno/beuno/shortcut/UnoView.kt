package com.beuno.beuno.shortcut

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.view.children

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

/** 隐藏所有的子控件 */
fun ViewGroup.hideContent() {
    for (child in children) {
        if (child.visibility != View.GONE)
            child.visibility = View.GONE
    }
}

/** 显示所有的子控件 */
fun ViewGroup.showContent() {
    for (child in children) {
        if (child.visibility != View.VISIBLE)
            child.visibility = View.VISIBLE
    }
}

/** 隐藏控件 */
fun View.hide() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

/** 显示控件 */
fun View.show() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
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
    val view: View
}

/**
 * 谁用谁知道.
 */
fun View.onGlobalLayoutListener(stuff: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    stuff()
                }
            }
    )
}