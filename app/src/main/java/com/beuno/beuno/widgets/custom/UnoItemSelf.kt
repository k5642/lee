package com.beuno.beuno.widgets.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.beuno.beuno.R

/**
 * Self页面中的下方Content项
 */
class UnoItemSelf(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val img: Drawable
    private val txt: String

    @JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : this(context, attr, 0)

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.UnoItemSelf)
        img = typeArray.getDrawable(R.styleable.UnoItemSelf_img_self)
        txt = typeArray.getString(R.styleable.UnoItemSelf_txt_self)
        typeArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.item_self, this)
        this.findViewById<ImageView>(R.id.item_self_img).setImageDrawable(img)
        this.findViewById<TextView>(R.id.item_self_txt).text = txt
    }

    fun onClickListener(stuff: () -> Unit) {
        isClickable = true
        setOnClickListener {
            stuff()
        }
    }

}
