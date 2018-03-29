package com.beuno.beuno.widgets

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import com.beuno.beuno.shortcut.logger

/**
 * 悬浮于两个View中间的CardView
 */
class UnoFloatingCardView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : CardView(context, attrs), CoordinatorLayout.AttachedBehavior {
    private val mContext: Context = context
    private val mAttrs: AttributeSet? = attrs
    override fun getBehavior() = UnoFloatingCardViewBehavior(mContext, mAttrs)
}

/**
 * 基于AppBarLayout, 可伸缩的Behavior.
 */
class UnoFloatingCardViewBehavior
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : CoordinatorLayout.Behavior<CardView>(context, attrs) {
    /** 基准高度 */
    private var mBaseHeight: Int = 0

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: CardView?, dependency: View?): Boolean {
        // 确认被观察者是AppBarLayout
        return (dependency is AppBarLayout).also {
            // 确认mBaseHeight只被赋值一次.
            if (it && child != null && mBaseHeight == 0) mBaseHeight = child.height
        }
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: CardView?, dependency: View?): Boolean {
        // 被观察者变化时, 触发此方法, 变更UnoFloatingCardView
        if (child == null || dependency == null) return false
        changeHeight(child, dependency)
        return true
    }

    /**
     * 基于被观察者, 变更自身高度.
     */
    private fun changeHeight(child: CardView, dependency: View) {
        // Toolbar开始收缩时, 当前View就开始收缩.
        val toolbarMovingDistance = -dependency.y
        val lpThis = child.layoutParams.apply {
            val heightThis = mBaseHeight - toolbarMovingDistance.toInt()
            height = if (heightThis < 0) 0 else heightThis
        }
        child.layoutParams = lpThis
        logger("${child.height} height, $toolbarMovingDistance distance, $mBaseHeight baseHeight")
    }
}