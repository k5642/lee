package com.beuno.beuno.page.cart

import android.support.v7.widget.RecyclerView
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.shortcut.snack
import com.beuno.beuno.widgets.adapters.CategoryAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters

class CartFragment : UnoBaseFragment() {

    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_EDIT -> snack(mRoot, "EDIT triggered")
        }
    }

    private lateinit var mCartList: RecyclerView

    override fun layoutRes(): Int = R.layout.fragment_cart
    override fun menuRes() = R.menu.menu_cart

    override fun initViews(root: View) {
        mCartList = UnoAdapters.initLinearAdapter(root, R.id.list_cart,
                activity!!, RecyclerView.VERTICAL, CategoryAdapter::class.java)
    }

}
