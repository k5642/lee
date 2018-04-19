package com.beuno.beuno.widgets.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.beuno.beuno.R

/**
 * 上方图片, 下方文字.
 */
class UnoItemGoods(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val img: Drawable
    private val txt: String

    @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : this(context, attr, 0)

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.UnoItemGoods)
        img = typeArray.getDrawable(R.styleable.UnoItemGoods_img)
        txt = typeArray.getString(R.styleable.UnoItemGoods_txt)
        typeArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.item_goods, this)
        this.findViewById<ImageView>(R.id.item_img).setImageDrawable(img)
        this.findViewById<TextView>(R.id.item_txt).text = txt
    }

    fun onClickListener(stuff: () -> Unit) {
        isClickable = true
        setOnClickListener {
            stuff()
        }
    }

}
