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
        val rtn = dependency is AppBarLayout
        if (rtn && child != null && mBaseHeight == 0) mBaseHeight = child.height
        return rtn
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: CardView?, dependency: View?): Boolean {
        // 被观察者变化时, 触发此方法, 变更UnoFloatingCardView
        if (child == null || dependency == null) return false
        changeHeight(child, dependency)
        return true
    }

    /**
     * 基于被观察者, 变更自身高度.
     * 目前的逻辑是, 前三分之一不动, 中间三分之一缩短, 最后三分之一消失.
     * todo 这个逻辑需要优化, 不优雅
     */
    private fun changeHeight(child: CardView, dependency: View) {
        val height = dependency.height
        val distance = -dependency.y
        val lp = child.layoutParams
        lp.height = when {
            distance > height / 3 * 2-> 0
            distance < height / 3 -> mBaseHeight
            else -> ((height / 3 * 2 - distance) / (height / 3) * mBaseHeight).toInt()
        }
        child.layoutParams = lp
        logger("${child.height} height, $distance distance, $height height, $mBaseHeight baseHeight")
    }
}