package com.beuno.beuno.shortcut

import android.app.Activity
import android.graphics.Rect
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConfig

/** 设为粗体 */
fun TextView.bold(): TextView {
    paint.isFakeBoldText = true
    return this
}

/**
 * 最上层的View的高度隔离出状态栏
 */
fun Activity.adjustTopViewWithStatusBar(view: View) {
    val statusBarHeight = getStatusBarHeight(this)
    val height = view.getSize().second
    view.layoutParams.height = height + statusBarHeight
    view.setPadding(view.paddingLeft, view.paddingTop + statusBarHeight, view.paddingRight, view.paddingBottom)
}

/**
 * 最上层的View的高度隔离出状态栏
 */
fun Activity.adjustTopViewWithStatusBarAndToolbar(view: View) {
    val height = view.getSize().second
    // 这个除以二, 我也不知道是为嘛...
    view.layoutParams.height = height + UnoConfig.TOOLBAR_HEIGHT / 2
    logger("top view height: ${view.layoutParams.height}")
    view.setPadding(view.paddingLeft, view.paddingTop + UnoConfig.TOOLBAR_HEIGHT / 2, view.paddingRight, view.paddingBottom)
}

/**
 * 最上层的View的高度隔离出状态栏
 */
fun Activity.adjustToolbar() {
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    if (toolbar != null) {
        val statusBarHeight = getStatusBarHeight(this)
        val height = UnoConfig.TOOLBAR_HEIGHT
        toolbar.layoutParams.height = height + statusBarHeight
        toolbar.setPadding(0, statusBarHeight, 0, 0)
        logger("toolbar height: $height -> ${toolbar.layoutParams.height}")
    }
}

/**
 * 获取状态栏高度
 */
private fun getStatusBarHeight(activity: Activity): Int {
    return Rect()
            .also { activity.window.decorView.getWindowVisibleDisplayFrame(it) }
            .top
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