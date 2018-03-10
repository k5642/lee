package com.beuno.beuno.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.beuno.beuno.R

/**
 * TODO: document your custom view class.
 */
class BannerIndiator(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
    /**页数  */
    private var count: Int = 0
    /** 当前显示的是第几页  */
    private var current: Int = 0
    private val mPaint: Paint = Paint().apply { isAntiAlias = true }

    @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : this(context, attr, 0)

    init {
        setScreenCount(3)
        onScreenChange(1)
    }

    fun setScreenCount(count: Int) {
        this.count = count
    }

    fun onScreenChange(currentScreen: Int) {
        if (currentScreen != current) {
            current = currentScreen
            postInvalidate()
        }
    }

    @Suppress("DEPRECATION")
    override fun onDraw(canvas: Canvas) {
        if (count <= 1) {
            this.visibility = View.GONE
            return
        }
        val height = height.toFloat()
        val width = DESIGN_INDICATOR_WIDTH.toFloat()

        mPaint.isAntiAlias = true
        for (i in 0 until count) {
            if (i == current) {
                mPaint.color = context.resources.getColor(R.color.indicator_on)
            } else {
                mPaint.color = context.resources.getColor(R.color.indicator_off)
            }
            canvas.drawRect(0f, 0f, width, height, mPaint)
        }
    }

    companion object {
        private val DESIGN_INDICATOR_WIDTH = 520
        private val DESIGN_INDICATOR_DISTANCE = 4
        private val DESIGN_BOTTOM_HEIGHT = 52
    }

}
