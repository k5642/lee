package com.beuno.beuno.page.homepage

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseFragment
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : UnoBaseFragment() {
    override fun topViewToAdjust(): View = tmp
    override fun layoutRes(): Int = R.layout.fragment_cart

    override fun initViews(root: View) {

    }
}
