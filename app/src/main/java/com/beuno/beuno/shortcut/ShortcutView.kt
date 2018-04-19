package com.beuno.beuno.shortcut

import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.view.children
import com.beuno.beuno.widgets.adapters.UnoBasicAdapter

/** 设为粗体 */
fun TextView.bold(): TextView {
    if (!paint.isFakeBoldText)
        paint.isFakeBoldText = true
    return this
}

/** 设为非粗体 */
fun TextView.boldOff(): TextView {
    if (paint.isFakeBoldText)
        paint.isFakeBoldText = false
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

/** onClickListener 链式 */
fun View.clickListener(onClick: (view: View) -> Unit): View {
    setOnClickListener(onClick)
    return this
}

/**
 * 改变LayoutParams
 */
fun View.setLP(setter : (ViewGroup.LayoutParams) -> Unit): View {
    val lp = layoutParams
    setter(lp)
    layoutParams = lp
    return this
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

/** 显示控件 */
fun View.show(): View {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
    return this
}

/** 隐藏控件 */
fun View.hide(): View {
    if (visibility != View.GONE)
        visibility = View.GONE
    return this
}

/** 启用控件的select属性 */
fun View.select(): View {
    if (!isSelected) isSelected = true
    return this
}

/** 关闭控件的select属性 */
fun View.deselect(): View {
    if (isSelected) isSelected = false
    return this
}

/** 反转控件的select属性 */
fun View.alterSelect(): View {
    isSelected = !isSelected
    return this
}

/** 显示菜单项 */
fun MenuItem.show() {
    isVisible = true
}

/** 隐藏菜单项 */
fun MenuItem.hide() {
    isVisible = false
}

/** RecyclerItem点击事件 */
fun RecyclerView.onItemClick(listener: (pos: Int) -> Unit) {
    val lAdapter = adapter as UnoBasicAdapter
    lAdapter.itemListener = listener
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