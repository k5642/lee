package com.beuno.beuno.page.homepage

import android.support.v7.widget.RecyclerView
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.widgets.adapters.CategoryAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : UnoBaseFragment() {
    override fun topViewToAdjust(): View = top_holder

    private lateinit var mCategoryList: RecyclerView

    override fun layoutRes(): Int = R.layout.fragment_category

    override fun initViews(root: View) {
        mCategoryList = UnoAdapters.initLinearAdapter(root, R.id.list_category,
                activity, RecyclerView.VERTICAL, CategoryAdapter::class.java)
        val adapter = mCategoryList.adapter as CategoryAdapter
        adapter.setOnItemClickListener {
            
        }
    }
}
