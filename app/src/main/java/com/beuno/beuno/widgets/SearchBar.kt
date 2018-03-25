package com.beuno.beuno.widgets

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import com.beuno.beuno.R
import kotlinx.android.synthetic.main.merge_search.view.*

// 搜索条

/**
 * Search Activity 外部的用于跳转的搜索栏
 */
class SearchingOutsideToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : Toolbar(context, attrs) {
    init {
        setBackgroundColor(Color.WHITE)
        setNavigationIcon(R.mipmap.ic_action_search)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        super.setOnClickListener(listener)
        setNavigationOnClickListener(listener)
    }
}

/**
 * Search Activity 内部的搜索栏
 */
class SearchingInsideToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : Toolbar(context, attrs) {
    init {
        setBackgroundColor(Color.WHITE)
        setNavigationIcon(R.mipmap.ic_action_back)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        inflate(context, R.layout.merge_search, this)
    }

    fun getInputFocus() {
        toolbar_search_edit_text.requestFocus()
    }

    fun clearText() {
        toolbar_search_edit_text.text = null
    }
}