package com.beuno.beuno.page.cart

import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.shortcut.toFragment

class CartActivity : UnoBaseSimpleActivity() {
    override fun defaultFragment() = CartFragment::class.java.toFragment()
}
