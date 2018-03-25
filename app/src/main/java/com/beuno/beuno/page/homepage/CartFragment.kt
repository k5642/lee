package com.beuno.beuno.page.homepage

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.shortcut.snack

class CartFragment : UnoBaseFragment() {
    override fun layoutRes(): Int = R.layout.fragment_cart
    override fun menuRes() = R.menu.menu_cart

    override fun onMenuItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.action_edit -> snack(mRoot, "EDIT triggered")
        }
        return true
    }

    override fun initViews(root: View) {

    }

}
