package com.beuno.beuno.page.homepage

import android.support.v7.widget.RecyclerView
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoSearchBarFragment
import com.beuno.beuno.page.settings.SettingsActivity
import com.beuno.beuno.widgets.adapters.CategoryAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters

/**
 * MainActivity下的商品分类页面.
 *
 * todo UI重制, item展开项.
 */
class HomeCategoryFragment : UnoSearchBarFragment() {
    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_SETTINGS -> SettingsActivity::class.java.startActivityForTest()
        }
    }

    private lateinit var mCategoryList: RecyclerView

    override fun layoutRes(): Int = R.layout.fragment_home_category

    override fun initViews(root: View) {
        mCategoryList = UnoAdapters.initLinearAdapter(root, R.id.list_category,
                activity!!, RecyclerView.VERTICAL, CategoryAdapter::class.java)
//        val adapter = mCategoryList.adapter as CategoryAdapter
    }
}
