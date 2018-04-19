package com.beuno.beuno.widgets.custom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageButton
import com.beuno.beuno.R
import com.beuno.beuno.shortcut.logger

/**
 * 购买商品时的数量选择视图
 */
class UnoItemNumSelector(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {

    @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : this(context, attr, 0)

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.item_number_selector, this)
        logger("初始化NumSelector")
        mNum = this.findViewById(R.id.item_txt)
        mNum.setSelectAllOnFocus(true)
        this.findViewById<ImageButton>(R.id.item_minus).setOnClickListener {
            minus()
        }
        this.findViewById<ImageButton>(R.id.item_plus).setOnClickListener {
            plus()
        }
    }
    private lateinit var mNum: EditText
    private var mMax: Int = 10000

    fun setMax(max: Int) {
        mMax = max
    }

    fun setNum(num: Int) {
        mNum.setText("$num")
    }

    fun getNum(): Int {
        val value = if (mNum.text.isEmpty()) "0" else mNum.text
        return value.toString().toInt()
    }

    private fun plus() {
        val num = getNum()
        if (num < mMax) {
            setNum(num + 1)
        }
    }

    private fun minus() {
        val num = getNum()
        if (num > 0) {
            setNum(num - 1)
        }
    }

}
